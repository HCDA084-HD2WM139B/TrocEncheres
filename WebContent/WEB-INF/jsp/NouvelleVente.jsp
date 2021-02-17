<%@include file="head.jsp" %>
<title>Nouvelle vente</title>
</head>
<body>

	<%@include file="nav.jsp" %>
		
	<div class="container">
			
		<h2 class="text-center">Nouvelle Vente</h2>
		
			<form action="<%=request.getContextPath()%>/NouvelleVente" method="post">
			
				<div class="row">
				
					<div class="col-3">
						<div class="row justify-content-start">
						<img alt="Logo" class="img-thumbnail" style="width: 350px" src="/TrocEncheres/img/Logo_trocEncheres.png">
						</div>
					</div>
					
					<div class="col-8">
					
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">Article:</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="article" placeholder="Article" value="${ requestScope.corrects.article }">
							</div>
							<div class="col-sm-4 text-danger">
								<c:if test="${ requestScope.erreurs.article != null }">
											<c:out value="${ requestScope.erreurs.article }"></c:out>
								</c:if>
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">Description:</label>
							<div class="col-sm-6">
								<textarea rows="3" cols="10" class="form-control" name="description" placeholder="Description" value="${ requestScope.corrects.description }" ></textarea>
							</div>
							<div class="col-sm-4 text-danger">
								<c:if test="${ requestScope.erreurs.description != null }">
											<c:out value="${ requestScope.erreurs.description }"></c:out>
								</c:if>
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">Catégorie:</label>
							<div class="col-sm-6">
								<select class="form-control" name="categorie">
								    <option value="0">Choisir une catégorie</option>
								    <option value="1">Informatique</option>
								    <option value="2">Ameublement</option>
								    <option value="3">Vêtement</option>
								    <option value="4">Sport & Loisirs</option>
								    <option value="5">Erreur</option>								    
								</select>
							</div>
							<div class="col-sm-4 text-danger">
								<c:if test="${ requestScope.erreurs.categorie != null }">
											<c:out value="${ requestScope.erreurs.categorie }"></c:out>
								</c:if>
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Photo de l'article:</label>
							<div class="col-sm-5">
								<input type="file" class="form-control" name="photo" accept="image/png, image/jpeg" placeholder="Uploader">
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Mise à prix:</label>
							<div class="col-sm-3">
								<input type="number" class="form-control" name="prix" value="${ requestScope.corrects.prix }">
							</div>
							<div class="col-sm-4 text-danger">
								<c:if test="${ requestScope.erreurs.prix != null }">
											<c:out value="${ requestScope.erreurs.prix }"></c:out>
								</c:if>
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Début de l'enchère:</label>
							<div class="col-sm-4">
								<input type="date" class="form-control" name="debutEnchere" value="${ requestScope.corrects.debutEnchere }">
							</div>
							<div class="col-sm-4 text-danger">
								<c:if test="${ requestScope.erreurs.debutEnchere != null }">
											<c:out value="${ requestScope.erreurs.debutEnchere }"></c:out>
								</c:if>
							</div>
						</div>
						
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Fin de l'enchère:</label>
							<div class="col-sm-4">
								<input type="date" class="form-control" name="finEnchere" value="${ requestScope.corrects.finEnchere }">
							</div>
							<div class="col-sm-4 text-danger">
								<c:if test="${ requestScope.erreurs.finEnchere != null }">
											<c:out value="${ requestScope.erreurs.finEnchere }"></c:out>
								</c:if>
							</div>
						</div>
						
						<fieldset class="border p-2">
							<legend  class="w-auto">Retrait</legend>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Rue:</label>
								<div class="col-sm-4">
									<c:choose>
										<c:when test = "${ requestScope.corrects.rue == null }">
											<input type="text" class="form-control" name="rue" placeholder="Rue" value="${ afficheUtilisateur.rue}">
										</c:when>
										<c:when test = "${ requestScope.corrects.rue != null }">
											<input type="text" class="form-control" name="rue" placeholder="Rue" value="${ requestScope.corrects.rue }">
										</c:when>
									</c:choose>
								</div>
								<div class="col-sm-4 text-danger">
									<c:if test="${ requestScope.erreurs.rue != null }">
												<c:out value="${ requestScope.erreurs.rue }"></c:out>
									</c:if>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Code postal:</label>
								<div class="col-sm-4">
									<c:choose>
										<c:when test = "${ requestScope.corrects.codePostal == null }">
											<input type="text" class="form-control" name="codePostal" placeholder="Code postal" value="${ afficheUtilisateur.codePostal}">
										</c:when>
										<c:when test = "${ requestScope.corrects.codePostal != null }">
											<input type="text" class="form-control" name="codePostal" placeholder="Code postal" value="${ requestScope.corrects.codePostal }">
										</c:when>
									</c:choose>
								</div>
								<div class="col-sm-4 text-danger">
									<c:if test="${ requestScope.erreurs.codePostal != null }">
												<c:out value="${ requestScope.erreurs.codePostal }"></c:out>
									</c:if>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Ville:</label>
								<div class="col-sm-4">
									<c:choose>
										<c:when test = "${ requestScope.corrects.ville == null }">
											<input type="text" class="form-control" name="ville" placeholder="Ville" value="${ afficheUtilisateur.ville}">
										</c:when>
										<c:when test = "${ requestScope.corrects.ville != null }">
											<input type="text" class="form-control" name="ville" placeholder="Ville" value="${ requestScope.corrects.ville }">
										</c:when>
									</c:choose>
								</div>
								<div class="col-sm-4 text-danger">
									<c:if test="${ requestScope.erreurs.ville != null }">
												<c:out value="${ requestScope.erreurs.ville }"></c:out>
									</c:if>
								</div>
							</div>
						</fieldset>
					<div class="row">
						<button class="ml-3 mr-2 col-2 btn btn-success p-3 mt-3 mb-2" type="submit">Enregistrer</button>
						<button class="ml-2 col-2  btn btn-success p-3 mt-3 mb-2" type="reset" onclick="window.location.replace('<%= request.getContextPath() %>/encheres')">Annuler</button>
				
						<c:choose>
							<c:when test = "${ requestScope.erreurs.insertion == null }">
							</c:when>
							<c:when test = "${ requestScope.erreurs.insertion != null }">
								<p class="col-6 text-danger text-center p-3 mt-2 mb-2">
										<c:out value="${ requestScope.erreurs.insertion }"></c:out>
								</p>
							</c:when>
						</c:choose>
				</div>
					</div>
					
				</div>
			</form>

	</div>
	

</body>
</html>