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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.User;

/***
 * Album Pane Controller controls the album pane FXML file and implements all the functionalities dealing with albums. 
 * @author Daivik Sheth | Rohan Patel
 */

public class albumPaneController {
	/***
	 * Open Button - Button on the Pane that Opens the Album when Clicked. 
	 */
	@FXML Button openButton;
	/***
	 * Create Button - Button on the Pane that opens the create Dialog Box when Clicked. 
	 */
	@FXML Button createButton;
	/***
	 * Rename Button - Button on the Pane that opens the rename Dialog Box when Clicked. 
	 */
	@FXML Button renameButton;
	/***
	 * Delete Button - Button on the Pane that opens the delete Dialog Box when Clicked. 
	 */
	@FXML Button deleteButton;
	/***
	 * Search Button - Button on the Pane that opens the search Dialog Box when Clicked. 
	 */
	@FXML Button searchButton;
	/***
	 * Logout Button - Button on the Pane that logs the user out of the session. 
	 */
	@FXML Button logoutButton;
	/***
	 * Tilepane - The pane where all the buttons and albums lie on. 
	 */
	@FXML TilePane tilePane;
	/***
	 * Scrollpane - Scrolling Pane. 
	 */
	@FXML ScrollPane scrollPane;
	
	/***
	 * Primarystage  - primaryStage Stage of the previous scene
	 */
	Stage primaryStage;

	/***
	 * Users  - ArrayList of all the users.  
	 */
	ArrayList<User> users;

	/***
	 * Prev  - Previous Scene
	 */
	Scene prev;

	/***
	 * lpg  - Login Pane Controller 
	 */
	loginPaneController lpg;

	/***
	 * username  - current username
	 */
	String userName;

	/***
	 * Admin  - admin object
	 */
	Admin admin;

	/***
	 * Albums  - Arraylist of all the albums 
	 */
	public static ArrayList<Album> albums;

	/***
	 * Current User  - Current User 
	 */
	User currentUser;

	/***
	 * User Index  - Current User Index
	 */
	int userIndex;

	/***
	 * Selected -  Selected Album Name. 
	 */	
	public static String selected;

	/***
	 * isSelected  - Tells you what is selected.
	 */
	public static Boolean isSelected = false;
/**
 * Start initializes the album pane controller.
 * @param primaryStage Stage of the previous scene
 * @param user list of users
 * @param prev prev scene
 * @param lpg login pane controller
 * @param userName current username
 * @param aList album list
 * @param cUser current user object
 * @param uIndex current user index
 * @param admin admin object 
 */
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
	/**
	 * Open Album - When the open album button is clicked this method is executed. 
	 * @param e ActionEvent e - On Click Execute this method. 
	 */
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
	/**
	 * Create Album - When the Create album button is clicked this method is executed. 
	 * @param e ActionEvent E
	 * @throws IOException  throw an exception. 
	 */
	public void createAlbum(ActionEvent e) throws IOException{
		   deselect();
		   Stage stageAdd = new Stage();
		   
		   FXMLLoader load = new FXMLLoader();
		   load.setLocation(getClass().getResource("/view/create.fxml"));
		   AnchorPane root = (AnchorPane)load.load();
		   createAlbumController cac= load.getController();
		   cac.start(stageAdd,this,albums,null,null,null);
		   
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
	/**
	 * Rename Album - When the Rename album button is clicked this method is executed. 
	 * @param e ActionEvent E
	 */
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
	
	/**
	 * Delete Album  - When the Delete album button is clicked this method is executed. 
	 * @param e ActionEvent E
	 */
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
	/**
	 * Search Album - When the Search album button is clicked this method is executed. 
	 * @param e ActionEvent E
	 */
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
	/**
	 * Logout - When the logout button is clicked this method is executed. 
	 * @param e ActionEvent E
	 * @throws IOException  throw an exception. 
	 */
	public void logout(ActionEvent e) throws IOException{
		deselect();
		Admin.writeAdmin(admin);
		primaryStage.setScene(prev);
		primaryStage.setWidth(1296);
		primaryStage.setHeight(760);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
	}
	/**
	 * Gets the selected album.
	 * @return int - Selected ALbum Index
	 */
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
	/**
	 * Gets the tilepane. 
	 * @return tile pane
	 */
	public TilePane getTPane(){
		return this.tilePane;
	}
	/**
	 * Deselects an album. 
	 */
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
