<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Create Account</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/inventory.css" />
	</head>

	<body>
		<h1>Create Account</h1>
		<form action="${pageContext.servletContext.contextPath}/CreateAccount" method="post">
			<table class="CreateAccount">
				<tr>
					<th>Enter Username:</th>
					<td><input type = "text" name="Username" size="10" ></td>
				</tr>
				<tr>
					<th>Enter New Password:</th>
					<td><input type = "password" name="Password" size="10" ></td>
				</tr>
				<tr>
					<th>Confirm New Password:</th>
					<td><input type = "password" name="ConfirmPassword" size="10" ></td>
				</tr>
				<tr>
					<th>Enter First Name:</th>
					<td><input type = "text" name="FirstName" size="10" ></td>
				</tr>
				<tr>
					<th>Enter Last Name:</th>
					<td><input type = "text" name="LastName" size="10" ></td>
				</tr>
			</table>
			<c:if test="${!empty result}">
				<p><c:out value="${result}"/><p>
			</c:if>
		</form>
		<div class = "submitButton"><input type="submit" name="submit" value="Submit"></div>
	</body>
</html>