package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
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
import model.Admin;
import model.User;
/**
 * Admin Pane Controller controls the admin pane FXML file and implements all the functionalities dealing with admin. 
 * @author Daivik Sheth | Rohan Patel
 *
 * */
public class adminPaneController {
	/**
	 * Username - Gets the username from the field
	 */
	@FXML TextField userName;
	/**
	 * Search Button - Button on the Pane that searches the users. 
	 */
	@FXML Button searchButton;
	/**
	 * Create Button - Button on the Pane that opens the create Dialog Box when Clicked.
	 */
	@FXML Button createButton;
	/**
	 * Delete Button - Button on the Pane that opens the delete Dialog Box when Clicked.
	 */
	@FXML Button deleteButton;
	/**
	 * Logout - Button on the Pane that logs user out
	 */
	@FXML Button logoutButton;
	/***
	 * Tilepane - The pane where all the buttons and albums lie on. 
	 */
	@FXML TilePane tilePane;
	/***
	 * Scrollpane - Scrolling Pane. 
	 */
	@FXML ScrollPane scrollPane;
	/**
	 * Admin  - Admin Object
	 */
	Admin admin;
	/**
	 * Primary Stage - primaryStage Stage of the previous scene 
	 */
	Stage primaryStage;
	/**
	 * Arraylist of all the users
	 */
	public static ArrayList<User> users;
	/**
	 * Link to the previous scene
	 */
	Scene prev;
	/**
	 * Login Pane Controller
	 */
	loginPaneController lpg;
	
	/***
	 * Selected -  Selected Album Name. 
	 */	
	public static String selected;
	/***
	 * isSelected  - Tells you what is selected.
	 */
	public static Boolean isSelected = false;
	
	/**
	 * Start initializes the album pane controller.
	 * @param primaryStage Stage of the previous scene
	 * @param user list of users
	 * @param prev prev scene
	 * @param lpg login pane controller
	 * @param admin - admin object
	 */
	public void start(Stage primaryStage, ArrayList<User> user, Scene prev, loginPaneController lpg, Admin admin) {
		this.primaryStage = primaryStage;
		adminPaneController.users = user;
		this.prev = prev;
		this.lpg = lpg;
		selected = null;
		isSelected = false;
		this.admin = admin;
		for(int i = 0; i < users.size(); i++){
			users.get(i).setUserImage();
		}
		for(int i = 0; i < users.size(); i++){
			tilePane.getChildren().add(users.get(i).getLabel());
		}
		primaryStage.setResizable(true);
		
		
	}
	/**
	 * Search Album - When the Search button is clicked this method is executed.
	 * @param e ActionEvent e
	 */
	public void searchUser(ActionEvent e){  
		
		deselect();
		String name = userName.getText().trim();
		userName.clear();
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
		else{
			Boolean found = false;
			int select = 0;
			 for(int i = 0; i < users.size(); i++){
				   if(users.get(i).userName.equalsIgnoreCase(name)){ 
					   select = i;
					   found = true;
					   break;
				   }
			   }
			 if(found){
				 	users.get(select).selectImage();
				 	Bounds vp = scrollPane.getViewportBounds();
				 	double height = scrollPane.getContent().getBoundsInLocal().getHeight();
				 	//double width = scrollPane.getContent().getBoundsInLocal().getWidth();
				 	double labelLow = ((Label)tilePane.getChildren().get(select)).getBoundsInParent().getMinY();
				 	double labelHigh =  ((Label)tilePane.getChildren().get(select)).getBoundsInParent().getMaxY();
				 		
				 	double vpLow = (height - vp.getHeight()) * scrollPane.getVvalue();
				 	double vpHigh = vpLow + vp.getHeight();
				 	
				 	 if (labelLow < vpLow) {
				 		scrollPane.setVvalue(labelLow / (height - vp.getHeight()));
					 }
				 	 else if (labelHigh > vpHigh) {
					   	scrollPane.setVvalue((labelHigh - vp.getHeight()) / (height - vp.getHeight()));
					}
				 
				 
			 }
			 else{
				 Alert message = new Alert(AlertType.INFORMATION);
				 message.initOwner(primaryStage);
				 message.setTitle("Search Error");
				 message.setHeaderText("Search Completed");
				 message.setContentText("Username was not found.");
				 message.setGraphic(null);
				 message.getDialogPane().getStylesheets().add("/view/loginPane.css");
				 message.showAndWait();
				 return;
			 }
		}
		
		
	}
	/**
	 * Create User  - When the Create button is clicked this method is executed.
	 * @param e ActionEvent e
	 * @throws IOException 
	 */
	public void createUser(ActionEvent e) throws IOException{
			deselect();
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
		   primaryStage.setResizable(false);

		   stageAdd.showAndWait();
			primaryStage.setResizable(true);

		   
	}
	/**
	 * Delete User  - When the Create button is clicked this method is executed.
	 * @param e ActionEvent e
	 * @throws IOException 
	 */
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
		if(getSelectedUser() == -1){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(primaryStage);
			message.setTitle("Delete User");
			message.setHeaderText("Cannot Delete User");
			message.setContentText("You musst first select a user to delete.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
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
			   int userIndex = getSelectedUser();
			   for(int i = 0; i < tilePane.getChildren().size(); i++){
				   Label label = (Label) tilePane.getChildren().get(i);
				   if(label.getId().equalsIgnoreCase(users.get(userIndex).userName)){
					   tilePane.getChildren().remove(i);
					   users.remove(userIndex);
				   }
			   }
		   
	
			   			   
			   
		   }
		deselect();
	}
	/**
	 * Logout - When the logout button is clicked this method is executed. 
	 * @param e ActionEvent E
	 * @throws IOException
	 */
	public void logout(ActionEvent e) throws IOException{  
		deselect();
		Admin.writeAdmin(admin);
		primaryStage.setScene(prev);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
	}
	
	/**
	 * Gets the selected user.
	 * @return int - Selected user Index
	 */
	public int getSelectedUser(){
		if(!isSelected && selected == null){
			return -1;
		}
		for(int i = 0; i < tilePane.getChildren().size(); i++){
			if(tilePane.getChildren().get(i).getId().equalsIgnoreCase(selected)){
				return i;
			}
		}
		return -1;
		
	}
	
	/**
	 * Deselects an album. 
	 */
	public void deselect(){
		int x = getSelectedUser();
		if(x == -1){
			selected = null;
			isSelected = false;
			return;
		}
		if(isSelected && x >= 0 && x < users.size()){
			if(((Label)tilePane.getChildren().get(x)).getStylesheets().size() == 2){
				((Label)tilePane.getChildren().get(x)).getStylesheets().remove(1);
				((Label)tilePane.getChildren().get(x)).getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
			}
		}
		selected = null;
		isSelected = false;
	}
	
	
	
	
}
