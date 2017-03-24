package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import application.Main;
import application.DBConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;

public class AsthmaController {
	Main main = new Main();
	//-----------------------------------------------------------------------------------------------------------------
	//accessing the controls from .fxml file for inserting purpose
	@FXML private TextField txtfirstName, txtlastName,txtuserName, txtgetUserName, txtbirthDate, txtfullName, txtrelation, txtphone, txtemail;
	@FXML private PasswordField  txtpassword, txtconPassword, txtgetPassword;
	@FXML private Button btnSubmit, btnNext, btngetLogIn, btngetLogIn1, btnlogIn, btnCreateAccount;
	@FXML private Label lblStatus, lblErrorallFields1, lblErrorallFields2, lblErrorfirstName, lblErrorlastName, lblErroruserName, 
	lblErrorPassword, lblErrorbirthDate, lblErrorlogIn, lblErrorgetUserName, lblErrorgetPassword, lblErrorECname, 
	lblErrorECrelation, lblErrorECphone, lblErrorECemail;
	Stage stage;
	Scene scene;
	Parent root;
	@FXML TabPane tabPane;
	@FXML Tab tab1, tab2;
	// EventHandler +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//-----------------------------------------------------------------------------------------------------------------
	//defines user object to enable the passing userName
	public static Account curUser = new Account();//-Anna
	//-----------------------------------------------------------------------------------------------------------------
    //sets main in Main.java 
	public void setMain(Main mainIn)
	{
		main = mainIn;
	}
	//-----------------------------------------------------------------------------------------------------------------
	/*Click Event which checks password & username, clears if error occurs, takes to LogIn view*/
	public void ClickNextButton(ActionEvent event) throws SQLException {
		String firstName, lastName, userName, password, conPassword, birthDate;
		int password2, conPassword2;
		boolean test = false;
		firstName = txtfirstName.getText();
		lastName = txtlastName.getText();
		userName = txtuserName.getText();
		password = txtpassword.getText();
		conPassword = txtconPassword.getText();
		birthDate = txtbirthDate.getText();
		
		int plen = txtpassword.getLength();
		int cplen = txtconPassword.getLength();
		
		try{
			lblErroruserName.setText(null);
			lblErrorallFields1.setText(null);
			lblErrorfirstName.setText(null);
			lblErrorlastName.setText(null);
			lblErrorbirthDate.setText(null);
		}catch(NullPointerException e){
			e.getMessage();
		}

		if(firstName.equals("") || lastName.equals("") || userName.equals("") || 
				password.equals("") || conPassword.equals("") || birthDate.equals("")){//catches when all or any field is blank
			lblErrorallFields1.setText("Please fill in all fields.");
			test = false;
		}
		
		//checks for numbers in firstName and lastName
		Pattern p1 = Pattern.compile("([0-9])", Pattern.CASE_INSENSITIVE);
		Matcher firstN = p1.matcher(firstName);
		Matcher lastN = p1.matcher(lastName);
		boolean firNam = firstN.find();
		boolean lasNam = lastN.find();
		
		if(firNam){
			lblErrorfirstName.setText("First name cannot contain numbers.");
			test = false;
		}
		if(lasNam){
			lblErrorlastName.setText("Last name cannot contain numbers.");
			test = false;
		}

		//checks for spaces in userName
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(userName);
		boolean space = matcher.find();
		
		if(space){
			lblErroruserName.setText("User name cannot contain any spaces.");
			test = false;
		}
		
		//checks for special characters in firstName, lastName, and userName -> not allowed, except - or .
		Pattern p = Pattern.compile("[^a-zA-Z0-9-\\.]");//regex, can test on regex101.com -> very useful
		Matcher uName = p.matcher(userName);
		Matcher fName = p.matcher(firstName);
		Matcher lName = p.matcher(lastName);
		boolean un = uName.find();
		boolean fn = fName.find();
		boolean ln = lName.find();
		
		if(un){
			lblErroruserName.setText("Invalid user name. Cannot use special characters.");
			test = false;
		}
		if(fn){//fix for - and .
			lblErrorfirstName.setText("Invalid first name.Cannot use special characters.");
			test = false;
		}
		if(ln){	
			lblErrorlastName.setText("Invalid last name.Cannot use special characters.");
			test = false;
		}
		
		//won't run password checks if null
		if(plen > 0 && cplen > 0){
			try{
				lblErrorPassword.setText(null);
				password2 = Integer.parseInt(txtpassword.getText());
				conPassword2 = Integer.parseInt(txtconPassword.getText());
				
				if(plen != 4 || cplen != 4){//catches when field is less than or greater than 4 digits
					lblErrorPassword.setText("Password must be 4 digits long.");
					test = false;
					txtpassword.setText(null);
					txtconPassword.setText(null);
				}else if(password2 == conPassword2){//will insert account
					test = true;
				}else{//password does not equal confirmation password
					lblErrorPassword.setText("Incorrect password confirmation.");
					test = false;
					txtpassword.setText(null);
					txtconPassword.setText(null);
				}
			}catch(NumberFormatException e){//thrown when a non-number is entered for password
				lblErrorPassword.setText("Password must consist of numbers.");
				txtpassword.setText(null);
				txtconPassword.setText(null);
				//e.printStackTrace();
				test = false;
			}catch(Exception ex){
				ex.getMessage();
			}
		}
		
		//error check for birthDate
		Pattern p2 = Pattern.compile("[^0-9/]");
		Matcher bDate = p2.matcher(birthDate);
		boolean bDat = bDate.find();
		
		String string = birthDate;
		int count = 0;
		for( int i = 0; i < string.length(); i++ ) {
		    if( string.charAt(i) == '/' ) {
		        count++;
		    } 
		}
		
		if(bDat){
			lblErrorbirthDate.setText("Birth Date must only contain numbers.");
 			test = false;
		}else if(!birthDate.equals("") && count == 0 || count == 1){
			lblErrorbirthDate.setText("Date format required.");
			test = false;
		}else{
			try{
				String[] parts = string.split("/");
				String part1 = parts[0]; //charAt(0) & charAr(1)
				String part2 = parts[1];
				String part3 = parts[2];
				int month = Integer.parseInt(part1);
				int day = Integer.parseInt(part2);
				int year = Integer.parseInt(part3);
				//System.out.println(month+":"+day+":"+year);//testing
				if(!birthDate.equals("") && birthDate.charAt(2) != '/'|| birthDate.charAt(5) != '/'|| birthDate.length() != 10){
					lblErrorbirthDate.setText("Incorrect date format.");
					test = false;
				}else if(!birthDate.equals("") && month > 12 || month < 1 || day > 31 || day < 1 || year > 2017 || year < 1920){
					lblErrorbirthDate.setText("Invalid date.");
					test = false;
				}else if(!birthDate.equals("") && month == 2 && day > 29){
					lblErrorbirthDate.setText("Invalid day for February.");
					test = false;
				}else if(!birthDate.equals("") && month == 4 || month == 6 || month == 9 || month == 11 && day > 30){
					lblErrorbirthDate.setText("Invalid day for that month.");
					test = false;
				}
			}catch(ArrayIndexOutOfBoundsException e){
				e.getMessage();
			}
		}
		/*if(birthDate.isEmpty()){
			lblErrorbirthDate.setText(null);
			test = false;
		}*/
		//will only insert account if everything checks out
		if(test == true){
			tabPane.getSelectionModel().select(tab2);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//if all error checking is passed all data is inserted into account
	public void ClickSubmitButton(ActionEvent event) throws SQLException {
		String fullName, relation, phone, email; 
		fullName = txtfullName.getText();
		relation = txtrelation.getText();
		phone = txtphone.getText();
		email = txtemail.getText();
		boolean test = false;
		
		try{
			lblErrorallFields2.setText(null);
			lblErrorECname.setText(null);
			lblErrorECrelation.setText(null);
			lblErrorECphone.setText(null);
			lblErrorECemail.setText(null);
		}catch(NullPointerException e){
			e.getMessage();
		}
		
		if(fullName.equals("") || relation.equals("") || phone.equals("") || email.equals("")){
			lblErrorallFields2.setText("Please fill in all fields.");
			test = false;
		}else{
			test = true;
		}
		
		//checks for to make sure full name only consists of letters, ., -, and spaces
		Pattern p = Pattern.compile("[^a-zA-Z-\\.\\s]");
		Matcher fuName = p.matcher(fullName);
		boolean fun = fuName.find();
		if(fun){
			lblErrorECname.setText("Invalid full name. Cannot use special characters or numbers.");
			test = false;
		}
	
		//checks to make sure relation only contains letters, -, and spaces
		Pattern p2 = Pattern.compile("[^a-zA-Z-\\s]");
		Matcher relat = p2.matcher(relation);
		boolean rel = relat.find();
		if(rel){
			lblErrorECrelation.setText("Invalid relation. Cannot use special characters or numbers.");
			test = false;
		}
		
		//checks to make sure phone number only consists of numbers and -
		Pattern p3 = Pattern.compile("[^0-9-]");
		Matcher phon = p3.matcher(phone);
		boolean pho = phon.find();
		if(pho){
			lblErrorECphone.setText("Invalid phone number. Phone number must consist of numbers only.");
			test = false;
		}else if(!phone.equals("") && (phone.charAt(3) != '-' || phone.charAt(7) != '-' || phone.length() != 12)){
			lblErrorECphone.setText("Incorrect phone number format.");
			test = false;
		}
		
		//checks to make sure email consists of at least one @ and .
		String s = email;
		int count = 0;
		for( int i = 0; i < s.length(); i++ ) {
		    if( s.charAt(i) == '@' ) {
		        count++;
		    } 
		}
		int counter = 0;
		for( int i = 0; i < s.length(); i++ ) {
		    if( s.charAt(i) == '.' ) {
		        counter++;
		    } 
		}
		if(!email.equals("") && count < 1 || counter < 1 || s.length() < 5){
			lblErrorECemail.setText("Invalid email address.");
			test = false;
		}else if(s.length() > 30){
			lblErrorECemail.setText("Email is too long. Cannot exceed 30 characters.");
			test = false;
		}
		if(email.equals("")){
			lblErrorECemail.setText(null);
			test = false;
		}
		if(test == true){
			insertAccount(event);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//capitalizes first letter in name
	public String capitalizeName(String name){
			return name.substring(0,1).toUpperCase() + name.substring(1);//.toLowerCase();
	}
	//-----------------------------------------------------------------------------------------------------------------		
	//log in button clicked on CreateAccount page, takes you to LogIn page
	public void ClickgetLogInButton(ActionEvent event) throws Exception {
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
	//-----------------------------------------------------------------------------------------------------------------
	//takes you from the LogIn page to the CreateAccount page
	public void ClickgotoCreateAccountButton(ActionEvent event) throws Exception {
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/CreateAccount.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Checks userName and password against database userNames */
	public boolean checkLogIn(String userName, String password) throws SQLException{//having issues:it's trying to look for a column name rather than a value in that column
		boolean logIn = false;
		boolean missing_credentials = false;
		try{
			lblErrorgetUserName.setText(null);
			lblErrorgetPassword.setText(null);
		}catch(NullPointerException e){
			e.getMessage();
		}
		
		if(userName.equals("")){//runs if userName is null
			lblErrorgetUserName.setText("User name is required.");
			missing_credentials = true;
		}
		if(password.equals("")){//runs if password is null
			lblErrorgetPassword.setText("Password is required.");
			missing_credentials = true;
		}
		if(missing_credentials){//break out of method if missing userName or password
			return false;
		}
		try 
		{
			Connection conn = DBConfig.getConnection();
			
			String query = "SELECT userName, password FROM userinfo WHERE userName = ? AND password = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
	
		    while(rs.next()){
			    String checkUser = rs.getString("userName");
			    String checkPass = rs.getString("password");
			    if(checkUser != null && checkPass != null){//checks userName and password from database with local variables
			    	if(checkUser.equals(userName) && checkPass.equals(password)){
			    		logIn = true;
			    	}else{
			    		logIn = false;
			    	}
			    }else{
			    	lblErrorlogIn.setText("checkUser or userName was null");
			    }
		    }

		    conn.close();  
		}catch (SQLException ex) {
			DBConfig.displayException(ex);
		}catch(Exception e){
			e.getMessage();
		}
		return logIn;
	}
	//-----------------------------------------------------------------------------------------------------------------
	//Click Log In button on Log In page, takes you to main view page
	public void ClicklogInButton(ActionEvent event) throws Exception {
		String userName, password; 
		userName = txtgetUserName.getText();
		password = txtgetPassword.getText();
		
		try{
			lblErrorlogIn.setText(null);
		}catch(NullPointerException e){
			e.getMessage();
		}
		
		if(checkLogIn(userName, password)){ 
			setCurrentUserInfo(userName);
			resetBreathZero(userName);
			
			stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
			scene = new Scene(root);
			stage.setScene(scene);
		}else{
			if(!userName.equals("") && !password.equals("")){
				lblErrorlogIn.setText("Incorrect user name or password.");
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//gets and sets current users
	public void setCurrentUserInfo(String curUserName) throws SQLException{
		//String currentUser = curUser;//mod by Anna
		//return curUser;//mod by Anna
		String SQLQuery = "SELECT * FROM `userinfo` WHERE userinfo.userName=" + "'"+curUserName+"'";
		ResultSet rs = null;

		try(
			Connection conn = DBConfig.getConnection();
			PreparedStatement curUserInfo = conn.prepareStatement(SQLQuery);
		){

			rs = curUserInfo.executeQuery();

			// check to see if receiving any data
			if (rs.next())
			{
				//create an instance of your model
	        	curUser.setfirstName(rs.getString("firstName"));
	        	curUser.setlastName(rs.getString("lastName"));
		   		curUser.setuserName(rs.getString("userName"));
	       		System.out.println("error check: current user " + curUser.getfirstName() + " " + curUser.getlastName() + " " +curUser.getuserName());//-Anna
			}//if
			else
			{
				curUser.setfirstName(null);
      			curUser.setlastName(null);
     			curUser.setuserName(null);
	  			System.out.println("current user not found" + curUser);
			}
		}catch(SQLException ex){//try
			ex.printStackTrace();
		}finally{//catch
			if(rs != null)
			{
				rs.close();
			}
		}//finally
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Insert a row to Account table of Database -> populates: firstName, lastName, userName, password */
	private void insertAccount(ActionEvent event) throws MySQLIntegrityConstraintViolationException, SQLException {
		
		String userInfoQuery = "insert into userinfo " + "(firstName, lastName, userName, password, "
				+ "birthDate) " + "values(?,?,?,?,?)";
		String contactInfoQuery = "insert into contactinfo " + "(uNameFK1, fullName, relation, phone, email) " + 
				"values (?,?,?,?,?)";
		
		String clickTrackerQuery = "INSERT INTO `asthmatrackerdb`.`clicktracker` (`uNameFK2`) VALUES (?)";//query to add row in clicktracker-Anna
		
		String mildAAPQuery = "INSERT INTO `asthmatrackerdb`.`mildaap` (`uNameFK3`) VALUES (?)";//query to add row in app-Anna
		String modAAPQuery = "INSERT INTO `asthmatrackerdb`.`moderateaap` (`uNameFK4`) VALUES (?)";
		String sevAAPQuery = "INSERT INTO `asthmatrackerdb`.`severeaap` (`uNameFK5`) VALUES (?)";
		String docInfoQuery = "INSERT INTO `asthmatrackerdb`.`doctorinfo` (`uNameFK6`) VALUES (?)";

		ResultSet keys = null;
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement insertUserInfo = conn.prepareStatement(userInfoQuery, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement insertContactInfo = conn.prepareStatement(contactInfoQuery, Statement.RETURN_GENERATED_KEYS);
	
				PreparedStatement insertClicktracker = conn.prepareStatement(clickTrackerQuery, Statement.RETURN_GENERATED_KEYS);//-Anna
				
				PreparedStatement insertMildAAP = conn.prepareStatement(mildAAPQuery, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement insertModAAP = conn.prepareStatement(modAAPQuery, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement insertSevAAP = conn.prepareStatement(sevAAPQuery, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement insertDocInfo = conn.prepareStatement(docInfoQuery, Statement.RETURN_GENERATED_KEYS);)

		{	
			// get values from the TextField controls
			String firstName, lastName, userName, password, birthDate, fullName, relation, phone, email;
			
			//goes in userinfo
			firstName = txtfirstName.getText();
			lastName = txtlastName.getText();
			userName = txtuserName.getText();
			password = txtpassword.getText();
			birthDate = txtbirthDate.getText();
			
			//goes in contactinfo
			//username goes in as well
			fullName = txtfullName.getText();
			relation = txtrelation.getText();
			phone = txtphone.getText();
			email = txtemail.getText();
			
			//capitalize first and last name
			firstName = capitalizeName(firstName);
			lastName = capitalizeName(lastName);
			fullName = capitalizeName(fullName);
			relation = capitalizeName(relation);
			
			// working on model, creating a model and setting values into it
			Account account = new Account();
			account.setfirstName(firstName);
			account.setlastName(lastName);
			account.setuserName(userName);
			account.setpassword(password);
			account.setbirthDate(birthDate);
			
			account.setfullName(fullName);
			account.setrelation(relation);
			account.setphone(phone);
			account.setemail(email);
			
			//insertUserInfo to insert into userinfo table
			insertUserInfo.setString(1, account.getfirstName());
			insertUserInfo.setString(2, account.getlastName());
			insertUserInfo.setString(3, account.getuserName());
			insertUserInfo.setString(4, account.getpassword());
			insertUserInfo.setString(5, account.getbirthDate());
			
			//insertContactInfo to insert into contactinfo table
			insertContactInfo.setString(1, account.getuserName());
			insertContactInfo.setString(2, account.getfullName());
			insertContactInfo.setString(3, account.getrelation());
			insertContactInfo.setString(4, account.getphone());
			insertContactInfo.setString(5, account.getemail());
			
			// get the number of return rows, will return 0 if successful
			int affectedRow = insertUserInfo.executeUpdate();
			int affectedRow2 = insertContactInfo.executeUpdate();
			System.out.println(affectedRow);
			System.out.println(affectedRow2);

			
			//set for clicktracker and update-Anna
			insertClicktracker.setString(1, account.getuserName());
			insertClicktracker.executeUpdate();
			
			//set for mildAAP and update-Anna
			insertMildAAP.setString(1, account.getuserName());
			insertMildAAP.executeUpdate();

			//set for modAAP and update-Anna
			insertModAAP.setString(1, account.getuserName());
			insertModAAP.executeUpdate();
			
			//set for sevAAP and update-Anna
			insertSevAAP.setString(1, account.getuserName());
			insertSevAAP.executeUpdate();
			
			//set for doctorinfo and update-Anna
			insertDocInfo.setString(1, account.getuserName());
			insertDocInfo.executeUpdate();
			
			//Once we enter the data, we need to clear our UI so as to accept  the next input
			if (affectedRow == 1) {
				
				//clears all textfields
				txtfirstName.setText(null);
				txtlastName.setText(null);
				txtuserName.setText(null);
				txtpassword.setText(null);
				txtconPassword.setText(null);
				txtbirthDate.setText(null);
				
				//takes user to personal info page
				stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
				scene = new Scene(root);
				stage.setScene(scene);
			}
			
			if (affectedRow2 == 1)
			{
				txtfullName.setText(null);
				txtrelation.setText(null);
				txtphone.setText(null);
				txtemail.setText(null);
			}
			
			lblStatus.setText(null);
				
		  //catches entered userNames that are already in the database
		} catch (MySQLIntegrityConstraintViolationException e) {
			lblErroruserName.setText("That User Name is taken.");
			tabPane.getSelectionModel().select(tab1);
			txtuserName.setText(null);
		} catch (Exception e){//catches other exceptions
			lblStatus.setText("Status: operation failed due to: " + e.getMessage());
			System.out.println("Here hs the error "+ e.getMessage());
			tabPane.getSelectionModel().select(tab1);
			e.printStackTrace();
			
		} finally {
			if (keys != null) {
				keys.close();
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//when user logs in, resets breath count to 0-Anna
		public void resetBreathZero(String username)
		{
			String zeroQuery = "update clicktracker set clicks = 0 where uNameFK2 = ?";

	    	//attempt to connect to database
			try (Connection conn = DBConfig.getConnection();
					PreparedStatement setZero = conn.prepareStatement(zeroQuery);
				)
			{
				setZero.setString(1, username);

				//execute update
				setZero.executeUpdate();

				System.out.println("error check: success! breath reset to 0! user:  " + username);

			} catch (Exception e) {

				System.out.println("Error: " + e);
			}
		}//end method
}