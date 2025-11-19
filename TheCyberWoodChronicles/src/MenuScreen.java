import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MenuScreen extends JPanel {
    private GameWindow window;
    private Image bg;

    public MenuScreen(GameWindow window) {
        this.window = window;

        URL bgUrl = getClass().getResource("/Images/MainMenuBG.gif");
        if (bgUrl != null) {
            bg = new ImageIcon(bgUrl).getImage();
        } else {
            bg = null;
        }

        setLayout(null);

        JButton startButton = new JButton("START GAME");
        startButton.setBounds(300, 200, 200, 60);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> window.switchScreen(new ChoosePetScreen(window)));
        add(startButton);

        JButton statsButton = new JButton("PET STATS");
        statsButton.setBounds(300, 280, 200, 60);
        statsButton.setFont(new Font("Arial", Font.BOLD, 20));
        statsButton.setFocusPainted(false);
        statsButton.addActionListener(e -> window.switchScreen(new StatsScreen(window)));
        add(statsButton);

        JButton exitButton = new JButton("EXIT");
        exitButton.setBounds(300, 360, 200, 60);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        String title = "CYBERWOOD CHRONICLES";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(title)) / 2;
        int y = 100;

        g2d.setColor(Color.BLACK);
        g2d.drawString(title, x - 2, y - 2);
        g2d.drawString(title, x + 2, y - 2);
        g2d.drawString(title, x - 2, y + 2);
        g2d.drawString(title, x + 2, y + 2);

        g2d.setColor(Color.WHITE);
        g2d.drawString(title, x, y);
    }
}