package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import base.BaseClass;

public class ReadExcelData {
    public static String cellData = null;
    private static int tdCount;

    // Excel Data Driven--------------------------------------------------------------
    public synchronized String getExcelData(String sheetName, String tcName, String columnName) {

        FileInputStream testData;
        try {
            tdCount = 0;
            int j = 0;
            int columnNum = 0;
            int tcRowNum = 0;
            testData = new FileInputStream(System.getProperty("user.dir") + BaseClass.testDataLoc);
            XSSFWorkbook wbook = new XSSFWorkbook(testData);

            int sheetSize = wbook.getNumberOfSheets();

            for (int i = 0; i < sheetSize; i++) {
                if (wbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                    XSSFSheet sheet = wbook.getSheetAt(i);

                    //tdCount = sheet.getPhysicalNumberOfRows();
                    //tdCount = sheet.getLastRowNum();

                    Iterator<Row> rowIT = sheet.iterator();
                    Row headerRow = rowIT.next();
                    Iterator<Cell> nextHeaderCell = headerRow.iterator();

                    while (nextHeaderCell.hasNext()) {
                        Cell headerCell = nextHeaderCell.next();
                        if (headerCell.getStringCellValue().equalsIgnoreCase(columnName)) {
                            columnNum = +j;
                        }
                        j++;
                    }

                    while (rowIT.hasNext()) {
                        Row dataRow = rowIT.next();
                        Iterator<Cell> nextTCCell = dataRow.iterator();
                        Cell tcCell = nextTCCell.next();

                        // counts the total test data count based on TestCase column
                        if (!tcCell.getStringCellValue().isEmpty()) {
                            tdCount++;
                        }

                        if (tcCell.getStringCellValue().equalsIgnoreCase(tcName)) {
                            // check data type of cell value
                            if (dataRow.getCell(columnNum) == null) {
                                cellData = "";
                            } else if (dataRow.getCell(columnNum).getCellType() == CellType.STRING) {
                                cellData = dataRow.getCell(columnNum).getStringCellValue();
                            } else {
                                cellData = NumberToTextConverter.toText(dataRow.getCell(columnNum).getNumericCellValue());
                            }
                        }
                        testData.close();
                    }
                }

            }

        } catch (

                FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cellData;
    }

    public static int getTdCount() {
        return tdCount;
    }

    public static void setTdCount(int tdCount) {
        ReadExcelData.tdCount = tdCount;
    }

}
