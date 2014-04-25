<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>${Course.code} : ${Course.title} </title>
		<strong>${Course.code} : ${Course.title} </strong>
	</head>
	
	<body>
		<c:if test="${empty action or action == 'view'}">
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
			<h1>Edit</h1>
		</c:if>
		
		<c:if test="${action == 'delete'}">
			<h1>Delete</h1>
		</c:if>
		
		<c:if test="${action == 'add'}">
			<h1>Add</h1>
		</c:if>
		
		<c:if test="${! empty result}">
			<div class="result">${result}</div>
		</c:if>
		
		<div class="link"><a href="${pageContext.servletContext.contextPath}/MyCourseList">Back to My Course List</a></div>
	</body>		
</html>