package com.example.demo_test.test;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static int countSubString(String s1, String s2){
        if (s1.length() < s2.length()){
            String temp = s1;
            s1 = s2;
            s2 = temp;
        }
        int len1 = s1.length();
        int len2 = s2.length();
        System.out.println(s1 + " " + s2);
        int cnt = 0, cnt1 = 0;
        for (int i = 0; i < len1; i++){
            int temp = i;
            for (int j = 0; j < len2; j++){
                if (s1.charAt(temp) == s2.charAt(j)){
                    temp++;
                    cnt1++;
                } else {
                    cnt1 = 0;
                    break;
                }
                if (cnt1 == len2){
                    cnt++;
                    cnt1 = 0;
                }
            }
        }
        return cnt;
    }

    public static void eratosthenes(int[] sieve, int n){
        for (int i = 0; i <= n; i++){
            sieve[i] = 1;
        }
        sieve[0] = 0; sieve[1] = 0;
        for (int i = 2; i * i <= n; i++){
            if (sieve[i] == 1){
                for (int j = i * i; j <= n; j += i){
                    sieve[j] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
//        int n = 500;
//        int[] sieve = new int[n + 1];
//        eratosthenes(sieve, n);
//        for (int i = 2; i <= n; i++){
//            if (sieve[i] == 1){
//                System.out.print(i + " - ");
//            }
//        }
        //test2
        System.out.println(countSubString("aaaaaa", "a"));
        // test 3
//        List<String> levels = new ArrayList<>(Arrays.asList("P", "K", "T13", "T16", "T18"));
//        String test = "K";
//        int index = levels.indexOf(test);
        // test 4
        String testtt = null;
        System.out.println(testtt.length());
        
    }
}
