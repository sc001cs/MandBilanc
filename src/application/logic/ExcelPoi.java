package application.logic;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.xmlbeans.impl.regex.ParseException;

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
	
	public static Integer getInteger(Row rc, int nc) {

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
				return covertCellNumericToInteger(cc);
			case Cell.CELL_TYPE_STRING:
				return covertCellNumericToInteger(cc);
			case Cell.CELL_TYPE_FORMULA:
				return handleCellInteger(cc);
			}
		}
		return null;
	}
	
	public static Date getDate(Row rc, int nc) {

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
			default:
				return getDateValue(cc);
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
	
	private static Integer handleCellInteger(Cell cell) {
		Integer cellFormulaValue = null;

        switch(cell.getCachedFormulaResultType()) {
            case Cell.CELL_TYPE_NUMERIC:
                cellFormulaValue = new Double(cell.getNumericCellValue()).intValue();
                break;
            case Cell.CELL_TYPE_STRING:
                cellFormulaValue = new Integer(cell.getRichStringCellValue().getString());
                break;
        }
        
        return cellFormulaValue;
     }
	
	private static String covertCellNumericToString(Cell cc) {

		cc.setCellType(Cell.CELL_TYPE_STRING);
		return cc.getRichStringCellValue().getString();
	}
	
	private static Integer covertCellNumericToInteger(Cell cc) {

		cc.setCellType(Cell.CELL_TYPE_STRING);
		
		Integer numb = null;
		
		try {
			numb = new Integer(cc.getRichStringCellValue().getString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return numb;
	}

	private static Date getDateValue(Cell cc) {

		Date cellValue = null;
		if (cc.getCellType() == Cell.CELL_TYPE_NUMERIC) {

			if (DateUtil.isCellDateFormatted(cc)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					cellValue = sdf.parse((sdf.format(cc.getDateCellValue())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				double valueDouble = cc.getNumericCellValue();	
				cellValue = DateUtil.getJavaDate(valueDouble);
			}
		} else if(cc.getCellType() == Cell.CELL_TYPE_STRING) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String test = cc.getRichStringCellValue().toString();
			try {
				cellValue = sdf.parse((sdf.format(cc.getRichStringCellValue().toString())));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		} else if(cc.getCellType() == Cell.CELL_TYPE_FORMULA) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				cellValue = sdf.parse((sdf.format(cc.getDateCellValue())));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		return cellValue;
	}
	
}
