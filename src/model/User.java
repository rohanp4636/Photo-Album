package model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class User {

	public String userName;

	ArrayList<Album> albums;
	
	
	Label label;
	Image image;
	ImageView imageView;
	
	public User(String name){
		albums = new ArrayList<Album>();
		this.userName = name;
		label = new Label();
		label.setId(name);
		Boolean test = this.setUserImage();
	}
	
	public Label getLabel(){
		return label;
	}
	
	public void updateAlbum(){
		for(Album i: albums){
			i.label.setId(i.toString());
		}
	}
	
	
	public boolean setUserImage(){
		image = new Image("/view/user.png");
		imageView = new ImageView(image);
		imageView.setFitWidth(400);
		imageView.setFitHeight(250);
		imageView.setId(this.userName);
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		
		return true;
	}
	
	
	
}
