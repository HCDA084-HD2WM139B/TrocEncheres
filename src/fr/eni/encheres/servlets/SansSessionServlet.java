package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;

/**
 * Servlet implementation class SansSessionServlet
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
		CategorieManager categorieManager = new CategorieManager();

		// Recherche des articles
		// listeArticles = categorieManager.(TODO Méthode)

		// Recherche des categories
		listeCategories = categorieManager.selectionnerToutesLesCategories();

		if (!listeCategories.isEmpty() /* && !listeArticles.isEmpty() */ ) {
			
			// D�pot du r�sultat dans l'espace d'�change (contexte de requete)
			request.setAttribute(ATTRIBUT_LISTE_CATEGORIES, listeCategories);
			request.setAttribute(ATTRIBUT_LISTE_ARTICLES, listeArticles);
			// Transfert de l'affichage � la JSP
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
		//
	}

}
