package sutaj.worttrainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class WordImagePair {
    private String word;
    private String imageUrl;

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

    private boolean isValidURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
    public String getWord() {
        return word;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Optional: Override equals and hashCode if needed

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordImagePair that = (WordImagePair) o;

        if (!word.equals(that.word)) return false;
        return imageUrl.equals(that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, imageUrl);
    }
}