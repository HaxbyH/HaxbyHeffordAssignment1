public class TotalTerms implements Comparable<TotalTerms>{
    int id;
    int count = 0;

    public TotalTerms(int id, int x) {
        this.id = id;
        count = x;
    }

    public void IncreaseCount(int num) {
        this.count += num;
    }

    public String toString() {
        return (id + " " + count);
    }

    public int getCount() {
        return count;
    }

    public int getID() {
        return id;
    }

    public int compareTo(TotalTerms x) {
        return x.getCount() - count;

    }
    
}
