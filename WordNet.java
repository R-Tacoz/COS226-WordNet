import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private Digraph g;
    private ArrayList<LinkedList<Integer>> adjList; // WordNet adjacency list
    private ArrayList<String[]> synsetWordsST; // ST of <synset ID, words>
    private HashMap<String, ArrayList<Integer>> wordSynsetST; // ST of <word, synset IDs>
    private ShortestCommonAncestor scaObj; // ShortestCommonAncestor

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Cannot construct with null arguments");

        adjList = new ArrayList<>();
        synsetWordsST = new ArrayList<>();
        wordSynsetST = new HashMap<>();



        // read synsets file to initialize adjList and fill symbol tables
        In synsetsStream = new In(synsets);
        while (!synsetsStream.isEmpty()) {
            String[] line = synsetsStream.readLine().split(",");
            int synsetId = Integer.parseInt(line[0]);
            String[] synset = line[1].split(" ");

            adjList.add(new LinkedList<>());
            synsetWordsST.add(synset);

            for (String word : synset)
                if (wordSynsetST.containsKey(word))
                    wordSynsetST.get(word).add(synsetId);
                else {
                    ArrayList<Integer> IDs = new ArrayList<Integer>();
                    IDs.add(synsetId);
                    wordSynsetST.put(word, IDs);
                }
        }
        synsetsStream.close();

        Digraph G = new Digraph(adjList.size());

        // read hypernyms file to fill adjList
        In hypernymsStream = new In(hypernyms);
        while (!hypernymsStream.isEmpty()) {
            String[] line = hypernymsStream.readLine().split(",");
            int synsetId = Integer.parseInt(line[0]);
            int hypernym;
            for (int i = 1; i < line.length; i++) {
                hypernym = Integer.parseInt(line[i]);
                G.addEdge(synsetId, hypernym);
                adjList.get(synsetId).add(hypernym);
            }
        }
        scaObj = new ShortestCommonAncestor(G);
        hypernymsStream.close();

        // for (int i : adjList.get(0))
        //     StdOut.print(i);
        //
        // for (String s : synsetWordsST.get(1000))
        //     StdOut.print(s);

    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return wordSynsetST.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("isNoun is invalid null arguments");
        return wordSynsetST.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException(
                    "Cannot call sca with words not in WordNet");

        ArrayList<Integer> noun1SynsetId = wordSynsetST.get(noun1);
        ArrayList<Integer>  noun2SynsetId = wordSynsetST.get(noun2);

        System.out.println(scaObj.ancestorSubset(noun1SynsetId, noun2SynsetId));

        return synsetWordsST.get(scaObj.ancestorSubset(noun1SynsetId, noun2SynsetId))[0];
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException(
                    "Cannot call distance with words not in WordNet");

        ArrayList<Integer> noun1SynsetId = wordSynsetST.get(noun1);
        ArrayList<Integer>  noun2SynsetId = wordSynsetST.get(noun2);

        int distance = scaObj.lengthSubset(noun1SynsetId, noun2SynsetId);

        return distance;
    }

    // unit testing
    public static void main(String[] args) {

        WordNet w = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.print(w.sca("town", "village"));
    }

}
