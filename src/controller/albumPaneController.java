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

public class albumPaneController {

	@FXML Button openButton;
	@FXML Button createButton;
	@FXML Button renameButton;
	@FXML Button deleteButton;
	@FXML Button searchButton;
	@FXML Button logoutButton;
	
	@FXML TilePane tilePane;
	
	@FXML ScrollPane scrollPane;
	
	Stage primaryStage;
	ArrayList<User> users;
	Scene prev;
	loginPaneController lpg;
	String userName;
	
	public void start(Stage primaryStage, ArrayList<User> user, Scene prev, loginPaneController lpg, String userName) {
		this.primaryStage = primaryStage;
		this.users = user;
		this.prev = prev;
		this.lpg = lpg;
		this.userName = userName;
		
	}
	
	public void openAlbum(ActionEvent e){
		
	}
	
	public void createAlbum(ActionEvent e){
		
	}
	
	public void renameAlbum(ActionEvent e){
		
	}
	
	public void deleteAlbum(ActionEvent e){
		
	}
	
	public void searchAlbum(ActionEvent e){
		
	}
	public void logout(ActionEvent e){
		primaryStage.setScene(prev);
	}
	
	
	
	
	
}
