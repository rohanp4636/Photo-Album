package model;

import java.util.ArrayList;

import controller.adminPaneController;
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
	
	Image image2;
	ImageView imageView2;
	
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
		imageView.setPreserveRatio(true);
		imageView.setId(this.userName);
		imageView.setPickOnBounds(true);	
			
		label.setText(imageView.getId());
		label.setGraphic(imageView);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setContentDisplay(ContentDisplay.TOP);
		label.setStyle("-fx-text-fill: white");
		label.getStylesheets().add(getClass().getResource("/view/font.css").toExternalForm());
		label.setPadding(new Insets(5,5,5,5));
		label.setOnMouseClicked(e -> selectImage());
		return true;
	}
	
	public void selectImage(){
		for(int i = 0; i < adminPaneController.users.size(); i++){
			if(adminPaneController.users.get(i).label.getStylesheets().size() == 2){
				adminPaneController.users.get(i).label.getStylesheets().remove(1);
			}
		}
		adminPaneController.selected = userName; 
		adminPaneController.isSelected= true; 
		label.getStylesheets().add(getClass().getResource("/view/border.css").toExternalForm());
	}

	
}
