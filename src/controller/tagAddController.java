package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Photo;

public class tagAddController {
	@FXML TextField tagType;
	@FXML TextField tagValue;
	
	@FXML TextArea tagArea;
	private Stage localStage;
	Photo photo;
	

	public void start(Stage localStage,  Photo photo) {
		this.localStage = localStage;
		this.photo = photo;
		tagArea.setWrapText(true);	

	}
	
	public void add(ActionEvent e){
		localStage.close();
	}
	
	public void ok(ActionEvent e){
		localStage.close();
	}
	
	public void cancel(ActionEvent e){
		localStage.close();
	}

}
