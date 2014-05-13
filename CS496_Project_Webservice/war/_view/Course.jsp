<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<style>
		h1
		{
			text-align:center;
			color:white;
			background-color:#00CC00;
		}
	</style>	

	<head>
		<h1>Edu App!</h1>
	</head>
	
	<body>
		
		<c:if test="${empty action or action == 'view'}">
			<table width="100%">
				<td><center><a href="${pageContext.servletContext.contextPath}/MyCourseList/${Course.code}?action=edit">Edit Course</a></center></td>
				<td><center><a href="${pageContext.servletContext.contextPath}/MyCourseList/${Course.code}?action=delete">Delete Course</a></center></td>
				<td><center><a href="${pageContext.servletContext.contextPath}/MyCourseList/${Course.code}?action=register">Register Users</a></center></td>
			</table>
			<br>
			<title>${Course.code} : ${Course.title} </title>
			<center><h2 {text-alight:center}>${Course.code} : ${Course.title} </h2></center>
			
			<fieldset>
				<legend>Course Location & Time</legend>
				<table>
					<td><p><font size="5">${classDays}	&nbsp&nbsp&nbsp</font></p></td>
					<td><p><font size="5">${startTime.hour}:${startTime.min}-${endTime.hour}:${endTime.min}</font></p></td> 
					<td><p><font size="5">&nbsp&nbsp&nbsp${MeetingTime.location}</font></p></td>
				</table>
			</fieldset>
			
			<h2>Resources</h2>
			<c:if test="${validresources == 'true'}">
				<table>
					<c:forEach var="resource" items="${resourcelist}">
						<td><a href="${resource.link}">${resource.id}</a></td>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${validresources == 'false'}">
				<p>There are no resources.</p>
			</c:if>
			
			<h2>Notifications</h2>
			<c:if test="${validnotes == 'true'}">
				<table>
					<c:forEach var="note" items="${notelist}">
						<td><p>${note.noteText}</p></td>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${validnotes == 'false'}">
				<p>There are no notifications.</p>
			</c:if>
			
			<h2>Class Dates</h2>
			<p>Class Starts: ${classDateS} Class Ends on: ${classDateE}</p>
			
			
			
			
			
			<h2>Course Description</h2>
				<p>${Course.description}</p>
				</table>
																						
		</c:if>
		
		<!-- TODO: UIs for other actions (edit, add, delete) -->
		
		<c:if test="${action == 'edit'}">
			<title>${Course.code} : ${Course.title} </title>		
			<strong>${Course.code} : ${Course.title} </strong>

			<h2>Edit an existing Course</h2>
			<form action="${pageContext.servletContext.contextPath}/MyCourseList?action=edit" method="post">
				<table class="Course">
					<tr>
						<th>Course Code: </th>
						<td><input type = "text" name="${Course.code}" size="10" value="${Course.code}"></input></td>
					</tr>
					<tr>
						<th>Course Title: </th>
						<td><input type = "text" name="${Course.title}" size="50" value="${Course.title}"></input></td>
					</tr>
					<tr>
						<th>Course Description:</th>
						<td><textarea rows="4" cols="50" name="${Course.description}" >${Course.description}</textarea></td>
					</tr>
					<tr>
						<th>Resources:</th>
						<td>
							<table>
								<c:forEach var="resource" items="${resourcelist}">
									<td><input type = "text" name="${resource.id}" size="50" value="${resource.id}"></td>
									<td><input type = "text" name="${resource.link}" size="50" value="${resource.link}"></td>
									<td><a href="${resource.link}">${resource.id}</a></td>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<th>Course Location & Time:</th>
						<td>
							<table>
								<c:forEach var="MeetingTime" items="${meetingtimes}">
									<tr>
										<td><input type = "text" name="" size="15" value="Nothing for this yet"></td>
										<td><input type = "text" name="${MeetingTime.location}" size="15" value="${MeetingTime.location}"></td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr>
						<th>Notifications:</th>
						<td><table>
							<c:forEach var="Notification" items="${notelist}">
								
									<tr>
										<td>Implement Note Date...</td>
										<td>${Notification.noteText}</td>
									</tr>
								
							</c:forEach>
							<tr>
								<th>Add new notification:</th>
								<td><input type = "text" name="newNoteDate" size="15" value=""></td>
								<td><textarea name="newNoteText" rows="4" cols="50"></textarea></td>
							</tr></table>
						</td>
					</tr>
				</table>
				<input type="submit" name="submit" value="Submit">
			</form>
		</c:if>
		
		<c:if test="${action == 'delete'}">
			<h2>Delete</h2>
			<form action="${pageContext.servletContext.contextPath}/MyCourseList?action=delete" method="post">
				<p>Are you sure you want to delete?</p>
				<p>${Course.code} : ${Course.title} </p>
				<input type="submit" name="submit" value="Confirm">
			</form>
		</c:if>
		
		<c:if test="${action == 'add'}">
			<h2>Create a new course</h2>
			<fieldset>
			<legend>Enter Course Information: </legend>
				<form action="${pageContext.servletContext.contextPath}/MyCourseList?action=add" method="post">
					<table class="Course">
						<tr>
							<th>Course Code: </th>
							<td><input type = "text" name="courseCode" size="10" ></input></td>
							<th>Course Title: </th>
							<td><input type = "text" name="courseTitle" size="10" ></input></td>
						</tr>
					</table>
					<h4>Course Description: </h4>
					<table>
						<tr>
							<td><textarea rows="4" cols="50" name="courseDesc">Enter your course description here</textarea></td>
						</tr>
					</table>
					<h4>Course Meeting Times</h4>
					<table>
						<tr>
							<td>Start Time</td>
							<td></td>
							<td>End Time</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>HH</td>
							<td>MM</td>
							<td>HH</td>
							<td>MM</td>
							<td>Sun</td>
							<td>Mon</td>
							<td>Tue</td>
							<td>Wed</td>
							<td>Thu</td>
							<td>Fri</td>
							<td>Sat</td>
						</tr>
						<tr>
							<td><input type = "text" name="startHr" size="10" ></input></td>
							<td><input type = "text" name="startMin" size="10" ></input></td>
							<td><input type = "text" name="endHr" size="10" ></input></td>
							<td><input type = "text" name="endMin" size="10" ></input></td>
							<td>
								<input type = "checkbox" name="sunChk" value="true"></input>
							</td>
							<td>
								<input type = "checkbox" name="monChk" value="true"></input>
							</td>
							<td>
								<input type = "checkbox" name="tueChk" value="true"></input>
							</td>
							<td>
								<input type = "checkbox" name="wedChk" value="true"></input>
							</td>
							<td>
								<input type = "checkbox" name="thuChk" value="true"></input>
							</td>
							<td>
								<input type = "checkbox" name="friChk" value="true"></input>
							</td>
							<td>
								<input type = "checkbox" name="satChk" value="true"></input>
							</td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td>
								Start Date:
							</td>
							<td>
								Month:
							</td>
							<td>
								Day:
							</td>
							<td>
								Year:
							</td>
							<td>
								End Date:
							</td>
							<td>
								Month:
							</td>
							<td>
								Day:
							</td>
							<td>
								Year:
							</td>
						</tr>
						<tr>
							<td></td>
							<td><input type = "text" name="startMon" size="10" ></input></td>
							<td><input type = "text" name="startDay" size="10" ></input></td>
							<td><input type = "text" name="startYr" size="10" ></input></td>
							<td></td>
							<td><input type = "text" name="endMon" size="10" ></input></td>
							<td><input type = "text" name="endDay" size="10" ></input></td>
							<td><input type = "text" name="endYr" size="10" ></input></td>
						<tr>
							<td>Type of Meeting :</td>
							<td>Lecture : <input type="radio" name="lecRad" value="checked"></input></td>
							<td>Lab : <input type="radio" name="labRad" value="checked"></input></td>
						</tr>
						<tr>
							<td>Location : </td>
							<td><input type = "text" name="loc" size="10" ></input></td>
						</tr>
					</table>
				<input type="submit" name="submit" value="Submit">
			</fieldset>
		</c:if>
		
		<!-- REGISTER USERS 																														-->
		<c:if test="${empty action or action == 'register'}">
			<h4>Register Users</h4>
			<form action="${pageContext.servletContext.contextPath}/Login" method="post">
				<table>
					<tr>
						<td>
							<select id="userList" size="10">
								<c:forEach var="user" items="${userlist}">
									<option>"${user.username}"</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<button onclick="addFunc()">Add User</button>
						</td>
						<td>
							<button onclick="removeFunc()">Remove User</button>
						</td>
						<td>
							<select id="regList" size="10">
								
							</select>
						</td>
					<tr>
				</table>
				<input type="submit" name="submit" value="Submit">
			</form>
			
			<script>
				function addFunc()
				{
					var userList = document.getElementById("userList");
					var regList = document.getElementById("regList");
					regList.add(userList.selectedIndex);
					userList.remove(userList.selectedIndex);
				}
			</script>
			<script>
				function removeFunc()
				{
					var userList = document.getElementById("userList");
					var regList = document.getElementById("regList");
					userList.add(regList.selectedIndex);
					regList.remove(regList.selectedIndex);
				}
			</script>
		</c:if>
		
		<c:if test="${! empty result}">
			<div class="result">${result}</div>
		</c:if>
		
		<div class="link"><a href="${pageContext.servletContext.contextPath}/MyCourseList">Back to My Course List</a></div>
	</body>		
</html>