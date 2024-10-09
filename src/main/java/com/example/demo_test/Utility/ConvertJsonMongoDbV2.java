package com.example.demo_test.Utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertJsonMongoDbV2 {

    public static void main(String[] args) {
        // Đường dẫn đến file đầu vào và đầu ra
        String inputFilePath = "inputjson.txt";  // Đường dẫn file txt đầu vào chứa JSON 1 dòng
        String outputFilePath = "outputfile.json"; // Đường dẫn file JSON đầu ra

        // Đọc dữ liệu từ file đầu vào
        StringBuilder jsonInput = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line.trim());  // Đọc từng dòng và loại bỏ khoảng trắng
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Chuyển đổi và chuẩn hóa JSON
        String normalizedJson = normalizeJsonString(jsonInput.toString());

        // Ghi dữ liệu chuẩn hóa ra file JSON
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(normalizedJson);
            System.out.println("JSON chuẩn đã được ghi ra file: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm chuẩn hóa chuỗi JSON và định dạng lại thành JSON chuẩn
    private static String normalizeJsonString(String jsonString) {
        // Xử lý các trường hợp đặc biệt nếu có, ví dụ ObjectId, ISODate, NumberLong
        jsonString = jsonString.replaceAll("ObjectId\\((.*?)\\)", "\"$1\"");
        jsonString = jsonString.replaceAll("ISODate\\((.*?)\\)", "\"$1\"");
        jsonString = jsonString.replaceAll("NumberLong\\((.*?)\\)", "$1");

        // Xử lý các trường hợp đặc biệt khác như danh sách các đối tượng trong một mảng
        jsonString = handleNestedObjects(jsonString);

        // Định dạng lại JSON cho đẹp bằng Jackson
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // Định dạng đẹp (pretty print)

            // Đọc chuỗi JSON thành đối tượng Map
            Object jsonObject = mapper.readValue(jsonString, Object.class);

            // Ghi lại JSON chuẩn với định dạng đẹp
            return mapper.writeValueAsString(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
            return jsonString;  // Trả về chuỗi ban đầu nếu có lỗi
        }
    }

    // Hàm xử lý các đối tượng lồng nhau
    private static String handleNestedObjects(String jsonString) {
        // Tìm các đối tượng lồng nhau có dạng [{...},{...}]
        Pattern pattern = Pattern.compile("\\{[^{}]*\\}");
        Matcher matcher = pattern.matcher(jsonString);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String nestedObject = matcher.group();
            String cleanedObject = nestedObject
                    .replaceAll("ObjectId\\((.*?)\\)", "\"$1\"")
                    .replaceAll("ISODate\\((.*?)\\)", "\"$1\"")
                    .replaceAll("NumberLong\\((.*?)\\)", "$1");
            matcher.appendReplacement(result, cleanedObject);
        }
        matcher.appendTail(result);

        return result.toString();
    }

}
