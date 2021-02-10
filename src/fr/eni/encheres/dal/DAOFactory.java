/**
 * 
 */
package fr.eni.encheres.dal;

/**
 * @author Joachim
 *
 */
public abstract class DAOFactory {

	public static UtilisateurDAO getUtilisateurDAO() {
		
		return new UtilisateurDAOImpl();
	}
}
