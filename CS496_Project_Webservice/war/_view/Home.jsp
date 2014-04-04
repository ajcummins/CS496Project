<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Fruit Inventory</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/inventory.css" />
	</head>

	<body>
		<h1>Fruit Inventory</h1>
		<table class="inventory">
			<tr>
				<th>Name</th>
				<th>Quantity</th>
				<th>Operations</th>
			</tr>
			<c:forEach var="item" items="${Inventory}">
				<tr>
					<td>${item.name}</td>
					<td>${item.quantity}</td>
					<td>
						<a href="${pageContext.servletContext.contextPath}/inventory/${item.name}">View</a>
						<a href="${pageContext.servletContext.contextPath}/inventory/${item.name}?action=edit">Edit</a>
						<a href="${pageContext.servletContext.contextPath}/inventory/${item.name}?action=delete">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="link"><a href="${pageContext.servletContext.contextPath}/inventory/NewItem?action=add">Add item</a></div>
	</body>
</html>