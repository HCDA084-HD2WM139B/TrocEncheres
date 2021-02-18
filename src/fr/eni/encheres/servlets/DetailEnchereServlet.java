package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

@WebServlet("/DetailEnchereServlet")
public class DetailEnchereServlet extends HttpServlet {
	
	//Constante de paramètre
	private static final String PARAM_PROPOSITION_ENCHERE = "proposition";
	private static final String PARAM_ID_ARTICLE = "idArticle";
	
	//Constante de redirection
	private static final String DETAIL_ENCHERE_JSP = "/WEB-INF/jsp/DetailEnchere.jsp";
	//Déclaration des variables 
	Article articleTrouve = null;
	int IdSession = 0;

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Récupération du paramètre URL de l'id article
		String sNo_article = request.getParameter(PARAM_ID_ARTICLE);
		//Transformation du type du paramètre en int pour pouvoir le manipuler avec le manager
		int no_Article = Integer.parseInt(sNo_article);
		
		//Récupération de l'id utilisateur connecté
		IdSession = (int) request.getSession().getAttribute("id_utilisateur");

		//Appel au manager pour utiliser les méthodes de la classe
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		//Appel de la méthode du manager qui nous renvoie un article et ses informations
		try {
			articleTrouve = manager.selectArticleById(no_Article);
		} catch (BusinessException e) {
			e.printStackTrace();
		}


		//On set un attribut de l'article trouvé afin de l'utiliser dans la jsp
		request.setAttribute("detailArticle", articleTrouve);
		//On set un attribut pour que la barre de navigation disparaisse dans la jsp
		request.setAttribute("undisplayLinkNavBar", "none");
		//On redirige la requête vers la page d'enchère jsp.
		RequestDispatcher rd = request.getRequestDispatcher(DETAIL_ENCHERE_JSP);
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Déclarations de variables
		int creditAcheteur = 0;
		int idAcheteur = 0;
		
		//Récupération des paramètres 
		String sPropositionEnchere = request.getParameter(PARAM_PROPOSITION_ENCHERE);
		//Transformer la proposition en type int
		int propositionEnchere = Integer.parseInt(sPropositionEnchere);
		//Récupération des différentes informations de l'article trouvé 
		int pxInitial = articleTrouve.getPrixInitial();
		int pxVente = articleTrouve.getPrixVente();
		Date datedeFin = articleTrouve.getDateFinEchere();
		int idVendeur = articleTrouve.getVendeur().getNoUtilisateur();
		boolean statutUtilisateur = false;
		boolean encherePossible = false;
		boolean propEnchere=false;
		boolean creditUpdated = false;
		int nouveauCredit = 0;
		
		//Appel au manager afin d'utiliser les méthodes dans la classe
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		//Récupération du crédit de l'acheteur et de son ID
		try {
			creditAcheteur = manager.getUtilisateurByID(IdSession).getCredit();
			idAcheteur = manager.getUtilisateurByID(IdSession).getNoUtilisateur();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
			//Vérification si l'ID de l'utilisateur est différent de celui de l'acheteur 
			if(manager.VerifStatutUtilisateur(idAcheteur, idVendeur)) {
				statutUtilisateur = true;
			} 
			
			//Vérification que de la date de fin d'enchère soit supérieure ou égale à la date du jour
			if(manager.verifDateEnchereNonFinie(datedeFin, manager.stringVersDate(manager.dateJour()))) {
				encherePossible = true;
			} 
			
			//Si il n'y a pas d'enchères alors on vérifie la proposition de l'acheteur est supérieure à l'enchère (prixVente)
			if(pxVente == 0) {
				try {
					propEnchere = manager.propEnchereSup(creditAcheteur, propositionEnchere, pxInitial);
					nouveauCredit = manager.calculNouveauCreditAcheteur(creditAcheteur, pxInitial);
				} catch (BusinessException e) {
					e.printStackTrace();
				}

			} else {
				try {
					propEnchere = manager.propEnchereSup(creditAcheteur, propositionEnchere, pxVente);
					nouveauCredit = manager.calculNouveauCreditAcheteur(creditAcheteur, pxVente);
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}
			
			if(statutUtilisateur == true && encherePossible == true && propEnchere == true) {
				try {
					creditUpdated = manager.creditUpdated(nouveauCredit, idAcheteur);
				} catch (BusinessException e) {
					e.printStackTrace();
				}
			}
			
		}
		

}
