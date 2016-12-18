//Fixed queries 12/1/16-Anna
//Fixed errors with multiple updates 12/2/16-Anna
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import controller.AsthmaController;

public class UpdateController {
	Main main = new Main();
	//-----------------------------------------------------------------------------------------------------------------
	//accessing the controls from .fxml file for updating purpose
	@FXML private TextField txtfirstName, txtlastName, txtbirthDate, txtfullName, txtrelation, txtphone, txtemail;
	@FXML private PasswordField  txtCurrentPass, txtNewPass, txtConfirmPass;
	@FXML private Button btnUpdate1, btnUpdate2, btnUpdate3, btngetMain1, btngetMain2, btngetMain3;
	@FXML private Label lblStatus, lblErrorallFields1, lblErrorallFields2, lblErrorfirstName, lblErrorlastName,  
	lblErrorPassword, lblUsername, lblErrorbirthDate,  lblErrorfullName, lblErrorrelation, lblErrorphone, lblErroremail;
	Stage stage;
	Scene scene;
	Parent root;
	@FXML TabPane tabPane;
	@FXML Tab tab1, tab2, tab3;
	//current user
    Account activeUser = AsthmaController.curUser;//Anna
    int ran = 0;
	// EventHandler +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //sets main in Main.java 
	public void setMain(Main mainIn)
	{
		main = mainIn;
	}
	//-----------------------------------------------------------------------------------------------------------------
	//Runs methods at scene start
    @FXML
    void initialize(){
    	setName();
    }

    //Displays welcome message, and makes it editable in code. Watch method called for changes.
    public void setName() {
    	lblUsername.setText(AsthmaController.curUser.getuserName());
    }
    //-----------------------------------------------------------------------------------------------------------------
  	/*Error checks textfields, if passed they are updated*/
  	public void ClickUpdateButton(ActionEvent event) throws Exception {
  		System.out.println("ran" + ran++);
  		String firstName, lastName, password, conPassword, curPassword, birthDate, fullName, relation, phone, email;
  		int count = 0;
  		int counter = 0;
  		int counting = 0;
  		
  		lblErrorallFields1.setText(null);
  		lblErrorallFields2.setText(null);
		
		firstName = txtfirstName.getText();
		lastName = txtlastName.getText();
		password = txtNewPass.getText();
		conPassword = txtConfirmPass.getText();
		curPassword = txtCurrentPass.getText();
		birthDate = txtbirthDate.getText();
		fullName = txtfullName.getText();
		relation = txtrelation.getText();
		phone = txtphone.getText();
		email = txtemail.getText();
		
		try{
			System.out.println("entered try1");
			lblErrorfirstName.setText(null);
			if(!firstName.equals("")){
				System.out.println("reached firstname");
				if(checkFirst(firstName) == true){
					firstName = capitalizeName(firstName);
					updateFirst(firstName);
					txtfirstName.setText(null);
					count += 1;
					if(count > 0){
						lblErrorallFields1.setText("Your first name has been updated.");
					}
				}else{
					System.out.println("firstName update failed.");
				}
			}
		} catch(Exception e){
			e.getMessage();
		}//try 1
		
		try{
			System.out.println("entered try2");
			lblErrorlastName.setText(null);
			if(!lastName.equals("")){
				System.out.println("reached lastname");
				if(checkLast(lastName) == true){
					lastName = capitalizeName(lastName);
					updateLast(lastName);
					System.out.println("line 104");
					txtlastName.setText(null);
					count += 1;
						if(count > 0){
						lblErrorallFields1.setText("Your last name has been updated.");
						}
				}else{
					System.out.println("lastName update failed.");
				}
			}
		} catch(Exception e){
			e.getMessage();
		}//try 2
			
		try{
			System.out.println("entered try3");
			lblErrorbirthDate.setText(null);
			if(!birthDate.equals("")){
				if(checkbirthDate(birthDate) == true){
					updatebirthDate(birthDate);
					txtbirthDate.setText(null);
					count += 1;
						if(count > 0){
						lblErrorallFields1.setText("Your birth date has been updated.");
					}
				}else{
					System.out.println("birthDate update failed.");
				}
			}
		} catch(Exception e){
			e.getMessage();
		}//try 3
			
			try{
				System.out.println("entered try4");
			lblErrorPassword.setText(null);
			if(!curPassword.equals("") || !password.equals("") || !conPassword.equals("")){
				if(!curPassword.equals("") && !password.equals("") && !conPassword.equals("")){
					if(checkcurrentPassword(curPassword) == true){
						if(checkPassword(password, conPassword) == true){
							updatePassword(password);
							txtCurrentPass.setText(null);
							txtNewPass.setText(null);
							txtConfirmPass.setText(null);
							counter += 1;
						}else{
							System.out.println("password update failed.");
						}
					}else{
						lblErrorPassword.setText("Incorrect current password.");
					}
				}else{
					lblErrorPassword.setText("Please fill in all password fields.");
				}
			}
			} catch(Exception e){
				e.getMessage();
			}//try4
			
			try{
				System.out.println("entered try5");
			lblErrorfullName.setText(null);
			if(!fullName.equals("")){
				if(checkfullName(fullName) == true){
					fullName = capitalizeName(fullName);
					updatefullName(fullName);
					txtfullName.setText(null);
					counting += 1;
						if(counting > 0){
							lblErrorallFields2.setText("Your contact full name has been updated.");
						}
				}else{
					System.out.println("fullName update failed.");
				}
			}
			} catch(Exception e){
				e.getMessage();
			}//try5
			
			try{
				System.out.println("entered try6");
			lblErrorrelation.setText(null);
			if(!relation.equals("")){
				if(checkrelation(relation) == true){
					relation = capitalizeName(relation);
					updaterelation(relation);
					txtrelation.setText(null);
					counting += 1;
						if(counting > 0){
							lblErrorallFields2.setText("Your contact relation has been updated.");
						}
				}else{
					System.out.println("relation update failed.");
				}
			}
			} catch(Exception e){
				e.getMessage();
			}//try6
			
			try{
				System.out.println("entered try7");
			lblErrorphone.setText(null);
			if(!phone.equals("")){
				if(checkphone(phone) == true){
					updatephone(phone);
					txtphone.setText(null);
					counting += 1;
						if(counting > 0){
							lblErrorallFields2.setText("Your contact phone number has been updated.");
						}
				}else{
					System.out.println("phone update failed.");
				}
			}
			} catch(Exception e){
				e.getMessage();
			}//try7
			
			try{
				System.out.println("entered try8");
			lblErroremail.setText(null);
			if(!email.equals("")){
				if(checkemail(email) == true){
					updateemail(email);
					txtemail.setText(null);
					counting += 1;
						if(counting > 0){
							lblErrorallFields2.setText("Your contact email has been updated.");
						}
				}else{
					System.out.println("email update failed.");
				}
			}
			} catch(Exception e){
				e.getMessage();
			}//try8
	
		System.out.println("line 249");
  	}
  	//-----------------------------------------------------------------------------------------------------------------
  	//capitalizes first letter in name
  	public String capitalizeName(String name){
  			return name.substring(0,1).toUpperCase() + name.substring(1);//.toLowerCase();
  	}
  	//-----------------------------------------------------------------------------------------------------------------
  	//Error checks firstName
  	public boolean checkFirst(String firstName){
  		boolean test = true;
  		lblErrorfirstName.setText(null);
  		//checks for numbers
  		Pattern p = Pattern.compile("([0-9])", Pattern.CASE_INSENSITIVE);
  		Matcher firstN = p.matcher(firstName);
		boolean firNam = firstN.find();
		if(firNam){
			lblErrorfirstName.setText("First name cannot contain numbers.");
			test = false;
		}
		
		//checks for special characters
		Pattern p2 = Pattern.compile("[^a-zA-Z0-9-\\.]");//regex, can test on regex101.com -> very useful
		Matcher fName = p2.matcher(firstName);
		boolean fn = fName.find();
		
		if(fn){//fix for - and .
			lblErrorfirstName.setText("Invalid first name.Cannot use special characters.");
			test = false;
		}
		return test;
  	}
  	//-----------------------------------------------------------------------------------------------------------------
  	/* Updates firstName in userinfo table of Database -> populates: firstName */
	private void updateFirst(String firstName) throws SQLException {
		Account account = new Account();
		account.setfirstName(firstName);
		String query = "UPDATE userinfo SET firstName = ? WHERE userName = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updateFirst = conn.prepareStatement(query);)
		{
			updateFirst.setString(1, account.getfirstName());
			updateFirst.setString(2, activeUser.getuserName());//Anna
			
			//execute the update
			updateFirst.executeUpdate();

			System.out.println("Update for firstName was successful.");

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
  	//Error checks lastName
  	public boolean checkLast(String lastName){
  		boolean test = true;
  		lblErrorlastName.setText(null);
  		//checks for numbers
  		Pattern p = Pattern.compile("([0-9])", Pattern.CASE_INSENSITIVE);
  		Matcher lastN = p.matcher(lastName);
		boolean lasNam = lastN.find();
		if(lasNam){
			lblErrorlastName.setText("Last name cannot contain numbers.");
			test = false;
		}
		
		//checks for special characters
		Pattern p2 = Pattern.compile("[^a-zA-Z0-9-\\.]");//regex, can test on regex101.com -> very useful
		Matcher lName = p2.matcher(lastName);
		boolean ln = lName.find();
		
		if(ln){//fix for - and .
			lblErrorlastName.setText("Invalid last name.Cannot use special characters.");
			test = false;
		}
		return test;
  	}
  	//-----------------------------------------------------------------------------------------------------------------
  	/* Updates lastName in userinfo table of Database -> populates: lastName */
	private void updateLast(String lastName) throws SQLException {
		Account account = new Account();
		account.setlastName(lastName);
		String query = "UPDATE userinfo SET lastName = ? WHERE userName = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updateLast = conn.prepareStatement(query);)
		{
			updateLast.setString(1, account.getlastName());
			updateLast.setString(2, activeUser.getuserName());//Anna
			//execute the update
			updateLast.executeUpdate();

			System.out.println("Update for lastName was successful.");

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//Error checks birthDate dates and format
	public boolean checkbirthDate(String birthDate){
		boolean test = true;
		
		//error check for birthDate
		Pattern p = Pattern.compile("[^0-9/]");
		Matcher bDate = p.matcher(birthDate);
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
		}else if(count == 0 || count == 1 && !birthDate.equals("")){
			lblErrorbirthDate.setText("Date format required.");
			test = false;
		}else{
			String[] parts = string.split("/");
			String part1 = parts[0]; //charAt(0) & charAr(1)
			String part2 = parts[1];
			String part3 = parts[2];
			int month = Integer.parseInt(part1);
			int day = Integer.parseInt(part2);
			int year = Integer.parseInt(part3);
			//System.out.println(month+":"+day+":"+year);//testing
			if(birthDate.charAt(2) != '/'|| birthDate.charAt(5) != '/'|| birthDate.length() != 10 && 
				!birthDate.equals("")){
					lblErrorbirthDate.setText("Incorrect date format.");
					test = false;
			}else if(month > 12 || month < 1 || day > 31 || day < 1 || year > 2017 || year < 1920){
				lblErrorbirthDate.setText("Invalid date.");
				test = false;
			}else if(month == 2 && day > 29){
				lblErrorbirthDate.setText("Invalid day for February.");
				test = false;
			}else if(month == 4 || month == 6 || month == 9 || month == 11 && day > 30){
				lblErrorbirthDate.setText("Invalid day for that month.");
				test = false;
			}
		}
		if(birthDate.equals("")){
			lblErrorbirthDate.setText(null);
			test = false;
		}
		return test;
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Updates birthDate in userinfo table of Database -> populates: birthDate */
	private void updatebirthDate(String birthDate) throws SQLException {	
		Account account = new Account();
		account.setbirthDate(birthDate);
		String query = "UPDATE userinfo SET birthDate = ? WHERE userName = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updatebirthDate = conn.prepareStatement(query);)
		{
			updatebirthDate.setString(1, account.getbirthDate());
			updatebirthDate.setString(2,activeUser.getuserName());//Anna
			
			//execute the update
			updatebirthDate.executeUpdate();

			System.out.println("Update for birthDate was successful.");

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//Error checks password and conPassword and makes sure they match
	public boolean checkPassword(String password, String conPassword){
		boolean test = true;
		int plen = txtNewPass.getLength();
		int cplen = txtConfirmPass.getLength();
		int password2, conPassword2;
		
		//won't run password checks if null
		if(plen > 0 && cplen > 0){
			try{
				lblErrorPassword.setText(null);
				password2 = Integer.parseInt(txtNewPass.getText());
				conPassword2 = Integer.parseInt(txtConfirmPass.getText());
				
				if(plen != 4 || cplen != 4){//catches when field is less than or greater than 4 digits
					lblErrorPassword.setText("Password must be 4 digits long.");
					test = false;
					txtNewPass.setText(null);
					txtConfirmPass.setText(null);
				}else if(password2 == conPassword2){//will update account
					test = true;
				}else{//password does not equal confirmation password
					lblErrorPassword.setText("Incorrect password confirmation.");
					test = false;
					txtNewPass.setText(null);
					txtConfirmPass.setText(null);
				}
			 }catch (NumberFormatException e){//thrown when a non-number is entered for password
				lblErrorPassword.setText("Password must consist of numbers.");
				txtNewPass.setText(null);
				txtConfirmPass.setText(null);
				e.printStackTrace();
				test = false;
			 }
		}
		return test;
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Checks password against database passwords */
	public boolean checkcurrentPassword(String curPassword) throws SQLException{
		boolean pass = false;
		
		try 
		{
			Connection conn = DBConfig.getConnection();
			
			String query = "SELECT password FROM userinfo WHERE password = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, curPassword);
			ResultSet rs = ps.executeQuery();
	
		    while(rs.next()){
			    String checkPass = rs.getString("password");
			    if(checkPass != null){//checks userName and password from database with local variables
			    	if(checkPass.equals(curPassword)){
			    		pass = true;
			    	}else{
			    		lblErrorPassword.setText("Incorrect current password.");
			    		pass = false;
			    	}
			    }else{
			    	lblErrorPassword.setText("Null password.");
			    }
		    }

		    conn.close();  
		}catch (SQLException ex) {
			DBConfig.displayException(ex);
		}
		return pass;
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Updates password in userinfo table of Database -> populates: password */
	private void updatePassword(String password) throws SQLException {	
		Account account = new Account();
		account.setpassword(password);
		String query = "UPDATE userinfo SET password = ? WHERE userName = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
			PreparedStatement updatePassword = conn.prepareStatement(query);)
		{
			updatePassword.setString(1, account.getpassword());
			updatePassword.setString(2,activeUser.getuserName());//Anna
			//execute the update
			updatePassword.executeUpdate();

			System.out.println("Update for password was successful.");

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//Error checks fullName for special characters and numbers
	public boolean checkfullName(String fullName){
		boolean test = true;
		
		//checks for numbers
  		Pattern p = Pattern.compile("([0-9])", Pattern.CASE_INSENSITIVE);
  		Matcher fullN = p.matcher(fullName);
		boolean fulNam = fullN.find();
		if(fulNam){
			lblErrorfullName.setText("Full name cannot contain numbers.");
			test = false;
		}
		
		//checks for to make sure full name only consists of letters, ., -, and spaces
		Pattern p2 = Pattern.compile("[^a-zA-Z0-9-\\.\\s]");
		Matcher fName = p2.matcher(fullName);
		boolean fun = fName.find();
		if(fun){
			lblErrorfullName.setText("Invalid full name. Cannot use special characters.");
			test = false;
		}
		return test;
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Updates fullName in contactinfo table of Database -> populates: fullName */
	private void updatefullName(String fullName) throws SQLException {	
		Account account = new Account();
		account.setfullName(fullName);
		String query = "UPDATE contactinfo SET fullName = ? WHERE uNameFK1 = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updatefullName = conn.prepareStatement(query);)
		{
			updatefullName.setString(1, account.getfullName());
			updatefullName.setString(2,activeUser.getuserName());//Anna
			//execute the update
			updatefullName.executeUpdate();

			System.out.println("Update for fullName was successful.");

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
	} 
	//-----------------------------------------------------------------------------------------------------------------
	//Error checks relation for special characters and numbers, separately
	public boolean checkrelation(String relation){
		boolean test = true;
		
		//checks for numbers
  		Pattern p = Pattern.compile("([0-9])", Pattern.CASE_INSENSITIVE);
  		Matcher relate = p.matcher(relation);
		boolean rela = relate.find();
		if(rela){
			lblErrorrelation.setText("Relationship cannot contain numbers.");
			test = false;
		}
		
		//checks to make sure relation only contains letters, -, and spaces
		Pattern p2 = Pattern.compile("[^a-zA-Z0-9-\\s]");
		Matcher relat = p2.matcher(relation);
		boolean rel = relat.find();
		if(rel){
			lblErrorrelation.setText("Invalid relation. Cannot use special characters.");
			test = false;
		}
		return test;
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Updates relation in contactinfo table of Database -> populates: relation */
	private void updaterelation(String relation) throws SQLException {	
		Account account = new Account();
		account.setrelation(relation);
		String query = "UPDATE contactinfo SET relation = ? WHERE uNameFK1 = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updaterelation = conn.prepareStatement(query);)
		{
			updaterelation.setString(1, account.getrelation());
			updaterelation.setString(2,activeUser.getuserName());//Anna
			//execute the update
			updaterelation.executeUpdate();

			System.out.println("Update for relation was successful.");

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//Error checks phone for anything other than numbers and -
	public boolean checkphone(String phone){
		boolean test = true;
		
		//checks to make sure phone number only consists of numbers and -
		Pattern p = Pattern.compile("[^0-9-]");
		Matcher phon = p.matcher(phone);
		boolean pho = phon.find();
		if(pho){
			lblErrorphone.setText("Invalid phone number. Phone number must consist of numbers only.");
			test = false;
		}else if(phone.charAt(3) != '-' || phone.charAt(7) != '-') {
			lblErrorphone.setText("Incorrect phone number format.");
			test = false;
		}else if(phone.length() != 12){
			lblErrorphone.setText("Incorrect phone number length.");
			test = false;
		}
		return test;
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Updates phone in contactinfo table of Database -> populates: phone */
	private void updatephone(String phone) throws SQLException {	
		Account account = new Account();
		account.setphone(phone);
		String query = "UPDATE contactinfo SET phone = ? WHERE uNameFK1 = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updatephone = conn.prepareStatement(query);)
		{
			updatephone.setString(1, account.getphone());
			updatephone.setString(2,activeUser.getuserName());//Anna
			//execute the update
			updatephone.executeUpdate();

			System.out.println("Update for phone was successful.");

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//Error checks email for at least one @ and . plus length
	public boolean checkemail(String email){
		boolean test = true;
		
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
		if(count < 1 || counter < 1 || s.length() < 5){
			lblErroremail.setText("Invalid email address.");
			test = false;
		}else if(s.length() > 30){
			lblErroremail.setText("Email is too long. Cannot exceed 30 characters.");
			test = false;
		}
		return test;
	}
	//-----------------------------------------------------------------------------------------------------------------
	/* Updates email in contactinfo table of Database -> populates: email */
	private void updateemail(String email) throws SQLException {	
		Account account = new Account();
		account.setemail(email);
		String query = "UPDATE contactinfo SET email = ? WHERE uNameFK1 = ?";
		//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updateemail = conn.prepareStatement(query);)
		{
			updateemail.setString(1, account.getemail());
			updateemail.setString(2,activeUser.getuserName());//Anna
			//execute the update
			updateemail.executeUpdate();

			System.out.println("Update for email was successful.");

		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	//log in button clicked on CreateAccount page, takes you to LogIn page
	public void ClickgetLogInButton(ActionEvent event) throws Exception {
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}
	//-----------------------------------------------------------------------------------------------------------------
}
