package com.goranrsbg.app;
	
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(final Stage stage) {
		try {
			Properties props = new Properties();
			props.load(getClass().getResourceAsStream("/app.properties"));
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.setTitle(props.getProperty("title"));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println("Poštonoša ugašen.");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
