import java.io.*;
import java.util.*;

public class n5TowerOfHanoi {
    static void toh(int n, char src, char aux, char dst) {
        if (n == 0)
            return;

        toh(n - 1, src, dst, aux);

        System.out.println(src + " " + dst);

        toh(n - 1, aux, src, dst);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        toh(n, 'a', 'b', 'c');
        sc.close();
    }
}