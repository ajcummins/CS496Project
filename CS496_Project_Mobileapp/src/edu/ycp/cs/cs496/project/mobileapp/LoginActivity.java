package edu.ycp.cs.cs496.project.mobileapp;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import edu.ycp.cs496.eduapp.model.User;
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
				Toast.makeText(LoginActivity.this,"not a valid user/password"+ passText , Toast.LENGTH_LONG).show();
			}
		} 	
	}

	public void createAcct()
	{
		//On Click submit Button
		setContentView(R.layout.createuser);
		Button submitCreateAcc = (Button) findViewById(R.id.submitCreateUser);
		submitCreateAcc.setOnClickListener(new View.OnClickListener() {
			//on click
			@Override
			public void onClick(View v) {
				//go to the users home
				setDefaultView(); //back button
			}
		});
		//Set activity to home activity
		// Intent intent = new Intent(Gra.this??, HomeActivty.class);
		// startActivity(intent);


	}
	
	//Home view of user
	public void setHomeView(User user){
		setContentView(R.layout.home);
		TextView welcomeMsg = (TextView) findViewById(R.id.WelcomeMsg);
		welcomeMsg.setText("Welcome "+user.getFName()+" "+user.getLName());
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