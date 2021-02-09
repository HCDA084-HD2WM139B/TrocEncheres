<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Connexion</title>
</head>
<body>

<div>

	<form action="/listeEncheresConnecte" method="post">

		<label for="identifiant">Identifiant : </label>
		<input type="text" name="identifiant" id="identifiant"/>

<br>
<br>

		<label for="MotDePasse">Mot de passe : </label>
		<input type="password" name="motDePasse" id="MotDePasse"/>

		<button type="submit">Connexion</button>

<div>

		<label for="souvenir">Se souvenir de moi</label>
		<input type="checkbox" name="souvenir" id="souvenir"/>

<br>

		<a href="">Mot de passe oublié</a>

</div>

</form>



</div>

</body>
</html>