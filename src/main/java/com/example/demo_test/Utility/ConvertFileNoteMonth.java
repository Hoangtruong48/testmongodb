package com.example.demo_test.Utility;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ConvertFileNoteMonth {
    public static Integer constWord = 125;
    public static void main(String[] args) {
        String filePath = "C:\\Users\\acer\\OneDrive\\Desktop\\yeucauthang9.txt";
        String fileSave = "noteNew.txt";
        Set<String> notes =  new HashSet<>();
        Set<String> newKnowLedge = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String temp = line.trim();
                if (temp.charAt(0) == '#'){
                    int index = temp.lastIndexOf("#");
                    String note = temp.substring(index+1).trim();
                    notes.add(note);
                }
                else if (temp.startsWith("---") && temp.charAt(0) == '-'){
                    String knowLedge = temp.substring(temp.indexOf("---") + 3).trim();
                    newKnowLedge.add(knowLedge);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileSave))) {
            writer.write("---------------------------------------------------Những lỗi mắc phải--------------------------------------------------  ");
            writer.newLine(); // Ghi xuống dòng
            for (String note : notes){
                int len = note.length();
                int k = len / constWord;
                writer.write("+, ");
                for (int i = 0; i < k; i++){
                    writer.write(note.substring(i * constWord, i * constWord + constWord));
                    writer.newLine();
                }
                if (k * constWord < len){
                    writer.write(note.substring(k * constWord));
                    writer.newLine();
                }
                writer.newLine();
            }
            writer.newLine();
            writer.write("-------------------------------------------------------Kiến thức mới-------------------------------------------------");
            writer.newLine(); // Ghi xuống dòng
            for (String knowLedge : newKnowLedge){
                int len = knowLedge.length();
                int k = len / constWord;
                writer.write("+, ");
                for (int i = 0; i < k; i++){
                    writer.write(knowLedge.substring(i * constWord, i * constWord + constWord));
                    writer.newLine();
                }
                if (k * constWord < len){
                    writer.write(knowLedge.substring(k * constWord));
                    writer.newLine();
                }
                writer.newLine();
            }
            System.out.println("Ghi ra file thành công.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
