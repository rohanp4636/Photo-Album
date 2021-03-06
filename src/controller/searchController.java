package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Tag;
import model.Photo;

/**
 * Search Controller controls the Search Dialog FXML file and implements all the functionalities dealing with Search. 
 * @author Daivik Sheth | Rohan Patel
 *
 * */
public class searchController {
	
	/** The from date */
	@FXML DatePicker from;
	
	/** The to date */
	@FXML DatePicker to;
	
	/** The tag type. */
	@FXML ComboBox<Tag> tagType;
	
	/** The tag value. */
	@FXML ComboBox<String> tagValue;
	
	/** The tag area where all the tags appear */
	@FXML TextArea tagArea;


	
	/** Prev Stage */
	private Stage localStage;
	
	/** The album pane controller. */
	private albumPaneController apc;
	
	/** The album arraylist*/
	ArrayList<Album> album;
	
	/** The photos arraylit */
	ArrayList<Photo> photos;
	
	/** The from date. */
	Date fromDate;
	
	/** The to date. */
	Date toDate;
	
	/** The arraylist with all the tags */
	ArrayList<Tag> searchTag;
		
	/**
	 * Start initializes the search controller.
	 *
	 * @param localStage the previous stage
	 * @param apc the album pane controller
	 * @param a the arraylist of albums
	 */
	public void start(Stage localStage, albumPaneController apc, ArrayList<Album> a) {
		this.localStage=localStage;
		this.apc=apc;
		this.album=a;
		ArrayList<Tag> treeTags = new ArrayList<Tag>();
		searchTag = new ArrayList<Tag>();
		
		for(int albumIndex = 0; albumIndex < this.album.size(); albumIndex++){
			ArrayList<Photo> tempPhotos = this.album.get(albumIndex).getPhotos();
			for(int photoIndex = 0; photoIndex < tempPhotos.size();photoIndex++)
			{
				ArrayList<Tag> tags = tempPhotos.get(photoIndex).getTags(); 
				for(int tagIndex = 0; tagIndex < tags.size(); tagIndex++){
					Boolean foundTag = false;
					for(int treeTagIndex = 0; treeTagIndex < treeTags.size(); treeTagIndex++){
						if(treeTags.get(treeTagIndex).getType().equalsIgnoreCase(tags.get(tagIndex).getType())){
							foundTag = true;
						}
					}
					if(!foundTag)
					{
						Tag nTag = new Tag(tags.get(tagIndex).getType());
						nTag.getValue().addAll(tags.get(tagIndex).getValue());
						treeTags.add(nTag);					
					}
					else
					{
						ArrayList<String> val = tags.get(tagIndex).getValue();
						for(int valueIndex=0; valueIndex<val.size();valueIndex++)
						{
							int index=indexTag(tags.get(tagIndex).getType(),treeTags);
							if(index == -1){
								continue;
							}
							Boolean found = false;
							for(int k = 0; k < treeTags.get(index).getValue().size(); k++){
								if(treeTags.get(index).getValue().get(k).equalsIgnoreCase(val.get(valueIndex)))
								{
									found = true;
									break;
								}
							}
							if(!found){
								treeTags.get(index).getValue().add(val.get(valueIndex));
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
	
	/**
	 * Index of the tag
	 *
	 * @param type the tag type
	 * @param tags the arraylist of all tags tags
	 * @return the int of index
	 */
	public int indexTag(String type, ArrayList<Tag> tags){
		for(int i = 0; i < tags.size(); i ++){
			if(tags.get(i).getType().equalsIgnoreCase(type)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Select value appears if the tag type is selected and it populates with the values of the tag type. 
	 */
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
	
	/**
	 * When the addtag button is clicked this method is executed. Adds the tag to the tag area to be added to be search. 
	 *
	 * @param e the e
	 */
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

	/**
	 * When the search button is hit this method is executed. 
	 *
	 * @param e the e
	 * @throws ParseException the parse exception
	 */
	public void searchOK(ActionEvent e) throws ParseException {
		//check if valid date
		//add based on date
		Boolean hasTags = false;
		ArrayList<Photo> output = new ArrayList<Photo>();
		//Check if incomplete search query.
		if((from.getValue()!=null && to.getValue()==null) || (from.getValue()==null && to.getValue()!=null))
		{
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Search");
			message.setHeaderText("Cannot Search");
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
					for(int z=0;z<currPhoto.getTags().size();z++)
					{
						for(int a=0;a<searchTag.size();a++)
						{
							if(currPhoto.getTags().get(z).getType().equalsIgnoreCase(searchTag.get(a).getType()))
							{
								for(int b = 0; b < searchTag.get(a).getValue().size();b++){
									if(currPhoto.getTags().get(z).getValue().contains(searchTag.get(a).getValue().get(b)))
									{
										if(!tag.contains(currPhoto))
										{
											tag.add(currPhoto);
											hasTags = true;
										}
									}
								}
							}
						}
					}
				}
			}
			output.addAll(tag);
		}
		//CHECK FOR IF ONLY DATES
		if((from.getValue()!=null && to.getValue()!=null))
		{
			Calendar fd = Calendar.getInstance();
			fd.set(from.getValue().getYear(), from.getValue().getMonthValue()-1, from.getValue().getDayOfMonth());
			fd.set(Calendar.MILLISECOND, 0);
			
			Calendar td = Calendar.getInstance();
			td.set(to.getValue().getYear(), to.getValue().getMonthValue()-1, to.getValue().getDayOfMonth());
			td.set(Calendar.MILLISECOND, 0);
			if(td.before(fd)){
				Alert message = new Alert(AlertType.INFORMATION);
				message.initOwner(localStage);
				message.setTitle("Search");
				message.setHeaderText("Cannot Search");
				message.setContentText("From Date must be before or equal to To Date.");
				message.setGraphic(null);
				message.getDialogPane().getStylesheets().add("/view/loginPane.css");
				message.showAndWait();
				return;
			}
			ArrayList<Photo> date = new ArrayList<Photo>();
			Album currAlbum;
			Photo currPhoto;
			
			for(int x=0; x<album.size();x++)
			{
				currAlbum = album.get(x);
				for(int y=0; y<album.get(x).getPhotos().size(); y++)
				{
					currPhoto= currAlbum.getPhotos().get(y);
					if( ((fd.before(currPhoto.getCal())) || isSameDay(currPhoto,fd)) && (td.after(currPhoto.getCal()) || isSameDay(currPhoto,td)) )
					{
						if(!date.contains(currPhoto))
						{
							date.add(currPhoto);
						}
						
					}
				}
			}
			ArrayList<Photo> both = new ArrayList<Photo>();
			if(!hasTags && !date.isEmpty()){
				output.addAll(date);
			}	
			else if(hasTags && !date.isEmpty())
			{
				
				for(int x=0;x<date.size();x++)
				{
					if(output.contains(date.get(x)))
					{
						both.add(date.get(x));
					}
				}
				output = both;
			}
			else if(hasTags && date.isEmpty()){
				output.clear();
			}
		}
		if(output.isEmpty()){
			Alert message = new Alert(AlertType.INFORMATION);
			message.initOwner(localStage);
			message.setTitle("Search");
			message.setHeaderText("Cannot Search");
			message.setContentText("No Photos Found with selected criteria");
			message.setGraphic(null);
			message.getDialogPane().getStylesheets().add("/view/loginPane.css");
			message.showAndWait();
			return;
		}
		try{
			
			FXMLLoader load = new FXMLLoader();
			load.setLocation(getClass().getResource("/view/photoPane.fxml"));
			AnchorPane root = (AnchorPane)load.load();
			photoPaneController ppc = load.getController();
			Album aa = new Album("search album");
			aa.getPhotos().addAll(output);
			ppc.start(apc.primaryStage,apc.users, apc.currentUser, apc.userIndex, apc.primaryStage.getScene(),apc,aa, true);
			Scene scene = new Scene(root);
			double w = apc.primaryStage.getWidth();
			double h = apc.primaryStage.getHeight();
			apc.primaryStage.setScene(scene);
			apc.primaryStage.setWidth(w);
			apc.primaryStage.setHeight(h);
			localStage.close();
			root.requestFocus();
			
		}catch(Exception ee){
			ee.printStackTrace();
		}


		
		localStage.close();
	}
		
	/**
	 * Checks if it is same day.
	 *
	 * @param currPhoto the current photo
	 * @param day the day
	 * @return the boolean if its the same day or not
	 */
	public Boolean isSameDay(Photo currPhoto, Calendar day)
	{
		String date = new SimpleDateFormat("MM/dd/yyyy").format(day.getTime());
		if(currPhoto.getDate().equalsIgnoreCase(date))
		{
			return true;
		}
		return false;
	}

	/**
	 * When you hit the cancel button this method is executed. 
	 *
	 * @param e the e
	 */
	public void searchCancel(ActionEvent e){
		localStage.close();
	}
	
		
}
