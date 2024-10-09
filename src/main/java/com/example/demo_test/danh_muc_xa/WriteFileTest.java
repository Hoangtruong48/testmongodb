package com.example.demo_test.danh_muc_xa;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class WriteFileTest {
    public static void thongKeAllTest(String path1, String path2){
        long start = System.currentTimeMillis();
//        Map<String, String> dmXa = new HashMap<>();
//        Map<String, String> dmPhuongXa = new HashMap<>();
//        Map<String, String> sme = new HashMap<>();
        Map<String, String> dmXa = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> dmPhuongXa = Collections.synchronizedMap(new HashMap<>());
//        Map<String, String> sme = Collections.synchronizedMap(new HashMap<>());
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> read1 = CompletableFuture.runAsync(() -> {
            try (FileInputStream fis = new FileInputStream(path1)) {
                // Tạo Workbook instance từ file Excel
                Workbook workbook = new XSSFWorkbook(fis);
                // Lấy sheet đầu tiên từ workbook
                Sheet sheet = workbook.getSheetAt(0);
                // Duyệt qua các hàng, bắt đầu từ hàng thứ 2 (index 1)
                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row != null) {
                        Cell cell3 = row.getCell(2);
                        Cell cell4 = row.getCell(3);
                        String maXa = cell3.getStringCellValue().substring(1);
                        String tenXa = null;
                        switch (cell4.getCellTypeEnum()) {
                            case STRING:
                                tenXa = cell4.getStringCellValue();
                                break;
                            case NUMERIC:
                                tenXa = String.valueOf(cell4.getNumericCellValue());
                                break;
                            default:
                                System.out.print("Cột 4: Unknown Value\t");
                                break;
                        }
//                        if (StringUtils.hasLength(maXa) && StringUtils.hasLength(tenXa)) {
//                            dmXa.put(maXa, tenXa);
//                        }
                        dmXa.put(maXa, tenXa);
                    }
                }
                workbook.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }, executorService);
        CompletableFuture<Void> read2 = CompletableFuture.runAsync(() -> {
            try (FileInputStream fis1 = new FileInputStream(path2)) {
                // Tạo Workbook instance từ file Excel
                Workbook workbook = new XSSFWorkbook(fis1);

                // Lấy sheet đầu tiên từ workbook
                Sheet sheet = workbook.getSheetAt(0);

                // Duyệt qua các hàng, bắt đầu từ hàng thứ 2 (index 1)
                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row != null) {
                        Cell cell1 = row.getCell(0);
                        Cell cell2 = row.getCell(1);
                        String maXa = cell1.getStringCellValue();
                        String tenXa = cell2.getStringCellValue();
//                        if (StringUtils.hasLength(maXa) && StringUtils.hasLength(tenXa)) {
//                            dmPhuongXa.put(maXa, tenXa);
//                        }
                        dmPhuongXa.put(maXa, tenXa);
                    }
                }
                workbook.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        }, executorService);

        futures.add(read1);
        futures.add(read2);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();



        /////-----------------------------------------------////////
        // ghi ra file
        Workbook workbook = new XSSFWorkbook();

        // Tạo một sheet mới
        Sheet sheet = workbook.createSheet("Giống nhau giữa MOET và DM_PXA");
//        Sheet sheet1 = workbook.createSheet("Giống nhau giữa MOET và SME");
        Sheet sheet2 = workbook.createSheet("khác tên giữa MOET và DM_PXA");
//        Sheet sheet3 = workbook.createSheet("khác têngiữa file MOET và SME");
        Sheet sheet4 = workbook.createSheet("Có trong MOET khôngcó trong DM_PXA");
        Sheet sheet5 = workbook.createSheet("Có trong DM_PXA khôngcó trong MOET");
//        Sheet sheet6 = workbook.createSheet("Có trong MOET khôngcó trong SME");
//        Sheet sheet7 = workbook.createSheet("Có trong SME không trong MOET");
        Sheet sheet8 = workbook.createSheet("Tên file");
        // Tạo hàng đầu tiên để lưu tiêu đề cột
        Row headerRow = sheet.createRow(0); // Hàng đầu tiên (chỉ số 0)
        headerRow.createCell(0).setCellValue("Mã xã");  // Cột đầu tiên: "Mã xã"
        headerRow.createCell(1).setCellValue("Tên xã"); // Cột thứ hai: "Tên xã"

//        Row headerRow1 = sheet1.createRow(0); // Hàng đầu tiên (chỉ số 0)
//        headerRow1.createCell(0).setCellValue("Mã xã");  // Cột đầu tiên: "Mã xã"
//        headerRow1.createCell(1).setCellValue("Tên xã"); // Cột thứ hai: "Tên xã"

        Row headerRow2 = sheet2.createRow(0); // Hàng đầu tiên (chỉ số 0)
        headerRow2.createCell(0).setCellValue("Mã xã");  // Cột đầu tiên: "Mã xã"
        headerRow2.createCell(1).setCellValue("Tên xã trong file danh mục xã"); // Cột thứ hai: "Tên xã"
        headerRow2.createCell(2).setCellValue("Tên xã trong file DM phường xã");

//        Row headerRow3 = sheet3.createRow(0); // Hàng đầu tiên (chỉ số 0)
//        headerRow3.createCell(0).setCellValue("Mã xã");  // Cột đầu tiên: "Mã xã"
//        headerRow3.createCell(1).setCellValue("Tên xã trong file danh mục xã"); // Cột thứ hai: "Tên xã"
//        headerRow3.createCell(2).setCellValue("Tên xã trong file SME");

        Row headerRow4 = sheet4.createRow(0); // Hàng đầu tiên (chỉ số 0)
        headerRow4.createCell(0).setCellValue("Mã xã");  // Cột đầu tiên: "Mã xã"
        headerRow4.createCell(1).setCellValue("Tên xã"); // Cột thứ hai: "Tên xã"

        Row headerRow5 = sheet5.createRow(0); // Hàng đầu tiên (chỉ số 0)
        headerRow5.createCell(0).setCellValue("Mã xã");  // Cột đầu tiên: "Mã xã"
        headerRow5.createCell(1).setCellValue("Tên xã"); // Cột thứ hai: "Tên xã"

//        Row headerRow6 = sheet6.createRow(0); // Hàng đầu tiên (chỉ số 0)
//        headerRow6.createCell(0).setCellValue("Mã xã");  // Cột đầu tiên: "Mã xã"
//        headerRow6.createCell(1).setCellValue("Tên xã"); // Cột thứ hai: "Tên xã"

//        Row headerRow7 = sheet7.createRow(0); // Hàng đầu tiên (chỉ số 0)
//        headerRow7.createCell(0).setCellValue("Mã xã");  // Cột đầu tiên: "Mã xã"
//        headerRow7.createCell(1).setCellValue("Tên xã"); // Cột thứ hai: "Tên xã"

        int rowCount8 = 1;
        Row headerRow8 = sheet8.createRow(0);
        headerRow8.createCell(0).setCellValue("Tên viết tắt");
        headerRow8.createCell(1).setCellValue("Ý nghĩa");
        Row row1 = sheet8.createRow(rowCount8++);
        row1.createCell(0).setCellValue("DM_PXA");
        row1.createCell(1).setCellValue("Dữ liệu trong bảng DM_PHUONG_XA");
        Row row2 = sheet8.createRow(rowCount8++);
        row2.createCell(0).setCellValue("SME");
        row2.createCell(1).setCellValue("Dữ liệu trong bảng SME_CONSTANT");
        Row row3 = sheet8.createRow(rowCount8++);
        row3.createCell(0).setCellValue("MOET");
        row3.createCell(1).setCellValue("Dữ liệu trong file excel");

        int rowCount = 1;
        int rowCount1 = 1;
        int rowCount2 = 1;
        int rowCount3 = 1;
        int rowCount4 = 1;
        AtomicInteger rowCount5 = new AtomicInteger(1);
        int rowCount6 = 1;
        AtomicInteger rowCount7 = new AtomicInteger(1);
        for (Map.Entry<String, String> entry : dmXa.entrySet()) {
            if (dmPhuongXa.containsKey(entry.getKey())) {
                if (dmPhuongXa.get(entry.getKey()).equals(entry.getValue())) {
                    Row row = sheet.createRow(rowCount++);
                    row.createCell(0).setCellValue(entry.getKey());   // Ghi mã xã vào cột đầu tiên
                    row.createCell(1).setCellValue(entry.getValue()); // Ghi tên xã vào cột thứ hai
                } else {
                    Row row = sheet2.createRow(rowCount2++);
                    row.createCell(0).setCellValue(entry.getKey());   // Ghi mã xã vào cột đầu tiên
                    row.createCell(1).setCellValue(entry.getValue()); // Ghi tên xã vào cột thứ hai
                    row.createCell(2).setCellValue(dmPhuongXa.get(entry.getKey()));
                }
            } else {
                Row row = sheet4.createRow(rowCount4++);
                row.createCell(0).setCellValue(entry.getKey());   // Ghi mã xã vào cột đầu tiên
                row.createCell(1).setCellValue(entry.getValue()); // Ghi tên xã vào cột thứ hai
            }
//            if (sme.containsKey(entry.getKey())) {
//                if (sme.get(entry.getKey()).equals(entry.getValue())) {
////                    Row row = sheet1.createRow(rowCount1++);
////                    row.createCell(0).setCellValue(entry.getKey());   // Ghi mã xã vào cột đầu tiên
////                    row.createCell(1).setCellValue(entry.getValue()); // Ghi tên xã vào cột thứ hai
//                } else {
////                    Row row = sheet3.createRow(rowCount3++);
////                    row.createCell(0).setCellValue(entry.getKey());   // Ghi mã xã vào cột đầu tiên
////                    row.createCell(1).setCellValue(entry.getValue()); // Ghi tên xã vào cột thứ hai
////                    row.createCell(2).setCellValue(sme.get(entry.getKey()));
//                }
//            } else {
//                Row row = sheet6.createRow(rowCount6++);
//                row.createCell(0).setCellValue(entry.getKey());   // Ghi mã xã vào cột đầu tiên
//                row.createCell(1).setCellValue(entry.getValue()); // Ghi tên xã vào cột thứ hai
//            }
        }

        CompletableFuture<Void> write1 = CompletableFuture.runAsync(() -> {
            for (Map.Entry<String, String> entry : dmPhuongXa.entrySet()) {
                if (!dmXa.containsKey(entry.getKey())){
                    Row row = sheet5.createRow(rowCount5.getAndIncrement());
                    row.createCell(0).setCellValue(entry.getKey());   // Ghi mã xã vào cột đầu tiên
                    row.createCell(1).setCellValue(entry.getValue()); // Ghi tên xã vào cột thứ hai
                }
            }
        }, executorService);
//        CompletableFuture<Void> write2 = CompletableFuture.runAsync(() -> {
//            for (Map.Entry<String, String> entry : sme.entrySet()) {
//                if (!dmXa.containsKey(entry.getKey())){
//                    Row row = sheet7.createRow(rowCount7.getAndIncrement());
//                    row.createCell(0).setCellValue(entry.getKey());   // Ghi mã xã vào cột đầu tiên
//                    row.createCell(1).setCellValue(entry.getValue());
//                    if (entry.getKey().equals("11107")) {
//                        System.out.println(entry.getKey() + " " + entry.getValue());
//                    }
//                }
//            }
//        }, executorService);
        CompletableFuture.allOf(new CompletableFuture[]{write1}).join();
        executorService.shutdown();
        //---//
        // Tạo hàng đầu tiên để lưu tiêu đề cột

        // Ghi workbook ra file Excel
        try (FileOutputStream outputStream = new FileOutputStream("TK_DM_PXA_TEST_3.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        // Đóng workbook
        try {
            workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void main(String[] args) {
        thongKeAllTest("E:\\tuyensinhdaucap\\demo_test\\src\\main\\java\\com\\example\\demo_test\\danh_muc_xa\\danhmucxa.xlsx",
                "E:\\tuyensinhdaucap\\demo_test\\src\\main\\java\\com\\example\\demo_test\\danh_muc_xa\\dm_pxa_test_3.xlsx");
    }
}
