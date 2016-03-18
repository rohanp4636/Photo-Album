package controller;



import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
		loginScene = primaryStage.getScene();
	}
	
	
	public void login(ActionEvent e) {
		String s = userName.getText().trim();
		FXMLLoader loader= new FXMLLoader();
		

		
		if(s.equals("admin")){
			adminLogin();
		}
		else{
			if(users.contains(s)){
				userLogin(s);
			}
		}
		
	}	
	public void adminLogin() {
		try{
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/adminPane.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			adminPaneController apg = loader.getController();
			apg.start(primaryStage,users);
			Scene scene = new Scene(root);	
			primaryStage.setResizable(false);
			primaryStage.setTitle("Photos");
			primaryStage.setScene(scene);

			root.requestFocus();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void userLogin(String name){
		
	}
		
		
		
	
}
