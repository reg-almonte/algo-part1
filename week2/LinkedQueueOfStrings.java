/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class LinkedQueueOfStrings {
    private Node first, last;

    private class Node { /* same as in StackOfStrings */
        private String item;
        private LinkedQueueOfStrings.Node next;

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(String item) {
        Node oldLast = last;
        last = new Node();
        last.setItem(item);
        last.setNext(null);
        if (isEmpty()) first = last;
        else oldLast.setNext(last);
    }

    public String dequeue() {
        String item = first.getItem();
        first = first.getNext();
        if (isEmpty()) last = null;
        return item;
    }

    public static void main(String[] args) {

    }
}
