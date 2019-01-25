package com.goranrsbg.app.ui.main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
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
	private Label zoomLabel;

	private Window window;
	private FileChooser fileChooser;
	private final double MAX_ZOOM = 0.03;

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
		event.consume();
	}

	@FXML
	void readImageData(ActionEvent event) {
		event.consume();
	}

	@FXML
	void onScroll(ScrollEvent event) {
		Image image = imageView.getImage();
		double deltaY = event.getDeltaY();
		if (event.isControlDown() && image != null && deltaY != 0d) {
			double zoomValue = getZoomLabelValue();
			if (deltaY > 0d) {
				zoomValue -= MAX_ZOOM;
			} else { // deltaY < 0, zoom out
				zoomValue += MAX_ZOOM;
			}
			imageView.setViewport(new Rectangle2D(0d, 0d, image.getWidth() * zoomValue, image.getHeight() * zoomValue));
			setZoomLabelValue(zoomValue * 100d);
			event.consume();
		}
	}
	
	private double getZoomLabelValue() {
		return Double.parseDouble(zoomLabel.getText().substring(0, zoomLabel.getText().length() - 1)) / 100d;
	}
	
	private void setZoomLabelValue(double value) {
		zoomLabel.setText(Math.round(value) + "%");
	}

	private void setTheMap(String url) {
		Image theMap = new Image(url);
		imageView.setImage(theMap);
		imageView.fitWidthProperty().bind(theMap.widthProperty());
		imageView.fitHeightProperty().bind(theMap.heightProperty());
	}

}
