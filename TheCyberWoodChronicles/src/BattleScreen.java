import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.net.URL;

public class BattleScreen extends JPanel {
    private GameWindow window;
    private Pet pet;
    private Enemy enemy;
    private Level petLevel;
    private Image bg;

    private JProgressBar petHealthBar;
    private JProgressBar petManaBar;
    private JProgressBar enemyHealthBar;
    private JLabel petImageLabel;
    private JLabel enemyImageLabel;
    private JLabel vsLabel;
    private JLabel logLabel;
    private JButton basicAttackBtn;
    private JButton skillBtn;
    private JButton ultimateBtn;
    private JButton backBtn;
    private JLabel bossWarningLabel;
    private boolean isEnemyTurn = false;

    public BattleScreen(GameWindow window) {
        this.window = window;
        this.pet = GameWindow.pet;
        this.petLevel = GameWindow.petLevel;
        this.enemy = StoryManager.getChapterEnemy();

        URL bgUrl = getClass().getResource("/Images/Arena.png");
        if (bgUrl != null) {
            bg = new ImageIcon(bgUrl).getImage();
        } else {
            bg = null;
        }

        setLayout(null);
        setupUI();
    }

    private void setupUI() {
        removeAll();

        Image petImage = pet.getImage();
        Image enemyImage = enemy.getImage();

        int imageSize = 150;

        Image scaledPetImage = petImage.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        Image scaledEnemyImage = enemyImage.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);

        petImageLabel = new JLabel(new ImageIcon(scaledPetImage));
        petImageLabel.setBounds(100, 150, imageSize, imageSize);
        add(petImageLabel);

        petHealthBar = new JProgressBar(0, pet.getMaxHealth());
        petHealthBar.setValue(pet.getHealth());
        petHealthBar.setStringPainted(true);
        petHealthBar.setForeground(Color.GREEN);
        petHealthBar.setBackground(new Color(0, 0, 0, 150));
        petHealthBar.setBounds(100, 120, 150, 20);
        add(petHealthBar);

        petManaBar = new JProgressBar(0, pet.getMaxMana());
        petManaBar.setValue(pet.getMana());
        petManaBar.setStringPainted(true);
        petManaBar.setForeground(Color.BLUE);
        petManaBar.setBackground(new Color(0, 0, 0, 150));
        petManaBar.setBounds(100, 140, 150, 15);
        petManaBar.setString(pet.getMana() + "/" + pet.getMaxMana() + " Mana");
        add(petManaBar);

        enemyImageLabel = new JLabel(new ImageIcon(scaledEnemyImage));
        enemyImageLabel.setBounds(550, 150, imageSize, imageSize);
        add(enemyImageLabel);

        enemyHealthBar = new JProgressBar(0, enemy.getMaxHealth());
        enemyHealthBar.setValue(enemy.getHealth());
        enemyHealthBar.setStringPainted(true);
        enemyHealthBar.setForeground(Color.RED);
        enemyHealthBar.setBackground(new Color(0, 0, 0, 150));
        enemyHealthBar.setBounds(550, 120, 150, 25);
        add(enemyHealthBar);

        JLabel petNameLabel = new JLabel("Your Pet", SwingConstants.CENTER);
        petNameLabel.setBounds(100, 100, 150, 20);
        petNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        petNameLabel.setForeground(Color.WHITE);
        add(petNameLabel);

        JLabel enemyNameLabel = new JLabel(enemy.getName(), SwingConstants.CENTER);
        enemyNameLabel.setBounds(550, 100, 150, 20);
        enemyNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        enemyNameLabel.setForeground(Color.WHITE);
        add(enemyNameLabel);

        vsLabel = new JLabel("V.S", SwingConstants.CENTER);
        vsLabel.setBounds(375, 180, 100, 60);
        vsLabel.setFont(new Font("Arial", Font.BOLD, 36));
        vsLabel.setForeground(Color.YELLOW);
        add(vsLabel);

        if (enemy.isBoss()) {
            bossWarningLabel = new JLabel("IMPOSSIBLE BOSS BATTLE!", SwingConstants.CENTER);
            bossWarningLabel.setBounds(280, 50, 240, 30);
            bossWarningLabel.setFont(new Font("Arial", Font.BOLD, 20));
            bossWarningLabel.setForeground(Color.RED);
            add(bossWarningLabel);
        }

        logLabel = new JLabel("Battle started! " + enemy.getName() + " appears!", SwingConstants.CENTER);
        logLabel.setBounds(50, 350, 700, 40);
        logLabel.setFont(new Font("Arial", Font.BOLD, 16));
        logLabel.setForeground(Color.WHITE);
        logLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(logLabel);

        setupAbilityButtons();

        backBtn = new JButton("Back to Menu");
        backBtn.setBounds(650, 520, 150, 40);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(70, 70, 70, 200));
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> window.switchScreen(new MenuScreen(window)));
        add(backBtn);

        revalidate();
        repaint();
    }

    private void setupAbilityButtons() {
        basicAttackBtn = new JButton(pet.getBasicAttackName());
        skillBtn = new JButton(pet.getSkillName());
        ultimateBtn = new JButton(pet.getUltimateName());

        int buttonWidth = 180;
        int buttonHeight = 50;
        int spacing = 20;
        int startX = 100;

        basicAttackBtn.setBounds(startX, 450, buttonWidth, buttonHeight);
        basicAttackBtn.setFont(new Font("Arial", Font.BOLD, 14));
        basicAttackBtn.setBackground(new Color(70, 130, 180, 200));
        basicAttackBtn.setForeground(Color.WHITE);
        basicAttackBtn.setFocusPainted(false);
        basicAttackBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        basicAttackBtn.addActionListener(e -> useAbility("basic"));
        add(basicAttackBtn);

        skillBtn.setBounds(startX + buttonWidth + spacing, 450, buttonWidth, buttonHeight);
        skillBtn.setFont(new Font("Arial", Font.BOLD, 14));
        skillBtn.setBackground(new Color(220, 120, 0, 200));
        skillBtn.setForeground(Color.WHITE);
        skillBtn.setFocusPainted(false);
        skillBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        skillBtn.addActionListener(e -> useAbility("skill"));
        add(skillBtn);

        ultimateBtn.setBounds(startX + (buttonWidth + spacing) * 2, 450, buttonWidth, buttonHeight);
        ultimateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        ultimateBtn.setBackground(new Color(178, 34, 34, 200));
        ultimateBtn.setForeground(Color.WHITE);
        ultimateBtn.setFocusPainted(false);
        ultimateBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        ultimateBtn.addActionListener(e -> useAbility("ultimate"));
        add(ultimateBtn);

        basicAttackBtn.setToolTipText(pet.getBasicAttackDescription());
        skillBtn.setToolTipText(pet.getSkillDescription());
        ultimateBtn.setToolTipText(pet.getUltimateDescription());

        JLabel basicLabel = new JLabel("Basic Attack", SwingConstants.CENTER);
        basicLabel.setBounds(startX, 430, buttonWidth, 20);
        basicLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        basicLabel.setForeground(Color.WHITE);
        add(basicLabel);

        JLabel skillLabel = new JLabel("Skill", SwingConstants.CENTER);
        skillLabel.setBounds(startX + buttonWidth + spacing, 430, buttonWidth, 20);
        skillLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        skillLabel.setForeground(Color.WHITE);
        add(skillLabel);

        JLabel ultimateLabel = new JLabel("Ultimate", SwingConstants.CENTER);
        ultimateLabel.setBounds(startX + (buttonWidth + spacing) * 2, 430, buttonWidth, 20);
        ultimateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        ultimateLabel.setForeground(Color.WHITE);
        add(ultimateLabel);
    }

    private void useAbility(String abilityType) {
        if (isEnemyTurn) return;
        if (pet.getHealth() <= 0) {
            logLabel.setText("Your pet is knocked out! Battle lost!");
            disableAbilityButtons();
            return;
        }

        if (enemy.getHealth() <= 0) {
            logLabel.setText("Enemy is already defeated!");
            return;
        }

        String result = switch (abilityType) {
            case "basic" -> pet.useBasicAttack(enemy);
            case "skill" -> {
                if (pet.getMana() < getSkillManaCost()) {
                    yield "Not enough mana! Need " + getSkillManaCost() + " mana for " + pet.getSkillName() + ".";
                }
                yield pet.useSkill(enemy);
            }
            case "ultimate" -> {
                if (pet.getMana() < getUltimateManaCost()) {
                    yield "Not enough mana! Need " + getUltimateManaCost() + " mana for " + pet.getUltimateName() + ".";
                }
                yield pet.useUltimate(enemy);
            }
            default -> "Invalid ability!";
        };

        logLabel.setText("<html><div style='text-align: center;'>" + result + "</div></html>");
        updateBars();

        if (result.contains("Not enough mana")) {
            return;
        }

        if (enemy.getHealth() <= 0) {
            handleVictory();
            return;
        }

        disableAbilityButtons();
        isEnemyTurn = true;

        Timer enemyTurnTimer = new Timer(2000, e -> {
            enemyAttack();
            updateBars();

            if (pet.getHealth() <= 0) {
                handleDefeat();
            } else {
                enableAbilityButtons();
                isEnemyTurn = false;
            }
        });
        enemyTurnTimer.setRepeats(false);
        enemyTurnTimer.start();
    }

    private int getSkillManaCost() {
        if (pet instanceof FireDragon) return 45;
        if (pet instanceof WaterTurtle) return 35;
        if (pet instanceof EarthGolem) return 50;
        return 40;
    }

    private int getUltimateManaCost() {
        if (pet instanceof FireDragon) return 85;
        if (pet instanceof WaterTurtle) return 75;
        if (pet instanceof EarthGolem) return 90;
        return 80;
    }

    private void enemyAttack() {
        if (enemy.getHealth() <= 0) return;

        String enemyAttackText;
        int enemyDamage;

        if (enemy.isBoss()) {
            enemyDamage = enemy.performBossAttack();
            enemyAttackText = enemy.getBossAttackMessage(enemyDamage);
        } else {
            enemyDamage = enemy.performNormalAttack();
            enemyAttackText = enemy.getName() + " attacks for " + enemyDamage + " damage!";
        }

        pet.takeDamage(enemyDamage);

        logLabel.setText("<html><div style='text-align: center;'>" +
                logLabel.getText().replaceAll("<[^>]*>", "") +
                "<br>" + enemyAttackText + "</div></html>");
    }

    private void handleVictory() {
        String victoryMessage;
        if (enemy.isBoss()) {
            victoryMessage = "<html><div style='text-align: center; color: #90EE90;'>" +
                    "MIRACULOUS VICTORY! You defeated " + enemy.getName() + "!<br>" +
                    "+50 Happiness & +50 EXP</div></html>";
            pet.increaseHappiness(50);
            petLevel.addExperience(50);
        } else {
            victoryMessage = "<html><div style='text-align: center; color: #90EE90;'>" +
                    "VICTORY! You defeated " + enemy.getName() + "!<br>" +
                    "+20 Happiness & +20 EXP</div></html>";
            pet.increaseHappiness(20);
            petLevel.addExperience(20);
        }

        logLabel.setText(victoryMessage);
        pet.evolve();

        disableAbilityButtons();

        Timer victoryTimer = new Timer(3000, e -> {
            if (StoryManager.getCurrentChapter() < 3) {
                StoryManager.nextChapter();
                window.switchScreen(new StoryScreen(window));
            } else {
                JOptionPane.showMessageDialog(this,
                        "LEGENDARY! You completed CyberWood Chronicles on IMPOSSIBLE difficulty!\n\n" +
                                "You defeated the impossible Lab Guardian!\n" +
                                "Your " + pet.getName() + " has become a living legend!",
                        "ULTIMATE VICTORY!",
                        JOptionPane.INFORMATION_MESSAGE);
                window.switchScreen(new MenuScreen(window));
            }
        });
        victoryTimer.setRepeats(false);
        victoryTimer.start();
    }

    private void handleDefeat() {
        logLabel.setText("<html><div style='text-align: center; color: #FF6B6B;'>" +
                "CRUSHING DEFEAT! Your pet was obliterated!<br>" +
                "The enemy was too powerful...</div></html>");

        disableAbilityButtons();

        Timer defeatTimer = new Timer(3000, e -> {
            window.switchScreen(new MenuScreen(window));
        });
        defeatTimer.setRepeats(false);
        defeatTimer.start();
    }

    private void disableAbilityButtons() {
        basicAttackBtn.setEnabled(false);
        skillBtn.setEnabled(false);
        ultimateBtn.setEnabled(false);
    }

    private void enableAbilityButtons() {
        basicAttackBtn.setEnabled(true);
        skillBtn.setEnabled(true);
        ultimateBtn.setEnabled(true);
    }

    private void updateBars() {
        petHealthBar.setValue(pet.getHealth());
        petHealthBar.setMaximum(pet.getMaxHealth());
        petHealthBar.setString(pet.getHealth() + "/" + pet.getMaxHealth());

        petManaBar.setValue(pet.getMana());
        petManaBar.setMaximum(pet.getMaxMana());
        petManaBar.setString(pet.getMana() + "/" + pet.getMaxMana() + " Mana");

        enemyHealthBar.setValue(enemy.getHealth());
        enemyHealthBar.setString(enemy.getHealth() + "/" + enemy.getMaxHealth());

        updateHealthBarColor(petHealthBar, pet.getHealth(), pet.getMaxHealth());
        updateHealthBarColor(enemyHealthBar, enemy.getHealth(), enemy.getMaxHealth());

        repaint();
    }

    private void updateHealthBarColor(JProgressBar bar, int current, int max) {
        double percentage = (double) current / max;
        if (percentage > 0.6) {
            bar.setForeground(Color.GREEN);
        } else if (percentage > 0.3) {
            bar.setForeground(Color.YELLOW);
        } else {
            bar.setForeground(Color.RED);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(30, 30, 50));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRoundRect(25, 80, 750, 250, 20, 20);

        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(25, 80, 750, 250, 20, 20);

        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRoundRect(25, 340, 750, 60, 15, 15);

        g2d.setColor(new Color(255, 255, 255, 80));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(25, 340, 750, 60, 15, 15);
    }
}
