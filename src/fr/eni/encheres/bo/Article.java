package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private Integer noArticle;
	private String nomArticle;
	private String description;
	private Date dateDebutEnchere;
	private Date dateFinEchere;
	private int prixInitial;
	private int prixVente; 
	private Categorie categorie;
	private Utilisateur vendeur;
	
	// ---------------------------- Constructeur ----------------------------
	
	public Article() {
		noArticle = null;
	}
	
	public Article(int noArticle, String nomArticle, String description, Date dateDebutEnchere, Date dateFinEchere,
			int prixInitial, int prixVente, Categorie categorie, Utilisateur vendeur) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEnchere = dateDebutEnchere;
		this.dateFinEchere = dateFinEchere;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.categorie = categorie;
		this.vendeur = vendeur;
	}
	
	// ---------------------------- Getter & Settter ---------------------------- 

	public Integer getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebutEnchere() {
		return dateDebutEnchere;
	}

	public void setDateDebutEnchere(Date dateDebutEnchere) {
		this.dateDebutEnchere = dateDebutEnchere;
	}

	public Date getDateFinEchere() {
		return dateFinEchere;
	}

	public void setDateFinEchere(Date dateFinEchere) {
		this.dateFinEchere = dateFinEchere;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	} 

	
}
