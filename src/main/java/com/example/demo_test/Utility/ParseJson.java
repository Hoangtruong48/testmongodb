package com.example.demo_test.Utility;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class ParseJson {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
//        try {
//            Map<String, String> map = new HashMap<>();
//            String line = sc.nextLine();
//            String[] arrayLine = line.split("=");
//            for (String s : arrayLine) {
//                int position = s.indexOf(":");
//                String key = s.substring(0, position);
//                String value = s.substring(position + 1);
//                map.put(key, value);
//            }
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonString = objectMapper.writeValueAsString(map);
//
//            // In chuỗi JSON
//            System.out.println(jsonString);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        List<Integer> list3 = list1;
    }
}
