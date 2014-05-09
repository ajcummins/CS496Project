<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<title>Edu App!</title>
		<h1>Edu App!</h1>
	</head>
	
	<body>
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
		
	</body>
</html>