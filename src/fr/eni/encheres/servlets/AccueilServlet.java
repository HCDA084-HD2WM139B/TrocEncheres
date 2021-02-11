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
import fr.eni.encheres.bo.Categorie;

/**
 * Servlet implementation class AccueilServlet
 */

public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ATTRIBUT_LISTE_CATEGORIES = "listeCategories";
	private static final String JSP_ACCUEIL = "/WEB-INF/jsp/AccueilSansConnexion.jsp";
	private static final String JSP_CONNECTION = "/cnx";
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Recherche des categories -> appel à la BLL
		CategorieManager categorieManager = new CategorieManager();
		List<Categorie> listeCategories = null;
		listeCategories = categorieManager.selectionnerToutesLesCategories();
			if (listeCategories.isEmpty()) {
				RequestDispatcher rd = request.getRequestDispatcher(JSP_CONNECTION);
				rd.forward(request, response);
			} else {
				// Transfert de l'affichage à la JSP
				RequestDispatcher rd = request.getRequestDispatcher(JSP_ACCUEIL);
				rd.forward(request, response);
				// Dépot du résultat dans l'espace d'échange (contexte de requete)
				request.setAttribute(ATTRIBUT_LISTE_CATEGORIES, listeCategories);
			}

		System.out.println(listeCategories.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
