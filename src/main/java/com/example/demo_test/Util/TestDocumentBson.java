package com.example.demo_test.Util;

import org.bson.Document;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class TestDocumentBson {
    private static Random rand = new Random();
    public static void InsertEvenNumber(Document map){
        for (int i = 0; i < 1000000; i++){
            map.put(String.valueOf(2 * rand.nextInt(2299)), String.valueOf(i));
        }
    }
    public static void main(String[] args) {
        Document map = new Document();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {});
    }
}
