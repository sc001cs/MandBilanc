package application.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javafx.scene.control.Alert.AlertType;

public class ConfigFileExcel {
	
	AlertMsg alertMsg = new AlertMsg();

	/**
	 * Convert the file Excel to byte array
	 * @param pathExcel
	 * @return byte array of Excel converted
	 */
	public byte[] getByteFromFile(String pathExcel) {

		if(pathExcel == null && pathExcel.equals(""))
			return null;

		Path path = Paths.get(pathExcel);
		byte[] data = null;

		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			
			alertMsg.alertMsg(AlertType.ERROR, "Asycuda Converter", "Template Excel me emer: " + pathExcel + " nuk u gjen!", null);
		}

		return data;
	}

	public ConfigExcel getConfigXML() {

		try {  
			
			String configLoc = "C:\\BilancMand\\config\\config.xml";
			
			File file = new File(configLoc);  
			JAXBContext jaxbContext = JAXBContext.newInstance(ConfigExcel.class);  

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
			ConfigExcel configXML= (ConfigExcel) jaxbUnmarshaller.unmarshal(file);  

			return configXML;
		} catch (JAXBException e) {  
			
			alertMsg.alertMsg(AlertType.ERROR, "Bilanc Mand", null, ExceptionUtils.getStackTrace(e));
			
			return null;
		}  

	}  
	
}
