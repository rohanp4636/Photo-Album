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
		if(adminPaneController.users.isEmpty()){
			image = new Image("/view/user.png");
		}
		else if(adminPaneController.users.size() % 2 == 0){
			image = new Image("/view/user.png");
		}
		else{
			image = new Image("/view/user2.png");
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
		label.getStylesheets().add(getClass().getResource("/view/font.css").toExternalForm());
		label.getStylesheets().add(getClass().getResource("/view/emptyBorder.css").toExternalForm());
		label.setPadding(new Insets(5,16,5,16));
		label.setOnMouseClicked(e -> selectImage());
		return true;
	}
	
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
