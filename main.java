import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Pet pet;
    static JFrame statusWindow;
    static JLabel statusLabel;

    public static void main(String[] args) {
        
        createStatusWindow();

        System.out.println("Welcome to Axie Arena! >///<");
        choosePet();

        boolean playing = true;
        while (playing) {
            updateStatusWindow();  
            System.out.println("\n1. Battle");
            System.out.println("2. Feed Pet");
            System.out.println("3. View Pet Stats");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> battle();
                case 2 -> feedPet();
                case 3 -> System.out.println("\n" + pet);
                case 4 -> {
                    System.out.println("Goodbye!");
                    playing = false;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void createStatusWindow() {
        statusWindow = new JFrame("Pet Status");
        statusWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statusWindow.setSize(300, 100);
        statusWindow.setLayout(new FlowLayout());

        statusLabel = new JLabel("Waiting for pet selection...");
        statusWindow.add(statusLabel);

        statusWindow.setLocation(800, 100);  
        statusWindow.setVisible(true);
    }

    private static void updateStatusWindow() {
        if (pet != null) {
            statusLabel.setText("<html>Pet: " + pet.getName() +
                    "<br>Health: " + pet.getHealth() +
                    "<br>Happiness: " + pet.getHappiness() + "</html>");
        }
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

    public static void battle() {
        Enemy enemy = new Enemy();
        Random rand = new Random();

        System.out.println("\nA wild " + enemy.getName() + " appeared!");
        System.out.println("Enemy Health: " + enemy.getHealth());

        while (pet.getHealth() > 0 && enemy.getHealth() > 0) {
            updateStatusWindow();
            System.out.println("\nChoose attack:");
            System.out.println("1. Basic Attack");
            System.out.println("2. Special Attack");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();

            int playerDamage = (choice == 2)
                    ? rand.nextInt(pet.getAttackPower()) + 10
                    : rand.nextInt(pet.getAttackPower());

            enemy.takeDamage(playerDamage);
            System.out.println("You dealt " + playerDamage + " damage to " + enemy.getName() + "!");

            if (enemy.getHealth() <= 0) {
                System.out.println("You defeated the " + enemy.getName() + "!");
                pet.increaseHappiness(10);
                break;
            }

            int enemyDamage = rand.nextInt(enemy.getAttackPower());
            pet.takeDamage(enemyDamage);
            System.out.println(enemy.getName() + " hits back for " + enemyDamage + " damage!");
        }

        if (pet.getHealth() <= 0) {
            System.out.println("\nYour pet fainted! You lost the battle.");
        }
    }

    public static void feedPet() {
        System.out.println("\nYou fed your pet!");
        pet.increaseHappiness(10);
        if (pet.getHealth() < 100) {
            System.out.println("Your pet feels stronger and heals a bit!");
        }
        updateStatusWindow();
    }
}


