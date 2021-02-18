package fr.eni.encheres.bo;

/**
 * Cette classe permet de générer une catégorie d'articles pour les enchères
 * @author groupe 3
 *
 */
public class Categorie {
	private Integer noCategorie;
	private String libelle;
	
	
	//Constructeurs 
	public Categorie() {
		noCategorie = null;
		libelle = null;
	}
	
	/**
	 * @param noCategorie : définit le numéro de la catégorie 
	 * @param libelle : libellé de la catégorie
	 */
	public Categorie(Integer noCategorie, String libelle) {
		this.libelle = libelle;
		this.noCategorie = noCategorie;
	}
	
	public Categorie(String libelle) {
		this.libelle = libelle;
	}

	//Getters & Setters
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
		
	public Integer getnoCategorie() {
		return noCategorie;
	}
	
	public void setnoCategorie(Integer noCategorie) {
		this.noCategorie = noCategorie;
	}
}
