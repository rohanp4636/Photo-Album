package model;

import java.util.ArrayList;
import java.util.Calendar;

import controller.adminPaneController;
import controller.photoPaneController;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class Album {
	
	String albumName;
	int numPhotos;
	Calendar oldest = null;   // when new photo is added compare time as well as date
	Calendar newest = null;
	ArrayList<Photo> photos;
	Label label;
	Image image;
	ImageView imageView;
	
	
	public Album(String name){
		this.albumName = name;
		numPhotos = 0;
		photos = new ArrayList<Photo>();
		label = new Label();
	}
	public Label getLabel(){
		return label;
	}
	public boolean setAlbumCover(){
		image = new Image("/view/albumIcon.png");
		imageView = new ImageView(image);
		imageView.setFitWidth(400);
		imageView.setFitHeight(250);
		imageView.setPreserveRatio(true);
		imageView.setId(this.toString());
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		label.setId(this.albumName);
		label.setStyle("-fx-text-fill: white");
		label.getStylesheets().add(getClass().getResource("/view/font.css").toExternalForm());
		label.getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
		label.setPadding(new Insets(5,16,5,16));
		label.setOnMouseClicked(e -> selectImage());
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
		albumPaneController.selected = albumName; 
		albumPaneController.isSelected= true; 

	}
	
	//public setTimes(){
		//for(int i = 0; i < photos.size(); i++){
			
	//	}
//	}
	
	public String toString(){
		String s = albumName + "  -  " + numPhotos + " photos\n";
		if(oldest == null && newest == null){
			return s;
		}
		String dates = oldest.MONTH +"/"+oldest.DAY_OF_MONTH + "/" + oldest.DAY_OF_YEAR + " - " +newest.MONTH +"/"+newest.DAY_OF_MONTH + "/" + newest.DAY_OF_YEAR;
		return s + dates;
		
	}
	



}
