import java.util.*;

// ============================================================
// FAT — COMPLETE SOLUTIONS
// Sections: Linked List | Stack & Queue | Trees | Graphs |
// Heaps | Maps/Sets/Hashing | DP | Interview Prep
// ============================================================

public class FATSolutions {
    /* entry point kept intentionally empty */
}

// ╔══════════════════════════════════════════════════════════╗
// ║ SECTION 1 — LINKED LIST                                  ║
// ╚══════════════════════════════════════════════════════════╝

class ListNode {
    int val;
    ListNode next, prev;

    ListNode(int x) {
        val = x;
    }
}

class LinkedListSolutions {

    // ── 1.1 Loop Detection — Floyd's Cycle Detection ────────
    /** Returns true if the singly-linked list contains a cycle. */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    /** Returns the node where the cycle begins, or null if no cycle. */
    public ListNode detectCycleStart(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow; // meeting point = cycle start
            }
        }
        return null;
    }

    // ── 1.2 Sort Bitonic DLL ────────────────────────────────
    /**
     * A bitonic DLL increases then decreases.
     * Strategy: split at peak, reverse the descending half,
     * then merge two sorted DLLs.
     */
    public ListNode sortBitonicDLL(ListNode head) {
        if (head == null || head.next == null)
            return head;

        // Find peak
        ListNode peak = head;
        while (peak.next != null && peak.val <= peak.next.val)
            peak = peak.next;

        // Split
        ListNode right = peak.next;
        peak.next = null;
        if (right != null)
            right.prev = null;

        // Reverse descending half → ascending
        right = reverseDLL(right);

        // Merge two ascending DLLs
        return mergeSortedDLL(head, right);
    }

    private ListNode reverseDLL(ListNode head) {
        ListNode curr = head, newHead = null;
        while (curr != null) {
            ListNode temp = curr.prev;
            curr.prev = curr.next;
            curr.next = temp;
            newHead = curr;
            curr = curr.prev; // old next after swap
        }
        return newHead;
    }

    private ListNode mergeSortedDLL(ListNode a, ListNode b) {
        if (a == null)
            return b;
        if (b == null)
            return a;
        if (a.val <= b.val) {
            a.next = mergeSortedDLL(a.next, b);
            if (a.next != null)
                a.next.prev = a;
            a.prev = null;
            return a;
        } else {
            b.next = mergeSortedDLL(a, b.next);
            if (b.next != null)
                b.next.prev = b;
            b.prev = null;
            return b;
        }
    }

    // ── 1.3 Segregate Even & Odd Nodes ─────────────────────
    /**
     * Rearranges nodes so all even-valued nodes come before
     * odd-valued nodes, preserving relative order within each group.
     */
    public ListNode segregateEvenOdd(ListNode head) {
        if (head == null)
            return null;
        ListNode evenHead = null, evenTail = null;
        ListNode oddHead = null, oddTail = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = null;
            if (curr.val % 2 == 0) {
                if (evenHead == null)
                    evenHead = evenTail = curr;
                else {
                    evenTail.next = curr;
                    evenTail = curr;
                }
            } else {
                if (oddHead == null)
                    oddHead = oddTail = curr;
                else {
                    oddTail.next = curr;
                    oddTail = curr;
                }
            }
            curr = next;
        }
        if (evenHead == null)
            return oddHead;
        evenTail.next = oddHead;
        return evenHead;
    }

    // ── 1.4 Merge Sort for DLL ──────────────────────────────
    /** Sorts a doubly-linked list using the merge sort algorithm. O(n log n). */
    public ListNode mergeSortDLL(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode mid = getMiddle(head);
        ListNode right = mid.next;
        mid.next = null;
        if (right != null)
            right.prev = null;
        ListNode l = mergeSortDLL(head);
        ListNode r = mergeSortDLL(right);
        return mergeSortedDLL(l, r);
    }

    private ListNode getMiddle(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // ── 1.6 Celebrity Problem ───────────────────────────────
    /**
     * A celebrity is known by everyone but knows nobody.
     * matrix[i][j] == 1 means i knows j.
     * Uses a two-pass O(n) approach.
     */
    public int findCelebrity(int[][] matrix, int n) {
        // Pass 1: find candidate
        int candidate = 0;
        for (int i = 1; i < n; i++)
            if (matrix[candidate][i] == 1)
                candidate = i; // candidate knows i → can't be celebrity

        // Pass 2: verify candidate
        for (int i = 0; i < n; i++) {
            if (i == candidate)
                continue;
            // Candidate must not know i, and i must know candidate
            if (matrix[candidate][i] == 1 || matrix[i][candidate] == 0)
                return -1;
        }
        return candidate;
    }
}

// ── 1.5 Minimum Stack ───────────────────────────────────────
/**
 * Stack that supports push, pop, top and getMin — all in O(1).
 * A parallel minStack tracks the running minimum.
 */
class MinStack {
    private final Stack<Integer> stack = new Stack<>();
    private final Stack<Integer> minStack = new Stack<>();

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek())
            minStack.push(val);
    }

    public void pop() {
        if (stack.isEmpty())
            return;
        if (stack.pop().equals(minStack.peek()))
            minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}

// ╔══════════════════════════════════════════════════════════╗
// ║ SECTION 2 — STACK AND QUEUE                              ║
// ╚══════════════════════════════════════════════════════════╝

class StackQueueSolutions {

    // ── 2.1 Iterative Tower of Hanoi ────────────────────────
    /**
     * Solves Tower of Hanoi iteratively using three explicit stacks.
     * Pattern: for odd n → cycle (src→dest, src→aux, dest→aux);
     * for even n → swap dest & aux first.
     * Total moves = 2^n − 1.
     */
    public void hanoiIterative(int n, char src, char dest, char aux) {
        // For even n, swap dest and aux to keep the direction correct
        if (n % 2 == 0) {
            char t = dest;
            dest = aux;
            aux = t;
        }

        Stack<Integer> stkSrc = new Stack<>();
        Stack<Integer> stkDest = new Stack<>();
        Stack<Integer> stkAux = new Stack<>();

        for (int i = n; i >= 1; i--)
            stkSrc.push(i);

        int totalMoves = (1 << n) - 1;
        char fSrc = src, fDest = dest, fAux = aux; // effectively final for lambda

        System.out.println("Tower of Hanoi — " + n + " disks (" + totalMoves + " moves):");
        for (int move = 1; move <= totalMoves; move++) {
            if (move % 3 == 1)
                moveBetween(stkSrc, stkDest, src, dest);
            else if (move % 3 == 2)
                moveBetween(stkSrc, stkAux, src, aux);
            else
                moveBetween(stkDest, stkAux, dest, aux);
        }
    }

    /** Moves the smaller top disk from one peg to another. */
    private void moveBetween(Stack<Integer> a, Stack<Integer> b, char nameA, char nameB) {
        if (a.isEmpty() || (!b.isEmpty() && b.peek() < a.peek())) {
            a.push(b.pop());
            System.out.println("  Move disk " + a.peek() + ": " + nameB + " → " + nameA);
        } else {
            b.push(a.pop());
            System.out.println("  Move disk " + b.peek() + ": " + nameA + " → " + nameB);
        }
    }

    // ── 2.2 Stock Span Problem ──────────────────────────────
    /**
     * For each day i, the span is the number of consecutive days
     * (including day i) for which the price was ≤ prices[i].
     * Uses a monotonic stack of indices. O(n).
     */
    public int[] stockSpan(int[] prices) {
        int n = prices.length;
        int[] span = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && prices[st.peek()] <= prices[i])
                st.pop();
            span[i] = st.isEmpty() ? i + 1 : i - st.peek();
            st.push(i);
        }
        return span;
    }

    // ── 2.4 Sort Stack Without Extra Space ──────────────────
    /**
     * Sorts a stack in ascending order (top = smallest) using
     * only the call stack — no auxiliary data structure.
     */
    public void sortStack(Stack<Integer> st) {
        if (!st.isEmpty()) {
            int top = st.pop();
            sortStack(st);
            insertSorted(st, top);
        }
    }

    private void insertSorted(Stack<Integer> st, int val) {
        if (st.isEmpty() || st.peek() <= val) {
            st.push(val);
            return;
        }
        int top = st.pop();
        insertSorted(st, val);
        st.push(top);
    }

    // ── 2.5 Max Sliding Window ──────────────────────────────
    /**
     * Returns the maximum value in each window of size k.
     * Uses a monotonic deque to achieve O(n) overall.
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>(); // stores indices

        for (int i = 0; i < n; i++) {
            // Remove indices outside the current window
            while (!dq.isEmpty() && dq.peekFirst() < i - k + 1)
                dq.pollFirst();
            // Maintain decreasing order — remove smaller elements from back
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i])
                dq.pollLast();
            dq.addLast(i);
            if (i >= k - 1)
                res[i - k + 1] = nums[dq.peekFirst()];
        }
        return res;
    }

    // ── 2.6 Stack Permutations ──────────────────────────────
    /**
     * Checks whether 'output' is a valid stack permutation of 'input'.
     * Push elements from input one by one; pop whenever the top
     * matches the next expected output element.
     */
    public boolean isStackPermutation(int[] input, int[] output) {
        Stack<Integer> st = new Stack<>();
        int j = 0;
        for (int val : input) {
            st.push(val);
            while (!st.isEmpty() && j < output.length && st.peek() == output[j]) {
                st.pop();
                j++;
            }
        }
        return st.isEmpty();
    }
}

// ── 2.3 Priority Queue using DLL ────────────────────────────
class PQNode {
    int val, priority;
    PQNode next, prev;

    PQNode(int v, int p) {
        val = v;
        priority = p;
    }
}

/**
 * Max-priority queue backed by a sorted doubly-linked list.
 * Enqueue: O(n) — insert in sorted position by priority.
 * Dequeue: O(1) — always remove from head (highest priority).
 */
class PriorityQueueDLL {
    PQNode head;

    public void enqueue(int val, int priority) {
        PQNode node = new PQNode(val, priority);
        if (head == null || priority > head.priority) {
            node.next = head;
            if (head != null)
                head.prev = node;
            head = node;
            return;
        }
        PQNode curr = head;
        while (curr.next != null && curr.next.priority >= priority)
            curr = curr.next;
        node.next = curr.next;
        node.prev = curr;
        if (curr.next != null)
            curr.next.prev = node;
        curr.next = node;
    }

    public int dequeue() {
        if (head == null)
            throw new NoSuchElementException("Queue is empty");
        int val = head.val;
        head = head.next;
        if (head != null)
            head.prev = null;
        return val;
    }

    public int peek() {
        if (head == null)
            throw new NoSuchElementException("Queue is empty");
        return head.val;
    }

    public boolean isEmpty() {
        return head == null;
    }
}

// ╔══════════════════════════════════════════════════════════╗
// ║ SECTION 3 — TREES (+ Dial's Algorithm)                   ║
// ╚══════════════════════════════════════════════════════════╝

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int x) {
        val = x;
    }
}

class TreeSolutions {
    TreeNode first, second, prev;

    // ── 3.1 Recover the BST ─────────────────────────────────
    /**
     * Two nodes of a BST are swapped. This restores the tree
     * using Morris-style in-order traversal. O(n) time, O(h) space.
     */
    public void recoverTree(TreeNode root) {
        first = second = null;
        prev = new TreeNode(Integer.MIN_VALUE);
        inorder(root);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    private void inorder(TreeNode root) {
        if (root == null)
            return;
        inorder(root.left);
        if (first == null && prev.val >= root.val)
            first = prev;
        if (first != null && prev.val >= root.val)
            second = root;
        prev = root;
        inorder(root.right);
    }

    // ── 3.2 Views of Tree ───────────────────────────────────
    /** Right view: the last node visible at each level (BFS). */
    public List<Integer> rightView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = q.poll();
                if (i == size - 1)
                    res.add(curr.val);
                if (curr.left != null)
                    q.add(curr.left);
                if (curr.right != null)
                    q.add(curr.right);
            }
        }
        return res;
    }

    /** Left view: the first node visible at each level (BFS). */
    public List<Integer> leftView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = q.poll();
                if (i == 0)
                    res.add(curr.val);
                if (curr.left != null)
                    q.add(curr.left);
                if (curr.right != null)
                    q.add(curr.right);
            }
        }
        return res;
    }

    /** Top view: first node at each horizontal distance (BFS + HD map). */
    public List<Integer> topView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<Object[]> q = new LinkedList<>();
        q.add(new Object[] { root, 0 });
        while (!q.isEmpty()) {
            Object[] cur = q.poll();
            TreeNode node = (TreeNode) cur[0];
            int hd = (int) cur[1];
            if (!map.containsKey(hd))
                map.put(hd, node.val);
            if (node.left != null)
                q.add(new Object[] { node.left, hd - 1 });
            if (node.right != null)
                q.add(new Object[] { node.right, hd + 1 });
        }
        res.addAll(map.values());
        return res;
    }

    /** Bottom view: last node at each horizontal distance (BFS + HD map). */
    public List<Integer> bottomView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        Map<Integer, Integer> map = new TreeMap<>();
        Queue<Object[]> q = new LinkedList<>();
        q.add(new Object[] { root, 0 });
        while (!q.isEmpty()) {
            Object[] cur = q.poll();
            TreeNode node = (TreeNode) cur[0];
            int hd = (int) cur[1];
            map.put(hd, node.val); // always overwrite → last seen = bottom
            if (node.left != null)
                q.add(new Object[] { node.left, hd - 1 });
            if (node.right != null)
                q.add(new Object[] { node.right, hd + 1 });
        }
        res.addAll(map.values());
        return res;
    }

    // ── 3.3 Vertical Order Traversal ────────────────────────
    /** Groups nodes by horizontal distance; within each column, top-down. */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        Queue<Object[]> q = new LinkedList<>();
        q.add(new Object[] { root, 0 });
        while (!q.isEmpty()) {
            Object[] cur = q.poll();
            TreeNode node = (TreeNode) cur[0];
            int hd = (int) cur[1];
            map.computeIfAbsent(hd, k -> new ArrayList<>()).add(node.val);
            if (node.left != null)
                q.add(new Object[] { node.left, hd - 1 });
            if (node.right != null)
                q.add(new Object[] { node.right, hd + 1 });
        }
        res.addAll(map.values());
        return res;
    }

    // ── 3.4 Boundary Traversal ──────────────────────────────
    /**
     * Anti-clockwise boundary: root → left boundary → leaves → right boundary
     * (reversed).
     */
    public List<Integer> boundaryTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        if (!isLeaf(root))
            res.add(root.val);
        addLeftBoundary(root.left, res);
        addLeaves(root, res);
        addRightBoundary(root.right, res);
        return res;
    }

    private boolean isLeaf(TreeNode n) {
        return n.left == null && n.right == null;
    }

    private void addLeftBoundary(TreeNode n, List<Integer> res) {
        while (n != null) {
            if (!isLeaf(n))
                res.add(n.val);
            n = (n.left != null) ? n.left : n.right;
        }
    }

    private void addLeaves(TreeNode n, List<Integer> res) {
        if (n == null)
            return;
        if (isLeaf(n)) {
            res.add(n.val);
            return;
        }
        addLeaves(n.left, res);
        addLeaves(n.right, res);
    }

    private void addRightBoundary(TreeNode n, List<Integer> res) {
        Stack<Integer> st = new Stack<>();
        while (n != null) {
            if (!isLeaf(n))
                st.push(n.val);
            n = (n.right != null) ? n.right : n.left;
        }
        while (!st.isEmpty())
            res.add(st.pop());
    }

    // ── Helper: build tree from level-order array ─────────────
    public TreeNode buildFromLevelOrder(Integer[] arr) {
        if (arr == null || arr.length == 0)
            return null;
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (i < arr.length) {
            TreeNode parent = q.poll();
            if (arr[i] != null) {
                parent.left = new TreeNode(arr[i]);
                q.add(parent.left);
            }
            i++;
            if (i < arr.length && arr[i] != null) {
                parent.right = new TreeNode(arr[i]);
                q.add(parent.right);
            }
            i++;
        }
        return root;
    }
}

// ── 3.5 + 3.6 BFS, DFS & Dial's Algorithm ──────────────────
/**
 * Dial's Algorithm — a bucket-based shortest-path algorithm
 * efficient when edge weights are small integers (0 to W).
 * Time: O(V·W + E) vs Dijkstra's O((V+E) log V).
 */
class DialAlgorithm {
    public int[] dial(List<List<int[]>> adj, int src, int W, int V) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        int bucketCount = W * V + 1;
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++)
            buckets.add(new ArrayList<>());
        buckets.get(0).add(src);

        int idx = 0;
        while (idx < bucketCount) {
            // Skip empty buckets
            while (idx < bucketCount && buckets.get(idx).isEmpty())
                idx++;
            if (idx == bucketCount)
                break;

            List<Integer> bucket = buckets.get(idx);
            int u = bucket.remove(bucket.size() - 1);

            for (int[] edge : adj.get(u)) {
                int v = edge[0], w = edge[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    buckets.get(dist[v]).add(v);
                }
            }
        }
        return dist;
    }
}

// ╔══════════════════════════════════════════════════════════╗
// ║ SECTION 4 — GRAPHS                                       ║
// ╚══════════════════════════════════════════════════════════╝

class GraphSolutions {
    int V;
    List<List<int[]>> adj; // adj[u] = list of {v, weight}

    GraphSolutions(int v) {
        V = v;
        adj = new ArrayList<>();
        for (int i = 0; i < v; i++)
            adj.add(new ArrayList<>());
    }

    // ── 3.5 BFS ─────────────────────────────────────────────
    /** Breadth-First Search from source s. Visits all reachable vertices. */
    public List<Integer> BFS(int s) {
        boolean[] vis = new boolean[V];
        List<Integer> order = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        vis[s] = true;
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int[] edge : adj.get(u))
                if (!vis[edge[0]]) {
                    vis[edge[0]] = true;
                    q.add(edge[0]);
                }
        }
        return order;
    }

    // ── 3.5 DFS ─────────────────────────────────────────────
    /** Depth-First Search from vertex u (recursive). */
    public void DFS(int u, boolean[] vis) {
        vis[u] = true;
        for (int[] edge : adj.get(u))
            if (!vis[edge[0]])
                DFS(edge[0], vis);
    }

    // ── 4.1 Dijkstra's Algorithm ────────────────────────────
    /** Single-source shortest paths for non-negative weights. O((V+E) log V). */
    public int[] dijkstra(int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[] { src, 0 });
        while (!pq.isEmpty()) {
            int[] c = pq.poll();
            if (c[1] > dist[c[0]])
                continue;
            for (int[] nb : adj.get(c[0])) {
                if (dist[c[0]] + nb[1] < dist[nb[0]]) {
                    dist[nb[0]] = dist[c[0]] + nb[1];
                    pq.add(new int[] { nb[0], dist[nb[0]] });
                }
            }
        }
        return dist;
    }

    // ── 4.1 Bellman-Ford Algorithm ──────────────────────────
    /**
     * Single-source shortest paths allowing negative-weight edges.
     * Detects negative-weight cycles. O(V · E).
     * edges[][]: {from, to, weight}
     */
    public int[] bellmanFord(int src, int[][] edges) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Relax all edges V-1 times
        for (int i = 0; i < V - 1; i++)
            for (int[] e : edges)
                if (dist[e[0]] != Integer.MAX_VALUE && dist[e[0]] + e[2] < dist[e[1]])
                    dist[e[1]] = dist[e[0]] + e[2];

        // Check for negative-weight cycles (V-th relaxation)
        for (int[] e : edges)
            if (dist[e[0]] != Integer.MAX_VALUE && dist[e[0]] + e[2] < dist[e[1]])
                System.out.println("Graph contains a negative-weight cycle");

        return dist;
    }

    // ── 4.2 Topological Sort (Kahn's BFS Algorithm) ─────────
    /**
     * Returns a topological ordering of a DAG.
     * Returns an empty list if the graph has a cycle.
     */
    public List<Integer> topoSort() {
        int[] inDegree = new int[V];
        for (int i = 0; i < V; i++)
            for (int[] e : adj.get(i))
                inDegree[e[0]]++;

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++)
            if (inDegree[i] == 0)
                q.add(i);

        List<Integer> res = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            res.add(u);
            for (int[] e : adj.get(u))
                if (--inDegree[e[0]] == 0)
                    q.add(e[0]);
        }
        return res.size() == V ? res : new ArrayList<>(); // empty → cycle detected
    }
}

// ╔══════════════════════════════════════════════════════════╗
// ║ SECTION 5 — HEAPS                                        ║
// ╚══════════════════════════════════════════════════════════╝

// ── 4.3 / 5.3 Heap Sort ─────────────────────────────────────
class HeapSolutions {
    /** In-place ascending sort using a max-heap. O(n log n). */
    public void heapSort(int[] a) {
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(a, n, i);
        for (int i = n - 1; i > 0; i--) {
            int t = a[0];
            a[0] = a[i];
            a[i] = t;
            heapify(a, i, 0);
        }
    }

    private void heapify(int[] a, int n, int i) {
        int largest = i, l = 2 * i + 1, r = 2 * i + 2;
        if (l < n && a[l] > a[largest])
            largest = l;
        if (r < n && a[r] > a[largest])
            largest = r;
        if (largest != i) {
            int t = a[i];
            a[i] = a[largest];
            a[largest] = t;
            heapify(a, n, largest);
        }
    }
}

// ── 5.1 Binomial Heap ───────────────────────────────────────
class BinomialHeapNode {
    int key, degree;
    BinomialHeapNode parent, child, sibling;

    BinomialHeapNode(int k) {
        key = k;
    }
}

/**
 * Binomial Min-Heap — a collection of binomial trees.
 * insert: O(log n)
 * getMin: O(log n)
 * extractMin: O(log n)
 * union: O(log n)
 */
class BinomialHeap {
    BinomialHeapNode head;

    /** Links two binomial trees of equal degree (y becomes child of z). */
    private void link(BinomialHeapNode y, BinomialHeapNode z) {
        y.parent = z;
        y.sibling = z.child;
        z.child = y;
        z.degree++;
    }

    /** Merges root lists of two heaps in non-decreasing degree order. */
    private BinomialHeapNode mergeRoots(BinomialHeapNode h1, BinomialHeapNode h2) {
        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;
        BinomialHeapNode head, curr;
        if (h1.degree <= h2.degree) {
            head = h1;
            h1 = h1.sibling;
        } else {
            head = h2;
            h2 = h2.sibling;
        }
        curr = head;
        while (h1 != null && h2 != null) {
            if (h1.degree <= h2.degree) {
                curr.sibling = h1;
                h1 = h1.sibling;
            } else {
                curr.sibling = h2;
                h2 = h2.sibling;
            }
            curr = curr.sibling;
        }
        curr.sibling = (h1 != null) ? h1 : h2;
        return head;
    }

    /** Unions this heap with another and returns the merged heap. */
    public BinomialHeap union(BinomialHeap other) {
        BinomialHeap result = new BinomialHeap();
        result.head = mergeRoots(this.head, other.head);
        if (result.head == null)
            return result;

        BinomialHeapNode prev = null, curr = result.head, next = curr.sibling;
        while (next != null) {
            boolean sameDegreePair = curr.degree == next.degree;
            boolean nextHasTwin = next.sibling != null && next.sibling.degree == curr.degree;
            if (!sameDegreePair || nextHasTwin) {
                prev = curr;
                curr = next;
            } else if (curr.key <= next.key) {
                curr.sibling = next.sibling;
                link(next, curr);
            } else {
                if (prev == null)
                    result.head = next;
                else
                    prev.sibling = next;
                link(curr, next);
                curr = next;
            }
            next = curr.sibling;
        }
        return result;
    }

    public void insert(int key) {
        BinomialHeap tmp = new BinomialHeap();
        tmp.head = new BinomialHeapNode(key);
        BinomialHeap merged = this.union(tmp);
        this.head = merged.head;
    }

    public int getMin() {
        if (head == null)
            throw new NoSuchElementException();
        int min = Integer.MAX_VALUE;
        for (BinomialHeapNode curr = head; curr != null; curr = curr.sibling)
            if (curr.key < min)
                min = curr.key;
        return min;
    }

    public int extractMin() {
        if (head == null)
            throw new NoSuchElementException();
        // Find node with minimum key
        BinomialHeapNode minPrev = null, minNode = head, prev = null, curr = head;
        while (curr.sibling != null) {
            if (curr.sibling.key < minNode.key) {
                minPrev = curr;
                minNode = curr.sibling;
            }
            curr = curr.sibling;
        }
        // Remove minNode from root list
        if (minPrev == null)
            head = minNode.sibling;
        else
            minPrev.sibling = minNode.sibling;

        // Reverse children of minNode and make them a new heap
        BinomialHeapNode child = minNode.child, newHead = null;
        while (child != null) {
            BinomialHeapNode next = child.sibling;
            child.sibling = newHead;
            child.parent = null;
            newHead = child;
            child = next;
        }
        BinomialHeap tmp = new BinomialHeap();
        tmp.head = newHead;
        BinomialHeap merged = this.union(tmp);
        this.head = merged.head;
        return minNode.key;
    }
}

// ── 5.2 K-ary Min-Heap ──────────────────────────────────────
/**
 * A min-heap where each node has at most k children.
 * Parent of index i → (i - 1) / k
 * j-th child of i → k*i + j (j = 1..k)
 */
class KaryHeap {
    int[] heap;
    int sz, k;

    KaryHeap(int capacity, int k) {
        heap = new int[capacity];
        this.k = k;
    }

    public void insert(int x) {
        heap[sz] = x;
        int i = sz++;
        while (i > 0) {
            int parent = (i - 1) / k;
            if (heap[i] < heap[parent]) {
                int t = heap[i];
                heap[i] = heap[parent];
                heap[parent] = t;
                i = parent;
            } else
                break;
        }
    }

    public int extractMin() {
        int min = heap[0];
        heap[0] = heap[--sz];
        heapifyDown(0);
        return min;
    }

    private void heapifyDown(int i) {
        int smallest = i;
        for (int j = 1; j <= k; j++) {
            int child = k * i + j;
            if (child < sz && heap[child] < heap[smallest])
                smallest = child;
        }
        if (smallest != i) {
            int t = heap[i];
            heap[i] = heap[smallest];
            heap[smallest] = t;
            heapifyDown(smallest);
        }
    }
}

// ── 5.3 Winner Tree ─────────────────────────────────────────
/**
 * A complete binary tree where each internal node stores the index
 * of the winner (smaller value) of its two children.
 * Used in external sorting and multi-way merges.
 */
class WinnerTree {
    int[] tree, players;

    WinnerTree(int[] p) {
        players = p;
        tree = new int[2 * p.length - 1];
        build(0, 0, p.length - 1);
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = l;
            return;
        }
        int mid = (l + r) / 2;
        build(2 * node + 1, l, mid);
        build(2 * node + 2, mid + 1, r);
        tree[node] = players[tree[2 * node + 1]] <= players[tree[2 * node + 2]]
                ? tree[2 * node + 1]
                : tree[2 * node + 2];
    }

    /** Returns the index of the overall winner (minimum). */
    public int getWinner() {
        return tree[0];
    }
}

// ╔══════════════════════════════════════════════════════════╗
// ║ SECTION 6 — MAPS, SETS AND HASHING                       ║
// ╚══════════════════════════════════════════════════════════╝

class HashingSolutions {

    // ── 6.1 HashMap → TreeMap ───────────────────────────────
    /**
     * Converts a HashMap to a TreeMap so keys are in
     * natural sorted order. O(n log n).
     */
    public <K extends Comparable<K>, V> TreeMap<K, V> hashMapToTreeMap(HashMap<K, V> map) {
        return new TreeMap<>(map);
    }

    // ── 6.2 Types of Sets ───────────────────────────────────
    /**
     * Three main Set implementations:
     * HashSet — unordered, O(1) avg add/remove/contains
     * LinkedHashSet — insertion-order preserved, O(1) avg
     * TreeSet — sorted (natural/comparator), O(log n)
     */
    public void demonstrateSets() {
        Set<Integer> hashSet = new HashSet<>(Arrays.asList(5, 3, 1, 4, 1, 5));
        Set<Integer> linkedSet = new LinkedHashSet<>(Arrays.asList(5, 3, 1, 4, 1, 5));
        Set<Integer> treeSet = new TreeSet<>(Arrays.asList(5, 3, 1, 4, 1, 5));

        System.out.println("HashSet   (no order): " + hashSet);
        System.out.println("LinkedSet (insertion): " + linkedSet);
        System.out.println("TreeSet   (sorted):   " + treeSet);

        // NavigableSet operations on TreeSet
        TreeSet<Integer> nav = new TreeSet<>(treeSet);
        System.out.println("Floor of 3: " + nav.floor(3)); // ≤ 3
        System.out.println("Ceiling of 2: " + nav.ceiling(2)); // ≥ 2
        System.out.println("Subset [1,4]: " + nav.subSet(1, true, 4, true));
    }

    // ── 6.3 Distributing Items (max 2 of same type per person) ─
    /**
     * Checks whether n items can be distributed among 'persons' people
     * such that no person receives more than 2 items of the same type.
     * items[] holds item type identifiers.
     */
    public boolean canDistribute(int[] items, int persons) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int item : items)
            freq.merge(item, 1, Integer::sum);
        for (int count : freq.values())
            if (count > 2 * persons)
                return false; // can't place all of this type
        return true;
    }

    // ── 6.4 Fibonacci (Bottom-up DP / memoization intro) ────
    public long fibonacci(int n) {
        if (n <= 1)
            return n;
        long[] dp = new long[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++)
            dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n];
    }

    // ── 6.5 Longest Common Subsequence ──────────────────────
    /** Classic DP — O(m·n) time and space. */
    public int lcs(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1)
                        ? dp[i - 1][j - 1] + 1
                        : Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[m][n];
    }

    // ── 6.6 Longest Increasing Subsequence (Patience Sorting) ─
    /** O(n log n) using binary search on a tails array. */
    public int lis(int[] nums) {
        List<Integer> tails = new ArrayList<>();
        for (int num : nums) {
            int lo = 0, hi = tails.size();
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (tails.get(mid) < num)
                    lo = mid + 1;
                else
                    hi = mid;
            }
            if (lo == tails.size())
                tails.add(num);
            else
                tails.set(lo, num);
        }
        return tails.size();
    }
}

// ╔══════════════════════════════════════════════════════════╗
// ║ SECTION 7 — DYNAMIC PROGRAMMING                          ║
// ╚══════════════════════════════════════════════════════════╝

class DPSolutions {

    // ── 7.1 Longest Bitonic Subsequence ─────────────────────
    /**
     * A subsequence that first increases then decreases.
     * Computes LIS from left (lis[]) and LDS from right (lds[]).
     * Answer = max(lis[i] + lds[i] - 1).
     */
    public int longestBitonicSubseq(int[] arr) {
        int n = arr.length;
        int[] lis = new int[n], lds = new int[n];
        Arrays.fill(lis, 1);
        Arrays.fill(lds, 1);

        for (int i = 1; i < n; i++)
            for (int j = 0; j < i; j++)
                if (arr[j] < arr[i])
                    lis[i] = Math.max(lis[i], lis[j] + 1);

        for (int i = n - 2; i >= 0; i--)
            for (int j = i + 1; j < n; j++)
                if (arr[j] < arr[i])
                    lds[i] = Math.max(lds[i], lds[j] + 1);

        int max = 0;
        for (int i = 0; i < n; i++)
            max = Math.max(max, lis[i] + lds[i] - 1);
        return max;
    }

    // ── 7.2 Longest Palindromic Subsequence ─────────────────
    /** Equivalent to LCS(s, reverse(s)). Solved directly with interval DP. */
    public int longestPalindromicSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++)
            dp[i][i] = 1;
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp[i][j] = s.charAt(i) == s.charAt(j)
                        ? dp[i + 1][j - 1] + 2
                        : Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        return dp[0][n - 1];
    }

    // ── 7.3 Subset Sum Problem ──────────────────────────────
    /** Returns true if any subset of arr[] sums to exactly target. */
    public boolean subsetSum(int[] arr, int target) {
        int n = arr.length;
        boolean[][] dp = new boolean[n + 1][target + 1];
        for (int i = 0; i <= n; i++)
            dp[i][0] = true; // empty subset sums to 0
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                if (arr[i - 1] <= j)
                    dp[i][j] |= dp[i - 1][j - arr[i - 1]];
            }
        return dp[n][target];
    }

    // ── 7.4 0-1 Knapsack ────────────────────────────────────
    /** Classic 0/1 knapsack: each item used at most once. O(n·W). */
    public int knapsack01(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++)
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];
                if (weights[i - 1] <= w)
                    dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
            }
        return dp[n][capacity];
    }

    // ── 7.5 Traveling Salesman Problem (Bitmask DP) ──────────
    /**
     * Finds the minimum cost Hamiltonian cycle starting and ending at vertex 0.
     * dp[mask][u] = min cost to visit exactly the vertices in 'mask',
     * ending at vertex u.
     * Time: O(2^n · n²) Space: O(2^n · n)
     */
    public int tsp(int[][] dist) {
        int n = dist.length;
        int INF = Integer.MAX_VALUE / 2;
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp)
            Arrays.fill(row, INF);
        dp[1][0] = 0; // Start at vertex 0, only vertex 0 visited

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0 || dp[mask][u] == INF)
                    continue;
                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0)
                        continue;
                    int newMask = mask | (1 << v);
                    dp[newMask][v] = Math.min(dp[newMask][v], dp[mask][u] + dist[u][v]);
                }
            }
        }

        int fullMask = (1 << n) - 1, res = INF;
        for (int u = 1; u < n; u++)
            if (dp[fullMask][u] != INF)
                res = Math.min(res, dp[fullMask][u] + dist[u][0]);
        return res;
    }

    // ── 7.6 Coin Change (Minimum Coins) ─────────────────────
    /** Returns the fewest coins needed to make up 'amount', or -1 if impossible. */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++)
            for (int coin : coins)
                if (coin <= i)
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ── 7.7 Shortest Common Supersequence ───────────────────
    /**
     * Length of the shortest string that has both s1 and s2 as subsequences.
     * SCS length = len(s1) + len(s2) - LCS length.
     */
    public int shortestCommonSuperseq(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++)
            dp[i][0] = i;
        for (int j = 0; j <= n; j++)
            dp[0][j] = j;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1)
                        ? dp[i - 1][j - 1] + 1
                        : Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
        return dp[m][n];
    }

    // ── 7.8 Levenshtein Edit Distance ───────────────────────
    /**
     * Minimum single-character edits (insert, delete, replace)
     * needed to transform s1 into s2.
     */
    public int editDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++)
            dp[i][0] = i;
        for (int j = 0; j <= n; j++)
            dp[0][j] = j;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1]));
            }
        return dp[m][n];
    }
}

// ╔══════════════════════════════════════════════════════════╗
// ║ SECTION 8 — PRODUCT COMPANY INTERVIEW PREP               ║
// ╚══════════════════════════════════════════════════════════╝

class InterviewPrep {

    // ── 8.1 Rod Cutting Problem ─────────────────────────────
    /**
     * Given a rod of length n and a price array (price[i] = price of length i+1),
     * find the maximum revenue by cutting the rod.
     * Unbounded knapsack variant. O(n²).
     */
    public int rodCutting(int[] prices, int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++)
            for (int cut = 1; cut <= i; cut++)
                dp[i] = Math.max(dp[i], prices[cut - 1] + dp[i - cut]);
        return dp[n];
    }

    // ── 8.2 Wildcard Pattern Matching ───────────────────────
    /**
     * Returns true if string s matches pattern p.
     * '?' matches any single character.
     * '*' matches any sequence (including empty).
     */
    public boolean wildcardMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 1; j <= n; j++)
            if (p.charAt(j - 1) == '*')
                dp[0][j] = dp[0][j - 1];

        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                if (pc == '*')
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1]; // use or skip '*'
                else if (pc == '?' || s.charAt(i - 1) == pc)
                    dp[i][j] = dp[i - 1][j - 1];
                // else dp[i][j] remains false
            }
        return dp[m][n];
    }

    // ── 8.3 Pots of Gold Game (Interval DP) ─────────────────
    /**
     * Two players alternately pick from either end of a row of pots.
     * Both play optimally. Returns the maximum gold the first player can collect.
     *
     * dp[i][j] = max gold player 1 can collect from subarray pots[i..j].
     * When player 1 picks left (pots[i]), player 2 plays optimally on the rest
     * and will leave player 1 the minimum of the two sub-problems.
     */
    public int potsOfGold(int[] pots) {
        int n = pots.length;
        int[][] dp = new int[n][n];

        // Base case: single pot
        for (int i = 0; i < n; i++)
            dp[i][i] = pots[i];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;

                // Player picks left → opponent faces [i+1..j]
                // Opponent leaves minimum sub-problem for player
                int afterPickLeft = Math.min(
                        (i + 2 <= j) ? dp[i + 2][j] : 0,
                        (i + 1 <= j - 1) ? dp[i + 1][j - 1] : 0);
                // Player picks right → opponent faces [i..j-1]
                int afterPickRight = Math.min(
                        (i <= j - 2) ? dp[i][j - 2] : 0,
                        (i + 1 <= j - 1) ? dp[i + 1][j - 1] : 0);

                dp[i][j] = Math.max(pots[i] + afterPickLeft, pots[j] + afterPickRight);
            }
        }
        return dp[0][n - 1];
    }

    /*
     * ══════════════════════════════════════════════════════
     * 8.4 OS — KEY CONCEPTS FOR INTERVIEWS
     * ══════════════════════════════════════════════════════
     *
     * Process & Threads
     * -----------------
     * • Process: independent execution unit with its own memory space.
     * • Thread: lightweight unit within a process, sharing memory.
     * • Context Switch: saving/restoring state when the CPU switches tasks.
     *
     * CPU Scheduling Algorithms
     * -------------------------
     * • FCFS (First-Come First-Served) — non-preemptive, simple, convoy effect.
     * • SJF (Shortest Job First) — optimal average waiting time, needs burst time.
     * • SRTF — preemptive version of SJF.
     * • Round Robin — preemptive, time quantum; fair but many context switches.
     * • Priority Scheduling — can cause starvation; solved by aging.
     * • Multilevel Queue — separate queues per priority class.
     *
     * Deadlock
     * --------
     * Four necessary conditions (Coffman):
     * 1. Mutual Exclusion 2. Hold & Wait
     * 3. No Preemption 4. Circular Wait
     * Detection: Resource Allocation Graph (RAG).
     * Prevention: Banker's Algorithm (safe state guarantee).
     * Avoidance: Break one of the four conditions.
     *
     * Memory Management
     * -----------------
     * • Paging: fixed-size frames; eliminates external fragmentation.
     * • Segmentation: variable-size segments; logical view of memory.
     * • Virtual Memory / Demand Paging: only load pages when needed.
     * • Page Replacement: FIFO, LRU, Optimal (Belady's), Clock.
     * • Thrashing: excessive paging → CPU utilization drops; fix with
     * Working Set Model or Page-Fault Frequency.
     *
     * Synchronization
     * ---------------
     * • Race Condition: outcome depends on execution order.
     * • Critical Section: code accessing shared resource.
     * • Mutex: binary lock; only the owner can unlock.
     * • Semaphore: counting signal; wait (P) / signal (V).
     * • Monitor: high-level synchronization with condition variables.
     * • Peterson's Solution: software-based, 2-process mutual exclusion.
     *
     * IPC Mechanisms
     * --------------
     * Pipes | Named Pipes (FIFOs) | Message Queues | Shared Memory | Sockets.
     *
     * ══════════════════════════════════════════════════════
     * 8.5 DBMS — KEY CONCEPTS
     * ══════════════════════════════════════════════════════
     *
     * ACID Properties
     * ---------------
     * • Atomicity: transaction is all-or-nothing.
     * • Consistency: DB moves from one valid state to another.
     * • Isolation: concurrent transactions appear serial.
     * • Durability: committed data survives failures.
     *
     * Normalization
     * -------------
     * • 1NF: atomic values, no repeating groups.
     * • 2NF: 1NF + no partial dependency on composite PK.
     * • 3NF: 2NF + no transitive dependency on PK.
     * • BCNF: every determinant is a candidate key.
     * • 4NF: BCNF + no multi-valued dependencies.
     *
     * Indexing
     * --------
     * • B-Tree / B+ Tree: balanced, sorted; good for range queries.
     * • Hash Index: O(1) lookup; no range support.
     * • Clustered Index: data rows sorted with index (one per table).
     * • Non-Clustered: separate structure pointing to data rows.
     * • Covering Index: index includes all query columns.
     *
     * Transactions & Concurrency
     * --------------------------
     * • 2PL (Two-Phase Locking): growing phase → shrinking phase.
     * • MVCC (Multi-Version Concurrency Control): readers don't block writers.
     * • Isolation Levels: READ UNCOMMITTED, READ COMMITTED,
     * REPEATABLE READ, SERIALIZABLE.
     * • Anomalies: Dirty Read, Non-Repeatable Read, Phantom Read.
     *
     * Query Optimization
     * ------------------
     * • Cost-based optimizer uses statistics (histograms, cardinality).
     * • Join algorithms: Nested Loop, Sort-Merge, Hash Join.
     * • Execution plan: EXPLAIN / EXPLAIN ANALYZE.
     *
     * ══════════════════════════════════════════════════════
     * 8.6 RDBMS — KEY CONCEPTS
     * ══════════════════════════════════════════════════════
     *
     * Relational Model
     * ----------------
     * • Relation (Table), Tuple (Row), Attribute (Column).
     * • Keys: Super → Candidate → Primary → Foreign → Alternate.
     * • Constraints: NOT NULL, UNIQUE, CHECK, DEFAULT, FK references.
     *
     * SQL Categories
     * --------------
     * • DDL: CREATE, ALTER, DROP, TRUNCATE, RENAME.
     * • DML: SELECT, INSERT, UPDATE, DELETE.
     * • DCL: GRANT, REVOKE.
     * • TCL: COMMIT, ROLLBACK, SAVEPOINT.
     *
     * Joins
     * -----
     * • INNER JOIN — matching rows only.
     * • LEFT OUTER JOIN — all left + matching right.
     * • RIGHT OUTER JOIN — all right + matching left.
     * • FULL OUTER JOIN — all rows from both sides.
     * • CROSS JOIN — Cartesian product.
     * • SELF JOIN — table joined with itself.
     *
     * Advanced Features
     * -----------------
     * • Views: virtual tables; updatable under constraints.
     * • Stored Procedures: precompiled SQL logic.
     * • Triggers: auto-execute on INSERT/UPDATE/DELETE.
     * • Functions: scalar, table-valued, aggregate.
     * • Window Functions: ROW_NUMBER, RANK, DENSE_RANK, LEAD, LAG.
     *
     * ER Model
     * --------
     * • Entity, Attribute (simple/composite/derived/multivalued).
     * • Relationship cardinality: 1:1, 1:N, M:N.
     * • Weak Entity: depends on strong entity for identification.
     *
     * Distributed DB
     * --------------
     * • CAP Theorem: you can guarantee only 2 of 3:
     * Consistency | Availability | Partition Tolerance.
     * • BASE (NoSQL): Basically Available, Soft state, Eventually consistent.
     * • Sharding: horizontal partitioning across nodes.
     * • Replication: master-slave or multi-master.
     */
}