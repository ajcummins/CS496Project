<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Welcome To EDUapp</title>
	</head>

	<body>
		<h1>Welcome To EDUapp</h1>
		<h2>Please Sign in:</h2>
		<form action="${pageContext.servletContext.contextPath}/Login" method="post">
			<table>
				<tr>
					<th>UserName: </th>
					<th><input type="text" name="userName" size="12" /></th>
				</tr>
				<tr>
					<th>Password: </th>
					<th><input type="password" name="password" size="12" /></th>
				</tr>
			</table>
			<input type="submit" value="Submit"></input>
			<button type="button">Create Account</button>
			<c:if test="${!empty result}">
				<p><c:out value="${result}"/><p>
			</c:if>
		</form>
		
		
	</body>
</html>