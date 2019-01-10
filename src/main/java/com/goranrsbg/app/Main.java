package com.goranrsbg.app;
	
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("ui/login/Login.fxml"));
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
			try {
				Connection connection = getConnection(props, getInet4Address(networkInterface));
				log("CONNECTED : " + (connection != null));
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("select * from test");
				while(rs.next()) {
					log("> " + rs.getString(1) + " | " + rs.getString(2));
				}
				connection.close();
				break;
			} catch (SQLException e) {
				log(e.getMessage() + " CODE: " + e.getErrorCode());
			}
		}
		return "none";
	}

	private Connection getConnection(Properties props, String sname) throws SQLException {
		String connectionString = null;
		connectionString = "jdbc:" + props.getProperty("dbms") + "://" + sname + ":" + props.getProperty("port") + "/" + props.getProperty("dbName");
		log(connectionString);
		return DriverManager.getConnection(connectionString, props);
	}
	 
	private String getInet4Address(NetworkInterface networkInterface) {
		//log("Display name: " + networkInterface.getDisplayName());
		//log("Name: " + networkInterface.getName());
		Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
		while (inetAddresses.hasMoreElements()) {
			InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
			//log("InetAddress: " + inetAddress);
			if(inetAddress instanceof Inet4Address) {
				return inetAddress.getHostAddress();
			}
		}
		return "none";
	}
	

	private void log(String text) {
		System.out.println(text);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
