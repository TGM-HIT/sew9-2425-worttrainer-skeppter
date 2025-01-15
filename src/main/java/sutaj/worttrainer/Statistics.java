package sutaj.worttrainer;

/**
 * Verwaltet die Statistiken eines Rechtschreibtrainers, einschließlich der Gesamtanzahl
 * von Versuchen, korrekten und inkorrekten Antworten.
 *
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
public class Statistics {
    private int total;    // Gesamtanzahl der Versuche
    private int correct;  // Anzahl der korrekten Antworten
    private int incorrect; // Anzahl der falschen Antworten

    /**
     * Erstellt ein neues {@code Statistics}-Objekt mit allen Werten auf 0 initialisiert.
     */
    public Statistics() {
        this.total = 0;
        this.correct = 0;
        this.incorrect = 0;
    }

    /**
     * Erhöht die Gesamtanzahl der Versuche um 1.
     */
    public void incrementTotal() {
        total++;
    }

    /**
     * Erhöht die Anzahl der korrekten Antworten um 1.
     */
    public void incrementCorrect() {
        correct++;
    }

    /**
     * Erhöht die Anzahl der inkorrekten Antworten um 1.
     */
    public void incrementIncorrect() {
        incorrect++;
    }

    /**
     * Gibt die Gesamtanzahl der Versuche zurück.
     *
     * @return Die Gesamtanzahl der Versuche.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Gibt die Anzahl der korrekten Antworten zurück.
     *
     * @return Die Anzahl der korrekten Antworten.
     */
    public int getCorrect() {
        return correct;
    }

    /**
     * Gibt die Anzahl der inkorrekten Antworten zurück.
     *
     * @return Die Anzahl der inkorrekten Antworten.
     */
    public int getIncorrect() {
        return incorrect;
    }

    /**
     * Setzt die Gesamtanzahl der Versuche.
     *
     * @param total Die neue Gesamtanzahl der Versuche.
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Setzt die Anzahl der korrekten Antworten.
     *
     * @param correct Die neue Anzahl der korrekten Antworten.
     */
    public void setCorrect(int correct) {
        this.correct = correct;
    }

    /**
     * Setzt die Anzahl der inkorrekten Antworten.
     *
     * @param incorrect Die neue Anzahl der inkorrekten Antworten.
     */
    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }
}
