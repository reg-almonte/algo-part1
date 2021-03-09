/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/09
 *  Description: Stack: Resizing array implementation in Java (From the lecture)
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ResizingArrayStackOfStrings {
    private String[] s;
    private int N = 0;

    public ResizingArrayStackOfStrings() {
        s = new String[1];
    }

    public void push(String item) {
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    public String pop() {
        String item = s[--N];
        s[N] = null;
        if (N > 0 && N == s.length / 4) resize(s.length / 2);
        return item;
    }

    private void resize(int capacity) {
        // StdOut.println("Resize Stack Array");
        String[] copy = new String[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
        // StdOut.println("Resized to: " + capacity + " (" + N + ")");
    }

    public static void main(String[] args) {
        ResizingArrayStackOfStrings stack = new ResizingArrayStackOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.println(stack.pop());
            else stack.push(s);
        }
    }
}
