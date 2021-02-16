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
 * Servlet implementation class AvecSessionServlet
 */
@WebServlet("/avecsession")
public class AvecSessionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// Noms des attributs des contextes de requetes
	private static final String ATTRIBUT_LISTE_CATEGORIES = "listeCategories";
	private static final String ATTRIBUT_LISTE_ARTICLES = "listeArticles";
	// Noms des parametres du formulaire
	private static final String NAME_PARAM_ARTICLE_FORMULAIRE = "saisieArticle";
	private static final String NAME_PARAM_CATEGORIE_FORMULAIRE = "categories";
	// Nom de la categorie par defaut : "Toutes"
	private static final String CATEGORIE_PAR_DEFAUT = "Toutes";
	// routes de redirections
	private static final String JSP_ACCUEIL_AVEC_CONNEXION = "/WEB-INF/jsp/AccueilConnexion.jsp";

	// Variable d'instance
	private List<Article> listeArticlesComplete;
	private List<Article> listeArticlesAfficher;
	private List<Categorie> listeCategories;

	/**
	 * Méthode d'initialisation de la servlet.
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		this.listeArticlesAfficher = new ArrayList<Article>();
		this.listeArticlesComplete = new ArrayList<Article>();
		this.listeCategories = new ArrayList<Categorie>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
System.out.println("servlet avec session");
		// declarations :
		RequestDispatcher rd = null;
		
		// Appel à la BLL
		try {
			EnchereManager enchereManager = EnchereManager.getEnchereManager();
			// Recherche des articles
			listeArticlesComplete = enchereManager.getAllSales();
			// Recherche des categories
			listeCategories = enchereManager.selectionnerToutesLesCategories();
		} catch (BusinessException be) {
			be.printStackTrace();
		}

		if (!listeCategories.isEmpty() && !listeArticlesComplete.isEmpty()) {
			// Dépot du résultat dans l'espace d'échange (contexte de requete)
			request.setAttribute(ATTRIBUT_LISTE_CATEGORIES, listeCategories);
			request.setAttribute(ATTRIBUT_LISTE_ARTICLES, listeArticlesComplete);
			// Transfert de l'affichage à la JSP
			rd = request.getRequestDispatcher(JSP_ACCUEIL_AVEC_CONNEXION);
			rd.forward(request, response);
		} else {
			// on envoi une reponse 404
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
