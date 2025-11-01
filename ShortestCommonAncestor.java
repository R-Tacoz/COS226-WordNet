import java.util.Iterator;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class ShortestCommonAncestor {
    // The argument to the constructor is not a rooted DAG
    // Any argument is null
    // Any vertex argument is outside its prescribed range
    // Any iterable argument contains zero vertices
    // Any iterable argument contains a null item

    // Your data type must use O(E+V)
    // space.
    // All methods and the constructor must take O(E+V)
    // time.
    private Digraph Graph;
    private int inquiryID;
    private int[] visited;
    private int[] distance;
    private boolean[] aList;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Cannot construct with null argument");
        Graph = G;
        inquiryID = 1;
        visited = new int[Graph.V()];
        distance = new int[Graph.V()];
        aList = new boolean[Graph.V()];
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        Queue<triple> q = new Queue<triple>();
        inquiryID++;
        q.enqueue(new triple(v, 0, true));
        q.enqueue(new triple(w, 0, false));
        visited[v] = inquiryID;
        visited[w] = inquiryID;
        distance[v] = 0;
        distance[w] = 0;
        while (!q.isEmpty()){
            triple node = q.dequeue();
            for (int i : Graph.adj(node.node)) {
                if (visited[i] == inquiryID) {
                    return node.distance+distance[i]+1;
                }
                visited[i] = inquiryID;
                distance[i] = node.distance+1;
                triple nextpair = new triple(i, node.distance+1, node.a);
                q.enqueue(nextpair);
            }
        }
        return -1;
    }
    private class triple {
        int node;
        int distance;
        boolean a;
        public triple(int node, int distance, boolean a){
            this.node = node;
            this.distance = distance;
            this.a = a;
        }
    }
    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        Queue<triple> q = new Queue<triple>();
        inquiryID++;
        q.enqueue(new triple(v, 0, true));
        q.enqueue(new triple(w, 0, false));
        visited[v] = inquiryID;
        visited[w] = inquiryID;
        distance[v] = 0;
        distance[w] = 0;
        while (!q.isEmpty()){
            triple word = q.dequeue();
            for (int i : Graph.adj(word.node)) {
                if (visited[i] == inquiryID) {
                    return i;
                }
                visited[i] = inquiryID;
                distance[i] = word.distance+1;
                triple nextpair = new triple(i, word.distance+1, word.a);
                q.enqueue(nextpair);
            }
        }


        return -1;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null)
            throw new IllegalArgumentException(
                    "lengthSubset cannot take null arguments");

        Iterator<Integer> aIter = subsetA.iterator();
        Iterator<Integer> bIter = subsetB.iterator();

        if (!aIter.hasNext() || !bIter.hasNext())
            throw new IllegalArgumentException("lengthSubset cannot take empty sets");
        Queue<triple> q = new Queue<triple>();
        inquiryID++;
        for (int i : subsetA)  {
            q.enqueue(new triple(i, 0, true));
            visited[i] = inquiryID;
            distance[i] = 0;
            aList[i] = true;
        }
        for (int i : subsetB) {
            q.enqueue(new triple(i, 0, false));
            visited[i] = inquiryID;
            distance[i] = 0;
            aList[i] = false;
        }

        while (!q.isEmpty()){
            triple node = q.dequeue();
            for (int i : Graph.adj(node.node)) {
                if (visited[i] == inquiryID && aList[i]^node.a)
                    return node.distance+distance[i]+1;

                visited[i] = inquiryID;
                distance[i] = node.distance+1;
                aList[i] = node.a;
                triple nextpair = new triple(i, node.distance+1, node.a);
                q.enqueue(nextpair);
            }
        }
        return -1;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null)
            throw new IllegalArgumentException(
                    "ancestorSubset cannot take null arguments");

        Iterator<Integer> aIter = subsetA.iterator();
        Iterator<Integer> bIter = subsetB.iterator();

        if (!aIter.hasNext() || !bIter.hasNext())
            throw new IllegalArgumentException(
                    "ancestorSubset cannot take empty sets");

        Queue<triple> q = new Queue<triple>();
        inquiryID++;
        for (int i : subsetA)  {
            q.enqueue(new triple(i, 0, true));
            visited[i] = inquiryID;
            distance[i] = 0;
            aList[i] = true;
        }
        for (int i : subsetB) {
            q.enqueue(new triple(i, 0, false));
            visited[i] = inquiryID;
            distance[i] = 0;
            aList[i] = false;
        }
        while (!q.isEmpty()){
            triple node = q.dequeue();
            for (int i : Graph.adj(node.node)) {
                if (visited[i] == inquiryID && aList[i]^node.a)
                    return i;

                visited[i] = inquiryID;
                distance[i] = node.distance;
                aList[i] = node.a;
                triple nextpair = new triple(i, node.distance+1, node.a);
                q.enqueue(nextpair);
            }
        }

        return -1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
