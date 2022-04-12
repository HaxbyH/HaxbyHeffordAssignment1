import java.io.*;
import java.util.*;

public class Indexer {
    public static HashMap<String, TermFrequencies> map = new HashMap<>();
    public static ArrayList<WordIndex> words = new ArrayList<WordIndex>();
    public static ArrayList<DocPostings> docs = new ArrayList<DocPostings>();

    public static void main(String[] args) {
        // Read Index file
        System.out.println("Reading File");
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("parse.txt"));
            String word;
            int docno = 0;
            String docName = null;
            while ((word = reader.readLine()) != null) {
                // For first document only
                if (docno == 0) {
                    docName = word;
                    docno++;
                    docs.add(new DocPostings(docno, docName));

                // Change docName on empty 
                } else if (word.isEmpty()) {
                    docName = reader.readLine();
                    if(docName!=null) {
                        docno++;
                        docs.add(new DocPostings(docno, docName));
                    }

                } else {
                    TermFrequencies term = map.get(word);
                    if (term == null) {
                        term = new TermFrequencies(word);
                        map.put(word, term);
                    }
                    term.addOccurrence(docName, docno);
                }
            }
            System.out.println("Reading Complete");
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }


        // Sort the hashmap
        System.out.println("Sorting");
        ArrayList<String> sortedList = new ArrayList<>(map.keySet());
		Collections.sort(sortedList); //Sorting the ArrayList

            // Write sorted hashmap to disk
            // Save only starting letter position
        
        try {
            RandomAccessFile file = new RandomAccessFile("index", "rw");
            for(String s:sortedList) {
                long start = file.getFilePointer();
                TermFrequencies x = map.get(s);
                for(int i = 0; i < x.docPostings().size(); i++) {
                    file.writeInt(x.docPostings().get(i));
                    file.writeInt(x.OccurrencesInDocument().get(i));
                }
                long end = file.getFilePointer();
                words.add(new WordIndex(s, start, end));
            }
                file.close();
            } catch (IOException p) {
                p.printStackTrace();
            }
        
    
        //Write Points to disk
        try {
            RandomAccessFile p = new RandomAccessFile("pointers.txt", "rw");
            for (WordIndex x : words) {
                p.writeBytes(x.toString() + "\n");
            }
            p.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Sorting Complete");

        // Write postings locations
        try {
            RandomAccessFile p = new RandomAccessFile("postings.txt", "rw");
            for (DocPostings x: docs) {
                p.writeBytes(x.toString() + "\n");
            }
            p.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
