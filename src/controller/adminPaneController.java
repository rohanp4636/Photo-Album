package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.User;

public class adminPaneController {
	@FXML TextField userName;
	@FXML Button searchButton;
	@FXML Button createButton;
	@FXML Button deleteButton;
	@FXML Button logoutButton;
	
	@FXML TilePane tilePane;
	
	@FXML ScrollPane scrollPane;
	
	Stage primaryStage;
	ArrayList<User> users;
	Scene prev;
	loginPaneController lpg;
	
	public void start(Stage primaryStage, ArrayList<User> user, Scene prev, loginPaneController lpg) {
		this.primaryStage = primaryStage;
		this.users = user;
		this.prev = prev;
		this.lpg = lpg;
		
		//userName.setText("in adminPane start");
	}
	
	public void searchUser(ActionEvent e){
		
	}
	
	public void createUser(ActionEvent e){
		
	}
	
	public void deleteUser(ActionEvent e){
		
	}
	
	public void logout(ActionEvent e){
		primaryStage.setScene(prev);
	}
	
	
	
	
	
}
