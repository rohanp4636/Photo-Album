package model;

import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Photo {

	String path;
	
	String caption;
	
	Calendar dateTime;
	
	Label label;
	Image image;
	ImageView imageView;
	
	ArrayList<Tag> tags;
	
	public Photo(String path){    //date and time stuff
		this.path = path;
		tags = new ArrayList<Tag>();
		//set time and date in dateTime
	}
	
	public Label getLabel(){
		return label;
	}
	
}
