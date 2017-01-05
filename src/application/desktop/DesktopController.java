package application.desktop;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;

import application.configuration.AlertMsg;
import application.configuration.ConfigExcel;
import application.configuration.ConfigFileExcel;
import application.configuration.FileExcel;
import application.logic.StartElaboration;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DesktopController implements Initializable {

	@FXML private Button btnUploadExcel;
	@FXML private Button btnSignOut;
	
	StartElaboration startElab = new StartElaboration();
//	GenerateXMLFINAL genXML = new GenerateXMLFINAL();
	ConfigFileExcel configFileExcel = new ConfigFileExcel();
	AlertMsg alertMsg = new AlertMsg();
	
	public static HashMap<String, List<String>> alerts = new HashMap<String, List<String>>();
	
	String pathFolder = "";
	
	public void uploadExcel(ActionEvent event) {
		
		FileChooser fileChooser  = new FileChooser();
		
		//Extention filter
		FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("Excel file", "*.xlsm", "*.xlsx", "*.xls");
		fileChooser.getExtensionFilters().add(extentionFilter);
		
		//Set to user directory or go to default if cannot access
		String userDirectoryString = System.getProperty("user.home");
		File userDirectory = new File(userDirectoryString);
		if(userDirectory == null && !userDirectory.canRead()) {
		    userDirectory = new File("C:/");
		}
		fileChooser.setInitialDirectory(userDirectory);
		
		File fileExcel = fileChooser.showOpenDialog(null);
		
		if(fileExcel != null) {

			try {
				alerts = new HashMap<String, List<String>>();
				startElab.start(fileExcel.getAbsolutePath());
				
			} catch (Exception e) {
				e.printStackTrace();
				alertMsg.alertMsg(AlertType.ERROR, "Bilanci", null, ExceptionUtils.getStackTrace(e));
			}

		} else {
			alertMsg.alertMsg(AlertType.ERROR, "Bilanci", "File nuk u aksesua!", null);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void signOut(ActionEvent event) {

		try {
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/application/Login.fxml").openStream());

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (Exception e) {
			alertMsg.alertMsg(AlertType.ERROR, "Bilanci", null, ExceptionUtils.getStackTrace(e));
		}
	}
	
}
