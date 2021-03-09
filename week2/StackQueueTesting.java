/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/09
 *  Description: Class for testing Stacks and Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class StackQueueTesting {
    public static void main(String[] args) {
        ResizingArrayStackOfStrings stack1 = new ResizingArrayStackOfStrings();
        GenericStack<String> stack2 = new GenericStack<>();
        LinkedQueueOfStrings queue1 = new LinkedQueueOfStrings();
        ResizingArrayQueueOfStrings queue2 = new ResizingArrayQueueOfStrings();
        In in = new In(args[0]);

        while (!in.isEmpty()) {
            String s = in.readString();
            if (s.equals("-")) {
                StdOut.println("Queue1: " + queue1.dequeue());
                StdOut.println("Queue2: " + queue2.dequeue());
                StdOut.println("Stack1: " + stack1.pop());
                StdOut.println("Stack1: " + stack2.pop());
            }
            else {
                queue1.enqueue(s);
                queue2.enqueue(s);
                stack1.push(s);
                stack2.push(s);
            }
        }
    }
}
