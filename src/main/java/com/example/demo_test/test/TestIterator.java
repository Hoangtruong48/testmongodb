package com.example.demo_test.test;

import java.util.ArrayList;
import java.util.Iterator;

public class TestIterator {
    public static void main(String[] args) {
        ArrayList<Integer> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            lists.add(i);
        }
        Iterator var = lists.iterator();
        while(var.hasNext()){
            System.out.println(var.next());
        }
    }
}
