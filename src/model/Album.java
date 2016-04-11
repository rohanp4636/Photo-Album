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

public class Album implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String albumName;
	public int numPhotos;
	Calendar oldest = null;   // when new photo is added compare time as well as date
	Calendar newest = null;
	ArrayList<Photo> photos;
	String path = "";
	
	transient Boolean create = false;
	transient Label label;
	transient Image image;
	transient ImageView imageView;
	
	
	public Album(String name){
		this.albumName = name;
		photos = new ArrayList<Photo>();
		numPhotos = photos.size();
		label = new Label();
		create = true;
		setAlbumCover();
	}
	public Label getLabel(){
		return label;
	}
	public String getAlbumName()
	{
		return albumName;
	}
	public void setAlbum(String name)
	{
		albumName = name;
	}
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
	
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
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
	
	public String toString(){
		String s = albumName + "  -  " + photos.size() + " photos\n";
		if(oldest == null && newest == null){
			return s + "\n";
		}
		String dates = new SimpleDateFormat("MM/dd/yyyy").format(oldest.getTime()) + " - " + new SimpleDateFormat("MM/dd/yyyy").format(newest.getTime());
		return s + dates;
		
	}
	



}
