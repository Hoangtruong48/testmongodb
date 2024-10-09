package com.example.demo_test.danh_muc_xa;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;


@Slf4j
public class WriteSQL {
    public static void generateSQL(String path){
        FileInputStream inputStream = null;
        Workbook workbook = null;
        String txtFilePath = "generatesql.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))){
            inputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(inputStream);
            String sheetName = "khác têngiữa file MOET và SME";
            Sheet sheetUpdateSme = workbook.getSheet(sheetName);
            boolean isFirstRow = true;
            writer.write("\n--------------------------------Update tên trong bảng SME giống với MOET--------------------------" + "\n");
            for (Row row : sheetUpdateSme){
                if (isFirstRow){
                    isFirstRow = false;
                    continue;
                }
                String maXa = row.getCell(0).getStringCellValue();
                String tenXa = row.getCell(1).getStringCellValue();
                String updateSme = "UPDATE SME_CONSTANT SET CONSTANT_TITLE = '" + tenXa +
                        "', MODIFIED_DATE = SYSDATE WHERE CONSTANT_TYPE LIKE 'DM_XA' AND CONSTANT_CODE = " + "'" + maXa + "';";
                writer.write(updateSme + "\n");
            }

            //
            String sheetName1 = "khác tên giữa MOET và DM_PXA";
            Sheet sheetUpdateDmPxa = workbook.getSheet(sheetName1);
            isFirstRow = true;
            writer.write("\n-----------------------Update tên trong bảng DM_PHUONG_XA giống với MOET---------------------------" + "\n");
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
            writer.write("\n--------------------Update trạng thái trong bảng dmphuongxa = 0------------------" + "\n");
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
