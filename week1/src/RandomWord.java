import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

	public static void main(String[] args) {
		int i = 0;
		String output = "";
		String inputWord = null;
		while(!StdIn.isEmpty()) {
			inputWord = StdIn.readString();
            i++;
            if (StdRandom.bernoulli(1.0 / i)) {
                output = inputWord;
            }
		}
        StdOut.println(output);		
	}

}
