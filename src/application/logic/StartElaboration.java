package application.logic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import application.configuration.ConfigFileExcel;
import application.genInfo.GeneralInfo;

public class StartElaboration {

	private static Integer START_SHEET = 0;
	private static Integer START_SHEET_FINAL = 0;
	private static Integer B2B_SHEET = 12;
	private static Integer INDIV_SHEET = 13;
	
	public static Integer ROW_START = 3;
	public static Integer ROW_START_BUSINESS = 3;
	public static Integer ROW_START_INDIVIDUAL = 3;
	public static Integer ROW_START_FINAL = 3;
	
	public static Integer COL_DATA_FATURES = 0;
	public static Integer COL_DATA_AKTUALE = 1;
	public static Integer COL_KOHEVONESA = 2;
	public static Integer COL_FIRMA = 3;
	public static Integer COL_ID_CARD = 4;
	public static Integer COL_NIPT = 5;
	public static Integer COL_PERFAQESUESI = 6;
	public static Integer COL_TOTALI = 45;
	
	public static Integer COL_NIPT_IDCARD_FINAL = 4;
	
	public static Integer COL_TOTALI_FINAL_BUSINESS = 6;
	public static Integer COL_TOTALI_FINAL_INDIVIDUAL = 6;
	
	public void start(String excelPath) {

		ConfigFileExcel configFileExcel = new ConfigFileExcel();
		HashMap<String, GeneralInfo> listClients = new HashMap<String, GeneralInfo>();
		
		byte[] byteExcel = configFileExcel.getByteFromFile(excelPath);

		Workbook wb = null;
		Sheet sheet = null;
		
		try {
			wb = WorkbookFactory.create(new ByteArrayInputStream(byteExcel));
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		while(START_SHEET < 12) {

			System.out.println("Muaji: " + START_SHEET);
			
			sheet = wb.getSheetAt(START_SHEET);

			Row row = sheet.getRow(ROW_START);

			while(checkHasNextRow(row)) {

				System.out.println("Rreshti: " + ROW_START);
				
				String dataFatures = ExcelPoi.getString(row, COL_DATA_FATURES);
				String dataAktuale = ExcelPoi.getString(row, COL_DATA_AKTUALE);
				String kohevonesa = ExcelPoi.getString(row, COL_KOHEVONESA);
				String firma = ExcelPoi.getString(row, COL_FIRMA);
				String idCard = ExcelPoi.getString(row, COL_ID_CARD) == null ? null : ExcelPoi.getString(row, COL_ID_CARD).toUpperCase();
				String nipt = ExcelPoi.getString(row, COL_NIPT) == null ? null : ExcelPoi.getString(row, COL_NIPT).toUpperCase();
				String perfaqesuesi = ExcelPoi.getString(row, COL_PERFAQESUESI);
				String totali = ExcelPoi.getString(row, COL_TOTALI);
				String key = "";
				
				if(idCard != null) {
					key = idCard;
				} else if(nipt != null) {
					key = nipt;
				}
				
				if(listClients.containsKey(key)) {

					BigDecimal totailGI = BigDecimal.ZERO;
					if(totali != null && !(totali.trim()).equalsIgnoreCase("") ) {
						totailGI = new BigDecimal(totali);	
					}
					
					BigDecimal totaliOld = listClients.get(key).getTotaliPlote();
					BigDecimal totaliNew = totailGI;

					BigDecimal totalUpdate = CalDec.addition(totaliOld, totaliNew);

					listClients.get(key).setTotaliPlote(totalUpdate);

				} else {

					BigDecimal totailGI = BigDecimal.ZERO;
					if(totali != null && !(totali.trim()).equalsIgnoreCase("") ) {
						totailGI = new BigDecimal(totali);	
					}
					
					GeneralInfo genInfo = new GeneralInfo(dataFatures, dataAktuale, kohevonesa, firma, idCard, nipt, perfaqesuesi, totailGI);

					listClients.put(key, genInfo);
				}

				ROW_START++;
				row = sheet.getRow(ROW_START);
			}
			ROW_START = ROW_START_FINAL;

			START_SHEET++;
		}
		START_SHEET = START_SHEET_FINAL;

		setCellsValue(listClients, wb, excelPath);
	}
	
	private void setCellsValue(HashMap<String, GeneralInfo> listClients, Workbook wb, String excelPath) {

		try {

			Sheet sheetB2B = wb.getSheetAt(B2B_SHEET);
			Sheet sheetIndivid = wb.getSheetAt(INDIV_SHEET);
			Row row = null;
			
			for (GeneralInfo genInfo : listClients.values()) {
				
				Boolean isBussines = false;
				
				if(genInfo.getNipt() != null && !genInfo.getNipt().equalsIgnoreCase("")) {
					row = sheetB2B.getRow(ROW_START_BUSINESS);
					isBussines = true;
				} else if(genInfo.getIdCard() != null && !genInfo.getIdCard().equalsIgnoreCase("")) {
					row = sheetIndivid.getRow(ROW_START_INDIVIDUAL);
				} else {
					continue;
				}

				Cell cellDataFatures = row.getCell(COL_DATA_FATURES);
				cellDataFatures.setCellValue(genInfo.getDataFatures());

				Cell cellDataAktuale = row.getCell(COL_DATA_AKTUALE);
				cellDataAktuale.setCellValue(genInfo.getDataAktuale());

				Cell cellKohevonesa = row.getCell(COL_KOHEVONESA);
				cellKohevonesa.setCellValue(genInfo.getKohevonesa());

				Cell cellFirma = row.getCell(COL_FIRMA);
				cellFirma.setCellValue(genInfo.getFirma());

				Cell cellNiptOrIdCard = row.getCell(COL_NIPT_IDCARD_FINAL);
				
				if(isBussines)
					cellNiptOrIdCard.setCellValue(genInfo.getNipt());
				else 
					cellNiptOrIdCard.setCellValue(genInfo.getIdCard());

				Cell cellPerfaqesuesi = row.getCell(COL_PERFAQESUESI);
				cellPerfaqesuesi.setCellValue(genInfo.getPerfaqesuesi());

				Cell cellTotaliPlote = row.getCell(COL_TOTALI_FINAL_BUSINESS);
				cellTotaliPlote.setCellValue(genInfo.getTotaliPlote().toString());

				if(isBussines) {
					ROW_START_BUSINESS++;
				} else {
					ROW_START_INDIVIDUAL++;
				}
			}
			ROW_START_BUSINESS = ROW_START_FINAL;
			ROW_START_INDIVIDUAL = ROW_START_FINAL;

			FileOutputStream outputStream = new FileOutputStream(new File(excelPath));
			wb.write(outputStream);
			outputStream.close();//Close in finally if possible

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Boolean checkHasNextRow(Row row) {
		
		Boolean hasNextRow = false;
		String nipt = ExcelPoi.getString(row, COL_NIPT);
		String idCard = ExcelPoi.getString(row, COL_ID_CARD);
		
		if( (nipt != null && !(nipt.trim()).equalsIgnoreCase(""))
				|| ( idCard != null && !(idCard.trim()).equalsIgnoreCase("") )) {
			hasNextRow = true;
		}
		
		return hasNextRow;
	}
}
