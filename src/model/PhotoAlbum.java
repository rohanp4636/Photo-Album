package model;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class PhotoAlbum extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/create.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			//test tester = loader.getController();
			//tester.start(primaryStage);
			Scene scene = new Scene(root);
			
			root.requestFocus();
			primaryStage.setResizable(false);
			primaryStage.setTitle("Photos");
			primaryStage.setScene(scene);


			primaryStage.show();
		

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

