<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://kit.fontawesome.com/7a045c691c.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> 
<title>Accueil</title>
</head>


<body>
<nav class="mb-4 navbar navbar-expand-lg navbar-light bg-dark">
    <div class="col-md navbar-brand">
      <h4 class="text-white mr-4 mt-1">ENI Enchères</h4>
    </div>
    <div class="justify-content-end">
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
    </div>
    </div>
      <div class="col collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="nav">
          <li class="nav-item">
            <a class="nav-link text-white" href="#">Se connecter / S'inscrire</a>
          </li>
        </ul>
      </div>
    </nav>
    
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
    						<option value="toutes">Toutes</option>
    						<option value="informatique">Informatique</option>
    						<option value="ameublement">Ameublement</option>
    						<option value="vetements">Vetements</option>
    						<option value="sport_loisirs">Sport & Loisirs</option>
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