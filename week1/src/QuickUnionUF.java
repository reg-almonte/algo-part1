
public class QuickUnionUF {
    private int[] id;
    
    public QuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }
    
    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }
    
    public boolean isConnected(int p, int q) {
        return root(p) == root(q);
    }
    
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;  // the p's parent will become a child of q's parent
    }
    
    private int level(int i) {
        int level = 0;
        while (i != id[0]) {
            i = id[0];
            level++;
        }
        return level;
    }
    
    public int getMaxTreeHeight() {
        int max = 0;
        for(int i = 0; i < id.length; i++) {
            int height = level(i);
            if (max < height) max = height;
        }
        return max;
    }

}
