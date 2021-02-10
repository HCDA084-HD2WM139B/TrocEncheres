package fr.eni.encheres.bll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
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
	
	
	public boolean verifierEmail(String pIdentifiant) {
		boolean resultat = false;
		
		if( !pIdentifiant.isEmpty() && pIdentifiant.length() < 20 ) {
			resultat = true;
		} 
		return resultat;
	}
	

	public boolean verifierPseudo(String pIdentifiant) {
		boolean resultat = false;
		
		if( !pIdentifiant.isEmpty() && pIdentifiant.length() < 15 ) {
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
	
	public int getTypeIdentifiant(String pIdentifiant) {
		int resultat = 0;
		
		Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		Pattern pseudoPattern = Pattern.compile("^[a-zA-Z0-9]*$");
		
		Matcher emailMatcher = emailPattern.matcher(pIdentifiant);
		Matcher pseudoMatcher = pseudoPattern.matcher(pIdentifiant);

		if(emailMatcher.find()) {
			resultat = 1;
		} else if(pseudoMatcher.find()) {
			resultat = -1;
		} else {
			resultat = 0;
		}

		return resultat;
	}
	
	
	public boolean isItSamePassword(String mDpSaisie, String mDpBdd) {
		boolean resultat = false;
		if (mDpSaisie.equals(mDpBdd)) {
			resultat = true;
		}
		return resultat;
	}

	public Utilisateur getUtilisateurByLogin(String pIdentifiant) throws BusinessException {
		Utilisateur utilisateurTrouve = null;
		// savoir si 1 -1 0
		int typeIdentifiant = getTypeIdentifiant(pIdentifiant);
		
		
		if(typeIdentifiant == 1) {
			utilisateurTrouve = DAOFactory.getUtilisateurDAO().selectByEmail(pIdentifiant);
			
		} else if (typeIdentifiant == -1) {
			utilisateurTrouve = DAOFactory.getUtilisateurDAO().selectByPseudo(pIdentifiant);

		} else {
			//TODO faire remonter une erreur si jamais l'email ou le pseudo n'est pas trouvé en BDD.
		}
		
		return utilisateurTrouve;
	}
	
	public boolean verifierSiChampVide(String pChampAverifier) {
        boolean resultat = false;
       
        if( !pChampAverifier.isEmpty()) {
            resultat = true;
        }
        return resultat;
    }
}