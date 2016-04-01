package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Photo;
import model.User;


public class captionController {
	
	@FXML TextArea caption;
	@FXML Button okButton;
	@FXML Button cancelButton;
	
	private Stage localStage;
	private photoPaneController ppc;
	Photo photo;
	TilePane tilePane;
	Boolean recaption = false;
		
	public void start(Stage localStage, photoPaneController ppc, Photo photo, boolean recaption, TilePane tilePane) {
		this.tilePane = tilePane;
		this.localStage = localStage;
		this.ppc = ppc;
		this.photo = photo;
		caption.setWrapText(true);
		this.recaption = recaption;
		if(recaption){
			caption.setText(photo.getCaption());
		}
		else{
			caption.setPromptText("Enter a caption here.");
		}
		
		
	}
	
	
	public void okCaption(ActionEvent e) {
		String cap = caption.getText().trim();
		if(cap.isEmpty() && !recaption){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Caption");
			message.setHeaderText("Cannot Caption Photo");
			message.setContentText("Caption was not entered.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else if(caption.getText().length() > 140){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Caption");
			message.setHeaderText("Cannot Caption Photo");
			message.setContentText("Caption must be 140 characters or less.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		else{ // put in sorted order if you want.
			photo.setCaption(caption.getText());
			int index = tilePane.getChildren().indexOf(photo.getLabel());
			((Label)tilePane.getChildren().get(index)).setText(caption.getText());
		}
		localStage.close();
		
	}	

	public void cancelCaption(ActionEvent e){
		localStage.close();
	}
	

		
}

