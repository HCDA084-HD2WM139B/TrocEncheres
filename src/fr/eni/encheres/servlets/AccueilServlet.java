package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
public class AccueilServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// Noms des attributs des contextes de requetes
	private static final String ATTRIBUT_LISTE_CATEGORIES = "listeCategories";
	private static final String ATTRIBUT_LISTE_ARTICLES = "listeArticles";
	// Noms des parametres du formulaire
	private static final String NAME_PARAM_ARTICLE_FORMULAIRE = "saisieArticle";
	private static final String NAME_PARAM_CATEGORIE_FORMULAIRE = "categories";
	// Nom de la categorie par defaut : "Toutes"
	private static final String CATEGORIE_PAR_DEFAUT = "0";
	// routes de redirections
	private static final String JSP_ACCUEIL_AVEC_CONNEXION = "/WEB-INF/jsp/AccueilConnexion.jsp";
	private static final String SERVLET_DEMANDE_CONNECTION = "/connexion";

	// Variable d'instance
	private List<Categorie> listeCategories;
	private List<Article> listCompleteOfSales;
	private List<Article> listSalesToDisplay;

	/**
	 * Méthode d'initialisation de la servlet.
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		this.listCompleteOfSales = new ArrayList<Article>();
		this.listeCategories = new ArrayList<Categorie>();
		this.listSalesToDisplay = new ArrayList<Article>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// declarations :
		RequestDispatcher rd = null;
		boolean isItConnected = false;

		// test de connexion
		isItConnected = (request.getSession().getAttribute("utilisateurConnecte") != null) ? true : false;

		// Appel à la BLL
		try {
			EnchereManager enchereManager = EnchereManager.getEnchereManager();
			// Recherche des articles
			listCompleteOfSales = enchereManager.getAllSales();
			// Recherche des categories
			listeCategories = enchereManager.selectionnerToutesLesCategories();
		} catch (BusinessException be) {
			be.printStackTrace();
		}
		

		if (!listeCategories.isEmpty() && !listCompleteOfSales.isEmpty()) {
			// Dépot du résultat dans l'espace d'échange (contexte de requete)
			request.setAttribute(ATTRIBUT_LISTE_CATEGORIES, listeCategories);
			request.setAttribute(ATTRIBUT_LISTE_ARTICLES, listCompleteOfSales);
			// Transfert de l'affichage à la JSP
			rd = request.getRequestDispatcher(JSP_ACCUEIL_AVEC_CONNEXION);
			rd.forward(request, response);
		} else {
			if (isItConnected) {
				// on envoi une reponse 404
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				// Redirection vers la connexion
				rd = request.getRequestDispatcher(SERVLET_DEMANDE_CONNECTION);
				rd.forward(request, response);
			}

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Declarations
		RequestDispatcher rd = null;
		String saisieArticle = null;
		String selectionCategories = null;
		String selectionInpRadio = null;
		String selectAllSales = null;
		String selectMyOfferInProgress = null;
		String selectMyOfferDone = null;
		String selectMySalesInProgress = null;
		String selectMySalesNotStarted = null;
		String selectMySalesDone = null;
		boolean isItConnected = false;
		Integer idUser = null;
		Date today = new Date(System.currentTimeMillis());
		List<Article> listSalesTemp = new ArrayList<Article>();
		List<Article> listSalesTempFilter = new ArrayList<Article>();

		// test de session ou non
		isItConnected = (request.getSession().getAttribute("utilisateurConnecte") != null) ? true : false;

		// Récupération des parametres de filtres
		saisieArticle = request.getParameter(NAME_PARAM_ARTICLE_FORMULAIRE);
		selectionCategories = request.getParameter(NAME_PARAM_CATEGORIE_FORMULAIRE);
		selectionInpRadio = request.getParameter("inp_radio"); // achat ou vente
		selectAllSales = request.getParameter("en_open");
		selectMyOfferInProgress = request.getParameter("en_encours");
		selectMyOfferDone = request.getParameter("en_val");
		selectMySalesInProgress = request.getParameter("ve_encours"); // on ou null
		selectMySalesNotStarted = request.getParameter("ve_null");
		selectMySalesDone = request.getParameter("ve_over");

		// Appel à la BLL
		try {
			EnchereManager enchereManager = EnchereManager.getEnchereManager();
			// Recherche des articles
			listCompleteOfSales = enchereManager.getAllSales();
		} catch (BusinessException be) {
			be.printStackTrace();
		}
		
		// on re-initialise la liste à afficher car c'est une nouvelle recherche
		listSalesToDisplay = new ArrayList<Article>();

		// si mode connecté
		if (isItConnected) {
			// on récupère l'ID de l'utilisateur connecté
			idUser = (Integer) request.getSession().getAttribute("id_utilisateur");

			// Si le type de recherche correspond à "mes ventes"
			if ("vente".equals(selectionInpRadio)) {
				// on parcours la liste totale des ventes
				for (Article article : listCompleteOfSales) {
					// si se sont celles de l'utilisateur :
					if (idUser == article.getVendeur().getNoUtilisateur()) {
						// si la case "mes ventes en cours" est cochée
						if ("on".equals(selectMySalesInProgress)) {
							if (article.getDateDebutEnchere().compareTo(today) < 0
									&& article.getDateFinEchere().compareTo(today) > 0) {
								listSalesTemp.add(article);
							}
						}
						// si la case "mes ventes débutées" est cochée
						if ("on".equals(selectMySalesNotStarted)) {
							if (article.getDateDebutEnchere().compareTo(today) > 0) {
								listSalesTemp.add(article);
							}
						}
						// si la case "mes ventes terminées" est cochée
						if ("on".equals(selectMySalesDone)) {
							if (article.getDateFinEchere().compareTo(today) < 0) {
								listSalesTemp.add(article);
							}
						}
					}
				}
				// Sinon si le type de recherche correspond à "achats"
			} else if ("achat".equals(selectionInpRadio)) {
				// si la case "encheres ouvertes" est cochée
				if ("on".equals(selectAllSales)) {
					listSalesTemp = listCompleteOfSales;
				}
				if ("on".equals(selectMyOfferInProgress)) {
					//
				}
				if ("on".equals(selectMyOfferDone)) {
					//
				}
			} else {
				// erreur
				// TODO
				// à l'initialisation lors de la connexion par defaut ce sont les cases "achats" et "encheres ouvertes" qui sont selectionees
				listSalesTemp = listCompleteOfSales;
			}
		// sinon, mode déconnecté
		} else {
			// on ne prend en compte uniquement la liste complete des ventes
			listSalesTemp = listCompleteOfSales;
		}

		// en mode connecté ou non :
		// si on a une categorie en filtre on agit sur listSalesTemp
		if (!(CATEGORIE_PAR_DEFAUT).equals(selectionCategories) && selectionCategories != null ) {
			// on parcours la liste complete pour prendre que ceux de la categorie
			for (Article article : listSalesTemp) {
				// si les "id categories" correspondent on l'ajoute
				if (article.getCategorie().getnoCategorie().equals(Integer.parseInt(selectionCategories))) {
					listSalesTempFilter.add(article);
				}
			}
		} else {

			listSalesTempFilter = listSalesTemp;
		}
		// si on a une saisie de recherche, on affine la recherche
		if (saisieArticle != null && !saisieArticle.isEmpty()) {
			// on parcours la liste filtrée pour prendre que ceux qui ont le nom de l'objet
			for (Article article : listSalesTempFilter) {
				// si les noms d'objet correspondent
				if (article.getNomArticle().toLowerCase().equals(saisieArticle.toLowerCase())) {
					listSalesToDisplay.add(article);
				}
			}
		} else {
			listSalesToDisplay = listSalesTempFilter;
		}
		
		// on redirige vers la jsp 
		// Dépot du résultat dans l'espace d'échange (contexte de requete)
		request.setAttribute(ATTRIBUT_LISTE_CATEGORIES, listeCategories);
		request.setAttribute(ATTRIBUT_LISTE_ARTICLES, listSalesToDisplay);
		// Transfert de l'affichage à la JSP
		rd = request.getRequestDispatcher(JSP_ACCUEIL_AVEC_CONNEXION);
		rd.forward(request, response);
		

	}

}
