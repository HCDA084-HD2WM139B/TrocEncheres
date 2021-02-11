package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Inscription.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO à retirer après le test
		System.out.println("Passage dans le DoGet");
		
		// Déclaration des variables
		int valeurMax = 0;
		boolean champMax = true;
		boolean champMin = true;
		boolean pseudoUtilise = true;
		boolean emailUtilise = true;
		boolean erreurMotDePasse = true;
		boolean utilisateurAjoute = false;
		List<String> listErreurs = new ArrayList<String>();
		
		// Récupération des paramètres et stockage dans une Map pour un stockage clé / valeur
		// LinkedHashMap permet d'utiliser les clés dans l'ordre d'insertion
		Map<String, String> parametre = new LinkedHashMap<String, String>();
		parametre.put("pseudo", request.getParameter("pseudo"));
		parametre.put("nom", request.getParameter("nom"));
		parametre.put("prenom", request.getParameter("prenom"));
		parametre.put("email", request.getParameter("email"));
		parametre.put("telephone", request.getParameter("telephone"));
		parametre.put("rue", request.getParameter("rue"));
		parametre.put("codePostal", request.getParameter("codePostal"));
		parametre.put("ville", request.getParameter("ville"));
		parametre.put("motDePasse", request.getParameter("motDePasse"));		
		parametre.put("motDePasseConf", request.getParameter("motDePasseConf"));
		
		
		// Appel du manager pour appeler les méthodes
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		for(Entry<String, String> entree:parametre.entrySet()) {
			// La taille maximum du champ est défini par la méthode valeurMax()
			valeurMax = manager.valeurMax(entree.getKey());
			// Vérification de la taille du champ qui doit être compris entre deux et valeurMax. (Le champ téléphone est exclu car il peut être nul
			if (!manager.verifierTailleChamp(entree.getValue(), valeurMax) && !entree.getKey().contains("telephone") ) {
				listErreurs.add("Le champ " + entree.getKey() + " doit être compris entre 2 et " + valeurMax + " caractères.");
				champMin = false;
			}
			// Vérification que le mot de passe est égal à la confirmation
			try {
				if (manager.verifMotDePasse(parametre.get("motDePasse"), parametre.get("motDePasseConf"))) {
					erreurMotDePasse = false;
				} else {
					listErreurs.add("Le mot de passe ne correpond pas à la confirmation.");
				}
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		// Vérification dans la base de données si le pseudo est déjà utilisé
		try {
			if (!manager.getPseudoExiste(parametre.get("pseudo"))){
				pseudoUtilise = false;
			} else {
				listErreurs.add("Le pseudo " + parametre.get("pseudo") + " est déjà utilisé.");
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Vérification dans la base de données si l'email est déjà utilisé
		try {
			if (!manager.getEmailExiste(parametre.get("email"))){
				emailUtilise = false;
			} else {
				listErreurs.add("L'email " + parametre.get("email") + " est déjà utilisé.");
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// Vérification que toutes les conditions soient remplies. Si c'est le cas, l'utilisateur est ajouté à la base de données
		if (champMax == true && champMin == true && pseudoUtilise == false && emailUtilise == false && erreurMotDePasse == false) {
			try {
				manager.getInsertUtilisateur(parametre.get("pseudo"), parametre.get("prenom"), parametre.get("telephone"), parametre.get("codePostal"),
						parametre.get("motDePasse"), parametre.get("nom"), parametre.get("email"), parametre.get("rue"), parametre.get("ville"));
				utilisateurAjoute = true;
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		// Si listErreurs est différente de nulle on redirige vers la page d'accueil
		if (utilisateurAjoute) {
			// Redirection vers la page d'inscription :
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/accueil.jsp");
			rd.forward(request, response);
		} else {
			// On ajoute les erreurs dans request :
			request.setAttribute("erreurs", listErreurs);
			// Redirection vers la page d'inscription :
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/Inscription.jsp");
			rd.forward(request, response);
		}
	}

}
