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

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;

/**
 * Servlet implementation class SansSessionServlet
 * Servlet contrôlant l'accès aux pages non connecté (en fonction s'il y a des enchères en cours)
 * 
 */
@WebServlet("/sanssession")
public class SansSessionServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	// Noms des attributs des contextes de requetes
	private static final String ATTRIBUT_LISTE_CATEGORIES = "listeCategories";
	private static final String ATTRIBUT_LISTE_ARTICLES = "listeArticles";
	// routes de redirections
	private static final String JSP_ACCUEIL_SANS_CONNEXION = "/WEB-INF/jsp/AccueilSansConnexion.jsp";
	private static final String SERVLET_DEMANDE_CONNECTION = "/connexion";

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// declarations :
		RequestDispatcher rd = null;
		List<Categorie> listeCategories = null;
		List<Article> listeArticles = null;
		
		// Appel à la BLL
		try {
			EnchereManager enchereManager = EnchereManager.getEnchereManager();

			// Recherche des articles
			listeArticles = enchereManager.getAllSales();
			
			// Recherche des categories
			listeCategories = enchereManager.selectionnerToutesLesCategories();
		} catch (BusinessException be) {
			be.printStackTrace();
		}

		if (!listeCategories.isEmpty() && !listeArticles.isEmpty() ) {
			// Dépot du résultat dans l'espace d'échange (contexte de requete)
			request.setAttribute(ATTRIBUT_LISTE_CATEGORIES, listeCategories);
			request.setAttribute(ATTRIBUT_LISTE_ARTICLES, listeArticles);
			// Transfert de l'affichage à la JSP
			rd = request.getRequestDispatcher(JSP_ACCUEIL_SANS_CONNEXION);
			rd.forward(request, response);
		} else {
			// Redirection vers la connexion
			rd = request.getRequestDispatcher(SERVLET_DEMANDE_CONNECTION);
			rd.forward(request, response);
		}
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
