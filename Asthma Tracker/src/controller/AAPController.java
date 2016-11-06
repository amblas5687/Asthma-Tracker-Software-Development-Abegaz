//Author: Anna
//Description: This is the controller for the AAP
//Problems: none
//Comments: this controller grabs the info from database, populates textfields, and lets the user submit new information
//I've set activeUser in the methods with the username ab6789 for testing purposes
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.AAPLauncher;
import application.DBConfig;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AAP;
import model.Account;


public class AAPController {

    @FXML
    private TextField mildMedTF;
    @FXML
    private TextField mildAmtTF;
    @FXML
    private TextField mildFreqTF;

    @FXML
    private TextField modMedTF;
    @FXML
    private TextField modAmtTF;
    @FXML
    private TextField modFreqTF;

    @FXML
    private TextField sevMedTF;
    @FXML
    private TextField sevAmtTF;
    @FXML
    private TextField sevFreqTF;

    @FXML
    private Button backBTN;
    @FXML
    private Button resetBTN;
    @FXML
    private Button updateBTN;
    

    @FXML
    private TextField drNameTF;
    @FXML
    private TextField drPhoneTF;
    @FXML
    private TextField drCityTF;
    
  //For loading new scenes
    Stage stage;
    Scene scene;
    Parent root;

    
    //current user
    Account activeUser = AsthmaController.curUser;
    
    
    //for testing and used by the AAPLancher
    private AAPLauncher main;
    public void setMain(AAPLauncher mainIn)
  	{
  		main=mainIn;
  	}
    
	//for integration
    /*
    private Main main;
  	public void setMain(Main mainIn)
  	{
  		main=mainIn;
  	}*/
  	
  	
  	//runs when initialized, grabs info from database and populates the textfields
	@FXML
  	public void initialize() throws SQLException
  	{
  		System.out.println("error check: initialize ran");
  		
  		//set username for testing
  		activeUser.setuserName("ab6789");
  		
  		//pass the object from getAAPInfo method
  		AAP userPlan = getAAPInfo(activeUser.getuserName());
  		
  		//set the textfields with the content of the database
  		
  		//mildMed
	    mildMedTF.setText(userPlan.getMildMed());
	    mildAmtTF.setText(Integer.toString(userPlan.getMildAmt()));
	    mildFreqTF.setText(Integer.toString(userPlan.getMildFreq()));

	    //modMed
	    modMedTF.setText(userPlan.getModMed());
	    modAmtTF.setText(Integer.toString(userPlan.getModAmt()));
	    modFreqTF.setText(Integer.toString(userPlan.getModFreq()));

	    //sevMed
	    sevMedTF.setText(userPlan.getSevMed());
	    sevAmtTF.setText(Integer.toString(userPlan.getSevAmt()));
	    sevFreqTF.setText(Integer.toString(userPlan.getSevFreq()));
	    
	    //dr info
	    drNameTF.setText(userPlan.getDrName());
	    drPhoneTF.setText(Integer.toString(userPlan.getDrPhone()));
	    drCityTF.setText(userPlan.getDrCity());
  	}//end method
  	
	
	
	//TODO need the fxml for main menu and the controller
  	//right now this is integrated personally
    @FXML
    void backToMain(ActionEvent event) throws Exception 
    {
    	/*
    	//get the stage the button was hit in
    	stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    	
    	//load the new fxml file
    	//TODO update the fxml to the main menu
    	root = FXMLLoader.load(getClass().getResource("/view/MainMenuTest.fxml"));
    	
    	//create a new controller
		MainMenuController con1 = new MainMenuController();
		
		//link the controller to the main
		con1.setMain(main);
		
		//sets fxml file as a scene
		scene = new Scene(root);
		
		//loads the scene on top of whatever stage the button is in
		stage.setScene(scene);
		*/
    }//end method
    
    
    
    //used to reset fields in AAP
    @FXML
    void resetAAP(ActionEvent event) {
    	//mildMed
	    mildMedTF.setText(null);
	    mildAmtTF.setText(null);
	    mildFreqTF.setText(null);

	    //modMed
	    modMedTF.setText(null);
	    modAmtTF.setText(null);
	    modFreqTF.setText(null);

	    //sevMed
	    sevMedTF.setText(null);
	    sevAmtTF.setText(null);
	    sevFreqTF.setText(null);
	    
	    //dr info
	    drNameTF.setText(null);
	    drPhoneTF.setText(null);
	    drCityTF.setText(null);
	    
	    System.out.println("error check: current user reset " + activeUser.getuserName());
    }//end method

    
    //use to submit AAP to database
    @FXML
    void submitAAP(ActionEvent event) throws SQLException {
    	
    	//get the information from the textfields 
    	
    	//mildMed
	    String mildMed = mildMedTF.getText();
	    int mildAmt = Integer.parseInt(mildAmtTF.getText());
	    int mildFreq = Integer.parseInt(mildFreqTF.getText());

	    //modMed
	    String modMed = modMedTF.getText();
	    int modAmt = Integer.parseInt(modAmtTF.getText());
	    int modFreq = Integer.parseInt(modFreqTF.getText());

	    //sevMed
	    String sevMed = sevMedTF.getText();
	    int sevAmt = Integer.parseInt(sevAmtTF.getText());
	    int sevFreq = Integer.parseInt(sevFreqTF.getText());
	    
	    //dr info
	    String drName = drNameTF.getText();
	    int drPhone = Integer.parseInt(drPhoneTF.getText());
	    String drCity = drCityTF.getText();
	    
    	
    	//create an instance of your model and set the values into it
    	AAP newPlan = new AAP();
    	
    	//mild
    	newPlan.setMildMed(mildMed);
    	newPlan.setMildAmt(mildAmt);
    	newPlan.setMildFreq(mildFreq);
    	
    	//mod
    	newPlan.setModMed(modMed);
    	newPlan.setModAmt(modAmt);
    	newPlan.setModFreq(modFreq);
    	
    	//sev
    	newPlan.setSevMed(sevMed);
    	newPlan.setSevAmt(sevAmt);
    	newPlan.setSevFreq(sevFreq);
    	
    	//dr info
    	newPlan.setDrName(drName);
    	newPlan.setDrPhone(drPhone);
    	newPlan.setDrCity(drCity);
    	
    	
    	//create a query 
    	String AAP = "update aap set mildMed = ?, mildAmt = ?, mildFreq = ?, modMed = ?, modAmt = ?, modFreq = ?, sevMed = ?, sevAmt = ?, sevFreq = ?, drName = ?, drPhone = ?, drCity = ?"
				+ "where uNameFK = ?";
    	

		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement insertAAP = conn.prepareStatement(AAP);) 
		{
		
			//mild
			insertAAP.setString(1, newPlan.getMildMed());
			insertAAP.setInt(2, newPlan.getMildAmt());
			insertAAP.setInt(3, newPlan.getMildFreq());
			
			//mod
			insertAAP.setString(4, newPlan.getModMed());
			insertAAP.setInt(5, newPlan.getModAmt());
			insertAAP.setInt(6, newPlan.getModFreq());
			
			//sev
			insertAAP.setString(7, newPlan.getSevMed());
			insertAAP.setInt(8, newPlan.getSevAmt());
			insertAAP.setInt(9, newPlan.getSevFreq());
			
			//dr info
			insertAAP.setString(10, newPlan.getDrName());
			insertAAP.setInt(11, newPlan.getDrPhone());
			insertAAP.setString(12, newPlan.getDrCity());
			
			insertAAP.setString(13, activeUser.getuserName());
		
			//set username for testing
	  		activeUser.setuserName("ab6789");
	  		
			//execute the update
			insertAAP.executeUpdate();
			
			System.out.println("error check: current user updated " + activeUser.getuserName());
			System.out.println("error check: success! account updated " + newPlan);

		}//try
    }//end method
    
    
    //gets info from the database 
    private AAP getAAPInfo (String userName) throws SQLException
    {
    	
    	String AAPInfo = "select * from aap where uNameFK =" + "'"+userName+"'";
		ResultSet rs = null;

		try(
				Connection conn = DBConfig.getConnection();
				PreparedStatement displayAAPInfo = conn.prepareStatement(AAPInfo);
		){
			//displayAccountInfo.setInt(1, voterId);
			rs = displayAAPInfo.executeQuery();

			// check to see if receiving any data
			if (rs.next())
			{
				//create instance of model
				AAP displayPlan = new AAP();
		    	
				//add info from db to model
				
		    	//mild
				displayPlan.setMildMed(rs.getString("mildMed"));
				displayPlan.setMildAmt(rs.getInt("mildAmt"));
				displayPlan.setMildFreq(rs.getInt("mildFreq"));
		    	
		    	//mod
				displayPlan.setModMed(rs.getString("modMed"));
				displayPlan.setModAmt(rs.getInt("modAmt"));
				displayPlan.setModFreq(rs.getInt("modFreq"));
		    	
		    	//sev
				displayPlan.setSevMed(rs.getString("sevMed"));
				displayPlan.setSevAmt(rs.getInt("sevAmt"));
				displayPlan.setSevFreq(rs.getInt("sevFreq"));
		    	
		    	//dr info
				displayPlan.setDrName(rs.getString("drName"));
				displayPlan.setDrPhone(rs.getInt("drPhone"));
				displayPlan.setDrCity(rs.getString("drCity"));
				
					
		        System.out.println("gotten aap " + displayPlan);
		       
		        //return the object 
		        return displayPlan;
			}//if
			else
			{
				return null;
			}
		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
			return null;
		}finally //catch
		{
			if(rs != null)
			{
				rs.close();
			}
		}//finally
    }//end method

}//end program