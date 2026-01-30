import java.io.*;
import java.util.*;

public class n1CycleDetection {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        HashSet<Integer> seen = new HashSet<>();

        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            if (seen.contains(x)) {
                System.out.print("YES");
                System.exit(0);
            }
            seen.add(x);
        }
        System.out.print("NO");
        sc.close();
    }
}