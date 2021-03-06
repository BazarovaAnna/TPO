package main.SplayTree;

import java.util.LinkedList;
import java.util.List;

/** Class SplayTree **/
public class SplayTree
{
    private Node root;
    private int count = 0;
    public List<String> actLog;

    /** Constructor **/
    public SplayTree()
    {
        root = null;
        actLog=new LinkedList<>();
    }

    /** Function to check if tree is empty **/
    public boolean isEmpty()
    {
        actLog.add("empty");
        return root == null;
    }

    /** clear tree **/
    public void clear()
    {
        actLog.add("clear");
        root = null;
        count = 0;
    }

    /** function to insert element */
    public void insert(int ele)
    {
        Node z = root;
        Node p = null;
        while (z != null)
        {
            p = z;
            if (ele > p.element){
                actLog.add("moveRight");
                z = z.right;}
            else {
                actLog.add("moveLeft");
                z = z.left;
            }
        }
        actLog.add("makeNode");
        z = new Node();
        z.element = ele;
        z.parent = p;
        if (p == null)
            root = z;
        else if (ele > p.element){
            actLog.add("rotateRight");
            p.right = z;}
        else {
            actLog.add("rotateLeft");
            p.left = z;
        }
        Splay(z);
        count++;
    }
    /** rotate **/
    public void makeLeftChildParent(Node c, Node p)
    {
        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p))
            throw new RuntimeException("WRONG");

        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.right != null)
            c.right.parent = p;

        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    /** rotate **/
    public void makeRightChildParent(Node c, Node p)
    {
        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p))
            throw new RuntimeException("WRONG");
        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.left != null)
            c.left.parent = p;
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }

    /** function splay **/
    private void Splay(Node x)
    {
        while (x.parent != null)
        {
            Node Parent = x.parent;
            Node GrandParent = Parent.parent;
            if (GrandParent == null)
            {
                if (x == Parent.left)
                    makeLeftChildParent(x, Parent);
                else
                    makeRightChildParent(x, Parent);
            }
            else
            {
                if (x == Parent.left)
                {
                    if (Parent == GrandParent.left)
                    {
                        makeLeftChildParent(Parent, GrandParent);
                        makeLeftChildParent(x, Parent);
                    }
                    else
                    {
                        makeLeftChildParent(x, x.parent);
                        makeRightChildParent(x, x.parent);
                    }
                }
                else
                {
                    if (Parent == GrandParent.left)
                    {
                        makeRightChildParent(x, x.parent);
                        makeLeftChildParent(x, x.parent);
                    }
                    else
                    {
                        makeRightChildParent(Parent, GrandParent);
                        makeRightChildParent(x, Parent);
                    }
                }
            }
        }
        root = x;
    }

    /** function to remove element **/
    public void remove(int ele)
    {
        Node node = findNode(ele);
        remove(node);
    }

    /** function to remove node **/
    private void remove(Node node)
    {
        if (node == null)
            return;

        Splay(node);
        if( (node.left != null) && (node.right !=null))
        {
            Node min = node.left;
            while(min.right!=null)
                min = min.right;

            min.right = node.right;
            node.right.parent = min;
            node.left.parent = null;
            root = node.left;
        }
        else if (node.right != null)
        {
            node.right.parent = null;
            root = node.right;
        }
        else if( node.left !=null)
        {
            node.left.parent = null;
            root = node.left;
        }
        else
        {
            root = null;
        }
        node.parent = null;
        node.left = null;
        node.right = null;
        node = null;
        count--;
    }

    /** Functions to count number of nodes **/
    public int countNodes()
    {
        return count;
    }

    /** Functions to search for an element **/
    public boolean search(int val)
    {
        actLog.add("find");
        return findNode(val) != null;
    }

    private Node findNode(int ele)
    {
        Node PrevNode = null;
        Node z = root;
        while (z != null)
        {
            PrevNode = z;
            if (ele > z.element){
                actLog.add("moveRight");
                z = z.right;}
            else if (ele < z.element){
                actLog.add("moveLeft");
                z = z.left;}
            else if(ele == z.element) {
                actLog.add("splay");
                Splay(z);
                return z;
            }

        }
        if(PrevNode != null)
        {
            Splay(PrevNode);
            return null;
        }
        return null;
    }

    /** Function for inorder traversal **/
    public void inorder()
    {
        inorder(root);
    }
    private void inorder(Node r)
    {
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.element +" ");
            inorder(r.right);
        }
    }

    /** Function for preorder traversal **/
    public void preorder()
    {
        preorder(root);
    }
    private void preorder(Node r)
    {
        if (r != null)
        {
            System.out.print(r.element +" ");
            preorder(r.left);
            preorder(r.right);
        }
    }

    /** Function for postorder traversal **/
    public void postorder()
    {
        postorder(root);
    }
    private void postorder(Node r)
    {
        if (r != null)
        {
            postorder(r.left);
            postorder(r.right);
            System.out.print(r.element +" ");
        }
    }

}