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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.Photo;
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
	Admin admin;
	public static ArrayList<Album> albums;
	User currentUser;
	int userIndex;
	
	public static String selected;
	public static Boolean isSelected = false;

	public void start(Stage primaryStage, ArrayList<User> user, Scene prev, loginPaneController lpg, String userName, ArrayList<Album> aList, User cUser, int uIndex, Admin admin) {
		this.primaryStage = primaryStage;
		this.users = user;
		this.prev = prev;
		this.lpg = lpg;
		this.userName = userName;
		this.currentUser = cUser;
		albums=aList;
		this.userIndex=uIndex;
		this.admin = admin;
		selected=null;
		isSelected=false;
		for(int j = 0; j < albums.size(); j++){
			for(int k = 0; k < albums.get(j).getPhotos().size(); k++){  // fix this
				File file = new File(albums.get(j).getPhotos().get(k).getPath());
				if(!file.exists()){
					albums.get(j).getPhotos().remove(k);
					k--;
					albums.get(j).numPhotos--;
				}

			}
			albums.get(j).updateDates();
			albums.get(j).setAlbumCover();	
		}
		
		for(int i = 0; i < albums.size(); i++){
			tilePane.getChildren().add(albums.get(i).getLabel());
		}
		primaryStage.setResizable(true);

		
	}
	
	public void openAlbum(ActionEvent e){
		if(albums.size()==0)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Open Album");
			message.setHeaderText("Cannot Open Album");
			message.setContentText("There are no albums to open");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(getSelectedAlbum()==-1)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Open Album");
			message.setHeaderText("Cannot Open Album");
			message.setContentText("You must first select an Album");
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
				load.setLocation(getClass().getResource("/view/photoPane.fxml"));
				AnchorPane root = (AnchorPane)load.load();
				photoPaneController ppc = load.getController();
				ppc.start(primaryStage,users, currentUser, userIndex, primaryStage.getScene(),this, albums.get(getSelectedAlbum()), false);
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
	
	public void createAlbum(ActionEvent e) throws IOException{
		   deselect();
		   Stage stageAdd = new Stage();
		   
		   FXMLLoader load = new FXMLLoader();
		   load.setLocation(getClass().getResource("/view/create.fxml"));
		   AnchorPane root = (AnchorPane)load.load();
		   createAlbumController cac= load.getController();
		   cac.start(stageAdd,this,albums,null);
		   
		   Scene add = new Scene(root);
		   stageAdd.setScene(add);
		   stageAdd.setTitle("Create Album");
		   stageAdd.setResizable(false);
		   stageAdd.initModality(Modality.WINDOW_MODAL);
		   stageAdd.initOwner(primaryStage);
		   root.requestFocus();
		   primaryStage.setResizable(false);

		   stageAdd.showAndWait();
			primaryStage.setResizable(true);
	}
	
	public void renameAlbum(ActionEvent e){
		if(albums.size()==0)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Rename Album");
			message.setHeaderText("Cannot Rename Album");
			message.setContentText("There are no albums to Rename");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(getSelectedAlbum()==-1)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Rename Album");
			message.setHeaderText("Cannot Rename Album");
			message.setContentText("You must first select an Album");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(isSelected)
		{
			try{
				Stage stageAdd = new Stage();
				FXMLLoader load = new FXMLLoader();
				load.setLocation(getClass().getResource("/view/rename.fxml"));
				AnchorPane root = (AnchorPane)load.load();
				renameController rc= load.getController();
				rc.start(stageAdd,this, albums, getSelectedAlbum());
				deselect(); 
			    Scene add = new Scene(root);
			    stageAdd.setScene(add);
			    stageAdd.setTitle("Rename Album");
			    stageAdd.setResizable(false);
			    stageAdd.initModality(Modality.WINDOW_MODAL);
			    stageAdd.initOwner(primaryStage);
			    root.requestFocus();
			    primaryStage.setResizable(false);
			    stageAdd.showAndWait();
				primaryStage.setResizable(true);
				
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		deselect();
	}
	
	public void deleteAlbum(ActionEvent e){
		if(albums.size()==0)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Delete Album");
			message.setHeaderText("Cannot Delete Album");
			message.setContentText("There are no albums to Delete");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(getSelectedAlbum()==-1)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Delete Album");
			message.setHeaderText("Cannot Delete Album");
			message.setContentText("You must first select an Album");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		if(isSelected)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(primaryStage);
			alert.setTitle("Delete Album");
			alert.setHeaderText("Confirm Delete");
			alert.setContentText("Are you sure you want to Delete this photo?");
			alert.getDialogPane().getStylesheets().add("/view/loginPane.css");
			alert.setGraphic(null);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) { 
				  Alert message = new Alert(AlertType.INFORMATION);
				   message.initOwner(primaryStage);
				   message.setTitle("Delete Photo");
				   message.setHeaderText("Delete Complete");
				   message.setContentText("The selected Album has been Deleted");
				   message.getDialogPane().getStylesheets().add("/view/loginPane.css");
				   message.setGraphic(null);
				   message.showAndWait();
				   int index = getSelectedAlbum();
				   for(int i = 0; i < tilePane.getChildren().size(); i++){
					   Label label = (Label) tilePane.getChildren().get(i);
					   if(label.getId().equalsIgnoreCase(albums.get(index).getAlbumName())){
						   tilePane.getChildren().remove(i);
						   albums.remove(index);
						 
						   break;
					   }
				   }
			   }
			  deselect();
		}
	}
	
	public void searchAlbum(ActionEvent e){  // when you open photo album with searched photos make last parameter false in start method.  also check milisecond
		if(albums.size()==0)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Search Album");
			message.setHeaderText("Cannot Search Album");
			message.setContentText("There are no albums to Search");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			deselect();
			return;
		}
		try{
			Stage stageAdd = new Stage();
			FXMLLoader load = new FXMLLoader();
			load.setLocation(getClass().getResource("/view/search.fxml"));
			AnchorPane root = (AnchorPane)load.load();
			searchController sc= load.getController();
			sc.start(stageAdd,this, albums);
			deselect(); 
		    Scene add = new Scene(root);
		    stageAdd.setScene(add);
		    stageAdd.setTitle("Delete Album");
		    stageAdd.setResizable(false);
		    stageAdd.initModality(Modality.WINDOW_MODAL);
		    stageAdd.initOwner(primaryStage);
		    root.requestFocus();
		    primaryStage.setResizable(false);
		    stageAdd.showAndWait();
			primaryStage.setResizable(true);
			
		}catch(Exception ee){
			ee.printStackTrace();
		}
		
	}
	public void logout(ActionEvent e) throws IOException{
		deselect();
		Admin.writeAdmin(admin);
		primaryStage.setScene(prev);
		primaryStage.setWidth(1296);
		primaryStage.setHeight(760);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
	}
	public int getSelectedAlbum(){
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
	
	public TilePane getTPane(){
		return this.tilePane;
	}
	
	public void deselect(){
		int x = getSelectedAlbum();
		if(x == -1){
			selected = null;
			isSelected = false;
			return;
		}
		if(isSelected && x >= 0 && x < albums.size()){
			if(((Label)tilePane.getChildren().get(x)).getStylesheets().size() == 2){
				((Label)tilePane.getChildren().get(x)).getStylesheets().remove(1);
				((Label)tilePane.getChildren().get(x)).getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
			}
		}
		selected = null;
		isSelected = false;
	}
	
	
	
	
	
	
}
