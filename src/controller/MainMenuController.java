package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MainMenuController {
	Stage stage;
	Scene scene;
	Parent root;

	private Main main;
	public void setMain(Main mainIn)
	{
	main=mainIn;
	}
	//////////////If you want to use something other than Button/Label for these in the FXML
	//////////////Go ahead I will change the code here to fit GUI, just remember to use the same
	//////////////Name though
	@FXML private Button logOut; //Logs Out
    @FXML private Button OOB; // OutOfBreath
    @FXML private Button AAP; // Asthma Action Plan
    @FXML private Button updateAcnt; //Update Account
    @FXML private Label welcome; // Welcome Message

    //Runs methods at scene start
    @FXML
    void initialize(){
    setWelcome();
    }

    //Displays welcome message, and makes it editable in code. Watch method called for changes.
    public void setWelcome() {
    	welcome.setText("Welcome, " + AsthmaController.curUser.getfirstName());
    }

    @FXML
	void ClickButton(ActionEvent event) throws Exception {
    	try{
    	//finds what stage the button is in
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		//this gets the name of button
		String temp = ((Node) event.getSource()).getId().toString();

		switch (temp) {
		case "logOut":
			root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
			AsthmaController con1=new AsthmaController();
			con1.setMain(main);
			break;
		case "OOB":
			root = FXMLLoader.load(getClass().getResource("/view/OutOfBreathView.fxml"));
			OutOfBreathController con2=new OutOfBreathController();
			con2.setMain(main);
			break;

		case "AAP":
			root = FXMLLoader.load(getClass().getResource("/view/AAPView.fxml"));
			AAPController con3=new AAPController();
			con3.setMain(main);
			break;
			
		case "updateAcnt":
			root = FXMLLoader.load(getClass().getResource("/view/UpdateView.fxml"));
			AAPController con4=new AAPController();
			con4.setMain(main);
			break;

		default:
			root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
			MainMenuController conX=new MainMenuController();
			conX.setMain(main);
			break;
		}
		scene = new Scene(root);
		stage.setScene(scene);
	} catch (Exception e){
		e.printStackTrace();
	}
    }
}