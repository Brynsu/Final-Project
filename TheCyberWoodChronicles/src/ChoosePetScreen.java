import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ChoosePetScreen extends JPanel {
    private GameWindow window;
    private Image bg;

    public ChoosePetScreen(GameWindow window) {
        this.window = window;

        URL bgUrl = getClass().getResource("/Images/MainMenuBG.gif");
        if (bgUrl != null) {
            bg = new ImageIcon(bgUrl).getImage();
        } else {
            bg = null;
        }

        setLayout(null);

        addPetButton("Fire Dragon", 100, 200, "/Images/firedragon.png");
        addPetButton("Water Turtle", 300, 200, "/Images/waterturtle.png");
        addPetButton("Earth Golem", 500, 200, "/Images/earthgolem.png");

        JButton backButton = new JButton("Back to Menu");
        backButton.setBounds(10, 10, 150, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> window.switchScreen(new MenuScreen(window)));
        add(backButton);
    }

    private void addPetButton(String petName, int x, int y, String imagePath) {
        ImageIcon icon = ResourceManager.loadImageIcon(imagePath, 200, 200);

        int buttonWidth = icon != null ? icon.getIconWidth() : 150;
        int buttonHeight = icon != null ? icon.getIconHeight() + 20 : 50;

        JButton button = new JButton(petName, icon);
        button.setBounds(x, y, buttonWidth, buttonHeight);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            Pet selectedPet = switch (petName) {
                case "Fire Dragon" -> new FireDragon();
                case "Water Turtle" -> new WaterTurtle();
                case "Earth Golem" -> new EarthGolem();
                default -> null;
            };

            if (selectedPet != null) {
                window.getGameState().setCurrentPet(selectedPet);

                String message = String.format(
                        "You chose %s!\n\nAbilities:\n" +
                                "• %s: %s\n" +
                                "• %s: %s\n" +
                                "• %s: %s\n\nBase Stats:\nHP: %d | ATK: %d | DEF: %d",
                        selectedPet.getName(),
                        selectedPet.getBasicAttackName(), selectedPet.getBasicAttackDescription(),
                        selectedPet.getSkillName(), selectedPet.getSkillDescription(),
                        selectedPet.getUltimateName(), selectedPet.getUltimateDescription(),
                        selectedPet.getMaxHealth(), selectedPet.getAttackPower(), selectedPet.getDefense()
                );

                JOptionPane.showMessageDialog(this, message);

                Timer battleTimer = new Timer(2000, evt -> {
                    window.switchScreen(new BattleScreen(window));
                });
                battleTimer.setRepeats(false);
                battleTimer.start();
            }
        });

        add(button);
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
        String title = "CHOOSE YOUR PET";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(title)) / 2;
        int y = 150;

        g2d.setColor(Color.BLACK);
        g2d.drawString(title, x - 2, y - 2);
        g2d.drawString(title, x + 2, y - 2);
        g2d.drawString(title, x - 2, y + 2);
        g2d.drawString(title, x + 2, y + 2);

        g2d.setColor(Color.WHITE);
        g2d.drawString(title, x, y);
    }
}
