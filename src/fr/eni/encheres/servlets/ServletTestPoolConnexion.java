package fr.eni.encheres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dal.UtilisateurDAOImpl;

/**
 * Servlet implementation class ServletTestPoolConnexion
 */
@WebServlet("/ServletTestPoolConnexion")
public class ServletTestPoolConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTestPoolConnexion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out= response.getWriter();
		//Cr�ation d'un objet de type Context permettant la recherche d'une ressource nomm�e dans l'arbre JNDI
		try {
			Context context = new InitialContext();
			//Recherche de la ressource
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/pool_cnx");
			//Demande d'une connexion. La m�thode getConnection met la demande en attente tant qu'il n'y a pas de connexion disponible
			Connection cnx = dataSource.getConnection();
			//Exploitation de la connexion
			out.print("La connexion est "+ (cnx.isClosed()?"ferm�e":"ouverte")+".");
			
			// TODO � commenter
			UtilisateurDAO utilisateurDao = new UtilisateurDAOImpl();
			//Utilisateur utilisateur = new Utilisateur("brebr", "grebre", "fzfz", "jo@campus-eni.fr", "0606060606", "19 boulevard", "44300", "Nantes", "azerty", 0, false);
			try {
				utilisateurDao.selectByEmail("jo@camus-eni.fr");
				System.out.println("Ajout Ok");
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ********************************************************
			
			//Lib�ration de la connexion. Elle n'est pas ferm�e mais remise dans le pool
			cnx.close();
		} catch (NamingException | SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
			out.println("Une erreur est survenue lors de l'utilisation de la base de donn�es : " + e.getMessage());
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

