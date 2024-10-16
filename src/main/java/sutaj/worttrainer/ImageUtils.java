package sutaj.worttrainer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Drinor Sutaj
 * @version 14.10.2024
 */
public class ImageUtils {
    private static final Map<String, ImageIcon> imageCache = new HashMap<>();

    /**
     * Skaliert ein ImageIcon auf die maximalen Breiten- und Höhenwerte,
     * behält dabei das Seitenverhältnis bei. Verwendet Caching, um bereits
     * geladene und skalierte Bilder wiederzuverwenden.
     *
     * @param urlString Der URL-String des Bildes.
     * @param maxWidth  Maximale Breite.
     * @param maxHeight Maximale Höhe.
     * @return Ein neues, skaliertes ImageIcon oder null bei Fehlern.
     */
    public static ImageIcon getScaledImageIcon(String urlString, int maxWidth, int maxHeight) {
        if (urlString == null || urlString.trim().isEmpty()) {
            return null;
        }

        // Überprüfe das Cache
        if (imageCache.containsKey(urlString)) {
            return imageCache.get(urlString);
        }

        try {
            URL url = new URL(urlString);
            ImageIcon originalIcon = new ImageIcon(url);
            ImageIcon scaledIcon = getScaledImageIcon(originalIcon, maxWidth, maxHeight);
            imageCache.put(urlString, scaledIcon);
            return scaledIcon;
        } catch (Exception e) {
            // Fehler beim Laden des Bildes
            return null;
        }
    }

    /**
     * Skaliert ein ImageIcon auf die maximalen Breiten- und Höhenwerte,
     * behält dabei das Seitenverhältnis bei.
     *
     * @param icon      Das ursprüngliche ImageIcon.
     * @param maxWidth  Maximale Breite.
     * @param maxHeight Maximale Höhe.
     * @return Ein neues, skaliertes ImageIcon.
     */
    public static ImageIcon getScaledImageIcon(ImageIcon icon, int maxWidth, int maxHeight) {
        if (icon == null) {
            return null;
        }

        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();

        // Berechne Skalierungsfaktor, um das Seitenverhältnis beizubehalten
        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double scalingFactor = Math.min(widthRatio, heightRatio);

        // Falls das Bild kleiner als die maximalen Abmessungen ist, keine Skalierung
        if (scalingFactor >= 1.0) {
            return icon;
        }

        int newWidth = (int) (originalWidth * scalingFactor);
        int newHeight = (int) (originalHeight * scalingFactor);

        Image scaledImage = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
