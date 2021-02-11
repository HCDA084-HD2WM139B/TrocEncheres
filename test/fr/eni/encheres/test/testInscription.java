import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.EnchereManager;
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
		// TODO Auto-generated method stub
		Utilisateur utilisateur = new Utilisateur();
		

		
		// TODO � retirer apr�s les tests
		System.out.println("Passage dans le DoGet");
		
		// Déclaration des variables
		int valeurMax = 0;
		boolean champMax = true;
		boolean champMin = true;
		boolean pseudoUtilise = true;
		boolean emailUtilise = true;
		boolean erreurMotDePasse = true;
		
		// R�cup�ration des param�tres et stockage dans une Map pour un stockage cl� / valeur
		Map<String, String> parametre = new HashMap<String, String>();
		parametre.put("pseudo", request.getParameter("pseudo"));
		parametre.put("prenom", request.getParameter("prenom"));
		parametre.put("ville", request.getParameter("ville"));
		parametre.put("codePostal", request.getParameter("codePostal"));
		parametre.put("motDePasse", request.getParameter("motDePasse"));
		parametre.put("nom", request.getParameter("nom"));
		parametre.put("email", request.getParameter("email"));
		parametre.put("rue", request.getParameter("rue"));
		parametre.put("telephone", request.getParameter("telephone"));
		parametre.put("motDePasseConf", request.getParameter("motDePasseConf"));
		
		// Appel du manager pour appeler les m�thodes 
		EnchereManager manager = EnchereManager.getEnchereManager();
		
		for(Entry<String, String> entree:parametre.entrySet()) {
			valeurMax = manager.valeurMax(entree.getKey());
			// Vérification pour chaque champ si le nombre de caractère utilisé n'est pas inférieur au chiffre autorisé, sauf pour le champ téléphone qui peut être nul
			if (!manager.verifierTailleChamp(entree.getValue(), valeurMax) && !entree.getKey().contains("telephone") ) {
				System.out.println("Le champ " + entree.getKey() + " doit être compris entre 2 et " + valeurMax + " caractères.");
				request.setAttribute("erreurChampMin", "Le champ " + entree.getKey() + " ne peut pas être inférieur à deux caractères.");
				champMin = false;
			} else {
				System.out.println("Le champ " + entree.getKey() + " est ok car compris entre 2 et " + valeurMax + " caractères." + " et il vaut: " + entree.getValue());
			}
		}
		
		// Vérification que le mot de passe est égal à la confirmation
		try {
			if (manager.verifMotDePasse(parametre.get("motDePasse"), parametre.get("motDePasseConf"))) {
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
		
		// Vérification dans la base de données si le pseudo est déjà utilisé
		try {
			if (!manager.getPseudoExiste(parametre.get("pseudo"))){
				System.out.println("le pseudo n'existe pas");
				pseudoUtilise = false;
			} else {
				System.out.println("Le pseudo existe");
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Vérification dans la base de données si l'email est déjà utilisé
		try {
			if (!manager.getEmailExiste(parametre.get("email"))){
				System.out.println("l'email n'existe pas");
				emailUtilise = false;
			} else {
				System.out.println("L'email existe");
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO credit doit �tre �gale � z�ro sauf pour les X premi�res personnes il sera �gale � 100
		
		// TODO verif si existant en BDD (si existant --> message d'erreur / si inexistant --> ajout � la base)
		if (champMax == true && champMin == true && pseudoUtilise == false && emailUtilise == false && erreurMotDePasse == false) {
			try {
				manager.getInsertUtilisateur(parametre.get("pseudo"), parametre.get("prenom"), parametre.get("telephone"), parametre.get("codePostal"),
						parametre.get("motDePasse"), parametre.get("nom"), parametre.get("email"), parametre.get("rue"), parametre.get("ville"));
				System.out.println("Utilisateur ajouté");
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("L'utilisateur n'a pas été ajouté");
		}
	}
	
	// Déclarations des méthodes
	
	public boolean verifierTailleChamp(String pChampAverifier, int tailleChamp) {
        boolean resultat = false;
        if( pChampAverifier.length() > 2 && pChampAverifier.length() <= tailleChamp) {
            resultat = true;
        }
        return resultat;
    }
    
    public int valeurMax(String pChampAverifier) {
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
    
    public boolean getPseudoExiste(String pPseudo) throws BusinessException {
    	boolean pseudoTrouve = false;
    	pseudoTrouve = DAOFactory.getUtilisateurDAO().selectAllPseudo(pPseudo);
        return pseudoTrouve;
    }
    
    public boolean getEmailExiste(String pEmail) throws BusinessException {
    	boolean emailTrouve = false;
    	emailTrouve = DAOFactory.getUtilisateurDAO().selectAllEmail(pEmail);
        return emailTrouve;
    }
    
    public boolean verifMotDePasse(String pMotDePasse, String pConfirmation) throws BusinessException {
    	boolean motDePasse = false;
    	if(pMotDePasse.equals(pConfirmation)) {
    		motDePasse = true;
    	}
    	return motDePasse;
    }
    
    public Utilisateur getInsertUtilisateur(String pPseudo, String pPrenom, String pTelephone, String pCodePostal, String pMotDePasse, 
    		String pNom, String pEmail, String pRue, String pVille) throws BusinessException {
        Utilisateur utilisateurCree = new Utilisateur(pPseudo, pNom, pPrenom, pEmail, pTelephone, pRue, pCodePostal, pVille, pMotDePasse, 0, false);
        utilisateurCree = DAOFactory.getUtilisateurDAO().insertUtilisateur(utilisateurCree);
        // état applicatif : on enregistre l'utilisateur qui est connecté dans le manager :
        this.utilisateurConnecte = utilisateurCree;
        return utilisateurCree;
    }
}
