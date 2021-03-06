package model;

import java.io.Serializable;
import java.util.ArrayList;

import controller.adminPaneController;
import controller.albumPaneController;

import javafx.geometry.Insets;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.text.TextAlignment;

/**
 * User Class - defines attributes of the User. 
 */
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user name. */
	public String userName;

	/** The albums arraylist */
	ArrayList<Album> albums;
		
	/** The path */
	String path = "";
	
	/** The create user */
	transient Boolean create = false;
	
	/** The label. */
	transient Label label;
	
	/** The image. */
	transient Image image;
	
	/** The image view. */
	transient ImageView imageView;
	

	
	/**
	 * Instantiates a new user.
	 *
	 * @param name username
	 */
	public User(String name){
		albums = new ArrayList<Album>();
		this.userName = name;
		label = new Label();
		label.setId(name);
		create = true;
		this.setUserImage();
	}
	
	/**
	 * Gets the albums.
	 *
	 * @return the albums
	 */
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public Label getLabel(){
		return label;
	}
	
	/**
	 * Update album.
	 *
	 * @param apc the album pane controller
	 * @param album the album
	 */
	public void updateAlbum(albumPaneController apc, Album album){
		for(Album i: albums){
			i.label.setText(i.toString());
			i.label.setId(i.albumName);
		} 
		
	}
	
	
	/**
	 * Sets the user image.
	 *
	 * @return true, if successful
	 */
	public boolean setUserImage(){
		if(path.isEmpty()){
			path = "/view/user2.png";
			image = new Image("/view/user2.png");
	
		}
		else{
			image = new Image(path);
		}
		if(label == null){
			label = new Label();
			label.setId(this.userName);
			create = true;
		}
		imageView = new ImageView(image);
		imageView.setFitWidth(280);
		imageView.setFitHeight(280);
		imageView.setPreserveRatio(true);
		imageView.setId(this.userName);
		imageView.setPickOnBounds(true);		
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		label.setStyle("-fx-text-fill: white");
		if(create){
			label.getStylesheets().add(getClass().getResource("/view/font.css").toExternalForm());
			label.getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
		}
		label.setPadding(new Insets(5,16,5,16));
		label.setOnMouseClicked(e -> selectImage());
		create = false;
		return true;
	}
	
	/**
	 * Select image - method of listeners for selection of users. 
	 */
	public void selectImage(){
		for(int i = 0; i < adminPaneController.users.size(); i++){
			if(adminPaneController.users.get(i).label.getStylesheets().size() == 2){
				adminPaneController.users.get(i).label.getStylesheets().remove(1);
				if(adminPaneController.users.get(i).userName.equalsIgnoreCase(this.userName)){
					adminPaneController.users.get(i).label.getStylesheets().add(getClass().getResource("/view/border.css").toExternalForm());
				}
				else{
					adminPaneController.users.get(i).label.getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
				}
			}
		}
		adminPaneController.selected = userName; 
		adminPaneController.isSelected= true; 

	}

	
}
