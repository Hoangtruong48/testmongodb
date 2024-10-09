package com.example.demo_test.service;


import com.example.demo_test.model.Student;
import com.example.demo_test.repo.StudentRepo;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.iterators.EntrySetMapIterator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    public List<Student> findAllStudentByTenSV(String tenSV) {
        if (ObjectUtils.isEmpty(tenSV)) {
            return studentRepo.findAll();
        }

        return studentRepo.findAllByTenSV(tenSV);
    }
    public Student addNewStudent(Student student){
        if (ObjectUtils.isEmpty(student.getNgayTao())) {
            student.setNgayTao(String.valueOf(LocalDate.now()));
        }
        return studentRepo.save(student);
    }
    public List<Student> findAllByKhoaAndOrderByTenSV(String khoa){
        if (ObjectUtils.isEmpty(khoa)) {
            return studentRepo.findAll(Sort.by(Sort.Direction.ASC, "tenSV"));
        }

        return studentRepo.findALlByKhoaAndOrderByTenSV(khoa);
    }

    public void add10000Student(){
        studentRepo.add10000StudentToDB();
    }

    public List<Student> findStudentByKhoaLimit(String khoa, int start, int limit){
        return studentRepo.getStudentByKhoaPageable(khoa, start, limit);
    }

    public Long updateStudentByKhoaAndKhoaHoc(String khoa, Integer khoaHoc){
        return studentRepo.updateStudentByKhoaAndKhoaHoc(khoa, khoaHoc);
    }
    public static int minimumNumber(int n, String password) {
        boolean isLower = false, isUpper = false, isNum = false, isSpe = false;
        Set<Character> specialChars = new HashSet<>(Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'));
        for (int i = 0; i < n; i++){
            if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z'){
                isLower = true;
            }
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z'){
                isUpper = true;
            }
            if (password.charAt(i) >= '0' && password.charAt(i) <= '9'){
                isNum = true;
            }
            if (specialChars.contains(password.charAt(i))){
                isSpe = true;
            }
        }
        int cnt = 0;
        if (!isLower){
            cnt++;
            n++;
        }
        if (!isUpper){
            cnt++;
            n++;
        }
        if (!isNum){
            cnt++;
            n++;
        }
        if (!isSpe){
            cnt++;
            n++;
        }
        if (n < 6){
            cnt += (6 - n);
        }
        return cnt;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        List<Integer> list_add_number = new ArrayList<>();
//        CompletableFuture<Void> test = CompletableFuture.runAsync(() -> {
//            int cnt = 0;
//            while (cnt < 10){
//                list_add_number.add(cnt);
//                cnt++;
//            }
//        });
//        test.get();
//        CompletableFuture<List<Integer>> list_test = CompletableFuture.supplyAsync(() -> {
//            List<Integer> list = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                list.add(i);
//            }
//            return list;
//        });
//        System.out.println(list_add_number);
//        System.out.println(list_test.get());
//        int n = 2929922;
//        int[] sieve = new int[n + 1];
//        CompletableFuture<Void> testSieve = CompletableFuture.runAsync(() -> {
//           for (int i = 0; i <= n; i++) {
//               sieve[i] = 1;
//           }
//           sieve[0] = 0;
//           sieve[1] = 0;
//           for (int i = 2; i * i <= n; i++){
//               if (sieve[i] == 1){
//                   for (int j = i * i; j <= n; j += i){
//                       sieve[j] = 0;
//                   }
//               }
//           }
//        });
//        testSieve.get();
//        for (int i = 1; i <= 1000; i++){
//            if (sieve[i] == 1){
//                System.out.print(i + " --- ");
//            }
//        }
//        System.out.println("\n");
//        CompletableFuture<Void> checkPrime = CompletableFuture.runAsync(() -> {
//            long num = 1000000007L;
//            boolean isPrime = true;
//            for (long i = 2; i * i <= num; i++){
//                if (num % i == 0){
//                    isPrime = false;
//                    break;
//                }
//            }
//            System.out.println(isPrime ? "True" : "False");
//        });
//        CompletableFuture.allOf(checkPrime, testSieve).join();
        // -----
//        HashMap<Pair<Integer, Integer>, Integer> map = new HashMap<>();
//        Pair<Integer, Integer> a = new Pair<>(1, 1);
//        Pair<Integer, Integer> b = new Pair<>(2, 2);
//        map.put(a, 2);
//        map.put(b, 2);
//        for (Map.Entry<Pair<Integer, Integer>, Integer> entry : map.entrySet()){
//            System.out.println(map.get(entry.getKey()));
//        }
        int value = 11;
        int iter = 48;
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());
        Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        try{
            futures.add(CompletableFuture.runAsync(() -> {
                addValueIntoMap(map, value, iter);
            }, executorService));
            futures.add(CompletableFuture.runAsync(() -> {
                addValueIntoSet(set, value, iter);
            }, executorService));
        } catch(Exception e){
            System.out.println(e.getMessage());
        } finally {
            executorService.shutdown();
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("--> Set and Map after completableFuture: ");
        for (Map.Entry<String, Integer> iterator : map.entrySet()){
            System.out.println(iterator.getKey() + " " + iterator.getValue());
        }
        System.out.println("---- SET -----");
        for (Integer val : set){
            System.out.println(val);
        }
    }
    private static void addValueIntoMap(Map<String, Integer> map, int value, int iter){
        for (int i = 0; i < iter; i++){
            map.put(String.valueOf(i + 1), value);
        }
    }
    private static void addValueIntoSet(Set<Integer> se, int value, int iter){
        for (int i = 0; i < iter; i++){
            se.add((i * value + value));
        }
    }
}
