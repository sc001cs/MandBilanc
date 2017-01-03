package application.configuration;

import javax.xml.bind.annotation.XmlRootElement;

import application.configuration.xml.general.GeneralConfig;

@XmlRootElement  
public class ConfigExcel {

	public static String rootFolder = "C:\\BilancMand";
	public static String excelFolder = "C:\\BilancMand\\fileExcel";
	public static String configFolder = "C:\\BilancMand\\config";
	public static String assetFolder = "C:\\BilancMand\\asset";
	
	public static String excelSource = 
			" <configExcel> " +
				" <general> " +
					" <data_format_file>dd-MM-yyyy_kk_mm-ss</data_format_file> " +
					" <utf8>UTF-8</utf8> " +
					" <path_folder>C:\\BilancMand</path_folder> " +
					" <path_folder_excel>C:\\BilancMand\\fileExcel</path_folder_excel> " +
				" </general> " +
			" </configExcel> ";
	
	private GeneralConfig general;

	public GeneralConfig getGeneral() {
		return general;
	}

	public void setGeneral(GeneralConfig general) {
		this.general = general;
	}
	
}
