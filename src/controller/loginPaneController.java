package controller;



import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.User;

/**
 * Login Pane Controller controls the login pane FXML file and implements all the functionalities dealing with login. 
 * @author Daivik Sheth | Rohan Patel
 *
 * */
public class loginPaneController {
	
	/** The user name. */
	@FXML TextField userName;
	
	/** The primary stage or previous stage */
	Stage primaryStage;
	
	/** Arraylist of all the users*/
	ArrayList<User> users;
	
	/** The admin obj */
	Admin admin;
	
	/** The login scene. */
	Scene loginScene;
	
	/**
	 * Start initializes the login pane controller.
	 *
	 * @param primaryStage the previous stage
	 * @param user list of users
	 * @param admin admin obj
	 */
	public void start(Stage primaryStage, ArrayList<User> user, Admin admin) {
		this.primaryStage = primaryStage;
		this.users = user;
		this.admin = admin;
		
	}
	
	
	/**
	 * WHen you hit the login button this method is executed. 
	 *
	 * @param e the e
	 */
	public void login(ActionEvent e) {
		loginScene = primaryStage.getScene();
		String s = userName.getText().trim().toLowerCase();
		String admin = "admin";
		if(s.equalsIgnoreCase(admin)){
			userName.clear();
			adminLogin();
		}
		else{
			Boolean found = false;
			for(int i = 0; i < users.size(); i++){
				if(users.get(i).userName.equalsIgnoreCase(s)){
					userName.clear();
					found = true;
					userLogin(s, users.get(i).getAlbums(), users.get(i), i);
					break;
				}
			}
		
			if(found == false){
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
	
	/**
	 * Admin login.
	 */
	public void adminLogin() {
		try{
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/adminPane.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			adminPaneController apg = loader.getController();
			apg.start(primaryStage,users, loginScene,this, admin);
			Scene scene = new Scene(root);	
			primaryStage.setScene(scene);
			root.requestFocus();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * User login.
	 *
	 * @param name username
	 * @param album album list
	 * @param user the current user obj
	 * @param index the index of the user. 
	 */
	public void userLogin(String name, ArrayList<Album> album, User user, int index){
		try{
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/albumPane.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			albumPaneController apg = loader.getController();
			
			apg.start(primaryStage,users, loginScene,this, name, album, user, index, admin);
			Scene scene = new Scene(root);	
			primaryStage.setScene(scene);
			root.requestFocus();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
		
		
	
}
