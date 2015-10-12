package Interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import Model.Arc;
import Model.Voyageur;
import Model.Capitale;

public interface VoyageursInterface {

	//DECLARE FUNCTIONS
	HashSet<Arc> getArcsBetweenCapitales(TreeMap<Integer, Capitale>  capitales);
	Capitale findNextCapitale(Voyageur voyageur);
	Arc findArcByNomCapitale(Capitale current, Capitale next);
	static final ArrayList<Voyageur> ListeVoyageur = new ArrayList<Voyageur>();
}
