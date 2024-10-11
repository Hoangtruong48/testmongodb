package com.example.demo_test.danh_muc_xa;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;

@Slf4j
public class WriterFileTxtTest {
    public static void main(String[] args) {
        String filePath = "testTxtht48.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write("user : htruong48 \n");
            bw.write("Name : Hoang Truong \n");
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
    }
}
