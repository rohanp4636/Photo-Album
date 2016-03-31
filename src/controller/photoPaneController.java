package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class photoPaneController {
	
	@FXML TilePane tilePane;
	
	@FXML ScrollPane scrollPane;
	
	Stage primaryStage;
	ArrayList<User> users;
	public static ArrayList<Photo> photos;
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
		photoPaneController.photos = album.getPhotos();
		this.currentUser = currentUser;
		this.prev = prev;
		this.userIndex = index;
		this.apc = apc;
		this.album = album;
		selected = null;
		isSelected = false;
		for(int i = 0; i < photos.size(); i++){
			tilePane.getChildren().add(photos.get(i).getLabel()); 
		}
		primaryStage.setResizable(true);
		
	}
	
	public void addPhoto(ActionEvent e){
		deselect();
		FileChooser filechooser = new FileChooser();
		filechooser.getExtensionFilters().add(
				new ExtensionFilter("Images", "*.png", "*.jpg"));
		File file = filechooser.showOpenDialog(null);
		if(file != null){
			System.out.println(file.getAbsolutePath());
			try {
				System.out.println(file.getCanonicalPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(file.getPath());

		}
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
		if(apc.tilePane.getChildren().size() == 0){
			for(int i = 0; i < currentUser.getAlbums().size(); i++){
				apc.tilePane.getChildren().add(0,currentUser.getAlbums().get(i).getLabel());
			}
		}
		
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
