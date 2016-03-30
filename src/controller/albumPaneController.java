package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
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
	
	public static ArrayList<Album> albums;
	User currentUser;
	int userIndex;
	
	public static String selected;
	public static Boolean isSelected = false;
	
	public void start(Stage primaryStage, ArrayList<User> user, Scene prev, loginPaneController lpg, String userName, ArrayList<Album> aList, User cUser, int uIndex) {
		this.primaryStage = primaryStage;
		this.users = user;
		this.prev = prev;
		this.lpg = lpg;
		this.userName = userName;
		this.currentUser = cUser;
		albums=aList;
		this.userIndex=uIndex;
		selected=null;
		isSelected=false;
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
			return;
		}
		if(isSelected)
		{
			try{
				
				FXMLLoader load = new FXMLLoader();
				load.setLocation(getClass().getResource("/view/photoPane.fxml"));
				AnchorPane root = (AnchorPane)load.load();
				photoPaneController ppc = load.getController();
				ppc.start(primaryStage,users, currentUser, userIndex, primaryStage.getScene(),this, albums.get(getSelectedAlbum()));
				deselect();
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				root.requestFocus();
				
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
		
	}
	
	public void createAlbum(ActionEvent e) throws IOException{
			deselect();
		   Stage stageAdd = new Stage();
		   
		   FXMLLoader load = new FXMLLoader();
		   load.setLocation(getClass().getResource("/view/create.fxml"));
		   AnchorPane root = (AnchorPane)load.load();
		   createAlbumController cac= load.getController();
		   cac.start(stageAdd,this,albums);
		   
		   Scene add = new Scene(root);
		   stageAdd.setScene(add);
		   stageAdd.setTitle("Create User");
		   stageAdd.setResizable(false);
		   stageAdd.initModality(Modality.WINDOW_MODAL);
		   stageAdd.initOwner(primaryStage);
		   root.requestFocus();
		   primaryStage.setResizable(false);

		   stageAdd.showAndWait();
			primaryStage.setResizable(true);
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
	public int getSelectedAlbum(){
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
