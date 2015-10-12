package Model;

import Interfaces.ArcInterface;

public abstract class Arc implements ArcInterface{

	
	//ATTRIBUTS
	public String depart;
	public String arrivee;
	public double pheromone;
	public int distanceRoute = 0;       //En KM
	public int distanceAerienne = 0;    //En KM
	public int distanceMer = 0;         //En KM


	
	//CONSTRUCTOR
	public Arc(){
		
	}
	
	public Arc(String capitale1, String capitale2, int distanceRoute, int distanceAerienne, int distanceMer,  int pheromone) {
		this.depart = capitale1;
		this.arrivee = capitale2;
		this.distanceRoute = distanceRoute;
		this.distanceAerienne = distanceAerienne;
		this.distanceMer = distanceMer;
		this.pheromone = pheromone;
	}


	
	//GETTERS AND SETTERS
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrivee() {
		return arrivee;
	}

	public void setArrivee(String arrivee) {
		this.arrivee = arrivee;
	}

	public double getPheromone() {
		return pheromone;
	}

	public void setPheromone(double pheromone) {
		this.pheromone = pheromone;
	}

	public int getDistanceMer() {
		return distanceMer;
	}

	public void setDistanceMer(int distanceMer) {
		this.distanceMer = distanceMer;
	}

	public int getDistanceAerienne() {
		return distanceAerienne;
	}

	public void setDistanceAerienne(int distanceAerienne) {
		this.distanceAerienne = distanceAerienne;
	}

	public int getDistanceRoute() {
		return distanceRoute;
	}

	public void setDistanceRoute(int distanceRoute) {
		this.distanceRoute = distanceRoute;
	}
}
