package fr.eni.encheres.bll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

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
	
	/* Méthode permettant de vérifier si l'identifiant n'est pas null et s'il ne dépasse pas 20 caractères.
	 * 
	 * @param : pIdentifiant = identifiant de l'utilisateur
	 */
	public boolean verifierIdentifiant(String pIdentifiant) {
		boolean resultat = false;
		
		//Permet de vérifier si l'identifiant ne contient que des caractères alphanumériques.
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
		Matcher matcher = pattern.matcher(pIdentifiant);
		
		if( !pIdentifiant.isEmpty() && pIdentifiant.length() < 20  && matcher.find()) {
			resultat = true;
		} 
		return resultat;
	}
	
	/* Méthode permettant de vérifier si le mot de passe n'est pas null et s'il ne dépasse pas 30 caractères.
	 * 
	 * @param : pMotdePasse = mot de passe de l'utilisateur
	 */
	public boolean verifierMotDePasse(String pMotDePasse) {
		boolean resultat = false;
		
		if( !pMotDePasse.isEmpty() && pMotDePasse.length() < 30 ) {
			resultat = true;
		} 
		return resultat;
	}
}