package edu.ycp.cs.cs496.project.mobileapp;


import edu.ycp.cs496.eduapp.model.controllers.LoginController;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
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
			
			
		} 
		else
		{
			Toast.makeText(LoginActivity.this, "Please Fill Username and Password Fields", Toast.LENGTH_LONG).show();
		}	
	}
	
	public void createAcct()
	{
		//On Click Create Account Button
		setContentView(R.layout.createuser);
		//Set activity to home activity
		// Intent intent = new Intent(Gra.this??, HomeActivty.class);
        // startActivity(intent);
		
		
	}

}
