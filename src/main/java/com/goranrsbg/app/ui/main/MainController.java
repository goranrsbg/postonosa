package com.goranrsbg.app.ui.main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class MainController implements Initializable {

	@FXML
	private StackPane stackPane;

	@FXML
	private ImageView imageView;

	private Window window;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			window = stackPane.getScene().getWindow();
		});
	}

	@FXML
	void openMap(ActionEvent event) {
		System.out.println("Time to open map.");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Izaberi Mapu");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", "*_map.jpg"));
		File file = fileChooser.showOpenDialog(window);
		if(file != null) {
			Image theMap = new Image(file.toURI().toString());
			imageView.setImage(theMap);
			imageView.fitWidthProperty().bind(theMap.widthProperty());
		}
	}

}
