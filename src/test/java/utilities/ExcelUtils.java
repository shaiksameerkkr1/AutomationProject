package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	String filePath;
	XSSFWorkbook workbook;
	XSSFSheet sheet;

	public ExcelUtils(String filePath, String sheetName) {
		try {
			this.filePath = filePath;
			FileInputStream file = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not load the Excel file: " + filePath);
		}
	}

	// Get the value from a specific cell
	public String getCellData(int rowNum, int colNum) {
		try {
			XSSFCell cell = sheet.getRow(rowNum).getCell(colNum);
			if (cell != null) {
				switch (cell.getCellType()) {
				case STRING:
					return cell.getStringCellValue();
				case NUMERIC:
					return String.valueOf((int) cell.getNumericCellValue());
				case BOOLEAN:
					return String.valueOf(cell.getBooleanCellValue());
				default:
					return "";
				}
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// Write data to a specific cell
	public void setCellData(int rowNum, int colNum, String data) {
		try {
			XSSFRow row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
			}
			XSSFCell cell = row.getCell(colNum);
			if (cell == null) {
				cell = row.createCell(colNum);
			}
			cell.setCellValue(data);

			// Save the changes to the file
			FileOutputStream fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Get the total number of rows in the sheet
	public int getRowCount() {
		return sheet.getPhysicalNumberOfRows();
	}

	// Get the total number of columns in a specific row
	public int getColumnCount(int rowNum) {
		XSSFRow row = sheet.getRow(rowNum);
		return (row == null) ? 0 : row.getPhysicalNumberOfCells();
	}
	
	//close the workbook
	public void closeWorkbook() {
		try {
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
