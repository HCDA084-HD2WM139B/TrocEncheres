<%@include file="head.jsp" %>
 
<title>Modifier mon Profil</title>
</head>
<body>
	<%@include file="nav.jsp" %>

	<section class="container">
		
		<h2 class="text-center mt-3 mb-5">Modifier mon Profil</h2>
		
		<form class="col-12" method="POST" action="<c:url value="/modificationUpdateProfil" />">
		
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="pseudo">Pseudo : </label>
				<input class="col-6" name="pseudo" id="pseudo" value="<c:out value="${ afficheUtilisateur.pseudo }"></c:out>">
			</div>
			<div class="col-6">
				<label class="col-5" for="nom">Nom : </label>
				<input class="col-6" name="nom" id="nom" value="<c:out value="${ afficheUtilisateur.nom }"></c:out>">
			</div>
		</article>
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="prenom">Prénom : </label>
				<input class="col-6" name="prenom" id="prenom" value="<c:out value="${ afficheUtilisateur.prenom }"></c:out>">
			</div>
			<div class="col-6">
				<label class="col-5" for="email">Email : </label>
				<input class="col-6" name="email" id="email" value="<c:out value="${ afficheUtilisateur.email }"></c:out>">
			</div>
		</article>
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="tel">Téléphone : </label>
				<input class="col-6" name="tel" id="tel" value="<c:out value="${ afficheUtilisateur.telephone }"></c:out>">
			</div>
			<div class="col-6">
				<label class="col-5" for="rue">Rue : </label>
				<input class="col-6" name="rue" id="rue" value="<c:out value="${ afficheUtilisateur.rue }"></c:out>">
			</div>
		</article>
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="postal">Code Postal : </label>
				<input class="col-6" name="postal" id="postal" value="<c:out value="${ afficheUtilisateur.codePostal }"></c:out>">
			</div>
			<div class="col-6">
				<label class="col-5" for="ville">Ville : </label>
				<input class="col-6" name="ville" id="ville" value="<c:out value="${ afficheUtilisateur.ville }"></c:out>">
			</div>
		</article>
		
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="mdp_actuel">Mot de passe actuel : </label>
				<input readonly class="col-6" type="password" name="mdp_actuel" id="mdp_actuel" value="<c:out value="${ afficheUtilisateur.motDePasse }"></c:out>">
			</div>
		</article>
		
		<article class="d-flex col-12 mb-4">
			<div class="col-6">
				<label class="col-5" for="new_mdp">Nouveau mot de passe : </label>
				<input type="password" class="col-6" name="new_mdp" id="new_mdp">
			</div>
			<div class="col-6">
				<label class="col-5" for="confir_mdp">Confirmation : </label>
				<input  type="password" class="col-6" name="confir_mdp" id="confir_mdp">
			</div>
		</article>
		
	   <article class="d-flex col-12 mb-4">
	   		<div class="col-6">
	   			<span class="col-5">Crédit : </span>
	   			<span class="col-6"><c:out value="${ afficheUtilisateur.credit }"></c:out></span>
	   		</div>
	   </article>
	   	
	   <article class="d-flex col-12 mb-4 justify-content-center">
	   	<div class="col-6">
		   <input class="col-5 btn btn-success p-2 mr-2" type="submit" value="Enregistrer">
		   <a class="col-6 btn btn-danger p-2 ml-2" href="<c:url value="/supprimerProfil?id=${ sessionScope.id_utilisateur }" />">Supprimer mon compte</a>
	   </div>
	   </article>


		</form>
		
		<c:if test="${ requestScope.erreurs != null }">
			<div class="alert alert-danger mt-4" role="alert">
				<c:forEach items="${ requestScope.erreurs }" var="erreur">
					<p class="my-0">
						<c:out value="${ erreur }"></c:out>
					</p>
				</c:forEach>
			</div>
		</c:if>
	
	</section>
</body>
</html>