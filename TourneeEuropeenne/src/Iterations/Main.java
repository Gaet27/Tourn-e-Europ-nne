package Iterations;
import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Map.Entry;

import Controller.ArcController;
import Controller.CapitaleController;
import Controller.VoyageursController;
import Model.Arc;
import Model.Capitale;
import Model.Voyageur;
import Model.Voyageur.Etat;


public class Main {

	////////////////////////////////////////////////////////////////////////////////
	////////////////----------ON RENSEIGNE ICI -------------------//////////////////
	////////////////////////////////////////////////////////////////////////////////
	
	//LE NOMBRE DE Voyageur
	final static int nbVoyageurParIteration = 3;
	//LE NOMBRE D'ITERATIONS
	final static int nbIterations = 300;
	//LE TAUX D'EVAPORATION
	final static double tauxEvaporation = 0.02;
	//DE COMBIEN AVANCE LES VOYAGEURS PAR ITERATION SUR UN ARC EN SECONDES
	final static int distanceKM = 500;  
	//LA Capitale DE DEPART DES VOYAGEURS
	final static String CapitaleDeDepart = "Bruxelles";
	//PERMET D'AUGMENTER LA VALEUR D'UN THREAD SLEEP, POUR VOIR LES VALEURS DES VOYAGEURS ENTRE CHAQUE ITERATION
	final static int tempsEntreIteration = 0;
	
	
	public static void main(String[] args) {
		
		Capitale CapitaleDepart = new CapitaleController();
		Capitale prochaineCapitale = new CapitaleController();
		
		//CREATION DES CAPITALES ET DES ARCS VIA LES FICHERS TXT
		creerArcs();
		creerCapitales();
		
//		//AFFICHAGE DES ARCS ET CAPITALES DES FICHIER TXT
//		for (Capitale key : CapitaleController.ListeCapitale)
//		{
//			System.out.println(key.getNom());
//			System.out.println(" ");
//			for(Arc key2: key.getArcCapitale()){
//				 System.out.println(key2.getArrivee() + " : ");
//				 System.out.println(" * Distance Route : " + key2.getDistanceRoute());
//				 System.out.println(" * Distance Aerienne : " + key2.getDistanceAerienne());
//				 System.out.println(" * Distance Mer : " + key2.getDistanceMer());
//			}
//			System.out.println("\n---------------------------------------\n");
//		}

		//ITERATIONS
		for (int i = 1; i <= nbIterations; i++) {
			
			//EFFECTUE L EVAPORATION DES PHEROMONE A CHAQUE ITERATION
			evaporation();
			
			//CREER LES NOUVEAUX VoyageurS
			premiereCapitale(CapitaleDepart, prochaineCapitale);
			
			//POUR CHAQUE Voyageur FAIRE EXISTANT ET NOUVEAU FAIRE
			for(Voyageur key : VoyageursController.ListeVoyageur)
			{
				//SI LE NOMBRE DE Capitale VISITE EST SUPERIEUR A 100, ON SUPPRIME LE VOYAGEUR
				if(key.getNbCapitaleVisitees() >= 18)
				{
					key = null;
				}
				else
				{
					switch (key.etat) {
					
					//LANCE LE VOYAGEUR EN PARTANT DE BRUXELLES
					case depart:
						demarrerVoyageur(key);
						break;
						
					case enCours:
						//POUR LES VOYAGEURS EN COURS DE TOURNEE
						cherche(key, CapitaleDepart, prochaineCapitale);
						break;
						
					case arrivee:
						//LE Voyageur RENTRE, LES PHEROMONES SONT DEPOSES, DONC ON LE SUPPRIME
						suprVoyageur(key);
						break;
					}
				}
			}
		}
	}

	
	
	
	public static void premiereCapitale(Capitale CapitaleDepart, Capitale prochaineCapitale){
		
		//CREATIONS DE NOUVEAUX VOYAGEURS
		for (int j = 1; j <= nbVoyageurParIteration; j++)
		{
			//CREATION DU VOYAGEUR
			Voyageur voyageur = new VoyageursController();
			CapitaleDepart = CapitaleDepart.getCapitaleNom(CapitaleDeDepart);
			//ON AJOUTE DANS SA LISTE DE CAPITALES A VISITER TOUTES LES CAPITALES SAUF LA CAPITALE DE DEPART
			for (Capitale key : CapitaleController.ListeCapitale)
			{
				if(key.getNom() != CapitaleDeDepart)
				voyageur.setCapitalesAVisitees(key);
			}
			//ON AJOUTE LA Capitale DE DEPART A LA LISTE DES Capitales VISITES
			voyageur.setCapitalesVisitees(CapitaleDepart);
			//SA Capitale COURANTE, LA DERNIERE VISITEE
			voyageur.setCapitaleCurrent(CapitaleDepart);
			//ON LUI TROUVE LA PROCHAINE Capitale A ATTEINDRE AVEC UN FONCTIONNEMENT PAR ROUE BIAISEE
			prochaineCapitale = voyageur.findNextCapitale(voyageur);
			
			voyageur.setCapitaleDestination(prochaineCapitale);
			
			voyageur.setEtat(Etat.depart);
			//ON PASSE LE NOMBRE DE Capitale VISITE A UN, LA Capitale DE DEPART
			voyageur.setNbCapitaleVisitees(1);
		}
	}
	
	//PASSE LE Voyageur DE L'ETAT DEPART A ENCOURS
	public static void demarrerVoyageur(Voyageur voyageur){
		voyageur.setEtat(Etat.enCours);
		voyageur.setKMparcourus(distanceKM);
	}
	
	
	public static void cherche(Voyageur voyageur, Capitale CapitaleDepart, Capitale prochaineCapitale){
		
		HashSet<Arc> arcsParcourus = new HashSet<Arc>();
		//SI LE Voyageur A FINI DE PARCOURIR SON ARC
		if (voyageur.getKMparcourus() >= voyageur.getDistanceArc()) 
		{
			//ON AJOUTE LA Capitale DE DESTINATION QUE L'ON VIENT D'ATTEINDRE AUX Capitales VISITES
			voyageur.setCapitalesVisitees(voyageur.getCapitaleDestination());
			//ON ENLEVE LA Capitale DES Capitales A VISITER
			voyageur.CapitalesAVisitees.remove(voyageur.getCapitaleDestination());
			CapitaleDepart = voyageur.getCapitaleCurrent();
			//LA Capitale COURANTE DEVIENT LA DESTINATION ATTEINTE
			voyageur.setCapitaleCurrent(voyageur.getCapitaleDestination());
			voyageur.nbCapitaleVisitees++;
			
			//si la liste des capitales à visiter est vide
			//ALORS ON DEPOSE LES PHEROMONES !
			if (voyageur.getNbCapitaleVisitees() == 17)
			{
				//ON RECUPERE LA LISTE D'ARC PARCOURUE PAR LE Voyageur
				arcsParcourus = voyageur.getArcsBetweenCapitales(voyageur.getCapitalesVisitees());
				//ON AJOUTE 1 pheromone A CHAQUE ARC
				for (Arc arcs : arcsParcourus) {
					arcs.setPheromone(arcs.getPheromone()+1);
				}
				
				voyageur.setEtat(Etat.arrivee);
				
			}else{
				//On stocke le déplacement en trop sur un arc pour le ré-affecter sur le nouvel arc
				int residuKM = voyageur.KMparcourus - voyageur.DistanceArc;
				voyageur.setKMparcourus(residuKM);
				
				// On envoi le Voyageur vers une nouvelle Capitale
				prochaineCapitale = voyageur.findNextCapitale(voyageur);
				voyageur.setCapitaleDestination(prochaineCapitale);		
				
				voyageur.KMparcourus += distanceKM;
			}			
		}else{
			voyageur.KMparcourus += distanceKM;
		}
		
		
		//AFFICHAGE DE CHAQUE Voyageur une fois les 17 capitales parcourues
		if(voyageur.getNbCapitaleVisitees() == 17)
		{
			System.out.println("Etat du voyageur : " + voyageur.getEtat());
			System.out.println("Nombre de Capitales visitées : " + voyageur.getNbCapitaleVisitees());
			System.out.println("________________________________________");
			int i = 1;
			for(Entry<Integer, Capitale> key : voyageur.getCapitalesVisitees().entrySet()) {
				System.out.println("Capitale " + i + " : " + key.getValue().getNom());
				i++;
			}
			System.out.println("________________________________________");
			i = 1;
			for(Entry<Integer, String> key : voyageur.getMoyensDeTransportUtilises().entrySet()) {
				System.out.println("Transport " + i + " : " + key.getValue());
				i++;
			}
			System.out.println("________________________________________");
			i = 1;
			for (Arc arcs : arcsParcourus) {
				System.out.println("Arc " + i + " : " + arcs.getPheromone());
				i++;
			}
			System.out.println("________________________________________");
			System.out.println("Distance totale " + voyageur.getDistanceTotale());
			System.out.println("Prix total : " + voyageur.getPrixTotal());
			System.out.println("Temps total : " + voyageur.getTempsTotal());
			System.out.println("\n---------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------\n");
	
			//PERMET DE RALENTIR L'AFFICHAGE ENTRE DEUX VoyageurS
			try {
				Thread.sleep(tempsEntreIteration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void evaporation(){
	
		for (Arc key : ArcController.ListeArc)
		{
			//SI L'OBJET EST NON NUL ON EFFECTUE L'EVAPORATION DEFINIT DANS LES STATICS
			if(key.getDepart() != null)
			{
				double phe = key.getPheromone();
				phe = phe * (1 - tauxEvaporation);
				phe = Math.floor(10 * phe)/10;
				key.setPheromone(phe);
			}
		}
	}
	
	
	//SURRPIME LE Voyageur
	public static void suprVoyageur(Voyageur voyageur){
			voyageur = null;
	}
	
	
	//PARCOURS DU FICHIER DES CAPITALES, ON CREER LES OBJETS CAPITALE
	public static void creerCapitales (){
		try{
			Scanner s = new Scanner (new File ("Capitales.txt")).useDelimiter("\\s+");
			while (s.hasNext()) {
				Capitale capitale = new CapitaleController();
				capitale.setNom(s.next());
				capitale.lier(capitale);
			}
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	//PARCOURS DU FICHIER ARC, ON CREER LES OBJETS ARCS
	public static void creerArcs (){
		try{
			Scanner s = new Scanner (new File ("Arcs.txt")).useDelimiter("\\s+");
			while (s.hasNext()) {
				Arc arc = new ArcController();
				arc.setDepart(s.next());
				arc.setArrivee(s.next());
				arc.setDistanceRoute(s.nextInt());
				arc.setDistanceAerienne(s.nextInt());
				arc.setDistanceMer(s.nextInt());
				arc.setPheromone(0);
			}
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
}
