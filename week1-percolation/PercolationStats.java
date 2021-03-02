/* *****************************************************************************
 *  Name:    Reg Almonte
 *  NetID:   ralmonte
 *  Precept: P00
 *
 *  Description:  Performs a series of computational experiments for percolation
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] trialOutputs;
    private final int psize;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        psize = n * n;
        trialOutputs = new double[trials];
        for (int i = 0; i < trials; i++) {
            trialOutputs[i] = runOneTrial(n);
        }
    }

    private double runOneTrial(int n) {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniform(0, n);
            int col = StdRandom.uniform(0, n);
            p.open(row + 1, col + 1);
        }
        return (double) p.numberOfOpenSites() / psize;
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialOutputs);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialOutputs);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(trialOutputs.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(trialOutputs.length);
    }

    // test client
    public static void main(String[] args) {
        int n = 2;
        int trials = 100;
        if (args.length > 0) n = Integer.parseInt(args[0]);
        if (args.length > 1) trials = Integer.parseInt(args[1]);
        PercolationStats pstat = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + pstat.mean());
        StdOut.println("stddev                  = " + pstat.stddev());
        StdOut.println(
                "95% confidence interval = [" + pstat.confidenceLo() + ", " + pstat.confidenceHi()
                        + "]");
    }
}
