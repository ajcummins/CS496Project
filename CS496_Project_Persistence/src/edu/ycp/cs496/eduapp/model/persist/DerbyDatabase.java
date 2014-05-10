package edu.ycp.cs496.eduapp.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.eduapp.model.Course;
import edu.ycp.cs496.eduapp.model.CourseRegEntry;
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
		//josh
		//Connection conn = DriverManager.getConnection("jdbc:derby:E:/git/CS496Project/CS496_Project_Persistence/test.db;create=true");
		Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/Supersith9/git/CS496Project/CS496_Project_Persistence/test.db;create=true");
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
							"  description varchar(800)" +
							")"
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
							"create table courseReg (" +
							"  id integer primary key not null generated always as identity," +
							"  userID integer ," +
							"  courseID integer" +
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
	
	private void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		//FIXME: Do something with the user id from the table
		resultSet.getInt(index++);	
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
		resultSet.getInt(resultSet.getInt(index++));
		course.setCode(resultSet.getString(index++));
		course.setTitle(resultSet.getString(index++));
		course.setDescription(resultSet.getString(index++));
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
				ResultSet resultSet = null;
				
				try{
					// get user id w/ username
					stmt = conn.prepareStatement("select users.* from users where users.username = ?");
					stmt.setString(1, inUsername);
					
					resultSet = stmt.executeQuery();
					if(!resultSet.next()){
						return null;
					}
					else
					{
						// use user id w/ course reg to get course id's
						int userID = resultSet.getInt(1);//normally index++ not sure if it needs to be 1 or 2??
						stmt = conn.prepareStatement("select courseReg.* from courseReg where courseReg.courseID = ?");
						stmt.setInt(1, userID);
						
						resultSet = stmt.executeQuery();
						if(!resultSet.next())
						{
							return null;
						}
						else
						{
							
							// get a list of course registry entries
							List<CourseRegEntry> courseRegEntries = new ArrayList<CourseRegEntry>();
							int index = 1;
							while(!resultSet.next())
							{
								courseRegEntries.add(new CourseRegEntry(resultSet.getInt(index++),resultSet.getInt(index++)));
							}
							// get all the courses w/ the id's
							List<Course> courses = new ArrayList<Course>();
							for(int i = 0; i < courseRegEntries.size(); i++)
							{
								stmt = conn.prepareStatement("select courses.* from courses where courses.id = ?");
								stmt.setInt(1, courseRegEntries.get(i).getCourseID());
								
								resultSet = stmt.executeQuery();
								
								// fill the list object with the newly found courses
								Course course = new Course();
								loadCourse(course,resultSet,1);
								courses.add(course);
							}
							return courses;
						}
					}
					
					
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}

		});		
		
	}

	@Override
	public List<Course> getMainCourseList() {
		/* Do we need this? and if we do. for what? */
		return null;
	}

	@Override
	public Course getCourseByCode(String courseCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkIfProf(String inProfPass) {
		/* Do we need this/ and if we do. for what?*/
		return false;
	}

	@Override
	public Course editCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course addCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course deleteCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// Utility methods
	
}
