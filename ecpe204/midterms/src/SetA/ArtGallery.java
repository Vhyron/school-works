package SetA;

import java.util.Scanner;
import java.util.ArrayList;

public class ArtGallery {

    private node head;
    private static class node {
        String title;
        String artist;
        String year;
        node next;

        node(String title, String artist, String year) {
            this.title = title;
            this.artist = artist;
            this.year = year;
            this.next = null;
        }
    }

    public void add(String title, String artist, String year) {
        node new_node = new node(title, artist, year);

        if (head == null) {
            head = new_node;
            return;
        }
        node current_node = head;

        while (current_node.next != null) {
            current_node = current_node.next;
        }
        current_node.next = new_node;
    }

    public void display() {
        node current_node = head;

        while (current_node != null) {
            System.out.println("\tTitle: " + current_node.title);
            System.out.println("\tArtist: " + current_node.artist);
            System.out.println("\tYear: " + current_node.year);
            System.out.println();
            current_node = current_node.next;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArtGallery paintings = new ArtGallery();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> periods = new ArrayList<>();
        ArrayList<String> materials = new ArrayList<>();

        System.out.println("Art Gallery: Classic and Modern");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a new sculpture (Classic)");
            System.out.println("2. Add a new painting (Modern)");
            System.out.println("3. Display all sculptures and paintings");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("\nEnter sculpture's name: ");
                    String name = scanner.nextLine();
                    names.add(name);

                    System.out.print("Enter sculpture's period: ");
                    String period = scanner.nextLine();
                    periods.add(period);

                    System.out.print("Enter sculpture's material: ");
                    String material = scanner.nextLine();
                    materials.add(material);

                    System.out.println("Sculpture added successfully!");
                    break;

                case 2:
                    scanner.nextLine();
                    System.out.print("\nEnter painting's title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter painting's artist: ");
                    String artist = scanner.nextLine();

                    System.out.print("Enter painting's year: ");
                    String year = scanner.nextLine();
                    paintings.add(title, artist, year);

                    System.out.println("Painting added successfully");
                    break;

                case 3:
                    System.out.println("\nSculptures:");
                    for (int i = 0; i < names.size(); i++) {
                        System.out.println("\tName: " + names.get(i));
                        System.out.println("\tPeriod: " + periods.get(i));
                        System.out.println("\tMaterial: " + materials.get(i));
                        System.out.println();
                    }

                    System.out.println("Paintings:");
                    paintings.display();
                    break;

                case 4:
                    System.out.println("\nExiting Program");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid choice.");
            }
        }
    }
}