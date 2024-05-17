package ListManager;

import java.util.Scanner;
import java.util.ArrayList;

public class ListManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();

        System.out.println("List Manager");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add numbers to list");
            System.out.println("2. Add words to list");
            System.out.println("3. Display list");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\nEnter numbers (Enter a negative number to finish):");
                    while (true) {
                        int number = scanner.nextInt();
                        if (number < 0) {
                            break;
                        }
                        numbers.add(number);
                    }
                    break;

                case 2:
                    System.out.println("\nEnter words (Enter 'done' to finish):");
                    while (true) {
                        String word = scanner.nextLine();
                        if (word.equalsIgnoreCase("done")) {
                            break;
                        }
                        words.add(word);
                    }
                    break;

                case 3:
                    display(numbers, words);
                    break;

                case 4:
                    System.out.println("\nExiting Program");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid option. Please choose 1-4.");
            }
        }
    }

    public static void display(ArrayList<Integer> numbers, ArrayList<String> words) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n1. Display Numbers");
        System.out.println("2. Display Words");
        System.out.print("Choose between 1-2 what to display: ");

        int choice = scanner.nextInt();

        if (choice == 1) {
            if (!numbers.isEmpty()) {
                System.out.print("\nNumbers: ");
                for (int i = 0; i < numbers.size(); i++) {
                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(numbers.get(i));
                }
                System.out.println();
            } else {
                System.out.println("\nNumbers: Empty");
            }
        } else if (choice == 2) {
            if (!words.isEmpty()) {
                System.out.print("\nWords: ");
                for (int i = 0; i < words.size(); i++) {
                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(words.get(i));
                }
                System.out.println();
            } else {
                System.out.println("\nWords: Empty");
            }
        } else {
            System.out.println("\nInvalid choice");
        }
    }
}
