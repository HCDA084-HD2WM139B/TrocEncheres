<%@include file="head.jsp" %>
<title>Nouvelle vente</title>
</head>
<body>

	<%@include file="nav.jsp" %>
	
	<div class="container">
		<h2 class="text-center">Nouvelle Vente</h2>
		
			<form action="<%=request.getContextPath()%>/NouvelleVente" method="post">
			
				<div class="row">
				
					<div class="col-4">
						<div class="row justify-content-start">
						<img alt="Logo" class="img-thumbnail" style="width: 350px" src="/TrocEncheres/img/Logo_trocEncheres.png">
						</div>
					</div>
					
					<div class="col-7">
					
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">Article:</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="article" placeholder="Article">
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">Description:</label>
							<div class="col-sm-5">
								<textarea rows="3" cols="10" class="form-control" name="description" placeholder="Description"></textarea>
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Catégorie:</label>
							<div class="col-sm-4">
								<select class="form-control" name="categorie">
								    <option value="">Choisir une catégorie</option>
								    <option value="categorie1">Catégorie 1</option>
								    <option value="categorie2">Catégorie 2</option>
								</select>
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Photo de l'article:</label>
							<div class="col-sm-4">
								<input type="file" class="form-control" name="photo" accept="image/png, image/jpeg" placeholder="Uploader">
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Mise à prix:</label>
							<div class="col-sm-3">
								<input type="number" class="form-control" name="prix">
							</div>
							<div class="col-sm-6">
							${ requestScope.erreurs.prix }
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Début de l'enchère:</label>
							<div class="col-sm-4">
								<input type="date" class="form-control" name="debutEnchere">
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Fin de l'enchère:</label>
							<div class="col-sm-4">
								<input type="date" class="form-control" name="finEnchere">
							</div>
						</div>
						
						<fieldset class="border p-2">
							<legend  class="w-auto">Retrait</legend>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Rue:</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="rue" placeholder="Rue">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Code postal:</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="codePostal" placeholder="Code postal">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Ville:</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" name="ville" placeholder="Ville">
								</div>
							</div>
						</fieldset>
						
						<button class="mr-2 col-2 btn btn-success p-3 mt-2 mb-2" type="submit">Enregistrer</button>
						<button class="ml-2 col-2  btn btn-success p-3 mt-2 mb-2" type="reset">Annuler</button>
					
					</div>
					
				</div>
			</form>
			${ requestScope.erreurs.prix }
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