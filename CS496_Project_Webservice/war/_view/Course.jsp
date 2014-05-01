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
				<td><center><a  method="post" href="${pageContext.servletContext.contextPath}/MyCourseList/${Course.code}?action=edit">Edit Course</a></center></td>
				<td><center><a href="${pageContext.servletContext.contextPath}/MyCourseList/${Course.code}?action=delete">Delete Course</a></center></td>
			</table>
			<title>${Course.code} : ${Course.title} </title>
			<strong>${Course.code} : ${Course.title} </strong>
			
			<h2>Course Description</h2>
				<p>${Course.description}</p>
			<h2>Resources</h2>
				<table>
					<c:forEach var="resource" items="${resourcelist}">
						<tr>
							<td><a href="${resource.link}">${resource.id}</a></td>
						</tr>
					</c:forEach>
				</table>
			<h2>Course Location & Time</h2>
				<c:forEach var="MeetingTime" items="${meetingtimes}">
					<table>
						<tr>
							<td>Implement MeetingTimes...</td>
							<td>${MeetingTime.location}</td>
						</tr>
					</table>
				</c:forEach>
			<h2>Notifications</h2>
				<c:forEach var="Notification" items="${notelist}">
					<table>
						<tr>
							<td>Implement Note Date...</td>
							<td>${Notification.noteText}</td>
						</tr>
					</table>
				</c:forEach>
		</c:if>
		
		<!-- TODO: UIs for other actions (edit, add, delete) -->
		
		<c:if test="${action == 'edit'}">
			<title>${Course.code} : ${Course.title} </title>		
			<strong>${Course.code} : ${Course.title} </strong>

			<h2>Edit an existing Course</h2>
			<!--form action="${pageContext.servletContext.contextPath}/MyCourseList" method="post">
				<table class="Course">
					<tr>
						<th>Course Code: </th>
						<td><input type = "text" name="${Course.courseCode}" size="10" value="${Course.courseCode}"></input></td>
					</tr>
					<tr>
						<th>Course Title: </th>
						<td><input type = "text" name="${Course.title}" size="10" value="${Course.title}"></input></td>
					</tr>
					<tr>
						<h2>Course Description</h2>
						<p><input type = "text" name="${Course.description}" value="${Course.description}"></input></p>
					</tr>
				</table>
			<h2>Resources</h2>
				<table>
					<c:forEach var="resource" items="${resourcelist}">
						<tr>
							<td><a href="${resource.link}">${resource.id}</a></td>
						</tr>
					</c:forEach>
				</table>
			<h2>Course Location & Time</h2>
				<c:forEach var="MeetingTime" items="${meetingtimes}">
					<table>
						<tr>
							<td>Implement MeetingTimes...</td>
							<td>${MeetingTime.location}</td>
						</tr>
					</table>
				</c:forEach>
			<h2>Notifications</h2>
				<c:forEach var="Notification" items="${notelist}">
					<table>
						<tr>
							<td>Implement Note Date...</td>
							<td>${Notification.noteText}</td>
						</tr>
					</table>
				</c:forEach>
				</table>
				<input type="submit" name="submit" value="Submit">
			</form-->
		</c:if>
		
		<c:if test="${action == 'delete'}">
			<h2>Delete</h2>
		</c:if>
		
		<c:if test="${action == 'add'}">
			<h2>Create a new course</h2>
			<h3>Enter Course Information: </h3>
			<form action="${pageContext.servletContext.contextPath}/MyCourseList" method="post" action="add">
				<table class="Course">
					<tr>
						<th>Enter Course Code: </th>
						<td><input type = "text" name="${Course.courseCode}" size="10" ></input></td>
					</tr>
					<tr>
						<th>Enter Course Title: </th>
						<td><input type = "text" name="${Course.title}" size="10" ></input></td>
					</tr>
				</table>
				<input type="submit" name="submit" value="Submit">
			</form>
		</c:if>
		
		<c:if test="${! empty result}">
			<div class="result">${result}</div>
		</c:if>
		
		<div class="link"><a href="${pageContext.servletContext.contextPath}/MyCourseList">Back to My Course List</a></div>
	</body>		
</html>