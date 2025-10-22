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
}


