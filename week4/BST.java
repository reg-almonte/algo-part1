/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    // public BST(Key key, Value val) {
    //     root = new Node(key, val);
    // }

    public Value get(Key key)
    {
        Node x = root;
        while (x != null)
        {
            int cmp = key.compareTo(x.key);
            if      (cmp  < 0) x = x.left;
            else if (cmp  > 0) x = x.right;
            else return x.val; // if (cmp == 0)
        }
        return null;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if      (cmp  < 0)
            x.left  = put(x.left,  key, val);
        else if (cmp  > 0)
            x.right = put(x.right, key, val);
        else    // if (cmp == 0)
            x.val = val;
        return x;
    }

    public Iterable<Key> keys()
    {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }
    private void inorder(Node x, Queue<Key> q)
    {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    public void morrisInorderTraversal(Node rt) {

        if (rt == null) {
            return;
        }

        Node cur;
        cur = rt;
        Node pre;
        while (cur != null) {
            // if no left subtree the visit right subtree right away after printing current node
            if (cur.left == null) {
                System.out.print(cur.key+", ");
                cur = cur.right;
            }
            else {
                // otherwise we will traverse the left subtree and come back to current
                // node by using threaded pointer from predecessor of current node
                // first find the predecessor of cur
                pre = cur.left;
                while (pre.right != null && !pre.right.equals(cur)) {
                    pre = pre.right;
                }

                // threaded pointer not added - add it and go to left subtree to traverse
                if (pre.right == null) {
                    pre.right = cur;
                    cur = cur.left;
                }
                else {
                    // we traversed left subtree through threaded pointer and reached cur again
                    // so revert the threaded pointer and print out current node before traversing right subtree
                    pre.right = null;
                    System.out.print(cur.key+", ");
                    // now traverse right subtree
                    cur = cur.right;
                }
            }
        }
    }

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }


    public static void main(String[] args)
    {
        BST<String, Integer> st = new BST<String, Integer>();

        // for (int i = 0; !StdIn.isEmpty(); i++)
        // {
        //     String key = StdIn.readString();
        //     st.put(key, i);
        // }

        In in = new In(args[0]);
        int i = 0;
        while (in.hasNextLine()) {
            String key = in.readString();
            st.put(key, i++);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));

        st.morrisInorderTraversal(st.root);
    }
}
