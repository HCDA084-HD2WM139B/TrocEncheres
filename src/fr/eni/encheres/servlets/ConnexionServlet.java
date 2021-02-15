package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Utilisateur;


/**
 * Servlet controlant le point d'entrée vers la connexion à la page d'Accueil.
 * @author Groupe 3
 *
 */
@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {
	
	// Constantes :
	private static final long serialVersionUID = 1L;
	private static final int LOGIN_EMAIL = 1;
	private static final int LOGIN_PSEUDO = -1;
	private static final int LOGIN_ERROR = 0;
	// le nom exact des colonnes dans la table utilisateur en BDD :
	private static final String TYPE_REQUETE_EMAIL = "email";
	private static final String TYPE_REQUETE_PSEUDO = "pseudo";
	// les messages d'erreur et d'aide à la saisie :
	private static final String MSG_ERROR_MAIL_INVALIDE = "E-mail invalide.";
	private static final String MSG_ERROR_PSEUDO_INVALIDE = "Pseudo invalide.";
	private static final String MSG_ERROR_LOGIN_INVALIDE = "Entrez un pseudo alpha-numérique ou votre e-mail.";
	private static final String MSG_ERROR_PASSWORD_INVALIDE = "Mot de passe invalide.";
	private static final String MSG_ERROR_REQUETE = "Désolé le site est en maintenance.";
	private static final String MSG_ERROR_CONNEXION = "Identifiants incorrects.";
	// redirections 
	private static final String PAGE_CONNEXION = "WEB-INF/jsp/PageConnexion.jsp";
	//private static final String PAGE_ACCUEIL_CONNECTE = "WEB-INF/jsp/AccueilConnexion.jsp";
	private static final String SERVLET_ACCUEIL = "/encheres";
	
	

	/**
	 * Méthode GET de gestion de connexion à l'application (accès à la pageConnexion.jsp par l'URL)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Redirection définitive vers la pageConnexion.jsp
		request.setAttribute("undisplayLinkNavBar", "none");
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/PageConnexion.jsp");
		rd.forward(request, response);
	}

	
	/**
	 * Méthode POST de gestion de connexion à l'application (accès à la page accueilConnexion.jsp par la réception des éléments du formulaire de connexion)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Récupération des paramètres du formulaire de connexion
		String identifiant = request.getParameter("identifiant");
		String motDePasse = request.getParameter("motDePasse");

		// Appel du manager (BLL)
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		// Déclaration de variables
		boolean pseudoValide = false;
		boolean emailValide = false;
		boolean motDePasseValide = false;
		String typeRequete = TYPE_REQUETE_EMAIL;	// Email par defaut
		List<String> listErreurs = new ArrayList<String>();
		Utilisateur utilisateurTrouve = null;
		
		// Vérification du format du login (soit pseudo, soit Email, soit rien) :
		int resultatTypeIdentifiant = manager.getTypeIdentifiant(identifiant);
		
		// Vérification du login (Pseudo ou E-mail)
		switch (resultatTypeIdentifiant) {
		case LOGIN_EMAIL:
			// Controle si l' E-mail est valide :
			if (manager.verifierEmail(identifiant)) {
				emailValide = true;
				typeRequete = TYPE_REQUETE_EMAIL;
			} else {
				listErreurs.add(MSG_ERROR_MAIL_INVALIDE);
			}
			break;
		case LOGIN_PSEUDO:
			// Controle si le pseudo est valide :
			if (manager.verifierPseudo(identifiant)) {
				pseudoValide = true;
				typeRequete = TYPE_REQUETE_PSEUDO;
			} else {
				listErreurs.add(MSG_ERROR_PSEUDO_INVALIDE);
			}
			break;
		case LOGIN_ERROR:
			// Le login n'est ni d'un format pseudo, ni d'un format Email
			listErreurs.add(MSG_ERROR_LOGIN_INVALIDE);
			break;
		default:
			break;
		}
		
		// Controle si le mot de passe est valide :
		if (manager.verifierMotDePasse(motDePasse)) {
			motDePasseValide = true;
		} else {
			listErreurs.add(MSG_ERROR_PASSWORD_INVALIDE);
		}
		
		// traitement de la requete :
		try {
			// On appel la BLL pour réaliser la requete si le login et le MdP sont valides :
			if ( ( pseudoValide || emailValide ) && motDePasseValide ) {
				utilisateurTrouve = manager.getUtilisateurByLogin(typeRequete, identifiant, motDePasse);
				if (utilisateurTrouve == null) {
					listErreurs.add(MSG_ERROR_CONNEXION);
				}
			}
		} catch (BusinessException be) {
			// en cas d'erreur de la requete, on signale une erreur
			listErreurs.add(MSG_ERROR_REQUETE);
			be.printStackTrace();
		}
		
		// Si la requete nous renvoi un utilisateur ou non : 
		if (utilisateurTrouve == null) {
			// On ajoute les erreurs dans request :
			request.setAttribute("erreurs", listErreurs);
			// Redirection définitive à la page de connexion :
			RequestDispatcher rd = request.getRequestDispatcher(PAGE_CONNEXION);
			rd.forward(request, response);
		} else {
			// On ouvre une session :
			HttpSession session = request.getSession();
			// On ajoute l'Id utilisateur en attribut de session (pour pouvoir le reconnaitre)
			session.setAttribute("utilisateurConnecte", "connecte");
			session.setAttribute("pseudo", utilisateurTrouve.getPseudo());
			session.setAttribute("id_utilisateur", utilisateurTrouve.getNoUtilisateur());
			// Redirection définitive à la page d'accueil :
			RequestDispatcher rd = request.getRequestDispatcher(SERVLET_ACCUEIL);
			rd.forward(request, response);
		}
	}

}
