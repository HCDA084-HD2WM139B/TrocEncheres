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
     * Méthode vérifiant si la chaine de caractère passé en paramètre est vide.
     * @param pChampAverifier (String) la chaine de caractère.
     * @return True si la chaine de caractère n'est pas vide, sinon False.
     */
    public boolean verifierSiChampVide(String pChampAverifier) {
        boolean resultat = false;
        if( !pChampAverifier.isEmpty()) {
            resultat = true;
        }
        return resultat;
    }

 

    
    
    public boolean verifierTailleChampMax(String pChampAverifier, int tailleChamp) {
        boolean resultat = false;
        if(pChampAverifier.length() > tailleChamp) {
            resultat = true;
        }
        return resultat;
    }
    
    
    
    // Getters & Setters
    private UtilisateurDAO getDAOUtilisateur() {
        return DAOutilisateur;
    }

 

    private Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    
    
}