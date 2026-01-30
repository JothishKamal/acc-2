import java.util.*;

class Node {
    int data;
    int priority;
    Node next, prev;

    Node(int data, int priority) {
        this.data = data;
        this.priority = priority;
        this.next = null;
        this.prev = null;
    }
}

public class n12PriorityQueueUsingDLL {

    static Node head = null;

    static void insert(int data, int priority) {
        Node node = new Node(data, priority);

        if (head == null || priority < head.priority) {
            node.next = head;
            if (head != null)
                head.prev = node;
            head = node;
            return;
        }

        Node curr = head;
        while (curr.next != null && curr.next.priority <= priority) {
            curr = curr.next;
        }

        node.next = curr.next;
        if (curr.next != null)
            curr.next.prev = node;

        curr.next = node;
        node.prev = curr;
    }

    static void delete() {
        if (head == null) {
            System.out.println("Error List Empty");
            return;
        }

        head = head.next;
        if (head != null)
            head.prev = null;
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
                    insert(sc.nextInt(), sc.nextInt());
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
