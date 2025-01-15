package sutaj.worttrainer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for handling image operations such as scaling while maintaining
 * the original aspect ratio. This class also employs caching to store previously processed images.
 *
 * Author: Drinor Sutaj
 * Version: 14.10.2024
 */
public class ImageUtils {
    // A cache to store scaled ImageIcons, improving performance for frequently accessed images.
    private static final Map<String, ImageIcon> imageCache = new HashMap<>();

    /**
     * Scales an ImageIcon to fit within the specified maximum width and height,
     * while preserving the original aspect ratio.
     * <p>
     * If the original image dimensions are smaller than or equal to the specified
     * maximum dimensions, the original image is returned without any scaling.
     *
     * @param icon      The original ImageIcon to be scaled. Must not be null.
     * @param maxWidth  The maximum allowed width for the scaled image in pixels.
     * @param maxHeight The maximum allowed height for the scaled image in pixels.
     * @return A new ImageIcon object representing the scaled image, or {@code null}
     *         if the provided {@code icon} is {@code null}.
     */
    public static ImageIcon getScaledImageIcon(ImageIcon icon, int maxWidth, int maxHeight) {
        if (icon == null) {
            // Return null if the input ImageIcon is null
            return null;
        }

        // Retrieve the original dimensions of the image
        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();

        // Calculate the scaling factor to maintain the aspect ratio
        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double scalingFactor = Math.min(widthRatio, heightRatio);

        // If the scaling factor is greater than or equal to 1, return the original image
        if (scalingFactor >= 1.0) {
            return icon;
        }

        // Calculate the new dimensions based on the scaling factor
        int newWidth = (int) (originalWidth * scalingFactor);
        int newHeight = (int) (originalHeight * scalingFactor);

        // Create a scaled version of the image using smooth scaling for better quality
        Image scaledImage = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Return the newly created ImageIcon with the scaled image
        return new ImageIcon(scaledImage);
    }
}
