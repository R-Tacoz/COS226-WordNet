import java.util.Iterator;

import edu.princeton.cs.algs4.Digraph;

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

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Cannot construct with null argument");
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {

        return -1;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {

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

        return -1;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}