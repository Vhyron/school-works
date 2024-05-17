package SequenceOperations;

import java.util.Scanner;
import java.util.ArrayList;

public class SequenceOperations {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> sequence1 = new ArrayList<>();
        ArrayList<Integer> sequence2 = new ArrayList<>();

        ArrayList<Integer> sum = new ArrayList<>();
        ArrayList<Integer> difference = new ArrayList<>();
        ArrayList<Integer> product = new ArrayList<>();

        System.out.println("Sequence Operations");

        System.out.print("\nEnter size of the sequences: ");
        int size = scanner.nextInt();

        System.out.println("Enter elements for sequence 1: ");
        for (int i = 0; i < size; i++) {
            System.out.print("\tIndex " + i + ": ");
            int element = scanner.nextInt();
            sequence1.add(element);
        }

        System.out.println("Enter elements for sequence 2: ");
        for (int i = 0; i < size; i++) {
            System.out.print("\tIndex " + i + ": ");
            int element = scanner.nextInt();
            sequence2.add(element);
        }
        scanner.close();

        System.out.print("\nSequence 1: ");
        print(sequence1);
        System.out.print("\nSequence 2: ");
        print(sequence2);
        System.out.println();

        for (int i = 0; i < size; i++) {
            sum.add(sequence1.get(i) + sequence2.get(i));
            difference.add(sequence1.get(i) - sequence2.get(i));
            product.add(sequence1.get(i) * sequence2.get(i));
        }

        System.out.print("\nSum: ");
        print(sum);
        System.out.print("\nDifference: ");
        print(difference);
        System.out.print("\nProduct: ");
        print(product);
    }

    public static void print(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(list.get(i));
        }
    }
}
