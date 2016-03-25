package model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
		imageView.setFitWidth(300);
		imageView.setFitHeight(300);
		//imageView.setPreserveRatio(true);
		imageView.setId(this.userName);
		imageView.setPickOnBounds(true);	
		imageView.setOnMouseClicked(e -> adminPaneController.selected);
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		label.setStyle("-fx-text-fill: white");
		label.getStylesheets().add(getClass().getResource("/view/font.css").toExternalForm());
		label.setPadding(new Insets(10,10,10,10));
		
		return true;
	}
	
	
	
}
