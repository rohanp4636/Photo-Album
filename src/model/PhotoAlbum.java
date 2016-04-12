package model;
	
import java.util.ArrayList;

import controller.loginPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
/**
 * Photo Album Class - defines attributes of the Photo Album
 */
public class PhotoAlbum extends Application {
	
	/** The admin obj. */
	Admin admin;
	
	/**
	 * Close the read admin method and loads up the userdat file
	 */
	public void init() throws Exception{
		try{
			admin = Admin.readAdmin();

		}
		catch(Exception e){
			admin = new Admin();
		}
		
	}
	

	//when saving make all static selection var false or nulll. also check other static stuff;
	/**
	 * Starts the login pane controller
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			if(admin.userList == null){
				admin.userList = new ArrayList<User>();
			}
			 
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/loginPane.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			loginPaneController lpg = loader.getController();
			lpg.start(primaryStage,admin.userList, admin);
			Scene scene = new Scene(root);
			
			root.requestFocus();
			primaryStage.setResizable(false);
			primaryStage.setTitle("Photos");
			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(1296);
			primaryStage.setScene(scene);
			primaryStage.show();
		

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * when the app is closed it saves it in the data file
	 */
	public void stop() throws Exception{
		try{
			Admin.writeAdmin(admin);
		}
		catch(Exception e){
			return;
		}
	}
	
	/**
	 * The main method runs the photo album.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

