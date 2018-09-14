<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<title>Simple Login Form</title>
	<link rel="stylesheet" href="<c:url value="/static/css/reset.css"/>" type="text/css">
	<link rel="stylesheet" href="<c:url value ="/static/css/structure.css" />" type="text/css">
</head>

<body>

<spring:form method="post"  modelAttribute="userJSP" action="check-user">
	<fieldset class="boxBody">

		<spring:label path="user">Username</spring:label>
			<spring:input path="user" />
		<spring:errors path="user" cssClass="error" />
		<spring:label path="pwd">Password</spring:label>
			<spring:input path="pwd" />
		<spring:errors path="pwd" cssClass="error" />
	</fieldset>
	<footer>
		<label><input type="checkbox" tabindex="3">Keep me logged in</label>
			<input type="submit" class="btnLogin" value="Login" tabindex="4">
	</footer>
</spring:form>


</body>
</html>
