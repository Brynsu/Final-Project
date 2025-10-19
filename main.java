import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//Kani kay for Main Menu
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Pet playerPet;
    static int foodCount = 0;
    static ArrayList<String> inventory = new ArrayList<>();
    static int victories = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the Pet Battle Game!");
        choosePet();

        boolean gameRunning = true;
        while (gameRunning) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. Battle");
            System.out.println("2. Feed Pet");
            System.out.println("3. View Pet Stats");
            System.out.println("4. View Inventory");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    battle();
                    break;
                case 2:
                    feedPet();
                    break;
                case 3:
                    viewPetStats();
                    break;
                case 4:
                    viewInventory();
                    break;
                case 5:
                    System.out.println("Exiting the game...");
                    gameRunning = false;
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    public static void choosePet() {
        System.out.println("Choose your pet:");
        System.out.println("1. Fire Dragon");
        System.out.println("2. Water Turtle");
        System.out.println("3. Earth Golem");
        System.out.print("Enter the number corresponding to your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                playerPet = new Pet("Fire Dragon", 100, 20, 15);
                break;
            case 2:
                playerPet = new Pet("Water Turtle", 120, 15, 10);
                break;
            case 3:
                playerPet = new Pet("Earth Golem", 150, 10, 20);
                break;
            default:
                System.out.println("Invalid choice, defaulting to Fire Dragon.");
                playerPet = new Pet("Fire Dragon", 100, 20, 15);
        }
        System.out.println("You have chosen " + playerPet.getName() + " as your pet!");
    }
//Please ko implement sa Battle System Diri
//Ari Start
 
public static void battle() {
        viewInventory();
        victories++;
        Enemy enemy = new Enemy();
 
        if (victories % 5 == 0) {
            System.out.println("A powerful Boss appears for the " + victories + "th victory!");
            if (bossBattle(enemy)) {
                System.out.println("You defeated the Boss!");
                foodCount += new Random().nextInt(16) + 5;
                inventory.add("Food x" + (new Random().nextInt(16) + 5));
                getRandomItem();
            } else {
                System.out.println("You lost the boss battle.");
            }
        } else {
            System.out.println("A wild opponent appears!");
            if (enemyBattle(enemy)) {
                System.out.println("You won the battle!");
                foodCount += new Random().nextInt(16) + 5;
                inventory.add("Food x" + (new Random().nextInt(16) + 5));
                getRandomItem();
                if (foodCount >= 50 && foodCount < 100) {
                    System.out.println(playerPet.getName() + " is evolving!");
                    playerPet.evolve();
                } else if (foodCount >= 100) {
                    System.out.println(playerPet.getName() + " has evolved again!");
                    playerPet.evolve();
                }
            } else {
                System.out.println("You lost the battle. Better luck next time!");
            }
        }
    }
 
    public static boolean bossBattle(Enemy enemy) {
        Random random = new Random();
        int winChance = random.nextInt(100);
        printBattleScene(enemy);
        return winChance > 60;
    }
 
    public static boolean enemyBattle(Enemy enemy) {
        Random random = new Random();
        printBattleScene(enemy);
        return random.nextInt(100) > 40;
    }
 
    public static void printBattleScene(Enemy enemy) {
        System.out.println("\n--- Battle Start! ---");
        System.out.println("You are fighting a " + enemy.getName() + "!");
        System.out.println("Enemy Health: " + enemy.getHealth());
        System.out.println("Your Pet: " + playerPet.getName() + " | Health: " + playerPet.getHealth());
 
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("Fight Round " + (i + 1) + "...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
 
        System.out.println("\nChoose your attack:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Special Attack");
        System.out.println("3. Defend");
        System.out.print("Enter your choice: ");
        int attackChoice = scanner.nextInt();
        scanner.nextLine();
 
        int damageToEnemy = 0;
        int damageToPlayer = new Random().nextInt(enemy.getAttackPower());
 
        switch (attackChoice) {
            case 1:
                damageToEnemy = new Random().nextInt(playerPet.getAttackPower());
                System.out.println("You use Basic Attack! You deal " + damageToEnemy + " damage.");
                break;
            case 2:
                damageToEnemy = new Random().nextInt(playerPet.getAttackPower() * 2);
                System.out.println("You use Special Attack! You deal " + damageToEnemy + " damage.");
                break;
            case 3:
                System.out.println("You defend! Your pet takes less damage this round.");
                damageToPlayer = damageToPlayer / 2;
                break;
            default:
                System.out.println("Invalid choice! You missed your turn.");
        }
 
        if (damageToPlayer > playerPet.getHealth()) {
            damageToPlayer = playerPet.getHealth();
        }
 
        enemy.takeDamage(damageToEnemy);
        playerPet.takeDamage(damageToPlayer);
 
        if (enemy.getHealth() <= 0) {
            System.out.println("You defeated the " + enemy.getName() + "!");
        } else if (playerPet.getHealth() <= 0) {
            System.out.println("Your pet has been defeated. You lost the battle.");
        }
    }  
//Please ko implement sa Feeding System diri
// Ari Start
    public static void feedPet() {
        System.out.println("You have " + foodCount + " food(s) available.");
        System.out.print("How many foods do you want to feed your pet? ");
        int foodToFeed = scanner.nextInt();
        scanner.nextLine();
 
        if (foodToFeed <= foodCount && foodToFeed > 0) {
            foodCount -= foodToFeed;
            System.out.println("You fed your pet " + foodToFeed + " food(s)! Remaining food: " + foodCount);
            playerPet.increaseHappiness(foodToFeed);
        } else if (foodToFeed > foodCount) {
            System.out.println("You don't have enough food!");
        } else {
            System.out.println("Invalid amount of food.");
        }
    }
 
    public static void viewPetStats() {
        System.out.println(playerPet);
    }
 
    public static void viewInventory() {
        System.out.println("Your Inventory:");
        System.out.println("Food: " + foodCount);
        for (String item : inventory) {
            System.out.println(item);
        }
    }
 
    public static void getRandomItem() {
        Random rand = new Random();
        int chance = rand.nextInt(100);
 
        if (chance < 30) {
            inventory.add("Stone (Elemental)");
            System.out.println("You found a stone!");
        } else if (chance < 60) {
            inventory.add("Armor");
            System.out.println("You found an armor!");
        } else {
            System.out.println("No item found.");
        }
    }
}

// Diri kay ang Inventory
// Ari Start
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

