package sutaj.worttrainer;

import javax.swing.*;
import java.awt.*;

public class ImageUtils {
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

