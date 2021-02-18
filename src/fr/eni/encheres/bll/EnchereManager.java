package fr.eni.encheres.bll;
 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;

/**
 * Classe de la couche Métier (BLL)
 * @author Groupe 3
 *
 */
public class EnchereManager {
    
    // Attributs d'instance
    private UtilisateurDAO DAOutilisateur;
	private EnchereDAO enchereDAO;
    private Utilisateur utilisateurConnecte;
    private List<Article> listeArticlesEnchereEnCours;
    
    // Constructeurs
    private EnchereManager() {
        this.DAOutilisateur = DAOFactory.getUtilisateurDAO();
        this.utilisateurConnecte = null;
        this.listeArticlesEnchereEnCours = new ArrayList<Article>();
        this.enchereDAO = DAOFactory.getEnchereDAO();
    }
    
    /**
 	 * Selectionne l'ensemble des categories depuis que l'application est d�ploy�e
 	 * @return
 	 * @throws BusinessException
 	 */
 	public List<Categorie> selectionnerToutesLesCategories() throws BusinessException {
 		return this.enchereDAO.selectAllCategorie();
 		
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
     * Méthode permettant de vérifier la validé du mail
     * @param pEmail
     * @return True si le mail est au bon format
     */
    public boolean verifFormatEmail(String pEmail) {
        Pattern p = Pattern
                .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(pEmail.toUpperCase());
        return m.matches();
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
    
    public Utilisateur getUpdatedUtilisateur(int pNo_utilisateur, String pPseudo, String pPrenom, String pTelephone, String pCodePostal, String pMotDePasse, 
    		String pNom, String pEmail, String pRue, String pVille) throws BusinessException {
    	
    	Utilisateur utilisateurUpdated = new Utilisateur(pNo_utilisateur, pPseudo, pNom, pPrenom, pEmail, pTelephone, pRue, pCodePostal, pVille, pMotDePasse, 0, false);
    	
    	utilisateurUpdated= DAOFactory.getUtilisateurDAO().updateUtilisateur(utilisateurUpdated);
    	
    	return utilisateurUpdated;
    }
    
    public Utilisateur getUtilisateurByID(int id) throws BusinessException {
        Utilisateur utilisateurTrouve =null;
       
        utilisateurTrouve = DAOFactory.getUtilisateurDAO().afficheUtilisateurbyId(id);
       
        return utilisateurTrouve;
        
    }
    
    public List<Article> getAllSales() throws BusinessException {
    	List<Article> listeArticle = null;
    	listeArticle = DAOFactory.getEnchereDAO().selectAllSales();
    	// état applicatif : on enregistre la liste des encheres en cours dans le manager :
    	this.listeArticlesEnchereEnCours = listeArticle;
    	return listeArticle;
    }
    
    /**
     * Méthode permettant de supprimer un utilisateur par le numero d'utilisateur passé en paramêtre.
     * @param idUtilisateur
     * @return (boolean) True si la suppression s'est correctement déroulée ; sinon False.
     * @throws BusinessException
     */
    public boolean deleteUserById(int idUtilisateur) throws BusinessException {
    	boolean resultSql = false;
    	resultSql = DAOFactory.getUtilisateurDAO().deleteUserById(idUtilisateur);
    	return resultSql;
    }
    
    /**
     * Méthode permettant de connaître la date du jour
     * @return String date
     */
	public String dateJour() {
		 Date actuelle = new Date();
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String date = dateFormat.format(actuelle);
		 return date;
	}
	
	/**
	 * Méthode permettant de vérifier si la taille du champ est correcte
	 * @param pChampAverifier: String où la taille doit être vérifiée
	 * @param tailleChamp: int taille maximum du champs
	 * @return boolean true si la longueur du String est correcte
	 */
   public boolean verifierTailleChampOk(String pChampAverifier, int tailleChamp) {
       boolean resultat = false;
       // On vérifie que la longueur du champ à vérifier est supérieure à 0 après avoir supprimer les espaces,
       // On vérifie que la longueur du champ à vérifier est supérieure à 2 
       // On vérifie que la longueur du champ à vérifier est inférieure à tailleChamp
       if( pChampAverifier.trim().length() > 0 && pChampAverifier.length() > 2 && pChampAverifier.length() <= tailleChamp) {
           resultat = true;
       }
       return resultat;
   }
   
   /**
    * Méthode permettant de vérifier si le type String est composé uniquement de type int
    * @param pchampAverifier: String à vérifier
    * @return: boolean true si le String est composé uniquement de chiffre
    */
   public boolean verifierSiEntier (String pchampAverifier) {
   	boolean resultat = true;
       try {
       	Integer.parseInt(pchampAverifier);
       } catch (NumberFormatException e) {
           resultat = false;
       }
       return resultat;
   }
   
   /**
    * Méthode permettant de convertir un type String vers un type Date
    * @param pchampAconvertir: le champ à convertir
    * @return: type Date
    */
   public Date stringVersDate(String pchampAconvertir) {
	   
	   DateFormat formatter;
	   java.util.Date d1 = new java.util.Date();
	   java.sql.Date date = new java.sql.Date(d1.getTime());
	   formatter = new SimpleDateFormat("dd-MM-yy");
	   try {
		   d1 = formatter.parse(pchampAconvertir);
	} catch (ParseException e) {
		// TODO à supprimer
		e.printStackTrace();
	}
	return date;
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
   public Article getInsertArticle(String pNomArticle, String pDescription, Date pDateDebut, Date pDateFin, int pPrixInitial, 
   		Categorie pNoCategorie, Utilisateur pNoUtilisateur) throws BusinessException {
	   Article artcileCree = new Article(pNomArticle, pDescription, pDateDebut, pDateFin, pPrixInitial, pNoCategorie, pNoUtilisateur);
	   artcileCree = DAOFactory.getEnchereDAO().insertArticle(artcileCree);
	return artcileCree;
   }
   
   /**
    * Méthode permettant d'ajouter un entier et un libellé à une catégorie
    * @param pNoCategorie: int numéro de la catégorie
    * @param pLibelle: String libellé de la catégorie
    * @return: un type categorie
    * @throws BusinessException
    */
   public Categorie getInsertCategorie (int pNoCategorie, String pLibelle) throws BusinessException {
	   
	   Categorie categorie = new Categorie (pNoCategorie, pLibelle);
	   return categorie;
   }
 
   /**
   * Méthode pour verifier que l'ID et le Pseudo correspond dans la BDD
   * @return boolean
   * @throws BusinessException
   */
   public boolean VerifierPseudoId(String pPseudo, Integer pNo_utilisateur) throws BusinessException {
   boolean resultat = false;
   if ( !pPseudo.isEmpty() && pNo_utilisateur != null) {
   resultat = DAOFactory.getUtilisateurDAO().VerifByPseudoAndId(pPseudo, pNo_utilisateur);
   }
   return resultat;
   }
   /**
   * Méthode pour verifier que l'ID et l'Email correspond dans la BDD
   * @return boolean
   * @throws BusinessException
   */
   public boolean VerifierEmailId(String pEmail, Integer pNo_utilisateur) throws BusinessException {
   boolean resultat = false;
   if ( !pEmail.isEmpty() && pNo_utilisateur != null) {
   resultat = DAOFactory.getUtilisateurDAO().VerifByEmailAndId(pEmail, pNo_utilisateur);
   }
   return resultat;
   }
   /**
   * Méthode pour Récupérer un ID avec le Pseudo dans la BDD
   * @return Integer
   * @throws BusinessException
   */
   public Integer RecupererIdAvecPseudo(String pPseudo) throws BusinessException {
   Integer resultat = null;
   if ( !pPseudo.isEmpty() ) {
   resultat = DAOFactory.getUtilisateurDAO().SelectIdWherePseudo(pPseudo);
   }
   return resultat;
   }
   
   /**
   * Méthode pour Récupérer un ID avec l'email dans la BDD
   * @return Integer
   * @throws BusinessException
   */
   public Integer RecupererIdAvecEmail(String pEmail) throws BusinessException {
   Integer resultat = null;
   if ( !pEmail.isEmpty() ) {
   resultat = DAOFactory.getUtilisateurDAO().SelectIdWhereEmail(pEmail);
   }
   return resultat;
   }
   
   /**
    * Méthode qui affiche le détail de l'article selon son ID
    * @param pIdArticle : id de l'article 
    * @return articleTrouve
    * @throws BusinessException
    */
   public Article selectArticleById(int pIdArticle) throws BusinessException {
	   Article articleTrouve = null;
	   
	   articleTrouve = DAOFactory.getEnchereDAO().selectDetailArticle(pIdArticle);
	   
	   return articleTrouve;
   }
   
   public List<Integer> getNoArticleEncheresRemporteesOuEnCoursById(Integer idUser, Integer typeRqt) throws BusinessException {
	   
	   List<Integer> listeNumeroArticles = new ArrayList<>();
	   
	   if (idUser != null && typeRqt != null && (typeRqt == 1 || typeRqt == 2) ) {
		   listeNumeroArticles = DAOFactory.getEnchereDAO().getEncheresEnCoursOuRemportesById(idUser, typeRqt);
	   } else {
			listeNumeroArticles = null;
			
	   } return listeNumeroArticles;
   }
   /**
    * Méthode qui vérifie que la proposition d'enchère soit supérieur au prixVente ou au prixInitial si pas d'enchères et que le crédit de l'acheteur soit supérieur au prixVente ou au prixInitial si pas d'enchères
    * @return boolean
    * @throws BusinessException
    */
   public boolean propEnchereSup(int pCreditAcheteur, int pProposition, int pPrixEnchere) {
		boolean resultat = false;
			if(pProposition > pPrixEnchere && pCreditAcheteur > pPrixEnchere) {
				resultat = true;
			}
	
			return resultat;
   		}
	   
   /**
    * Méthode qui vérifie si la date de fin de l'enchère est supérieure à la date du jour
    * @param pDateFinEnchere : date de fin de l'enchère
    * @param pDateDuJour : date du jour
    * @return
    */
   public boolean verifDateEnchereNonFinie(Date pDateFinEnchere, Date pDateDuJour) {
	   boolean resultat = false; 

	   if(pDateFinEnchere.compareTo(pDateDuJour) >= 0 ) {
		   resultat = true;
	   }
	   
	   return resultat; 
   }
	
   /**
	* Méthode qui vérifie que l'enchérisseur ne soit pas l'acquéreur   
    * @param pDateDuJour : date du jour
    * @return
    */
   	public boolean VerifStatutUtilisateur(int idUtilisateurAcheteur, int idUtilisateurVendeur) {
   		boolean resultat = false;
   		
   		if(idUtilisateurAcheteur != idUtilisateurVendeur) {
   			resultat = true; 
   		}
   		
   		return resultat; 
   	}
 
   	/**
   	 * Méthode qui met à jour le crédit de l'enchérisseur
   	 * @param pCreditUtilisateur : crédit de l'utilisateur
   	 * @param idUtilisateur : id de l'acheteur
   	 * @return si le credit a été mis à jour ou pas 
   	 * @throws BusinessException
   	 */
   	public boolean creditUpdated(int pCreditUtilisateur, int idUtilisateur) throws BusinessException {
   		
   		boolean creditUpdated = false;
   		
   		creditUpdated = DAOFactory.getEnchereDAO().updateCredit(pCreditUtilisateur, idUtilisateur);
   	
   		return creditUpdated;
   	}
   	
   	/**
   	 * Méthode qui soustrait le crédit de l'acheteur selon le prix de l'enchère
   	 * @param pCreditAcheteur
   	 * @param prixEnchere
   	 * @return le nouveau crédit 
   	 */
   	public int calculNouveauCreditAcheteur(int pCreditAcheteur, int prixEnchere) {
   		int nouveauCredit = ( pCreditAcheteur  - prixEnchere );
   
   		return nouveauCredit;
   	}
   	
   	
   	public int calculNouveauCreditAncienAcheteur(int pCreditAcheteur, int prixEnchere) {
   		int nouveauCredit = ( pCreditAcheteur  + prixEnchere );
   
   		return nouveauCredit;
   	}
   	
   	
   	public Enchere selectAcheteurByIdArticle(int idArticle) throws BusinessException {
   		Enchere enchere = null;
   		enchere = DAOFactory.getEnchereDAO().selectEnchereByIdArticle(idArticle);
   		return enchere;
   	}
   	
   	public void insertEnchere(int idUtilisateur, Date date, int noArticle, int propositionEnchere) throws BusinessException {
   		DAOFactory.getEnchereDAO().insertEnchere( idUtilisateur, date, propositionEnchere, noArticle);
   	}
   	
   	public void updateEnchere( int idUtilisateur, Date dateEnchere, int montant, int noArticle ) throws BusinessException {
   		DAOFactory.getEnchereDAO().updateEnchere(idUtilisateur, dateEnchere, montant, noArticle);
   	}
    
    // Getters & Setters
    private UtilisateurDAO getDAOUtilisateur() {
        return DAOutilisateur;
    }

    private Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
        
}