package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Photo;
/**
 * Caption Controller controls the caption dialog box, the FXML file and implements all the functionalities dealing with Caption.
 * @author Daivik Sheth | Rohan Patel
 */
public class captionController {
	
	/**caption */
	@FXML TextArea caption;
	
	/**ok button*/
	@FXML Button okButton;
	
	/**cancel button*/
	@FXML Button cancelButton;
	
	/** local stage*/
	private Stage localStage;
	
	/**photo*/
	Photo photo;
	
	/**tile pane */
	TilePane tilePane;
	
	/** recaption */
	Boolean recaption = false;
		
	/**
	 *  Start initializes the caption pane controller.
	 *
	 * @param localStage previous
	 * @param ppc Photo Pane Controller
	 * @param photo Photo object that you are putting a caption on
	 * @param recaption if its recaption or not. 
	 * @param tilePane the tile pane where its populated. 
	 */
	public void start(Stage localStage, photoPaneController ppc, Photo photo, boolean recaption, TilePane tilePane) {
		this.tilePane = tilePane;
		this.localStage = localStage;
		this.photo = photo;
		caption.setWrapText(true);
		this.recaption = recaption;
		if(recaption){
			caption.setText(photo.getCaption());
		}
		else{
			caption.setPromptText("Enter a caption here.");
		}
		
		
	}
	
	
	/**
	 * Ok Caption - When the Ok button is hit then this method is executed. 
	 *
	 * @param e the e
	 */
	public void okCaption(ActionEvent e) {
		String cap = caption.getText().trim();
		if(cap.isEmpty() && !recaption){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Caption");
			message.setHeaderText("Cannot Caption Photo");
			message.setContentText("Caption was not entered.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			
		}
		else if(caption.getText().length() > 140){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Caption");
			message.setHeaderText("Cannot Caption Photo");
			message.setContentText("Caption must be 140 characters or less.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
		}
		else{ // put in sorted order if you  want.
			photo.setCaption(caption.getText());
			int index = tilePane.getChildren().indexOf(photo.getLabel());
			((Label)tilePane.getChildren().get(index)).setText(photo.getCaption()+" ");
		}
		localStage.close();
		
	}	

	/**
	 * Cancel Caption - When the Cancel button is hit then this method is executed. 
	 *
	 * @param e the e
	 */
	public void cancelCaption(ActionEvent e){
		localStage.close();
	}
	

		
}

