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

/**
 * The Class createUserController controls the create user pane FXML file and implements all the functionalities dealing with creating a user.
 * @author Daivik Sheth | Rohan Patel
 */
public class createUserController {
	
	/** The user name. */
	@FXML TextField userName;
	
	/** The ok button. */
	@FXML Button okButton;
	
	/** The cancel button. */
	@FXML Button cancelButton;
	
	/** The local stage. */
	private Stage localStage;
	
	/** The admin pane controller. */
	private adminPaneController apc;
	
	/** list of all users. */
	ArrayList<User> users;
		
	/**
	 * Start initializes the create user controller.
	 *
	 * @param localStage previous scene
	 * @param apc - admin pane controller
	 * @param users - list of users
	 */
	public void start(Stage localStage, adminPaneController apc, ArrayList<User> users) {
		this.localStage = localStage;
		this.apc = apc;
		this.users = users;
	}
	
	
	/**
	 * When the ok button is clicked this method is executed.
	 * @param e the e
	 */
	public void createOK(ActionEvent e) {
		String name = userName.getText().trim().toLowerCase();
		if(name.isEmpty()){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create User Error");
			message.setHeaderText("Cannot Create User");
			message.setContentText("Usern was not entered.");
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

	/**
	 * When the Cancel button is clicked this method is executed.
	 *
	 * @param e the e
	 */
	public void createCancel(ActionEvent e){
		localStage.close();
	}
	
	/**
	 * Checks if the user exists
	 *
	 * @param name username
	 * @return boolean - if the user exits of not
	 */
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
