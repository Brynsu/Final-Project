import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;

public final class ResourceManager {
    private ResourceManager() {}

    public static Image loadImage(String path, int width, int height) {
        URL url = ResourceManager.class.getResource(path);
        if (url != null) {
            try {
                ImageIcon icon = new ImageIcon(url);
                return icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            } catch (Exception e) {
                System.err.println("Error loading image: " + path + " - " + e.getMessage());
            }
        }
        return createPlaceholderImage(width, height, "Missing: " + path);
    }

    public static ImageIcon loadImageIcon(String path, int width, int height) {
        return new ImageIcon(loadImage(path, width, height));
    }

    private static Image createPlaceholderImage(int width, int height, String text) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            g2d.drawString(lines[i], 10, 20 + (i * 15));
        }

        g2d.dispose();
        return img;
    }
}