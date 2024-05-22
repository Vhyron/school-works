package SinglyFunctions;
import java.util.Scanner;

public class SinglyFunctions {
    private Node head;

    private static class Node {
        private int data;
        private Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public void append(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    public void prepend(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    public boolean search(int x) {
        Node current = head;

        while (current != null) {
            if (current.data == x) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void delete(int x) {
        Node current = head;
        Node previous = null;

        if (current != null && current.data == x) {
            head = current.next;
            return;
        }

        while (current != null && current.data != x) {
            previous = current;
            current = current.next;
        }

        if (current == null) return;

        previous.next = current.next;
    }

    public void display() {
        Node current = head;

        System.out.print("\nNodes: ");

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }

        System.out.println("null");
    }

    public void reverseDisplay() {
        Node previous = null;
        Node current = head;

        while (current != null) {
            Node next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        head = previous;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SinglyFunctions list = new SinglyFunctions();

        System.out.println("Singly Functions");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Append");
            System.out.println("2. Prepend");
            System.out.println("3. Search node");
            System.out.println("4. Delete node");
            System.out.println("5. Display node");
            System.out.println("6. Reverse display");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("\nEnter value to append: ");
                    int appendValue = scanner.nextInt();
                    list.append(appendValue);

                    System.out.println("Value appended successfully!");
                    break;

                case 2:
                    System.out.print("\nEnter value to prepend: ");
                    int prependValue = scanner.nextInt();
                    list.prepend(prependValue);

                    System.out.println("Value prepended successfully!");
                    break;

                case 3:
                    System.out.print("\nEnter element to search for: ");
                    int searchValue = scanner.nextInt();
                    boolean found = list.search(searchValue);

                    if (found) {
                        System.out.println("'" + searchValue + "' is found in the list");
                    } else {
                        System.out.println("'" + searchValue + "' is not found in the list");
                    }
                    break;

                case 4:
                    System.out.print("\nEnter value to delete: ");
                    int deleteValue = scanner.nextInt();
                    list.delete(deleteValue);

                    System.out.println("Value deleted successfully!");
                    break;

                case 5:
                    list.display();
                    break;

                case 6:
                    list.reverseDisplay();
                    System.out.println("\nReversed!");
                    list.display();
                    break;

                case 7:
                    System.out.println("\nExiting Program");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid choice!");
            }
        }
    }
}

