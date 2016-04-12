package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import controller.albumPaneController;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
/**
 * The album class - defines attributes of the album. 
 */
public class Album implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The album name. */
	String albumName;
	
	/** The number photos. */
	public int numPhotos;
	
	/** The oldest photo. */
	Calendar oldest = null;   // when new photo is added compare time as well as date
	
	/** The newest photo */
	Calendar newest = null;
	
	/** The photos arraylist */
	ArrayList<Photo> photos;
	
	/** The path of the file */
	String path = "";
	
	/** The create album */
	transient Boolean create = false;
	
	/** Label*/
	transient Label label;
	
	/** The image of the album */
	transient Image image;
	
	/** The image view where image lies */
	transient ImageView imageView;
	
	
	/**
	 * Instantiates a new album.
	 *
	 * @param name album name
	 */
	public Album(String name){
		this.albumName = name;
		photos = new ArrayList<Photo>();
		numPhotos = photos.size();
		label = new Label();
		create = true;
		setAlbumCover();
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
	 * Gets the album name.
	 *
	 * @return the album name
	 */
	public String getAlbumName()
	{
		return albumName;
	}
	
	/**
	 * Sets the album.
	 *
	 * @param name the new album
	 */
	public void setAlbum(String name)
	{
		albumName = name;
	}
	
	/**
	 * Update dates.
	 */
	public void updateDates(){
		if(photos.isEmpty()){
			oldest = null;
			newest = null;
			return;
		}
		oldest = photos.get(0).dateTime;
		newest = photos.get(0).dateTime;
		
		for(int i = 0; i < photos.size(); i++){
			if(photos.get(i).dateTime.before(oldest)){
				oldest = photos.get(i).dateTime;
			}
			if(photos.get(i).dateTime.after(newest)){
				newest = photos.get(i).dateTime;
			}
		}
	}
	
	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	
	/**
	 * Sets the album cover.
	 *
	 * @return true, if successful
	 */
	public boolean setAlbumCover(){
		if(path.isEmpty()){
			path = "/view/albumIcon.png";
			image = new Image("/view/albumIcon.png");
		}
		else{
			image = new Image(path);
		}
		if(label == null){
			label = new Label();
			create = true;
		}
		imageView = new ImageView(image);
		imageView.setFitWidth(280);
		imageView.setFitHeight(280);
		imageView.setPreserveRatio(true);
		imageView.setId(this.toString());
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		label.setId(this.albumName);
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
	 * Select image.
	 */
	public void selectImage(){
		for(int i = 0; i < albumPaneController.albums.size(); i++){
			if(albumPaneController.albums.get(i).label.getStylesheets().size() == 2){
				albumPaneController.albums.get(i).label.getStylesheets().remove(1);
				if(albumPaneController.albums.get(i).albumName.equalsIgnoreCase(this.albumName)){
					albumPaneController.albums.get(i).label.getStylesheets().add(getClass().getResource("/view/border.css").toExternalForm());
				}
				else{
					albumPaneController.albums.get(i).label.getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
				}
			}
		}
		albumPaneController.selected = this.albumName; 
		albumPaneController.isSelected= true; 

	}
	
	//public setTimes(){
		//for(int i = 0; i < photos.size(); i++){
			
	//	}
//	}
	
	/**
	 * To string - Album. Print its attributes. 
	 */
	public String toString(){
		String s = albumName + "  -  " + photos.size() + " photos\n";
		if(oldest == null && newest == null){
			return s + "\n";
		}
		String dates = new SimpleDateFormat("MM/dd/yyyy").format(oldest.getTime()) + " - " + new SimpleDateFormat("MM/dd/yyyy").format(newest.getTime());
		return s + dates;
		
	}
	



}
