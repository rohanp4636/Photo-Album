package model;

import java.util.ArrayList;
import java.util.Calendar;

import controller.photoPaneController;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class Photo {

	String path = "";
	
	String caption = "";
	
	Calendar dateTime;
	
	Label label;
	Image image;
	ImageView imageView;
	
	ArrayList<Tag> tags;
	
	public Photo(String path){    //date and time stuff
		this.path = path;
		tags = new ArrayList<Tag>();
		boolean test = setPhotoThumbnail();
		//set time and date in dateTime
	}
	
	public Label getLabel(){
		return label;
	}
	
	public String getCaption(){
		return caption;
	}
	
	public void setCaption(String s){
		this.caption = s;
	}
	
	public boolean setPhotoThumbnail(){
		image = new Image("/view/albumIcon.png");
		imageView = new ImageView(image);
		imageView.setFitWidth(280);
		imageView.setFitHeight(280);
		imageView.setPreserveRatio(true);
		imageView.setId(this.caption);
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		label.setId(this.path);
		label.setStyle("-fx-text-fill: white");
		label.getStylesheets().add(getClass().getResource("/view/font.css").toExternalForm());
		label.getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
		label.setPadding(new Insets(5,16,5,16));
		label.setOnMouseClicked(e -> selectImage());
		return true;
	}
		
	
	public void selectImage(){
		for(int i = 0; i < photoPaneController.photos.size(); i++){
			if(photoPaneController.photos.get(i).label.getStylesheets().size() == 2){
				photoPaneController.photos.get(i).label.getStylesheets().remove(1);
				if(photoPaneController.photos.get(i).path.equalsIgnoreCase(this.path)){
					photoPaneController.photos.get(i).label.getStylesheets().add(getClass().getResource("/view/border.css").toExternalForm());
				}
				else{
					photoPaneController.photos.get(i).label.getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
				}
			}
		}
		photoPaneController.selected = this.path; 
		photoPaneController.isSelected= true; 

	}
	
}
