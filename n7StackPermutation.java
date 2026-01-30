import java.io.*;
import java.util.*;

public class n7StackPermutation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String line1 = sc.nextLine().trim();
        String[] inStr = line1.split(" ");
        int n = inStr.length;

        int[] ip = new int[n];
        for (int i = 0; i < n; i++) {
            ip[i] = Integer.parseInt(inStr[i]);
        }

        String line2 = sc.nextLine().trim();
        String[] outStr = line2.split(" ");

        int[] op = new int[n];
        for (int i = 0; i < n; i++) {
            op[i] = Integer.parseInt(outStr[i]);
        }

        Stack<Integer> stack = new Stack<>();
        int j = 0;

        for (int i = 0; i < n; i++) {
            stack.push(ip[i]);

            while (!stack.isEmpty() && j < n && stack.peek() == op[j]) {
                stack.pop();
                j++;
            }
        }

        if (stack.isEmpty() && j == n) {
            System.out.print("YES");
        } else {
            System.out.print("Not Possible");
        }

        sc.close();
    }
}