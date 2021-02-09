package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;


@WebServlet("/listeEncheresConnecte")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Récupération des paramètres
		String identifiant = request.getParameter("identifiant");
		String motDePasse = request.getParameter("motDePasse");
		
		// Appel du manager pour appeler les méthodes de vérification de connexion !!!
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		if(!manager.verifierIdentifiant(identifiant)) {
			request.setAttribute("erreurConnexionId", "L'identifiant saisi est incorrect.");
			
			RequestDispatcher rd = request.getRequestDispatcher("/connexion.jsp");
			rd.forward(request, response);
		}
		
		if(!manager.verifierMotDePasse(motDePasse)) {
			request.setAttribute("erreurConnexionMdp", "Le mot de passe saisi est incorrect.");
			
			RequestDispatcher rd = request.getRequestDispatcher("/connexion.jsp");
			rd.forward(request, response);
		}
		
		// verif si existant en BDD 
		
		// si ok -> redirection + session 
		
		// sinon -> message 
		
	}

}
