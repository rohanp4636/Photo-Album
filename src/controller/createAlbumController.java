package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.User;


public class createAlbumController {
	
	@FXML TextField albumName;
	@FXML Button okButton;
	@FXML Button cancelButton;
	
	private Stage localStage;
	private albumPaneController apc;
	ArrayList<Album> albums;
		
	public void start(Stage localStage, albumPaneController apc, ArrayList<Album> albums) {
		this.localStage = localStage;
		this.apc = apc;
		this.albums = albums;
	}
	
	
	public void createOK(ActionEvent e) {
		String name = albumName.getText().trim().toLowerCase();
		if(name.isEmpty()){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create Album Error");
			message.setHeaderText("Cannot Create Album");
			message.setContentText("Album was not entered.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else if(name.length() > 30){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create Album Error");
			message.setHeaderText("Cannot Create Album");
			message.setContentText("Album must be 30 characters or less.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		else if(albumExists(name.toLowerCase())){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create User Error");
			message.setHeaderText("Cannot Create User");
			message.setContentText("Album already exists.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else{ // put in sorted order if you want.
			Album newAlbum = new Album(name);
			albums.add(0, newAlbum);
			apc.tilePane.getChildren().add(0, newAlbum.getLabel());
		}
		localStage.close();
		
	}	

	public void createCancel(ActionEvent e){
		localStage.close();
	}
	
	public Boolean albumExists(String name){
		if(albums.isEmpty()){
			return false;
		}
		for(int i = 0; i < albums.size(); i ++){
			if(albums.get(i).getAlbumName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
		
}
