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

//Please ko implement sa Feeding System diri
// Ari Start

// Diri kay ang Inventory
// Ari Start

