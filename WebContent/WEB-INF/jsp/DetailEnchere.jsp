<%@include file="head.jsp" %> 
<title>Détail de l'enchère</title>
</head>
<body>

<%@include file="nav.jsp" %>

<h1 class="text-center">Détail vente</h1>

	<div class="container d-flex mt-5" >

		<section class="col-4">
			<img class="img-thumbnail" src="https://zupimages.net/up/21/06/blep.png">
		</section>
	
		<section class="col-10">
			<div class="d-flex mb-5">
				<h4 class="col-5">${ detailArticle.nomArticle }</h4>
			</div>
			
			<div class="d-flex">
				<span class="col-5">Description : </span>
				<p> ${ detailArticle.description } </p>
			</div>
			
			<div class="d-flex">
				<span class="col-5">Catégorie : </span>
				<p> ${ detailArticle.categorie.libelle } </p>
			</div>
			
			<div class="d-flex">
				<span class="col-5">Meilleure offre : </span>
				<p> ${ detailArticle.prixVente } </p>
			</div>
			
			<div class="d-flex">
				<span class="col-5">Mise à prix : </span>
				<p> ${ detailArticle.prixInitial } </p>
			</div>
			
			<div class="d-flex">
				<span class="col-5">Fin de l'enchère : </span>
				<p> ${ detailArticle.dateFinEchere } </p>
			</div>
			
			<div class="d-flex">
				<span class="col-5">Retrait : </span>
					<div>
						<p> ${ detailArticle.vendeur.rue } </p>
						<p> ${ detailArticle.vendeur.codePostal } ${ detailArticle.vendeur.ville } </p>
					</div>
			</div>
			
			<div class="d-flex mb-4">
				<span class="col-5">Vendeur : </span>
				<a href="<c:url value="/monProfil?id=${ detailArticle.vendeur.noUtilisateur }"></c:url>">${ detailArticle.vendeur.pseudo }</a>
			</div>
			
			<form class="d-flex" action="<%=request.getContextPath()%>/DetailEnchereServlet" method="post">
				<label class="col-5" for="proposition">Ma proposition : </label>
				<input class="col-2" type="number" name="proposition" id="proposition"/>
				<input class="btn ml-3 btn-primary col-5"  type="submit" value="Enchérir"/>
			</form>
			
		</section>

	</div>

</body>
</html>