package com.example.demo_test.test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiTheardTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Random random = new Random();
        int n = 1000001;
        int[] sieve = new int[n + 1];
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());
        List<Integer> arrayRandom = new ArrayList<>();
        for (int i = 0; i < n; i++){
            arrayRandom.add(random.nextInt(n));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        futures.add(CompletableFuture.runAsync(() -> Test.eratosthenes(sieve, n), executorService));
        futures.add(CompletableFuture.runAsync(() -> {
            for (Integer integer : arrayRandom) {
                map.put(integer, 1);
            }
        }, executorService));
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Time : " + (endTime - startTime));
    }
}
