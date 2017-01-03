package application.configuration;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javafx.scene.control.Alert.AlertType;

public class SetUpFolders {

	AlertMsg alertMsg = new AlertMsg();
	
	public void setUpTheFolders() {
		
		// Create Root folder
		new File(ConfigExcel.rootFolder).mkdirs();
		
		// Create converted excel folder
		new File(ConfigExcel.excelFolder).mkdirs();
		
		// Create config folder
		new File(ConfigExcel.configFolder).mkdirs();
		
		// Create asset folder
		new File(ConfigExcel.assetFolder).mkdirs();
		
		/** 
		 * Create config xml file
		 * */
		String xmlSource = ConfigExcel.excelSource;

		try {
			// Parse the given input
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

			// Write the parsed document to an xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result =  new StreamResult(new File(ConfigExcel.configFolder + "\\" + "config.xml"));
			transformer.transform(source, result);
		} catch(Exception e) {
			alertMsg.alertMsg(AlertType.ERROR, "Asycuda Converter", null, ExceptionUtils.getStackTrace(e));
		}
		
	}
	
}
