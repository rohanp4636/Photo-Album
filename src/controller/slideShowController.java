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
import javafx.scene.layout.HBox;

import javafx.stage.Stage;
import model.Photo;
/**
 * Slideshow Controller controls the sideshow pane FXML file and implements all the functionalities dealing with slideshow. 
 * @author Daivik Sheth | Rohan Patel
 *
 * */
public class slideShowController {
	
	/** The image box. */
	@FXML HBox imageBox;
	
	/** The auto slideshow toggle. */
	@FXML ToggleButton auto;
	
	/** The back button. */
	@FXML Button backButton;
	
	/** The prev button. */
	@FXML Button prevButton;
	
	/** The next button. */
	@FXML Button nextButton;
	
	/** The image view where photo is displayed */
	@FXML ImageView imageView;
	

	/** The primary stage prev stage */
	Stage primaryStage;
	
	/** array list of photos*/
	ArrayList<Photo> photos;
	
	/** The prev. */
	Scene prev;
	
	/** photo pane controller. */
	photoPaneController ppc;
	
	/** The user index. */
	int userIndex;
	
	/** The photo. */
	Photo photo;
	
	/** Timer */
	Timer t;
	
	/**
	 * Start initializes the controller.
	 *
	 * @param ps the previous stage
	 * @param p the photos array
	 * @param prev the prev scene
	 * @param ppc the photo pane controller
	 * @param i the index
	 * @param pho the photo obj
	 */
	public void start(Stage ps,ArrayList<Photo> p, Scene prev,photoPaneController ppc,int i,Photo pho) {
		this.primaryStage=ps;
		this.photos=p;
		this.prev=prev;
		this.ppc=ppc;
		this.userIndex=i;
		this.photo=pho;
		setPhoto();
		t=new Timer();
	}
	
	/**
	 * Next photo.
	 *
	 * @param e the e
	 */
	public void nextPhoto(ActionEvent e){
		if(this.userIndex == (photos.size()-1)){
			this.userIndex = 0;
		}
		else{
			this.userIndex++;
		}
		setPhoto();
	}
	
	/**
	 * Prev photo.
	 *
	 * @param e the e
	 */
	public void prevPhoto(ActionEvent e){
		if(this.userIndex == 0){
			this.userIndex = photos.size()-1;
		}
		else{
			this.userIndex--;
		}
		setPhoto();
	}
	
	/**
	 * Sets the photo in the hbox and use an animation while setting photo. 
	 */
	public void setPhoto(){
		
		Image i = photos.get(userIndex).getImage();
		if(i != null){
			imageView.setImage(i);	
			imageView.setPreserveRatio(true);
			imageView.fitHeightProperty().bind(imageBox.heightProperty());
			imageView.fitWidthProperty().bind(imageBox.widthProperty());
			FadeTransition fade = new FadeTransition();
			fade.setNode(imageView);
	        fade.setFromValue(0.0);
	        fade.setToValue(1.0);
	        fade.setRate(0.2);
	        fade.play();
		}
	}
	
	/**
	 * Slideshow Timer Task
	 */
	private class slideShowTimer extends TimerTask {
	    
    	public void run() {
	      nextP();
	    }
	 }

	/**
	 * Next photo, remove the buttons since its auto. 
	 */
	public void nextP()
	{
		if(this.userIndex == (photos.size()-1)){
			this.userIndex = 0;
		}
		else{
			this.userIndex++;
		}
		setPhoto();
		nextButton.setVisible(false);
		prevButton.setVisible(false);
		t=new Timer();
		t.schedule(new slideShowTimer(), 5000);
	}
	
	/**
	 * When the auto slideshow toggle is ued this method is executed. Auto slideshow with FADE transition. 
	 *
	 * @param e the e
	 * @throws InterruptedException the interrupted exception
	 */
	public void autoSlideshow(ActionEvent e) throws InterruptedException
	{
		
		if(auto.isSelected())
		{
			
			nextButton.setVisible(false);
			prevButton.setVisible(false);
			t=new Timer();
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
	
	/**
	 * Back to previous scene. 
	 *
	 * @param e the e
	 */
	public void back(ActionEvent e){
		t.cancel();
		t.purge();
		nextButton.setVisible(true);
		prevButton.setVisible(true);
		primaryStage.setScene(prev);
		if(ppc.tilePane.getChildren().size() == 0){
			for(int i = 0; i < photos.size(); i++){
				ppc.tilePane.getChildren().add(0,photos.get(i).getLabel());
			}
		}
		
	}
}
