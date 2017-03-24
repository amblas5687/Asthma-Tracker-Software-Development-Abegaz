package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.DBConfig;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
  

    //Danni<start>-----------------------------------------------------------------------------------------------------
    @FXML private Label allFieldsErr, drNameErr, drCityErr, drPhoneErr, sevMedErr, sevAmtErr, sevFreqErr, modMedErr,
    	modAmtErr, modFreqErr, mildMedErr, mildAmtErr, mildFreqErr;
    @FXML Button btnNext1, btnNext2, btnNext3;
    @FXML TabPane tabpane;
    @FXML Tab tab1, tab2, tab3, tab4;
    //Danni<end>-------------------------------------------------------------------------------------------------------
    
    //current user
    Account activeUser = AsthmaController.curUser;


	//for integration
    private Main main;
  	public void setMain(Main mainIn)
  	{
  		main = mainIn;
  	}


  	//runs when initialized, grabs info from database and populates the textfields
	@FXML
  	public void initialize() throws SQLException
  	{
  		System.out.println("error check: initialize ran");
  		
  		//pass the object from getAAPInfo method
  		AAP userPlan = getAAPInfo(activeUser.getuserName());
  		
  		//set the textfields with the content of the database
  		//mildMed
	    mildMedTF.setText(userPlan.getMildMed());
	    mildAmtTF.setText(userPlan.getMildAmt());
	    mildFreqTF.setText(userPlan.getMildFreq());
	    //modMed
	    modMedTF.setText(userPlan.getModMed());
	    modAmtTF.setText(userPlan.getModAmt());
	    modFreqTF.setText(userPlan.getModFreq());
	    //sevMed
	    sevMedTF.setText(userPlan.getSevMed());
	    sevAmtTF.setText(userPlan.getSevAmt());
	    sevFreqTF.setText(userPlan.getSevFreq());
	    //dr info
	    drNameTF.setText(userPlan.getDrName());
	    drPhoneTF.setText(userPlan.getDrPhone());
	    drCityTF.setText(userPlan.getDrCity());
  	}//end method


	//TODO need the fxml for main menu and the controller
  	//right now this is integrated personally
    @FXML
    void backToMain(ActionEvent event) throws Exception
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

    //Danni<start>-----------------------------------------------------------------------------------------------------
    //takes user to moderate tab if all error checking is passed
    public void ClickNext1Button(ActionEvent event) throws SQLException{
   	 	String mim, mia, mif;
   	 	mim = mildMedTF.getText();
   	 	mia = mildAmtTF.getText();
	    mif = mildFreqTF.getText();
	     
	    allFieldsErr.setText(null);
	    //checks to see if it is null
	    if(!mim.equals("") && !mia.equals("") && !mif.equals("")){
	    	//takes user to next tab is all error checking is passed
	    	if(checkmildMed() == true){
	    		updatemildMed();
	    		tabpane.getSelectionModel().select(tab2);
	    	}
	     }else{
	    	 allFieldsErr.setText("Please fill in all fields for mild asthma info.");
	    }
    }
   
    //takes user to severe tab if all error checking is passed
    public void ClickNext2Button(ActionEvent event) throws SQLException{
   	 	String mom, moa, mof;
   	 	mom = modMedTF.getText();
   	 	moa = modAmtTF.getText();
	    mof = modFreqTF.getText();
	    
	    allFieldsErr.setText(null);
	    if(!mom.equals("") && !moa.equals("") && !mof.equals("")){
	    	//takes user to next tab is all error checking is passed
	    	if(checkmodMed() == true){
	    		updatemodMed();
	    		tabpane.getSelectionModel().select(tab3);
	    	}
	    }else{
	    	allFieldsErr.setText("Please fill in all fields for moderate asthma info.");
	    }
    }
    
    //takes user to doctor tab if all error checking is passed
    public void ClickNext3Button(ActionEvent event) throws SQLException{
    	 String sem, sea, sef;
    	 sem = sevMedTF.getText();
    	 sea = sevAmtTF.getText();
 	     sef = sevFreqTF.getText();
 	     
 	     allFieldsErr.setText(null);
 	     //checks to see if it is null
 	     if(!sem.equals("") && !sea.equals("") && !sef.equals("")){
 	    	//takes user to next tab is all error checking is passed
    		if(checksevMed() == true){
    			updatesevMed();
    			tabpane.getSelectionModel().select(tab4);
    		}
 	     }else{
 	    	allFieldsErr.setText("Please fill in all fields for severe asthma info.");
 	     }
    }
    //Danni<end>-------------------------------------------------------------------------------------------------------

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
	    
	    //clear error labels---Danni<start>----------------------------------------------------------------------------
	    //all fields
	    allFieldsErr.setText(null);
	    //dr info
	    drNameErr.setText(null); 
	    drCityErr.setText(null);
	    drPhoneErr.setText(null);
	    //sev
	    sevMedErr.setText(null);
	    sevAmtErr.setText(null);
	    sevFreqErr.setText(null);
	    //mod
	    modMedErr.setText(null);
    	modAmtErr.setText(null);
    	modFreqErr.setText(null);
    	//mild
    	mildMedErr.setText(null);
    	mildAmtErr.setText(null);
    	mildFreqErr.setText(null);
	    //Danni<end>---------------------------------------------------------------------------------------------------

	    System.out.println("error check: current user reset " + activeUser.getuserName());
    }//end method

    //Danni<start>-----------------------------------------------------------------------------------------------------
    //capitalizes first letter in name
  	public String capitalizeName(String name){
  			return name.substring(0,1).toUpperCase() + name.substring(1);//.toLowerCase();
  	}
    //Danni<end>-------------------------------------------------------------------------------------------------------
  	
    //use to submit AAP to database
    @FXML
    void submitAAP(ActionEvent event) throws SQLException {

    	//get the information from the textfields
    	//mildMed
	    String mildMed = mildMedTF.getText(); 
	    //modMed
	    String modMed = modMedTF.getText(); 
	    //sevMed
	    String sevMed = sevMedTF.getText();
		//dr info
	    String drName = drNameTF.getText();
	    String drPhone = drPhoneTF.getText();
	    String drCity = drCityTF.getText();
	    //Danni<start>-------------------------------------------------------------------------------------------------
	    //check to see if all fields of one of the tabs is filled in
	    String mia, mif, moa, mof, sea, sef;
	    mia = mildAmtTF.getText();
	    mif = mildFreqTF.getText();
	    moa = modAmtTF.getText();
	    mof = modFreqTF.getText();
	    sea = sevAmtTF.getText();
	    sef = sevFreqTF.getText();
	    
	    allFieldsErr.setText(null);
	    //checks for blank fields
	    if(!drName.equals("") && !drPhone.equals("") && !drCity.equals("")){
	    	if(checkdoctor() == true){
	    		updatedoctor();
	    		if(checkmildMed() == false){
	    			tabpane.getSelectionModel().select(tab1);
	    		}else if(checkmodMed() == false){
	    			tabpane.getSelectionModel().select(tab2);
	    		}else if(checksevMed() == false){
	    			tabpane.getSelectionModel().select(tab3);
	    		}else{
	    			updatemildMed();
	    			updatemodMed();
	    			updatesevMed();
	    			allFieldsErr.setText("Your AAP information has been updated.");
	    		}
	    	}
	    }else if(mildMed.equals("") || mia.equals("") || mif.equals("") || modMed.equals("") || moa.equals("") 
    			|| mof.equals("") || sevMed.equals("") || sea.equals("") || sef.equals("")){
	    	allFieldsErr.setText("Please fill in all fields for severity info.");
	    }else{
	    	allFieldsErr.setText("Please fill in all fields for doctor info.");
	    }
    	//Danni<end>---------------------------------------------------------------------------------------------------
    }//end method
    //Danni<start>-----------------------------------------------------------------------------------------------------
    private boolean checkdoctor(){
    	boolean test = true;
    	String drName = drNameTF.getText();
	    String drPhone = drPhoneTF.getText();
	    String drCity = drCityTF.getText();
	    
    	drNameErr.setText(null);
    	drCityErr.setText(null);
    	//checks for special characters and number in drName and drCity
    	Pattern p = Pattern.compile("[^a-zA-Z-\\.\\s]");
		Matcher dName = p.matcher(drName);
		Matcher dCity = p.matcher(drCity);
		boolean dn = dName.find();
		boolean dc = dCity.find();
		if(dn){
			drNameErr.setText("Invalid full name. Cannot use special characters or numbers.");
			test = false;
		}
		if(dc){
			drCityErr.setText("Invalid city name. Cannot use special characters or numbers.");
			test = false;
		}
		//capitalizes names
		drName = capitalizeName(drName);
		drCity = capitalizeName(drCity);
		
		drPhoneErr.setText(null);
		//checks to make sure phone number only consists of numbers and -
		Pattern p2 = Pattern.compile("[^0-9-]");
		Matcher phon = p2.matcher(drPhone);
		boolean pho = phon.find();
		if(pho){
			drPhoneErr.setText("Invalid phone number. Phone number must consist of numbers only.");
			test = false;
		}
		if(!drPhone.equals("") && drPhone.length() != 12){
			drPhoneErr.setText("Incorrect phone number length.");
			test = false;
		}else if(!drPhone.equals("") && (drPhone.charAt(3) != '-' || drPhone.charAt(7) != '-')){
			drPhoneErr.setText("Incorrect phone number format.");
			test = false;
		}
		return test;
    }
    //-----------------------------------------------------------------------------------------------------------------
    private void updatedoctor() throws SQLException{
    	//dr info
	    String drName = drNameTF.getText();
	    String drPhone = drPhoneTF.getText();
	    String drCity = drCityTF.getText();
	    //create an instance of your model and set the values into it
	    AAP newPlan = new AAP();
	    //dr info
    	newPlan.setDrName(drName);
    	newPlan.setDrPhone(drPhone);
    	newPlan.setDrCity(drCity);
    	//creates a query
    	String docInfoQuery = "UPDATE `asthmatrackerdb`.`doctorinfo` SET drName = ?, drPhone = ?, drCity = ?"
				+ "WHERE uNameFK6 = ?";
    	try(Connection conn = DBConfig.getConnection();
    			PreparedStatement insertDoc = conn.prepareStatement(docInfoQuery);
    	){
    		//doctor info
			insertDoc.setString(10, newPlan.getDrName());
			insertDoc.setString(11, newPlan.getDrPhone());
			insertDoc.setString(12, newPlan.getDrCity());
			insertDoc.setString(13, activeUser.getuserName());
			
			//execute the update
			insertDoc.executeUpdate();
			
			System.out.println("error check: current user updated " + activeUser.getuserName());
			System.out.println("error check: success! account updated " + newPlan);

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
    }
    //-----------------------------------------------------------------------------------------------------------------
    private boolean checkmildMed(){
   	 	boolean test = true;
   	 	String mim, mia, mif;
   	 	mim = mildMedTF.getText();
   	 	mia = mildAmtTF.getText();
	    mif = mildFreqTF.getText();
	    mim = capitalizeName(mim);
	     mildMedErr.setText(null);
	     //tests med for special characters and numbers
	     Pattern p = Pattern.compile("[^a-zA-Z-\\.\\s]");
	     Matcher miMed = p.matcher(mim);
	     boolean mi1 = miMed.find();
	     if(mi1){
			mildMedErr.setText("Invalid medicine type. Cannot contain special characters or numbers.");
			test = false;
		 }else{
			 mim = capitalizeName(mim);
		 }
	     
	     mildAmtErr.setText(null);
		 mildFreqErr.setText(null);
	     //checks for anything that isn't a number in
		 Pattern p2 = Pattern.compile("[^0-9]");
		 Matcher mia2 = p2.matcher(mia);
		 Matcher mif2 = p2.matcher(mif);
		 boolean mi2 = mia2.find();
		 boolean mi3 = mif2.find();
		 if(mi2){
			mildAmtErr.setText("Amount must consist of only numbers.");
			test = false;
		 }
		 if(mi3){
			mildFreqErr.setText("Frequency must consist of only numbers.");
			test = false;
		 }
		 return test;
    }
    //-----------------------------------------------------------------------------------------------------------------
    private void updatemildMed() throws SQLException{
    	//mildMed
	    String mildMed = mildMedTF.getText();
	    String mildAmt = mildAmtTF.getText();
		String mildFreq = mildFreqTF.getText();
    	//create an instance of your model and set the values into it
		AAP newPlan = new AAP();
    	//mild
    	newPlan.setMildMed(mildMed);
    	newPlan.setMildAmt(mildAmt);
    	newPlan.setMildFreq(mildFreq);
    	//create a query
    	String mildAAPQuery = "UPDATE `asthmatrackerdb`.`mildaap` SET mildMed = ?, mildAmt = ?, mildFreq = ?"
    			+ "WHERE uNameFK3 = ?";
    	//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement insertMild = conn.prepareStatement(mildAAPQuery);
		)
		{
			//mild
			insertMild.setString(1, newPlan.getMildMed());
			insertMild.setString(2, newPlan.getMildAmt());
			insertMild.setString(3, newPlan.getMildFreq());
			insertMild.setString(4, activeUser.getuserName());
			
			//execute the update
			insertMild.executeUpdate();
			
			System.out.println("error check: current user updated " + activeUser.getuserName());
			System.out.println("error check: success! account updated " + newPlan);

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
    }
    //-----------------------------------------------------------------------------------------------------------------
    private boolean checkmodMed(){
    	boolean test = true;
   	 	String mom, moa, mof;
   	 	mom = modMedTF.getText();
   	 	moa = modAmtTF.getText();
	    mof = modFreqTF.getText();
	     
	     modMedErr.setText(null);
	     //tests med for special characters and numbers
	     Pattern p = Pattern.compile("[^a-zA-Z-\\.\\s]");
	     Matcher moMed = p.matcher(mom);
	     boolean mo1 = moMed.find();
	     if(mo1){
			modMedErr.setText("Invalid medicine type. Cannot contain special characters or numbers.");
			test = false;
		 }else{
			 mom = capitalizeName(mom);
		 }
	     
	     modAmtErr.setText(null);
		 modFreqErr.setText(null);
	     //checks for anything that isn't a number in
		 Pattern p2 = Pattern.compile("[^0-9]");
		 Matcher moa2 = p2.matcher(moa);
		 Matcher mof2 = p2.matcher(mof);
		 boolean mo2 = moa2.find();
		 boolean mo3 = mof2.find();
		 if(mo2){
			modAmtErr.setText("Amount must consist of only numbers.");
			test = false;
		 }
		 if(mo3){
			modFreqErr.setText("Frequency must consist of only numbers.");
			test = false;
		 }
		 return test;
    }
    //-----------------------------------------------------------------------------------------------------------------
    private void updatemodMed() throws SQLException{
    	//modMed
	    String modMed = modMedTF.getText();
	    String modAmt = modAmtTF.getText();
		String modFreq = modFreqTF.getText();
	    //create an instance of your model and set the values into it
		AAP newPlan = new AAP();
    	//mod
    	newPlan.setModMed(modMed);
    	newPlan.setModAmt(modAmt);
    	newPlan.setModFreq(modFreq);
    	//create a query
		String modAAPQuery = "UPDATE `asthmatrackerdb`.`moderateaap` SET modMed = ?, modAmt = ?, modFreq = ?"
				+ "WHERE uNameFK4 = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();	
				PreparedStatement insertMod = conn.prepareStatement(modAAPQuery);	
		)
		{
			//mod
			insertMod.setString(1, newPlan.getModMed());
			insertMod.setString(2, newPlan.getModAmt());
			insertMod.setString(3, newPlan.getModFreq());
			insertMod.setString(4, activeUser.getuserName());
			
			//execute the update
			insertMod.executeUpdate();
			
			System.out.println("error check: current user updated " + activeUser.getuserName());
			System.out.println("error check: success! account updated " + newPlan);

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
    }
    //-----------------------------------------------------------------------------------------------------------------
    private boolean checksevMed(){
    	boolean test = true;
    	String sem, sea, sef;
   	 	sem = sevMedTF.getText();
   	 	sea = sevAmtTF.getText();
   	 	sef = sevFreqTF.getText();
	     
	    sevMedErr.setText(null);
	    //tests med for special characters and numbers
	    Pattern p = Pattern.compile("[^a-zA-Z-\\.\\s]");
	    Matcher seMed = p.matcher(sem);
	    boolean se1 = seMed.find();
	    if(se1){
			sevMedErr.setText("Invalid medicine type. Cannot contain special characters or numbers.");
			test = false;
	    }else{
	    	sem = capitalizeName(sem);
	    }
		
	    sevAmtErr.setText(null);
	    sevFreqErr.setText(null);
	    //checks for anything that isn't a number in
	    Pattern p2 = Pattern.compile("[^0-9]");
	    Matcher sea2 = p2.matcher(sea);
	    Matcher sef2 = p2.matcher(sef);
	    boolean se2 = sea2.find();
	    boolean se3 = sef2.find();
	    if(se2){
			sevAmtErr.setText("Amount must consist of only numbers.");
			test = false;
	    }
	    if(se3){
			sevFreqErr.setText("Frequency must consist of only numbers.");
			test = false;
	    }
	    return test;
    }
    //-----------------------------------------------------------------------------------------------------------------
    private void updatesevMed() throws SQLException{
    	//sevMed
	    String sevMed = sevMedTF.getText();
	    String sevAmt = sevAmtTF.getText();
		String sevFreq = sevFreqTF.getText();
    	//create an instance of your model and set the values into it
		AAP newPlan = new AAP();
    	//sev
    	newPlan.setSevMed(sevMed);
    	newPlan.setSevAmt(sevAmt);
    	newPlan.setSevFreq(sevFreq);
    	//create a query
		String sevAAPQuery = "UPDATE `asthmatrackerdb`.`severeaap` SET sevMed = ?, sevAmt = ?, sevFreq = ?"
				+ "WHERE uNameFK5 = ?";
		
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement insertSev = conn.prepareStatement(sevAAPQuery);
				)
		{
			//severe
			insertSev.setString(1, newPlan.getSevMed());
			insertSev.setString(2, newPlan.getSevAmt());
			insertSev.setString(3, newPlan.getSevFreq());
			insertSev.setString(4, activeUser.getuserName());
			
			//execute the update
			insertSev.executeUpdate();
			
			
			System.out.println("error check: current user updated " + activeUser.getuserName());
			System.out.println("error check: success! account updated " + newPlan);

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
    }
    //Danni<end>-------------------------------------------------------------------------------------------------------
    //gets info from the database
    private AAP getAAPInfo (String userName) throws SQLException
    {

    	String mildInfo = "select * from mildaap where uNameFK3 =" + "'"+userName+"'";
    	String modInfo = "select * from moderateaap where uNameFK4 =" + "'"+userName+"'";
    	String sevInfo = "select * from severeaap where uNameFK5 =" + "'"+userName+"'";
    	String docInfo = "select * from doctorinfo where uNameFK6 =" + "'"+userName+"'";
    	
    	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	ResultSet rs3 = null;
    	ResultSet rs4 = null;
    	    	
    	//create instance of model
		AAP displayPlan = new AAP();

		try(
				Connection conn = DBConfig.getConnection();
				PreparedStatement displayMildInfo = conn.prepareStatement(mildInfo);
				PreparedStatement displayModInfo = conn.prepareStatement(modInfo);
				PreparedStatement displaySevInfo = conn.prepareStatement(sevInfo);
				PreparedStatement displayDocInfo = conn.prepareStatement(docInfo);
			
		){
			rs1 = displayMildInfo.executeQuery();
			rs2 = displayModInfo.executeQuery();
			rs3 = displaySevInfo.executeQuery();
			rs4 = displayDocInfo.executeQuery();
			
			/*System.out.println("result set1" + rs1);
			System.out.println("result set2" + rs1);
			System.out.println("result set3" + rs1);
			System.out.println("result set4" + rs1);*/

			// check to see if receiving any data
			if (rs1.next() && rs2.next() && rs3.next() && rs4.next())
			{

				//add info from db to model

		    	//mild
				displayPlan.setMildMed(rs1.getString("mildMed"));
				displayPlan.setMildAmt(rs1.getString("mildAmt"));
				displayPlan.setMildFreq(rs1.getString("mildFreq"));
				
		    	//mod
				displayPlan.setModMed(rs2.getString("modMed"));
				displayPlan.setModAmt(rs2.getString("modAmt"));
				displayPlan.setModFreq(rs2.getString("modFreq"));

		    	//sev
				displayPlan.setSevMed(rs3.getString("sevMed"));
				displayPlan.setSevAmt(rs3.getString("sevAmt"));
				displayPlan.setSevFreq(rs3.getString("sevFreq"));

		    	//dr info
				displayPlan.setDrName(rs4.getString("drName"));
				displayPlan.setDrPhone(rs4.getString("drPhone"));
				displayPlan.setDrCity(rs4.getString("drCity"));
				

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
			if(rs1 != null && rs2 != null && rs3 != null && rs4 != null)
			{
				rs1.close();
				rs2.close();
				rs3.close();
				rs4.close();
				
			}
		}//finally
    }//end method

}//end program