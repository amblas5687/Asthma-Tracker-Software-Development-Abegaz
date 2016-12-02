package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DBConfig;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Account;


public class OutOfBreathController
{

	//FXML
    @FXML
    private Label breathCountLBL;
    @FXML
    private Button outBreathBTN;
    @FXML
    private Button backMainBTN;
    @FXML
    private Button resetBTN;

    //For loading new scenes
    Stage stage;
    Scene scene;
    Parent root;

    //counter for breath count
    private int breathCount;

    Account activeUser = AsthmaController.curUser;

  	private Main main;
  	public void setMain(Main mainIn)
  	{
  		main=mainIn;
  	}


  	@FXML
  	public void initialize() throws SQLException
  	{
  		System.out.println("error check: initialize ran");
  		getBreath();
  	}

  	//gets breath from database so if user moves pages, current breath is not lost. Reset during log in or log out
  	public int getBreath() throws SQLException
  	{
  		String SQLQuery = "SELECT * FROM `clicktracker` WHERE clicktracker.uNameFK2=" + "'"+activeUser.getuserName()+"'";
		ResultSet rs = null;

		//System.out.println ("error check line");

		try(

				Connection conn = DBConfig.getConnection();
				PreparedStatement dbBreathCount = conn.prepareStatement(SQLQuery);
		){
			rs = dbBreathCount.executeQuery();

			// check to see if receiving any data
			if (rs.next())
			{
	        	breathCount = rs.getInt("clicks");
	        	breathCountLBL.setText(Integer.toString(breathCount));

	        	System.out.println("error check: getBreath ran, breath count " + breathCount);
	        	return breathCount;

			}//if
			
		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);

		}finally //catch
		{
			if(rs != null)
			{
				rs.close();
			}
		}//finally
		return breathCount;
  	}

    //send the out of breath experiences to the DB
    @FXML
    void countOutOfBreath(ActionEvent event) throws SQLException
    {

  		System.out.println("error check: countOutOfBreath, current breath: " +breathCount);

    	breathCount ++;

    	//query for database
    	String updateQuery = "update clicktracker set clicks = ? where uNameFK2 = ?";

    	//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updateBreath = conn.prepareStatement(updateQuery);
			)
		{
			//the 1,2,3 in the setString correspond to the ?,?,? in the query
			updateBreath.setInt(1, breathCount);
			updateBreath.setString(2, activeUser.getuserName());

			//execute update
			updateBreath.executeUpdate();

			System.out.println("error check: success! breath updated! user:  " + activeUser.getuserName() + " breath count: " + breathCount);
        	breathCountLBL.setText(Integer.toString(breathCount));


		} catch (Exception e) {

			System.out.println("Error: " + e);
			//statusLabel.setText("Status: operation failed due to: " + e.getMessage());
		}
    }


    //resets breath count
    @FXML
    void resetOutOfBreath(ActionEvent event)
    {
    	breathCount = 0;

    	//query for database
    	String query = "update clicktracker set clicks = ? where uNameFK2 = ?";

    	//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement resetBreath = conn.prepareStatement(query);
			)
		{
			resetBreath.setInt(1, breathCount);
			resetBreath.setString(2, activeUser.getuserName());

			//execute update
			resetBreath.executeUpdate();
			
			System.out.println("error check: success! breath reset! user:  " + activeUser.getuserName() + " breath count: " + breathCount);
        	breathCountLBL.setText(Integer.toString(breathCount));


		} catch (Exception e) {

			System.out.println("Error: " + e);
		}
    }//end reset


    //return to the main view
    @FXML
    void returnToMain(ActionEvent event)
    {

    	//get the stage the button was hit in
    	stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

    	try {
			root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));

		MainMenuController conX=new MainMenuController();
		conX.setMain(main);
		scene = new Scene(root);
		stage.setScene(scene);
    	} catch (Exception e){
			e.printStackTrace();
		}


    }



}