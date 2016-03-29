package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Album;
import model.User;

public class photoPaneController {
	
	@FXML TilePane tilePane;
	
	@FXML ScrollPane scrollPane;
	
	Stage primaryStage;
	ArrayList<User> users;
	public static ArrayList<Album> albums;
	User currentUser;
	int userIndex;
	Scene prev;
	albumPaneController apc;
	Album album;
	
	public static String selected;
	public static Boolean isSelected = false;
	
	public void start(Stage primaryStage, ArrayList<User> user, User currentUser, int index, Scene prev, albumPaneController apc, Album album) {
		this.primaryStage = primaryStage;
		this.users = user;
		photoPaneController.albums = currentUser.getAlbums();
		this.currentUser = currentUser;
		this.prev = prev;
		this.userIndex = index;
		this.apc = apc;
		this.album = album;
		selected = null;
		isSelected = false;
		for(int i = 0; i < albums.size(); i++){
			tilePane.getChildren().add(albums.get(i).getLabel());
		}
		primaryStage.setResizable(true);
		
	}
	
	public void addPhoto(ActionEvent e){
		
	}
	
	public void removePhoto(ActionEvent e){
			
	}
	
	public void captionPhoto(ActionEvent e){
		
	}
	
	public void displayPhoto(ActionEvent e){
		
	}
	
	public void tagPhoto(ActionEvent e){
		
	}
	
	public void movePhoto(ActionEvent e){
		
	}
	
	public void deletePhoto(ActionEvent e){
		
	}
	public void slideshow(ActionEvent e){
			
	}
	public void createAlbum(ActionEvent e){
		
	}
	
	
	public void back(ActionEvent e){
		deselect();
		primaryStage.setScene(prev);
	}
	
	
	public int getSelectedUser(){
		if(!isSelected && selected != null){
			return -1;
		}
		for(int i = 0; i < tilePane.getChildren().size(); i++){
			if(tilePane.getChildren().get(i).getId().equalsIgnoreCase(selected)){
				return i;
			}
		}
		return -1;
		
	}
	
	public void deselect(){
		int x = getSelectedUser();
		if(x == -1){
			selected = null;
			isSelected = false;
			return;
		}
		if(isSelected && x >= 0 && x < users.size()){
			if(((Label)tilePane.getChildren().get(x)).getStylesheets().size() == 2){
				((Label)tilePane.getChildren().get(x)).getStylesheets().remove(1);
				((Label)tilePane.getChildren().get(x)).getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
			}
		}
		selected = null;
		isSelected = false;
	}
	
}
