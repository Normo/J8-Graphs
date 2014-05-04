package J8Graphs.model;

import java.util.LinkedList;

/**
 * Basisklasse für Knoten in gerichteten Graphen.
 * @author Undisputed
 * @author normo
 *
 */
public class Node {

	/**
	 * Knotennummer
	 */
	public int Id;
	
	/**
	 * Verkettete Liste der ausgehenden Kanten.
	 */
	public LinkedList<Arc> outArcs;
	
	/**
	 * Verkettet Liste der eingehenden Kanten.
	 */
	public LinkedList<Arc> inArcs;
	
	/**
	 * Label, das angibt, ob der Knoten im Rahmen eines Scan-Algorithmus
	 * bereits besucht bzw. betrachtet wurde. 
	 */
	public boolean visited;
	
	/**
	 * Label, das angibt, ob der Knoten im Rahmen eines Scan-Algorithmus
	 * bereits abgearbeitet wurde. 
	 */
	public boolean finished;
	

	
	/**
	 * Konstruktor.
	 * @param id ID des Knoten
	 */
	public Node(int id){
		this.Id = id;
		this.outArcs = new LinkedList<Arc>();
		this.inArcs = new LinkedList<Arc>();
		this.visited = false;
		this.finished = false;
	}
	
	/**
	 * Liefert den Grad eines Knotens zurück. Dafür werden die eingehenden und
	 * ausgehenden Kanten der beiden Adjazenzlisten aufsummiert.
	 * @return Knotengrad
	 */
	public int getDegree(){
		int result = 0;
		if (outArcs != null){
			result = outArcs.size(); 
		}
		if (inArcs != null){
			result += inArcs.size(); 
		}
		return result;
	};
	
	/**
	 * Fügt dem Knoten eine neue eingehende Kante hinzu.
	 * @param inArc Referenz auf die neue eingehende Kante
	 */
	public void addIncomingArc(Arc inArc) {
		this.inArcs.add(inArc);
	}
	
	/**
	 * Fügt dem Knoten eine neue ausgehende Kante hinzu.
	 * @param outArc Referenz auf die neue ausgehende Kante
	 */
	public void addOutgoingArc(Arc outArc) {
		this.outArcs.add(outArc);
	}
	
	/**
	 * Gibt das "visited"-Label zurück.
	 * @return TRUE, falls der Knoten schon besucht wurde
	 */
	public boolean isVisited() {
		return this.visited;
	}
	
	/**
	 * Setzt das "visited"-Label des Knotens.
	 * @param state Boolsche Variable, die angibt, ob der Knoten besucht wurde.
	 */
	public void visited(boolean state) {
		this.visited = state;
	}
	
	/**
	 * Setzt das "finished"-Label des Knotens.
	 * @param state Bool'sche Variable, die angibt, obe der Knoten abgearbeitet wurde.
	 */
	public void finished(boolean state) {
		this.finished = state;
	}
	
	/**
	 * Setzt alle Knotenmarkierungen auf false.
	 */
	public void resetLabels() {
		this.finished = false;
		this.visited = false;
	}
	
	/**
	 * ToString-Methode. Gibt das Knoten-Objekt als Zeichenkette zurück.
	 */
	public String toString() {
		return "Node " + this.Id + " [Degree: " + this.getDegree() + ", visited: " + this.visited + ", finished: " + this.finished + "]";
	}
}
