package sutaj.worttrainer;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Hauptklasse des Rechtschreibtrainers. Stellt die Benutzeroberfläche bereit und
 * verwaltet die Hauptlogik des Programms, einschließlich der Lade- und Speicheroperationen
 * sowie des Trainingsprozesses.
 *
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
public class Main {
    private static final String DATA_FILE = "src/main/resources/wordtrainer.json"; // Dateipfad für gespeicherte Daten
    private static final int MAX_IMAGE_WIDTH = 600; // Maximale Breite für Bilder
    private static final int MAX_IMAGE_HEIGHT = 300; // Maximale Höhe für Bilder

    /**
     * Einstiegspunkt des Programms. Verwaltet das Laden von Daten, den Trainingsprozess
     * und das Speichern des Fortschritts.
     *
     * @param args Argumente der Kommandozeile (nicht verwendet)
     */
    public static void main(String[] args) {
        PersistenceManager persistenceManager = new PersistenceManager(DATA_FILE);
        SpellingTrainer trainer;

        // Lade gespeicherte Daten oder erstelle einen neuen Trainer, falls keine Daten vorhanden sind
        try {
            trainer = persistenceManager.load();
            if (trainer == null) {
                trainer = createDefaultTrainer();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Daten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            trainer = createDefaultTrainer();
        }

        // Haupt-Trainingsschleife
        boolean continueTraining = true;
        WordImagePair previousPair = null;
        boolean previousResult = false; // true = letzte Antwort korrekt, false = falsch

        while (continueTraining) {
            // Zufälliges Wort-Bild-Paar auswählen
            trainer.selectRandomPair();
            WordImagePair currentPair = trainer.getCurrentPair();

            if (currentPair == null) {
                JOptionPane.showMessageDialog(null, "Alle Wörter wurden richtig geraten! Gut gemacht!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);

                // Datei mit Standard-Wort-Bild-Paaren und zurückgesetzter Statistik überschreiben
                try {
                    SpellingTrainer resetTrainer = createDefaultTrainer(); // Standarddaten erstellen
                    Statistics resetStatistics = resetTrainer.getStatistics(); // Statistik abrufen und zurücksetzen
                    resetStatistics.setTotal(0);
                    resetStatistics.setCorrect(0);
                    resetStatistics.setIncorrect(0);
                    persistenceManager.save(resetTrainer); // Trainer mit zurückgesetzten Daten speichern

                    JOptionPane.showMessageDialog(null, "Die Daten wurden zurückgesetzt. Beim nächsten Start beginnt das Training von vorne.", "Zurücksetzen", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Fehler beim Zurücksetzen der Daten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
                System.exit(0);
                break;
            }





            // Statistik- und Ergebnismeldung zusammenstellen
            String statsMessage = "<html><body>" +
                    "<h3>Statistik:</h3>" +
                    "<p>Gesamtversuche: " + trainer.getStatistics().getTotal() + "<br>" +
                    "Richtig: " + trainer.getStatistics().getCorrect() + "<br>" +
                    "Falsch: " + trainer.getStatistics().getIncorrect() + "</p>";

            if (previousPair != null) {
                statsMessage += "<p>Letzter Versuch mit <strong>" + previousPair.getWord() + "</strong>: " +
                        (previousResult ? "Richtig!" : "Falsch!") + "</p>";
            }

            // Bild laden und skalieren
            ImageIcon imageIcon;
            try {
                URL url = new URL(currentPair.getImageUrl());
                imageIcon = new ImageIcon(url);
                imageIcon = ImageUtils.getScaledImageIcon(imageIcon, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);
            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(null, "Ungültige Bild-URL: " + currentPair.getImageUrl(), "Fehler", JOptionPane.ERROR_MESSAGE);
                imageIcon = new ImageIcon(); // Fallback
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Laden des Bildes: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                imageIcon = new ImageIcon(); // Fallback
            }

            // Panel mit Bild und Statistik erstellen
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("<html>" + statsMessage + "</html>"));
            panel.add(Box.createVerticalStrut(10)); // Abstand
            panel.add(new JLabel(imageIcon));

            // Benutzeraufforderung für Eingabe
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
                continueTraining = false; // Beenden, falls keine Eingabe
            } else {
                trainer.submitAnswer(userInput);
                previousPair = currentPair;
                previousResult = trainer.getCurrentPair() == null; // Richtig, falls null
                JOptionPane.showMessageDialog(null,
                        previousResult ? "Richtig! Weiter geht's." : "Falsch! Versuch es noch einmal.",
                        previousResult ? "Erfolg" : "Fehler",
                        previousResult ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
            }
        }

        // Fortschritt speichern
        try {
            persistenceManager.save(trainer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern der Daten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(null, "Training beendet. Bis zum nächsten Mal!", "Abschluss", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    /**
     * Erstellt einen neuen Trainer mit einer Standardliste von Wort-Bild-Paaren.
     *
     * @return Ein SpellingTrainer mit voreingestellten Daten.
     */
    private static SpellingTrainer createDefaultTrainer() {
        List<WordImagePair> defaultPairs = new ArrayList<>();
        defaultPairs.add(new WordImagePair("Hund", "https://i.natgeofe.com/n/4f5aaece-3300-41a4-b2a8-ed2708a0a27c/domestic-dog_thumb_square.jpg"));
        defaultPairs.add(new WordImagePair("Katze", "https://media.4-paws.org/a/5/c/4/a5c4c9cdfd3a8ecb58e9b1a5bd496c9dfbc3cedc/VIER%20PFOTEN_2020-10-07_00132-2890x2000-1920x1329.jpg"));
        defaultPairs.add(new WordImagePair("Apfel", "https://www.mcdonalds.at/wp-content/uploads/2023/02/1500x1500-web-pop-neu-happy-meal-apfel.png"));
        defaultPairs.add(new WordImagePair("Baum", "https://biberberti.com/wp-content/uploads/2021/08/04-px-alter-Baum.jpg"));
        return new SpellingTrainer(defaultPairs);
    }
}