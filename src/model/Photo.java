package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Photo {

	String path;
	
	String caption;
	
	Calendar dateTime;
	
	ArrayList<Tag> tags;
	
	public Photo(String path){    //date and time stuff
		this.path = path;
		tags = new ArrayList<Tag>();
		//set time and date in dateTime
	}
	
}
