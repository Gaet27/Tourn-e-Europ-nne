package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;

import Interfaces.VoyageursInterface;
import Model.Arc;
import Model.Capitale;
import Model.Voyageur;



public class VoyageursController extends Voyageur implements VoyageursInterface {

	//IMPLEMENTS FUNTIONS
	public VoyageursController()
	{
		super();
	}
	
	public VoyageursController(Etat etat, int DistanceArc, int nbCapitaleVisitees, TreeMap<Integer, Capitale> CapitalesVisitees, HashSet<Capitale> CapitalesAVisitees, int KMparcourus) {
		super(etat, DistanceArc, nbCapitaleVisitees, CapitalesVisitees, CapitalesAVisitees, KMparcourus);
	}
	
	//On récupère la liste des arcs parcouru pour pouvoir déposer les phéromones
	public HashSet<Arc> getArcsBetweenCapitales(TreeMap<Integer, Capitale> capitales) {
		HashSet<Arc> ListeArcBetweenCapitale  = new HashSet<Arc>();
		
		String nomDepart = null;
		String nomArrivee = null;
		int cursor = 0;
		int cursor2 = 0;
		
		//ON PARCOURS LA LISTE DES ARCS QUE LE VOYAGEUR A EMPRUNTE
		for(Entry<Integer, Capitale> key : capitales.entrySet()) {
		    			
		    nomDepart = key.getValue().getNom();
		    cursor = key.getKey().intValue();
		    
		    for(Entry<Integer, Capitale> key2 : capitales.entrySet()) {
		    	
		    	cursor2 = key2.getKey().intValue();
		    	//APRES AVOIR RECUPERER LE NOM DE LA PREMIERE Capitale ON RECUPERE LE NOM DE LA Capitale SUIVANTE
		    	if (cursor2 > cursor){
		    		nomArrivee = key2.getValue().getNom();
		    		break;
		    	}
		    }   
		    
			for (Arc key3 : key.getValue().getArcCapitale())
			{
				//ENSUITE SI L'ARC CONTIENT LE NOM DE LA PREMIERE CAPITALE DANS SON NOM DE DEPART ET LE NOM DE LA 
				//DEUXIEME CAPITALE DANS SON NOM D'ARRIVEE ALORS ON L'AJOUTE DANS LA LISTE RETOURNEE
				if(nomDepart.equals(key3.getDepart()) && nomArrivee.equals(key3.getArrivee()))
				{
					ListeArcBetweenCapitale.add(key3);
				}
			}
		}
		return ListeArcBetweenCapitale;
	}
	
	
	//On indique la prochaine Capitale à visiter
	public Capitale findNextCapitale(Voyageur voyageur){
		
		ArrayList<Arc> ListeArcPheromone = new ArrayList<Arc>();
		Capitale prochaineCapitale = new CapitaleController();
		boolean capitaleViable = false;
		int cpt = 0;
		
		do
		{
			//ON PARCOURS LA LISTE DES ARCS DU METRO PASSE EN PARAMETRE
			for (Arc arcCapitale : voyageur.getCapitaleCurrent().getArcCapitale()) {
				
				double distanceRoute = Math.round(arcCapitale.getDistanceRoute()); 
				double distanceAerienne = Math.round(arcCapitale.getDistanceAerienne()); 
				double distanceMer = Math.round(arcCapitale.getDistanceRoute()); 
				double distanceGlobale = distanceRoute + distanceAerienne + distanceMer;
				
				//Plus la distance est petite meilleure est la note
				int noteD = noteDistance(distanceGlobale);
				
				double txPheromone = 0;
				if (arcCapitale.getPheromone() != 0)
				{
					txPheromone = (arcCapitale.getPheromone()*10);
				}
	
				
				//EN FONCTION DE LA DISTANCE ET DU NOMBRE DE PHEROMONE, PLUS LE NOMBRE DE PHEROMONE
				//EST IMPORTANT ET PLUS LA DISTANCE EST COURTE SUR L'ARC, PLUS IL AURA DE CASE DANS LE TABLEAU
				for (int i = 0; i < noteD + txPheromone; i++) {
					ListeArcPheromone.add(arcCapitale);
				}			
			}
			
			//ON TIRE UN NOMBRE ALEATOIRE ENTRE 1 ET LA TAILLE DU TABLEAU
			int nombreAleatoire = (int)(Math.random() * (ListeArcPheromone.size() - 0)) + 0;
			//L'ARC QUE L'ON RECUPERE EST EGAL A L'INDEX DU NOMBRE ALEATOIRE
			Arc arc = ListeArcPheromone.get(nombreAleatoire);
			//ON RECUPERE LA PROCHAINE Capitale EN PASSANT l'ID DE l'ARRIVE DE L'ARC DONC l'ID DE LA Capitale DE DESTINATION
			prochaineCapitale = prochaineCapitale.getCapitaleNom(arc.getArrivee());
			
			capitaleViable = false;
			if(voyageur.CapitalesVisitees.containsValue(prochaineCapitale))
			{
				capitaleViable = true;
				cpt += 1;
			}
			if(cpt > 10)
			{
				//Pour qu'il soit supprimer à son prochain passage dans le main, car ça signifie que l'on a fait revenir le voyageur
				//d'une capitale en arrière et que l'on a chercher une autre destination 10 fois, donc on le supprime
				voyageur.setNbCapitaleVisitees(voyageur.getNbCapitaleVisitees()+100000);
				break;
			}
			
		}while(capitaleViable);
		
		//On définit le moyen de transport pour l'arc qui va nous mener à la capitale choisie
		Arc arc = findArcByNomCapitale(voyageur.getCapitaleCurrent(), prochaineCapitale);
		String transport = choixMoyenDeTransport(arc);
		
		voyageur.setMoyenDeTransport(transport);
		voyageur.setMoyensDeTransportUtilises(transport);
		
		switch (transport)
		{
			case "Voiture":		
				voyageur.setDistanceArc(arc.getDistanceRoute());
				voyageur.setTempsTotal(voyageur.getTempsTotal()+(voyageur.getDistanceArc() / 130));
				voyageur.setPrixTotal(voyageur.getPrixTotal()+(voyageur.getDistanceArc() * 0.19));
				break;
			
			case "Autocar":
				voyageur.setDistanceArc(arc.getDistanceRoute());
				voyageur.setTempsTotal(voyageur.getTempsTotal()+(voyageur.getDistanceArc() / 90));
				voyageur.setPrixTotal(voyageur.getPrixTotal()+(voyageur.getDistanceArc() * 0.07));
				break;
			
			case "Bateau":
				voyageur.setDistanceArc(arc.getDistanceMer());
				voyageur.setTempsTotal(voyageur.getTempsTotal()+(voyageur.getDistanceArc() / 40));
				voyageur.setPrixTotal(voyageur.getPrixTotal()+(voyageur.getDistanceArc() * 0.08));
				break;
			
			case "Train":
				voyageur.setDistanceArc(arc.getDistanceRoute());
				voyageur.setTempsTotal(voyageur.getTempsTotal()+(voyageur.getDistanceArc() / 320));
				voyageur.setPrixTotal(voyageur.getPrixTotal()+(voyageur.getDistanceArc() * 0.10));
				break;
			
			case "Avion":
				voyageur.setDistanceArc(arc.getDistanceAerienne());
				voyageur.setTempsTotal(voyageur.getTempsTotal()+(voyageur.getDistanceArc() / 900 + 1));
				voyageur.setPrixTotal(voyageur.getPrixTotal()+(voyageur.getDistanceArc() * 0.15));
				break;
		}
		
		//Ajoute à la distance totale, la distance qui vient d'être déterminée
		voyageur.setDistanceTotale(voyageur.getDistanceTotale()+voyageur.getDistanceArc());		
		
		

		return prochaineCapitale;
	}
	
	
	private int noteDistance(double distanceGlobale)
	{
		int note = 0;
		
		if (distanceGlobale <= 200)
			note = 10;
		if (distanceGlobale > 200 && distanceGlobale <= 500)
			note = 9;
		if (distanceGlobale > 500 && distanceGlobale <= 1000)
			note = 8;
		if (distanceGlobale > 1000 && distanceGlobale <= 1500)
			note = 7;
		if (distanceGlobale > 1500 && distanceGlobale <= 2000)
			note = 6;
		if (distanceGlobale > 2000 && distanceGlobale <= 3500)
			note = 5;
		if (distanceGlobale > 3500 && distanceGlobale <= 5000)
			note = 4;
		if (distanceGlobale > 5000 && distanceGlobale <= 6500)
			note = 3;
		if (distanceGlobale > 6500 && distanceGlobale <= 7500)
			note = 2;
		if (distanceGlobale > 7500 && distanceGlobale <= 8500)
			note = 1;
		if (distanceGlobale > 8500)
			note = 1;
		
		return note;
	}
	
	private int noteTransport(double tempsTrajet, double prix)
	{
		int note = 0;
		
		//LE PLUS COURT
		//MEILLEUR TEMPS / PRIX
		if (tempsTrajet <= 2)
			note = 10;
		if (tempsTrajet > 2 && tempsTrajet <= 4)
			note = 9;
		if (tempsTrajet > 4 && tempsTrajet <= 8)
			note = 8;
		if (tempsTrajet > 8 && tempsTrajet <= 15)
			note = 7;
		if (tempsTrajet > 15 && tempsTrajet <= 20)
			note = 6;
		if (tempsTrajet > 20 && tempsTrajet <= 25)
			note = 5;
		if (tempsTrajet > 25 && tempsTrajet <= 35)
			note = 4;
		if (tempsTrajet > 35 && tempsTrajet <= 55)
			note = 3;
		if (tempsTrajet > 55 && tempsTrajet <= 75)
			note = 2;
		if (tempsTrajet > 75 && tempsTrajet <= 90)
			note = 1;
		if (tempsTrajet > 90)
			note = 1;	
		
		//LE PLUS LENT
//		if (tempsTrajet > 90)
//			note = 10;
//		if (tempsTrajet < 90 && tempsTrajet >= 75)
//			note = 9;
//		if (tempsTrajet < 75 && tempsTrajet >= 55)
//			note = 8;
//		if (tempsTrajet < 55 && tempsTrajet >= 35)
//			note = 7;
//		if (tempsTrajet < 35 && tempsTrajet >= 25)
//			note = 6;
//		if (tempsTrajet < 25 && tempsTrajet >= 20)
//			note = 5;
//		if (tempsTrajet < 20 && tempsTrajet >= 15)
//			note = 4;
//		if (tempsTrajet < 15 && tempsTrajet >= 8)
//			note = 3;
//		if (tempsTrajet < 8 && tempsTrajet >= 4)
//			note = 2;
//		if (tempsTrajet < 4 && tempsTrajet >= 2)
//			note = 1;
//		if (tempsTrajet < 2)
//			note = 1;	
		
		//MEILLEUR TEMPS / PRIX
		//LE MOINS CHER
		if (prix <= 100)
			note = note + 10;
		if (prix > 100 && prix <= 200)
			note = note + 9;
		if (prix > 200 && prix <= 300)
			note = note + 8;
		if (prix > 300 && prix <= 400)
			note = note + 7;
		if (prix > 500 && prix <= 600)
			note = note + 6;
		if (prix > 600 && prix <= 700)
			note = note + 5;
		if (prix > 800 && prix <= 900)
			note = note + 4;
		if (prix > 1000 && prix <= 1100)
			note = note + 3;
		if (prix > 1100 && prix <= 1200)
			note = note + 2;
		if (prix > 1300 && prix <= 1400)
			note = note + 1;
		if (prix > 1400)
			note = note + 1;	
		
		return note;
	}

	
	//LE CHOIX DE LA CAPITALE EST FAIT
	//IL FAUT MAINTENANT CHOISIR LE MOYEN DE TRANSPORT
	private String choixMoyenDeTransport(Arc arc){
		
		ArrayList<String> choixTransport = new ArrayList<String>();
		
		if(arc.getDistanceRoute() != 0)
		{
			//AUTOCAR
			double tempsTrajetAutocar = arc.getDistanceRoute() / 90;
			double prixAutocar = arc.getDistanceRoute() * 0.07;
			
			int noteAutocar = noteTransport(tempsTrajetAutocar, prixAutocar);
			for (int i = 0; i < noteAutocar; i++) {
				choixTransport.add("Autocar");
			}
			
			//TRAIN
			double tempsTrajetTrain = arc.getDistanceRoute() / 320;
			double prixTrain = arc.getDistanceRoute() * 0.10;
			int noteTrain = noteTransport(tempsTrajetTrain, prixTrain);
			for (int i = 0; i < noteTrain; i++) {
				choixTransport.add("Train");
			}	
			
			//VOITURE
			double tempsTrajetVoiture = arc.getDistanceRoute() / 130;
			double prixVoiture = arc.getDistanceRoute() * 0.19;
			int noteVoiture = noteTransport(tempsTrajetVoiture, prixVoiture);
			for (int i = 0; i < noteVoiture; i++) {
				choixTransport.add("Voiture");
			}
		}

		if(arc.getDistanceAerienne() != 0)
		{
			//AVION
			double tempsTrajetAvion = arc.getDistanceAerienne() / 900 + 1;
			double prixAvion = arc.getDistanceAerienne() * 0.15;
			int noteAvion = noteTransport(tempsTrajetAvion, prixAvion);
			for (int i = 0; i < noteAvion; i++) {
				choixTransport.add("Avion");
			}	
		}
		
		if(arc.getDistanceMer() != 0)
		{
			//BATEAU
			double tempsTrajetBateau = arc.getDistanceMer() / 40;
			double prixBateau = arc.getDistanceMer() * 0.08;
			int noteBateau = noteTransport(tempsTrajetBateau, prixBateau);
			for (int i = 0; i < noteBateau; i++) {
				choixTransport.add("Bateau");
			}	
		}
		
		
		//EN FONCTION DU TEMPS DE TRAJET ET DU PRIX
		//PLUS LE TEMPS DE TRAJET EST COURT ET PLUS LE PRIX EST FAIBLE,
		//PLUS LA NOTE EST ELEVEE ET PLUS LE TRANSPORT AURA DE CASE DANS LE TABLEAU
	
		//ON TIRE UN NOMBRE ALEATOIRE ENTRE 1 ET LA TAILLE DU TABLEAU
		int nombreAleatoire = (int)(Math.random() * (choixTransport.size() - 0)) + 0;
		//LE TRANSPORT QUE L'ON RECUPERE EST EGAL A L'INDEX DU NOMBRE ALEATOIRE
		String transport = choixTransport.get(nombreAleatoire);
		
		return transport;
	}
	
	
	//On retrouve un arc à partir du nom des deux capitales correspondates
	public Arc findArcByNomCapitale(Capitale current, Capitale next){
		Arc arc = new ArcController();
		for (Arc key : current.getArcCapitale())
		{
			if (key.getDepart().equals(current.getNom()) && key.getArrivee().equals(next.getNom())){
				arc = key;
			}
		}
		return arc;
	}
	
	
	//LISTE DE TOUTES LES INSTANCES DE CETTE CLASSE
	public static final ArrayList<Voyageur> ListeVoyageur = new ArrayList<Voyageur>();
	{
		ListeVoyageur.add(this);
	}
}
