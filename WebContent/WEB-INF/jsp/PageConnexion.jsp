<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://kit.fontawesome.com/7a045c691c.js" crossorigin="anonymous"></script>
<script src="script.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<title>Connexion</title>
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
    </nav>

<div class="container mt-5 col-4 d-flex flex-column justify-content-center">
	
	<h2 class="text-center mb-5">Se Connecter</h2>

	<form class="d-flex flex-column justify-content-center" action="/listeEncheresConnecte" method="post">
		<div class="d-flex mt-2 mb-2">
			<label class="col-4" for="identifiant">Identifiant : </label>
			<input class="col-8" type="text" name="identifiant" id="identifiant"/>
		</div>
		<div class="d-flex mt-2 mb-2">
			<label class="col-4" for="MotDePasse">Mot de passe : </label>
			<input class="col-8" type="password" name="motDePasse" id="MotDePasse"/>
		</div>

		<div class="d-flex justify-content-between mt-2">
			<div class="col-6"><button class="col-12 btn btn-success p-3 mt-2 mb-2" type="submit">Connexion</button></div>
			<div class="col-5 d-flex flex-column justify-content-center">
				<div>
					<input type="checkbox" name="souvenir" id="souvenir"/>
					<label for="souvenir">Se souvenir de moi</label>
				</div>
				<a href="">Mot de passe oublié</a>
			</div>
</div>

</form>



</div>

</body>
</html>