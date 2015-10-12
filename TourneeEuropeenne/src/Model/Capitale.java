package Model;


import java.util.HashSet;

import Interfaces.CapitaleInterface;

public abstract class Capitale implements CapitaleInterface{
	
	
	//ATRIBUTS
	public String nom;
	public HashSet<Arc> ArcCapitale = new HashSet<Arc>();  //HASHSET NE PERMET PAS LES DOUBLONS
	
	
	//CONSTRUCTOR
	public Capitale() {

	}

	
	//GETTERS AND SETTERS
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public HashSet<Arc> getArcCapitale() {
		return ArcCapitale;
	}
	public void setArcCapitale(Arc arcCapitale) {
		ArcCapitale.add(arcCapitale);
	}
}
