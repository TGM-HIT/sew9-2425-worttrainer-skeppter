package sutaj.worttrainer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
public class PersistenceManager {
    private String filePath;

    public PersistenceManager(String filePath) {
        this.filePath = filePath;
    }

    public void save(SpellingTrainer trainer) throws IOException {
        JSONObject root = new JSONObject();

        // Save availablePairs
        JSONArray pairsArray = new JSONArray();
        for (WordImagePair pair : trainer.getAvailablePairs()) {
            JSONObject pairObj = new JSONObject();
            pairObj.put("word", pair.getWord());
            pairObj.put("imageUrl", pair.getImageUrl());
            pairsArray.put(pairObj);
        }
        root.put("availablePairs", pairsArray);

        // Save statistics
        JSONObject statsObj = new JSONObject();
        Statistics stats = trainer.getStatistics();
        statsObj.put("total", stats.getTotal());
        statsObj.put("correct", stats.getCorrect());
        statsObj.put("incorrect", stats.getIncorrect());
        root.put("statistics", statsObj);

        // Save currentPair if exists
        if (trainer.getCurrentPair() != null) {
            JSONObject currentPairObj = new JSONObject();
            currentPairObj.put("word", trainer.getCurrentPair().getWord());
            currentPairObj.put("imageUrl", trainer.getCurrentPair().getImageUrl());
            root.put("currentPair", currentPairObj);
        }

        // Write to file
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(root.toString(4)); // Pretty print with indentation
        }
    }

    public SpellingTrainer load() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null; // Indicate that no persisted data exists
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        JSONObject root = new JSONObject(content.toString());

        // Load availablePairs
        JSONArray pairsArray = root.getJSONArray("availablePairs");
        List<WordImagePair> pairs = new ArrayList<>();
        for (int i = 0; i < pairsArray.length(); i++) {
            JSONObject pairObj = pairsArray.getJSONObject(i);
            String word = pairObj.getString("word");
            String imageUrl = pairObj.getString("imageUrl");
            pairs.add(new WordImagePair(word, imageUrl));
        }

        // Initialize trainer
        SpellingTrainer trainer = new SpellingTrainer(pairs);

        // Load statistics
        if (root.has("statistics")) {
            JSONObject statsObj = root.getJSONObject("statistics");
            Statistics stats = trainer.getStatistics();
            stats.setTotal(statsObj.getInt("total"));
            stats.setCorrect(statsObj.getInt("correct"));
            stats.setIncorrect(statsObj.getInt("incorrect"));
        }

        // Load currentPair if exists
        if (root.has("currentPair")) {
            JSONObject currentPairObj = root.getJSONObject("currentPair");
            String word = currentPairObj.getString("word");
            String imageUrl = currentPairObj.getString("imageUrl");
            for (WordImagePair pair : pairs) {
                if (pair.getWord().equals(word) && pair.getImageUrl().equals(imageUrl)) {
                    trainer.selectPairByIndex(pairs.indexOf(pair));
                    break;
                }
            }
        }

        return trainer;
    }
}
