package sutaj.worttrainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Verwaltet die Hauptlogik des Rechtschreibtrainers, einschließlich der Auswahl
 * von Wort-Bild-Paaren, der Bewertung von Antworten und der Verfolgung von Statistiken.
 *
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
public class SpellingTrainer {
    private List<WordImagePair> availablePairs; // Liste der verfügbaren Wort-Bild-Paare
    private Set<WordImagePair> selectedPairs;  // Set der bereits ausgewählten Paare
    private WordImagePair currentPair;         // Aktuell ausgewähltes Wort-Bild-Paar
    private Statistics statistics;             // Statistiken des Trainers
    private Random random;                     // Zufallsgenerator zur Auswahl von Paaren

    /**
     * Erstellt einen neuen SpellingTrainer mit der gegebenen Liste von Wort-Bild-Paaren.
     *
     * @param pairs Eine Liste der Wort-Bild-Paare. Darf nicht null sein.
     * @throws IllegalArgumentException Wenn die übergebene Liste null ist.
     */
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

    /**
     * Wählt ein Wort-Bild-Paar anhand eines angegebenen Indexes aus.
     *
     * @param index Der Index des auszuwählenden Paares.
     * @throws IndexOutOfBoundsException Wenn der Index außerhalb des gültigen Bereichs liegt.
     */
    public void selectPairByIndex(int index) {
        if (index < 0 || index >= availablePairs.size()) {
            throw new IndexOutOfBoundsException("Ungültiger Index.");
        }
        currentPair = availablePairs.get(index);
    }

    /**
     * Wählt ein zufälliges Wort-Bild-Paar aus den verfügbaren Paaren aus.
     * Falls keine Paare verfügbar sind, wird das aktuelle Paar auf null gesetzt.
     */
    public void selectRandomPair() {
        if (availablePairs.isEmpty()) {
            currentPair = null;
            return;
        }
        int index = random.nextInt(availablePairs.size());
        currentPair = availablePairs.get(index);
    }

    /**
     * Gibt das aktuell ausgewählte Wort-Bild-Paar zurück.
     *
     * @return Das aktuelle {@code WordImagePair}, oder {@code null}, wenn kein Paar ausgewählt ist.
     */
    public WordImagePair getCurrentPair() {
        return currentPair;
    }

    /**
     * Gibt die Statistiken des Trainers zurück.
     *
     * @return Ein {@code Statistics}-Objekt mit den aktuellen Trainingsstatistiken.
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Überprüft die gegebene Antwort und aktualisiert die Statistiken entsprechend.
     * Falls die Antwort korrekt ist, wird das aktuelle Paar entfernt.
     *
     * @param answer Die vom Benutzer eingegebene Antwort. Darf nicht null sein.
     * @throws IllegalStateException Wenn kein aktuelles Paar ausgewählt ist.
     * @throws IllegalArgumentException Wenn die Antwort null ist.
     */
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

    /**
     * Gibt eine Liste der aktuell verfügbaren Wort-Bild-Paare zurück.
     *
     * @return Eine Kopie der Liste der verfügbaren Paare.
     */
    public List<WordImagePair> getAvailablePairs() {
        return new ArrayList<>(availablePairs);
    }

    /**
     * Setzt die verfügbaren Wort-Bild-Paare auf die gegebene Liste zurück.
     * Löscht alle ausgewählten Paare und setzt das aktuelle Paar zurück.
     *
     * @param pairs Eine neue Liste der Wort-Bild-Paare. Darf nicht null sein.
     * @throws IllegalArgumentException Wenn die übergebene Liste null ist.
     */
    public void setAvailablePairs(List<WordImagePair> pairs) {
        if (pairs == null) {
            throw new IllegalArgumentException("Paarliste darf nicht null sein.");
        }
        this.availablePairs = new ArrayList<>(pairs);
        this.selectedPairs.clear();
        this.currentPair = null;
    }
}
