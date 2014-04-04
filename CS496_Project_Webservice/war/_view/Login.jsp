<!DOCTYPE html>

<html>
	<head>
		<title>Welcome To EDUapp</title>
	</head>

	<body>
		<h1>Welcome To EDUapp</h1>
		<h2>Please Sign in:</h2>
		<table class="inventory">
			<tr>
				<th>UserName: </th>
				<th><input type="text" name="userName" size="12" value="${userName}" /></th>
			</tr>
			<tr>
				<th>Password: </th>
				<th><input type="text" name="password" size="12" value="${password}" /></th>
			</tr>
		</table>
		<button type="button">Submit</button>
		<button type="button">Create Account</button>
		<!--div class="link"><a href="${pageContext.servletContext.contextPath}/inventory/NewItem?action=add">Add item</a></div-->
	</body>
</html>