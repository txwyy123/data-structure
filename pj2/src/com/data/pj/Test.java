package com.data.pj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("../../random.dat"));
        List<Integer> randomNumbers = new ArrayList<>();
        String line;
        while((line = reader.readLine()) != null)
            randomNumbers.add(Integer.parseInt(line));
        reader.close();

        reader = new BufferedReader(new FileReader("../../serial.dat"));
        List<Integer> serialNumbers = new ArrayList<>();
        while((line = reader.readLine()) != null)
            serialNumbers.add(Integer.parseInt(line));
        reader.close();

        Test test = new Test();
        System.out.println("-------------------------- insert randomly -----------------------------");
        test.testInsert(randomNumbers, true);
        System.out.println("-------------------------- insert serially -----------------------------");
        test.testInsert(serialNumbers, false);
        System.out.println("-------------------------- search randomly -----------------------------");
        test.testSearch(randomNumbers, true);
        System.out.println("-------------------------- search serially -----------------------------");
        test.testSearch(serialNumbers, false);
        System.out.println("-------------------------- delete randomly -----------------------------");
        test.testDelete(randomNumbers, true);
        System.out.println("-------------------------- delete serially -----------------------------");
        test.testDelete(serialNumbers, false);
    }

    void testInsert(List<Integer> nums, boolean isRandom){
        StandardBST bst = new StandardBST();
        RedBlackTree rbt = new RedBlackTree();
        BTree bt = new BTree(5);
        Treap tp = new Treap();

        List<BalancedBST> list = new ArrayList<>();
        list.add(bst);
        list.add(rbt);
        list.add(bt);
        list.add(tp);

        for(int i = 1; i <= 10; i++){
            for(BalancedBST tree : list) {
                long total = 0;
                for(int k = 0; k < 200; k++) {
                    long start = System.currentTimeMillis();
                    for (int j = 0; j < i * 1000; j++) {
                        tree.insert(nums.get(j));
                    }
                    total += System.currentTimeMillis()-start;
                    tree.clear();
                }
                System.out.println("insert "+i*1000+" elements into "+tree.getClass().getName()+" takes "+total+" time");
            }
            System.out.println();
        }
    }

    void testSearch(List<Integer> nums, boolean isRandom){
        StandardBST bst = new StandardBST();
        RedBlackTree rbt = new RedBlackTree();
        BTree bt = new BTree(5);
        Treap tp = new Treap();

        List<BalancedBST> list = new ArrayList<>();
        list.add(bst);
        list.add(rbt);
        list.add(bt);
        list.add(tp);

        for(int i = 1; i <= 10; i++){
            for(BalancedBST tree : list) {
                long total = 0;
                for(int k = 0; k < 200; k++) {
                    for (int j = 0; j < i * 1000; j++) {
                        tree.insert(nums.get(j));
                    }
                    long start = System.currentTimeMillis();
                    for (int j = 0; j < i * 1000; j++) {
                        tree.search(nums.get(j));
                    }
                    total += System.currentTimeMillis()-start;
                    tree.clear();
                }
                System.out.println("search elements in "+tree.getClass().getName()+" with " + i*1000 + " elements takes "+total+" time");
            }
            System.out.println();
        }
    }

    void testDelete(List<Integer> nums, boolean isRandom){
        StandardBST bst = new StandardBST();
        RedBlackTree rbt = new RedBlackTree();
        BTree bt = new BTree(5);
        Treap tp = new Treap();

        List<BalancedBST> list = new ArrayList<>();
        list.add(bst);
        list.add(rbt);
        list.add(bt);
        list.add(tp);

        for(int i = 1; i <= 10; i++){
            for(BalancedBST tree : list) {
                long total = 0;
                for(int k = 0; k < 200; k++) {
                    for (int j = 0; j < i*1000; j++) {
                        tree.insert(nums.get(j));
                    }
                    long start = System.currentTimeMillis();
                    if(isRandom)
                        for (int j = 0; j < i*1000; j++)
                            tree.delete(nums.get(j));
                    else
                        for (int j = i*1000-1; j >= 0; j--)
                            tree.delete(nums.get(j));
                    total += System.currentTimeMillis()-start;
                    tree.clear();
                }
                System.out.println("delete elements in "+tree.getClass().getName()+" with " + i*1000 + " elements takes "+total+" time");
            }
            System.out.println();
        }
    }

}
