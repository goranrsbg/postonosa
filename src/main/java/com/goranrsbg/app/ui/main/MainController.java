package com.goranrsbg.app.ui.main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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

	@FXML
	private TextField x;

	@FXML
	private TextField y;

	@FXML
	private TextField w;

	@FXML
	private TextField h;

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
		event.consume();
	}

	@FXML
	void readViewPort(ActionEvent event) {
		Rectangle2D rec2d = getRec2d();
		imageView.setViewport(rec2d);
		event.consume();
	}

	private Rectangle2D getRec2d() {
		if (x.getText() == "" || y.getText() == "" || h.getText() == "" || w.getText() == "") {
			return null;
		}
		int X = Integer.parseInt(x.getText());
		int Y = Integer.parseInt(y.getText());
		int W = Integer.parseInt(w.getText());
		int H = Integer.parseInt(h.getText());
		return new Rectangle2D(X, Y, W, H);
	}

	private void setTheMap(String url) {
		Image theMap = new Image(url);
		imageView.setImage(theMap);
		imageView.fitWidthProperty().bind(theMap.widthProperty());
		imageView.fitHeightProperty().bind(theMap.heightProperty());
		imageView.scaleXProperty().bind(zoomSlider.valueProperty());
		imageView.scaleYProperty().bind(zoomSlider.valueProperty());
	}

}
