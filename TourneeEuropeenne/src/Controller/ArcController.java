package Controller;

import java.util.ArrayList;

import Interfaces.ArcInterface;
import Model.*;


public class ArcController extends Arc implements ArcInterface{

	//IMPLEMENTS FUNTIONS
	public ArcController(String capitale1, String capitale2, int distanceRoute, int distanceAerienne, int distanceMer,  int pheromone) {
		super(capitale1, capitale2, distanceRoute, distanceAerienne, distanceMer, pheromone);
	}
	
	public ArcController(){
		super();
	}
	
	
	//LISTE DE TOUTES LES INSTANCES DE CETTE CLASSE
	public static final ArrayList<Arc> ListeArc = new ArrayList<Arc>();
	{
		ListeArc.add(this);
	}
}
