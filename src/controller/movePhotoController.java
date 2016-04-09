package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

public class movePhotoController {
	
	@FXML ComboBox<String> albumBox;
	
	
	
	Stage localStage;
	photoPaneController ppc;
	Photo photo;
	TilePane tilePane;
	ArrayList<Album> albums;
	Album album;
	public void start(Stage localStage, photoPaneController ppc, Photo photo, TilePane tilePane,ArrayList<Album> albums, Album album) {
		this.tilePane = tilePane;
		this.localStage = localStage;
		this.ppc = ppc;
		this.photo = photo;
		this.albums = albums;
		this.album = album;
		ArrayList<String> albumNames = new ArrayList<String>();
		for(Album i : albums){
			if(i.getAlbumName().equalsIgnoreCase(album.getAlbumName())){
				continue;
			}
			albumNames.add(i.getAlbumName());
		}
		albumNames.add("Move to a new album");
		albumBox.setItems(FXCollections.observableArrayList(albumNames));
	
		
	}
	
	
	public void okMove(ActionEvent e) {

		
	}	

	public void cancelMove(ActionEvent e){
		localStage.close();
	}
	
}
