/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/14
 *  Description: Programming Assignment - Week 2: Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = 0;
        if (args.length > 0) {
            k = Integer.parseInt(args[0]);
        }

        int n = 0;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            n++;
            String s = StdIn.readString();
            if (n > k) {
                int position = StdRandom.uniform(n);
                if (position <= k) queue.dequeue();
            }
            if (queue.size() < k) queue.enqueue(s);
        }
        for (int i = 0; i < k; i++) StdOut.println(queue.dequeue());
    }
}
