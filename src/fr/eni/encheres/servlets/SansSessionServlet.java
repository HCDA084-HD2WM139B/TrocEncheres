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
 * 
 * Servlet contrôlant l'accès aux pages non connecté (en fonction s'il y a des enchères en cours)
 * 
 */
@WebServlet("/sanssession")
public class SansSessionServlet extends HttpServlet {
	
	
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
	private static final String JSP_ACCUEIL_SANS_CONNEXION = "/WEB-INF/jsp/AccueilSansConnexion.jsp";
	private static final String SERVLET_DEMANDE_CONNECTION = "/connexion";

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
	 * Méthode controlant l'affichage de la page d'accueil non connecté. 
	 * Elle charge les articles possédant une enchère en cours. Elle redirige vers la page connexion si aucunes ressources n'est à afficher (Categories, et Articles).
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		if (!listeCategories.isEmpty() && !listeArticlesComplete.isEmpty() ) {
			// Dépot du résultat dans l'espace d'échange (contexte de requete)
			request.setAttribute(ATTRIBUT_LISTE_CATEGORIES, listeCategories);
			request.setAttribute(ATTRIBUT_LISTE_ARTICLES, listeArticlesComplete);
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
	 * Méthode controlant la recherche d'un article en mode déconnecté. 
	 * Cette méthode agit sur la variable d'instance "listeArticleAfficher". 
	 * Elle repasse le controle à la méthode doGet pour réactualiser les données.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Declarations 
		RequestDispatcher rd = null;
		String saisieArticle = null;
		String selectionCategories = null;
		List<Article> listeArticlesFiltres = new ArrayList<Article>();
		
		// Récupération des parametres de filtres
		saisieArticle = request.getParameter(NAME_PARAM_ARTICLE_FORMULAIRE);
		selectionCategories = request.getParameter(NAME_PARAM_CATEGORIE_FORMULAIRE);
		
		// on re-initialise la liste à afficher car c'est une nouvelle recherche
		listeArticlesAfficher = new ArrayList<Article>();
		
		// si on a une categorie en filtre on agit sur listeArticlesFiltres
		if ( !(CATEGORIE_PAR_DEFAUT).equals(selectionCategories) ) {
			// on parcours la liste complete pour prendre que ceux de la categorie
			for (Article article : this.listeArticlesComplete) {
				// si les "id categories" correspondent on l'ajoute
				if (article.getCategorie().getnoCategorie().equals(Integer.parseInt(selectionCategories))) {
					listeArticlesFiltres.add(article);
				}
			}
		} else {
			listeArticlesFiltres = listeArticlesComplete;
		}
		// si on a une saisie de recherche, on affine la recherche
		if (!saisieArticle.isEmpty()) {
			// on parcours la liste filtrée pour prendre que ceux qui ont le nom de l'objet
			for (Article article : listeArticlesFiltres) {
				// si les noms d'objet correspondent
				if (article.getNomArticle().toLowerCase().equals(saisieArticle.toLowerCase())) {
					listeArticlesAfficher.add(article);
				}
			}
		} else {
			listeArticlesAfficher = listeArticlesFiltres;
		}
		
		// s'il y a des articles à afficher on redirige vers la jsp ; sinon on appel la méthod doGet pour réactualiser les données :
		if (!listeCategories.isEmpty() && !listeArticlesAfficher.isEmpty() ) {
			// Dépot du résultat dans l'espace d'échange (contexte de requete)
			request.setAttribute(ATTRIBUT_LISTE_CATEGORIES, listeCategories);
			request.setAttribute(ATTRIBUT_LISTE_ARTICLES, listeArticlesAfficher);
			// Transfert de l'affichage à la JSP
			rd = request.getRequestDispatcher(JSP_ACCUEIL_SANS_CONNEXION);
			rd.forward(request, response);
		} else {
			doGet(request, response);
		}
	}
	

}
