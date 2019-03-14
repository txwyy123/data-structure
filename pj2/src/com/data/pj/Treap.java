package com.data.pj;

import java.util.Random;

public class Treap implements BalancedBST<Treap.Node>{

    Random random = new Random();
    Node root;

    Treap(){
        this.root = null;
    }

    public Node search(int value){
        Node cur = root;
        while(cur != null && cur.value != value){
            if(cur.value > value)
                cur = cur.l;
            else
                cur = cur.r;
        }
        return cur;
    }

    public void insert(int value){
        Node cur = root;
        Node pre = null;
        Node n = new Node(value);
        while(cur != null && cur.value != value){
            pre = cur;
            if(cur.value > value)
                cur = cur.l;
            else
                cur = cur.r;
        }

        if(pre == null)
            root = n;
        else if(pre.value > value) {
            pre.l = n;
        }else if(pre.value < value) {
            pre.r = n;
        }
        n.p = pre;

        while(n != root && n.priority > n.p.priority){
            if(n == n.p.l)
                rightRotate(n.p);
            else
                leftRotate(n.p);
        }
    }

    public void delete(int value){
        Node n = search(value);
        if(n != null){
            while(n.l != null || n.r != null){
                if(n.l == null)
                    leftRotate(n);
                else if(n.r == null || n.l.priority > n.r.priority)
                    rightRotate(n);
                else
                    leftRotate(n);
            }
            doDelete(n);
        }
    }

    @Override
    public void clear() {
        this.root = null;
    }

    private void doDelete(Node n){
        if(n.l == null)
            replace(n, n.r);
        else if(n.r == null)
            replace(n, n.l);
        else{
            Node succ = n.r;
            while(succ.l != null)
                succ = succ.l;
            if(succ != n.r){
                replace(succ, succ.r);
                succ.r = n.r;
                succ.r.p = succ;
            }
            replace(n, succ);
            succ.l = n.l;
            succ.l.p = succ;
        }
    }

    private void replace(Node u, Node v){
        if(u.p == null)
            root = v;
        else if(u == u.p.l)
            u.p.l = v;
        else
            u.p.r = v;
        if(v != null)
            v.p = u.p;
    }

    private void leftRotate(Node node){
        Node right = node.r;
        node.r = right.l;
        if(right.l != null)
            right.l.p = node;
        right.p = node.p;
        if(node.p == null)
            root = right;
        else if(node == node.p.l)
            node.p.l = right;
        else
            node.p.r = right;
        right.l = node;
        node.p = right;
    }

    private void rightRotate(Node node){
        Node left = node.l;
        node.l = left.r;
        if(left.r != null)
            left.r.p = node;
        left.p = node.p;
        if(node.p == null)
            root = left;
        else if(node == node.p.l)
            node.p.l = left;
        else
            node.p.r = left;
        left.r = node;
        node.p = left;
    }

    class Node{
        int value;
        double priority;
        Node l, r, p;

        Node(int value){
            this.value = value;
            this.priority = random.nextDouble();
            this.p = null;
            this.l = null;
            this.r = null;
        }
    }
}
