package sutaj.worttrainer;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String DATA_FILE = "src/main/resources/wordtrainer.json";
    private static final int MAX_IMAGE_WIDTH = 600;
    private static final int MAX_IMAGE_HEIGHT = 300;

    public static void main(String[] args) {
        // Initialize PersistenceManager
        PersistenceManager persistenceManager = new PersistenceManager(DATA_FILE);
        SpellingTrainer trainer = null;

        // Load persisted data
        try {
            trainer = persistenceManager.load();
            if (trainer == null) {
                // Keine persistierten Daten gefunden, erstelle neuen Trainer
                trainer = createDefaultTrainer();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Daten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            trainer = createDefaultTrainer();
        }

        // Hauptschleife
        boolean continueTraining = true;
        WordImagePair previousPair = null;
        boolean previousResult = false; // true für richtig, false für falsch

        while (continueTraining) {
            // Wähle zufälliges Paar
            trainer.selectRandomPair();
            WordImagePair currentPair = trainer.getCurrentPair();

            if (currentPair == null) {
                JOptionPane.showMessageDialog(null, "Alle Wörter wurden richtig geraten! Gut gemacht!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            // Erstelle die Statistik-Nachricht
            String statsMessage = "<html><body>" +
                    "<h3>Statistik:</h3>" +
                    "<p>Gesamtversuche: " + trainer.getStatistics().getTotal() + "<br>" +
                    "Richtig: " + trainer.getStatistics().getCorrect() + "<br>" +
                    "Falsch: " + trainer.getStatistics().getIncorrect() + "</p>";

            // Optional: Zeige vorheriges Ergebnis
            if (previousPair != null) {
                String resultMessage = previousResult ? "Richtig!" : "Falsch!";
                statsMessage += "<p>Letzter Versuch mit <strong>" + previousPair.getWord() + "</strong>: " + resultMessage + "</p>";
            }

            // Lade das Bild von der URL
            ImageIcon imageIcon = null;
            try {
                URL url = new URL(currentPair.getImageUrl());
                imageIcon = new ImageIcon(url);
                // Skaliere das Bild auf die maximalen Abmessungen
                imageIcon = ImageUtils.getScaledImageIcon(imageIcon, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);
            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "Ungültige Bild-URL: " + currentPair.getImageUrl(), "Fehler", JOptionPane.ERROR_MESSAGE);
                // Setze imageIcon auf ein leeres Icon oder eine Standardgrafik
                imageIcon = new ImageIcon();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Laden des Bildes: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                imageIcon = new ImageIcon();
            }

            // Erstelle ein JLabel mit dem Bild
            JLabel imageLabel = new JLabel(imageIcon);

            // Erstelle ein JPanel, das Statistik und Bild enthält
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("<html>" + statsMessage + "</html>"));
            panel.add(Box.createVerticalStrut(10)); // Abstand
            panel.add(imageLabel);

            // Eingabeaufforderung
            String userInput = (String) JOptionPane.showInputDialog(
                    null,
                    panel,
                    "Rechtschreibtrainer",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    ""
            );

            if (userInput == null || userInput.trim().isEmpty()) {
                // Beende die Schleife
                continueTraining = false;
            } else {
                // Überprüfe die Antwort
                trainer.submitAnswer(userInput);
                previousPair = currentPair;
                previousResult = trainer.getCurrentPair() == null; // true, wenn richtig
                if (previousResult) {
                    JOptionPane.showMessageDialog(null, "Richtig! Weiter geht's.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Falsch! Versuch es noch einmal.", "Fehler", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        // Speichere den aktuellen Zustand
        try {
            persistenceManager.save(trainer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern der Daten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(null, "Training beendet. Bis zum nächsten Mal!", "Abschluss", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static SpellingTrainer createDefaultTrainer() {
        List<WordImagePair> defaultPairs = new ArrayList<>();
        defaultPairs.add(new WordImagePair("Hund", "https://i.natgeofe.com/n/4f5aaece-3300-41a4-b2a8-ed2708a0a27c/domestic-dog_thumb_square.jpg"));
        defaultPairs.add(new WordImagePair("Katze", "https://media.4-paws.org/a/5/c/4/a5c4c9cdfd3a8ecb58e9b1a5bd496c9dfbc3cedc/VIER%20PFOTEN_2020-10-07_00132-2890x2000-1920x1329.jpg"));
        defaultPairs.add(new WordImagePair("Apfel", "https://www.mcdonalds.at/wp-content/uploads/2023/02/1500x1500-web-pop-neu-happy-meal-apfel.png"));
        defaultPairs.add(new WordImagePair("Baum", "https://biberberti.com/wp-content/uploads/2021/08/04-px-alter-Baum.jpg"));
        // Füge weitere Paare nach Bedarf hinzu

        return new SpellingTrainer(defaultPairs);
    }
}
