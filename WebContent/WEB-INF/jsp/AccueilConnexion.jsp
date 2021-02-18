<%@include file="head.jsp"%>

	<title>Accueil</title>
</head>


<body>
	<%@include file="nav.jsp"%>

	<div>
		<h1 class="container text-center mt-4 mb-3">Liste des encheres</h1>
	</div>


	<form method="POST" action="<%=request.getContextPath()%>/encheres">
		<div class="container d-flex justify-content-between align-items-center align-self-center flex-wrap">
			<section class="col-xl-6 col-md-12">
				<h2>Les Filtres :</h2>
				<article class="d-none d-lg-block d-md-none">
					<div class="input-group mt-1">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
						</div>
						<input type="text" name="saisieArticle" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
					</div>
				</article>

				<article class="input-group mt-2 mb-2">
					<label class="col-4" for="categories">Categories :</label> 
					<select class="col-8" name="categories" id="categories">
						<option value="0">Toutes</option>
	    						
	    				<!-- Traitement d'affichage de la liste déroulante des catégories -->
	    				<c:if test="${ ! empty listeCategories }">
							<c:forEach items="${ listeCategories }" var="categorie">
		    					<option value ="${ categorie.noCategorie }">${ categorie.libelle }</option>
	    					</c:forEach>
						</c:if>
								
	    			</select>
				</article>

				<article class="d-none d-md-block d-lg-none mt-4 mb-4">
					<div class="input-group mt-1">
						<div class="input-group-prepend">
							<span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
						</div>
						<input type="text" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
					</div>
				</article>

				<c:if test="${ sessionScope.utilisateurConnecte != null }">

				<article class="input-group mt-2 mb-2">
					<aside class="col-12 mt-3 d-flex justify-content-between">
						<div>
							<input id="inp_achats" name="inp_radio" type="radio" value="achat" checked />
							<label for="inp_achats">Achats</label>
							<div class="d-flex flex-column ml-3">
								<div>
									<input type="checkbox" id="en_open" name="en_open" checked>
									<label class="ml-2" for="en_open">encheres ouvertes</label>
								</div>
								<div>
									<input type="checkbox" id="en_encours" name="en_encours">
									<label class="ml-2" for="en_encours">mes encheres en cours</label>
								</div>
								<div>
									<input type="checkbox" id="en_val" name="en_val">
									<label class="ml-2" for="en_val">mes encheres acheves</label>
								</div>

							</div>
						</div>
						<div>
							<input id="vente" name="inp_radio" type="radio" value="vente" <c:if test="${ requestScope.test != null }"><c:out value="checked"></c:out></c:if> />
							<label for="vente">Mes ventes</label>
							<div class="d-flex flex-column ml-4">
								<div>
									<input type="checkbox" id="ve_encours" name="ve_encours" disabled>
									<label class="ml-2" for="ve_encours">mes ventes en cours</label>
								</div>
								<div>
									<input type="checkbox" id="ve_null" name="ve_null" disabled>
									<label class="ml-2" for="ve_null">mes ventes non debutees</label>
								</div>
								<div>
									<input type="checkbox" id="ve_over" name="ve_over" disabled>
									<label class="ml-2" for="ve_over">mes ventes terminees</label>
								</div>
							</div>

						</div>
					</aside>
				</article>
				
				</c:if>
				
			</section>
			<section class="col-xl-5 col-md-12">
				<input type="submit" class="col-12 pt-4 pb-4 btn btn-primary" value="Rechercher">
			</section>
		</div>
	</form>


	<div class="d-flex justify-content-between container mt-3 flex-wrap mb-5">

		<!-- Traitement d'affichage des Articles dont les enchères sont en cours -->
    	<c:if test="${ ! empty listeArticles }">
    		<c:forEach items="${ listeArticles }" var="article">
	    		<article class="card col-xl-5 col-md-12 d-flex mt-4">
				  <div class="card-body d-flex">
				  	<div class="col-xl-5 col-md-4">
				  		<img alt="img-default" class="img-thumbnail" src="https://zupimages.net/up/21/06/blep.png">
				  	</div>
				  	<div class="col-xl-7 col-md-5 d-flex flex-column">
				  	
					    <a class="card-title badge badge-pill badge-dark" href=
							<c:if test="${ sessionScope.utilisateurConnecte != null }">
				  				<c:url value="/DetailEnchereServlet?id=${ article.noArticle }"></c:url>
						  	</c:if>
						  	<c:if test="${ sessionScope.utilisateurConnecte == null }">
						  		<c:url value="/connexion"></c:url>
						  	</c:if>
						>
					    	<c:out value="${ article.nomArticle }"></c:out>
					    </a>
						<span>Prix : <c:out value="${ article.prixVente }"></c:out> </span>
						<span>Fin de l'enchere : <c:out value="${ article.dateFinEchere }"></c:out> </span>
						<span class="mt-3">Vendeur : <c:out value="${ article.vendeur.pseudo }"></c:out> </span>
				    </div>
				  </div>
	    		</article>
    		</c:forEach>
    	</c:if>

	</div>

</body>
</html>