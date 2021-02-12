package fr.eni.encheres.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class MonProfilServlet
 */
@WebServlet("/monProfil")
public class MonProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Utilisateur utilisateurTrouve= null;
		
		//Récupération du paramètre
		String sNo_utilisateur = request.getParameter("id");
		
		//Transforme le type Vue en type Modele pour préparer l'appel au Modele.
		int no_utilisateur = Integer.parseInt(sNo_utilisateur);
		
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		utilisateurTrouve = manager.getUtilisateurByID(no_utilisateur);
		
		System.out.println(utilisateurTrouve);
		
		request.setAttribute("afficheUtilisateur", utilisateurTrouve);


		getServletContext().getRequestDispatcher("/WEB-INF/jsp/AfficheProfil.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
