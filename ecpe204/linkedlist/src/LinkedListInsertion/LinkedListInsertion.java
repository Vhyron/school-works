package LinkedListInsertion;

import java.util.LinkedList;
import java.util.Scanner;

public class LinkedListInsertion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<String> elements = new LinkedList<>();

        System.out.println("LinkedList Insertion");

        System.out.println("\nEnter elements (Enter 'exit' to finish):");
        while (true) {
            String element = scanner.nextLine();
            if (element.equalsIgnoreCase("exit")) {
                break;
            }
            elements.add(element);
        }

        display("\nLinkedList:", elements);

        System.out.print("\nChoose an index to insert: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter element to insert: ");
        String insert = scanner.nextLine();

        elements.add(index, insert);
        scanner.close();

        display("\nLinkedList after insertion:", elements);
    }

    public static void display(String message, LinkedList<String> list) {
        System.out.print(message + " ");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(list.get(i));
        }
        System.out.println();
    }
}

