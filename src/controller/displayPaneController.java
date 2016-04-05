package controller;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class displayPaneController {
	
	@FXML TextArea caption;
	@FXML TextField dateCapture;
	@FXML TextField timeCapture;
	@FXML TreeTableColumn<String,String> tag;
	@FXML ImageView image;
	Stage primaryStage;
	public ArrayList<Photo> photos;
	Scene prev;
	photoPaneController ppc;
	int index;
	
	public void start(Stage primaryStage, ArrayList<Photo> photos, Scene prev, photoPaneController ppc, int index) {
		this.primaryStage = primaryStage;
		this.photos = photos;
		this.prev = prev;
		this.ppc = ppc;
		this.index = index;
		setPhoto();
		primaryStage.setResizable(true);
		
	}
	
	public void setPhoto(){
		caption.setText(photos.get(index).getCaption());
		dateCapture.setText(photos.get(index).getDate());
		timeCapture.setText(photos.get(index).getTime());
		Image i = photos.get(i).getImage;
		if(i != null){
			image.setImage(photos.get(index).getImage());	
		}
		image.setPreserveRatio(true);
	}

	public void back(ActionEvent e){
		primaryStage.setScene(prev);
		if(ppc.tilePane.getChildren().size() == 0){
			for(int i = 0; i < photos.size(); i++){
				ppc.tilePane.getChildren().add(0,photos.get(i).getLabel());
			}
		}
		
	}
	
	public void nextPic(ActionEvent e){
		if(this.index == (photos.size()-1)){
			this.index = 0;
		}
		else{
			this.index++;
		}
		setPhoto();
		
	}
	
	public void prevPic(ActionEvent e){
		if(this.index == 0){
			this.index = photos.size()-1;
		}
		else{
			this.index--;
		}
		setPhoto();
	}
	
}
