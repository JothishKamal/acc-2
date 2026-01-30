import java.util.*;

public class n9SortWithoutExtraSpace {

    static int getMin(Queue<Integer> q, int k) {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < q.size(); i++) {
            int x = q.poll();

            if (i < k && x < min) {
                min = x;
            }
            q.offer(x);
        }
        return min;
    }

    static void moveMinToRear(Queue<Integer> q, int min, int k) {
        boolean skipped = false;

        for (int i = 0; i < q.size(); i++) {
            int x = q.poll();

            if (i < k && x == min && !skipped) {
                skipped = true;
            } else {
                q.offer(x);
            }
        }
        q.offer(min);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            q.offer(sc.nextInt());
        }

        for (int i = 0; i < n; i++) {
            int min = getMin(q, n - i);
            moveMinToRear(q, min, n - i);
        }

        while (!q.isEmpty()) {
            System.out.print(q.poll() + " ");
        }

        sc.close();
    }
}

// OR

public class n9SortWithoutExtraSpace {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(sc.nextInt());
        }

        Collections.sort(list);

        for (int x : list) {
            System.out.print(x + " ");
        }

        sc.close();
    }
}
