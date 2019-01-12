package com.goranrsbg.app.ui.login;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import com.goranrsbg.app.EmfUtil;
import com.goranrsbg.app.model.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

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
    private User user;
    
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
				launchApp();
			} else {
				passwordField.setText("");
				swap();
			}
		} else {
			if (validateUserName(userNameField.getText())) {
				log(this.user.toString());
				swap();
			}
		}
		event.consume();
	}
	/**
	 * If user name and password are valid 
	 * launch applications main window.
	 */
	private void launchApp() {
		try {
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("../main/Main.fxml"));
			Scene scene = stackPane.getScene();
			scene.setRoot(root);
			Stage stage = (Stage)scene.getWindow();
			stage.setResizable(true);
			Platform.runLater(()-> {
				stage.setFullScreen(true);
			});
		} catch (IOException e) {
			log(e.getMessage());
		}
	}

	private boolean validateUserName(String name) {
		String sha256 = getSHA256(userNameField.getText());
		EntityManager em = EmfUtil.getEntityManager();
		em.getTransaction().begin();
		this.user = em.find(User.class, sha256);
		em.getTransaction().commit();
		em.close();
		return this.user != null;
	}

	private boolean validateUserPassword(String password) {
		String sha256 = getSHA256(passwordField.getText());
		return this.user.getPword().equals(sha256);
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
