import java.io.*;
import java.util.*;

class Node {
    long data;
    Node prev, next;

    Node(long data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class n10MergeSortDLL {
    static Node split(Node head) {
        Node slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node second = slow.next;
        slow.next = null;
        if (second != null)
            second.prev = null;

        return second;
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

    static Node mergeSort(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node second = split(head);

        head = mergeSort(head);
        second = mergeSort(second);

        return merge(head, second);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        Node head = null, tail = null;
        for (int i = 0; i < n; i++) {
            long x = sc.nextLong();
            Node newNode = new Node(x);

            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        }

        head = mergeSort(head);

        Node curr = head;
        while (curr.next != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println(curr.data);

        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.prev;
        }

        sc.close();
    }
}