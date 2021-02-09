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
	
	private UtilisateurDAO utilisateurDAO;
	
	private EnchereManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	public static EnchereManager getEnchereManager() {
		return new EnchereManager();
	}
	
}