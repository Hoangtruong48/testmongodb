package com.example.demo_test.danh_muc_huyen.real;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.lang.model.type.ArrayType;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SQLHuyen {
    public static void generateSQL(String path){
        FileInputStream inputStream = null;
        Workbook workbook = null;
        String txtFilePath = "generatesql_huyen_case_when.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))){
            inputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(inputStream);
            String sheetName = "khác têngiữa file MOET và SME";
            Sheet sheetUpdateSme = workbook.getSheet(sheetName);
            List<String> huyenSme = new ArrayList<>();
            boolean isFirstRow = true;
            writer.write("\n--------------------------------Update tên trong bảng SME giống với MOET--------------------------" + "\n");
            writer.write("UPDATE SME_CONSTANT" + "\n");
            writer.write("SET CONSTANT_TITLE = CASE CONSTANT_CODE" + "\n");
            for (Row row : sheetUpdateSme){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maHuyen = row.getCell(0).getStringCellValue();
                String tenHuyen = row.getCell(1).getStringCellValue();
                huyenSme.add(maHuyen);
                writer.write("\t" + " WHEN " + "'" + maHuyen + "'" + " THEN '" + tenHuyen + "'" + "\n");
            }
            writer.write("\t" + "ELSE CONSTANT_TITLE" + "\n");
            writer.write("END," + "\n");
            writer.write("MODIFIED_DATE = SYSDATE" + "\n");
            writer.write("WHERE CONSTANT_CODE IN (");
            for (int i = 0; i < huyenSme.size(); i++){
                writer.write("'");
                writer.write(huyenSme.get(i));
                if (i != huyenSme.size()-1){
                    writer.write("', ");
                }
            }
            writer.write("');");

            //
            String sheetName1 = "khác tên giữa MOET và DM_QH";
            Sheet sheetUpdateDmPxa = workbook.getSheet(sheetName1);
            isFirstRow = true;
            List<String> dmHuyen = new ArrayList<>();
            writer.write("\n-----------------------Update tên trong bảng DM_QUAN_HUYEN giống với MOET---------------------------" + "\n");
            writer.write("UPDATE DM_QUAN_HUYEN" + "\n");
            writer.write("SET TEN_QUAN_HUYEN = CASE MA_QUAN_HUYEN" + "\n");
            for (Row row : sheetUpdateDmPxa){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maHuyen = row.getCell(0).getStringCellValue();
                String tenHuyen = row.getCell(1).getStringCellValue();
                dmHuyen.add(maHuyen);
                writer.write("\t" + " WHEN " + "'" + maHuyen + "'" + " THEN '" + tenHuyen + "'" + "\n");
            }
            writer.write("\t" + "ELSE TEN_QUAN_HUYEN" + "\n");
            writer.write("END," + "\n");
            writer.write("NGAY_CAP_NHAT = SYSDATE" + "\n");
            writer.write("WHERE MA_QUAN_HUYEN IN (");
            for (int i = 0; i < dmHuyen.size(); i++){
                writer.write("'");
                writer.write(dmHuyen.get(i));
                if (i != dmHuyen.size()-1){
                    writer.write("', ");
                }
            }
            writer.write("');");
            //
            String sheetName2 = "Có trong DM_QH khôngcó trong MO";
            Sheet sheetUpdateTrangThaiDMPhuongXa = workbook.getSheet(sheetName2);
            isFirstRow = true;
            writer.write("\n--------------------Update trạng thái trong bảng DM_QUAN_HUYEN = 0------------------" + "\n");
            writer.write("UPDATE DM_QUAN_HUYEN SET TRANG_THAI = CASE MA_QUAN_HUYEN \n");
            List<String> dmHuyenn = new ArrayList<>();
            for (Row row : sheetUpdateTrangThaiDMPhuongXa){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maHuyen = row.getCell(0).getStringCellValue();
                String tenHuyen = row.getCell(1).getStringCellValue();
                dmHuyenn.add(maHuyen);
                writer.write("\tWHEN '" + maHuyen + "' THEN 0 \n");
            }
            writer.write("ELSE TRANG_THAI END, \n");
            writer.write("NGAY_CAP_NHAT = SYSDATE" + "\n");
            writer.write("WHERE MA_QUAN_HUYEN IN (");
            for (int i = 0; i < dmHuyenn.size(); i++){
                writer.write("'");
                writer.write(dmHuyenn.get(i));
                if (i != dmHuyenn.size()-1){
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
        generateSQL("E:\\tuyensinhdaucap\\demo_test\\TK_DM_HUYEN.xlsx");
    }
}
