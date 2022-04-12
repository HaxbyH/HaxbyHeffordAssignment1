public class WordIndex implements Comparable<WordIndex>{
    String word = null;
    long start = -1;
    long end = -1;

    public WordIndex(String word, long start, long end) {
        this.word = word;
        this.start = start;
        this.end = end;
    }

    public String toString() {
        return (word + " " + start + " " + end);
    }

    public String getWord() {
        return this.word;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }


    public int compareTo(WordIndex x) {
        return (word.compareTo(x.getWord()));
    }
    
}
