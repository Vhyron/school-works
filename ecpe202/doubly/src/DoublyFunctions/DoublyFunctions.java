package DoublyFunctions;

import java.util.Scanner;

public class DoublyFunctions {

    private static class node {
        int data;
        node previous;
        node next;

        node(int data) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }
    }

    private node head;
    private node tail;
    private int size;

    public boolean empty() {return size == 0;}

    public DoublyFunctions() {
        head = null;
        tail = null;
        size = 0;
    }

    public void append(int front) {
        node newnode = new node(front);

        if (empty()) {
            head = newnode;
            tail = newnode;
        } else {
            newnode.next = head;
            head.previous = newnode;
            head = newnode;
        }
        size++;
    }

    public void prepend(int back) {
        node newnode = new node(back);

        if (empty()) {
            head = newnode;
        } else {
            newnode.previous = tail;
            tail.next = newnode;
        }
        tail = newnode;
        size++;
    }

    public void remove(int x) {
        node currentnode = head;

        while (currentnode != null) {
            if (currentnode.data == x) {
                if (currentnode == head) {
                    head = currentnode.next;

                    if (head != null) {
                        head.previous = null;
                    }
                } else if (currentnode == tail) {
                    tail = currentnode.previous;
                    tail.next = null;
                } else {
                    currentnode.previous.next = currentnode.next;
                    currentnode.next.previous = currentnode.previous;
                }
                size--;
                return;
            }
            currentnode = currentnode.next;
        }
    }

    public void insert(int insert_value, int beside) {
        node new_node = new node(insert_value);
        node current_node = head;

        while (current_node != null) {
            if (current_node.data == beside) {
                new_node.previous = current_node;
                new_node.next = current_node.next;

                if (current_node == tail) {
                    tail = new_node;
                } else {
                    current_node.next.previous = new_node;
                }

                current_node.next = new_node;
                size++;
                return;
            }
            current_node = current_node.next;
        }
        System.out.println("The value " + beside + " was not found");
    }

    public void display() {
        node currentnode = head;

        while (currentnode != null) {
            System.out.print(currentnode.data + " ");
            currentnode = currentnode.next;
        }
    }

    public void reverse() {
        node current = head;
        node prev = null;

        while (current != null) {
            node next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DoublyFunctions numbers = new DoublyFunctions();

        System.out.println("Doubly Functions Program");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Front");
            System.out.println("2. Back");
            System.out.println("3. Remove");
            System.out.println("4. Insert");
            System.out.println("5. Display");
            System.out.println("6. Reverse");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("\nEnter value to add to the front: ");
                    int front = scanner.nextInt();
                    numbers.append(front);

                    System.out.println("Value added successfully");
                    break;

                case 2:
                    System.out.print("\nEnter value to add to the back: ");
                    int back = scanner.nextInt();
                    numbers.prepend(back);

                    System.out.println("Value added successfully");
                    break;

                case 3:
                    System.out.print("\nEnter value to be removed: ");
                    int x = scanner.nextInt();
                    numbers.remove(x);

                    System.out.println("Value removed successfully");
                    break;

                case 4:
                    System.out.print("\nEnter value to insert: ");
                    int insert_value = scanner.nextInt();

                    System.out.print("Beside what value? ");
                    int beside = scanner.nextInt();
                    numbers.insert(insert_value, beside);

                    System.out.println("Value inserted successfully");
                    break;

                case 5:
                    System.out.print("\nDisplaying integers: ");
                    numbers.display();

                    System.out.println();
                    break;

                case 6:
                    numbers.reverse();

                    System.out.print("\nDisplaying integers in reverse: ");
                    numbers.display();

                    numbers.reverse();

                    System.out.println();
                    break;

                case 7:
                    System.out.println("\nExiting program");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid Choice.");
            }
        }
    }
}
