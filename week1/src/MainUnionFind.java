import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MainUnionFind {
    public static void main(String[] args) {
        if (args.length == 0) {
            quickUnionUF();
        }
        else {
            quickFindUF();    
        }        
    }
    
    private static void quickFindUF() {
        int N = StdIn.readInt();
        QuickFindUF uf = new QuickFindUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.isConnected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " " + q);
                uf.printArray();
            }
        }    
    }
    
    private static void quickUnionUF() {
        int N = StdIn.readInt();
        QuickUnionUF uf = new QuickUnionUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.isConnected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " " + q);
                StdOut.println("Height = " + uf.getMaxTreeHeight());
            }
        }    
    }
}