package com.data.pj;

import java.util.ArrayList;
import java.util.List;

public class BTree implements BalancedBST<BTree.Pair>{

    Node root;
    int min, max;//min order and max order

    BTree(int maxOrder){
        this.root = new Node(true);
        this.min = (maxOrder+1)/2;
        this.max = maxOrder;
    }

    public Pair search(int value){
        Node cur = root;
        while(true){
            if(!cur.keys.contains(value) && cur.isLeaf)
                break;
            for(int i = 0; i < cur.keys.size(); i++){
                if(cur.keys.get(i) == value){
                    return new Pair(cur, i);
                }else if(i == 0 && cur.keys.get(i) > value){
                    cur = cur.children.get(i);
                    break;
                }else if((i == cur.keys.size()-1 && cur.keys.get(i) < value)
                        || (i < cur.keys.size()-1 && cur.keys.get(i) < value && cur.keys.get(i+1) > value)){
                    cur = cur.children.get(i+1);
                    break;
                }
            }
        }
        return null;
    }

    public void insert(int value){
        Node cur = root;
        if(cur.keys.size() == max){
            Node node = new Node(false);
            root = node;
            node.children.add(cur);
            splitChild(node, 0);
            insertNonfull(node, value);
        }else
            insertNonfull(cur, value);
    }

    private void splitChild(Node node, int index){
        Node cur = node.children.get(index);
        Node next = new Node(cur.isLeaf);

        List<Integer> curNewKeys = new ArrayList<>();
        List<Node> curNewChild = new ArrayList<>();
        int key = cur.keys.get(min-1);
        for(int i = 0; i < min-1; i++) {
            next.keys.add(cur.keys.get(min + i));
            curNewKeys.add(cur.keys.get(i));
        }
        cur.keys = curNewKeys;
        if(!cur.isLeaf){
            for(int i = 0; i < min; i++) {
                next.children.add(cur.children.get(min + i));
                curNewChild.add(cur.children.get(i));
            }
            cur.children = curNewChild;
        }

        node.children.add(index+1, next);
        node.keys.add(index, key);
    }

    private void insertNonfull(Node node, int value){
        if(node.isLeaf){
            int index = 0;
            while(index < node.keys.size() && node.keys.get(index) < value)
                index++;
            node.keys.add(index, value);
        }else{
            int index = 0;
            while(index < node.keys.size() && node.keys.get(index) < value)
                index++;
            Node child = node.children.get(index);
            if(child.keys.size() == max){
                splitChild(node, index);
                if(node.keys.get(index) < value)
                    index++;
            }
            insertNonfull(node.children.get(index), value);
        }
    }

    public void delete(int value){
        doDelete(root, value);
    }

    @Override
    public void clear() {
        this.root = new Node(true);
    }

    private int findPred(Node node){
        if(node.isLeaf)
            return node.keys.get(node.keys.size()-1);
        return findPred(node.children.get(node.children.size()-1));
    }

    private int findSucc(Node node){
        if(node.isLeaf)
            return node.keys.get(0);
        return findSucc(node.children.get(0));
    }

    private void doDelete(Node node, int value){
        if(node == root && node.keys.isEmpty()) {
            root = node.children.get(0);
            node = root;
        }

        int index = node.keys.indexOf(value);
        if(index >= 0){
            if(node.isLeaf)
                node.keys.remove(index);
            else{
                Node leftChild = node.children.get(index);
                if(leftChild.keys.size() >= min){
                    int pred = findPred(leftChild);
                    node.keys.remove(index);
                    node.keys.add(index, pred);
                    doDelete(leftChild, pred);
                }else{
                    Node rightChild = node.children.get(index+1);
                    if(rightChild.keys.size() >= min){
                        int succ = findSucc(rightChild);
                        node.keys.remove(index);
                        node.keys.add(index, succ);
                        doDelete(rightChild, succ);
                    }else{
                        leftChild.keys.add(node.keys.remove(index));
                        node.children.remove(index+1);
                        leftChild.keys.addAll(rightChild.keys);
                        if(!rightChild.isLeaf)
                            leftChild.children.addAll(rightChild.children);
                        doDelete(leftChild, value);
                    }
                }
            }
        }else{
            if(node.isLeaf)
                return;
            for(int i = 0; i < node.keys.size(); i++) {
                if (i == 0 && node.keys.get(i) > value) {
                    index = i;
                    break;
                } else if ((i == node.keys.size() - 1 && node.keys.get(i) < value)
                        || (i < node.keys.size() - 1 && node.keys.get(i) < value && node.keys.get(i + 1) > value)) {
                    index = i + 1;
                    break;
                }
            }
            Node child = node.children.get(index);
            if(child.keys.size() >= min)
                doDelete(child, value);
            else{
                if(index < node.keys.size() && node.children.get(index+1).keys.size() >= min){//right sibling
                    child.keys.add(node.keys.get(index));
                    node.keys.set(index, node.children.get(index+1).keys.get(0));
                    node.children.get(index+1).keys.remove(0);
                    if(!node.children.get(index+1).isLeaf) {
                        child.children.add(node.children.get(index + 1).children.get(0));
                        node.children.get(index + 1).children.remove(0);
                    }
                    doDelete(child, value);
                }else if(index > 0 && node.children.get(index-1).keys.size() >= min){//left sibling
                    child.keys.add(0, node.keys.get(index-1));
                    node.keys.set(index-1, node.children.get(index-1).keys.get(node.children.get(index-1).keys.size()-1));
                    node.children.get(index-1).keys.remove(node.children.get(index-1).keys.size()-1);
                    if(!node.children.get(index-1).isLeaf) {
                        child.children.add(0, node.children.get(index - 1).children.get(node.children.get(index - 1).children.size() - 1));
                        node.children.get(index - 1).children.remove(node.children.get(index - 1).children.size() - 1);
                    }
                    doDelete(child, value);
                }else{//no sibling has at least min keys
                    if(index < node.children.size()-1){
                        Node rightSibling = node.children.get(index+1);
                        child.keys.add(node.keys.get(index));
                        node.keys.remove(index);
                        node.children.remove(index+1);
                        child.keys.addAll(rightSibling.keys);
                        if(!rightSibling.isLeaf)
                            child.children.addAll(rightSibling.children);
                    }else{
                        Node leftSibling = node.children.get(index-1);
                        child.keys.add(0, node.keys.get(index-1));
                        node.keys.remove(index-1);
                        node.children.remove(index-1);

                        leftSibling.keys.addAll(child.keys);
                        child.keys = leftSibling.keys;
                        if(!leftSibling.isLeaf) {
                            leftSibling.children.addAll(child.children);
                            child.children = leftSibling.children;
                        }
                    }

                    doDelete(child, value);
                }
            }
        }
    }

    class Pair{
        Node node;
        int index;

        Pair(Node node, int index){
            this.node = node;
            this.index = index;
        }
    }

    class Node{
        List<Integer> keys;
        List<Node> children;
        boolean isLeaf;

        Node(boolean isLeaf){
            this.keys = new ArrayList<>();
            this.children = new ArrayList<>();
            this.isLeaf = isLeaf;
        }
    }
}
