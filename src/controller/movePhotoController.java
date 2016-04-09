package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Photo;

public class movePhotoController {
	
	@FXML ComboBox albumBox;
	
	
	
	Stage localStage;
	photoPaneController ppc;
	Photo photo;
	TilePane tilePane;
	
	public void start(Stage localStage, photoPaneController ppc, Photo photo, TilePane tilePane) {
		this.tilePane = tilePane;
		this.localStage = localStage;
		this.ppc = ppc;
		this.photo = photo;
		
		
		
	}
	
	
	public void okMove(ActionEvent e) {

		
	}	

	public void cancelMove(ActionEvent e){
		localStage.close();
	}
	
}
