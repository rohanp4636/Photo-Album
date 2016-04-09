package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
	
	@FXML Button createAlbumButton;
	Stage primaryStage;
	ArrayList<User> users;
	public static ArrayList<Photo> photos;
	User currentUser;
	int userIndex;
	Scene prev;
	albumPaneController apc;
	Album album;
	Boolean search;
	public static String selected;
	public static Boolean isSelected = false;
	
	public void start(Stage primaryStage, ArrayList<User> user, User currentUser, int index, Scene prev, albumPaneController apc, Album album, Boolean search) {
		this.primaryStage = primaryStage;
		this.users = user;
		photoPaneController.photos = album.getPhotos();
		this.currentUser = currentUser;
		this.prev = prev;
		this.userIndex = index;
		this.apc = apc;
		this.album = album;
		this.search = search;
		if(!this.search){
			createAlbumButton.setVisible(false);
		}
		selected = null;
		isSelected = false;
		for(int k = 0; k < photos.size(); k++){  // fix this
			File file = new File(photos.get(k).getPath());
			if(!file.exists()){
				photos.remove(k);
				k--;
				album.numPhotos--;
			}
			else{
				photos.get(k).setPhotoThumbnail();	
			}

		}
		for(int i = 0; i < photos.size(); i++){
			tilePane.getChildren().add(photos.get(i).getLabel()); 
		}
		primaryStage.setResizable(true);
		
	}
	
	public void addPhoto(ActionEvent e){  //use absolute path .. when using demo to submit create it from star???
		deselect();
		FileChooser filechooser = new FileChooser();
		filechooser.getExtensionFilters().add(
				new ExtensionFilter("Images (PNG,JPG)", "*.png", "*.jpg"));
		List<File> files = filechooser.showOpenMultipleDialog(primaryStage);
		if(files == null){
			return;
		}
		ArrayList<File> filesThatExist = new ArrayList<File>();
		for(int j = 0; j < files.size(); j++){
			File file = files.get(j);
			if(file != null){
				Boolean add = true;
				String demoPath = "\\data\\demoImagesIDRohanDaivik01\\";
				for(int i = 0; i < photos.size(); i++){
					if(photos.get(i).getPath().equalsIgnoreCase(file.getAbsolutePath())){
						filesThatExist.add(file);
						add = false;
						break;
					}
				}
				//System.out.println(file.lastModified());
				//check parent if in data and if it is make demo photo.
				if(add){
					Photo photo = null;
					if(file.getParentFile().getName().equals("demoImagesIDRohanDaivik01")){
						if(file.getParentFile().getParentFile().getName().equals("data")){
							photo = new Photo(file,album,true,demoPath+file.getName());
							
						}
					}
					else{
						photo = new Photo(file, album, false, file.getAbsolutePath());
					}
					photos.add(0, photo);
					tilePane.getChildren().add(0, photo.getLabel());
					album.updateDates();
					currentUser.updateAlbum(apc, album);
					
				}
			}
		}
		if(!filesThatExist.isEmpty()){
			primaryStage.setResizable(false);
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Add Photo");
			message.setHeaderText("Cannot Add Photo");
			message.setContentText("Some selected photos may already exists in album. Could not add the following files:");
			message.setGraphic(null);
			String photoPaths = "";
			for(int i = 0; i < filesThatExist.size(); i++){
				photoPaths= photoPaths + filesThatExist.get(i).getAbsolutePath()+"\n";
			}
			TextArea ta = new TextArea(photoPaths);
			ta.setEditable(false);
			ta.setMaxWidth(Double.MAX_VALUE);
			ta.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(ta, Priority.ALWAYS);
			GridPane.setHgrow(ta, Priority.ALWAYS);
			GridPane gp = new GridPane();
			
		
			gp.add(ta, 0, 0);
			message.getDialogPane().setExpandableContent(gp);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			primaryStage.setResizable(true);
			return;
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
			deselect();
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
			deselect();
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
					   album.updateDates();
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
			deselect();
			return;
		}
		if(getSelectedUser() == -1){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Caption Photo");
			message.setHeaderText("Cannot Caption Photo");
			message.setContentText("You must first select a photo to caption.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
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
			deselect();
	}
	
	public void displayPhoto(ActionEvent e){
		if(photos.size()==0)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Display Photo");
			message.setHeaderText("Cannot Display Photo");
			message.setContentText("There are no photos to display");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(getSelectedUser()==-1)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Display Photo");
			message.setHeaderText("Cannot Display Photo");
			message.setContentText("You must first select a photo");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(isSelected)
		{
			try{
				
				FXMLLoader load = new FXMLLoader();
				load.setLocation(getClass().getResource("/view/displayPane.fxml"));
				AnchorPane root = (AnchorPane)load.load();
				displayPaneController dpc = load.getController();
				dpc.start(primaryStage,photos, primaryStage.getScene(),this,getSelectedUser());
				deselect();
				Scene scene = new Scene(root);
				double w = primaryStage.getWidth();
				double h = primaryStage.getHeight();
				primaryStage.setScene(scene);
				primaryStage.setWidth(w);
				primaryStage.setHeight(h);
				
				root.requestFocus();
				
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		deselect();
	}
	
	public void tagPhoto(ActionEvent e) throws IOException{
		if(photos.size() == 0){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Tag Photo");
			message.setHeaderText("Cannot Tag Photo");
			message.setContentText("There are no photos to tag.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(getSelectedUser() == -1){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Tag Photo");
			message.setHeaderText("Cannot Tag Photo");
			message.setContentText("You musst first select a photo to tag.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		  
		 
		  Stage stageAdd = new Stage();
		   FXMLLoader load = new FXMLLoader();
		   AnchorPane root;
		   load.setLocation(getClass().getResource("/view/Tag.fxml"));
		   root = (AnchorPane)load.load();
			tagPaneController tpc= load.getController();
			tpc.start(stageAdd,photos.get(getSelectedUser())); 
		   Scene add = new Scene(root);
		   stageAdd.setScene(add);
		   stageAdd.setTitle("Tag Photo");
		   stageAdd.setResizable(false);
		   stageAdd.initModality(Modality.WINDOW_MODAL);
		   stageAdd.initOwner(primaryStage);
		   root.requestFocus();
		   primaryStage.setResizable(false);

		   stageAdd.showAndWait();
			primaryStage.setResizable(true);
			deselect();
	
	}
	
	public void movePhoto(ActionEvent e) throws IOException{
		if(photos.size() == 0){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Move Photo");
			message.setHeaderText("Cannot Move Photo");
			message.setContentText("There are no photos to move.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(getSelectedUser() == -1){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Move Photo");
			message.setHeaderText("Cannot Move Photo");
			message.setContentText("You must first select a photo to move.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		   Stage stageAdd = new Stage();
		   
		   FXMLLoader load = new FXMLLoader();
		   load.setLocation(getClass().getResource("/view/move.fxml"));
		   AnchorPane root = (AnchorPane)load.load();
		   movePhotoController mpc= load.getController();
		
		   mpc.start(stageAdd,this,photos.get(getSelectedUser()),this.tilePane);
		   
		   Scene add = new Scene(root);
		   stageAdd.setScene(add);
		   stageAdd.setTitle("Move Photo");
		   stageAdd.setResizable(false);
		   stageAdd.initModality(Modality.WINDOW_MODAL);
		   stageAdd.initOwner(primaryStage);
		   root.requestFocus();
		   primaryStage.setResizable(false);

		   stageAdd.showAndWait();
			primaryStage.setResizable(true);
			deselect();
	}
	
	public void slideshow(ActionEvent e){
			
	}
	public void createAlbum(ActionEvent e){ // create new album from searched photos.
		if(!this.search){
			return;
		}
		
		
	}
	
	
	public void back(ActionEvent e){
		deselect();
		album.updateDates();
		currentUser.updateAlbum(apc, album);
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
		if(isSelected && x >= 0 && x < photos.size()){
			if(((Label)tilePane.getChildren().get(x)).getStylesheets().size() == 2){
				((Label)tilePane.getChildren().get(x)).getStylesheets().remove(1);
				((Label)tilePane.getChildren().get(x)).getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
			}
		}
		selected = null;
		isSelected = false;
	}
	
}
