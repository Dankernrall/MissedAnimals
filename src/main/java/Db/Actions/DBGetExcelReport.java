package Db.Actions;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Date;

public class DBGetExcelReport {
    public static void getExcelReport(String dir) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.h2.Driver");
            conn = ConnectionToDB.getConnection();
            stmt = conn.createStatement();

            Workbook book = new HSSFWorkbook();
            Sheet sheet = book.createSheet("Animals");

            ResultSet rs = stmt.executeQuery(
                "SELECT ALLANIMALS.ID,ALLANIMALS.NAME, ANIMALTYPE.TYPE_NAME, ALLANIMALS.WEIGHT,ALLANIMALS.STERILIZED,ALLANIMALS.DATE_OF_RECEIPT\n" +
                    "FROM ALLANIMALS,ANIMALTYPE\n" +
                    "WHERE ALLANIMALS.TYPE = ANIMALTYPE.ID");
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            int rowCount = 1;

            while (rs.next()) {
                Row rowNames = sheet.createRow(0);
                Row row = sheet.createRow(rowCount++);
                for (int i = 2; i <= numberOfColumns + 1; i++) {
                    Cell cellNames = rowNames.createCell(i - 2);
                    Object valueObject = rs.getObject(i - 1);
                    Cell cell = row.createCell(i - 2);
                    cellNames.setCellValue(metaData.getColumnName(i - 1));
                    if (valueObject instanceof Boolean) {
                        cell.setCellValue((Boolean) valueObject == true ? "Да" : "Нет");
                    } else if (valueObject instanceof Integer) {
                        cell.setCellValue(Integer.parseInt(valueObject.toString()));
                    } else if (valueObject instanceof Double) {
                        cell.setCellValue(Double.parseDouble(valueObject.toString()));
                    } else if (valueObject instanceof Float) {
                        cell.setCellValue(Float.parseFloat(valueObject.toString()));
                    } else if (valueObject instanceof Date) {
                        cell.setCellValue((Date) valueObject);
                        CellStyle cellStyle = book.createCellStyle();
                        CreationHelper creationHelper = book.getCreationHelper();
                        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yy"));
                        cell.setCellStyle(cellStyle);
                    } else {
                        cell.setCellValue((String) valueObject);
                    }
                    sheet.autoSizeColumn(5);
                }
            }

            rs.close();
            stmt.close();
            conn.close();
            FileOutputStream fileOutputStream = new FileOutputStream(dir);
            book.write(fileOutputStream);
            fileOutputStream.close();
            book.close();
            Desktop.getDesktop().open(new File(dir));
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

