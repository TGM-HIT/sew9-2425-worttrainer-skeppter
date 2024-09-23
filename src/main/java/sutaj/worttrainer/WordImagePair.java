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
}