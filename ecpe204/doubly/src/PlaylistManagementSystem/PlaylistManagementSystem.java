package PlaylistManagementSystem;

import java.util.Scanner;

public class PlaylistManagementSystem {
    private static class node {
        String song;
        String artist;
        node previous;
        node next;

        node (String song, String artist) {
            this.song = song;
            this.artist = artist;
            this.previous = null;
            this.next = null;
        }
    }

    private node head;
    private node tail;
    private int size;

    public boolean empty() { return size == 0; }

    public PlaylistManagementSystem() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(String song, String artist) {
        node new_node = new node(song, artist);

        if (empty()) {
            head = new_node;
        } else {
            new_node.previous = tail;
            tail.next = new_node;
        }
        tail = new_node;
        size++;
    }

    public void delete(String x) {
        node current_node = head;

        while (current_node != null) {
            if (current_node.song.equalsIgnoreCase(x)) {
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
            System.out.println("\tSong: " + current_node.song);
            System.out.println("\tArtist: " + current_node.artist);
            current_node = current_node.next;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlaylistManagementSystem playlist = new PlaylistManagementSystem();

        System.out.println("Playlist Management System");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a song");
            System.out.println("2. Remove a song");
            System.out.println("3. Display song");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("\nEnter song: ");
                    String song = scanner.nextLine();

                    System.out.print("Enter song artist: ");
                    String artist = scanner.nextLine();
                    playlist.add(song, artist);

                    System.out.println("Song added successfully!");
                    break;

                case 2:
                    System.out.print("\nEnter song to remove: ");
                    String x = scanner.nextLine();
                    playlist.delete(x);

                    System.out.println("Song removed successfully!");
                    break;

                case 3:
                    System.out.println("\nSongs in the playlist:");
                    playlist.display();
                    break;

                case 4:
                    System.out.println("\nExiting Program");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
