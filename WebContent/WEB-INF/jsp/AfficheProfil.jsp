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

<%@include file="navVide.jsp" %>
    
    <session class="container d-flex justify-content-center flex-column" id="profil">
    
    	<h2 class="text-center mt-5 mb-5">Mon Profil</h2>
    	
    	
    	<article class=" mb-4 text-center d-flex justify-content-center">
    		<span class="col-3">Pseudo : </span> <span class="col-3"><c:out value="${ afficheUtilisateur.pseudo }"></c:out></span>
    	</article>
    	<article class=" mb-4 text-center d-flex justify-content-center">
    		<span class="col-3">Nom : </span> <span class="col-3"><c:out value="${ afficheUtilisateur.nom}"></c:out></span>
    	</article>
    	<article class=" mb-4 text-center d-flex justify-content-center">
    		<span class="col-3">Prénom : </span> <span class="col-3"><c:out value="${ afficheUtilisateur.prenom}"></c:out></span>
    	</article>
    	<article class=" mb-4 text-center d-flex justify-content-center">
    		<span class="col-3">Email: </span> <span class="col-3"><c:out value="${ afficheUtilisateur.email}"></c:out></span>
    	</article>
    	<article class=" mb-4 text-center d-flex justify-content-center">
    		<span class="col-3">Téléphone : </span> <span class="col-3"><c:out value="${ afficheUtilisateur.telephone}"></c:out></span>
    	</article>
    	<article class=" mb-4 text-center d-flex justify-content-center">
    		<span class="col-3">Rue : </span> <span class="col-3"><c:out value="${ afficheUtilisateur.rue}"></c:out></span>
    	</article>
    	<article class=" mb-4 text-center d-flex justify-content-center">
    		<span class="col-3">Code Postal : </span> <span class="col-3"><c:out value="${ afficheUtilisateur.codePostal}"></c:out></span>
    	</article>
    	<article class=" mb-4 text-center d-flex justify-content-center">
    		<span class="col-3">Ville : </span> <span class="col-3"><c:out value="${ afficheUtilisateur.ville}"></c:out></span>
    	</article>
    	
    	<c:if test="${ id_utilisateur ==  afficheUtilisateur.noUtilisateur }">
    	
    	<article class="mb-4 text-center d-flex justify-content-center">
    		<a class="col-6 p-3 btn btn-primary" href="<c:url value="/modifierProfil" />">Modifier</a>
    	</article>
    	
    	</c:if>
    
    </session>
    
    
</body>
</html>