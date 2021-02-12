<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://kit.fontawesome.com/7a045c691c.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> 
<title>Modifier mon Profil</title>
</head>
<body>
	<%@include file="navVide.jsp" %>

	<section class="container">
		
		<h2 class="text-center mt-3 mb-5">Modifier mon Profil</h2>
		
		<form class="col-12" method="POST" action="<c:url value="/modifierProfil" />">
		
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="pseudo">Pseudo : </label>
				<input class="col-6" name="pseudo" id="pseudo" value="<c:out value=""></c:out>">
			</div>
			<div class="col-6">
				<label class="col-5" for="nom">Nom : </label>
				<input class="col-6" name="nom" id="nom" value="<c:out value=""></c:out>">
			</div>
		</article>
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="prenom">Prénom : </label>
				<input class="col-6" name="prenom" id="prenom" value="<c:out value=""></c:out>">
			</div>
			<div class="col-6">
				<label class="col-5" for="email">Email : </label>
				<input class="col-6" name="email" id="email" value="<c:out value=""></c:out>">
			</div>
		</article>
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="tel">Téléphone : </label>
				<input class="col-6" name="tel" id="tel" value="<c:out value=""></c:out>">
			</div>
			<div class="col-6">
				<label class="col-5" for="rue">Rue : </label>
				<input class="col-6" name="rue" id="rue" value="<c:out value=""></c:out>">
			</div>
		</article>
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="postal">Code Postal : </label>
				<input class="col-6" name="postal" id="postal" value="<c:out value=""></c:out>">
			</div>
			<div class="col-6">
				<label class="col-5" for="ville">Ville : </label>
				<input class="col-6" name="ville" id="ville" value="<c:out value=""></c:out>">
			</div>
		</article>
		
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="mdp_actuel">Mot de passe actuel : </label>
				<input class="col-6" type="password" name="mdp_actuel" id="mdp_actuel" value="<c:out value=""></c:out>">
			</div>
		</article>
		
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="new_mdp">Nouveau mot de passe : </label>
				<input class="col-6" name="new_mdp" id="new_mdp">
			</div>
			<div class="col-6">
				<label class="col-5" for="confir_mdp">Confirmation : </label>
				<input class="col-6" name="confir_mdp" id="confir_mdp">
			</div>
		</article>
		
	   <article class="d-flex col-12 mb-4">
	   		<div class="col-6">
	   			<span class="col-5">Crédit : </span>
	   			<span class="col-6"><c:out value=""></c:out></span>
	   		</div>
	   </article>
	   	
	   <article class="d-flex col-12 mb-4 justify-content-center">
	   	<div class="col-6">
		   <input class="col-5 btn btn-success p-2 mr-2" type="submit" value="Enregistrer">
		   <a class="col-6 btn btn-danger p-2 ml-2" href="<c:url value="/supprimerProfil" />">Supprimer mon compte</a>
	   </div>
	   </article>
		
		
		
		
		</form>
	
	</section>
</body>
</html>