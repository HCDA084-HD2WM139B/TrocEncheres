package fr.eni.encheres.bo;

import java.util.Date;

/**
 * Cette classe permet de générer une enchère
 * @author groupe 3
 *
 */
public class Enchere {
	
	private Integer id;
	private Date dateEnchere;
	private Integer montantEnchere; 
	private Utilisateur acheteur;
	private Article articleVendu;
	
	// Constructeurs
	
	public Enchere() {
		 this.id = null;
		 this.dateEnchere = null;
		 this.montantEnchere = null;
		 this.acheteur = null;
		 this.articleVendu = null;
	}
	
	/**
	 * @param id : identifiant de l'enchere
	 * @param dateEnchere : désigne la date de l'enchère en cours
	 * @param montantEnchere : désigne le montant de l'enchère
	 * @param vendeur : désigne l'utilisateur qui vend l'article
	 * @param articleVendu : désigne l'article vendu
	 */
	public Enchere(Integer id, Date dateEnchere, Integer montantEnchere, Utilisateur acheteur, Article articleVendu) {
		this.id = id;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.acheteur = acheteur;
		this.articleVendu = articleVendu;
	}

	// Getters & setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public Integer getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(Integer montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Utilisateur getAcheteur() {
		return acheteur;
	}

	public void setAcheteur(Utilisateur acheteur) {
		this.acheteur = acheteur;
	}

	public Article getArticleVendu() {
		return articleVendu;
	}

	public void setArticleVendu(Article articleVendu) {
		this.articleVendu = articleVendu;
	}
	
	
}
