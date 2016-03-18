package controller;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
	
	public void start(Stage primaryStage, ArrayList<User> user) {
		this.primaryStage = primaryStage;
		this.users = user;
		
		//userName.setText("in adminPane start");
	}
	
	
}
