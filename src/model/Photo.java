package model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import controller.photoPaneController;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class Photo implements Serializable{

	String path;   // when reading from dat file. change file path to new path.
	
	String caption;
	
	Calendar dateTime;
	
	Boolean isDemoPhoto;
	
	transient Boolean create = false;
	transient Label label;
	transient Image image;
	transient ImageView imageView;
	
	ArrayList<Tag> tags;
	
	public Photo(File file, Album album, Boolean demo, String demoPath){    //date and time stuff
		if(demo){
			this.path = file.getAbsolutePath();
		}
		else{
			this.path = demoPath;
		}
		this.caption = "";
		tags = new ArrayList<Tag>();
		label = new Label();
		create = true;
		this.isDemoPhoto = demo;
		this.dateTime = Calendar.getInstance();
		this.dateTime.setTimeInMillis(file.lastModified());
		this.dateTime.set(Calendar.MILLISECOND, 0);

		
		boolean test = setPhotoThumbnail();
		album.numPhotos++;
		//set time and date in dateTime
	}
	
	
	
	public Label getLabel(){
		return label;
	}
	
	public String getCaption(){
		return caption;
	}
	
	public void setCaption(String s){
		this.caption = s.trim();
	}
	
	public String getPath(){
		return path;
	}
	public void setPath(String s){
		this.path = s;
	}
	
	public String getDate(){
		if(dateTime == null){
			return "";
		}
		String s = new SimpleDateFormat("MM/dd/yyyy").format(dateTime.getTime());
		return s;
	}
	public String getTime(){
		if(dateTime == null){
			return "";
		}
		String s = new SimpleDateFormat("hh:mm:ss aa").format(dateTime.getTime());
		return s;
		
	}
	
	public Image getImage(){
		return image;
	}
	
	public boolean setPhotoThumbnail(){
		if(isDemoPhoto){
			image = new Image("");  // put relative path here for photos in date folder
		}
		else{
			image = new Image("file:"+this.path);
		}
		imageView = new ImageView(image);
		imageView.setFitWidth(300);
		imageView.setFitHeight(200);
		imageView.setId(this.caption);
		if(label == null){
			label = new Label();
			create = true;
		}
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		label.setId(this.path);
		label.setStyle("-fx-text-fill: white");
		if(create){
			label.getStylesheets().add(getClass().getResource("/view/font.css").toExternalForm());
			label.getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
		}
		label.setPadding(new Insets(5,6,5,6));
		label.setMaxSize(label.getWidth(), label.getHeight());
		label.setTextOverrun(OverrunStyle.ELLIPSIS);
		label.setOnMouseClicked(e -> selectImage());
		create = false;
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
