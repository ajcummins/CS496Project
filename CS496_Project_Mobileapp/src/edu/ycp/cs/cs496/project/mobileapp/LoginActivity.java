package edu.ycp.cs.cs496.project.mobileapp;


import edu.ycp.cs496.eduapp.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setDefaultView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void authenticate()
	{
		//On Click Login Button
		
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
			Toast.makeText(LoginActivity.this, "Made a User object", Toast.LENGTH_LONG).show();
			
		} 
		else
		{
			Toast.makeText(LoginActivity.this, "Please Fill Username and Password Fields", Toast.LENGTH_LONG).show();
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
				setContentView(R.layout.login); //back button
			}
		});
        //Set activity to home activity
		// Intent intent = new Intent(Gra.this??, HomeActivty.class);
        // startActivity(intent);
		
		
	}
	
	//default view 
	 public void setDefaultView() {
		 //buttons on main page
		 Button createAccount = (Button) findViewById(R.id.createAcctBut);
		 Button login = (Button) findViewById(R.id.loginButton);
		
		 //when login button press
		 login.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						//go to login account
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
