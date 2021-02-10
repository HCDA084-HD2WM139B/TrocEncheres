package fr.eni.encheres.bo;

public class Categorie {
	private Integer noCategorie;
	private String libelle;
	
	public Categorie() {
		noCategorie = null;
	}
	
	public Categorie(String libelle) {
		super();
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
		
}
