package edu.ycp.cs496.eduapp.model.mobliecontrollers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import edu.ycp.cs496.eduapp.model.JSON;
import edu.ycp.cs496.eduapp.model.User;

public class LoginController {
	public User authenticateUser(String user, String pass) throws ClientProtocolException, URISyntaxException, IOException{
		return makeGetRequest(user,pass); 
	}
	private User makeGetRequest(String user, String pass) throws URISyntaxException, ClientProtocolException, IOException
	{
		//Create HTTP client
		HttpClient client = new DefaultHttpClient();
		
		// Construct URI
		URI uri;
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/eduapp/LoginMobile/"+user+"/"+pass, 
						    null, null);

		// Construct request
		Log.i("LoginController", "sending login HTTP request");
		HttpGet request = new HttpGet(uri);
				
		// Execute request
		HttpResponse response = client.execute(request);
		Log.i("LoginController", "received HTTP response");
		
		// Parse response
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// Copy the response body to a string
			HttpEntity entity = response.getEntity();
			
			// Parse JSON
			return JSON.getObjectMapper().readValue(entity.getContent(), User.class);
		}
		// Return null if invalid response
		return null;
	}
}
