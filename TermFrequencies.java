import java.util.*;

public class TermFrequencies implements Comparable<TermFrequencies> {
    String word = null;
    ArrayList<String> docnos = new ArrayList<String>();
    ArrayList<Integer> occurences = new ArrayList<Integer>();
    ArrayList<Integer> postings = new ArrayList<Integer>();

    TermFrequencies(String word) {
        this.word = word;
    }

    void addOccurrence(String documentId, int post){
        if (!docnos.contains(documentId)) {
            docnos.add(documentId);
            occurences.add(1);
            postings.add(post);
        } else {
            int old = occurences.get(docnos.indexOf(documentId));
            occurences.set(docnos.indexOf(documentId), ++old);
        }

    }

    ArrayList<String> getDocumentsIds() {
        return docnos;
    }
    
    ArrayList<Integer> OccurrencesInDocument() {
        return occurences;
    }

    ArrayList<Integer> docPostings() {
        return postings;
    }

    String getWord() {
        return word;
    }

    public int compareTo(TermFrequencies x) {
        return word.compareTo(x.getWord());
    }

    public String toString() {
        return docPostings() + "." + OccurrencesInDocument(); //+ " " + Arrays.toString(differences);
    }
}
