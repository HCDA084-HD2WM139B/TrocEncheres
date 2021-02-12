package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AccueilServlet
 * Point d'entrée de l'application 
 * Servlet conrôlant s'il y a déjà une session ouverte ou non
 */
public class AccueilServlet extends HttpServlet {
	
	// Constantes
	private static final long serialVersionUID = 1L;
	// routes de redirections
	private static final String JSP_ACCUEIL_CONNECTE = "/WEB-INF/jsp/AccueilConnexion.jsp";
	private static final String SERVLET_SANS_SESSION = "/sanssession";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// déclarations :
		RequestDispatcher rd = null;

		// test session ouverte ou non 
		if (request.getSession().getAttribute("utilisateurConnecte") != null) {
			
			// on redirige vers la page accueil connecté
			rd = request.getRequestDispatcher(JSP_ACCUEIL_CONNECTE);
			rd.forward(request, response);
		} else {
			// sinon on redirige vers la servlet qui contrôle l'accès aux pages non connectées
			rd = request.getRequestDispatcher(SERVLET_SANS_SESSION);
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
