package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name = "loginData")
	public static Object[][] getExcelData(){
		
		String filePath = ".\\test-data\\Book1.xlsx";
		String sheetName = "Sheet1";
		ExcelUtils excel = new ExcelUtils(filePath, sheetName);
		int rowCount = excel.getRowCount();
		int columnCount = excel.getColumnCount(0);
		
		 // Create a two-dimensional Object array for TestNG
		Object[][] data = new Object[rowCount-1][columnCount];
		
		// Read data from Excel into the array (skip the header row)
		for (int i = 1; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                data[i - 1][j] = excel.getCellData(i, j);
            }
        }
		excel.closeWorkbook(); // Close the workbook after use
        return data;
        
	}
	
}
