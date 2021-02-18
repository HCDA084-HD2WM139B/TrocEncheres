package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;

/**
 * Servlet contrôlant l'inscription d'un utilisateur selon différentes règles métier.
 */
@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
	
	//Constantes de paramètres
	private static final String PARAM_MOT_DE_PASSE_CONF = "motDePasseConf";
	private static final String PARAM_MOT_DE_PASSE = "motDePasse";
	private static final String PARAM_VILLE = "ville";
	private static final String PARAM_CODE_POSTAL = "codePostal";
	private static final String PARAM_RUE = "rue";
	private static final String PARAM_TELEPHONE = "telephone";
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_PRENOM = "prenom";
	private static final String PARAM_NOM = "nom";
	private static final String PARAM_PSEUDO = "pseudo";
	//Constantes de messages d'erreurs
	private static final String MOT_DE_PASSE_KO = "Le mot de passe ne correpond pas à la confirmation.";
	private static final String EMAIL_KO = "Le format de l'email n'est pas correcte";
	//Constantes de redirection 
	private static final String ACCUEIL_SERVLET = "/encheres";
	private static final String INSCRIPTION_JSP = "/WEB-INF/jsp/Inscription.jsp";
	
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("undisplayLinkNavBar", "none");
		RequestDispatcher rd = request.getRequestDispatcher(INSCRIPTION_JSP);
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Déclaration des variables
		int valeurMax = 0;
		boolean tailleChamp = true;
		boolean pseudoUtilise = true;
		boolean emailUtilise = true;
		boolean erreurMotDePasse = true;
		boolean utilisateurAjoute = false;
		boolean formatEmail = false;
		List<String> listErreurs = new ArrayList<String>();
		
		// Récupération des paramètres et stockage dans une Map pour un stockage clé / valeur
		// LinkedHashMap permet d'utiliser les clés dans l'ordre d'insertion
		Map<String, String> parametre = new LinkedHashMap<String, String>();
		parametre.put(PARAM_PSEUDO, request.getParameter(PARAM_PSEUDO));
		parametre.put(PARAM_NOM, request.getParameter(PARAM_NOM));
		parametre.put(PARAM_PRENOM, request.getParameter(PARAM_PRENOM));
		parametre.put(PARAM_EMAIL, request.getParameter(PARAM_EMAIL));
		parametre.put(PARAM_TELEPHONE, request.getParameter(PARAM_TELEPHONE));
		parametre.put(PARAM_RUE, request.getParameter(PARAM_RUE));
		parametre.put(PARAM_CODE_POSTAL, request.getParameter(PARAM_CODE_POSTAL));
		parametre.put(PARAM_VILLE, request.getParameter(PARAM_VILLE));
		parametre.put(PARAM_MOT_DE_PASSE, request.getParameter(PARAM_MOT_DE_PASSE));		
		parametre.put(PARAM_MOT_DE_PASSE_CONF, request.getParameter(PARAM_MOT_DE_PASSE_CONF));
		
		// Appel du manager pour appeler les méthodes
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		for(Entry<String, String> entree:parametre.entrySet()) {
			// La taille maximum du champ est défini par la méthode valeurMax()
			valeurMax = manager.valeurMax(entree.getKey());
			// Vérification de la taille du champ qui doit être compris entre deux et valeurMax. (Le champ téléphone est exclu car il peut être nul
			if (!manager.verifierTailleChamp(entree.getValue(), valeurMax) && !entree.getKey().contains(PARAM_TELEPHONE) ) {
				listErreurs.add("Le champ " + entree.getKey() + " doit être compris entre 2 et " + valeurMax + " caractères.");
				tailleChamp = false;
			}
		}
		
		//TODO Vérification que le pseudo est alphanumérique
		
		// Vérification que le format de l'email est correct
		if(manager.verifFormatEmail(parametre.get(PARAM_EMAIL))) {
			formatEmail = true;
		} else {
			listErreurs.add(EMAIL_KO);
		}
		
		// Vérification que le code postal est numérique

		// Vérification que le mot de passe est égal à la confirmation
		try {
			if (manager.verifMotDePasse(parametre.get(PARAM_MOT_DE_PASSE), parametre.get(PARAM_MOT_DE_PASSE_CONF))) {
				erreurMotDePasse = false;
			} else {
				listErreurs.add(MOT_DE_PASSE_KO);
			}
		} catch (BusinessException e1) {
			
		}
		
		// Vérification dans la base de données si le pseudo est déjà utilisé
		try {
			if (!manager.getPseudoExiste(parametre.get(PARAM_PSEUDO))){
				pseudoUtilise = false;
			} else {
				listErreurs.add("Le pseudo " + parametre.get(PARAM_PSEUDO) + " est déjà utilisé.");
			}
		} catch (BusinessException e) {
			
		}
		
		// Vérification dans la base de données si l'email est déjà utilisé
		try {
			if (!manager.getEmailExiste(parametre.get(PARAM_EMAIL))){
				emailUtilise = false;
			} else {
				listErreurs.add("L'email " + parametre.get(PARAM_EMAIL) + " est déjà utilisé.");
			}
		} catch (BusinessException e) {
			
		}
				
		// Vérification que toutes les conditions soient remplies. Si c'est le cas, l'utilisateur est ajouté à la base de données
		if (tailleChamp == true && pseudoUtilise == false && emailUtilise == false && erreurMotDePasse == false && formatEmail == true) {
			try {
				manager.getInsertUtilisateur(parametre.get(PARAM_PSEUDO), parametre.get(PARAM_PRENOM), parametre.get(PARAM_TELEPHONE), parametre.get(PARAM_CODE_POSTAL),
						parametre.get(PARAM_MOT_DE_PASSE), parametre.get(PARAM_NOM), parametre.get(PARAM_EMAIL), parametre.get(PARAM_RUE), parametre.get(PARAM_VILLE));
				utilisateurAjoute = true;
			} catch (BusinessException e) {
				
			}
		} 
		
		// Si l'utilisateur est ajouté, on redirige vers la page d'accueil
		if (utilisateurAjoute) {
			// Redirection vers la page d'accueil:
			RequestDispatcher rd = request.getRequestDispatcher(ACCUEIL_SERVLET);
			if (rd != null) {
				rd.forward(request, response);
			}
		} else {
			// Si non, on ajoute les erreurs dans request et on retourne la page d'inscription
			request.setAttribute("erreurs", listErreurs);
			// Redirection vers la page d'inscription :
			RequestDispatcher rd = request.getRequestDispatcher(INSCRIPTION_JSP);
			if (rd != null) {
				rd.forward(request, response);
			}
		}
	}

}
