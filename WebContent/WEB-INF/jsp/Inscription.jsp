<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Bootstrap CSS -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://kit.fontawesome.com/7a045c691c.js" crossorigin="anonymous"></script>
<script src="script.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<title>Inscription</title>
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

<div class="container">
<h2>Mon profil</h2>
<br/>
<form action="/InscriptionServlet" method="post">

<div class="row">
<div class="col">

<div class="form-group row">
<label for="staticEmail" class="col-sm-2 col-form-label">Pseudo: </label>
<div class="col-sm-10">
<input type="text"  class="form-control" name="pseudo" placeholder="Pseudo">
</div>
</div>

<div class="form-group row">
<label for="staticEmail" class="col-sm-2 col-form-label">Prénom:</label>
<div class="col-sm-10">
<input type="text" class="form-control" name="prenom" placeholder="Prénom">
</div>
</div>

<div class="form-group row">
<label for="staticEmail" class="col-sm-2 col-form-label">Téléphone: </label>
<div class="col-sm-10">
<input type="text" class="form-control" name="telephone" placeholder="Téléphone">
</div>
</div>

<div class="form-group row">
<label for="staticEmail" class="col-sm-2 col-form-label">Code postal: </label>
<div class="col-sm-10">
<input type="text" class="form-control" name="codePostal" placeholder="Code postal">
</div>
</div>

<div class="form-group row">
<label for="staticEmail" class="col-sm-2 col-form-label">Mot de passe: </label>
<div class="col-sm-10">
<input type="password" class="form-control" name="motDePasse" placeholder="Password">
</div>
</div>

</div>

<div class="col">

<div class="form-group row">
<label for="inputPassword" class="col-sm-2 col-form-label">Nom: </label>
<div class="col-sm-10">
<input type="text" class="form-control" name="nom" placeholder="Nom">
</div>
</div>

<div class="form-group row">
<label for="inputPassword" class="col-sm-2 col-form-label">Email: </label>
<div class="col-sm-10">
<input type="text" class="form-control" name="email" placeholder="Email">
</div>
</div>

<div class="form-group row">
<label for="inputPassword" class="col-sm-2 col-form-label">Rue: </label>
<div class="col-sm-10">
<input type="text" class="form-control" name="rue" placeholder="Rue">
</div>
</div>

<div class="form-group row">
<label for="inputPassword" class="col-sm-2 col-form-label">Ville: </label>
<div class="col-sm-10">
<input type="text" class="form-control" name="ville" placeholder="Ville">
</div>
</div>
<br/>
<div class="form-group row">
<label for="inputPassword" class="col-sm-2 col-form-label">Confirmation: </label>
<div class="col-sm-10">
<input type="password" class="form-control" name="motDePasseConf" placeholder="Password">
</div>
</div>

</div>
</div>

<br/>

<div class="d-flex justify-content-between mt-2">
<div class="col-6"><button class="col-12 btn btn-success p-3 mt-2 mb-2" type="submit">Créer</button></div>
<div class="col-6"><button class="col-12 btn btn-success p-3 mt-2 mb-2">Annuler</button></div>
</div>



</form>

</div>


		
</body>
</html>