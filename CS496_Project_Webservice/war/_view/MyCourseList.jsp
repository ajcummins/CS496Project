<!DOCTYPE html>

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
		
		<!--c:if action ="add">
			<h2>Add Course</h2>
			<table class="mycourselist">
				<tr>
					<th>Enter Course ID: </th>
					<th><input type="text"></input></th>
				</tr>
			</table>
		</c:if-->
	</body>
</html>
