import java.io.*;
import java.util.*;

class Node {
    long data;
    Node prev, next;

    Node(long data) {
        this.data = data;
    }
}

public class n11SortBitonicDLL {
    static Node reverse(Node head) {
        Node temp = null;
        Node curr = head;

        while (curr != null) {
            temp = curr.prev;
            curr.prev = curr.next;
            curr.next = temp;
            curr = curr.prev;
        }

        if (temp != null) {
            head = temp.prev;
        }
        return head;
    }

    static Node merge(Node first, Node second) {
        if (first == null)
            return second;
        if (second == null)
            return first;

        if (first.data <= second.data) {
            first.next = merge(first.next, second);
            if (first.next != null)
                first.next.prev = first;
            first.prev = null;
            return first;
        } else {
            second.next = merge(first, second.next);
            if (second.next != null)
                second.next.prev = second;
            second.prev = null;
            return second;
        }
    }

    static Node sortBitonic(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node curr = head;

        while (curr.next != null && curr.data <= curr.next.data) {
            curr = curr.next;
        }

        if (curr.next == null)
            return head;

        Node second = curr.next;
        curr.next = null;
        second.prev = null;

        second = reverse(second);

        return merge(head, second);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        while (t-- > 0) {
            Node head = null, tail = null;

            while (true) {
                long x = sc.nextLong();
                if (x == -1)
                    break;

                Node node = new Node(x);
                if (head == null) {
                    head = tail = node;
                } else {
                    tail.next = node;
                    node.prev = tail;
                    tail = node;
                }
            }

            head = sortBitonic(head);
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.data + " ");
                temp = temp.next;
            }
            System.out.println();

        }

        sc.close();
    }
}