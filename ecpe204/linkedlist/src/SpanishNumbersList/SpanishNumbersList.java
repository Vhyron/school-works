package SpanishNumbersList;

import java.util.LinkedList;

public class SpanishNumbersList {
    public static void main(String[] args) {
        LinkedList<String> numeros = new LinkedList<>();

        System.out.println("Spanish Numbers List");

        numeros.add("dos");
        numeros.add("tres");
        numeros.add("cuatro");

        display("\nNúmeros en Español:", numeros);

        numeros.addFirst("uno");
        numeros.addLast("cinco");

        display("Números en Español:", numeros);
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