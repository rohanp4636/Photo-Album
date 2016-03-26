package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;


public class createUserController {
	
	@FXML TextField userName;
	@FXML Button okButton;
	@FXML Button cancelButton;
	
	private Stage localStage;
	private adminPaneController apc;
	ArrayList<User> users;
		
	public void start(Stage localStage, adminPaneController apc, ArrayList<User> users) {
		this.localStage = localStage;
		this.apc = apc;
		this.users = users;
	}
	
	
	public void createOK(ActionEvent e) {
		String name = userName.getText().trim().toLowerCase();
		if(name.isEmpty()){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create User Error");
			message.setHeaderText("Cannot Create User");
			message.setContentText("Username was not entered.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else if(name.length() > 30){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create User Error");
			message.setHeaderText("Cannot Create User");
			message.setContentText("Username must be 30 characters or less.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		else if(userExists(name.toLowerCase())){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create User Error");
			message.setHeaderText("Cannot Create User");
			message.setContentText("Username already exists.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else{ // put in sorted order if you want.
			User newUser = new User(name);
			users.add(0, newUser);
			apc.tilePane.getChildren().add(0, newUser.getLabel());
		}
		localStage.close();
		
	}	

	public void createCancel(ActionEvent e){
		localStage.close();
	}
	
	public Boolean userExists(String name){
		if(users.isEmpty()){
			return false;
		}
		for(int i = 0; i < users.size(); i ++){
			if(users.get(i).userName.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
		
}
