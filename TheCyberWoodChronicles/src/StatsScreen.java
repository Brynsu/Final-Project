import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StatsScreen extends JPanel {
    private GameWindow window;
    private Image bg;
    private Pet selectedPet;

    public StatsScreen(GameWindow window) {
        this.window = window;
        
        URL bgUrl = getClass().getResource("/Images/MainMenuBG.gif");
        if (bgUrl != null) {
            bg = new ImageIcon(bgUrl).getImage();
        } else {
            bg = null;
        }
        
        setLayout(null);
        displayPetSelection();
    }

    private void displayPetSelection() {
        JLabel titleLabel = new JLabel("SELECT PET TO VIEW STATS", SwingConstants.CENTER);
        titleLabel.setBounds(150, 50, 550, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.YELLOW);
        add(titleLabel);

        addPetSelectionButton("Fire Dragon", "/Images/firedragon.png", 150, 120);
        addPetSelectionButton("Water Turtle", "/Images/waterturtle.png", 350, 120);
        addPetSelectionButton("Earth Golem", "/Images/earthgolem.png", 550, 120);

        JButton back = new JButton("Back to Menu");
        back.setBounds(325, 520, 150, 40);
        back.setFont(new Font("Arial", Font.BOLD, 16));
        back.setFocusPainted(false);
        back.setBackground(new Color(70, 70, 70, 200));
        back.setForeground(Color.WHITE);
        back.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        back.addActionListener(e -> window.switchScreen(new MenuScreen(window)));
        add(back);
    }

    private void addPetSelectionButton(String petName, String imagePath, int x, int y) {
        URL imgUrl = getClass().getResource(imagePath);
        ImageIcon icon = null;

        if (imgUrl != null) {
            Image img = new ImageIcon(imgUrl).getImage();
            img = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }

        JButton button = new JButton(petName, icon);
        button.setBounds(x, y, 150, 150);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 70, 70, 200));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));

        button.addActionListener(e -> {
            switch (petName) {
                case "Fire Dragon" -> selectedPet = new FireDragon();
                case "Water Turtle" -> selectedPet = new WaterTurtle();
                case "Earth Golem" -> selectedPet = new EarthGolem();
            }
            showPetStats();
        });

        add(button);
    }

    private void showPetStats() {
        removeAll();
        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(null);
        statsPanel.setBounds(150, 100, 550, 350);
        statsPanel.setBackground(new Color(0, 0, 0, 180));
        statsPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(statsPanel);

        Image petImage = selectedPet.getImage();
        if (petImage != null) {
            Image scaledImage = petImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setBounds(50, 50, 120, 120);
            statsPanel.add(imageLabel);
        }

        JLabel titleLabel = new JLabel(selectedPet.getName() + " STATS", SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 550, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        statsPanel.add(titleLabel);

        JLabel nameLabel = new JLabel("Name: " + selectedPet.getName());
        nameLabel.setBounds(200, 50, 300, 25);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        statsPanel.add(nameLabel);

        JLabel healthLabel = new JLabel("Health: " + selectedPet.getHealth() + " / " + selectedPet.getMaxHealth());
        healthLabel.setBounds(200, 80, 300, 25);
        healthLabel.setFont(new Font("Arial", Font.BOLD, 16));
        healthLabel.setForeground(Color.GREEN);
        statsPanel.add(healthLabel);

        JLabel manaLabel = new JLabel("Mana: " + selectedPet.getMana() + " / " + selectedPet.getMaxMana());
        manaLabel.setBounds(200, 110, 300, 25);
        manaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        manaLabel.setForeground(Color.BLUE);
        statsPanel.add(manaLabel);

        JLabel attackLabel = new JLabel("Attack Power: " + selectedPet.getAttackPower());
        attackLabel.setBounds(200, 140, 300, 25);
        attackLabel.setFont(new Font("Arial", Font.BOLD, 16));
        attackLabel.setForeground(Color.RED);
        statsPanel.add(attackLabel);

        JLabel defenseLabel = new JLabel("Defense: " + selectedPet.getDefense());
        defenseLabel.setBounds(200, 170, 300, 25);
        defenseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        defenseLabel.setForeground(Color.ORANGE);
        statsPanel.add(defenseLabel);

        JLabel happinessLabel = new JLabel("Happiness: " + selectedPet.getHappiness() + " / 100");
        happinessLabel.setBounds(200, 200, 300, 25);
        happinessLabel.setFont(new Font("Arial", Font.BOLD, 16));
        happinessLabel.setForeground(Color.PINK);
        statsPanel.add(happinessLabel);

        JLabel abilitiesTitle = new JLabel("ABILITIES:", SwingConstants.CENTER);
        abilitiesTitle.setBounds(0, 230, 550, 25);
        abilitiesTitle.setFont(new Font("Arial", Font.BOLD, 18));
        abilitiesTitle.setForeground(Color.YELLOW);
        statsPanel.add(abilitiesTitle);

        JLabel basicLabel = new JLabel("• " + selectedPet.getBasicAttackName() + ": " + selectedPet.getBasicAttackDescription());
        basicLabel.setBounds(50, 260, 450, 20);
        basicLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        basicLabel.setForeground(Color.WHITE);
        statsPanel.add(basicLabel);

        JLabel skillLabel = new JLabel("• " + selectedPet.getSkillName() + ": " + selectedPet.getSkillDescription());
        skillLabel.setBounds(50, 280, 450, 20);
        skillLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        skillLabel.setForeground(Color.WHITE);
        statsPanel.add(skillLabel);

        JLabel ultimateLabel = new JLabel("• " + selectedPet.getUltimateName() + ": " + selectedPet.getUltimateDescription());
        ultimateLabel.setBounds(50, 300, 450, 20);
        ultimateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        ultimateLabel.setForeground(Color.WHITE);
        statsPanel.add(ultimateLabel);

        JButton backToSelection = new JButton("View Other Pets");
        backToSelection.setBounds(250, 470, 150, 40);
        backToSelection.setFont(new Font("Arial", Font.BOLD, 14));
        backToSelection.setFocusPainted(false);
        backToSelection.setBackground(new Color(70, 70, 70, 200));
        backToSelection.setForeground(Color.WHITE);
        backToSelection.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backToSelection.addActionListener(e -> {
            removeAll();
            displayPetSelection();
            revalidate();
            repaint();
        });
        add(backToSelection);

        JButton backToMenu = new JButton("Back to Menu");
        backToMenu.setBounds(425, 470, 150, 40);
        backToMenu.setFont(new Font("Arial", Font.BOLD, 14));
        backToMenu.setFocusPainted(false);
        backToMenu.setBackground(new Color(70, 70, 70, 200));
        backToMenu.setForeground(Color.WHITE);
        backToMenu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backToMenu.addActionListener(e -> window.switchScreen(new MenuScreen(window)));
        add(backToMenu);

        revalidate();
        repaint();
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
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        String title = "PET STATISTICS";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(title)) / 2;
        int y = 30;

        g2d.setColor(Color.BLACK);
        g2d.drawString(title, x - 2, y - 2);
        g2d.drawString(title, x + 2, y - 2);
        g2d.drawString(title, x - 2, y + 2);
        g2d.drawString(title, x + 2, y + 2);

        g2d.setColor(Color.WHITE);
        g2d.drawString(title, x, y);
    }
}
