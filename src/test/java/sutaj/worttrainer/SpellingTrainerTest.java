package sutaj.worttrainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class SpellingTrainerTest {

    private SpellingTrainer trainer;
    private WordImagePair pair1;
    private WordImagePair pair2;

    /**
     * Einrichtung vor jedem Test.
     */
    @BeforeEach
    void setUp() {
        pair1 = new WordImagePair("Hund", "https://example.com/hund.jpg");
        pair2 = new WordImagePair("Katze", "https://example.com/katze.jpg");
        List<WordImagePair> pairs = Arrays.asList(pair1, pair2);
        trainer = new SpellingTrainer(pairs);
    }

    /**
     * Testet die Initialisierung des Trainers mit verfügbaren Paaren.
     */
    @Test
    void testInitialization() {
        List<WordImagePair> availablePairs = trainer.getAvailablePairs();
        assertEquals(2, availablePairs.size(), "Es sollten 2 verfügbare Paare initialisiert werden.");
        assertTrue(availablePairs.contains(pair1), "Das Paar1 sollte in den verfügbaren Paaren enthalten sein.");
        assertTrue(availablePairs.contains(pair2), "Das Paar2 sollte in den verfügbaren Paaren enthalten sein.");
    }

    /**
     * Testet die Auswahl eines Paars nach Index.
     */
    @Test
    void testSelectPairByIndex() {
        trainer.selectPairByIndex(0);
        assertEquals(pair1, trainer.getCurrentPair(), "Das aktuelle Paar sollte Paar1 sein.");

        trainer.selectPairByIndex(1);
        assertEquals(pair2, trainer.getCurrentPair(), "Das aktuelle Paar sollte Paar2 sein.");

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            trainer.selectPairByIndex(2);
        });

        String expectedMessage = "Ungültiger Index.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Es sollte eine IndexOutOfBoundsException ausgelöst werden.");
    }

    /**
     * Testet die zufällige Auswahl eines Paars.
     */
    @Test
    void testSelectRandomPair() {
        trainer.selectRandomPair();
        WordImagePair current = trainer.getCurrentPair();
        assertTrue(current.equals(pair1) || current.equals(pair2), "Das aktuelle Paar sollte entweder Paar1 oder Paar2 sein.");
    }

    /**
     * Testet die Überprüfung einer korrekten Antwort.
     */
    @Test
    void testSubmitCorrectAnswer() {
        trainer.selectPairByIndex(0);
        trainer.submitAnswer("Hund");

        assertEquals(1, trainer.getStatistics().getTotal(), "Gesamtversuche sollten 1 sein.");
        assertEquals(1, trainer.getStatistics().getCorrect(), "Korrekte Antworten sollten 1 sein.");
        assertEquals(0, trainer.getStatistics().getIncorrect(), "Falsche Antworten sollten 0 sein.");
        assertNull(trainer.getCurrentPair(), "Es sollte kein aktuelles Paar mehr ausgewählt sein.");
        assertEquals(1, trainer.getAvailablePairs().size(), "Es sollte nur noch 1 verfügbares Paar übrig sein.");
        assertFalse(trainer.getAvailablePairs().contains(pair1), "Paar1 sollte entfernt worden sein.");
    }

    /**
     * Testet die Überprüfung einer falschen Antwort.
     */
    @Test
    void testSubmitIncorrectAnswer() {
        trainer.selectPairByIndex(0);
        trainer.submitAnswer("Katze");

        assertEquals(1, trainer.getStatistics().getTotal(), "Gesamtversuche sollten 1 sein.");
        assertEquals(0, trainer.getStatistics().getCorrect(), "Korrekte Antworten sollten 0 sein.");
        assertEquals(1, trainer.getStatistics().getIncorrect(), "Falsche Antworten sollten 1 sein.");
        assertNotNull(trainer.getCurrentPair(), "Es sollte weiterhin ein aktuelles Paar ausgewählt sein.");
        assertEquals(pair1, trainer.getCurrentPair(), "Das aktuelle Paar sollte Paar1 bleiben.");
        assertEquals(2, trainer.getAvailablePairs().size(), "Es sollten weiterhin 2 verfügbare Paare vorhanden sein.");
    }

    /**
     * Testet das Entfernen eines Paars nach korrekter Antwort und das erneute Laden.
     */
    @Test
    void testRemovePairAfterCorrectAnswer() {
        trainer.selectPairByIndex(0);
        trainer.submitAnswer("Hund");

        List<WordImagePair> availablePairs = trainer.getAvailablePairs();
        assertEquals(1, availablePairs.size(), "Es sollte nur noch 1 verfügbares Paar übrig sein.");
        assertFalse(availablePairs.contains(pair1), "Paar1 sollte entfernt worden sein.");
        assertTrue(availablePairs.contains(pair2), "Paar2 sollte weiterhin verfügbar sein.");
    }

    /**
     * Testet die Initialisierung mit null Paarliste.
     * Erwartet eine IllegalArgumentException.
     */
    @Test
    void testInitializationWithNullPairs() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new SpellingTrainer(null);
        });

        String expectedMessage = "Paarliste darf nicht null sein.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Es sollte eine IllegalArgumentException wegen null Paarliste ausgelöst werden.");
    }

    /**
     * Testet das Setzen einer neuen Paarliste.
     */
    @Test
    void testSetAvailablePairs() {
        WordImagePair pair3 = new WordImagePair("Apfel", "https://example.com/apfel.jpg");
        trainer.setAvailablePairs(Arrays.asList(pair3));

        List<WordImagePair> availablePairs = trainer.getAvailablePairs();
        assertEquals(1, availablePairs.size(), "Es sollte genau 1 verfügbares Paar vorhanden sein.");
        assertTrue(availablePairs.contains(pair3), "Paar3 sollte in den verfügbaren Paaren enthalten sein.");
        assertFalse(availablePairs.contains(pair1), "Paar1 sollte nicht mehr verfügbar sein.");
        assertFalse(availablePairs.contains(pair2), "Paar2 sollte nicht mehr verfügbar sein.");
    }

    /**
     * Testet das Setzen einer null Paarliste.
     * Erwartet eine IllegalArgumentException.
     */
    @Test
    void testSetAvailablePairsWithNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainer.setAvailablePairs(null);
        });

        String expectedMessage = "Paarliste darf nicht null sein.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Es sollte eine IllegalArgumentException wegen null Paarliste ausgelöst werden.");
    }
}
