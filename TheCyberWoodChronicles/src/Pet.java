import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;

public abstract class Pet {
    protected static final Random rand = new Random();

    protected String name;
    protected String imagePath;
    protected int health;
    protected int maxHealth;
    protected int attackPower;
    protected int defense;
    protected int happiness;
    protected int evolutionStage;
    protected int mana;
    protected int maxMana;

    public Pet(String name, String imagePath, int maxHealth, int attackPower, int defense) {
        this.name = name;
        this.imagePath = imagePath;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
        this.happiness = 50;
        this.evolutionStage = 1;
        this.maxMana = 100;
        this.mana = 50;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public int getHappiness() { return happiness; }
    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }

    public Image getImage() {
        return ResourceManager.loadImage(imagePath, 150, 150);
    }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health -= actualDamage;
        if (health < 0) health = 0;
    }

    public void increaseHealth(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public void increaseHappiness(int amount) {
        happiness = Math.min(GameConstants.MAX_HAPPINESS, happiness + amount);
    }

    public void increaseMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }

    public boolean useMana(int amount) {
        if (mana >= amount) {
            mana -= amount;
            return true;
        }
        return false;
    }

    public void evolve() {
        if (evolutionStage < 3) {
            evolutionStage++;
            maxHealth += 20;
            attackPower += 5;
            defense += 3;
            maxMana += 20;
            health = maxHealth;
            mana = Math.min(maxMana, mana + 20);
        }
    }

    public abstract String useBasicAttack(Enemy enemy);
    public abstract String useSkill(Enemy enemy);
    public abstract String useUltimate(Enemy enemy);

    public abstract String getBasicAttackName();
    public abstract String getSkillName();
    public abstract String getUltimateName();

    public abstract String getBasicAttackDescription();
    public abstract String getSkillDescription();
    public abstract String getUltimateDescription();

    public abstract int getSkillManaCost();
    public abstract int getUltimateManaCost();
}

class FireDragon extends Pet {
    private boolean rageMode;

    public FireDragon() {
        super("Fire Dragon", "/Images/firedragon.png", 100, 20, 10);
        this.rageMode = false;
    }

    @Override
    public String useBasicAttack(Enemy enemy) {
        int damage = rand.nextInt(attackPower) + 8;
        enemy.takeDamage(damage);
        increaseMana(GameConstants.MANA_REGEN_BASIC_ATTACK);
        return "Claw Swipe deals " + damage + " damage! +" + GameConstants.MANA_REGEN_BASIC_ATTACK + " Mana";
    }

    @Override
    public String useSkill(Enemy enemy) {
        if (!useMana(getSkillManaCost())) return "Not enough mana! Need " + getSkillManaCost() + " mana for Fire Breath.";

        int damage = (int)(attackPower * 1.8);
        enemy.takeDamage(damage);
        takeDamage(8);

        if (health < maxHealth * 0.3 && !rageMode) {
            rageMode = true;
            attackPower += 10;
            return "Fire Breath deals " + damage + " damage! (Took 8 self-damage) -" + getSkillManaCost() + " Mana RAGE MODE ACTIVATED!";
        }

        return "Fire Breath deals " + damage + " damage! (Took 8 self-damage) -" + getSkillManaCost() + " Mana";
    }

    @Override
    public String useUltimate(Enemy enemy) {
        if (!useMana(getUltimateManaCost())) return "Not enough mana! Need " + getUltimateManaCost() + " mana for Inferno Blast.";

        int damage = attackPower * 3;
        enemy.takeDamage(damage);
        takeDamage(15);

        return "INFERNO BLAST deals " + damage + " MASSIVE damage! (Took 15 self-damage) -" + getUltimateManaCost() + " Mana";
    }

    @Override
    public String getBasicAttackName() { return "Claw Swipe"; }
    @Override
    public String getSkillName() { return "Fire Breath"; }
    @Override
    public String getUltimateName() { return "Inferno Blast"; }

    @Override
    public String getBasicAttackDescription() { return "Deals 8-28 damage +" + GameConstants.MANA_REGEN_BASIC_ATTACK + " Mana"; }
    @Override
    public String getSkillDescription() { return "Deals 180% damage, costs 8 HP (" + getSkillManaCost() + " Mana)"; }
    @Override
    public String getUltimateDescription() { return "Deals 300% damage, costs 15 HP (" + getUltimateManaCost() + " Mana)"; }

    @Override
    public int getSkillManaCost() { return 45; }
    @Override
    public int getUltimateManaCost() { return 85; }
}

class WaterTurtle extends Pet {
    private int shieldStacks;

    public WaterTurtle() {
        super("Water Turtle", "/Images/waterturtle.png", 120, 15, 15);
        this.shieldStacks = 0;
    }

    @Override
    public String useBasicAttack(Enemy enemy) {
        int damage = rand.nextInt(attackPower) + 5;
        enemy.takeDamage(damage);
        increaseHealth(3);
        increaseMana(18);
        return "Water Splash deals " + damage + " damage, heals 3 HP! +18 Mana";
    }

    @Override
    public String useSkill(Enemy enemy) {
        if (!useMana(getSkillManaCost())) return "Not enough mana! Need " + getSkillManaCost() + " mana for Tidal Wave.";

        int damage = (int)(attackPower * 1.5);
        enemy.takeDamage(damage);
        defense += 3;
        shieldStacks++;

        return "Tidal Wave deals " + damage + " damage and increases defense by 3! -" + getSkillManaCost() + " Mana";
    }

    @Override
    public String useUltimate(Enemy enemy) {
        if (!useMana(getUltimateManaCost())) return "Not enough mana! Need " + getUltimateManaCost() + " mana for Tsunami.";

        int damage = attackPower * 2;
        enemy.takeDamage(damage);
        increaseHealth(25);
        defense += 5;

        return "TSUNAMI deals " + damage + " damage, heals 25 HP, and increases defense by 5! -" + getUltimateManaCost() + " Mana";
    }

    @Override
    public String getBasicAttackName() { return "Water Splash"; }
    @Override
    public String getSkillName() { return "Tidal Wave"; }
    @Override
    public String getUltimateName() { return "Tsunami"; }

    @Override
    public String getBasicAttackDescription() { return "Deals 5-20 damage, heals 3 HP +18 Mana"; }
    @Override
    public String getSkillDescription() { return "Deals 150% damage, +3 defense (" + getSkillManaCost() + " Mana)"; }
    @Override
    public String getUltimateDescription() { return "Deals 200% damage, heals 25 HP, +5 defense (" + getUltimateManaCost() + " Mana)"; }

    @Override
    public int getSkillManaCost() { return 35; }
    @Override
    public int getUltimateManaCost() { return 75; }
}

class EarthGolem extends Pet {
    private int rockArmor;

    public EarthGolem() {
        super("Earth Golem", "/Images/earthgolem.png", 140, 10, 20);
        this.rockArmor = 0;
    }

    @Override
    public String useBasicAttack(Enemy enemy) {
        int damage = rand.nextInt(attackPower) + 10;
        enemy.takeDamage(damage);
        rockArmor++;
        increaseMana(12);
        return "Rock Throw deals " + damage + " damage! +1 armor +12 Mana";
    }

    @Override
    public String useSkill(Enemy enemy) {
        if (!useMana(getSkillManaCost())) return "Not enough mana! Need " + getSkillManaCost() + " mana for Earthquake.";

        int damage = (int)(attackPower * 1.6);
        enemy.takeDamage(damage);
        rockArmor += 3;

        if (rand.nextDouble() < 0.3) {
            return "Earthquake deals " + damage + " damage and STUNS the enemy! +3 armor -" + getSkillManaCost() + " Mana";
        }

        return "Earthquake deals " + damage + " damage! +3 armor -" + getSkillManaCost() + " Mana";
    }

    @Override
    public String useUltimate(Enemy enemy) {
        if (!useMana(getUltimateManaCost())) return "Not enough mana! Need " + getUltimateManaCost() + " mana for Mountain Crush.";

        int damage = attackPower * 2 + defense + rockArmor;
        enemy.takeDamage(damage);
        rockArmor += 8;

        return "MOUNTAIN CRUSH deals " + damage + " massive damage! +8 armor -" + getUltimateManaCost() + " Mana";
    }

    @Override
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense - rockArmor);
        health -= actualDamage;
        if (health < 0) health = 0;
    }

    @Override
    public String getBasicAttackName() { return "Rock Throw"; }
    @Override
    public String getSkillName() { return "Earthquake"; }
    @Override
    public String getUltimateName() { return "Mountain Crush"; }

    @Override
    public String getBasicAttackDescription() { return "Deals 10-20 damage, +1 armor +12 Mana"; }
    @Override
    public String getSkillDescription() { return "Deals 160% damage, 30% stun chance, +3 armor (" + getSkillManaCost() + " Mana)"; }
    @Override
    public String getUltimateDescription() { return "Deals (200% ATK + DEF + armor) damage, +8 armor (" + getUltimateManaCost() + " Mana)"; }

    @Override
    public int getSkillManaCost() { return 50; }
    @Override
    public int getUltimateManaCost() { return 90; }
}
