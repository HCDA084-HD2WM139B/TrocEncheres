package fr.eni.encheres.bo;

import java.io.Serializable;

/**
 * Classe de la couche Métier BO modélisant un Utilisateur (Classe BEAN)
 * @author Groupe 3
 *
 */
public class Utilisateur implements Serializable {

	// Attributs d'instance
	private static final long serialVersionUID = 1L;
	private Integer noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private int credit;
	private boolean administrateur;

	//Constructeurs
	public Utilisateur() {
		noUtilisateur = null;
	}

	/**
	 * @param pPseudo : désigne le pseudo de l'utilisateur
	 * @param pNom : désigne le nom de l'utilisateur
	 * @param pPrenom : désigne le prénom de l'utilisateur
	 * @param pEmail : désigne le email de l'utilisateur
	 * @param pRue : désigne le rue de l'utilisateur
	 * @param pCodePostal : désigne le code postal de l'utilisateur
	 * @param pVille : désigne le ville de l'utilisateur
	 * @param pMotDePasse : désigne le mot de passe de l'utilisateur
	 * @param pCredit : désigne le crédit de l'utilisateur
	 * @param pAdministrateur : précise si l'utilisateur est administrateur ou non
	 */
	public Utilisateur(String pPseudo, String pNom, String pPrenom, String pEmail, String pRue, String pCodePostal,
			String pVille, String pMotDePasse, int pCredit, boolean pAdministrateur) {
		this();
		setPseudo(pPseudo);
		setNom(pNom);
		setPrenom(pPrenom);
		setEmail(pEmail);
		setRue(pRue);
		setCodePostal(pCodePostal);
		setVille(pVille);
		setMotDePasse(pMotDePasse);
		setCredit(pCredit);
		setAdministrateur(pAdministrateur);
	}
	
	public Utilisateur(String pPseudo, String pNom, String pPrenom, String pEmail, String pTelephone, String pRue, String pCodePostal,
			String pVille, String pMotDePasse, int pCredit, boolean pAdministrateur) {
		
		this(pPseudo,pNom,pPrenom,pEmail,pRue,pCodePostal,pVille,pMotDePasse,pCredit,pAdministrateur);
		setTelephone(pTelephone);
	}
	
	public Utilisateur(Integer pNoUtilisateur, String pPseudo, String pNom, String pPrenom, String pEmail, String pTelephone, String pRue, String pCodePostal,
			String pVille, String pMotDePasse, int pCredit, boolean pAdministrateur) {
		
		this(pPseudo,pNom,pPrenom,pEmail,pTelephone,pRue,pCodePostal,pVille,pMotDePasse,pCredit,pAdministrateur);
		setNoUtilisateur(pNoUtilisateur);
	}
	
	
	/**
	 * Méthode toString() permettant d'afficher les informations de l'utilisateur
	 */
	@Override
	public String toString() {
		String utilisateur = null;
		String admin = (this.administrateur)? "OUI" : "NON" ;
		utilisateur = String.format("User [id: %d - pseudo: %s - nom: %s - prenom: %s - email: %s - tel: %s - adresse: %s %s %s - MdP: %s - credit: %d - admin: %s ]", 
				noUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, admin);
		return utilisateur;
	}

	// Getters & Setters

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

}
