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

/**
 * Servlet qui contrôle l'affichage des profils
 */
@WebServlet("/monProfil")
public class MonProfilServlet extends HttpServlet {
	private static final String AFFICHE_PROFIL_JSP = "/WEB-INF/jsp/AfficheProfil.jsp";
	private static final String PARAM_ID_UTILISATEUR = "id";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Déclarations
		Utilisateur utilisateurTrouve= null;
		
		//Récupération du paramètre
		String sNo_utilisateur = request.getParameter(PARAM_ID_UTILISATEUR);
		
		//Transforme le type Vue en type Modele pour préparer l'appel au Modele.
		int no_utilisateur = Integer.parseInt(sNo_utilisateur);
		//Appel à la BLL
		EnchereManager manager = EnchereManager.getEnchereManager();
		try {
			utilisateurTrouve = manager.getUtilisateurByID(no_utilisateur);
		} catch (BusinessException e) {

		}
				
		request.setAttribute("afficheUtilisateur", utilisateurTrouve);
		request.setAttribute("undisplayLinkNavBar", "none");
		RequestDispatcher rd = request.getRequestDispatcher(AFFICHE_PROFIL_JSP);
		rd.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
