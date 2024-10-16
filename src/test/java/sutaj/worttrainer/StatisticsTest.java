package sutaj.worttrainer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
class StatisticsTest {

    /**
     * Testet die Initialisierung der Statistikwerte.
     */
    @Test
    void testStatisticsInitialization() {
        Statistics stats = new Statistics();

        assertEquals(0, stats.getTotal(), "Initialer Gesamtwert sollte 0 sein.");
        assertEquals(0, stats.getCorrect(), "Initialer korrekter Wert sollte 0 sein.");
        assertEquals(0, stats.getIncorrect(), "Initialer falscher Wert sollte 0 sein.");
    }

    /**
     * Testet die Inkrementierung der Gesamtversuche.
     */
    @Test
    void testIncrementTotal() {
        Statistics stats = new Statistics();
        stats.incrementTotal();

        assertEquals(1, stats.getTotal(), "Gesamtversuche sollten um 1 erhöht werden.");
    }

    /**
     * Testet die Inkrementierung der korrekten Antworten.
     */
    @Test
    void testIncrementCorrect() {
        Statistics stats = new Statistics();
        stats.incrementCorrect();

        assertEquals(1, stats.getCorrect(), "Korrekte Antworten sollten um 1 erhöht werden.");
    }

    /**
     * Testet die Inkrementierung der falschen Antworten.
     */
    @Test
    void testIncrementIncorrect() {
        Statistics stats = new Statistics();
        stats.incrementIncorrect();

        assertEquals(1, stats.getIncorrect(), "Falsche Antworten sollten um 1 erhöht werden.");
    }

    /**
     * Testet die Setter-Methoden der Statistikklasse.
     */
    @Test
    void testSetters() {
        Statistics stats = new Statistics();

        stats.setTotal(10);
        stats.setCorrect(7);
        stats.setIncorrect(3);

        assertEquals(10, stats.getTotal(), "Gesamtversuche sollten auf 10 gesetzt werden.");
        assertEquals(7, stats.getCorrect(), "Korrekte Antworten sollten auf 7 gesetzt werden.");
        assertEquals(3, stats.getIncorrect(), "Falsche Antworten sollten auf 3 gesetzt werden.");
    }

    /**
     * Testet die Inkrementierungsreihenfolge und die Korrektheit der Werte.
     */
    @Test
    void testIncrementSequence() {
        Statistics stats = new Statistics();

        stats.incrementTotal();
        stats.incrementCorrect();
        stats.incrementTotal();
        stats.incrementIncorrect();

        assertEquals(2, stats.getTotal(), "Gesamtversuche sollten 2 sein.");
        assertEquals(1, stats.getCorrect(), "Korrekte Antworten sollten 1 sein.");
        assertEquals(1, stats.getIncorrect(), "Falsche Antworten sollten 1 sein.");
    }
}
