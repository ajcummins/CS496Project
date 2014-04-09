<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>${Course.courseID} : ${Course.courseTitle} </title>
		<strong>Taught by : ${Course.professor.lastName}, ${Course.professor.firstName}</strong>
	</head>
	
	<body>
		<c:if test="{empty action or action == 'view'}">
			<h1>Course Description</h1>
				<p>${Course.description}</p>
			<h2>Resources</h2>
				<c:forEach var="Resource" Resources="${Course.resourceList}">
					<table>
						<tr>
							<td><a href="${Resource.link}">${Resource.id}</a></td>
						</tr>
					</table>
				</c:forEach>
			<h3>Course Location & Time</h3>
				<c:forEach var="CourseTime" CourseTimes="${Course.coursetimes}">
					<table>
						<tr>
							<td>Implement CourseTime...</td>
							<td>${Course.location}</td>
						</tr>
					</table>
				</c:forEach>
			<h4>Notifications</h4>
				<c:forEach var="Notification" Notifications="${Course.noteList}">
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
		
		</c:if>
		
		<c:if test="${action == 'delete'}">
		
		</c:if>
		
		<c:if test="${action == 'add'}">
		
		</c:if>
		
		<c:if test="${! empty result}">
			<div class="result">${result}</div>
		</c:if>
		
		<div class="link"><a href="$pageContext.servletContext.contextPath}/MyCourseList">Back to My Course List</a></div>
	</body>		
</html>