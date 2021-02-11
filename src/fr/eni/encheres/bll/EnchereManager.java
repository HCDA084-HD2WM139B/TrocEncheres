package fr.eni.encheres.bll;
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

/**
 * Classe de la couche Métier (BLL)
 * @author Groupe 3
 *
 */
public class EnchereManager {
    
    // Attributs d'instance
    private UtilisateurDAO DAOutilisateur;
    private Utilisateur utilisateurConnecte;
    
    // Constructeurs
    private EnchereManager() {
        this.DAOutilisateur = DAOFactory.getUtilisateurDAO();
        this.utilisateurConnecte = null;
    }
    
    // Méthode static pour obtenir une instance d'EnchereManager
    public static EnchereManager getEnchereManager() {
        return new EnchereManager();
    }     
    
    /**
     * Méthode permettant de vérifier si l'E-mail est :
     * - d'une longueur comprise entre 2 et 8 inclus.
     * @param pIdentifiant
     * @return
     */
    public boolean verifierEmail(String pIdentifiant) {
        boolean resultat = false;
        if( !pIdentifiant.isEmpty() && pIdentifiant.length() < 20 && pIdentifiant.length() > 5 ) {
            resultat = true;
        } 
        return resultat;
    }      
    
    /**
     * Méthode permettant de vérifier si le pseudo est :
     * - d'une longueur comprise entre 2 et 8 inclus.
     * @param pIdentifiant Pseudo saisie par l'utilisateur
     * @return True s'il est valide, sinon False.
     */
    public boolean verifierPseudo(String pIdentifiant) {
        boolean resultat = false;
        if( !pIdentifiant.isEmpty() && pIdentifiant.length() <= 8 && pIdentifiant.length() > 2 ) {
            resultat = true;
        } 
        return resultat;
    }  
    
    /**
     * Méthode permettant de vérifier si le mot de passe est : 
     * - d'une longueur comprise entre 2 et 8 inclus.
     * - d'un format Alpha-numérique.
     * @param pMotDePasse Mot de passe saisie par l'utilisateur.
     * @return
     */
    public boolean verifierMotDePasse(String pMotDePasse) {
        boolean resultat = false;
        // TODO REGEX => regle métier -> au moins 1 caractere special, une majuscule et une minuscule
        Pattern mdpPattern = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher mdpMatcher = mdpPattern.matcher(pMotDePasse);
        if( !pMotDePasse.isEmpty() && pMotDePasse.length() <= 8 && pMotDePasse.length() > 2 && mdpMatcher.find() ) {
            resultat = true;
        } 
        return resultat;
    }
    
    /**
     * Méthode retournant le type de l'identifiant saisie par l'utilisateur passé en paramètre.
     * @param pIdentifiant Le login saisie par l'utilisateur
     * @return (int) 1 si pIdentifiant est un E-mail ; -1 si pIdentifiant est un Pseudo ; et 0 s'il ne correspond à aucun type. 
     */
    public int getTypeIdentifiant(String pIdentifiant) {
        int resultat = 0;
        // si pIdentifiant n'est pas vide
        if (!pIdentifiant.isEmpty()) {
            Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
            Pattern pseudoPattern = Pattern.compile("^[a-zA-Z0-9]*$");
            // on compare les formats de pIdentifiant aux REGEX
            Matcher emailMatcher = emailPattern.matcher(pIdentifiant);
            Matcher pseudoMatcher = pseudoPattern.matcher(pIdentifiant);
            // Traitement des comparaisons :
            if(emailMatcher.find()) {
                resultat = 1;
            } else if(pseudoMatcher.find()) {
                resultat = -1;
            } else {
                resultat = 0;
            }
        }
        return resultat;
    }
        
    /**
     * Méthode permettant de connaitre si un utilisateur existe en Base De Données selon le mot de passe et l'identifiant rentrés en paramètres.
     * @param typeRequete Le nom exact de la colonne de la table UTILISATEUR en Base de données.
     * @param identifiant Le Pseudo ou l'Email valide de saisie lors de la connexion
     * @param motDePasse Le mot de passe valide saisie lors de la connexion
     * @return Un utilisateur si l'identifiant et le mot de passe sont correctes en Base de données ; Null s'ils ne correspondent pas.
     * @throws BusinessException 
     */
    public Utilisateur getUtilisateurByLogin(String pTypeRequete, String pIdentifiant, String pMotDePasse) throws BusinessException {
        Utilisateur utilisateurTrouve = null;
        utilisateurTrouve = DAOFactory.getUtilisateurDAO().selectByLogin(pTypeRequete, pIdentifiant, pMotDePasse);
        // état applicatif : on enregistre l'utilisateur qui est connecté dans le manager :
        this.utilisateurConnecte = utilisateurTrouve;
        return utilisateurTrouve;
    }
        
    /**
     * Méthode vérifiant si la chaine de caractère passé en paramètre est supérieure à deux caractères,
     * et inférieur à tailleChamp
     * @param pChampAverifier (String) la chaine de caractère.
     * @return True si la chaine de caractère est comprise entre deux et tailleChamp, sinon False.
     */    
    public boolean verifierTailleChamp(String pChampAverifier, int tailleChamp) {
        boolean resultat = false;
        if( pChampAverifier.length() > 2 && pChampAverifier.length() <= tailleChamp) {
            resultat = true;
        }
        return resultat;
    }
    
    /**
     * Méthode permettant d'attribuer une valeur max à la chaine de caractère en fonction du champ à vérifier
     * @param pChampAverifier chaîne de caractère
     * @return la valeurMax pouvant être atteinte par la chaîne de caractère
     */
    public int valeurMax(String pChampAverifier) {
    	int valeurMax = 0;
    	
    	if (pChampAverifier.equals("pseudo") || pChampAverifier.equals("motDePasse") || pChampAverifier.equals("motDePasseConf")) {
    		valeurMax = 8;
    	} else if (pChampAverifier.equals("codePostal")) {
    		valeurMax = 6;
    	} else {
    		valeurMax = 40;
    	}
    	return valeurMax;
    }
    
    /**
     * Méthode permettant de récupérer la totalité des pseudo présent dans la base de données
     * @param pPseudo chaîne de caractère qui représente le pseudo
     * @return true si le pseudo est trouvé dans la base de données et false s'il n'existe pas
     * @throws BusinessException
     */
    public boolean getPseudoExiste(String pPseudo) throws BusinessException {
    	boolean pseudoTrouve = false;
    	pseudoTrouve = DAOFactory.getUtilisateurDAO().selectAllPseudo(pPseudo);
        return pseudoTrouve;
    }
    
    /**
     * Méthode permettant de récupérer la totalité des emails présent dans la base de données
     * @param pEmail chaîne de caractère qui représente l'email
     * @return true si l'email est trouvé dans la base de données et false s'il n'existe pas
     * @throws BusinessException
     */
    public boolean getEmailExiste(String pEmail) throws BusinessException {
    	boolean emailTrouve = false;
    	emailTrouve = DAOFactory.getUtilisateurDAO().selectAllEmail(pEmail);
        return emailTrouve;
    }
    
    /**
     * Méthode permettant de vérifier si le mot de passe saisie est identique au mot de passe confirmé
     * @param pMotDePasse chaîne de caractère qui représente le mot de passe
     * @param pConfirmation chaîne de caractère qui représente la confirmation du mot de passe
     * @return true si les mots de passe correspondent
     * @throws BusinessException
     */
    public boolean verifMotDePasse(String pMotDePasse, String pConfirmation) throws BusinessException {
    	boolean motDePasse = false;
    	if(pMotDePasse.equals(pConfirmation)) {
    		motDePasse = true;
    	}
    	return motDePasse;
    }
    
    /**
     * Méthode permettant d'ajouter un utilisateur dans la base de données
     * @param pPseudo chaîne de caractère représentant le pseudo
     * @param pPrenom chaîne de caractère représentant le prénom
     * @param pTelephone chaîne de caractère représentant le téléphone
     * @param pCodePostal chaîne de caractère représentant le code postal
     * @param pMotDePasse chaîne de caractère représentant le mot de passe
     * @param pNom  chaîne de caractère représentant le nom
     * @param pEmail chaîne de caractère représentant l'email
     * @param pRue chaîne de caractère représentant la rue
     * @param pVille chaîne de caractère représentant la ville
     * @return un utilisateur
     * @throws BusinessException
     */
    public Utilisateur getInsertUtilisateur(String pPseudo, String pPrenom, String pTelephone, String pCodePostal, String pMotDePasse, 
    		String pNom, String pEmail, String pRue, String pVille) throws BusinessException {
        Utilisateur utilisateurCree = new Utilisateur(pPseudo, pNom, pPrenom, pEmail, pTelephone, pRue, pCodePostal, pVille, pMotDePasse, 0, false);
        utilisateurCree = DAOFactory.getUtilisateurDAO().insertUtilisateur(utilisateurCree);
        // état applicatif : on enregistre l'utilisateur qui est connecté dans le manager :
        this.utilisateurConnecte = utilisateurCree;
        return utilisateurCree;
    }
    
    // Getters & Setters
    private UtilisateurDAO getDAOUtilisateur() {
        return DAOutilisateur;
    }

    private Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
        
}