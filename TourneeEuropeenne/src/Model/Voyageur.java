package Model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import Interfaces.VoyageursInterface;


public abstract class Voyageur implements VoyageursInterface{
	
	//ATTRIBUTS
	public Etat etat;
	public int DistanceArc;   //Distance de l'arc actuellement parcoru
	
	public enum Etat{
		depart,
		enCours,
		arrivee
	}
	
	public int nbCapitaleVisitees;
	public HashSet<Capitale> CapitalesAVisitees = new HashSet<Capitale>();
	private static Integer cursor = 0;
	private static Integer cursor2 = 0;

	public TreeMap<Integer, Capitale> CapitalesVisitees = new TreeMap<Integer, Capitale>();
	public TreeMap<Integer, String> MoyensDeTransportUtilises = new TreeMap<Integer, String>();
	public double distanceTotale = 0;
	public double prixTotal = 0;
	public double tempsTotal = 0; 
	
	public int KMparcourus;                      // Sa position en KM sur l'arc par rapport à la distance totale
	public Capitale CapitaleCurrent;	         // Dernière Capitale atteinte
	public String moyenDeTransport;				 //Type générique pour tous les moyens de transport
	public Capitale CapitaleDestination;         // Prochaine Capitale à atteindre	if(arc.getDistanceRoute() != 0)
	
	
	//CONSTRUCTOR
	public Voyageur(){
	}
	
	public Voyageur(Etat etat, int DistanceArc, int nbCapitaleVisitees,TreeMap<Integer, Capitale> CapitalesVisitees,HashSet<Capitale> CapitalesAVisitees, int KMparcourus){
		this.etat = etat;
		this.DistanceArc = DistanceArc;
		this.nbCapitaleVisitees = nbCapitaleVisitees;
		this.CapitalesVisitees = CapitalesVisitees;
		this.CapitalesAVisitees = CapitalesAVisitees;
		this.KMparcourus = KMparcourus;
	}
	
	
	
	//GETTERS END SETTERS
	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public int getDistanceArc() {
		return DistanceArc;
	}

	public void setDistanceArc(int DistanceArc) {
		this.DistanceArc = DistanceArc;
	}

	public int getNbCapitaleVisitees() {
		return nbCapitaleVisitees;
	}

	public void setNbCapitaleVisitees(int nbCapitaleVisitees) {
		this.nbCapitaleVisitees = nbCapitaleVisitees;
	}

	public TreeMap<Integer, Capitale> getCapitalesVisitees() {
		return CapitalesVisitees;
	}

	public void setCapitalesVisitees(Capitale Capitale) {
		this.CapitalesVisitees.put(getAndIncrement(), Capitale);
	}

	public HashSet<Capitale> getCapitalesAVisitees() {
		return CapitalesAVisitees;
	}

	public void setCapitalesAVisitees(Capitale CapitalesAVisitees) {
		this.CapitalesAVisitees.add(CapitalesAVisitees);
	}

	public int getKMparcourus() {
		return KMparcourus;
	}

	public void setKMparcourus(int KMparcourus) {
		this.KMparcourus = KMparcourus;
	}

	public Capitale getCapitaleCurrent() {
		return CapitaleCurrent;
	}

	public void setCapitaleCurrent(Capitale CapitaleCurrent) {
		this.CapitaleCurrent = CapitaleCurrent;
	}

	public Capitale getCapitaleDestination() {
		return CapitaleDestination;
	}

	public void setCapitaleDestination(Capitale CapitaleDestination) {
		this.CapitaleDestination = CapitaleDestination;
	}
	
	public Object getMoyenDeTransport() {
		return moyenDeTransport;
	}

	public void setMoyenDeTransport(String moyenDeTransport) {
		this.moyenDeTransport = moyenDeTransport;
	}

	public Integer getAndIncrement(){
		this.cursor++;
		return cursor;
	}	
	
	public Integer getAndIncrement2(){
		this.cursor2++;
		return cursor;
	}
	
	public void setMoyensDeTransportUtilises(String transport) {
		this.MoyensDeTransportUtilises.put(getAndIncrement2(), transport);
	}

	public TreeMap<Integer, String> getMoyensDeTransportUtilises() {
		return MoyensDeTransportUtilises;
	}

	public double getDistanceTotale() {
		return distanceTotale;
	}

	public void setDistanceTotale(double distanceTotale) {
		this.distanceTotale = distanceTotale;
	}

	public double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(double prixTotal) {
		this.prixTotal = prixTotal;
	}

	public double getTempsTotal() {
		return tempsTotal;
	}

	public void setTempsTotal(double tempsTotal) {
		this.tempsTotal = tempsTotal;
	}
}
