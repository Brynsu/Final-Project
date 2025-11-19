import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;

public class Enemy {
    private String name;
    private int health;
    private int maxHealth;
    private int attackPower;
    private Image image;
    private boolean isBoss = false;
    private int specialAttackCooldown = 0;
    private Random rand = new Random();

    public Enemy() {
        Random rand = new Random();
        int type = rand.nextInt(5);

        switch (type) {
            case 0 -> { name = "Goblin"; maxHealth = 100; attackPower = 30; loadImage("/Images/enemies/goblin.png"); }
            case 1 -> { name = "Dragon"; maxHealth = 300; attackPower = 80; loadImage("/Images/enemies/dragon.png"); }
            case 2 -> { name = "Wolf"; maxHealth = 150; attackPower = 40; loadImage("/Images/enemies/wolf.png"); }
            case 3 -> { name = "Skeleton"; maxHealth = 120; attackPower = 35; loadImage("/Images/enemies/skeleton.png"); }
            case 4 -> { name = "Troll"; maxHealth = 250; attackPower = 60; loadImage("/Images/enemies/troll.png"); }
        }

        health = maxHealth;
    }

    public void setStats(String name, int maxHealth, int attackPower, String imagePath) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackPower = attackPower;
        loadImage(imagePath);
    }

    public void setBoss(boolean isBoss) {
        this.isBoss = isBoss;
    }

    public boolean isBoss() {
        return isBoss;
    }

    private void loadImage(String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            image = new ImageIcon(url).getImage();
        } else {
            image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttackPower() { return attackPower; }
    public Image getImage() { return image; }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    public int performBossAttack() {
        specialAttackCooldown--;
        
        if (specialAttackCooldown <= 0) {
            specialAttackCooldown = 3;
            int specialDamage = (int)(attackPower * 1.8);
            return specialDamage;
        } else {
            int baseDamage = rand.nextInt(attackPower / 2) + (attackPower / 2);
            return baseDamage;
        }
    }

    public int performNormalAttack() {
        if (isBoss) {
            return rand.nextInt(attackPower / 2) + (attackPower / 2);
        } else {
            return rand.nextInt(attackPower) + 20;
        }
    }

    public String getBossAttackMessage(int damage) {
        if (specialAttackCooldown == 3) {
            return getSpecialAttackMessage(damage);
        } else {
            return getNormalAttackMessage(damage);
        }
    }

    private String getSpecialAttackMessage(int damage) {
        return switch (getName()) {
            case "Lab Guardian" -> "LAB GUARDIAN unleashes OBLITERATION BEAM for " + damage + " CRITICAL damage!";
            default -> getName() + " uses ANNIHILATION ATTACK for " + damage + " CRITICAL damage!";
        };
    }

    private String getNormalAttackMessage(int damage) {
        return getName() + " strikes for " + damage + " damage!";
    }

    public int getSpecialAttackCooldown() {
        return specialAttackCooldown;
    }

    @Override
    public String toString() {
        return name + " | Health: " + health + "/" + maxHealth + " | Attack Power: " + attackPower;
    }
}