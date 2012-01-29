<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test DB Connection</title>
</head>
<body>

<sql:query var="rs" dataSource="jdbc/spd">
select * from user order by userId
</sql:query>

<h2>Users</h2>
  
<c:forEach var="row" items="${rs.rows}">
	${row.userId}. ${row.lastname} ${row.firstname}
	<span style="color: red;">
		<c:choose>
			<c:when test="${row.active eq null}">(deleted)</c:when>
			<c:when test="${!row.active}">(not active)</c:when>
		</c:choose>
	</span>
	<br/>
</c:forEach>

</body>
</html>