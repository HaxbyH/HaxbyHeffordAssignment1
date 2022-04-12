import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.Collections;

public class Search {

    public static ArrayList<WordIndex> words = new ArrayList<>();
    public static HashMap<Integer, String> docIDs = new HashMap<>();

    public static void main(String args[]) {

        // Load Pointers
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("pointers.txt"));
            String line;
            while((line = reader.readLine()) !=null) {
                String[] splitted = line.split(" ");
                WordIndex x = new WordIndex(splitted[0], Long.parseLong(splitted[1]), Long.parseLong(splitted[2]));
                words.add(x);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load Postings
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("postings.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                String[] splitted = line.split(" ");
                docIDs.put(Integer.parseInt(splitted[0]), splitted[1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        // stdin scanner
        Scanner in = new Scanner(System.in);
        while(in.hasNextLine()) {
            String query = in.nextLine();
            if(query.equals("(exit)")) {
                break;
            }
            String[] splitted = query.split(" ");
            
            // Finds relevances
            ArrayList<TotalTerms> ids = new ArrayList<TotalTerms>();
            ArrayList<Integer> total = new ArrayList<Integer>();

            try {
                RandomAccessFile input = new RandomAccessFile("index", "r");
                for (String word: splitted) {
                    int j = Collections.binarySearch(words, new WordIndex(word, -1, -1));
                    if(j <= -1) {
                        continue;
                    }
                    WordIndex indexes = words.get(j);
                    long start = indexes.getStart();
                    long end = indexes.getEnd();
                    input.seek(start);
                    while(input.getFilePointer()<end) {
                        int posting = input.readInt();
                        int occurences = input.readInt();
                        if(!total.contains(posting)) {
                            total.add(posting);
                            TotalTerms newTotal = new TotalTerms(posting, occurences);
                            ids.add(newTotal);
                        } else {
                            ids.get(total.indexOf(posting)).IncreaseCount(occurences);
                        }
                    }
                }

                if (ids.isEmpty()) {
                    System.out.println("There were no matches.");
                }

                Collections.sort(ids);
                for (TotalTerms x: ids) {
                    System.out.println(docIDs.get(x.getID()) + " " + x.getCount());
                }
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        in.close();
    }
}
            

    