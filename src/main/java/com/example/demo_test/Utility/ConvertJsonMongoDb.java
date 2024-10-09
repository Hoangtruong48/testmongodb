package com.example.demo_test.Utility;

import java.io.*;
import java.util.Scanner;

public class ConvertJsonMongoDb {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Đường dẫn đến file đầu vào và đầu ra
        String inputFilePath = "inputjson.txt";  // Thay bằng đường dẫn file JSON của bạn
        String outputFilePath = "outputjson.txt"; // File đầu ra để ghi JSON chuẩn

        StringBuilder jsonInput = new StringBuilder();

        // Đọc file .txt
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonInput.append(line.trim()); // Ghi dữ liệu từng dòng vào StringBuilder
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return;
        }

        // Loại bỏ dấu {} bao quanh JSON
        String s = jsonInput.toString().trim();
        s = s.substring(1, s.length() - 1);

        // Tách các cặp khóa-giá trị trong JSON
        String[] sub = s.split(",(?=\\s*\"[^\"]+\"\\s*:)"); // Sử dụng regex để tách đúng cặp khóa-giá trị
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for (String x : sub) {
            String[] sub_sub = x.split(":", 2); // Chia làm 2 phần: khóa và giá trị

            // Xử lý khóa (sub_sub[0])
            sb.append(sub_sub[0].trim()).append(" : ");

            // Xử lý giá trị (sub_sub[1])
            String value = sub_sub[1].trim();
            if (value.startsWith("NumberInt") || value.startsWith("NumberLong")) {
                // Lấy giá trị bên trong dấu ngoặc tròn
                int begin = value.indexOf('(');
                int end = value.indexOf(')');
                sb.append(value.substring(begin + 1, end).trim());
            } else if (value.startsWith("ObjectId")) {
                // Xử lý ObjectId, giữ lại giá trị bên trong dấu ngoặc kép
                int begin = value.indexOf('"');
                int end = value.lastIndexOf('"');
                sb.append("\"").append(value.substring(begin + 1, end)).append("\"");
            } else {
                // Trường hợp giá trị là chuỗi hoặc các giá trị JSON chuẩn khác
                sb.append(value);
            }

            sb.append(",\n");
        }

        // Loại bỏ dấu phẩy cuối cùng và thêm dấu đóng ngoặc }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2); // Loại bỏ dấu phẩy và newline cuối cùng
        }
        sb.append("\n}");

        // Ghi JSON chuẩn ra file .txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(sb.toString());  // Ghi nội dung của StringBuilder ra file
            System.out.println("Đã ghi JSON chuẩn ra file: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
}
