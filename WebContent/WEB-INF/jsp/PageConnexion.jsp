<%@include file="head.jsp"%>

<title>Connexion</title>
</head>
<body>
	<%@include file="nav.jsp" %>

	<div
		class="container mt-5 col-4 d-flex flex-column justify-content-center">
		<h2 class="text-center mb-5">Se Connecter</h2>

		<!-- Formulaire de connexion -->
		<form class="d-flex flex-column justify-content-center"
			action="<%=request.getContextPath()%>/connexion" method="post">
			<div class="d-flex mt-2 mb-2">
				<label class="col-4" for="identifiant">Identifiant : </label> <input
					class="col-8" type="text" name="identifiant" id="identifiant" />
			</div>
			<div class="d-flex mt-2 mb-2">
				<label class="col-4" for="MotDePasse">Mot de passe : </label> <input
					class="col-8" type="password" name="motDePasse" id="MotDePasse" />
			</div>

			<div class="d-flex justify-content-between mt-2">
				<div class="col-6">
					<button class="col-12 btn btn-success p-3 mt-2 mb-2" type="submit">Connexion</button>
				</div>
				<div class="col-5 d-flex flex-column justify-content-center">
					<div>
						<input type="checkbox" name="souvenir" id="souvenir" /> <label
							for="souvenir">Se souvenir de moi</label>
					</div>
					<a href="">Mot de passe oubli�</a>
				</div>
			</div>
		</form>

		<!-- Message d'aide � la saisie -->
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