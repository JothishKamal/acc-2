import java.io.*;
import java.util.*;

public class n3MinStack {
    private int[] stack;
    private int top;
    private int minEle;

    n3MinStack(int n) {
        stack = new int[n];
        top = -1;
    }

    void push(int x) {
        if (top == -1) {
            stack[++top] = x;
            minEle = x;
        } else if (x >= minEle) {
            stack[++top] = x;
        } else {
            stack[++top] = 2 * x - minEle;
            minEle = x;
        }
    }

    int pop() {
        if (top == -1) {
            return -1;
        }

        int t = stack[top--];
        if (t >= minEle) {
            return t;
        } else {
            int ogMin = minEle;
            minEle = 2 * minEle - t;
            return ogMin;
        }
    }

    int getMin() {
        return minEle;
    }

    void print() {
        for (int x : stack) {
            System.out.print(x + " ");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        n = sc.nextInt();
        n3MinStack ms = new n3MinStack(n);

        for (int i = 0; i < n; i++) {
            ms.push(sc.nextInt());
        }

        System.out.print(ms.getMin());
        sc.close();
    }
}