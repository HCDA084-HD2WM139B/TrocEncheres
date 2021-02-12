<%@include file="head.jsp" %>

<title>Inscription</title>
</head>
<body>

	<nav class="mb-4 navbar navbar-expand-lg navbar-light bg-dark">
	<div class="col-md navbar-brand">
		<h4 class="text-white mr-4 mt-1">ENI Enchères</h4>
	</div>
	<div class="justify-content-end">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
	</div>
	</nav>

	<div class="container">
		<h2>Mon profil</h2>
		<br />
		<form action="<%=request.getContextPath()%>/inscription"
			method="post">

			<div class="row">
				<div class="col">

					<div class="form-group row">
						<label for="staticEmail" class="col-sm-3 col-form-label">Pseudo:
						</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="pseudo"
								placeholder="Pseudo">
						</div>
					</div>

					<div class="form-group row">
						<label for="staticEmail" class="col-sm-3 col-form-label">Prénom:</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="prenom"
								placeholder="Prénom">
						</div>
					</div>

					<div class="form-group row">
						<label for="staticEmail" class="col-sm-3 col-form-label">Téléphone:
						</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="telephone"
								placeholder="Téléphone">
						</div>
					</div>

					<div class="form-group row">
						<label for="staticEmail" class="col-sm-3 col-form-label">Code
							postal: </label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="codePostal"
								placeholder="Code postal">
						</div>
					</div>

					<div class="form-group row">
						<label for="staticEmail" class="col-sm-3 col-form-label">Mot
							de passe: </label>
						<div class="col-sm-9">
							<input type="password" class="form-control" name="motDePasse"
								placeholder="Password">
						</div>
					</div>

				</div>

				<div class="col">

					<div class="form-group row">
						<label for="inputPassword" class="col-sm-3 col-form-label">Nom:
						</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="nom"
								placeholder="Nom">
						</div>
					</div>

					<div class="form-group row">
						<label for="inputPassword" class="col-sm-3 col-form-label">Email:
						</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="email"
								placeholder="Email">
						</div>
					</div>

					<div class="form-group row">
						<label for="inputPassword" class="col-sm-3 col-form-label">Rue:
						</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="rue"
								placeholder="Rue">
						</div>
					</div>

					<div class="form-group row">
						<label for="inputPassword" class="col-sm-3 col-form-label">Ville:
						</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="ville"
								placeholder="Ville">
						</div>
					</div>
					<div class="form-group row">
						<label for="inputPassword" class="col-sm-3 col-form-label">Confirmation:
						</label>
						<div class="col-sm-9">
							<input type="password" class="form-control" name="motDePasseConf"
								placeholder="Password">
						</div>
					</div>

				</div>
			</div>

			<br />

			<div class=" d-flex justify-content-center mt-2">
					<button class="mr-2 col-4 btn btn-success p-3 mt-2 mb-2" type="submit">Créer</button>

					<button class="ml-2 col-4  btn btn-success p-3 mt-2 mb-2" type="reset"
						onclick="window.location.replace('<%= request.getContextPath() %>/encheres')">Annuler</button>
			</div>



		</form>

		<!-- Message d'aide à la saisie -->
		<c:if test="${ requestScope.erreurs != null }">
			<div class="alert alert-danger mt-4" role="alert">
				<c:forEach items="${ requestScope.erreurs }" var="erreur">
					<p class="my-0">
						<c:out value="${ erreur }"></c:out>
					</p>
				</c:forEach>
			</div>
		</c:if>

	</div>



</body>
</html>