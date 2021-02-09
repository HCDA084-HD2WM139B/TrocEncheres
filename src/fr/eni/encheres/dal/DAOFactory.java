/**
 * 
 */
package fr.eni.encheres.dal;

/**
 * @author Joachim
 *
 */
public abstract class DAOFactory {

	public static EnchereDAO getEnchereDAO() {
		
		return new EnchereDAOJdbcImpl();
	}
}
