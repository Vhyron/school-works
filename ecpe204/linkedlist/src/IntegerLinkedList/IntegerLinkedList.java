package IntegerLinkedList;

import java.util.LinkedList;
import java.util.Scanner;

public class IntegerLinkedList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Integer> integers = new LinkedList<>();

        System.out.println("Integer LinkedList");

        System.out.println("\nEnter integers (Enter non-integer to finish):");

        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                int integer = scanner.nextInt();
                integers.add(integer);
            } else {
                scanner.next();
                break;
            }
        }

        display("\nIntegers: ", integers);

        int sumBefore = sum(integers);
        System.out.println("Sum of integers: " + sumBefore);

        System.out.print("\nChoose an index to insert: ");
        int index = scanner.nextInt();

        System.out.print("Enter integer to insert: ");
        int insert = scanner.nextInt();
        integers.add(index, insert);

        display("\nIntegers: ", integers);

        int sumAfterInsertion = sum(integers);
        System.out.println("Sum of integers after insertion: " + sumAfterInsertion);

        scanner.close();
    }

    public static int sum(LinkedList<Integer> integers) {
        int sum = 0;
        for (int integer : integers) {
            sum += integer;
        }
        return sum;
    }

    public static void display(String message, LinkedList<Integer> list) {
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
