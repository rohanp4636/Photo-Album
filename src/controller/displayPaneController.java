package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Photo;


/**
 * Display Pane Controller controls the Display pane FXML file and implements all the functionalities dealing with display.
 * @author Daivik Sheth | Rohan Patel
 */
public class displayPaneController {
	
	/** The image box. */
	@FXML HBox imageBox;
	
	/** The caption. */
	@FXML TextArea caption;
	
	/** The date of capture. */
	@FXML TextField dateCapture;
	
	/** The time of capture. */
	@FXML TextField timeCapture;
	
	/** The tree view. */
	@FXML TreeView<String>  treeView;
	
	/** The image. */
	@FXML ImageView image;
	
	/** The primary stage. */
	Stage primaryStage;
	
	/** Arraylist of photos */
	public ArrayList<Photo> photos;
	
	/** Previous Scene */
	Scene prev;
	
	/** Photopane controller */
	photoPaneController ppc;
	
	/** Index of the photo */
	int index;
	
	/**
	 * Start initializes the display controller.
	 *
	 * @param primaryStage the previous scene
	 * @param photos arraylist of photos
	 * @param prev previous scene
	 * @param ppc photo pane controller
	 * @param index index of the photo
	 */
	public void start(Stage primaryStage, ArrayList<Photo> photos, Scene prev, photoPaneController ppc, int index) {
		this.primaryStage = primaryStage;
		this.photos = photos;
		this.prev = prev;
		this.ppc = ppc;
		this.index = index;
		setPhoto();		
		primaryStage.setResizable(true);
		
	}
	
	/**
	 * Takes the photo and sets it inside the HBox and updates the properties of the photo such as caption, time, tags, etc/ 
	 */
	public void setPhoto(){
		caption.setText(photos.get(index).getCaption());
		dateCapture.setText(photos.get(index).getDate());
		timeCapture.setText(photos.get(index).getTime());
		Image i = photos.get(index).getImage();
		if(i != null){
			image.setImage(i);	
			image.setPreserveRatio(true);
			image.fitHeightProperty().bind(imageBox.heightProperty());
			image.fitWidthProperty().bind(imageBox.widthProperty());
		
		}
		TreeItem<String> dumb = new TreeItem<String>("dumb");
		dumb.setExpanded(true);
		treeView.setRoot(dumb);
		treeView.setShowRoot(false);
		for(int i1 = 0; i1 < photos.get(index).getTags().size(); i1++){
			TreeItem<String> tagT = new TreeItem<String>(photos.get(index).getTags().get(i1).toString());
			for(int j = 0; j < photos.get(index).getTags().get(i1).getValue().size(); j++){
				TreeItem<String> tagV = new TreeItem<String>(photos.get(index).getTags().get(i1).getValue().get(j));
				tagT.getChildren().add(tagV);
			}
			dumb.getChildren().add(tagT);
		}
	
		
	}

	/**
	 * Goes back to the previous scene. 
	 *
	 * @param e the e
	 */
	public void back(ActionEvent e){
		primaryStage.setScene(prev);
		if(ppc.tilePane.getChildren().size() == 0){
			for(int i = 0; i < photos.size(); i++){
				ppc.tilePane.getChildren().add(0,photos.get(i).getLabel());
			}
		}
		
	}
	
	/**
	 * When the next arrow is hit this method is executed. 
	 *
	 * @param e the e
	 */
	public void nextPic(ActionEvent e){
		if(this.index == (photos.size()-1)){
			this.index = 0;
		}
		else{
			this.index++;
		}
		setPhoto();
		
	}
	
	/**
	 * When the previous arrow is hit this method is executed. 
	 *
	 * @param e the e
	 */
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
