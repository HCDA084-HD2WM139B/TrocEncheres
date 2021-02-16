<nav class="mb-4 navbar navbar-expand-lg navbar-light bg-dark">

	<!-- Lien avec logo -->
	<a href="<c:url value="/encheres" />">
		<div class="col-md navbar-brand d-flex justify-left">
			<img class="w-25 mh25 mr-3" alt="Logo" src="/TrocEncheres/img/Logo_trocEncheres.png">
			<h4 class="text-white mr-4 mt-1 align-self-center">ENI Enchères</h4>
		</div>
	</a>
	
	<!-- Liens du menu -->
	<div class="justify-content-end">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
	</div>
	</div>
	<div class="col collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
		<ul class="nav">
		
			<!-- Menu sans connexion session -->
			<c:if test="${ requestScope.undisplayLinkNavBar == null && sessionScope.utilisateurConnecte == null }">
				<li class="nav-item">
					<a class="nav-link text-white" href="<c:url value="/connexion" />">Se connecter</a>
				</li>
				<li class="nav-item">
					<a class="nav-link text-white" href="<c:url value="/inscription" />">S'inscrire</a>
				</li>
			</c:if>
			
			<!-- Menu avec connexion session -->
			<c:if test="${ requestScope.undisplayLinkNavBar == null && sessionScope.utilisateurConnecte != null }">
				<li class="nav-item">
					<a class="nav-link text-white" href="#">Enchères</a>
				</li>
				<li class="nav-item">
					<a class="nav-link text-white" href="<c:url value="//NouvelleVente?id=${ id_utilisateur }"/>">Vendre un objet</a>
				</li>
				<li class="nav-item">
					<a class="nav-link text-white"	href="<c:url value="/monProfil?id=${ id_utilisateur }"/>">Mon Profil</a>
				</li>
				<li class="nav-item">
					<a class="nav-link text-white" href="<c:url value="/deconnexion"/>">Déconnexion</a>
				</li>
			</c:if>
			
		</ul>
	</div>
</nav>