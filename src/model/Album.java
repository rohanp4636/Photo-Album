package model;

import java.util.ArrayList;
import java.util.Calendar;

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
	
	public boolean setAlbumCover(){
		image = new Image("/view/albumIcon.png");
		imageView = new ImageView(image);
		imageView.setFitWidth(400);
		imageView.setFitHeight(250);
		imageView.setId(this.toString());
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		
		return true;
	}
	
	public String toString(){
		String s = albumName + "  -  " + numPhotos + " photos\n";
		if(oldest == null && newest == null){
			return s;
		}
		String dates = oldest.MONTH +"/"+oldest.DAY_OF_MONTH + "/" + oldest.DAY_OF_YEAR + " - " +newest.MONTH +"/"+newest.DAY_OF_MONTH + "/" + newest.DAY_OF_YEAR;
		return s + dates;
		
	}
	



}
