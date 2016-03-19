package controller;



import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.User;


public class loginPaneController {
	
	@FXML TextField userName;
	
	Stage primaryStage;
	ArrayList<User> users;
	
	Scene loginScene;
	
	public void start(Stage primaryStage, ArrayList<User> user) {
		this.primaryStage = primaryStage;
		this.users = user;
	}
	
	
	public void login(ActionEvent e) {
		loginScene = primaryStage.getScene();
		String s = userName.getText().trim().toLowerCase();
		FXMLLoader loader= new FXMLLoader();
				
		if(s.equals("admin")){
			userName.clear();
			adminLogin();
		}
		else{
			if(users.contains(s)){
				userName.clear();
				userLogin(s);   
			}
			else{
				Alert message = new Alert(AlertType.INFORMATION);
				message.initOwner(primaryStage);
				message.setTitle("Login Error");
				message.setHeaderText("Cannot Login");
				message.setContentText("Username is incorrect.");
				message.setGraphic(null);
				message.getDialogPane().getStylesheets().add("/view/loginPane.css");
				message.showAndWait();
			}
		}		
		
	}	
	public void adminLogin() {
		try{
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/adminPane.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			adminPaneController apg = loader.getController();
			apg.start(primaryStage,users, loginScene,this);
			Scene scene = new Scene(root);	
			primaryStage.setScene(scene);
			root.requestFocus();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void userLogin(String name){
		try{
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/albumPane.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			albumPaneController apg = loader.getController();
			apg.start(primaryStage,users, loginScene,this, name);
			Scene scene = new Scene(root);	
			primaryStage.setScene(scene);
			root.requestFocus();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
		
		
	
}
