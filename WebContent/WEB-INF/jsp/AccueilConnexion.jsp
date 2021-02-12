<%@include file="head.jsp" %>
<title>Accueil</title>
</head>


<body>
<nav class="mb-4 navbar navbar-expand-lg navbar-light bg-dark">
    <div class="col-md navbar-brand">
      <h4 class="text-white mr-4 mt-1">ENI Ench�res</h4>
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
            <a class="nav-link text-white" href="#">Ench�res</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-white" href="#">Vendre un objet</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-white" href="<c:url value="/monProfil?id=${ id_utilisateur }"/>">Mon Profil</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-white" href="<c:url value="/deconnexion"/>">D�connexion</a>
          </li>
        </ul>
      </div>
    </nav>

    <div>
    	<h1 class="container text-center mt-4 mb-3">Liste des ench�res</h1>
    </div>
    
    <div class="container d-flex justify-content-between align-items-center align-self-center flex-wrap">
    	<section class="col-xl-6 col-md-12">
    		<h2>Les Filtres : </h2>
    		<form method="POST" action"">
    			 <article class="d-none d-lg-block d-md-none"><div class="input-group mt-1">
				      <div class="input-group-prepend">
				        <span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
				      </div>
      				  <input type="text" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
    			 </div></article>
    		
    			<article class="input-group mt-2 mb-2">
    				<label class="col-4" for="categories">Cat�gories :</label>
    					<select class="col-8" name="categories" id="categories">
    						<option value="toutes">Toutes</option>
    						<option value="informatique">Informatique</option>
    						<option value="ameublement">Ameublement</option>
    						<option value="vetements">Vetements</option>
    						<option value="sport_loisirs">Sport & Loisirs</option>
    					</select>
    			</article>
    			
    			 <article class="d-none d-md-block d-lg-none mt-4 mb-4"><div class="input-group mt-1">
				      <div class="input-group-prepend">
				        <span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
				      </div>
      				  <input type="text" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
    			 </div></article>
    			 
    			 <article class="input-group mt-2 mb-2">
    			 	<aside class="col-12 mt-3 d-flex justify-content-between">
    			 		<div>
	  						<input id="inp_achats" name="inp_radio" type="radio">
	    			 		<label for="inp_achats">Achats</label>
	    			 		<div class="d-flex flex-column ml-3">
	    			 			  <div><input type="checkbox" id="en_open" name="en_open"><label class="ml-2" for="en_open">ench�res ouvertes</label></div>
	    			 			  <div><input type="checkbox" id="en_encours" name="en_encours"><label class="ml-2" for="en_encours">mes ench�res en cours</label></div>
	    			 			  <div><input type="checkbox" id="en_val" name="en_val"><label class="ml-2" for="en_val">mes ench�res achev�s</label></div>
	    			 			  
	    			 		</div>
    			 		</div>
    			 		<div>
	  						<input id="vente" name="inp_radio" type="radio"/>
	    			 		<label for="vente">Mes ventes</label>
	    			 		<div class="d-flex flex-column ml-4">
	    			 			  <div><input type="checkbox" id="ve_encours" name="ve_encours"><label class="ml-2" for="ve_encours">mes ventes en cours</label></div>
	    			 			  <div><input type="checkbox" id="ve_null" name="ve_null"><label class="ml-2" for="en_encours">mes ventes non d�but�es</label></div>
	    			 			  <div><input type="checkbox" id="ve_over" name="ve_over"><label class="ml-2" for="ve_over">mes ventes termin�es</label></div>
	    			 		</div>
	    			 		
    			 		</div>
    			 	</aside>
    			 </article>
    	</section>
    	<section class="col-xl-5 col-md-12">
    		<input type="submit" class="col-12 pt-4 pb-4 btn btn-primary" value="Rechercher">
    	</section>
    	</form>
    </div>
    
    <div class="d-flex justify-content-between container mt-3 flex-wrap mb-5">
    
    		<article class="card col-xl-5 col-md-12 d-flex mt-4">
			  <div class="card-body d-flex">
			  	<div class="col-xl-5 col-md-4">
			  		<img alt="img-default" class="img-thumbnail" src="https://zupimages.net/up/21/06/blep.png">
			  	</div>
			  	<div class="col-xl-7 col-md-5 d-flex flex-column">
				    <a href="#" class="card-title badge badge-pill badge-dark">Ins�rez titre ici</a>
					<span>Prix : </span>
					<span>Fin de l'ench�re : </span>
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
				    <a href="#" class="card-title badge badge-pill badge-dark">Ins�rez titre ici</a>
					<span>Prix : </span>
					<span>Fin de l'ench�re : </span>
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
				    <a href="#" class="card-title badge badge-pill badge-dark">Ins�rez titre ici</a>
					<span>Prix : </span>
					<span>Fin de l'ench�re : </span>
					<span class="mt-3">Vendeur : </span>
			    </div>
			  </div>
    		</article>
    		
    </div>

</body>
</html>