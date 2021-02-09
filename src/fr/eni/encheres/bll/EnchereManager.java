/**
 * 
 */
package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;

/**
 * Cette classe permet d'effectuer les traitements attendus sur la classe
 * 
 * @author Joach
 *
 */
public class EnchereManager {

	private EnchereDAO enchereDAO;

	/**
	 * Le constructeur permet d'initialiser la variable membre enchereDAO pour 
	 * permettre une communication avec la base de données. 
	 */
	public EnchereManager() {
		this.enchereDAO= DAOFactory.getEnchereDAO();
	}
}
