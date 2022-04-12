public class DocPostings implements Comparable<DocPostings>{
    int num;
    String id;

    public DocPostings(int num, String id) {
        this.num = num;
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public int getPostings() {
        return num;
    }

    public String toString() {
        return (num + " " + id);
    }
    public int compareTo(DocPostings o) {
        return num - o.getPostings();
    }
    
}
