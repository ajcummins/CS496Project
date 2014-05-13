package edu.ycp.cs496.eduapp.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.CourseDate;
import edu.ycp.cs496.eduapp.model.CourseRegEntry;
import edu.ycp.cs496.eduapp.model.MeetingTime;
import edu.ycp.cs496.eduapp.model.MeetingType;
import edu.ycp.cs496.eduapp.model.TimeOfDay;
import edu.ycp.cs496.eduapp.model.User;
import edu.ycp.cs496.eduapp.model.UserType;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load sqlite driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

// Database relative methods
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {

		Connection conn = DriverManager.getConnection("jdbc:derby:E:/git/CS496Project/CS496_Project_Persistence/test.db;create=true"); //Josh
		//Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/Mr_E/git/CS496Project/CS496_Project_Persistence/test.db;create=true"); //Anthony
		//Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/Supersith9/git/CS496Project/CS496_Project_Persistence/test.db;create=true"); //Tom

		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}

	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					// Note that the 'id' column is an autoincrement primary key,
					// so SQLite will automatically assign an id when rows
					// are inserted.
					
					stmt = conn.prepareStatement(
							"create table courses (" +
							"  id integer primary key not null generated always as identity," +
							"  code varchar(40) unique," +
							"  title varchar(80)," +
							"  description varchar(800)," +
							"  sun integer," +
							"  mon integer," +
							"  tue integer," +
							"  wed integer," +
							"  thu integer," +
							"  fri integer," +
							"  sat integer," +
							" starthr integer," + 
							" startmin integer," + 
							" endhr integer," + 
							" endmin integer," + 
							" location varchar(20)," + 
							" type integer," + 
							" startmon integer," + 
							" startday integer," + 
							" startyr integer," + 
							" endmon integer," + 
							" endday integer," + 
							" endyr integer" + 
							")"
							// type is either 0 = LECTURE or 1 = LAB
							//FIXME: meetingTimes, noteList, resourceList
					);
					
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(		
							"create table users (" +
							"  id integer primary key not null generated always as identity," +
							"  username varchar(20) unique," +
							"  password varchar(20)," +
							"  firstname varchar(20)," +
							"  lastname varchar(20)," +
							"  userType integer" + 
							")"
					);
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(
							"create table coursereg (" +
							"  id integer primary key not null generated always as identity," +
							"  userid integer ," +
							"  courseid integer" +
							")"
					);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
				
			}
		});
	}
	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				User ajcummins = new User("ajcummins","test","A. Joshua","Cummins");
				User thon = new User("thon","test","Thomas","Hon");
				User ao = new User("ao","test","Anthony","O");
				User dhove = new User("dhove","test","David","Hovemeyer");
				User dbab = new User("dbab","test","David","Babcock");
				
				try {
					stmt = conn.prepareStatement("insert into users (name, quantity) values (?, ?)");
					storeUserNoId(ajcummins, stmt, 1);
					stmt.addBatch();
					storeUserNoId(thon, stmt, 1);
					stmt.addBatch();
					storeUserNoId(ao, stmt, 1);
					stmt.addBatch();
					storeUserNoId(dhove, stmt, 1);
					stmt.addBatch();
					storeUserNoId(dbab, stmt, 1);
					stmt.addBatch();
					
					stmt.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public static void main(String[] args) {
		DerbyDatabase db = new DerbyDatabase();
		System.out.println("Creating tables...");
		db.createTables();
		System.out.println("Loading initial data...");
		//db.loadInitialData();
		System.out.println("Done!");
	}

// Utility methods to make database interactions easier
	protected void storeUserNoId(User inUser, PreparedStatement stmt, int index) throws SQLException{
		/*
		 * A unique id should be automatically generated...
		 */
		stmt.setString(index++, inUser.getUsername());
		stmt.setString(index++, inUser.getPassword());
		stmt.setString(index++, inUser.getFName());
		stmt.setString(index++, inUser.getLName());
		stmt.setInt(index++, inUser.getUserType().typeID);
	}
	
	protected void storeCourseNoId(Course inCourse, PreparedStatement stmt, int index) throws SQLException{
		// unique id should be auto generated...
		stmt.setString(index++, inCourse.getCode());
		stmt.setString(index++, inCourse.getTitle());
		stmt.setString(index++, inCourse.getDescription());
		
		//Set Day Fields
		boolean[] days = inCourse.getMeetingTime().getDays();
		for(int i = 0; i < days.length; i++)
		{
			// Goes in order sun,mon,tue..... to sat
			if(days[i])
			{
				// days[i] is true, set to 1
				stmt.setInt(index++, 1);
			}
			else
			{
				stmt.setInt(index++, 0);
			}
		}
		
		// Set TimeOfDay Fields
		stmt.setInt(index++, inCourse.getMeetingTime().getStartTime().getHour());
		stmt.setInt(index++, inCourse.getMeetingTime().getStartTime().getMin());
		stmt.setInt(index++, inCourse.getMeetingTime().getEndTime().getHour());
		stmt.setInt(index++, inCourse.getMeetingTime().getEndTime().getMin());
		
		stmt.setString(index++, inCourse.getMeetingTime().getLocation());
		
		// Determine the type to send
		if(inCourse.getMeetingTime().getType() == MeetingType.LECTURE)
		{
			stmt.setInt(index++, 0);
		}
		else
		{
			stmt.setInt(index++, 1);
		}
		
		// set Start CourseDate Mon, Day, Year
		stmt.setInt(index++, inCourse.getStartDate().getMonth());
		stmt.setInt(index++, inCourse.getStartDate().getDay());
		stmt.setInt(index++, inCourse.getStartDate().getYear());
		
		// set End CourseDate Mon, Day, Year
		stmt.setInt(index++, inCourse.getEndDate().getMonth());
		stmt.setInt(index++, inCourse.getEndDate().getDay());
		stmt.setInt(index++, inCourse.getEndDate().getYear());
	}
	
	private void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		//FIXME: Do something with the user id from the table
		user.setUserID(resultSet.getInt(index++));	
		user.setUsername(resultSet.getString(index++));
		user.setPassword(resultSet.getString(index++));
		user.setFName(resultSet.getString(index++));
		user.setLName(resultSet.getString(index++));
		// Based on int userType convert to enum userType
		int userType = resultSet.getInt(index++);
		if(userType == UserType.ADMIN.typeID)
		{
			user.setToAdmin();
		}
		else if(userType == UserType.PROFESSOR.typeID)
		{
			user.setToProfessor();
		}
		else
		{
			user.setToStudent();
		}
	}
	
	private void loadCourse(Course course, ResultSet resultSet, int index) throws SQLException{
		// FIXME: Do something with the course id from the table
		course.setCourseID(resultSet.getInt(index++));
		course.setCode(resultSet.getString(index++));
		course.setTitle(resultSet.getString(index++));
		course.setDescription(resultSet.getString(index++));
		
		// Fill a meeting time object to set within the course object
		MeetingTime meet = new MeetingTime();
		//Set Day Fields
		boolean[] days = new boolean[7];
		for(int i = 0; i < days.length; i++)
		{
			int daysResult = resultSet.getInt(index++);
			if(daysResult == 1)
			{
				// true
				days[i] = true;
			}
			else
			{
				days[i] = false;
			}
		}
		meet.setDays(days);
		
		// Set TimeOfDay Fields
		meet.setStartTime(new TimeOfDay(resultSet.getInt(index++),resultSet.getInt(index++)));
		meet.setEndTime(new TimeOfDay(resultSet.getInt(index++),resultSet.getInt(index++)));
		
		// Set Location
		meet.setLocation(resultSet.getString(index++));
		
		// Determine the type to send
		int typeResult = resultSet.getInt(index++);
		if(typeResult == 0)
		{
			meet.setType(MeetingType.LECTURE);
		}
		else
		{
			meet.setType(MeetingType.LAB);
		}
		course.setMeetingTime(meet);
		
		// Set Start CourseDate
		course.setStartDate(new CourseDate(resultSet.getInt(index++),resultSet.getInt(index++),resultSet.getInt(index++)));
		// Set End CourseDate
		course.setEndDate(new CourseDate(resultSet.getInt(index++),resultSet.getInt(index++),resultSet.getInt(index++)));
	}
	
	private void loadEntry(CourseRegEntry entry, ResultSet resultSet, int index) throws SQLException{
		// FIXME: Do something with the course id from the table
		entry.setEntryID(resultSet.getInt(index++));
		entry.setUserID(resultSet.getInt(index++));
		entry.setCourseID(resultSet.getInt(index++));
	}
	
	
// Controller Methods	

	@Override
	public User authenticateUser(final String user,final String pass) {
		return executeTransaction(new Transaction<User>() {
			@Override
			public User execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try{
					stmt = conn.prepareStatement("select users.* from users where users.username = ? AND users.password = ?");
					stmt.setString(1, user);
					stmt.setString(2, pass);	
					
					resultSet = stmt.executeQuery();
					
					if(!resultSet.next()){
						// invalid User parameters, User could not be found
						return null;
					}
					
					User user = new User();
					loadUser(user, resultSet,1);
					printTables();
					return user;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}

		});
	}

	@Override
	public void createAccount(final User inUser) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;
				
				try{
					stmt = conn.prepareStatement(
							"insert into users (username,password,firstName,lastName,userType) values (?,?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
					
					storeUserNoId(inUser,stmt,1);
					
					stmt.executeUpdate();
					
					/*
					//used to check the id's of new user's
					generatedKeys = stmt.getGeneratedKeys();
					if(!generatedKeys.next()){
						throw new SQLException("Could not get auto-generated key for inserted User");
					}
					
					int userID = generatedKeys.getInt(1);
					System.out.println("New user has id " + userID);
					*/
					
					
					
					return true;
				}
				finally
				{
					DBUtil.closeQuietly(generatedKeys);
					DBUtil.closeQuietly(stmt);
				}
			}
		
		});
	}

	@Override
	public List<Course> getMyCourseList(final String inUsername) {
		return executeTransaction(new Transaction<List<Course>>() {
			@Override
			public List<Course> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				ResultSet resultSet = null;
				
				try{
					// get user id w/ username
					stmt = conn.prepareStatement("select users.id from users where users.username = ?");
					stmt.setString(1, inUsername);
					
					resultSet = stmt.executeQuery();
					if(!resultSet.next()){
						return null;
					}
					
					// use user id w/ course reg to get course id's
					int userID = resultSet.getInt(1);//normally index++ not sure if it needs to be 1 or 2??
					stmt2 = conn.prepareStatement("select coursereg.courseid from coursereg where coursereg.userid = ?");								//!! Not returning anything...
					stmt2.setInt(1, userID);
					
					resultSet = stmt2.executeQuery();
					/*if(!resultSet.next())
					{
						return null;
					}*/
							
					// get a list of course registry entries
					List<Integer> courseIDs = new ArrayList<Integer>();
					int index = 1;
					while(resultSet.next())
					{
						courseIDs.add(resultSet.getInt(index++));
					}
					// get all the courses w/ the id's
					List<Course> courses = new ArrayList<Course>();
					for(int i = 0; i < courseIDs.size(); i++)
					{
						stmt3 = conn.prepareStatement("select courses.* from courses where courses.id = ?");
						stmt3.setInt(1, courseIDs.get(i));
						
						resultSet = stmt3.executeQuery();
						
						// fill the list object with the newly found courses
						resultSet.next();
						Course course = new Course();
						loadCourse(course,resultSet,1);
						courses.add(course);
					}
					return courses;
						
				} catch(Exception e){
					return null;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}

		});		
		
	}

	@Override
	public Course getCourseByCode(final String inCourseCode) {
		return executeTransaction(new Transaction<Course>() {
		@Override
		public Course execute(Connection conn) throws SQLException {
			PreparedStatement stmt = null;
			ResultSet resultSet = null;
			
			try{
				// get user id w/ username
				stmt = conn.prepareStatement("select courses.* from courses where courses.code = ?");
				stmt.setString(1, inCourseCode);
				
				resultSet = stmt.executeQuery();
				if(!resultSet.next()){
					return null;
				}
				else
				{
					Course tempCourse = new Course();
					loadCourse(tempCourse,resultSet,1);
					return tempCourse;
				}
				
				
			} finally {
				DBUtil.closeQuietly(resultSet);
				DBUtil.closeQuietly(stmt);
			}
		}

		});		
	}

	@Override
	public boolean checkIfProf(String inProfPass) {
		/* Do we need this/ and if we do. for what?*/
		return false;
	}

	@Override
	public boolean editCourse(Course course) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean addCourse(final Course inCourse) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;
				
				try{
					stmt = conn.prepareStatement(
							"insert into courses (code,title,description,sun,mon,tue,wed,thu,fri,sat,starthr,startmin,endhr,endmin,location,type,startmon,startday,startyr,endmon,endday,endyr) " +
							"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
					
					storeCourseNoId(inCourse,stmt,1);
					
					stmt.executeUpdate();
					
					return true;
				}
				catch(Exception e){
					e.printStackTrace();
					return false;
				}
				finally{
					DBUtil.closeQuietly(generatedKeys);
					DBUtil.closeQuietly(stmt);
				}
			}
		
		});
	}

	@Override
	public boolean deleteCourse(Course course) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean addEntry(final String courseCode,final List<String> usernameList) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					// First get the course id
					stmt = conn.prepareStatement("select courses.id from courses where courses.code = ?");
					stmt.setString(1, courseCode);
					
					resultSet = stmt.executeQuery();
					if(!resultSet.next()){
						return false;
					}
					int courseID = resultSet.getInt(1);
					
					// Second get the user id's
					List<Integer> userIDs = new ArrayList<Integer>();
					for(int i = 0; i < usernameList.size(); i++)
					{
						// Ask database for each user id
						stmt = conn.prepareStatement("select users.id from users where users.username = ?");
						stmt.setString(1, usernameList.get(i));
						
						resultSet = stmt.executeQuery();
						if(!resultSet.next())
						{
							return false;
						}
						
						// add the found userID to the list
						userIDs.add(resultSet.getInt(1));
					}
					
					// Using the course id and user id's make course entries
					for(int i = 0; i < userIDs.size(); i++)
					{
						stmt = conn.prepareStatement(
								"insert into coursereg (userid,courseid) values (?,?)",
								PreparedStatement.RETURN_GENERATED_KEYS
						);
						
						int index = 1;
						stmt.setInt(index++,userIDs.get(i));
						stmt.setInt(index++,courseID);
						
						stmt.executeUpdate();
					}
				
					
					// Report true if successful
					return true;
					
				} 
				// Report false if unsuccessful
				catch(Exception e){
					return false;
				}
				finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
		
	}
	
	@Override
	public List<User> getAllUsers() {
		// Return the entire Users table 
		return executeTransaction(new Transaction<List<User>>() {
			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try{
					// Note: no 'where' so all items will be returned
					stmt = conn.prepareStatement("select users.* from users");
					
					resultSet = stmt.executeQuery();
					
					List<User> result = new ArrayList<User>();
					while(resultSet.next()){
						User anotherUser = new User();
						loadUser(anotherUser, resultSet, 1);
						result.add(anotherUser);
					}
					return result;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}

		});		
		
	}
	
	public List<Course> getAllCourses() {
		// Return entire Courses Table
		return executeTransaction(new Transaction<List<Course>>() {
			@Override
			public List<Course> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try{
					// Note: no 'where' so all items will be returned
					stmt = conn.prepareStatement("select courses.* from courses");
					
					resultSet = stmt.executeQuery();
					
					List<Course> result = new ArrayList<Course>();
					while(resultSet.next()){
						Course anotherCourse = new Course();
						loadCourse(anotherCourse, resultSet, 1);
						result.add(anotherCourse);
					}
					return result;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public List<CourseRegEntry> getCourseReg() {
		// Return Course Reg
		return executeTransaction(new Transaction<List<CourseRegEntry>>() {
			@Override
			public List<CourseRegEntry> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try{
					// Note: no 'where' so all items will be returned
					stmt = conn.prepareStatement("select courseReg.* from courseReg");
					
					resultSet = stmt.executeQuery();
					
					List<CourseRegEntry> result = new ArrayList<CourseRegEntry>();
					while(resultSet.next()){
						CourseRegEntry anotherEntry = new CourseRegEntry();
						loadEntry(anotherEntry, resultSet, 1);
						result.add(anotherEntry);
					}
					return result;
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void printTables() throws SQLException {
		try {
			// Obtain Users table
			List<User> userList = getAllUsers();
			// Obtain courses Table
			List<Course> courseList = getAllCourses();
			// Obtain courseReg Table
			List<CourseRegEntry> courseReg = getCourseReg();
			// Print out users table
			System.out.println("--------------------------------- Users Table -------------------------------------------");
			for(int i = 0; i < userList.size(); i++)
			{
				System.out.println("UserID: " + userList.get(i).getUserID() + " Username: " + userList.get(i).getUsername() + " Password: " + userList.get(i).getPassword() +
						" Firstname: " + userList.get(i).getFName() + " Lastname: " + userList.get(i).getLName());
			}
			System.out.println("--------------------------------- Courses Table -------------------------------------------");
			// Print out courses table
			for(int i = 0; i < courseList.size(); i++)
			{
				System.out.println("CourseID: " + courseList.get(i).getCourseID() + " CourseCode: " + courseList.get(i).getCode() 
						+ " CourseTitle: " + courseList.get(i).getTitle() + "CourseDesc: " + courseList.get(i).getDescription());
				System.out.println("MeetingTime Days-> Sun: " + courseList.get(i).getMeetingTime().getDays()[0] + " Mon: " + courseList.get(i).getMeetingTime().getDays()[1] + 
						 " Tue: " + courseList.get(i).getMeetingTime().getDays()[2] + " Wed: " + courseList.get(i).getMeetingTime().getDays()[3]);
				System.out.println("Meeting Times-> StartHr"+ courseList.get(i).getMeetingTime().getStartTime().getHour() + " StartMin: " + courseList.get(i).getMeetingTime().getStartTime().getMin() + 
						" EndHr: " + courseList.get(i).getMeetingTime().getEndTime().getHour() + " EndMin: " + courseList.get(i).getMeetingTime().getEndTime().getMin());
				System.out.println("Meeting Location : " + courseList.get(i).getMeetingTime().getLocation() +" Meeting Type: " + courseList.get(i).getMeetingTime().getType());
				System.out.println("Start Date -> Mon: " + courseList.get(i).getStartDate().getMonth() + " Day: " + courseList.get(i).getStartDate().getDay()  + " Year: " + courseList.get(i).getStartDate().getYear());
				System.out.println("End Date -> Mon: " + courseList.get(i).getEndDate().getMonth() + " Day: " + courseList.get(i).getEndDate().getDay()  + " Year: " + courseList.get(i).getEndDate().getYear());
			}
			System.out.println("--------------------------------- Course Registry -------------------------------------------");
			// Print out courseReg table
			for(int i = 0; i< courseReg.size(); i++)
			{
				System.out.println("EntryID: " + courseReg.get(i).getEntryID() + " UserID: " + courseReg.get(i).getUserID() + " CourseID: " + courseReg.get(i).getCourseID());
			}
			
		} 
		catch(Exception e){
			System.out.println("Print Tables ERROR ");
			e.printStackTrace();
		}
	}

	@Override
	public void createAccount(User inUser, boolean isProf) {
		// TODO Auto-generated method stub
		
	}
	
	// Utility methods
	
}
