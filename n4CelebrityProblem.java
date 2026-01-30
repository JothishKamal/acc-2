import java.io.*;
import java.util.*;

public class n4CelebrityProblem {
    static int findCeleb(int[][] m, int n) {
        int candidate = 0;

        for (int i = 1; i < n; i++) {
            if (m[candidate][i] == 1) {
                candidate = i;
            }
        }

        for (int i = 0; i < n; i++) {
            if (i != candidate) {
                if (m[candidate][i] == 1 || m[i][candidate] == 0) {
                    return -1;
                }
            }
        }

        return candidate;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] m = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = sc.nextInt();
            }
        }

        int res = findCeleb(m, n);

        if (res == -1) {
            System.out.print("No Celebrity");
        } else {
            System.out.print(res);
        }

        sc.close();
    }
}