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

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.JSON;

public class GetMyCourseList {
	public Course[] getMyCourseList(String inUsername) throws ClientProtocolException, URISyntaxException, IOException{
		return makeGetRequest(inUsername); 
	}
	private Course[] makeGetRequest(String inUsername) throws URISyntaxException, ClientProtocolException, IOException
	{
		//Create HTTP client
		HttpClient client = new DefaultHttpClient();
		
		// Construct URI
		URI uri;
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/MyCourseListMobile/"+inUsername, 
						    null, null);

		// Construct request
		Log.i("GetMyCourseList", "sending login HTTP request");
		HttpGet request = new HttpGet(uri);
				
		// Execute request
		HttpResponse response = client.execute(request);
		
		// Parse response
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// Copy the response body to a string
			HttpEntity entity = response.getEntity();
			
			// Parse JSON
			Log.i("GetMyCourseList", "Valid response");
			return JSON.getObjectMapper().readValue(entity.getContent(), Course[].class);
		}
		// Return null if invalid response
		Log.i("GetMyCourseList", "invalid response");
		return null;
	}
}
