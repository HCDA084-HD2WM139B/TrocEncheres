<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://kit.fontawesome.com/7a045c691c.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> 
<title>Mon Profil</title>
</head>
<body>

<%@include file="nav.jsp" %>
    
    <div id="profil">
    
    <p>Pseudo : <c:out value="${ utilisateurConnecte }"> </c:out> </p>
    
    <p>Nom : </p>
    <p>Prénom : </p>
    <p>Email : </p>
    <p>Téléphone : </p>
    <p>Rue : </p>
    <p>Code postal : </p>
    <p>Ville : </p>
    
    </div>
    
    
</body>
</html>