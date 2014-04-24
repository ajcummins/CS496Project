<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>My Course List</title>
	</head>
	
	<body>
		<h1>My Course List</h1>
		<table class="mycourselist">
			<tr>
				<th>Course ID</th>
				<th>Course Title</th>
			</tr>
			<c:forEach var="course" items="${MyCourseList}">
				<tr>
					<td>${course.code}</td>
					<td><a href="${pageContext.servletContext.contextPath}/MyCourseList/${course.code}?action=view">${course.title}</a></td>
				</tr>
			</c:forEach>
		</table>
		<div class="link"><a href="${pageContext.servletContext.contextPath}/MyCourseList/NewCourse?action=add">Add Course</a></div>
	</body>
</html>
