package com.data.pj;

public class StandardBST implements BalancedBST<StandardBST.Node>{

    Node root;

    StandardBST(){
        this.root = null;
    }

    public Node search(int value){
        Node cur = root;
        while(cur != null && cur.value != value){
            if(cur.value > value)
                cur = cur.left;
            else
                cur = cur.right;
        }
        return cur;
    }

    public void insert(int value){
        Node cur = root;
        Node pre = null;
        Node newNode = new Node(value);
        while(cur != null && cur.value != value) {
            pre = cur;
            if(cur.value > value)
                cur = cur.left;
            else
                cur = cur.right;
        }

        if(pre == null)
            root = newNode;
        else if(pre.value > value)
            pre.left = newNode;
        else if(pre.value < value)
            pre.right = newNode;
        newNode.p = pre;
    }

    public void delete(int value){
        Node cur = search(value);
        if(cur != null)
            doDelete(cur);
    }

    @Override
    public void clear() {
        this.root = null;
    }

    private void doDelete(Node n){
        if(n.left == null)
            replace(n, n.right);
        else if(n.right == null)
            replace(n, n.left);
        else{
            Node succ = n.right;
            while(succ.left != null)
                succ = succ.left;
            if(succ != n.right){
                replace(succ, succ.right);
                succ.right = n.right;
                succ.right.p = succ;
            }
            replace(n, succ);
            succ.left = n.left;
            succ.left.p = succ;
        }
    }

    private void replace(Node u, Node v){
        if(u.p == null)
            root = v;
        else if(u == u.p.left)
            u.p.left = v;
        else
            u.p.right = v;
        if(v != null)
            v.p = u.p;
    }

    class Node{
        int value;
        Node left, right, p;

        Node(int value){
            this.value = value;
        }
    }
}
