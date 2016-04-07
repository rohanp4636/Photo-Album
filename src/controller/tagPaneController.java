package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;

public class tagPaneController {
	
	@FXML TextArea tagArea;
	@FXML ComboBox<Tag> tagDropDown;
	@FXML ComboBox<String> valueDropDown;

	
	private Stage localStage;
	Photo photo;
	
		
	public void start(Stage localStage,  Photo photo) {
		this.localStage = localStage;
		this.photo = photo;
		tagArea.setWrapText(true);	
		setDropDown();
		tagDropDown.setOnAction(e -> setTagValue());
	}
	
	public void setTagValue(){
		if(tagDropDown.getValue() != null){
			valueDropDown.getItems().clear();
			valueDropDown.getItems().addAll(FXCollections.observableArrayList(tagDropDown.getValue().getValue()));
		}
	}
	
	public void setDropDown(){
		tagDropDown.getItems().clear();
		tagDropDown.getItems().addAll(FXCollections.observableArrayList(photo.getTags()));
	}
	public void delete(ActionEvent e){
		if(photo.getTags().isEmpty()){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Tag");
			message.setHeaderText("Cannot Delete Tag");
			message.setContentText("Photo has not been tagged yet");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		else if(tagDropDown.getValue() == null && valueDropDown.getValue() == null){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Tag");
			message.setHeaderText("Cannot Delete Tag");
			message.setContentText("Select a tag type and value to delete from the drop down menus");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		else if(tagDropDown.getValue() != null && valueDropDown.getValue() != null){
			String name = tagDropDown.getValue().toString();
			int index = photo.getTags().indexOf(tagDropDown.getValue());
			photo.getTags().get(index).deleteTag(name);
			setDropDown();
			
			
		}
	}
	
	
	public void ok(ActionEvent e){
		localStage.close();
	}
	
	public void cancel(ActionEvent e){
		localStage.close();
	}
}
