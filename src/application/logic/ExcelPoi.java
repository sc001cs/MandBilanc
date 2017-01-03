package application.logic;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelPoi {

	public static String getString(Row rc, int nc) {
		
		if(rc == null) return null;
		
		Cell cc = rc.getCell((short) nc);

		if (cc != null) {
			switch (cc.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				break;
			case Cell.CELL_TYPE_ERROR:
				break;
			case Cell.CELL_TYPE_NUMERIC:
				return covertCellNumericToString(cc);
			case Cell.CELL_TYPE_STRING:
				return cc.getRichStringCellValue().getString();
			case Cell.CELL_TYPE_FORMULA:
				return handleCell(cc);
			}
			
		}
		return null;
	}

	
	private static String handleCell(Cell cell) {
		String cellFormulaValue = "";

        switch(cell.getCachedFormulaResultType()) {
            case Cell.CELL_TYPE_NUMERIC:
                cellFormulaValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellFormulaValue = cell.getRichStringCellValue().getString();
                break;
        }
        
        return cellFormulaValue;
     }
	
	private static String covertCellNumericToString(Cell cc) {

		cc.setCellType(Cell.CELL_TYPE_STRING);
		return cc.getRichStringCellValue().getString();
	}

}
