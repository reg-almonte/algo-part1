/* *****************************************************************************
 *  Name: Reg Almonte
 *  Date: 2021/03/09
 *  Description: Queue: Resizing array implementation in Java
 **************************************************************************** */

public class ResizingArrayQueueOfStrings {
    private String[] s;
    private int first, last = 0;

    public ResizingArrayQueueOfStrings() {
        s = new String[1];
    }

    public boolean isEmpty() {
        return first == last;
    }

    public void enqueue(String item) {
        if (last == s.length) {
            if (last - first < s.length - 1) {
                resize(s.length);
            }
            else {
                resize(2 * s.length);
            }
        }
        s[last++] = item;
    }

    public String dequeue() {
        String item = s[first];
        s[first++] = null;
        if (last > 0 && last - first == s.length / 4) resize(s.length / 2);
        return item;
    }

    private void resize(int capacity) {
        // StdOut.println("Resize Queue Array");
        String[] copy = new String[capacity];
        for (int i = 0; i < last - first; i++)
            copy[i] = s[i + first];
        s = copy;
        last = last - first;
        first = 0;
    }

    public static void main(String[] args) {

    }
}
