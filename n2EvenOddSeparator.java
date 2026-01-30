import java.io.*;
import java.util.*;

public class n2EvenOddSeparator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Integer> odd = new ArrayList<>();
        ArrayList<Integer> even = new ArrayList<>();

        while (true) {
            int x = sc.nextInt();

            if (x == -1) {
                break;
            }

            if (x % 2 == 0) {
                even.add(x);
            } else {
                odd.add(x);
            }
        }

        for (int x : odd) {
            System.out.print(x + " ");
        }

        for (int x : even) {
            System.out.print(x + " ");
        }

        sc.close();
    }
}