import java.util.Random;
import java.util.Scanner;

public class main {
    static Scanner scanner = new Scanner(System.in);
    static Pet pet;
    static level petLevel = new level();

    public static void main(String[] args) {
        showTitleScreen();
        choosePet();

        boolean playing = true;
        while (playing) {
            showStatus();
            System.out.println("\n1. Battle");
            System.out.println("2. Feed Pet");
            System.out.println("3. View Pet Stats");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> battle();
                case 2 -> feedPet();
                case 3 -> showStatus();
                case 4 -> {
                    System.out.println("Goodbye!");
                    playing = false;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    public static void showTitleScreen() {
        printBox("""
ASCII INFINITY
Welcome to the game!
Press ENTER to start...
""");
        scanner.nextLine(); 
        scanner.nextLine();
    }

    public static void choosePet() {
        System.out.println("\nChoose your pet:");
        System.out.println("1. Fire Dragon");
        System.out.println("2. Water Turtle");
        System.out.println("3. Earth Golem");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> pet = new Pet("Fire Dragon", 100, 20, 10);
            case 2 -> pet = new Pet("Water Turtle", 120, 15, 15);
            case 3 -> pet = new Pet("Earth Golem", 140, 10, 20);
            default -> pet = new Pet("Fire Dragon", 100, 20, 10);
        }

        System.out.println("You chose " + pet.getName() + "!");
    }

    public static void showStatus() {
        String mood = (pet.getHappiness() < 30) ? "Neutral"
                : (pet.getHappiness() < 70) ? "Happy"
                : "Ecstatic";

        printBox("""
=== PET STATUS ===
Name: """ + pet.getName() + """
Health: """ + pet.getHealth() + """
Happiness: """ + pet.getHappiness() + """
Level: """ + petLevel.getLevel() + """
EXP: """ + petLevel.getExperience() + """
Mood: """ + mood + """
""");
    }

    public static void battle() {
        Random rand = new Random();
        Enemy enemy = new Enemy();
        printBox("ENEMY: " + enemy.getName() + "\n" + Art.getEnemyArt(enemy.getName()) + "\nHP: " + enemy.getHealth());
        printBox("YOUR PET: " + pet.getName() + "\n" + Art.getPetArt(pet.getName()) + "\nHP: " + pet.getHealth());

        while (pet.getHealth() > 0 && enemy.getHealth() > 0) {
            System.out.println("\nChoose attack:");
            System.out.println("1. Basic Attack");
            System.out.println("2. Special Attack");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();

            int playerDamage = (choice == 2) ? rand.nextInt(pet.getAttackPower()) + 10 : rand.nextInt(pet.getAttackPower());
            enemy.takeDamage(playerDamage);
            System.out.println("You dealt " + playerDamage + " damage to " + enemy.getName() + "!");

            if (enemy.getHealth() <= 0) {
                System.out.println("You defeated the " + enemy.getName() + "!");
                pet.increaseHappiness(10);
                petLevel.addExperience(10);
                pet.evolve();
                break;
            }

            int enemyDamage = rand.nextInt(enemy.getAttackPower());
            pet.takeDamage(enemyDamage);
            System.out.println(enemy.getName() + " hits back for " + enemyDamage + " damage!");
        }

        showStatus();
    }

    public static void feedPet() {
        String[] foods = {"Apple", "Meat", "Berry"};
        System.out.println("\nChoose food to feed your pet:");
        for (int i = 0; i < foods.length; i++) {
            System.out.println((i + 1) + ". " + foods[i]);
        }
        System.out.print("Your choice: ");
        int foodChoice = scanner.nextInt();

        if (foodChoice < 1 || foodChoice > foods.length) {
            System.out.println("Invalid choice!");
            return;
        }

        System.out.println("You fed your pet a " + foods[foodChoice - 1] + "!");
        pet.increaseHappiness(10 * foodChoice);
        pet.increaseHealth(10 * foodChoice);
        pet.evolve();
        petLevel.addExperience(10 * foodChoice);
        showStatus();
    }

    public static void printBox(String content) {
        String[] lines = content.split("\n");
        int maxLength = 0;
        for (String line : lines) if (line.length() > maxLength) maxLength = line.length();
        int width = maxLength + 4;

        System.out.println("+" + "-".repeat(width) + "+");
        for (String line : lines)
            System.out.println("|  " + line + " ".repeat(maxLength - line.length()) + "  |");
        System.out.println("+" + "-".repeat(width) + "+");
    }
}
