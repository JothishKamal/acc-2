import java.io.*;
import java.util.*;

class Node {
    int data;
    int priority;
    Node next;

    Node(int data, int priority) {
        this.data = data;
        this.priority = priority;
        this.next = null;
    }
}

public class n12PriorityQueueUsingLinkedList {
    static Node head = null;

    static void insert(int data, int priority) {
        Node node = new Node(data, priority);

        if (head == null || priority < head.priority) {
            node.next = head;
            head = node;
            return;
        }

        Node curr = head;
        while (curr.next != null && curr.next.priority <= priority) {
            curr = curr.next;
        }

        node.next = curr.next;
        curr.next = node;
    }

    static void delete() {
        if (head == null) {
            System.out.println("Error List Empty");
            return;
        }
        head = head.next;
    }

    static void display() {
        if (head == null) {
            System.out.println("Empty");
            return;
        }

        System.out.print("Queue: ");
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " " + temp.priority + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    int data = sc.nextInt();
                    int priority = sc.nextInt();
                    insert(data, priority);
                    break;
                case 2:
                    delete();
                    break;
                case 3:
                    display();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong Choice");
            }
        }
    }
}