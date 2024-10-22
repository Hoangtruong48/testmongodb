package com.example.demo_test.danh_muc_xa;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class WriteSQL {
    public static void generateSQL(String path){
        FileInputStream inputStream = null;
        Workbook workbook = null;
        String txtFilePath = "generatesql_casewhen.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))){
            inputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(inputStream);
            String sheetName = "khác têngiữa file MOET và SME";
            Sheet sheetUpdateSme = workbook.getSheet(sheetName);
            boolean isFirstRow = true;
            List<String> maXas = new ArrayList<>();
            writer.write("\n--------------------------------Update tên trong bảng SME giống với MOET--------------------------" + "\n");
            writer.write("UPDATE SME_CONSTANT" + "\n");
            writer.write("SET CONSTANT_TITLE = CASE CONSTANT_CODE" + "\n");
            for (Row row : sheetUpdateSme){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maXa = row.getCell(0).getStringCellValue();
                String tenXa = row.getCell(1).getStringCellValue();
                maXas.add(maXa);
                writer.write("\t" + " WHEN " + "'" + maXa + "'" + " THEN '" + tenXa + "'" + "\n");
            }
            writer.write("\t" + "ELSE CONSTANT_TITLE" + "\n");
            writer.write("END," + "\n");
            writer.write("MODIFIED_DATE = SYSDATE" + "\n");
            writer.write("WHERE CONSTANT_CODE IN (");
            for (int i = 0; i < maXas.size(); i++){
                writer.write("'");
                writer.write(maXas.get(i));
                if (i != maXas.size()-1){
                    writer.write("', ");
                }
            }
            writer.write("');");
            //
            String sheetName1 = "khác tên giữa MOET và DM_PXA";
            Sheet sheetUpdateDmPxa = workbook.getSheet(sheetName1);
            isFirstRow = true;
            writer.write("\n-----------------------Update tên trong bảng DM_PHUONG_XA giống với MOET---------------------------" + "\n");
            writer.write("UPDATE DM_PHUONG_XA" + "\n");
            writer.write("SET TEN_PHUONG_XA = CASE MA_PHUONG_XA" + "\n");
            List<String> maXaDmPxa = new ArrayList<>();
            for (Row row : sheetUpdateDmPxa){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maXa = row.getCell(0).getStringCellValue();
                String tenXa = row.getCell(1).getStringCellValue();
                maXaDmPxa.add(maXa);
                writer.write("\t" + " WHEN " + "'" + maXa + "'" + " THEN '" + tenXa + "'" + "\n");
            }
            writer.write("\t" + "ELSE TEN_PHUONG_XA" + "\n");
            writer.write("END," + "\n");
            writer.write("NGAY_CAP_NHAT = SYSDATE" + "\n");
            writer.write("WHERE MA_PHUONG_XA IN (");
            for (int i = 0; i < maXaDmPxa.size(); i++){
                writer.write("'");
                writer.write(maXaDmPxa.get(i));
                if (i != maXaDmPxa.size()-1){
                    writer.write("', ");
                }
            }
            writer.write("');");

            //
            String sheetName2 = "Có trong DM_PXA khôngcó trong M";
            Sheet sheetUpdateTrangThaiDMPhuongXa = workbook.getSheet(sheetName2);
            isFirstRow = true;
            List<String> maXass = new ArrayList<>();
            writer.write("\n--------------------Update trạng thái trong bảng dmphuongxa = 0------------------" + "\n");
            writer.write("UPDATE DM_PHUONG_XA SET TRANG_THAI = CASE MA_PHUONG_XA \n");
            for (Row row : sheetUpdateTrangThaiDMPhuongXa){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maXa = row.getCell(0).getStringCellValue();
                String tenXa = row.getCell(1).getStringCellValue();
                maXass.add(maXa);
                writer.write("\tWHEN '" + maXa + "' THEN 0 \n");
            }
            writer.write("ELSE TRANG_THAI END, \n");
            writer.write("NGAY_CAP_NHAT = SYSDATE" + "\n");
            writer.write("WHERE MA_PHUONG_XA IN (");
            for (int i = 0; i < maXass.size(); i++){
                writer.write("'");
                writer.write(maXass.get(i));
                if (i != maXass.size()-1){
                    writer.write("', ");
                }
            }
            writer.write("');");
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
            }
        }

    }
    private static String getCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    public static void main(String[] args) {
        generateSQL("C:\\Users\\acer\\Downloads\\Telegram Desktop\\TK_DM_PXA.xlsx");
    }
}
