//Author: Anna
//Description: This is a main launcher, to test the functionality of the controller without connecting it to the others
//Problems: none
//Comments: 

package application;
	
import controller.AAPController;
import controller.OutOfBreathController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class AAPLauncher extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/view/AAPView.fxml"));
			Scene scene = new Scene(root,700,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			// we need to give the controller access to the Main app.
			AAPController controller = new AAPController();
			controller.setMain(this);;
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}