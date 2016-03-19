package controller;

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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

public class adminPaneController {
	@FXML TextField userName;
	@FXML Button searchButton;
	@FXML Button createButton;
	@FXML Button deleteButton;
	@FXML Button logoutButton;
	
	@FXML TilePane tilePane;
	
	@FXML ScrollPane scrollPane;
	
	Stage primaryStage;
	ArrayList<User> users;
	Scene prev;
	loginPaneController lpg;
	
	
	int selectedUser;
	
	public void start(Stage primaryStage, ArrayList<User> user, Scene prev, loginPaneController lpg) {
		this.primaryStage = primaryStage;
		this.users = user;
		this.prev = prev;
		this.lpg = lpg;
		this.selectedUser = 0;
		/*for(int i = 0; i < this.users.size(); i++){
			Label label = users.get(i).getLabel();
			tilePane.getChildren().add(label);
		}*/
		
		
		//userName.setText("in adminPane start");
	}
	
	public void searchUser(ActionEvent e){
		String name = userName.getText();
		if(name.isEmpty()){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Search Error");
			message.setHeaderText("Cannot Complete Search");
			message.setContentText("Username was not entered.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
	}
	
	public void createUser(ActionEvent e) throws IOException{
		   Stage stageAdd = new Stage();
		   
		   FXMLLoader load = new FXMLLoader();
		   load.setLocation(getClass().getResource("/view/createUser.fxml"));
		   AnchorPane root = (AnchorPane)load.load();
		   createUserController cuc= load.getController();
		   cuc.start(stageAdd,this,users);
		   
		   Scene add = new Scene(root);
		   stageAdd.setScene(add);
		   stageAdd.setTitle("Create User");
		   stageAdd.setResizable(false);
		   stageAdd.initModality(Modality.WINDOW_MODAL);
		   stageAdd.initOwner(primaryStage);
		   root.requestFocus();
		   stageAdd.showAndWait();
	}
	
	public void deleteUser(ActionEvent e) throws IOException{
		if(users.size() == 0){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Delete User");
			message.setHeaderText("Cannot Delete User");
			message.setContentText("There are no users to delete.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
		if(selectedUser == -1){
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Delete User");
		alert.setHeaderText("Confirm Deletion");
		alert.setContentText("Are you sure you want to delete this user?");
		alert.getDialogPane().getStylesheets().add("/view/loginPane.css");
		alert.setGraphic(null);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) { 
			  Alert message = new Alert(AlertType.INFORMATION);
			   message.initOwner(primaryStage);
			   message.setTitle("Delete User");
			   message.setHeaderText("Delete Complete");
			   message.setContentText("The selected user has been deleted");
			   message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			   message.setGraphic(null);
			   message.showAndWait();
			   for(int i = 0; i < tilePane.getChildren().size(); i++){
				   Label label = (Label) tilePane.getChildren().get(i);
				   if(label.getId().equalsIgnoreCase(users.get(selectedUser).userName)){
					   tilePane.getChildren().remove(i);
					   users.remove(selectedUser);
				   }
			   }
	
			   			   
			   
		   }
	}
	
	public void logout(ActionEvent e){
		primaryStage.setScene(prev);
	}
	
	
	
	
	
}
