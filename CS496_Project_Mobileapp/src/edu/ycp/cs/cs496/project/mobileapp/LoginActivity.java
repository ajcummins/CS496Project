package edu.ycp.cs.cs496.project.mobileapp;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.MeetingTime;
import edu.ycp.cs496.eduapp.model.Notification;
import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.mobliecontrollers.CreateAcctController;
import edu.ycp.cs496.eduapp.model.mobliecontrollers.GetMainCourseList;
import edu.ycp.cs496.eduapp.model.mobliecontrollers.GetMyCourseList;
import edu.ycp.cs496.eduapp.model.mobliecontrollers.LoginController;

public class LoginActivity extends Activity {

	//where the username and password are stored
	String FileName = "UserStorage";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
					new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		setDefaultView();
		//try to login auto
		try {
			String userAndPass = getLastUser();
			//go to login sceen
			if (userAndPass == null || userAndPass.equals("")){
				setDefaultView();
			}
			//go to home page with the last user
			else {
				int locationOfSlash = userAndPass.indexOf("/");
				String user = userAndPass.substring(0, locationOfSlash);
				String pass = userAndPass.substring(locationOfSlash+1);
				User lastUser = getUserAccount(user, pass);
				if (lastUser != null){
					setHomeView(lastUser);
				}
				//go to login if last user is nonsense
				else {
					setDefaultView();
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	//handlers for get my course list
	public List <Course> getMyCourse(User user) throws URISyntaxException, ClientProtocolException,
	IOException, ParserConfigurationException, SAXException{

		GetMyCourseList controller = new GetMyCourseList();
		List <Course> userCourses = controller.getMyCourseList(user.getUsername());
		//show list of courses 
		if (userCourses.size() > 0){
			return userCourses;
		}
		return null;
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

	//if checkbox is click
	public void rememberLogin(View v) throws IOException {
		//get username and password
		EditText userTextInput = (EditText) findViewById(R.id.loginUsernameTxtBox);
		EditText passTextInput = (EditText) findViewById(R.id.loginPassTxtBox);
		String userText = userTextInput.getText().toString();
		String passText = passTextInput.getText().toString();
		String fileStuff = "";
		// Check if box is set or not
		if (((CheckBox) v).isChecked()) {
			// store the username in a file
			fileStuff = userText + "/" + passText;
		} else {
			// store nothing in the file
			fileStuff = "";
		}
		FileOutputStream fileOutStream = openFileOutput (FileName, Context.MODE_PRIVATE);
		fileOutStream.write(fileStuff.getBytes());
		fileOutStream.close();
	}

	//get the username 
	public String getLastUser() throws URISyntaxException, ParserConfigurationException, SAXException {
		try {
			//obtain the user and pass from last session
			FileInputStream fileInStream =  openFileInput(FileName);
			InputStreamReader isr = new InputStreamReader(fileInStream); 
			BufferedReader reader = new BufferedReader(isr);
			String userAndPass = reader.readLine();
			fileInStream.read();
			fileInStream.close();
			return userAndPass;
		} catch (IOException e) {
			//return null if no last User
			return null;
		}
	}
	//login
	public void authenticate() throws ClientProtocolException, URISyntaxException, IOException, ParserConfigurationException, SAXException
	{
		//Get Username/Password
		EditText userTextInput = (EditText) findViewById(R.id.loginUsernameTxtBox);
		EditText passTextInput = (EditText) findViewById(R.id.loginPassTxtBox);
		//Check if Username/Password isn't empty | Error if it is
		String userText = userTextInput.getText().toString();
		String passText = passTextInput.getText().toString();

		//login with the textboxes
		if(userText.equals("") || passText.equals("") || userText != null || passText != null)
		{
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
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
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
	public void setHomeView(final User user) throws URISyntaxException, ClientProtocolException,
	IOException, ParserConfigurationException, SAXException{
		setContentView(R.layout.home);
		//welcome message to user shows first and last name
		TextView welcomeMsg = (TextView) findViewById(R.id.WelcomeMsg);
		welcomeMsg.setText("Welcome "+user.getLName()+", "+user.getFName());

		//buttons on home page
		Button logout = (Button) findViewById(R.id.logout);
		Button myCourse = (Button) findViewById(R.id.courseBackButton);

		//courses
		final List <Course> userCourses = getMyCourse(user);

		//when logout button press
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//go to login page
				try {
					setDefaultView();
					//remove the user and pass from the save file
					String fileStuff = "";
					FileOutputStream fileOutStream = openFileOutput (FileName, Context.MODE_PRIVATE);
					fileOutStream.write(fileStuff.getBytes());
					fileOutStream.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//when my course button press
		myCourse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//go list of course
					if (userCourses != null){
						displayCoursesView(user, userCourses);
					} 
					else {
						Toast.makeText(LoginActivity.this, "There are no Courses", Toast.LENGTH_LONG).show();	
					}
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

	// Method for displaying My Course list
	public void displayCoursesView(final User user,final List <Course> myCourses) {
		// Create Linear layout
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);


		// Add back button
		Button backButton = new Button(this);
		backButton.setText("Back to Home");
		backButton.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		backButton.setX(20);
		backButton.setY(20);

		//back button onClickListener
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					setHomeView(user);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// Add button to layout
		layout.addView(backButton);

		//Add ListView with course
		final Course[] myCourseAsArray = myCourses.toArray(new Course[myCourses.size()]);
		String[] listArray = new String[myCourseAsArray.length];
		for (int i = 0; i < myCourseAsArray.length;i++){
			//display course title
			listArray[i] = myCourseAsArray[i].getCode()+" - "+myCourseAsArray[i].getTitle();
		}
		ListAdapter la = new ArrayAdapter<String>(this, R.layout.courselist, listArray);
		ListView lv = new ListView(this);
		lv.setAdapter(la);
		layout.addView(lv);

		lv.setTextFilterEnabled(true);
		// Register click callback for the course
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//go to the single course view
				displaySingleCourseView(user,myCourses,arg2);
			}
		});

		// Make inventory view visible
		setContentView(layout,llp);    	
	}

	//return a string of days
	public String getDays(MeetingTime meetingTimes){
		//get the days
		boolean[] days = new boolean[7];
		days = meetingTimes.getDays();
		int classDaysSize = 0;
		String[] classDay = new String[7];
		String classDaysTotal = "";
		//get the number of days that are class days
		for (int i = 0; i < days.length;i++){
			if (days[i]==true){
				classDaysSize++;
			}
			classDay[i] = "";
		}
		//put classdays into a string
		//SU,MO,TU,WE,TH,FR,SA
		if (days[0] == true) 
			classDay[0] = "SU";
		if (days[1] == true)
			classDay[1] = "MO";
		if (days[2] == true)
			classDay[2] = "TU";
		if (days[3] == true)
			classDay[3] = "WE";
		if (days[4] == true)
			classDay[4] = "TH";
		if (days[5] == true)
			classDay[5] = "FR";
		if (days[6] == true){
			classDay[6] = "SA";
		}
		for (int i = 0; i<7; i++){
			if (days[i]==true){
				classDaysTotal = classDaysTotal+","+classDay[i];
			}
		}
		//remove first comma
		classDaysTotal = classDaysTotal.substring(1, classDaysTotal.length());
		return classDaysTotal;
	}
	//show only the single course
	public void displaySingleCourseView(final User user,final List <Course> myCourses,final int number) {

		// Create layout
		setContentView(R.layout.singlecourselist);

		//buttons
		Button backButton = (Button) findViewById(R.id.courseBackButton);
		Button addEvent = (Button) findViewById(R.id.courseAddToCalendar);
		Button viewSwitch = (Button) findViewById(R.id.viewSwitchButton);

		//Add ListView with course
		Course[] myCourseAsArray = myCourses.toArray(new Course[myCourses.size()]);
		final Course myCourse = myCourseAsArray[number];

		//TextViews
		TextView courseTitle = (TextView) findViewById(R.id.courseTitle);
		TextView courseTimes = (TextView) findViewById(R.id.meetingTime);
		final TextView courseInfo = (TextView) findViewById(R.id.courseInfo);

		//set the description of the course
		courseTitle.setText(myCourse.getTitle());
		courseInfo.setText(myCourse.getDescription());

		//covert list of meeting times to an array
		MeetingTime meetingTimes = myCourse.getMeetingTime();

		String location,starttimes,endtimes,classDays;
		String courseMeetingInfo = "Unknown Dates";
		//get courses times and location
		
		classDays = getDays(meetingTimes);
		
		location = meetingTimes.getLocation();
		
		starttimes = meetingTimes.getStartTime().getHour() 
				+":"+meetingTimes.getStartTime().getMin();
		endtimes = meetingTimes.getEndTime().getHour() 
				+":"+meetingTimes.getEndTime().getMin();

		//set textviews on courses
		courseMeetingInfo = "location: "+location+" Days: "+classDays+" "+starttimes+"-"+endtimes+"\n";
		

		courseTimes.setText(courseMeetingInfo);

		//back button to list of course
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//go back to list of courses
					displayCoursesView(user, myCourses);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//switch the views of notification and course description 
		viewSwitch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//go back to list of courses
					displayNoteView(user, myCourses,number);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//addEvent button to
		addEvent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//go back to list of courses
					addCourseEventToCalendar(user, myCourse);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void addCourseEventToCalendar(final User user, Course myCourse){

		Intent intent = new Intent(Intent.ACTION_INSERT);

		//set the description of the Event
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra(Events.TITLE, myCourse.getTitle());
		intent.putExtra(Events.DESCRIPTION, myCourse.getDescription());
		intent.putExtra(Events.EVENT_LOCATION, myCourse.getMeetingTime().getLocation());

		//String startdate = myCourse.getMeetingTimes().get(0).
		//Setting dates
		//dates: year, month, day, hour, minute
		GregorianCalendar startDate = new GregorianCalendar(
				myCourse.getStartDate().getYear(),
				myCourse.getStartDate().getMonth(), 
				myCourse.getStartDate().getDay(),
				myCourse.getMeetingTime().getStartTime().getHour(),
				myCourse.getMeetingTime().getStartTime().getMin());
		
		GregorianCalendar endDate = new GregorianCalendar(
				myCourse.getStartDate().getYear(),
				myCourse.getStartDate().getMonth(), 
				myCourse.getStartDate().getDay(),
				myCourse.getMeetingTime().getEndTime().getHour(),
				myCourse.getMeetingTime().getEndTime().getMin());
		//last day of class
		GregorianCalendar lastDate = new GregorianCalendar(
				myCourse.getEndDate().getYear(),
				myCourse.getEndDate().getMonth(), 
				myCourse.getEndDate().getDay(),
				myCourse.getMeetingTime().getEndTime().getHour(),
				myCourse.getMeetingTime().getEndTime().getMin());
		
		intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
				startDate.getTimeInMillis());
		intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
				endDate.getTimeInMillis());
		
		// make it a full day event
		//intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
		//color
		intent.putExtra(Events.CALENDAR_COLOR, "GREEN");
		
		// make it a recurring Event
		//SU,MO,TU,WE,TH,FR,SA
		//intent.putExtra(Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");
		intent.putExtra(Events.RRULE, "FREQ=WEEKLY;WKST=SU;BYDAY="+getDays(myCourse.getMeetingTime())
				+";UNTIL="+lastDate.getTimeInMillis());
		startActivity(intent);
		//Toast.makeText(LoginActivity.this, "Event Added", Toast.LENGTH_LONG).show();
	}

	// Method for displaying notelist
	public void displayNoteView(final User user,final List <Course> myCourses,final int number) {
		// Create Linear layout

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		// Add back button
		Button backButton = new Button(this);
		backButton.setText("Back to Course");
		backButton.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		backButton.setX(20);
		backButton.setY(20);

		//back button onClickListener
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					displaySingleCourseView(user,myCourses,number);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// Add button to layout
		layout.addView(backButton);

		List <Notification> courseNotes = myCourses.get(number).getNotifications();
		//Add ListView with notes
		final Notification[] courseNotesAsArray = courseNotes.toArray(new Notification[courseNotes.size()]);
		String[] listArray = new String[courseNotesAsArray.length];
		String simpleDate = null;
		int numberOfNotes = courseNotesAsArray.length;
		for (int i = 0; i < numberOfNotes;i++){
			//display first few words of the note
			// should display as Month, Day
			simpleDate = courseNotesAsArray[i].getDate().getMonth() + "/" + courseNotesAsArray[i].getDate().getDay();
			listArray[i] = simpleDate+" - "+courseNotesAsArray[i].getNoteText();
		}
		ListAdapter la = new ArrayAdapter<String>(this, R.layout.courselist, listArray);
		ListView lv = new ListView(this);
		lv.setAdapter(la);
		layout.addView(lv);

		lv.setTextFilterEnabled(true);
		// Register click callback for the course
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//go to the single course view
				displaySingleCourseView(user,myCourses,arg2);
			}
		});

		// Make inventory view visible
		setContentView(layout,llp);    	
	}

}