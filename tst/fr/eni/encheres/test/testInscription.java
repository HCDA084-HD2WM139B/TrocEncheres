package fr.eni.encheres.test;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;

/**
 * 
 */

/**
 * @author Joach
 *
 */
public class testInscription {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
						
		// Utilisation d'un jeu d'essai pour le test
		String pseudo = "pseudo"; String pseudoUn = "pseudoUn";
		String prenom = "prenom"; String prenomUn = "";
		String ville = "ville"; String villeUn = "";
		String codePostal = "codePo"; String codePostalUn = "";
		String motDePasse = "password"; String motDePasseUn = "password";
		String nom = "motDePasse"; String nomUn = "";
		String email = "email"; String emailUn = "emailUn";
		String rue = "rue"; String rueUn = "";
		String telephone = "telephone"; String telephoneUn = "";
		String motDePasseConf = "password"; String motDePasseConfUn = "paword";
		
		// Enregistrement du jeu d'essai dans une Map clé / donnée pour EssaiOk
		Map<String, String> EssaiOk = new LinkedHashMap<String, String>();
		EssaiOk.put("pseudo", pseudo);
		EssaiOk.put("prenom", prenom);
		EssaiOk.put("ville", ville);
		EssaiOk.put("codePostal", codePostal);
		EssaiOk.put("motDePasse", motDePasse);
		EssaiOk.put("nom", nom);
		EssaiOk.put("email", email);
		EssaiOk.put("rue", rue);
		EssaiOk.put("telephone", telephone);
		EssaiOk.put("motDePasseConf", motDePasseConf);
		
		// Enregistrement du jeu d'essai dans une Map clé / donnée pour EssaiKo
		Map<String, String> EssaiKo = new LinkedHashMap<String, String>();
		EssaiKo.put("pseudo", pseudoUn);
		EssaiKo.put("prenom", prenomUn);
		EssaiKo.put("ville", villeUn);
		EssaiKo.put("codePostal", codePostalUn);
		EssaiKo.put("motDePasse", motDePasseUn);
		EssaiKo.put("nom", nomUn);
		EssaiKo.put("email", emailUn);
		EssaiKo.put("rue", rueUn);
		EssaiKo.put("telephone", telephoneUn);
		EssaiKo.put("motDePasseConf", motDePasseConfUn);
		
		// Test 1: Les champs sont remplis et ils respectent le nombre de caractère autorisé
		if (testChampOk(EssaiOk)) {
			System.out.println("*********TEST OK AVEC ESSAIOK***********");
		}
		
		// Test 2: Uniquement les champs pseudoUn, motDePasseUn, emailUn, motDePasseConfUn sont remplis et ils respectent le nombre de caractère autorisé
		// Les autres champs sont mauvais
		if (!testChampOk(EssaiKo)) {
			System.out.println("*********TEST OK AVEC ESSAIKO***********");
		}
		
		// Test 3: Le mot de passe correspond à la confirmation
		if (!testMotDePasse(EssaiOk)) {
			System.out.println("*********TEST OK AVEC ESSAIOK***********");
		}
		
		// Test 4: Le mot de passe ne correspond pas à la confirmation
		if (testMotDePasse(EssaiKo)) {
			System.out.println("*********TEST OK AVEC ESSAIKO***********");
		}
		
		// Test 5: Le pseudo n'est pas déjà existant
		if (!testPseudo(EssaiOk)) {
			System.out.println("*********TEST OK AVEC ESSAI OK***********");
		}
		
		// Test 6: Le pseudo est déjà existant
		if (testPseudo(EssaiKo)) {
			System.out.println("*********TEST OK AVEC ESSAI KO***********");
		}
		
		// Test 7: L'email n'est pas déjà existant
		if (!testEmail(EssaiOk)) {
			System.out.println("*********TEST OK AVEC ESSAI OK***********");
		}
		
		// Test 8: L'email est déjà existant
		if (testEmail(EssaiKo)) {
			System.out.println("*********TEST OK AVEC ESSAI KO***********");
		}
		
		// Test 9: La totalité des tests est Ok (tailleChamp == true && pseudoUtilise == false && emailUtilise == false && erreurMotDePasse == false)
		if (testAjoutUtilisateur(true, false, false, false, EssaiOk)) {
			System.out.println("*********TEST OK AVEC ESSAI OK***********");
		}
		
		// Test 10: Un des tests a echoué
		if (!testAjoutUtilisateur(true, true, true, true, EssaiKo)) {
			System.out.println("*********TEST OK AVEC ESSAI OK***********");
		}
	}
	
	/**
	 * Méthode permettant de vérifier l'état des conditions précédentes.
	 * Si c'est ok, l'utilisateur est ajouté
	 * Si c'est ko, l'utilisateur n'est pas ajouté
	 * @param tailleChamp: True si tous les champs respectent 
	 * @param pseudoUtilise: False si le pseudo n'est pas déjà utilisé
	 * @param emailUtilise: False si l'email n'est pas déjà utilisé
	 * @param erreurMotDePasse: False si le mot de passe est différent de sa confirmation
	 * @param parametre
	 * @return True si les conditions d'insertions sont remplies
	 */
	private static boolean testAjoutUtilisateur(boolean tailleChamp, boolean pseudoUtilise, boolean emailUtilise,
		boolean erreurMotDePasse, Map<String, String> parametre) {
		
		boolean utilisateurInsere = false;

		// TODO verif si existant en BDD (si existant --> message d'erreur / si inexistant --> ajout � la base)
		System.out.println("*****TEST AJOUT UTILISATEUR*****");
		if (tailleChamp == true && pseudoUtilise == false && emailUtilise == false && erreurMotDePasse == false) {
			try {
				getInsertUtilisateur(parametre.get("pseudo"), parametre.get("prenom"), parametre.get("telephone"), parametre.get("codePostal"),
						parametre.get("motDePasse"), parametre.get("nom"), parametre.get("email"), parametre.get("rue"), parametre.get("ville"));
				System.out.println("Utilisateur ajouté");
				utilisateurInsere = true;
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("L'utilisateur n'a pas été ajouté");
		}
		return utilisateurInsere;
	}

	/**
	 * Méthode permettant de vérifier si l'email est déjà utilisé
	 * @param parametre: String qui représente l'email 
	 * @return emailUtilise: False si l'email n'existe pas
	 */
	private static boolean testEmail(Map<String, String> parametre) {
		boolean emailUtilise = true;
		
		// Vérification dans la base de données si l'email est déjà utilisé
		System.out.println("*****TEST EMAIL*****");
		try {
			if (!getEmailExiste(parametre.get("email"))){
				System.out.println("l'email n'existe pas");
				emailUtilise = false;
			} else {
				System.out.println("L'email existe");
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emailUtilise;
	}

	/**
	 * Méthode permettant de vérifier si le pseudo est déjà utilisé
	 * @param parametre String qui représente le pseudo 
	 * @return pseudoUtilise: False si l'email n'existe pas
	 */
	private static boolean testPseudo(Map<String, String> parametre) {
		boolean pseudoUtilise = true;
		
		// Vérification dans la base de données si le pseudo est déjà utilisé
		System.out.println("*****TEST PSEUDO*****");
		try {
			if (!getPseudoExiste(parametre.get("pseudo"))){
				System.out.println("le pseudo n'existe pas");
				pseudoUtilise = false;
			} else {
				System.out.println("Le pseudo existe");
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pseudoUtilise;
	}

	/**
	 * Méthode permettant de vérifier si les champs respectent le nombre de caractère
	 * @param parametre Map contenant les String à vérifier
	 * @return tailleChamp: True si tous les champs sont ok
	 */
	private static boolean testChampOk(Map<String, String> parametre) {
		int valeurMax;
		boolean tailleChamp = true;
		
		System.out.println("*****TEST CHAMPS*****");
		for(Entry<String, String> entree:parametre.entrySet()) {
			valeurMax = valeurMax(entree.getKey());
			// Vérification pour chaque champ si le nombre de caractère utilisé n'est pas inférieur au chiffre autorisé, sauf pour le champ téléphone qui peut être nul
			if (!verifierTailleChamp(entree.getValue(), valeurMax) && !entree.getKey().contains("telephone") ) {
				System.out.println("Le champ " + entree.getKey() + " est KO. Il n'est pas compris entre 2 et " + valeurMax + " caractères.");
				tailleChamp = false;
			} else {
				System.out.println("Le champ " + entree.getKey() + " est OK. Il est compris entre 2 et " + valeurMax + " caractères." + " Variable = " + entree.getValue());
			}
		}
		return tailleChamp;
	}

	/**
	 * Méthode permettant de vérifier que le mot de passe correspond à sa confirmation
	 * @param parametre Map contenant les String à vérifier
	 * @return erreurMotDePasse: False si le mot de passe correspond à sa confirmation
	 */
	private static boolean testMotDePasse(Map<String, String> parametre) {
		boolean erreurMotDePasse = true;
		
		System.out.println("*****TEST DU MOT DE PASSE*****");
		// Vérification que le mot de passe est égal à la confirmation
		try {
			if (verifMotDePasse(parametre.get("motDePasse"), parametre.get("motDePasseConf"))) {
				System.out.println("Le mot de passe correpond");
				System.out.println("mot de passe: " + parametre.get("motDePasse"));
				System.out.println("confirmation: " + parametre.get("motDePasseConf"));
				erreurMotDePasse = false;
			} else {
				System.out.println("Il y a une erreur sur le mot de passe");
				System.out.println("mot de passe: " + parametre.get("motDePasse"));
				System.out.println("confirmation: " + parametre.get("motDePasseConf"));
			}
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return erreurMotDePasse;
	}
	
	// Déclarations des méthodes
	
	/**
	 * Méthode permettant de vérifier que la taille du champ est comprise entre
	 * 2 et tailleChamp
	 * @param pChampAverifier: String champ à vérifier
	 * @param tailleChamp: int taille max du champ
	 * @return resultat: True si la taille du champ est ok
	 */
	public static boolean verifierTailleChamp(String pChampAverifier, int tailleChamp) {
        boolean resultat = false;
        if( pChampAverifier.length() > 2 && pChampAverifier.length() <= tailleChamp) {
            resultat = true;
        }
        return resultat;
    }
    
	/**
	 * Méthode permettant de retourner la valeurMax de caractère d'un champ en fonction de ce champ
	 * @param pChampAverifier: String champ à vérifier
	 * @return valeurMax: int valeur max
	 */
    public static int valeurMax(String pChampAverifier) {
    	int valeurMax = 0;
    	
    	if (pChampAverifier.equals("pseudo") || pChampAverifier.equals("motDePasse")) {
    		valeurMax = 8;
    	} else if (pChampAverifier.equals("codePostal")) {
    		valeurMax = 6;
    	} else {
    		valeurMax = 40;
    	}
    	return valeurMax;
    }
    
    /**
     * Méthode permettant de vérifier si le pseudo est déjà utilisé
     * @param pPseudo: String pseudo
     * @return: True si le pseudo correspond
     * @throws BusinessException
     */
    public static boolean getPseudoExiste(String pPseudo) throws BusinessException {
    	boolean pseudoTrouve = false;
    	if (pPseudo.equals("pseudoUn")) {
    		pseudoTrouve = true;
    	}
        return pseudoTrouve;
    }
    
    /**
     * Méthode permettant de vérifier si l'email est déjà utilisé
     * @param pEmail: String email
     * @return: True si l'email correspond
     * @throws BusinessException
     */
    public static boolean getEmailExiste(String pEmail) throws BusinessException {
    	boolean emailTrouve = false;
    	if (pEmail.equals("emailUn")) {
    		emailTrouve = true;
    	}
        return emailTrouve;
    }
    
    /**
     * Méthode permettant de vérifier si le mot de passe correspond à sa confirmation
     * @param pMotDePasse: String mot de passe
     * @param pConfirmation: String confirmation
     * @return: True si le mot de passe correspond à sa confirmation
     * @throws BusinessException
     */
    public static boolean verifMotDePasse(String pMotDePasse, String pConfirmation) throws BusinessException {
    	boolean motDePasse = false;
    	if(pMotDePasse.equals(pConfirmation)) {
    		motDePasse = true;
    	}
    	return motDePasse;
    }
    
    /**
     * Méthode permettant d'insérer un utilisateur
     * @param pPseudo
     * @param pPrenom
     * @param pTelephone
     * @param pCodePostal
     * @param pMotDePasse
     * @param pNom
     * @param pEmail
     * @param pRue
     * @param pVille
     * @return
     * @throws BusinessException
     */
    public static Utilisateur getInsertUtilisateur(String pPseudo, String pPrenom, String pTelephone, String pCodePostal, String pMotDePasse, 
    		String pNom, String pEmail, String pRue, String pVille) throws BusinessException {
        Utilisateur utilisateurCree = new Utilisateur(pPseudo, pNom, pPrenom, pEmail, pTelephone, pRue, pCodePostal, pVille, pMotDePasse, 0, false);
//        utilisateurCree = DAOFactory.getUtilisateurDAO().insertUtilisateur(utilisateurCree);
        // état applicatif : on enregistre l'utilisateur qui est connecté dans le manager :
//        this.utilisateurConnecte = utilisateurCree;
        return utilisateurCree;
    }
}
