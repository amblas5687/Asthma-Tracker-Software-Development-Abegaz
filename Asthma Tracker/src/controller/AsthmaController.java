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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;

public class AsthmaController {
	Main main = new Main();
	//accessing the controls from .fxml file for inserting purpose
	@FXML private TextField txtfirstName, txtlastName,txtuserName, txtgetUserName;
	@FXML private PasswordField  txtpassword, txtconPassword, txtgetPassword;
	@FXML private Button btnSubmit, btnSave, btngetLogIn, btnlogIn, btnCreateAccount;
	@FXML private Label lblStatus, lblErrorallFields, lblErrorfirstName, lblErrorlastName, lblErroruserName,
	lblErrorPassword, lblErrorlogIn, lblErrorgetUserName, lblErrorgetPassword;
	Stage stage;
	Scene scene;
	Parent root;
	ResultSet resultSet;

	// EventHandler +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //defines user object to enable the passing userName
	public static Account curUser = new Account();//-Anna

	//sets main in Main.java
	public void setMain(Main mainIn)
	{
		main = mainIn;
	}

	/*Click Event which checks password & username, clears if error occurs, takes to LogIn view*/
	public void ClickSubmitButton(ActionEvent event) throws SQLException {
		String firstName, lastName, userName, password, conPassword;
		int password2, conPassword2;
		boolean test = false;
		firstName = txtfirstName.getText();
		lastName = txtlastName.getText();
		userName = txtuserName.getText();
		password = txtpassword.getText();
		conPassword = txtconPassword.getText();

		int plen = txtpassword.getLength();
		int cplen = txtconPassword.getLength();

		lblErrorallFields.setText(null);
		if(firstName.equals("") || lastName.equals("") || userName.equals("") ||
				password.equals("") || conPassword.equals("")){//catches when all or any field is blank
			lblErrorallFields.setText("Please fill in all fields.");
			test = false;
		}

		lblErrorfirstName.setText(null);
		lblErrorlastName.setText(null);

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

		lblErroruserName.setText(null);

		//checks for spaces in userName
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(userName);
		boolean space = matcher.find();

		if(space){
			lblErroruserName.setText("User name cannot contain any spaces.");
			test = false;
		}

		//checks for special characters in firstName, lastName, and userName -> not allowed, except - or .
		Pattern p = Pattern.compile("[^a-z0-9-\\.]", Pattern.CASE_INSENSITIVE);//regex, can test on regex101.com -> very useful
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
					lblErrorPassword.setText("Incorrect password confirmation. Please try again.");
					test = false;
					txtpassword.setText(null);
					txtconPassword.setText(null);
				}
			} catch (NumberFormatException e){//thrown when a non-number is entered for password
				lblErrorPassword.setText("Password must consist of numbers.");
				txtpassword.setText(null);
				txtconPassword.setText(null);
				e.printStackTrace();
				test = false;
			}
		}
		//will only insert account if everything checks out
		if(test == true){
			insertAccount(event);
		}
	}

	//capitalizes first letter in name
	public String capitalizeName(String name){
			return name.substring(0,1).toUpperCase() + name.substring(1);//.toLowerCase();
	}

	//log in button clicked on CreateAccount page, takes you to LogIn page
	public void ClickgetLogInButton(ActionEvent event) throws Exception {
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}

	//takes you from the LogIn page to the CreateAccount page
	public void ClickgotoCreateAccountButton(ActionEvent event) throws Exception {
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/CreateAccount.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
	}

	/* Checks userName and password against database userNames */
	public boolean checkLogIn(String userName, String password) throws SQLException{
		boolean logIn = false;
		boolean missing_credentials = false;
		lblErrorgetUserName.setText(null);
		lblErrorgetPassword.setText(null);
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

			String query = "SELECT userName, password FROM account WHERE userName = ? AND password = ?";
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
		}
		return logIn;
	}

	//gets and sets current users
	public void setCurrentUserInfo(String curUserName) throws SQLException{

		//String currentUser = curUser;//mod by Anna
		//return curUser;//mod by Anna

		String SQLQuery = "SELECT * FROM `account` WHERE account.userName=" + "'"+curUserName+"'";
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
		}catch(SQLException ex)//try
		{
			ex.printStackTrace();
		}finally //catch
		{
			if(rs != null)
			{
				rs.close();
			}
		}//finally
	}

	//Click Log In button on Log In page, takes you to main view page
	public void ClicklogInButton(ActionEvent event) throws Exception {
		String userName, password;
		userName = txtgetUserName.getText();
		password = txtgetPassword.getText();
		lblErrorlogIn.setText(null);
		if(checkLogIn(userName, password)){
			setCurrentUserInfo(userName);

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

	/* Insert a row to Account table of Database -> populates: firstName, lastName, userName, password */
	private void insertAccount(ActionEvent event) throws MySQLIntegrityConstraintViolationException, SQLException {
		String query = "insert into account " + "(firstName,lastName,userName, password) "
				+ "values(?,?,?,?)";
		String BreathUser = "INSERT INTO `asthmatrackerdb`.`clicktracker` (`userNameFK`) VALUES (?)";//query to add row in clicktracker-Anna
		String AAPUser = "INSERT INTO `asthmatrackerdb`.`aap` (`uNameFK`) VALUES (?)";//query to add row in app-Anna
<<<<<<< HEAD
		
=======

>>>>>>> refs/remotes/origin/Christian
		ResultSet keys = null;
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement insertAccount = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    		PreparedStatement insertClicktracker = conn.prepareStatement(BreathUser, Statement.RETURN_GENERATED_KEYS);//-Anna
				PreparedStatement insertAAP = conn.prepareStatement(AAPUser, Statement.RETURN_GENERATED_KEYS)//-Anna
<<<<<<< HEAD
		    ) 
=======
		    )
>>>>>>> refs/remotes/origin/Christian
		{

			// get values from the TextField controls
			String firstName, lastName, userName, password;
			firstName = txtfirstName.getText();
			lastName = txtlastName.getText();
			userName = txtuserName.getText();
			password = txtpassword.getText();

			//capitalize first and last name
			firstName = capitalizeName(firstName);
			lastName = capitalizeName(lastName);

			// working on model, creating a model and setting values into it
			Account account = new Account();
			account.setfirstName(firstName);
			account.setlastName(lastName);
			account.setuserName(userName);
			account.setpassword(password);

			//insertAccount object of PreparedStatement that passes data from the model to the database
			insertAccount.setString(1, account.getfirstName());
			insertAccount.setString(2, account.getlastName());
			insertAccount.setString(3, account.getuserName());
			insertAccount.setString(4, account.getpassword());

			// get the number of return rows, will return 0 if successful
			int affectedRow = insertAccount.executeUpdate();
			System.out.println(affectedRow);
			
			//set for clicktracker and update-Anna
			insertClicktracker.setString(1, account.getuserName());
			insertClicktracker.executeUpdate();
			
			//set for app and update-Anna
			insertAAP.setString(1, account.getuserName());
			insertAAP.executeUpdate();

			//set for clicktracker and update-Anna
			insertClicktracker.setString(1, account.getuserName());
			insertClicktracker.executeUpdate();

			//set for app and update-Anna
			insertAAP.setString(1, account.getuserName());
			insertAAP.executeUpdate();

			//Once we enter the data, we need to clear our UI so as to accept  the next input
			if (affectedRow == 1) {

				//clears all textfields
				txtfirstName.setText(null);
				txtlastName.setText(null);
				txtuserName.setText(null);
				txtpassword.setText(null);
				txtconPassword.setText(null);

				//takes user to personal info page
				stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
				scene = new Scene(root);
				stage.setScene(scene);
				}
		  //catches entered userNames that are already in the database
		} catch (MySQLIntegrityConstraintViolationException e) {
			lblErroruserName.setText("That User Name is taken. Please try again.");
			txtuserName.setText(null);
		} catch (Exception e){//catches other exceptions
			lblStatus.setText("Status: operation failed due to: " + e.getMessage());
			System.out.println("Here hs the error "+ e.getMessage());

		} finally {
			if (keys != null) {
				keys.close();
			}
		}
	}

}
