package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Tag;
import model.Photo;



public class searchController {
	
	@FXML DatePicker from;
	@FXML DatePicker to;
	@FXML ComboBox<Tag> tagType;
	@FXML ComboBox<String> tagValue;
	@FXML TextArea tagArea;


	
	private Stage localStage;
	private albumPaneController apc;
	ArrayList<Album> album;
	ArrayList<Photo> photos;
	Date fromDate;
	Date toDate;
	ArrayList<Tag> searchTag;
		
	public void start(Stage localStage, albumPaneController apc, ArrayList<Album> a) {
		this.localStage=localStage;
		this.apc=apc;
		this.album=a;
		ArrayList<Tag> treeTags = new ArrayList<Tag>();
		searchTag = new ArrayList<Tag>();
		for(int x = 0; x< a.size(); x++)
		{
			ArrayList<Photo> tempPhotos = a.get(x).getPhotos();
			for(int y = 0; y < tempPhotos.size();y++)
			{
				ArrayList<Tag> temp = tempPhotos.get(y).getTags(); 
				for(int i = 0; i < temp.size(); i++){
					if(!treeTags.contains(temp.get(i)))
					{
						treeTags.add(temp.get(i));					
					}
					else
					{
						ArrayList<String> val = temp.get(i).getValue();
						for(int z=0; z<val.size();z++)
						{
							int index=treeTags.indexOf(temp.get(i));
							Boolean found = false;
							for(int k = 0; k < treeTags.get(index).getValue().size(); k++){
								if(!treeTags.get(index).getValue().get(k).equalsIgnoreCase(val.get(z)))
								{
									found = true;
									break;
								}
							}
							if(!found){
								treeTags.get(index).getValue().add(val.get(z));
							}
							
							
						}
					}
				}
			}
		}
		tagType.setItems(FXCollections.observableArrayList(treeTags));
		tagType.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->selectVal());
		tagValue.setVisible(false);
	}
	public void selectVal()
	{
		if(tagType.getSelectionModel().getSelectedItem()==null)
		{
			return;
		}
		else
		{
			tagValue.setVisible(true);
			tagValue.setItems(FXCollections.observableArrayList(tagType.getSelectionModel().getSelectedItem().getValue()));
		}
	}
	public void addTag(ActionEvent e){
		
		if(tagType.getSelectionModel().getSelectedItem()==null)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Search");
			message.setHeaderText("Cannot Add Tag");
			message.setContentText("Please select a Tag Type");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			tagType.getSelectionModel().clearSelection();
			tagValue.getSelectionModel().clearSelection();
			
			return;
		}
		
		if(tagType.getSelectionModel().getSelectedItem()==null || tagValue.getSelectionModel().getSelectedItem()==null)
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Search");
			message.setHeaderText("Cannot Add Tag");
			message.setContentText("Please select a Tag Type and Tag Value");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			tagType.getSelectionModel().clearSelection();
			tagValue.getSelectionModel().clearSelection();
			return;
		}

		Tag temp= tagType.getSelectionModel().getSelectedItem();
		String s=tagValue.getSelectionModel().getSelectedItem();
		Boolean add = true;;
		int index = -1;
		for(int j = 0; j < searchTag.size(); j++){
			if(searchTag.get(j).getType().equalsIgnoreCase(temp.getType())){
				add = false;
				index = j;
				break;
			}
		}
		if(!add)
		{
			
			Boolean found = false;
			for(int i = 0; i < searchTag.get(index).getValue().size(); i ++){
				if(searchTag.get(index).getValue().get(i).equalsIgnoreCase(s))
				{
					found = true;
					break;
				}
			}
			if(!found){
				searchTag.get(index).addTag(s);
				String out = tagArea.getText();
				out = out+temp.getType()+" : "+ s+"\n";
				tagArea.setText(out);
			}
			
		}
		else
		{
			Tag temp2= new Tag(temp.getType());
			temp2.addTag(s);
			searchTag.add(temp2);	
			String out = tagArea.getText();
			out = out+temp.getType()+" : "+ s+"\n";
			tagArea.setText(out);
		}
		
		tagType.getSelectionModel().clearSelection();
		tagValue.getSelectionModel().clearSelection();
	}

	public void searchOK(ActionEvent e) throws ParseException {
		//check if valid date
		//add based on date
		
		//Check if incomplete search query.
		if((from.getValue()!=null && to.getValue()==null) || (from.getValue()==null && to.getValue()!=null))
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Search");
			message.setHeaderText("Cannot Search Album");
			message.setContentText("Please enter both From and To dates.");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
		//IF ONLY TAGS
		if(!searchTag.isEmpty())
		{
			ArrayList<Photo> tag = new ArrayList<Photo>();
			Album currAlbum;
			Photo currPhoto;
			
			for(int x=0; x<album.size();x++)
			{
				currAlbum = album.get(x);
				for(int y=0; y<album.get(x).getPhotos().size(); y++)
				{
					currPhoto= currAlbum.getPhotos().get(y);
					if()
					{
						if(!temp.contains(currPhoto))
						{
							temp.add(currPhoto);
						}
						
					}
				}
			}
		}
		//CHECK FOR IF ONLY DATES
		if(searchTag.isEmpty())
		{
			Calendar fd = Calendar.getInstance();
			fd.set(from.getValue().getYear(), from.getValue().getMonthValue()-1, from.getValue().getDayOfMonth());
			fd.set(Calendar.MILLISECOND, 0);
			
			Calendar td = Calendar.getInstance();
			td.set(to.getValue().getYear(), to.getValue().getMonthValue()-1, to.getValue().getDayOfMonth());
			td.set(Calendar.MILLISECOND, 0);
			
		
			Album currAlbum;
			Photo currPhoto;
			
			for(int x=0; x<album.size();x++)
			{
				currAlbum = album.get(x);
				for(int y=0; y<album.get(x).getPhotos().size(); y++)
				{
					currPhoto= currAlbum.getPhotos().get(y);
					if((currPhoto.getCal().before(td.getTime()) && currPhoto.getCal().after(fd)) ||())
					{
						if(!temp.contains(currPhoto))
						{
							temp.add(currPhoto);
						}
						
					}
				}
			}
		}
		
		
		localStage.close();
	}
		


	public void searchCancel(ActionEvent e){
		localStage.close();
	}
	
		
}
