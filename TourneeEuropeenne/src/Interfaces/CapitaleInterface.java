package Interfaces;

import java.util.ArrayList;
import java.util.HashSet;

import Model.Arc;
import Model.Capitale;

public interface CapitaleInterface {

	//DECLARE FUNCTIONS
	void lier(Capitale capitale);
	HashSet<Arc> getArcNomCapitale(String nom);
	Capitale getCapitaleNom(String nom);
	static final ArrayList<Capitale> ListeCapitale = new ArrayList<Capitale>();
}
