package com.goranrsbg.app.ui.login;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

public class LoginController implements Initializable {

	@FXML
	private StackPane stackPane;

	@FXML
	private TextField userNameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Label promptLabel;

	@FXML
	private Button clearButton;

	@FXML
	private Button nextButton;
	
    @FXML
    private SVGPath svgPath_nextButton;

    private MessageDigest digest;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory( "CRM" );
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.toString());
		}
		userNameField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) {
				showText("Ime");
			} else {
				clearText();
			}
		});
		passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) {
				showText("Lozinka");
			} else {
				clearText();
			}
		});
		getFocus();
	}

	@FXML
	void onClearActon(ActionEvent event) {
		((TextField) stackPane.getChildren().get(1)).setText("");
		getFocus();
		event.consume();
	}

	@FXML
	void onPasswordAction(ActionEvent event) {
		nextButton.fire();
	}

	@FXML
	void onUserNameAction(ActionEvent event) {
		nextButton.fire();
	}

	@FXML
	void onNextAction(ActionEvent event) {
		if (isPassword()) {
			if (validateUserPassword(passwordField.getText())) {
				System.out.println("GO APP");
			} else {
				swap();
			}
		} else {
			if (validateUserName(userNameField.getText())) {
				System.out.println("GO PASS");
				swap();
			}
		}
		event.consume();
	}

	private boolean validateUserName(String name) {
		// TODO Auto-generated method stub
		String sha256 = getSHA256(userNameField.getText());
		log(sha256 + " " + sha256.length());
		if (Math.random() > 0.5d) {
			return true;
		}
		return false;
	}

	private boolean validateUserPassword(String password) {
		// TODO Auto-generated method stub
		String sha256 = getSHA256(passwordField.getText());
		log(sha256 + " " + sha256.length());
		if (Math.random() > 0.5d) {
			return true;
		}
		return false;
	}

	private void swap() {
		stackPane.getChildren().get(1).toBack();
		getFocus();
	}

	private void getFocus() {
		Platform.runLater(() -> {
			stackPane.getChildren().get(1).requestFocus();
			if (((TextField) stackPane.getChildren().get(1)).getText().isEmpty()) {
				if (isPassword()) {
					svgPath_nextButton.setContent("M0,7 h2 a5.5,7 0 0,1 11,0 h2 v8 h-15 z M5,7 a2.5,3.5 0 0,1 5,0 h-5");
					showText("Lozinka");
				} else {
					svgPath_nextButton.setContent("M0,13 C0,1 11,1 11,13 z M5,3 a3,3 0 1,1 1,0 M3,9 v4 M8,9 v4");
					showText("Ime");
				}
			} else {
				clearText();
			}
		});
	}

	private void showText(String text) {
		promptLabel.setText(text);
	}
	
	private void clearText() {
		if(!promptLabel.getText().isEmpty()) {
			promptLabel.setText("");
		}
	}
	
	private String getSHA256(String text) {
		byte[] dig = digest.digest(text.getBytes(StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dig.length; i++) {
			sb.append(String.format("%02x", dig[i]));
		}
		return sb.toString();
	}

	private boolean isPassword() {
		return stackPane.getChildren().get(1) instanceof PasswordField;
	}
	
	private void log(String message) {
		System.out.println(message);
	}
	
}
