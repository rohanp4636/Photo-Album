package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
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
		
		//Check if incomplete search query.
		if((from.getValue()!=null && to.getValue()==null) || (from.getValue()==null && to.getValue()!=null))
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Search Album");
			message.setHeaderText("Cannot Search Album");
			message.setContentText("Please enter both From and To dates.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
		else if(from.getValue()==null && to.getValue()==null)
		{
			//ONLY SEARCH FOR TAGS
		}
		//CHECK FOR IF ONLY DATES
		
		//IT IS BOTH DATES AND TAGS. 
		else 
		{
			Calendar fd = Calendar.getInstance();
			fd.set(from.getValue().getYear(), from.getValue().getMonthValue()-1, from.getValue().getDayOfMonth());
			fd.set(Calendar.MILLISECOND, 0);
			
			Calendar td = Calendar.getInstance();
			td.set(to.getValue().getYear(), to.getValue().getMonthValue()-1, to.getValue().getDayOfMonth());
			td.set(Calendar.MILLISECOND, 0);
			
			ArrayList<Photo> temp = new ArrayList<Photo>();
			Album currAlbum;
			Photo currPhoto;
			
			//incomplete dates
			
			
			for(int x=0; x<album.size();x++)
			{
				currAlbum = album.get(x);
				for(int y=0; y<album.get(x).getPhotos().size(); y++)
				{
					currPhoto= currAlbum.getPhotos().get(y);
					if(currPhoto.getCal().before(td.getTime()) && currPhoto.getCal().after(fd))
					{
						if(!temp.contains(currPhoto.getPath()))
						{
							temp.add(currPhoto);
						}
						
					}
				}
				
			}
			/*
			for(int x=0;x<temp.size();x++)
			{
				//TAGS
			}*/
			
			/*try{
				
				FXMLLoader load = new FXMLLoader();
				load.setLocation(getClass().getResource("/view/photoPane.fxml"));
				AnchorPane root = (AnchorPane)load.load();
				photoPaneController ppc = load.getController();
				ppc.start(primaryStage, user, currentUser, index, prev, apc, album, search);
				ppc.start(localStage,users, currentUser, userIndex, primaryStage.getScene(),this, albums.get(getSelectedAlbum()), false);
				deselect();
				Scene scene = new Scene(root);
				double w = primaryStage.getWidth();
				double h = primaryStage.getHeight();
				primaryStage.setScene(scene);
				primaryStage.setWidth(w);
				primaryStage.setHeight(h);
				
				root.requestFocus();
				
			}catch(Exception ee){
				ee.printStackTrace();
			}*/
			
		}
		//check if tags are selected
		//checjk
		localStage.close();
	}
		


	public void createCancel(ActionEvent e){
		localStage.close();
	}
	
		
}
