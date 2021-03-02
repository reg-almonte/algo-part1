import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class QuickFindUF {
    private int[] id;
    
    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }
    
    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    }
    
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) id[i] = qid;
        }
    }
    
    public void printArray() {
        StdOut.println(Arrays.toString(id));
    }
}
