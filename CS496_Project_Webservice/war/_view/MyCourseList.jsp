<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<style>
		h1
		{
			text-align:center;
			color:white;
			background-color:#00CC00
		}
	</style>	
	<head>
		<title>My Course List</title>
		<h1>Edu App!</h1>
	</head>
	<body>
		<h2>My Course List</h2>
		<c:if test ="${validcourse == 'true'}">
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
		</c:if>
		<c:if test="${empty validcourse or validcourse == 'false'}">
			<p> You have no courses in your course list </p>
		</c:if>
		<div class="link"><a href="${pageContext.servletContext.contextPath}/MyCourseList/NewCourse?action=add">Add Course</a></div>
	</body>
</html>
