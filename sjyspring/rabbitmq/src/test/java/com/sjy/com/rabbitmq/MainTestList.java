package com.sjy.com.rabbitmq;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainTestList {

    boolean isLeafAP(Node root) {

        ArrayList<Integer> integers = new ArrayList<>();
        getVal(integers,root);
        Integer val2 = integers.get(1);
        Integer val1 = integers.get(0);
        int cha = val2 - val1;
        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i)+cha!=integers.get(i+1)) {
                return false;
            }
        }
        return true;
    }

    public void getVal(ArrayList<Integer> integers ,Node node){
        if (node!=null) {
            if (node.left!=null) {
                getVal(integers,node.left);
            }
            integers.add(node.val);

            if (node.re!=null) {
                getVal(integers,node.re);
            }
        }
    }

    class Node{
        Node left;
        Node re;
        int val;
    }
}
