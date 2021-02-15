package fr.eni.encheres.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import fr.eni.encheres.bll.EnchereManager;

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
	private static final String PARAM_PHOTO = "photo";
	private static final String PARAM_PRIX = "prix";
	private static final String PARAM_DEBUT_ENCHERE = "debutEnchere";
	private static final String PARAM_FIN_ENCHERE = "finEnchere";
	private static final String PARAM_RUE = "rue";
	private static final String PARAM_CODE_POSTAL = "codePostal";
	private static final String PARAM_VILLE = "ville";
	private static final int NBR_CAR_ARTICLE = 30;
	private static final int NBR_CAR_MIN = 1;
	// Constantes message erreur
	private static final String ERR_ARTICLE = "Le nombre de caractère doit être compris entre " + NBR_CAR_MIN + " et " + NBR_CAR_ARTICLE ;
	// Variables message erreur
	private static String messageErreur = "";
	// Variables de paramètres
	private static String paramArticle = "";
	private static String paramDescription = "";
	private static String paramCategorie = "";
	private static String paramPhoto = "";
	private static String paramPrix = "";
	private static String paramDebutEnchere = "";
	private static String paramFinEnchere = "";
	private static String paramRue = "";
	private static String paramcodePostal = "";
	private static String paramVille = "";
	//Constantes de redirection 
	private static final String NOUVELLE_VENTE_JSP = "/WEB-INF/jsp/NouvelleVente.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher(NOUVELLE_VENTE_JSP);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Récupération des paramètres sur le fichier NouvelleVente.jsp
		paramArticle = request.getParameter(PARAM_ARTICLE);
		paramDescription = request.getParameter(PARAM_DESCRIPTION);
		paramCategorie = request.getParameter(PARAM_CATEGORIE);
		paramPhoto = request.getParameter(PARAM_PHOTO);
		paramPrix = request.getParameter(PARAM_PRIX);
		paramDebutEnchere = request.getParameter(PARAM_DEBUT_ENCHERE);
		paramFinEnchere = request.getParameter(PARAM_FIN_ENCHERE);
		paramRue = request.getParameter(PARAM_RUE);
		paramcodePostal = request.getParameter(PARAM_CODE_POSTAL);
		paramVille = request.getParameter(PARAM_VILLE);
		
		// Appel du manager pour appeler les méthodes
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		// Contrôle du paramètre article
		if (!manager.verifierTailleChamp(paramArticle, NBR_CAR_ARTICLE)) {
			messageErreur = ERR_ARTICLE + NBR_CAR_ARTICLE;
		}
		
		System.out.println(messageErreur);
		
		
		//		doGet(request, response);
		
//		// Déclaration des variables
//		int valeurMax = 0;
//		int compareDateMin = 0;
//		int compareDateMax = 0;
//		String erreurTaille = "";
//		List<String> listErreurs = new ArrayList<String>();
//		Map<String, String> messageErreur = new LinkedHashMap<String, String>();
//		
//		// Récupération des paramètres et stockage dans une Map pour un stockage clé / valeur
//		// LinkedHashMap permet d'utiliser les clés dans l'ordre d'insertion
//		Map<String, String> parametre = new LinkedHashMap<String, String>();
//		parametre.put(PARAM_ARTICLE, request.getParameter(PARAM_ARTICLE));
//		parametre.put(PARAM_DESCRIPTION, request.getParameter(PARAM_DESCRIPTION));
//		parametre.put(PARAM_CATEGORIE, request.getParameter(PARAM_CATEGORIE));
//		parametre.put(PARAM_PHOTO, request.getParameter(PARAM_PHOTO));
//		parametre.put(PARAM_PRIX, request.getParameter(PARAM_PRIX));
//		parametre.put(PARAM_DEBUT_ENCHERE, request.getParameter(PARAM_DEBUT_ENCHERE));
//		parametre.put(PARAM_FIN_ENCHERE, request.getParameter(PARAM_FIN_ENCHERE));
//		parametre.put(PARAM_RUE, request.getParameter(PARAM_RUE));
//		parametre.put(PARAM_CODE_POSTAL, request.getParameter(PARAM_CODE_POSTAL));		
//		parametre.put(PARAM_VILLE, request.getParameter(PARAM_VILLE));
//		
//		// Appel du manager pour appeler les méthodes
//		EnchereManager manager = EnchereManager.getEnchereManager();
//		
//		// On boucle sur la totalité de la MAP pour contrôler les champs
//		for(Entry<String, String> entree:parametre.entrySet()) {
//			// La taille maximum du champ est défini par la méthode valeurMax()
//			valeurMax = manager.valeurMax(entree.getKey());
//			// Vérification de la taille du champ qui doit être compris entre deux et valeurMax
//			if (!manager.verifierTailleChamp(entree.getValue(), valeurMax)) {
//				// Message d'erreur remonté à l'utilisateur si la taille est incorrecte
//				erreurTaille = "Le champ doit être comprise entre deux et " + valeurMax + " caractères.";
//			}
//			
//		
//		}
//		
//		// Vérification de la date renseignée
//		compareDateMin = parametre.get(PARAM_DEBUT_ENCHERE).compareTo(dateJour());
//		compareDateMax = parametre.get(PARAM_FIN_ENCHERE).compareTo(parametre.get(PARAM_DEBUT_ENCHERE));
//		if (compareDateMin >= 0 && compareDateMax >= 0 )  {
//			// compareDate = true;
//		} else {
//		listErreurs.add("Merci de vérifier que la date de début de l'enchère ne soit pas entérieure à la date d");
//		}
		
//		for(Entry<String, String> entree:parametre.entrySet()) {
//			// La taille maximum du champ est défini par la méthode valeurMax()
//			valeurMax = manager.valeurMax(entree.getKey());
//			// Vérification de la taille du champ qui doit être compris entre deux et valeurMax. (Le champ téléphone est exclu car il peut être nul
//			if (!manager.verifierTailleChamp(entree.getValue(), valeurMax)) {
//				// Message d'erreur remonté à l'utilisateur si la taille est incorrecte
//				messageErreur = Le champ " + entree.getKey() + " doit être compris entre 2 et " + valeurMax + " caractères."
////				listErreurs.add("Le champ " + entree.getKey() + " doit être compris entre 2 et " + valeurMax + " caractères.");
//				messageErreur.put(entree.getKey(), "Le champ " + entree.getKey() + " doit être compris entre 2 et " + valeurMax + " caractères.");
//				messageErreur.put(entree.getKey(), "Second message sur la même clé");
//				
//			} else {
//				System.out.println(entree.getValue());
//			}
//		}
//		
//		// Vérification de la date renseignée
//		int compareDateMin = parametre.get(PARAM_DEBUT_ENCHERE).compareTo(dateJour());
//		int compareDateMax = parametre.get(PARAM_FIN_ENCHERE).compareTo(parametre.get(PARAM_DEBUT_ENCHERE));
//		System.out.println(compareDateMin);
//		System.out.println(compareDateMax);
//		if (compareDateMin >= 0 && compareDateMax >= 0 )  {
//			System.out.println("La date du jour est égale ou inférieure à la date de début de l'enchère");
//		} else {
//			listErreurs.add("Merci de vérifier que la date de début de l'enchère ne soit pas entérieure à la date d");
//		}
//		
//		
//		// Vérification de la mise à prix
//		if (!parametre.get(PARAM_PRIX).isEmpty()) {
//			int prixEnchere = Integer.parseInt(parametre.get(PARAM_PRIX));
//			if (prixEnchere <= 0) {
//				listErreurs.add("Le prix de vente doit être supérieur à zéro.");
////				messageErreur.put(PARAM_PRIX, "Le prix de vente doit être supérieur à zéro.");
//			}
//		}

		
//		// Si l'article est ajouté, on redirige vers la page d'accueil
//		if (utilisateurAjoute) {
//			// Redirection vers la page d'accueil:
//			RequestDispatcher rd = request.getRequestDispatcher(ACCUEIL_CONNEXION_JSP);
//			if (rd != null) {
//				rd.forward(request, response);
//			}
//		} else {
			// Si non, on ajoute les erreurs dans request et on retourne la page d'inscription
//			request.setAttribute("erreurs", messageErreur);
//			// Redirection vers la page d'inscription :
//			RequestDispatcher rd = request.getRequestDispatcher(NOUVELLE_VENTE_JSP);
//			if (rd != null) {
//				rd.forward(request, response);
////			}
//		}
	}
		
		private String dateJour() {
			 Date actuelle = new Date();
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 String dat = dateFormat.format(actuelle);
			 return dat;
		}
		
	

}
