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
/**
 * Photo Class - defines attributes of the Photo. 
 */
public class Photo implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The path of the photo. */
	String path;   // when reading from dat file. change file path to new path.
	
	/** The caption of the photo. */
	String caption;
	
	/** The date time. */
	Calendar dateTime;
	
	/** The is demo photo. */
	Boolean isDemoPhoto;
	
	/** The create. */
	transient Boolean create = false;
	
	/** The label. */
	transient Label label;
	
	/** The image. */
	transient Image image;
	
	/** The image view. */
	transient ImageView imageView;
	
	/** The arraylist of tags */
	ArrayList<Tag> tags;
	
	/**
	 * Instantiates a new photo.
	 *
	 * @param file the file
	 * @param album the album obj
	 * @param demo boolean
	 * @param demoPath the demo path
	 */
	public Photo(File file, Album album, Boolean demo, String demoPath){    //date and time stuff
		if(!demo){
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

		
		setPhotoThumbnail();
		album.numPhotos++;
		//set time and date in dateTime
	}
	
	/**
	 * Gets the calendar
	 *
	 * @return the cal
	 */
	public Calendar getCal()
	{
		return this.dateTime;
	}
	
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public Label getLabel(){
		return label;
	}
	
	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption(){
		return caption;
	}
	
	/**
	 * Sets the caption.
	 *
	 * @param s the new caption
	 */
	public void setCaption(String s){
		this.caption = s.trim();
	}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath(){
		return path;
	}
	
	/**
	 * Sets the path.
	 *
	 * @param s the new path
	 */
	public void setPath(String s){
		this.path = s;
	}
	
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public ArrayList<Tag> getTags(){
		return tags;
	}
	
	/**
	 * Sets the tags.
	 *
	 * @param t the new tags
	 */
	public void setTags(ArrayList<Tag> t){
		this.tags = t;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate(){
		if(dateTime == null){
			return "";
		}
		String s = new SimpleDateFormat("MM/dd/yyyy").format(dateTime.getTime());
		return s;
	}
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime(){
		if(dateTime == null){
			return "";
		}
		String s = new SimpleDateFormat("hh:mm:ss aa").format(dateTime.getTime());
		return s;
		
	}
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Image getImage(){
		return image;
	}
	
	/**
	 * Sets the photo thumbnail.
	 *
	 * @return true, if successful
	 */
	public boolean setPhotoThumbnail(){
		if(isDemoPhoto){
			File f = new File(path);
			String s = f.getPath();
			 
			image = new Image("file:"+System.getProperty("user.dir")+"\\"+s);   // put relative path here for photos in date folder
		}
		else{
			image = new Image("file:"+this.path);
		}
		imageView = new ImageView(image);
		imageView.setFitWidth(300);
		imageView.setFitHeight(200);
		imageView.setId(this.caption + " ");
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
	
	
	
	/**
	 * Select image.
	 */
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
