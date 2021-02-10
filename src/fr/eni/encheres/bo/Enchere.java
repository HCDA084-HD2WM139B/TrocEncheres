package fr.eni.encheres.bo;

import java.util.Date;

public class Enchere {
	private Date dateEnchere;
	private int montantEnchere; 
	private Utilisateur vendeur;
	private Article articleVendu;
	
	// --------------------------- Constructeur ---------------------------
	
	public Enchere(Date dateEnchere, int montantEnchere, Utilisateur vendeur, Article articleVendu) {
		super();
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.vendeur = vendeur;
		this.articleVendu = articleVendu;
	}
	
	public Enchere() {
		 
	}
	
	// --------------------------- Getters & Setters ---------------------------

	
	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public Article getArticleVendu() {
		return articleVendu;
	}

	public void setArticleVendu(Article articleVendu) {
		this.articleVendu = articleVendu;
	}
	
	
	
}
