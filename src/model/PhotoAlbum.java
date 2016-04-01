package model;
	
import java.io.File;
import java.util.ArrayList;

import controller.loginPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class PhotoAlbum extends Application {
	Admin admin;
	

	
	public void init() throws Exception{
		admin = Admin.readAdmin();
		
	}
	
	//when saving make all static selection var false or nulll. also check other static stuff;
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
	
	public void stop() throws Exception{
		Admin.writeAdmin(admin);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

