class Pet {
    private String name;
    private int health;
    private int attackPower;
    private int defensePower;
    private int happiness;
 
    public Pet(String name, int health, int attackPower, int defensePower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.happiness = 0;
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
        System.out.println(name + " took " + damage + " damage. Current health: " + health);
    }
 
    public void increaseHappiness(int amount) {
        happiness += amount;
        System.out.println(name + "'s happiness increased by " + amount + "!");
    }
 
    public void evolve() {
        if (happiness >= 50 && happiness < 100) {
            attackPower += 10;
            health += 20;
            System.out.println(name + " evolved to a stronger form!");
        } else if (happiness >= 100) {
            attackPower += 20;
            health += 40;
            System.out.println(name + " evolved to its ultimate form!");
        } else {
            System.out.println(name + " needs more feeding to evolve.");
        }
    }
 
    @Override
    public String toString() {
        return name + " | Health: " + health + " | Attack Power: " + attackPower + " | Happiness: " + happiness;
    }
}
