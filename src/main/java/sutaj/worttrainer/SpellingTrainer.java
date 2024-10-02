package sutaj.worttrainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SpellingTrainer {
    private List<WordImagePair> availablePairs;
    private Set<WordImagePair> selectedPairs;
    private WordImagePair currentPair;
    private Statistics statistics;
    private Random random;

    public SpellingTrainer(List<WordImagePair> pairs) {
        if (pairs == null) {
            throw new IllegalArgumentException("Paarliste darf nicht null sein.");
        }
        this.availablePairs = new ArrayList<>(pairs);
        this.selectedPairs = new HashSet<>();
        this.currentPair = null;
        this.statistics = new Statistics();
        this.random = new Random();
    }

    public void selectPairByIndex(int index) {
        if (index < 0 || index >= availablePairs.size()) {
            throw new IndexOutOfBoundsException("Ungültiger Index.");
        }
        currentPair = availablePairs.get(index);
    }

    public void selectRandomPair() {
        if (availablePairs.isEmpty()) {
            currentPair = null;
            return;
        }
        int index = random.nextInt(availablePairs.size());
        currentPair = availablePairs.get(index);
    }

    public WordImagePair getCurrentPair() {
        return currentPair;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void submitAnswer(String answer) {
        if (currentPair == null) {
            throw new IllegalStateException("Kein aktuelles Paar ausgewählt.");
        }
        if (answer == null) {
            throw new IllegalArgumentException("Antwort darf nicht null sein.");
        }

        statistics.incrementTotal();

        if (currentPair.getWord().equalsIgnoreCase(answer.trim())) {
            statistics.incrementCorrect();
            // Entferne das Paar nach korrekter Antwort
            availablePairs.remove(currentPair);
            selectedPairs.remove(currentPair);
            currentPair = null;
        } else {
            statistics.incrementIncorrect();
            // Paar bleibt ausgewählt
        }
    }

    public List<WordImagePair> getAvailablePairs() {
        return new ArrayList<>(availablePairs);
    }

    public void setAvailablePairs(List<WordImagePair> pairs) {
        if (pairs == null) {
            throw new IllegalArgumentException("Paarliste darf nicht null sein.");
        }
        this.availablePairs = new ArrayList<>(pairs);
        this.selectedPairs.clear();
        this.currentPair = null;
    }
}

