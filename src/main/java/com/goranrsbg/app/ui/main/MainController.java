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
	private final double MAX_ZOOM = 3.0 / 100;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Izaberi Mapu");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Mape", "*_map.*"));

		Platform.runLater(() -> {
			window = stackPane.getScene().getWindow();
		});
	}

//	private TextFormatter<Double> buildFormatter() {
//		StringConverter<Double> formatter = new StringConverter<Double>() {
//			@Override
//			public String toString(Double object) {
//				System.err.println("toString : " + object);
//				return object.toString();
//			}
//
//			@Override
//			public Double fromString(String string) {
//				System.err.println("fromString : " + string);
//				Double value = Double.parseDouble(string);
//				if (value < 0d)
//					value = 0d;
//				return value;
//			}
//		};
//		UnaryOperator<Change> filter = new UnaryOperator<Change>() {
//			@Override
//			public Change apply(Change change) {
//				System.err.println(change);
//				String controlNewText = change.getControlNewText();
//				System.err.println(controlNewText);
//				return change;
//			}
//		};
//		return new TextFormatter<Double>(formatter, 0d, filter);
//	}

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
		if (event.isControlDown() && imageView.getImage() != null) {
			double deltaX = event.getDeltaX();
			double deltaY = event.getDeltaY();
			Image image = imageView.getImage();
			double zoomValue = getZoomLabelValue();
			System.err.println("Scroll: " + deltaX + ", " + deltaY);
			if (deltaY > 0d) {
				zoomValue += MAX_ZOOM;
			} else if(deltaY < 0){
				zoomValue -= MAX_ZOOM;
			} else {
				return;
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
		zoomLabel.setText(((long)value) + "%");
	}

	private void setTheMap(String url) {
		Image theMap = new Image(url);
		imageView.setImage(theMap);
		imageView.fitWidthProperty().bind(theMap.widthProperty());
		imageView.fitHeightProperty().bind(theMap.heightProperty());
	}

}
