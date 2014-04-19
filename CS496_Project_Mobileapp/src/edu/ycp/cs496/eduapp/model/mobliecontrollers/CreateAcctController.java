package edu.ycp.cs496.eduapp.model.mobliecontrollers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import edu.ycp.cs496.eduapp.model.JSON;
import edu.ycp.cs496.eduapp.model.User;

public class CreateAcctController {
	public boolean createAccount(User inUser, boolean isProf) throws ClientProtocolException, URISyntaxException, IOException{
		return makePostRequest(inUser,isProf); 
	}
	private boolean makePostRequest(User inUser, boolean isProf) throws URISyntaxException, ClientProtocolException, IOException
	{
		//create JSON object from User
		if (isProf){
			inUser.setToProfessor();
		}
		else {
			inUser.setToStudent();
		}
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, inUser);
		
		//Create HTTP client
		HttpClient client = new DefaultHttpClient();
		
		// Construct URI
		URI uri;
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/eduapp/CreateAccountMobile/", 
						    null, null);

		// Construct request
		HttpPost request = new HttpPost(uri);
				
		//add JSON object to request
		StringEntity reqEntity = new StringEntity(sw.toString());
		reqEntity.setContentType("application/json");
		request.setEntity(reqEntity);
		
		// Execute request
		HttpResponse response = client.execute(request);

		// Parse response
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return true;
		} 
		// Return null if invalid response
		return false;
	}
}
