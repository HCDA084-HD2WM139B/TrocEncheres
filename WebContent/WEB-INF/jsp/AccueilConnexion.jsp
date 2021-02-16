<%@include file="head.jsp" %>

	<title>Accueil</title>
</head>


<body>
	<%@include file="nav.jsp" %>

    <div>
    	<h1 class="container text-center mt-4 mb-3">Liste des enchères</h1>
    </div>
    
    <div class="container d-flex justify-content-between align-items-center align-self-center flex-wrap">
    	<form method="POST" action"<%=request.getContextPath()%>/avecsession">
    		<section class="col-xl-6 col-md-12">
    			<h2>Les Filtres : </h2>
    			<article class="d-none d-lg-block d-md-none"><div class="input-group mt-1">
				      <div class="input-group-prepend">
				        <span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
				      </div>
      				  <input type="text" class="form-control" placeholder="Article" aria-label="Article" aria-describedby="basic-addon1">
    			 </div></article>
    		
    			<article class="input-group mt-2 mb-2">
    				<label class="col-4" for="categories">Catégories :</label>
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
	    			 			  <div><input type="checkbox" id="en_open" name="en_open"><label class="ml-2" for="en_open">enchères ouvertes</label></div>
	    			 			  <div><input type="checkbox" id="en_encours" name="en_encours"><label class="ml-2" for="en_encours">mes enchères en cours</label></div>
	    			 			  <div><input type="checkbox" id="en_val" name="en_val"><label class="ml-2" for="en_val">mes enchères achevés</label></div>
	    			 			  
	    			 		</div>
    			 		</div>
    			 		<div>
	  						<input id="vente" name="inp_radio" type="radio"/>
	    			 		<label for="vente">Mes ventes</label>
	    			 		<div class="d-flex flex-column ml-4">
	    			 			  <div><input type="checkbox" id="ve_encours" name="ve_encours"><label class="ml-2" for="ve_encours">mes ventes en cours</label></div>
	    			 			  <div><input type="checkbox" id="ve_null" name="ve_null"><label class="ml-2" for="en_encours">mes ventes non débutées</label></div>
	    			 			  <div><input type="checkbox" id="ve_over" name="ve_over"><label class="ml-2" for="ve_over">mes ventes terminées</label></div>
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