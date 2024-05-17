package PetListManager;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class PetListManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> pets = new ArrayList<>();

        System.out.println("Pet List Manager");

        System.out.println("\nEnter pets (Enter 'done' to finish):");

        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if (!input.equalsIgnoreCase("done")) {
                pets.add(input);
            } else {
                break;
            }
        }
        scanner.close();

        System.out.print("\nDisplay all pets: ");
        for (int i = 0; i < pets.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(pets.get(i));
        }

        Collections.reverse(pets);

        System.out.print("\nDisplay in reverse: ");
        for (int i = 0; i < pets.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(pets.get(i));
        }

        Collections.reverse(pets);

        System.out.print("\nDisplay all pets that end with 's': ");
        ArrayList<String> withS = new ArrayList<>();
        for (String pet : pets) {
            if (pet.endsWith("s")) {
                withS.add(pet);
            }
        }
        for (int i = 0; i < withS.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(withS.get(i));
        }

        System.out.print("\nDisplay all pets that do not end with 's': ");
        ArrayList<String> withoutS = new ArrayList<>();
        for (String pet : pets) {
            if (!pet.endsWith("s")) {
                withoutS.add(pet);
            }
        }
        for (int i = 0; i < withoutS.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(withoutS.get(i));
        }
        System.out.println();
    }
}