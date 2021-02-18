<%@include file="head.jsp" %> 
<title>Mon Profil</title>
</head>
<body>

<%@include file="nav.jsp" %>
    
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