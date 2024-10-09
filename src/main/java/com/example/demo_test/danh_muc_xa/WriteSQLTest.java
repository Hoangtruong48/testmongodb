package com.example.demo_test.danh_muc_xa;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class WriteSQLTest {
    public static void generateSQL(String path){
        FileInputStream inputStream = null;
        Workbook workbook = null;
        String txtFilePath = "generatesql_test.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))){
            inputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(inputStream);
//            String sheetName = "khác têngiữa file MOET và SME";
//            Sheet sheetUpdateSme = workbook.getSheet(sheetName);
            boolean isFirstRow = true;
//            writer.write("Update tên trong bảng SME giống với MOET" + "\n");
//            for (Row row : sheetUpdateSme){
//                if (isFirstRow){
//                    isFirstRow = false;
//                    continue;
//                }
//                String maXa = row.getCell(0).getStringCellValue();
//                String tenXa = row.getCell(1).getStringCellValue();
//                String updateSme = "UPDATE SME_CONSTANT SET CONSTANT_TITLE = \"" + tenXa +
//                        "\" WHERE CONSTANT_TYPE LIKE \"DM_XA\" AND CONSTANT_CODE = " + "\"" + maXa + "\"";
//                writer.write(updateSme + "\n");
//            }

            //
            String sheetName1 = "khác tên giữa MOET và DM_PXA";
            Sheet sheetUpdateDmPxa = workbook.getSheet(sheetName1);
            writer.write("------------------Update tên trong bảng DM_PHUONG_XA giống với MOET------" + "\n\n\n");
            for (Row row : sheetUpdateDmPxa){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maXa = row.getCell(0).getStringCellValue();
                String tenXa = row.getCell(1).getStringCellValue();
                String updateDmPxa = "UPDATE DM_PHUONG_XA SET TEN_PHUONG_XA = '" + tenXa +"', NGAY_CAP_NHAT = SYSDATE WHERE MA_PHUONG_XA " +
                        "= '" + maXa + "';";
                writer.write(updateDmPxa + "\n");
            }
            //
            String sheetName2 = "Có trong DM_PXA khôngcó trong M";
            Sheet sheetUpdateTrangThaiDMPhuongXa = workbook.getSheet(sheetName2);
            isFirstRow = true;
            writer.write("\n-------------------------Update trạng thái trong bảng dmphuongxa = 0" + "\n\n");
            for (Row row : sheetUpdateTrangThaiDMPhuongXa){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maXa = row.getCell(0).getStringCellValue();
                String tenXa = row.getCell(1).getStringCellValue();
                String updateTrangThai = "UPDATE DM_PHUONG_XA SET TRANG_THAI = 0, NGAY_CAP_NHAT = SYSDATE WHERE MA_PHUONG_XA LIKE '" + maXa
                        + "' AND TEN_PHUONG_XA LIKE '" + tenXa +"';";
                writer.write(updateTrangThai + "\n");
            }
        } catch(Exception e){
            log.error(e.getMessage());
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
                log.debug("AAAAAAAAA");
            }
        }

    }

    public static void main(String[] args) {
        generateSQL("E:\\tuyensinhdaucap\\demo_test\\TK_DM_PXA_TEST.xlsx");
    }
}
