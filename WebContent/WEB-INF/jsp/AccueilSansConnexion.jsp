<%@include file="head.jsp" %>
 

<%@page import="java.util.List"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
	<title>Accueil</title>
</head>


<body>
	<%@include file="nav.jsp" %>
    
    <div>
    	<h1 class="container text-center mt-4 mb-3">Liste des encheres</h1>
    </div>
    
    <div class="container d-flex justify-content-between align-items-center align-self-center flex-wrap">
    	<article class="col-xl-5 col-md-12">
    		<h2>Les Filtres : </h2>
    		<form method="POST" action="<%=request.getContextPath()%>/sanssession">
    			 <aside class="d-none d-lg-block d-md-none"><div class="input-group mt-1">
				      <div class="input-group-prepend">
				        <span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
				      </div>
      				  <input type="text" name="saisieArticle" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
    			 </div></aside>
    			 
    		
    			<aside class="input-group mt-2 mb-1">
    				<label class="col-4" for="categories">Categories :</label>
    					<select class="col-8" name="categories" id="categories">
    						<option value"0">Toutes</option>
    						
    						<!-- Traitement d'affichage de la liste d�roulante des cat�gories -->
    						<c:if test="${ ! empty listeCategories }">
								<c:forEach items="${ listeCategories }" var="categorie">
	    						<option value ="${ categorie.noCategorie }">${ categorie.libelle }</option>
    							</c:forEach>
							</c:if>
							
							
    					</select>
    			</aside>
    			 <aside class="d-none d-md-block d-lg-none mt-4 mb-4">
    			 	<div class="input-group mt-1">
				     	<div class="input-group-prepend">
				        	<span class="input-group-text" id="basic-addon1">
				        		<i class="fas fa-search"></i>
				        	</span>
				     	</div>
      				  	<input type="text" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
    			 	</div>
    			 </aside>
    	</article>
    	<article class="col-xl-5 col-md-12">
    		<input type="submit" class="col-12 pt-3 pb-3 btn btn-primary" value="Rechercher">
    	</article>
    	</form>
    </div>
    
    <div class="d-flex justify-content-between container mt-3 flex-wrap mb-5">
    
		<!-- Traitement d'affichage des Articles dont les ench�res sont en cours -->
    	<c:if test="${ ! empty listeArticles }">
    		<c:forEach items="${ listeArticles }" var="article">
	    		<article class="card col-xl-5 col-md-12 d-flex mt-4">
				  <div class="card-body d-flex">
				  	<div class="col-xl-5 col-md-4">
				  		<img alt="img-default" class="img-thumbnail" src="https://zupimages.net/up/21/06/blep.png">
				  	</div>
				  	<div class="col-xl-7 col-md-5 d-flex flex-column">
					    <a href="<%=request.getContextPath()%>/connexion" class="card-title badge badge-pill badge-dark">
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