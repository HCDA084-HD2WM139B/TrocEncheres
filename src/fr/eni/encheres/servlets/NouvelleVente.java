package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class NouvelleVente
 */
@WebServlet("/NouvelleVente")
public class NouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Constantes de paramètres
	private static final String PARAM_ARTICLE = "article";
	private static final String PARAM_DESCRIPTION = "description";
	private static final String PARAM_CATEGORIE = "categorie";
	private static final String PARAM_PRIX = "prix";
	private static final String PARAM_DEBUT_ENCHERE = "debutEnchere";
	private static final String PARAM_FIN_ENCHERE = "finEnchere";
	private static final String PARAM_RUE = "rue";
	private static final String PARAM_CODE_POSTAL = "codePostal";
	private static final String PARAM_VILLE = "ville";
	private static final String PARAM_ID_UTILISATEUR = "id";
	private static final String PARAM_INSERTION = "insertion";
	// Constantes sur le nombre de caractère
	private static final int NBR_CAR_ARTICLE = 50;
	private static final int NBR_CAR_DESCRIPTION = 200;
	private static final int NBR_CAR_CODE_POSTAL = 6;
	// Constantes message erreur
	private static final String ERR_TAILLE = "Doit être compris entre 2 et ";
	private static final String ERR_CATEGORIE = "Selectionner une catégorie";
	private static final String ERR_PRIX = "Le prix doit être positif";
	private static final String ERR_DATE = "Vérifier la date";
	private static final String ERR_CODE_POSTAL = "Doit être numérique";
	private static final String ERR_INSERTION = "Erreur lors de l'insertion. Merci de réessayer";
	//Constantes de redirection 
	private static final String NOUVELLE_VENTE_JSP = "/WEB-INF/jsp/NouvelleVente.jsp";
	private static final String ACCUEIL_CONNEXION_JSP = "/WEB-INF/jsp/AccueilConnexion.jsp";
	// Variables de paramètres
	private static String paramArticle = "", paramDescription = "", paramCategorie = "", paramPrix = "";
	private static String paramDebutEnchere = "", paramFinEnchere = "", paramRue = "", paramcodePostal = "";
	private static String paramVille = "";
	// Variables de validation des paramètres
	private static boolean paramArticleBoolean = false, paramDescriptionBoolean = false, paramCategorieBoolean = false;
	private static boolean paramPrixBoolean = false, paramDebutEnchereBoolean = false, paramFinEnchereBoolean = false;
	private static boolean paramRueBoolean = false, paramcodePostalBoolean = false, paramVilleBoolean = false;
	private static boolean articleAjoute = false;
	// Variables
	private static int intCategorie = 0;
	private static int compareDateMin = 0;
	private static int compareDateMax = 0;
	private static int paramPrixInt = 0;
	private static int no_utilisateur = 0;
	private static Date paramDebutEnchereDate;
	private static Date paramFinEnchereDate;
	private static Categorie categorie;
	private static Utilisateur utilisateur;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Déclarations
		Utilisateur utilisateurTrouve= null;
		
		//Récupération du paramètre
		String sNo_utilisateur = request.getParameter(PARAM_ID_UTILISATEUR);
		
		//Transforme le type Vue en type Modele pour préparer l'appel au Modele.
		no_utilisateur = Integer.parseInt(sNo_utilisateur);
		//Appel à la BLL
		EnchereManager manager = EnchereManager.getEnchereManager();
		try {
			utilisateurTrouve = manager.getUtilisateurByID(no_utilisateur);
		} catch (BusinessException e) {
			
		}
				
		request.setAttribute("afficheUtilisateur", utilisateurTrouve);
		request.setAttribute("undisplayLinkNavBar", "none");
		
		RequestDispatcher rd = request.getRequestDispatcher(NOUVELLE_VENTE_JSP);
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		// Récupération des paramètres sur le fichier NouvelleVente.jsp
		paramArticle = request.getParameter(PARAM_ARTICLE);
		paramDescription = request.getParameter(PARAM_DESCRIPTION);
		paramCategorie = request.getParameter(PARAM_CATEGORIE);
		paramPrix = request.getParameter(PARAM_PRIX);
		paramDebutEnchere = request.getParameter(PARAM_DEBUT_ENCHERE);
		paramFinEnchere = request.getParameter(PARAM_FIN_ENCHERE);
		paramRue = request.getParameter(PARAM_RUE);
		paramcodePostal = request.getParameter(PARAM_CODE_POSTAL);
		paramVille = request.getParameter(PARAM_VILLE);
		
		// Appel du manager pour appeler les méthodes
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		// Déclaration d'une MAP pour stocker les messages d'erreur et une MAP pour les valeurs correctes
		Map<String, String> messageErreurMap = new LinkedHashMap<String, String>();
		Map<String, String> messageOkMap = new LinkedHashMap<String, String>();
		
		// Contrôle du paramètre article
		if (!manager.verifierTailleChampOk(paramArticle, NBR_CAR_ARTICLE)) {
			messageErreurMap.put(PARAM_ARTICLE, ERR_TAILLE + NBR_CAR_ARTICLE);
		} else {
			messageOkMap.put(PARAM_ARTICLE, paramArticle);
			paramArticleBoolean = true;
		}
		
		// Contrôle du paramètre description
		if (!manager.verifierTailleChampOk(paramDescription, NBR_CAR_DESCRIPTION)) {
			messageErreurMap.put(PARAM_DESCRIPTION, ERR_TAILLE + NBR_CAR_DESCRIPTION);
		} else {
			messageOkMap.put(PARAM_DESCRIPTION, paramDescription);
			paramDescriptionBoolean = true;
		}
		
		// Contrôle du paramètre catégorie
		// On transforme la chaine de caractère en entier
		intCategorie = Integer.parseInt(paramCategorie);
		if (intCategorie <= 0) {
			messageErreurMap.put(PARAM_CATEGORIE, ERR_CATEGORIE);
		} else {
			try {
				categorie = manager.getInsertCategorie(intCategorie, "");
				paramCategorieBoolean = true;
			} catch (BusinessException e) {
				messageErreurMap.put(PARAM_CATEGORIE, ERR_CATEGORIE);
			}
		}
		
		// Photo de l'article
		
		// Contrôle du paramètre de mise à prix
		if (!manager.verifierSiEntier(paramPrix)) {
			messageErreurMap.put(PARAM_PRIX, ERR_TAILLE + NBR_CAR_ARTICLE);
		} else if (Integer.parseInt(paramPrix) <= 0 ) {
			messageErreurMap.put(PARAM_PRIX, ERR_PRIX);
		} else {
			messageOkMap.put(PARAM_PRIX, paramPrix);
			paramPrixInt = Integer.parseInt(paramPrix);
			paramPrixBoolean = true;
		}
		
		// Contrôle du paramètre date de début d'enchère
		compareDateMin = paramDebutEnchere.compareTo(manager.dateJour());
		if (!manager.verifierTailleChampOk(paramDebutEnchere, NBR_CAR_ARTICLE)) {
			messageErreurMap.put(PARAM_DEBUT_ENCHERE, ERR_TAILLE + NBR_CAR_ARTICLE);
		} else if (compareDateMin < 0 ) {
			messageErreurMap.put(PARAM_DEBUT_ENCHERE, ERR_DATE);
		} else {
			messageOkMap.put(PARAM_DEBUT_ENCHERE, paramDebutEnchere);
			System.out.println(paramDebutEnchere);
			paramDebutEnchereDate = manager.stringVersDate(paramDebutEnchere);
			paramDebutEnchereBoolean = true;
			System.out.println(paramDebutEnchereDate);
		}
		
		// Contrôle du paramètre date de fin d'enchère
		compareDateMax = paramFinEnchere.compareTo(paramDebutEnchere);
		if (!manager.verifierTailleChampOk(paramFinEnchere, NBR_CAR_ARTICLE)) {
			messageErreurMap.put(PARAM_FIN_ENCHERE, ERR_TAILLE + NBR_CAR_ARTICLE);
		} else if (compareDateMax < 0 ) {
			messageErreurMap.put(PARAM_FIN_ENCHERE, ERR_DATE);
		} else {
			System.out.println(paramFinEnchereDate);
			messageOkMap.put(PARAM_FIN_ENCHERE, paramFinEnchere);
			paramFinEnchereDate = manager.stringVersDate(paramFinEnchere);
			paramFinEnchereBoolean = true;
			System.out.println(paramFinEnchereDate);
		}
		
		// Contrôle du paramètre rue
		if (!manager.verifierTailleChampOk(paramRue, NBR_CAR_ARTICLE)) {
			messageErreurMap.put(PARAM_RUE, ERR_TAILLE + NBR_CAR_ARTICLE);
		} else {
			messageOkMap.put(PARAM_RUE, paramRue);
			paramRueBoolean = true;
		}
		
		// Contrôle du paramètre code postal
		if (!manager.verifierTailleChampOk(paramcodePostal, NBR_CAR_CODE_POSTAL)) {
			messageErreurMap.put(PARAM_CODE_POSTAL, ERR_TAILLE + NBR_CAR_CODE_POSTAL);
		} else if (!manager.verifierSiEntier(paramcodePostal)) {
			messageErreurMap.put(PARAM_CODE_POSTAL, ERR_CODE_POSTAL);
		} else {
			messageOkMap.put(PARAM_CODE_POSTAL, paramcodePostal);
			paramcodePostalBoolean = true;
		}
		
		// Contrôle du paramètre ville
		if (!manager.verifierTailleChampOk(paramVille, NBR_CAR_ARTICLE)) {
			messageErreurMap.put(PARAM_VILLE, ERR_TAILLE + NBR_CAR_ARTICLE);
		} else {
			messageOkMap.put(PARAM_VILLE, paramVille);
			paramVilleBoolean = true;
		}
		
		// Vérification que toutes les conditions soient remplies. Si c'est le cas, l'article est ajouté à la base de données
		if (paramArticleBoolean == true && paramDescriptionBoolean == true && paramCategorieBoolean == true && paramPrixBoolean == true &&
				paramDebutEnchereBoolean == true && paramFinEnchereBoolean == true && paramRueBoolean == true && paramcodePostalBoolean == true &&
				paramVilleBoolean == true) {
			try {
				utilisateur = manager.getUtilisateurByID(no_utilisateur);
				utilisateur.setVille(paramVille);
				utilisateur.setCodePostal(paramcodePostal);
				utilisateur.setRue(paramRue);
				manager.getInsertArticle(paramArticle, paramDescription, paramDebutEnchereDate, paramFinEnchereDate, paramPrixInt, categorie, utilisateur);
				articleAjoute = true;
			} catch (BusinessException e) {
				messageErreurMap.put(PARAM_INSERTION, ERR_INSERTION);
			}
		}
		
		// Si l'article est ajouté, on redirige vers la page d'accueil
		if (articleAjoute) {
			// Redirection vers la page d'accueil:
			RequestDispatcher rd = request.getRequestDispatcher(ACCUEIL_CONNEXION_JSP);
			if (rd != null) {
				rd.forward(request, response);
			}
		} else {
			// Si non, on ajoute les erreurs dans request et on retourne la page d'inscription
			request.setAttribute("erreurs", messageErreurMap);
			request.setAttribute("corrects", messageOkMap);
			// Redirection vers la page d'inscription :
			RequestDispatcher rd = request.getRequestDispatcher(NOUVELLE_VENTE_JSP);
			if (rd != null) {
				rd.forward(request, response);
			}
		}
	}

}
