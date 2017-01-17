package application;
	
import org.apache.commons.lang3.exception.ExceptionUtils;

import application.configuration.AlertMsg;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;


public class Main extends Application {

	AlertMsg alertMsg = new AlertMsg();
	
	@Override
	public void start(Stage primaryStage) {
 
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root, 300, 300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			alertMsg.alertMsg(AlertType.ERROR, "Bilanci", null, ExceptionUtils.getStackTrace(e));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
