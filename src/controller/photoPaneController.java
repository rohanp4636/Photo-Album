package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
	
	public void addPhoto(ActionEvent e){  //use absolute path .. when using demo to submit create it from star???
		deselect();
		FileChooser filechooser = new FileChooser();
		filechooser.getExtensionFilters().add(
				new ExtensionFilter("Images", "*.png", "*.jpg"));
		File file = filechooser.showOpenDialog(null);
		if(file != null){
			for(int i = 0; i < photos.size(); i++){
				if(photos.get(i).getPath().equalsIgnoreCase(file.getAbsolutePath())){
					primaryStage.setResizable(false);
					Alert message = new Alert(AlertType.INFORMATION);
					message.initOwner(primaryStage);
					message.setTitle("Add Photo");
					message.setHeaderText("Cannot Add Photo");
					message.setContentText("Photo already exists in album.");
					message.setGraphic(null);
					message.getDialogPane().getStylesheets().add("/view/loginPane.css");
					message.showAndWait();
					primaryStage.setResizable(true);
					return;
				}
			}
			//System.out.println(file.lastModified());
			
			Photo photo = new Photo(file, album);
			photos.add(0, photo);
			tilePane.getChildren().add(0, photo.getLabel());
			currentUser.updateAlbum(apc, album);
			

		}
	}
	
	public void removePhoto(ActionEvent e){
		if(photos.size() == 0){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Remove Photo");
			message.setHeaderText("Cannot Remove Photo");
			message.setContentText("There are no photos to remove.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
		if(getSelectedUser() == -1){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Remove Photo");
			message.setHeaderText("Cannot Remove Photo");
			message.setContentText("You musst first select a photo to remove.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Remove Photo");
		alert.setHeaderText("Confirm Remove");
		alert.setContentText("Are you sure you want to remove this photo?");
		alert.getDialogPane().getStylesheets().add("/view/loginPane.css");
		alert.setGraphic(null);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) { 
			  Alert message = new Alert(AlertType.INFORMATION);
			   message.initOwner(primaryStage);
			   message.setTitle("Remove Photo");
			   message.setHeaderText("Remove Complete");
			   message.setContentText("The selected photo has been removed");
			   message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			   message.setGraphic(null);
			   message.showAndWait();
			   int userIndex = getSelectedUser();
			   for(int i = 0; i < tilePane.getChildren().size(); i++){
				   Label label = (Label) tilePane.getChildren().get(i);
				   if(label.getId().equalsIgnoreCase(photos.get(userIndex).getPath())){
					   tilePane.getChildren().remove(i);
					   photos.remove(userIndex);
					   album.numPhotos--;
					   currentUser.updateAlbum(apc, album);
					   break;
				   }
			   }
		   
	
			   			   
			   
		   }
		deselect();
	}
	
	public void captionPhoto(ActionEvent e) throws IOException{
		if(photos.size() == 0){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Caption Photo");
			message.setHeaderText("Cannot Caption Photo");
			message.setContentText("There are no photos to caption.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
		if(getSelectedUser() == -1){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Caption Photo");
			message.setHeaderText("Cannot Caption Photo");
			message.setContentText("You musst first select a photo to caption.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
		   Stage stageAdd = new Stage();
		   
		   FXMLLoader load = new FXMLLoader();
		   load.setLocation(getClass().getResource("/view/caption.fxml"));
		   AnchorPane root = (AnchorPane)load.load();
		   captionController cuc= load.getController();
		   Boolean isRecaption = false;
		   if(!photos.get(getSelectedUser()).getCaption().isEmpty()){
			   isRecaption = true;
		   }
		   cuc.start(stageAdd,this,photos.get(getSelectedUser()), isRecaption,this.tilePane);
		   
		   Scene add = new Scene(root);
		   stageAdd.setScene(add);
		   stageAdd.setTitle("Caption Photo");
		   stageAdd.setResizable(false);
		   stageAdd.initModality(Modality.WINDOW_MODAL);
		   stageAdd.initOwner(primaryStage);
		   root.requestFocus();
		   primaryStage.setResizable(false);

		   stageAdd.showAndWait();
			primaryStage.setResizable(true);
	}
	
	public void displayPhoto(ActionEvent e){
		
	}
	
	public void tagPhoto(ActionEvent e){
		
	}
	
	public void movePhoto(ActionEvent e){
		
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
		if(!isSelected && selected == null){
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
