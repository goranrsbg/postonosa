package com.goranrsbg.app.ui.main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
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

	@FXML
	private Slider zoomSlider;

	private Window window;
	private FileChooser fileChooser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Izaberi Mapu");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Mape", "*_map.*"));
		Platform.runLater(() -> {
			window = stackPane.getScene().getWindow();
		});
	}

	@FXML
	void openMap(ActionEvent event) {
		File file = fileChooser.showOpenDialog(window);
		if (file != null) {
			setTheMap(file.toURI().toString());
		}
	}
	
	private void setTheMap(String url) {
		Image theMap = new Image(url);
		imageView.setImage(theMap);
		imageView.fitWidthProperty().bind(theMap.widthProperty());
		imageView.scaleXProperty().bind(zoomSlider.valueProperty());
		imageView.scaleYProperty().bind(zoomSlider.valueProperty());
	}

}







