<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@page import="java.util.List"%>
    <%@page import="fr.eni.encheres.bo.Categorie"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://kit.fontawesome.com/7a045c691c.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> 
<title>Accueil</title>
</head>


<body>
	<%@include file="nav.jsp" %>
    
    <div>
    	<h1 class="container text-center mt-4 mb-3">Liste des enchères</h1>
    </div>
    
    <div class="container d-flex justify-content-between align-items-center align-self-center flex-wrap">
    	<article class="col-xl-5 col-md-12">
    		<h2>Les Filtres : </h2>
    		<form method="POST" action"">
    			 <aside class="d-none d-lg-block d-md-none"><div class="input-group mt-1">
				      <div class="input-group-prepend">
				        <span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
				      </div>
      				  <input type="text" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
    			 </div></aside>
    			 
    		
    			<aside class="input-group mt-2 mb-1">
    				<label class="col-4" for="categories">Catégories :</label>
    					<select class="col-8" name="categories" id="categories">
    						<option value"0">Toutes</option>
    						<c:if test="${ ! empty listeCategories }">
								<c:forEach items="${ listeCategories }" var="categorie">
	    						<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
    							</c:forEach>
							</c:if>
    					</select>
    			</aside>
    			
    			 <aside class="d-none d-md-block d-lg-none mt-4 mb-4"><div class="input-group mt-1">
				      <div class="input-group-prepend">
				        <span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
				      </div>
      				  <input type="text" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
    			 </div></aside>
    	</article>
    	<article class="col-xl-5 col-md-12">
    		<input type="submit" class="col-12 pt-3 pb-3 btn btn-primary" value="Rechercher">
    	</article>
    	</form>
    </div>
    
    <div class="d-flex justify-content-between container mt-3 flex-wrap mb-5">
    
    		<article class="card col-xl-5 col-md-12 d-flex mt-4">
			  <div class="card-body d-flex">
			  	<div class="col-xl-5 col-md-4">
			  		<img alt="img-default" class="img-thumbnail" src="https://zupimages.net/up/21/06/blep.png">
			  	</div>
			  	<div class="col-xl-7 col-md-5 d-flex flex-column">
				    <a href="#" class="card-title badge badge-pill badge-dark">Insérez titre ici</a>
					<span>Prix : </span>
					<span>Fin de l'enchère : </span>
					<span class="mt-3">Vendeur : </span>
			    </div>
			  </div>
    		</article>
    		
    		<article class="card col-xl-5 col-md-12 d-flex mt-4">
			  <div class="card-body d-flex">
			  	<div class="col-xl-5 col-md-4">
			  		<img alt="img-default" class="img-thumbnail" src="https://zupimages.net/up/21/06/blep.png">
			  	</div>
			  	<div class="col-xl-7 col-md-5 d-flex flex-column">
				    <a href="#" class="card-title badge badge-pill badge-dark">Insérez titre ici</a>
					<span>Prix : </span>
					<span>Fin de l'enchère : </span>
					<span class="mt-3">Vendeur : </span>
			    </div>
			  </div>
    		</article>
    		
    		<article class="card col-xl-5 col-md-12 d-flex mt-4">
			  <div class="card-body d-flex">
			  	<div class="col-xl-5 col-md-4">
			  		<img alt="img-default" class="img-thumbnail" src="https://zupimages.net/up/21/06/blep.png">
			  	</div>
			  	<div class="col-xl-7 col-md-5 d-flex flex-column">
				    <a href="#" class="card-title badge badge-pill badge-dark">Insérez titre ici</a>
					<span>Prix : </span>
					<span>Fin de l'enchère : </span>
					<span class="mt-3">Vendeur : </span>
			    </div>
			  </div>
    		</article>
    		
    </div>

</body>
</html>