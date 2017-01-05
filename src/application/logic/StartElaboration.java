package application.logic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import application.configuration.AlertMsg;
import application.configuration.ConfigFileExcel;
import application.genInfo.GeneralInfo;
import javafx.scene.control.Alert.AlertType;

public class StartElaboration {

	private static Integer START_SHEET = 0;
	private static Integer START_SHEET_FINAL = 0;
	private static Integer B2B_SHEET = 12;
	private static Integer INDIV_SHEET = 13;
	
	public static Integer ROW_START = 4;
	public static Integer ROW_START_FINAL = 4;
	
	public static Integer ROW_START_BUSINESS = 3;
	public static Integer ROW_START_INDIVIDUAL = 3;
	public static Integer ROW_START_BUSS_INDIVID = 3;
	
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
	
	public static String WARNING = "WARNING";
	public static String ERROR = "ERROR";
	
	public void start(String excelPath) {

		
		ConfigFileExcel configFileExcel = new ConfigFileExcel();
		HashMap<String, GeneralInfo> listClients = new HashMap<String, GeneralInfo>();
		HashMap<String, List<String>> listErrors = new HashMap<String, List<String>>();
		List<String> listStringWarning = new ArrayList<String>();
		List<String> listStringErrors = new ArrayList<String>();
		
		byte[] byteExcel = configFileExcel.getByteFromFile(excelPath);

		Workbook wb = null;
		Sheet sheet = null;
		
		try {
			wb = WorkbookFactory.create(new ByteArrayInputStream(byteExcel));
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		while(START_SHEET < 12) {

			System.out.println("Muaji: " + START_SHEET);
			
			sheet = wb.getSheetAt(START_SHEET);

			Row row = sheet.getRow(ROW_START);

			while(checkHasNextRow(row)) {

				System.out.println("Rreshti: " + ROW_START);
				
				Date dataFatures = ExcelPoi.getDate(row, COL_DATA_FATURES);
				if(dataFatures == null) {
					if(listErrors.containsKey(WARNING)) {
						listErrors.get(WARNING).add("Mungon data e fatures tek sheet: " + (START_SHEET+1) + " rreshti: " + (ROW_START+1));
					} else {
						listStringWarning.add("Mungon data e fatures tek sheet: " + (START_SHEET+1) + " rreshti: " + (ROW_START+1));
						listErrors.put(WARNING, listStringWarning);
					}
				}
				
				Date dataAktuale = ExcelPoi.getDate(row, COL_DATA_AKTUALE);
				if(dataAktuale == null) {
					if(listErrors.containsKey(WARNING)) {
						listErrors.get(WARNING).add("Mungon data aktuale tek sheet: " + (START_SHEET+1) + " rreshti: " + (ROW_START+1));
					} else {
						listStringWarning.add("Mungon data aktuale tek sheet: " + (START_SHEET+1) + " rreshti: " + (ROW_START+1));
						listErrors.put(WARNING, listStringWarning);
					}
				}
				
				Integer kohevonesa = ExcelPoi.getInteger(row, COL_KOHEVONESA);
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
						try {
							totailGI = new BigDecimal(totali);	
						} catch (Exception e) {
							if(listErrors.containsKey(ERROR)) {
								listErrors.get(ERROR).add("Vlera e totalit e paformatuar tek sheet: " + (START_SHEET+1) + " rreshti: " + (ROW_START+1));
							} else {
								listStringErrors.add("Vlera e totalit e paformatuar tek sheet: " + (START_SHEET+1) + " rreshti: " + (ROW_START+1));
								listErrors.put(ERROR, listStringErrors);
							}
						}
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

		setCellsValue(listClients, wb, excelPath, listErrors);
	}
	
	private void setCellsValue(HashMap<String, GeneralInfo> listClients, Workbook wb, String excelPath, HashMap<String, List<String>> listErrors) {

		AlertMsg alertMsg = new AlertMsg();
		
		try {

			Sheet sheetB2B = wb.getSheetAt(B2B_SHEET);
			Sheet sheetIndivid = wb.getSheetAt(INDIV_SHEET);
			Row row = null;
			
			/**
			 * Clean rows of total sheets
			 * */
			for (int i = 3; i < 250; i++) {
				
				Row rowB2B = sheetB2B.getRow(i);
				Row rowIndivid = sheetIndivid.getRow(i);
				
				if(rowB2B == null) continue;
				if(rowIndivid == null) continue;
				
				Cell cellDataFatures = rowB2B.getCell(COL_DATA_FATURES);
				cellDataFatures.setCellValue("");
				Cell cellDataFatures2 = rowIndivid.getCell(COL_DATA_FATURES);
				cellDataFatures2.setCellValue("");

				Cell cellDataAktuale = rowB2B.getCell(COL_DATA_AKTUALE);
				cellDataAktuale.setCellValue("");
				Cell cellDataAktuale2 = rowIndivid.getCell(COL_DATA_AKTUALE);
				cellDataAktuale2.setCellValue("");

				Cell cellKohevonesa = rowB2B.getCell(COL_KOHEVONESA);
				cellKohevonesa.setCellValue("");
				Cell cellKohevonesa2 = rowIndivid.getCell(COL_KOHEVONESA);
				cellKohevonesa2.setCellValue("");

				Cell cellFirma = rowB2B.getCell(COL_FIRMA);
				cellFirma.setCellValue("");
				Cell cellFirma2 = rowIndivid.getCell(COL_FIRMA);
				cellFirma2.setCellValue("");

				Cell cellNiptOrIdCard = rowB2B.getCell(COL_NIPT_IDCARD_FINAL);
				cellNiptOrIdCard.setCellValue("");
				Cell cellNiptOrIdCard2 = rowIndivid.getCell(COL_NIPT_IDCARD_FINAL);
				cellNiptOrIdCard2.setCellValue("");

				Cell cellPerfaqesuesi = rowB2B.getCell(COL_PERFAQESUESI);
				cellPerfaqesuesi.setCellValue("");
				Cell cellPerfaqesuesi2 = rowIndivid.getCell(COL_PERFAQESUESI);
				cellPerfaqesuesi2.setCellValue("");

				Cell cellTotaliPlote = rowB2B.getCell(COL_TOTALI_FINAL_BUSINESS);
				cellTotaliPlote.setCellValue("");
				Cell cellTotaliPlote2 = rowIndivid.getCell(COL_TOTALI_FINAL_BUSINESS);
				cellTotaliPlote2.setCellValue("");
				
			}
			
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

				Cell cellPerfaqesuesi = row.getCell(COL_PERFAQESUESI-1);
				cellPerfaqesuesi.setCellValue(genInfo.getPerfaqesuesi());

				Cell cellTotaliPlote = row.getCell(COL_TOTALI_FINAL_BUSINESS);
				cellTotaliPlote.setCellValue(genInfo.getTotaliPlote().toString());

				if(isBussines) {
					ROW_START_BUSINESS++;
				} else {
					ROW_START_INDIVIDUAL++;
				}
			}
			ROW_START_BUSINESS = ROW_START_BUSS_INDIVID;
			ROW_START_INDIVIDUAL = ROW_START_BUSS_INDIVID;

			if(listErrors.get(ERROR) != null && listErrors.get(ERROR).size() > 0) {
				
				String errorMsg = "";
				for(String msg : listErrors.get(ERROR)) {
					errorMsg += msg + "\n";
				}
				
				alertMsg.alertMsg(AlertType.ERROR, "Bilanci", "Error ne perpunimin e te dhenave \n" + errorMsg, null);
				return;
			}
			
			FileOutputStream outputStream = new FileOutputStream(new File(excelPath));
			wb.write(outputStream);
			outputStream.close();//Close in finally if possible

			String warningMsg = "";
			if(listErrors.get(WARNING) != null && listErrors.get(WARNING).size() > 0) {
				for(String msg : listErrors.get(WARNING)) {
					warningMsg += msg + "\n";
				}
			}
			
			alertMsg.alertMsg(AlertType.INFORMATION, "Bilanci", warningMsg, "File Excel u perpunua me sukses!");
			
		} catch (FileNotFoundException e) {
			alertMsg.alertMsg(AlertType.ERROR, "Bilanci", "Error! Mbylleni file Excel nese jeni duke perpunuar te dhenat", ExceptionUtils.getStackTrace(e));
			return;
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
