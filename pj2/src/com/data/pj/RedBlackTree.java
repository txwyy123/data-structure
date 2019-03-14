package com.data.pj;

public class RedBlackTree implements BalancedBST<RedBlackTree.Node>{

    Node nil;
    Node root;

    RedBlackTree(){
        this.nil = new Node(0);
        nil.color = 1;
        nil.isLeaf = true;
        root = nil;
    }

    public Node search(int value){
        Node cur = root;
        while(cur != nil && cur.value != value){
            if(cur.value > value)
                cur = cur.l;
            else
                cur = cur.r;
        }
        return cur;
    }

    public void insert(int value){
        Node z = new Node(value);
        Node y = nil;
        Node x = root;
        while(x != nil){
            y = x;
            if(x.value > value)
                x = x.l;
            else
                x = x.r;
        }

        z.p = y;
        if(y == nil)
            root = z;
        else if(y.value > z.value)
            y.l = z;
        else
            y.r = z;
        z.l = nil;
        z.r = nil;
        z.color = 0;
        insertFixUp(z);
    }

    private void insertFixUp(Node z){
        while(z.p.color == 0){
            if(z.p == z.p.p.l){//uncle is grandparent's right child
                if(z.p.p.r.color == 0){//uncle's color is red
                    z.p.color = 1;
                    z.p.p.color = 0;
                    z.p.p.r.color = 1;
                    z = z.p.p;
                }else{
                    if(z == z.p.r) {//z is its parent's right child
                        z = z.p;
                        leftRotate(z);
                    }
                    //z is its parent's left child
                    z.p.color = 1;
                    z.p.p.color = 0;
                    rightRotate(z.p.p);
                }
            }else{
                if(z.p.p.l.color == 0){//uncle's color is red
                    z.p.color = 1;
                    z.p.p.color = 0;
                    z.p.p.l.color = 1;
                    z = z.p.p;
                }else{
                    if(z == z.p.l) {//z is its parent's left child
                        z = z.p;
                        rightRotate(z);
                    }
                    //z is its parent's right child
                    z.p.color = 1;
                    z.p.p.color = 0;
                    leftRotate(z.p.p);
                }
            }
        }
        root.color = 1;
    }

    public void delete(int value){
        Node z = search(value);

        if(z != nil){
            Node y = z;
            int origColor = y.color;
            Node x;
            if(z.l == nil) {
                x = z.r;
                replace(z, z.r);
            }else if(z.r == nil) {
                x = z.l;
                replace(z, z.l);
            }else{
                y = findSucc(z);
                origColor = y.color;
                x = y.r;
                if (y.p == z) {
                    x.p = y;
                }else {
                    replace(y, y.r);
                    y.r = z.r;
                    y.r.p = y;
                }
                replace(z, y);
                y.l = z.l;
                y.l.p = y;
                y.color = z.color;
            }

            if(origColor == 1)
                deleteFixUp(x);
        }
    }

    @Override
    public void clear() {
        this.root = nil;
    }

    private void replace(Node o, Node n){
        if(root == o)
            root = n;
        else if(o == o.p.l)
            o.p.l = n;
        else
            o.p.r = n;
        n.p = o.p;
    }

    private Node findSucc(Node cur){
        if(cur.r == nil)
            return nil;

        Node succ = cur.r;
        while(succ.l != nil)
            succ = succ.l;
        return succ;
    }

    private void deleteFixUp(Node x){
        while(x != root && x.color == 1){//x's color is black
            if(x == x.p.l){
                Node w = x.p.r;
                if(w.color == 0){//x's bro's color is red
                    w.color = 1;
                    x.p.color = 0;
                    leftRotate(x.p);
                    w = x.p.r;
                }
                if(w.l.color == 1 && w.r.color == 1){
                    w.color = 0;
                    x = x.p;
                }else {
                    if(w.r.color == 1){
                        w.l.color = 1;
                        w.color = 0;
                        rightRotate(w);
                        w = x.p.r;
                    }
                    w.color = x.p.color;
                    x.p.color = 1;
                    w.r.color = 1;
                    leftRotate(x.p);
                    x = root;
                }
            }else{
                Node w = x.p.l;
                if(w.color == 0){//x's bro's color is red
                    w.color = 1;
                    x.p.color = 0;
                    rightRotate(x.p);
                    w = x.p.l;
                }
                if(w.l.color == 1 && w.r.color == 1){
                    w.color = 0;
                    x = x.p;
                }else {
                    if(w.l.color == 1){
                        w.r.color = 1;
                        w.color = 0;
                        leftRotate(w);
                        w = x.p.l;
                    }
                    w.color = x.p.color;
                    x.p.color = 1;
                    w.l.color = 1;
                    rightRotate(x.p);
                    x = root;
                }
            }
        }
        x.color = 1;
    }

    private void leftRotate(Node x){
        Node y = x.r;
        x.r = y.l;
        if(y.l != nil)
            y.l.p = x;
        y.p = x.p;
        if(x.p == nil)
            root = y;
        else if(x == x.p.l)
            x.p.l = y;
        else
            x.p.r = y;
        y.l = x;
        x.p = y;
    }

    private void rightRotate(Node x){
        Node y = x.l;
        x.l = y.r;
        if(y.r != nil)
            y.r.p = x;
        y.p = x.p;
        if(x.p == nil)
            root = y;
        else if(x == x.p.l)
            x.p.l = y;
        else
            x.p.r = y;
        y.r = x;
        x.p = y;
    }

    class Node{
        int value;
        int color;//0-red 1-black
        boolean isLeaf;
        Node p, l, r;

        Node(int value){
            this.value = value;
        }
    }
}
