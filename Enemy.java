import java.util.Random;

class Enemy {
    private String name;
    private int health;
    private int attackPower;
 
    public Enemy() {
        Random rand = new Random();
        String[] enemyNames = {"Goblin", "Dragon", "Wolf", "Skeleton", "Troll"};
        this.name = enemyNames[rand.nextInt(enemyNames.length)];
        this.health = rand.nextInt(50) + 50;
        this.attackPower = rand.nextInt(15) + 10;
    }
 
    public String getName() {
        return name;
    }
 
    public int getHealth() {
        return health;
    }
 
    public int getAttackPower() {
        return attackPower;
    }
 
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
}
