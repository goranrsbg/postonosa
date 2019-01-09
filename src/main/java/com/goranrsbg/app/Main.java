package com.goranrsbg.app;
	
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
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
			findServer(props);
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
	
	private String findServer(Properties props) throws SocketException {
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
			// displayInformation(networkInterface);
			try {
				getConnection(props, networkInterface.getName());
				log("CONNECTED");
			} catch (SQLException e) {
				log(e.getMessage() + " CODE: " + e.getErrorCode());
			}
		}
		return "none";
	}
	 
	private void displayInformation(NetworkInterface networkInterface) {
		log("Display name: " + networkInterface.getDisplayName());
		log("Name: " + networkInterface.getName());
		Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
		while (inetAddresses.hasMoreElements()) {
			InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
			log("InetAddress: " + inetAddress);
		}
	}
	
	private Connection getConnection(Properties props, String sname) throws SQLException {
		Connection connection = null;
		String connectionString = null;
		connectionString = "jdbc:" + props.getProperty("dbms") + "://" + sname + ":" + props.getProperty("port") + "/" + props.getProperty("dbName");
		log(connectionString);
		DriverManager.getConnection(connectionString, props);
		return connection;
	}

	private void log(String text) {
		System.out.println(text);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
