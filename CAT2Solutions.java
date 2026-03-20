import java.util.*;

public class CAT2Solutions {

}

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int x) {
        val = x;
    }
}

class TreeSolutions {
    TreeNode first, second, prev;

    public TreeNode buildFromLevelOrder(Integer[] arr) {
        if (arr == null || arr.length == 0)
            return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (i < arr.length) {
            TreeNode parent = queue.poll();

            if (arr[i] != null) {
                parent.left = new TreeNode(arr[i]);
                queue.add(parent.left);
            }
            i++;

            if (i < arr.length && arr[i] != null) {
                parent.right = new TreeNode(arr[i]);
                queue.add(parent.right);
            }
            i++;
        }
        return root;
    }

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
        if (isLeaf(n))
            res.add(n.val);
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
}

class GraphSolutions {
    int V;
    List<List<int[]>> adj;

    GraphSolutions(int v) {
        V = v;
        adj = new ArrayList<>();
        for (int i = 0; i < v; i++)
            adj.add(new ArrayList<>());
    }

    public void BFS(int s) {
        boolean[] vis = new boolean[V];
        Queue<Integer> q = new LinkedList<>();
        vis[s] = true;
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int[] edge : adj.get(u)) {
                if (!vis[edge[0]]) {
                    vis[edge[0]] = true;
                    q.add(edge[0]);
                }
            }
        }
    }

    public void DFS(int u, boolean[] vis) {
        vis[u] = true;
        for (int[] edge : adj.get(u))
            if (!vis[edge[0]])
                DFS(edge[0], vis);
    }

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

    public void bellmanFord(int src, int[][] edges) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        for (int i = 0; i < V - 1; i++) {
            for (int[] e : edges) {
                if (dist[e[0]] != Integer.MAX_VALUE && dist[e[0]] + e[2] < dist[e[1]])
                    dist[e[1]] = dist[e[0]] + e[2];
            }
        }
    }

    public List<Integer> topoSort() {
        int[] in = new int[V];
        for (int i = 0; i < V; i++)
            for (int[] e : adj.get(i))
                in[e[0]]++;
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++)
            if (in[i] == 0)
                q.add(i);
        List<Integer> res = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            res.add(u);
            for (int[] e : adj.get(u))
                if (--in[e[0]] == 0)
                    q.add(e[0]);
        }
        return res.size() == V ? res : new ArrayList<>();
    }
}

class HeapSolutions {
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
        int lg = i, l = 2 * i + 1, r = 2 * i + 2;
        if (l < n && a[l] > a[lg])
            lg = l;
        if (r < n && a[r] > a[lg])
            lg = r;
        if (lg != i) {
            int t = a[i];
            a[i] = a[lg];
            a[lg] = t;
            heapify(a, n, lg);
        }
    }
}

class KaryHeap {
    int[] heap;
    int sz, k;

    KaryHeap(int cap, int k) {
        this.heap = new int[cap];
        this.k = k;
    }

    public void insert(int x) {
        heap[sz] = x;
        int i = sz++;
        while (i > 0) {
            int p = (i - 1) / k;
            if (heap[i] < heap[p]) {
                int t = heap[i];
                heap[i] = heap[p];
                heap[p] = t;
                i = p;
            } else
                break;
        }
    }
}

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
}