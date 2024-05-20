package LibraryManagementSystem;

import java.util.Scanner;

public class LibraryManagementSystem {
    private static class node {
        int id;
        String title;
        String author;
        node previous;
        node next;

        node(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.previous = null;
            this.next = null;
        }
    }

    private node head;
    private node tail;
    private int size;

    public boolean empty() { return size == 0; }

    public LibraryManagementSystem() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(int id, String title, String author) {
        node new_node = new node(id, title, author);

        if (empty()) {
            head = new_node;
        } else {
            new_node.previous = tail;
            tail.next = new_node;
        }
        tail = new_node;

        size++;
    }

    public void remove(int x) {
        node current_node = head;

        while (current_node != null) {
            if (current_node.id == x) {
                if (current_node == head) {
                    head = current_node.next;

                    if (head != null) {
                        head.previous = null;
                    }
                } else if (current_node == tail) {
                    tail = current_node.previous;
                    tail.next = null;
                } else {
                    current_node.previous.next = current_node.next;
                    current_node.next.previous = current_node.previous;
                }

                size--;
                return;
            }

            current_node = current_node.next;
        }
    }

    public void display() {
        node current_node = head;

        while (current_node != null) {
            System.out.println();
            System.out.println("\tID: " + current_node.id);
            System.out.println("\tBook title: " + current_node.title);
            System.out.println("\tBook author: " + current_node.author);
            current_node = current_node.next;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManagementSystem library = new LibraryManagementSystem();

        System.out.println("Library Management System");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a book");
            System.out.println("2. Remove a book");
            System.out.println("3. Display library");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("\nEnter book ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    library.add(id, title, author);

                    System.out.println("Book added successfully!");
                    break;

                case 2:
                    System.out.print("\nEnter book ID to remove: ");
                    int x = scanner.nextInt();
                    library.remove(x);

                    System.out.println("Book removed successfully!");
                    break;

                case 3:
                    System.out.println("\nBooks in the library:");
                    library.display();
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