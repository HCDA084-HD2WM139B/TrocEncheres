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
import fr.eni.encheres.bo.Article;

@WebServlet("/DetailEnchereServlet")
public class DetailEnchereServlet extends HttpServlet {
	
	//Constante de paramètre
	private static final String PARAM_PROPOSITION_ENCHERE = "proposition";
	private static final String PARAM_ID_ARTICLE = "id";
	
	//Constante de redirection
	private static final String DETAIL_ENCHERE_JSP = "/WEB-INF/jsp/DetailEnchere.jsp";

	
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Article articleTrouve = null;
		
		//Récupération du paramètre URL de l'id article
		String sNo_article = request.getParameter(PARAM_ID_ARTICLE);
		
		int no_Article = Integer.parseInt(sNo_article);
		
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		try {
			articleTrouve = manager.selectArticleById(no_Article);
			System.out.println(articleTrouve);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	
		request.setAttribute("detailArticle", articleTrouve);
		request.setAttribute("undisplayLinkNavBar", "none");
		RequestDispatcher rd = request.getRequestDispatcher(DETAIL_ENCHERE_JSP);
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Récupération des paramètres 
		
		String sPropositionEnchere = request.getParameter(PARAM_PROPOSITION_ENCHERE);
		String sProposition
		
		int propositionEnchere = Integer.parseInt(sPropositionEnchere);
		
		//EnchereManager manager = EnchereManager.getEnchereManager();

		}
		
		//Vérifier que l'acheteur a un crédit de points supérieur au prixVente
		
		//Vérifier que l'enchère ne se fasse pas si la date de fin est dépassé
		
		//Vérifier que l'acheteur n'est pas le vendeur 

	//Vérifier que l'enchère est supérieure au prixVente ou au prixInitial si pas d'enchères

	public boolean propEnchereSup(int pProposition, int pPrixInitial ) {
		boolean resultat = false;
		if(pProposition > pPrixInitial) {
			resultat = true;
		}
	
		return resultat;
	
}
}
