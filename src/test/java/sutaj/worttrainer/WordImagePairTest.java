package sutaj.worttrainer;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
class WordImagePairTest {

    /**
     * Testet die korrekte Erstellung eines gültigen WordImagePair-Objekts.
     */
    @Test
    void testValidWordImagePairCreation() {
        String word = "Hund";
        String imageUrl = "https://example.com/hund.jpg";

        WordImagePair pair = new WordImagePair(word, imageUrl);

        assertEquals(word, pair.getWord(), "Das Wort sollte korrekt gesetzt werden.");
        assertEquals(imageUrl, pair.getImageUrl(), "Die Bild-URL sollte korrekt gesetzt werden.");
    }

    /**
     * Testet die Erstellung eines WordImagePair-Objekts mit ungültiger Bild-URL.
     * Erwartet eine IllegalArgumentException.
     */
    @Test
    void testInvalidImageUrl() {
        String word = "Katze";
        String invalidImageUrl = "htp:/invalid-url";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new WordImagePair(word, invalidImageUrl);
        });

        String expectedMessage = "Ungültige Bild-URL.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Es sollte eine IllegalArgumentException wegen ungültiger Bild-URL ausgelöst werden.");
    }

    /**
     * Testet die Erstellung eines WordImagePair-Objekts mit leerem Wort.
     * Erwartet eine IllegalArgumentException.
     */
    @Test
    void testEmptyWord() {
        String emptyWord = "   ";
        String imageUrl = "https://example.com/baum.jpg";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new WordImagePair(emptyWord, imageUrl);
        });

        String expectedMessage = "Wort darf nicht null oder leer sein.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Es sollte eine IllegalArgumentException wegen leerem Wort ausgelöst werden.");
    }

    /**
     * Testet die equals-Methode der WordImagePair-Klasse.
     */
    @Test
    void testEqualsAndHashCode() {
        WordImagePair pair1 = new WordImagePair("Apfel", "https://example.com/apfel.jpg");
        WordImagePair pair2 = new WordImagePair("Apfel", "https://example.com/apfel.jpg");
        WordImagePair pair3 = new WordImagePair("Baum", "https://example.com/baum.jpg");

        assertEquals(pair1, pair2, "Paare mit gleichen Wort und Bild-URL sollten gleich sein.");
        assertNotEquals(pair1, pair3, "Paare mit unterschiedlichen Wörtern oder Bild-URLs sollten nicht gleich sein.");
        assertEquals(pair1.hashCode(), pair2.hashCode(), "Hashcodes für gleiche Objekte sollten gleich sein.");
    }
}
