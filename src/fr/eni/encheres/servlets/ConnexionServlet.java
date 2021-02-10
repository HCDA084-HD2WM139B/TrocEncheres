package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet("/cnx")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet");
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/PageConnexion.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("doPOST");
		// Récupération des paramètres
		String identifiant = request.getParameter("identifiant");
		String motDePasse = request.getParameter("motDePasse");

		// Appel du manager pour appeler les méthodes de vérification de connexion
		EnchereManager manager = EnchereManager.getEnchereManager();

		int resultatTypeIdentifiant = manager.getTypeIdentifiant(identifiant);
		boolean loginCorrect = true;
		switch (resultatTypeIdentifiant) {
		case 1:
			if (!manager.verifierEmail(identifiant)) {
				request.setAttribute("erreurConnexionId", "L'email saisi est incorrect.");
				loginCorrect = false;
			}
			break;
		case -1:

			if (!manager.verifierPseudo(identifiant)) {
				request.setAttribute("erreurConnexionId", "Le pseudo saisi est incorrect.");
				loginCorrect = false;
			}
			break;
		case 0:
			request.setAttribute("erreurConnexionId", "Veuilez entrer votre email ou votre pseudo");
			loginCorrect = false;
			break;

		default:
			break;
		}

		// verif regles Mdp
		boolean motDePasseCorrect = true;

		if (!manager.verifierMotDePasse(motDePasse)) {
			request.setAttribute("erreurConnexionMdp", "Le mot de passe saisi est incorrect.");

			motDePasseCorrect = false;
		}

		if ( loginCorrect && motDePasseCorrect ) {
			//appel BDD pour verif identifiants existants
			try {
				Utilisateur utilisateurTrouve = manager.getUtilisateurByLogin(identifiant);
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			
		} else {
			//redirection page connexion pour erreur
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/PageConnexion.jsp");
			rd.forward(request, response);

		}
		

		

	}

}
