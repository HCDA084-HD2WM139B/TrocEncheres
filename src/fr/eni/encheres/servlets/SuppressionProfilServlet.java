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

/**
 * Servlet Controlant la suppression d'un utilisateur en fonction de son Id. 
 * La route de déclenchement : "/supprimerProfil?id=xx"
 */
@WebServlet("/supprimerProfil")
public class SuppressionProfilServlet extends HttpServlet {

	
	// Constantes
	private static final long serialVersionUID = 1L;
	private static final String NAME_PARAM_ID = "id";
	private static final String NAME_USER_SESSION_ID = "id_utilisateur";
	private static final String SERVLET_DECONNEXION = "/deconnexion";
	
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Déclarations
		EnchereManager manager = null;
		Integer idUtilisateur = null;
		Integer idSession = null;
		boolean resultSql = false;
		RequestDispatcher rd = null;
		boolean erreur = false;

		// Appel du manager
		manager = EnchereManager.getEnchereManager();

		try {
			// Récupération du paramètre : id utilisateur
			idUtilisateur = Integer.parseInt(request.getParameter(NAME_PARAM_ID));
			// Récupération de l'Id utilisateur dans la session
			idSession = (int) request.getSession().getAttribute(NAME_USER_SESSION_ID);

			// Comparaison des Id :
			if (idUtilisateur == idSession) {
				resultSql = manager.deleteUserById(idUtilisateur);
			} else {
				erreur = true;
			}
		} catch (NumberFormatException nfe) {
			// erreur si l'id en parametre n'est pas un chiffre
			erreur = true;
			nfe.printStackTrace();
		} catch (BusinessException e) {
			// erreur si un problème survient lors de la requete 
			erreur = true;
			e.printStackTrace();
		}
		
		// si l'id de l'URL correspond à l'id en session et que l'id est bien un numero
		if (erreur) {
			// on envoi une reponse 404
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			// Si la requete de suppression s'est bien déroulé, on redirige vers la deconnexion
			if (resultSql) {
				rd = request.getRequestDispatcher(SERVLET_DECONNEXION);
				if (rd != null) {
					rd.forward(request, response);
				}
			}
		}
	}

	
	

}
