package edu.ycp.cs.cs496.project.mobileapp;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.mobliecontrollers.CreateAcctController;
import edu.ycp.cs496.eduapp.model.mobliecontrollers.GetMainCourseList;
import edu.ycp.cs496.eduapp.model.mobliecontrollers.GetMyCourseList;
import edu.ycp.cs496.eduapp.model.mobliecontrollers.LoginController;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDefaultView();
	}

	//handlers for get my course list
	public void getMyCourse(List<String> CourseIds) throws URISyntaxException, ClientProtocolException,
	IOException, ParserConfigurationException, SAXException{
		GetMyCourseList controller = new GetMyCourseList();
		if (controller.getMyCourseList(CourseIds) != null){
			//display my course list
		}
	}

	//handlers for get main course list
	public void getMainCourse() throws URISyntaxException, ClientProtocolException,
	IOException, ParserConfigurationException, SAXException{
		GetMainCourseList controller = new GetMainCourseList();
		if (controller.getMainCourseList() != null){
			//display main course list
		}
	}

	//authenticate and get user
	public User getUserAccount(String user, String pass) throws URISyntaxException, ClientProtocolException,
	IOException, ParserConfigurationException, SAXException{
		LoginController controller = new LoginController();
		User mainUser = controller.authenticateUser(user, pass);
		return mainUser;
	}
	
	//creating user
	public boolean createUserAccount(User user,boolean isProf)throws URISyntaxException, ClientProtocolException,
	IOException, ParserConfigurationException, SAXException{
		CreateAcctController controller = new CreateAcctController();
		//create User in Database
		return controller.createAccount(user, isProf);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void authenticate() throws ClientProtocolException, URISyntaxException, IOException, ParserConfigurationException, SAXException
	{
		//Get Username/Password
		EditText userTextInput = (EditText) findViewById(R.id.loginUsernameTxtBox);
		EditText passTextInput = (EditText) findViewById(R.id.loginPassTxtBox);
		//Check if Username/Password isn't empty | Error if it is
		String userText = userTextInput.getText().toString();
		String passText = passTextInput.getText().toString();
		if(userText.equals("") || passText.equals("") || userText != null || passText != null)
		{
			/*
			//Call controller and authenticate
			LoginController controller = new LoginController();
			boolean success = controller.authenticateUser(userText, passText);
			//If Successful goto home
			if(success)
			{
				Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
				setContentView(R.layout.home);
				//Set activity to home activity
				// Intent intent = new Intent(Gra.this??, HomeActivty.class);
		        // startActivity(intent);   

			}
			//Else Error
			else
			{
				Toast.makeText(LoginActivity.this, "Username or Password did not match", Toast.LENGTH_LONG).show();
			}
			 */
			User user = new User();
			//get the user if exist else it is a null
			user = getUserAccount(userText, passText);
			if (user != null){
				//Toast.makeText(LoginActivity.this,"this is a user:"+ user.getUsername()+" "+ user.getPassword() , Toast.LENGTH_LONG).show();
				//go to Home
				setHomeView(user);
			}
			else {
				//show error of fail auth
				Toast.makeText(LoginActivity.this,"not a valid user/password" , Toast.LENGTH_LONG).show();
			}
		} 	
	}

	//create account page
	public void createAcct()throws ClientProtocolException, URISyntaxException, IOException, ParserConfigurationException, SAXException
	{
		//On Click submit Button
		setContentView(R.layout.createuser);
		Button submitCreateAcc = (Button) findViewById(R.id.submitCreateUser);
		Button backToLogin = (Button) findViewById(R.id.backButton); 
		
		submitCreateAcc.setOnClickListener(new View.OnClickListener() {
			//on click
			@Override
			public void onClick(View v) {
				try {
					//get user parameters
					EditText userName = (EditText) findViewById(R.id.createUserNameTxtBox);
					EditText password = (EditText) findViewById(R.id.createPasswordTxtBox);
					EditText checkPassword = (EditText) findViewById(R.id.confirmPassTxtBox);
					EditText fName = (EditText) findViewById(R.id.createFirstNameTxtBox);
					EditText lName = (EditText) findViewById(R.id.createLastNameTxtBox);
					
					String userNameStr = userName.getText().toString();
					String passwordStr = password.getText().toString();
					String checkPasswordStr = checkPassword.getText().toString();
					String fNameStr = fName.getText().toString();
					String lNameStr = lName.getText().toString();

					User newUser = new User(userNameStr,passwordStr,fNameStr,lNameStr);
					//if fields are empty
					if (userNameStr.isEmpty() || passwordStr.isEmpty() || checkPasswordStr.isEmpty() || fNameStr.isEmpty() || lNameStr.isEmpty()){
						Toast.makeText(LoginActivity.this, "Please fill all the text boxes",Toast.LENGTH_LONG).show();
					}
					//if password does not match check password
					else if (!passwordStr.equals(checkPasswordStr)){
						Toast.makeText(LoginActivity.this, "Your Password does not equal the Confirm Password",Toast.LENGTH_LONG).show();
					}
					//go to newUser home page if created user is successful
					else if (createUserAccount(newUser,false) == true){
						//go to login page
						setDefaultView();
						Toast.makeText(LoginActivity.this, "Account has been created, Please login",Toast.LENGTH_LONG).show();
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//button to login page
		backToLogin.setOnClickListener(new View.OnClickListener() {
			//on click
			@Override
			public void onClick(View v) {
				//login page
				setDefaultView();
			}
		});

	}

	//Home page of user
	public void setHomeView(User user){
		setContentView(R.layout.home);
		//welcome message to user shows first and last name
		TextView welcomeMsg = (TextView) findViewById(R.id.WelcomeMsg);
		welcomeMsg.setText("Welcome "+user.getFName()+" "+user.getLName());

		//buttons on home page
		Button logout = (Button) findViewById(R.id.logout);
		//when logout button press
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					//go to login page
					setDefaultView();
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	//default view 
	public void setDefaultView() {
		//login page
		setContentView(R.layout.login);

		//buttons on main page
		Button createAccount = (Button) findViewById(R.id.createAcctBut);
		Button login = (Button) findViewById(R.id.loginButton);

		//when login button press
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					//go to home user account
					authenticate();
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		//when createAccount button press
		createAccount.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					//go to create account
					createAcct();
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}
}