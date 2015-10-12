package Controller;

import java.util.ArrayList;
import java.util.HashSet;

import Interfaces.CapitaleInterface;
import Model.Arc;
import Model.Capitale;


public class CapitaleController extends Capitale implements CapitaleInterface{

	
	//IMPLEMENTS FUNTIONS
	public CapitaleController() {
		super();
	}
	
	//CREER LES ARCS CORRESPONDANT ET IMPLEMENTE LA LISTE DES ARCS DE CHAQUE CAPITALE
	public void lier(Capitale capitale){
		String nom = capitale.getNom();
		for (Arc key : ArcController.ListeArc) {
			if (nom.equals(key.getDepart())) {
				capitale.setArcCapitale(key);
			}
		}
	}
	
	
	//RECUPERE LA LISTE DES ARCS EN FONCTIONS DU NOM DE LA CAPITALE
	public HashSet<Arc> getArcNomCapitale(String nom) {
		
		for (Capitale key : ListeCapitale)
		{
			if(nom.equals(key.getNom())){
				ArcCapitale = key.getArcCapitale();
			}
		}
		return ArcCapitale;
	}
	
	
	//RETOURNE UNE CAPITALE EN FONCTION DE SON NOM
	public Capitale getCapitaleNom(String nom) {
		Capitale capitale = new CapitaleController();
		for (Capitale key : ListeCapitale)
		{
			if(nom.equals(key.getNom())){
				capitale = key;
			}
		}
		return capitale;
	}
	
	
	//LISTE DE TOUTES LES INSTANCES DE CETTE CLASSE
	public static final ArrayList<Capitale> ListeCapitale = new ArrayList<Capitale>();
	{
		ListeCapitale.add(this);
	}
}
