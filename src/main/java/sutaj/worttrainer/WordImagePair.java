package sutaj.worttrainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Repräsentiert ein Paar aus einem Wort und einer zugehörigen Bild-URL.
 * Stellt Validierung für das Wort und die URL sicher.
 *
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
public class WordImagePair {
    private String word;      // Das Wort des Paares
    private String imageUrl;  // Die zugehörige Bild-URL

    /**
     * Erstellt ein neues {@code WordImagePair} mit einem Wort und einer Bild-URL.
     *
     * @param word     Das zu speichernde Wort. Darf nicht null oder leer sein.
     * @param imageUrl Die URL des zugehörigen Bildes. Muss eine gültige URL sein.
     * @throws IllegalArgumentException Wenn das Wort null/leer ist oder die URL ungültig ist.
     */
    public WordImagePair(String word, String imageUrl) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Wort darf nicht null oder leer sein.");
        }
        if (!isValidURL(imageUrl)) {
            throw new IllegalArgumentException("Ungültige Bild-URL.");
        }
        this.word = word.trim();
        this.imageUrl = imageUrl.trim();
    }

    /**
     * Validiert die gegebene URL.
     *
     * @param url Die zu prüfende URL.
     * @return {@code true}, wenn die URL gültig ist, sonst {@code false}.
     */
    private boolean isValidURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * Gibt das Wort dieses Paares zurück.
     *
     * @return Das Wort als {@code String}.
     */
    public String getWord() {
        return word;
    }

    /**
     * Gibt die Bild-URL dieses Paares zurück.
     *
     * @return Die Bild-URL als {@code String}.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Überprüft, ob dieses Objekt gleich einem anderen ist.
     * Zwei Objekte sind gleich, wenn sowohl das Wort als auch die Bild-URL übereinstimmen.
     *
     * @param o Das zu vergleichende Objekt.
     * @return {@code true}, wenn die Objekte gleich sind, sonst {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordImagePair that = (WordImagePair) o;

        if (!word.equals(that.word)) return false;
        return imageUrl.equals(that.imageUrl);
    }

    /**
     * Gibt den Hashcode dieses Objekts zurück.
     * Der Hashcode basiert auf dem Wort und der Bild-URL.
     *
     * @return Der Hashcode als {@code int}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(word, imageUrl);
    }
}
