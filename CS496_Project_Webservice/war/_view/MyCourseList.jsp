<!DOCTYPE html>

<html>
	<head>
		<title>My Course List</title>
	</head>
	
	<!--body>
		<h1>My Course List</h1>
		<table class="mycourselist">
			<tr>
				<th>Course ID</th>
				<th>Course Title</th>
				<th>Professor</th>
				<th>Operations</th>
			</tr>
			<c:forEach var="item" items="${Course}">
				<tr>
					<td>${Course.courseID}</td>
					<td>${Course.courseTitle}</td>
					<td>${Course.professor}</td>
					<td>
						<a href="${pageContext.servletContext.contextPath}/MyCourseList/${Course.code}">View</a>
						<a href="${pageContext.servletContext.contextPath}/MyCourseList/${Course.code}?action=edit">Edit</a>
						<a href="${pageContext.servletContext.contextPath}/MyCourseList/${Course.code}?action=delete">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="link"><a href="${pageContext.servletContext.contextPath}/MyCourseList/NewCourse?action=add">Add Course</a></div>
	</body-->
</html>
