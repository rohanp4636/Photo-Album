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
import model.Photo;

/**
 * Create album  Controller controls the create album dialog FXML file and implements all the functionalities dealing with creating album. 
 * @author Daivik Sheth | Rohan Patel
 *
 * */
public class createAlbumController {
	
	/** The album name. */
	@FXML TextField albumName;
	
	/** The ok button. */
	@FXML Button okButton;
	
	/** The cancel button. */
	@FXML Button cancelButton;
	
	/** The local stage. */
	private Stage localStage;
	
	/** Album Pane controller. */
	private albumPaneController apc;
	
	/** Arraylist of albums */
	ArrayList<Album> albums;
	
	/** move photo controller. */
	movePhotoController mpc;
	
	/** Arraylist of photos */
	ArrayList<Photo> searchPhoto;
	
	/** Photo Pane Controller */
	photoPaneController ppc;
	
	/**
	 * Start - initializes the controller.
	 *
	 * @param localStage prev stage. 
	 * @param apc album pane controller
	 * @param albums album array
	 * @param mpc move photo controller
	 * @param searchPhoto arraylist of photos
	 * @param ppc photo pane controller.
	 */
	public void start(Stage localStage, albumPaneController apc, ArrayList<Album> albums, movePhotoController mpc, ArrayList<Photo> searchPhoto,photoPaneController ppc) {
		this.localStage = localStage;
		this.apc = apc;
		this.albums = albums;
		this.mpc = mpc;
		this.searchPhoto = searchPhoto;
		this.ppc = ppc;
	}
	
	
	/**
	 * OK - When the OK button is hit then this method is executed. 
	 *
	 * @param e actionevent e
	 */
	public void createOK(ActionEvent e) {
		String name = albumName.getText().trim().toLowerCase();
		if(name.isEmpty()){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create Album Error");
			message.setHeaderText("Cannot Create Album");
			message.setContentText("Album was not entered.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else if(name.length() > 30){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create Album Error");
			message.setHeaderText("Cannot Create Album");
			message.setContentText("Album must be 30 characters or less.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		else if(albumExists(name.toLowerCase())){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Create Album Error");
			message.setHeaderText("Cannot Create Album");
			message.setContentText("Album already exists.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else{ // put in sorted order if you want.
			
			Album newAlbum = new Album(name);
			if(searchPhoto != null){
				newAlbum = ppc.album;
				newAlbum.setAlbum(name);
				ppc.createAlbumButton.setVisible(false);
			}
			albums.add(0, newAlbum);
			if(mpc != null){ //moves one photo to another album. mpc is from movePhotoController
				newAlbum.getPhotos().add(0,mpc.photo);
				mpc.album.getPhotos().remove(mpc.photo);
				mpc.tilePane.getChildren().remove(mpc.photo.getLabel());
				newAlbum.updateDates();
				mpc.album.updateDates();
				apc.currentUser.updateAlbum(apc, newAlbum);
			}
			apc.tilePane.getChildren().add(0, newAlbum.getLabel());
		}
		localStage.close();
		
	}	

	/**
	 * Cancel - When the Cancel button is hit then this method is executed. 
	 *
	 * @param e the e
	 */
	public void createCancel(ActionEvent e){
		localStage.close();
	}
	
	/**
	 * Checks if the album exists
	 *
	 * @param name the name of the akbum
	 * @return  boolean if the album exists or not
	 */
	public Boolean albumExists(String name){
		if(albums.isEmpty()){
			return false;
		}
		for(int i = 0; i < albums.size(); i ++){
			if(albums.get(i).getAlbumName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
		
}
