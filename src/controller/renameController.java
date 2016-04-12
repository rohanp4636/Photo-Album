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
/**
 * Rename Controller controls the rename dialog FXML file and implements all the functionalities dealing with rename. 
 * @author Daivik Sheth | Rohan Patel
 *
 * */
public class renameController {
	
	/** The album name. */
	@FXML TextField albumName;
	
	/** The ok button. */
	@FXML Button okButton;
	
	/** The cancel button. */
	@FXML Button cancelButton;
	
	/** The local stage. */
	private Stage localStage;
	
	/** The album pane controller. */
	private albumPaneController apc;
	
	/** The album arraylist. */
	ArrayList<Album> album;
	
	/** The index of the current photo. */
	int index;
		
	/**
	 * Start initializes the rename controller.
	 * @param localStage the previous stage
	 * @param apc the album pane controller
	 * @param a the album arraylist
	 * @param i the index of the current photo
	 */
	public void start(Stage localStage, albumPaneController apc, ArrayList<Album> a,int i) {
		this.localStage = localStage;
		this.apc = apc;
		this.album= a;
		this.index = i;
		albumName.setText(a.get(i).getAlbumName());
	}
	
	
	/**
	 * When the OK button is clicked this method is executed. 
	 *
	 * @param e the e
	 */
	public void createOK(ActionEvent e) {
		String name = albumName.getText().trim().toLowerCase();
		if(name.isEmpty()){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Rename Album Error");
			message.setHeaderText("Cannot Rename Album");
			message.setContentText("Album was not renamed.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else if(name.length() > 30){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Rename Album Error");
			message.setHeaderText("Cannot Rename Album");
			message.setContentText("Album name must be 30 characters or less.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		else if(albumExists(name.toLowerCase())){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Rename Album Error");
			message.setHeaderText("Cannot Rename Album");
			message.setContentText("Album with that name already exists.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else{ // put in sorted order if you want.
			album.get(index).setAlbum(name);
			apc.currentUser.updateAlbum(apc, album.get(index));
			
		}
		
		localStage.close();
		
	}	

	/**
	 * When the cancel button is hit this method is executed. 
	 *
	 * @param e the e
	 */
	public void createCancel(ActionEvent e){
		localStage.close();
	}
	
	/**
	 * Checks if the album exits to avoid renamaing it to an album that exits. 
	 *
	 * @param name the name
	 * @return the boolean
	 */
	public Boolean albumExists(String name){
		if(album.isEmpty()){
			return false;
		}
		for(int i = 0; i < album.size(); i ++){
			if(album.get(i).getAlbumName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
		
}
