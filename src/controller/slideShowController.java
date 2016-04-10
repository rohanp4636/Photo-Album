package controller;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javafx.stage.Stage;
import model.Album;
import model.Photo;

public class slideShowController {
	
	@FXML ToggleButton auto;
	@FXML Button backButton;
	@FXML Button prevButton;
	@FXML Button nextButton;
	@FXML ImageView imageView;
	

	Stage primaryStage;
	ArrayList<Photo> photos;
	Scene prev;
	photoPaneController ppc;
	int userIndex;
	Photo photo;
	
	public void start(Stage ps,ArrayList<Photo> p, Scene prev,photoPaneController ppc,int i,Photo pho) {
		this.primaryStage=ps;
		this.photos=p;
		this.prev=prev;
		this.ppc=ppc;
		this.userIndex=i;
		this.photo=pho;
		setPhoto();
	}
	public void nextPhoto(ActionEvent e){
		if(this.userIndex == (photos.size()-1)){
			this.userIndex = 0;
		}
		else{
			this.userIndex++;
		}
		setPhoto();
		
	}
	
	public void prevPhoto(ActionEvent e){
		if(this.userIndex == 0){
			this.userIndex = photos.size()-1;
		}
		else{
			this.userIndex--;
		}
		setPhoto();
	}
	
	public void setPhoto(){
		
		Image i = photos.get(userIndex).getImage();
		if(i != null){
			imageView.setImage(i);	
			imageView.setPreserveRatio(true);
			FadeTransition fade = new FadeTransition();
			fade.setNode(imageView);
	        fade.setFromValue(0.0);
	        fade.setToValue(1.0);
	        fade.setRate(0.1);
	        fade.play();
		}
	}
	private class slideShowTimer extends TimerTask {
	    public void run() {
	      nextP();
	    }
	 }

	public void nextP()
	{
		if(this.userIndex == (photos.size()-1)){
			this.userIndex = 0;
		}
		else{
			this.userIndex++;
		}
		setPhoto();
	}
	
	public void autoSlideshow(ActionEvent e) throws InterruptedException
	{
		Timer t = new Timer();
		if(auto.isSelected())
		{
			nextButton.setVisible(false);
			prevButton.setVisible(false);
			t.schedule(new slideShowTimer(), 5000);
			
		}
		else
		{
			t.cancel();
			t.purge();
			nextButton.setVisible(true);
			prevButton.setVisible(true);
		}
	}
	public void back(ActionEvent e){
		primaryStage.setScene(prev);
		if(ppc.tilePane.getChildren().size() == 0){
			for(int i = 0; i < photos.size(); i++){
				ppc.tilePane.getChildren().add(0,photos.get(i).getLabel());
			}
		}
		
	}
}
