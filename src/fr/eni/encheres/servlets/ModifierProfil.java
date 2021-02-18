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
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet qui redirige vers la page ModifieProfil.jsp
 */
@WebServlet("/modifierProfil")
public class ModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SERVLET_MODIF_PROFIL = "/WEB-INF/jsp/ModifierProfil.jsp";
	private static final String SERVLET_ACCUEIL_SERVLET = "/encheres";
	private static final String PARAM_ID_UTILISATEUR = "id";


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
				//Déclarations
				Utilisateur utilisateurTrouve= null;
				
				//Récupération du paramètre URL
				String sNo_utilisateur = request.getParameter(PARAM_ID_UTILISATEUR);
				
				//Transforme le type Vue en type Modele pour préparer l'appel au Modele.
				int no_utilisateur = Integer.parseInt(sNo_utilisateur);
				
				//Appel à la BLL pour récupérer les informations 
				EnchereManager manager = EnchereManager.getEnchereManager();
				try {
					utilisateurTrouve = manager.getUtilisateurByID(no_utilisateur);
				} catch (BusinessException e) {
					
				}
				
				//Cast l'id de l'utilisateur en int 
				int IdSession = (int) request.getSession().getAttribute("id_utilisateur");
								
				//Si l'IdSession et l'ID utilisateur récupérées en BDD sont différentes, on renvoie  la requête vers la servlet d'accueil
				//Afin que l'utilisateur ne puisse pas modifier les informations d'un autre profil que le sien
				if ( IdSession != no_utilisateur ) {
					RequestDispatcher rd = request.getRequestDispatcher(SERVLET_ACCUEIL_SERVLET);
					rd.forward(request, response);
				
				//Sinon la page modifier mon profil s'affiche.
				} else {
					request.setAttribute("afficheUtilisateur", utilisateurTrouve);
					RequestDispatcher rd = request.getRequestDispatcher(SERVLET_MODIF_PROFIL);
					rd.forward(request, response);
				}				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		}
		
	}
	
	

		
	


