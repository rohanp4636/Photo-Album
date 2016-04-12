package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

/**
 * Move Photo Controller controls the Move dialog box's FXML file and implements all the functionalities dealing with move. 
 * @author Daivik Sheth | Rohan Patel
 *
 * */
public class movePhotoController {
	
	/** The album box. */
	@FXML ComboBox<String> albumBox;
	
	/** The previous stage. */
	Stage localStage;
	
	/** Photo pane controller*/
	photoPaneController ppc;
	
	/** The photo. */
	Photo photo;
	
	/** The tile pane. */
	TilePane tilePane;
	
	/** The albums arraylist.  */
	ArrayList<Album> albums;
	
	/** The album obj */
	Album album;
	
	/**
	 * Start initializes the move photo controller.
	 *
	 * @param localStage the previous stage
	 * @param ppc the photo pane controller
	 * @param photo the photo obj
	 * @param tilePane the tile pane where elements populated
	 * @param albums the albums list
	 * @param album the album itself
	 */
	public void start(Stage localStage, photoPaneController ppc, Photo photo, TilePane tilePane,ArrayList<Album> albums, Album album) {
		this.tilePane = tilePane;
		this.localStage = localStage;
		this.ppc = ppc;
		this.photo = photo;
		this.albums = albums;
		this.album = album;
		ArrayList<String> albumNames = new ArrayList<String>();
		for(Album i : albums){
			if(i.getAlbumName().equalsIgnoreCase(album.getAlbumName())){
				continue;
			}
			albumNames.add(i.getAlbumName());
		}
		albumBox.setItems(FXCollections.observableArrayList(albumNames));
		
		
	}
	
	/**
	 * Move to a new  album. When you hit the new album button this method is executed 
	 *
	 * @param e the e
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void newAlbum(ActionEvent e) throws IOException{	   
		   FXMLLoader load = new FXMLLoader();
		   load.setLocation(getClass().getResource("/view/create.fxml"));
		   AnchorPane root = (AnchorPane)load.load();
		   createAlbumController cac= load.getController();
		   cac.start(localStage,ppc.apc,albums, this,null,null);
		   Scene add = new Scene(root);
		   localStage.setScene(add);
		   localStage.setTitle("Create Album");
		   localStage.setResizable(false);
		   root.requestFocus();
	}
	
	/**
	 * When the Ok button is hit this method is executed. 
	 *
	 * @param e the e
	 */
	public void okMove(ActionEvent e) {
		if(albumBox.getSelectionModel().getSelectedItem() == null){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Move Photo");
			message.setHeaderText("Cannot Move Photo");
			if(albums.size()==1){
				message.setContentText("There are no other albums to move to. You can create a new album by clicking the 'New Album' button.");
			}
			else{
				message.setContentText("You did not select an album to move to.");	
			}
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		int index = 0;
		for(int i = 0; i < albums.size(); i++){
			if(albums.get(i).getAlbumName().equalsIgnoreCase(albumBox.getSelectionModel().getSelectedItem())){
				index = i;
				break;
			}
		}
		Boolean canAdd = true;
		for(int j = 0; j < albums.get(index).getPhotos().size(); j++){
			if(albums.get(index).getPhotos().get(j).getPath().equals(photo.getPath())){
				canAdd = false;
				break;
			}
		}
		if(canAdd){
			albums.get(index).getPhotos().add(0,photo);
			album.getPhotos().remove(photo);
			tilePane.getChildren().remove(photo.getLabel());
			album.updateDates();
			albums.get(index).updateDates();
			ppc.currentUser.updateAlbum(ppc.apc, album);
			localStage.close();
		}
		else{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Move Photo");
			message.setHeaderText("Cannot Move Photo");
			message.setContentText("The selected album already contains this photo.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		
	}	

	/**
	 * When the cancel button is hit this method is executed. 
	 *
	 * @param e the e
	 */
	public void cancelMove(ActionEvent e){
		localStage.close();
	}
	
}
