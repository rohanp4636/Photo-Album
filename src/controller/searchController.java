package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.Tag;
import model.Photo;



public class searchController {
	
	@FXML TextField albumName;
	@FXML TreeView<Tag> treeView;
	@FXML DatePicker from;
	@FXML DatePicker to;
	@FXML Button searchButton;
	@FXML Button cancelButton;
	
	private Stage localStage;
	private albumPaneController apc;
	ArrayList<Album> album;
	ArrayList<Photo> photos;
	private Date createdDate;
	private SimpleDateFormat sdf;
	 Date fromDate;
	 Date toDate;
		
	public void start(Stage localStage, albumPaneController apc, ArrayList<Album> a) {
		this.localStage=localStage;
		this.apc=apc;
		this.album=a;
	}
	
	
	public void createOK(ActionEvent e) throws ParseException {
		//check if valid date
		//add based on date
		/*String fromD;
		String toD;
		fromD=from.getValue().toString();
		toD=to.getValue().toString();
		fromDate = sdf.parse(fromD);
		toDate = sdf.parse(toD);
		
*/		
		//"MM/dd/yyyy"
		Calendar fd = Calendar.getInstance();
		fd.set(from.getValue().getMonthValue(),from.getValue().getDayOfMonth(),from.getValue().getYear());
		fd.set(Calendar.MILLISECOND, 0);
		
		Calendar td = Calendar.getInstance();
		td.set(to.getValue().getMonthValue(),to.getValue().getDayOfMonth(),to.getValue().getYear());
		td.set(Calendar.MILLISECOND, 0);
		
		
		
		for(int x=0; x<album.size();x++)
		{
			for(int y=0; y<album.get(x).getPhotos().size(); y++)
			{
								
			}
			
		}
		
		//check if tags are selected
		//checjk
		localStage.close();
	}
		


	public void createCancel(ActionEvent e){
		
	}
	
		
}
