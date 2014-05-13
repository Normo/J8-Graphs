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
	 * X-Koordinate des Knotens
	 */
	public int x;
	
	/**
	 * Y-Koordinate des Knotens
	 */
	public int y;
	
	/**
	 * Verkettete Liste der ausgehenden Kanten.
	 */
	public LinkedList<Edge> outEdges;
	
	/**
	 * Verkettet Liste der eingehenden Kanten.
	 */
	public LinkedList<Edge> inEdges;
	
	/**
	 * Label, das angibt, ob der Knoten im Rahmen eines Scan-Algorithmus
	 * bereits besucht bzw. betrachtet wurde. 
	 */
	public boolean isVisited;
	
	/**
	 * Label, das angibt, ob der Knoten im Rahmen eines Scan-Algorithmus
	 * bereits abgearbeitet wurde. 
	 */
	public boolean isFinished;
	
	/**
	 * Zeitstempel, der angibt, wann der Knoten das erste mal besucht wurde.
	 */
	public int discoverTime;
	
	/**
	 * Zeitstempel, der angibt, wann der Knoten vom DFS-Stack heruntergenommen wurde.
	 */
	public int finishTime;
	
	/**
	 * Konstruktor.
	 * @param id ID des Knoten
	 */
	public Node(int id){
		this.Id = id;
		this.outEdges = new LinkedList<Edge>();
		this.inEdges = new LinkedList<Edge>();
		this.isVisited = false;
		this.isFinished = false;
	}
	
	/**
	 * Liefert den Grad eines Knotens zurück. Dafür werden die eingehenden und
	 * ausgehenden Kanten der beiden Adjazenzlisten aufsummiert.
	 * @return Knotengrad
	 */
	public int getDegree(){
		int result = 0;
		if (outEdges != null){
			result = outEdges.size(); 
		}
		if (inEdges != null){
			result += inEdges.size(); 
		}
		return result;
	};
	
	/**
	 * Fügt dem Knoten eine neue eingehende Kante hinzu.
	 * @param inEdge Referenz auf die neue eingehende Kante
	 */
	public void addIncomingEdge(Edge inEdge) {
		this.inEdges.add(inEdge);
	}
	
	/**
	 * Fügt dem Knoten eine neue ausgehende Kante hinzu.
	 * @param outEdge Referenz auf die neue ausgehende Kante
	 */
	public void addOutgoingEdge(Edge outEdge) {
		this.outEdges.add(outEdge);
	}
	
	/**
	 * Gibt das "visited"-Label zurück.
	 * @return TRUE, falls der Knoten schon besucht wurde
	 */
	public boolean isVisited() {
		return this.isVisited;
	}
	
	/**
	 * Setzt das "visited"-Label des Knotens.
	 * @param state Boolsche Variable, die angibt, ob der Knoten besucht wurde.
	 */
	public void visited(boolean state) {
		this.isVisited = state;
	}
	
	/**
	 * Setzt das "finished"-Label des Knotens.
	 * @param state Bool'sche Variable, die angibt, obe der Knoten abgearbeitet wurde.
	 */
	public void finished(boolean state) {
		this.isFinished = state;
	}
	
	/**
	 * Setzt den Discover-Zeistempel auf die angegebene Zeit.
	 * @param time Zeitstempel
	 */
	public void discovered(int time) {
		this.discoverTime = time;
	}
	
	/**
	 * Setzt den finished-Zeitstempel auf die angegebene Zeit.
	 * @param time Zeitstempel
	 */
	public void finish(int time) {
		this.finishTime = time;
	}
	
	/**
	 * Setzt alle Knotenmarkierungen auf false.
	 */
	public void resetLabels() {
		this.isFinished = false;
		this.isVisited = false;
		this.discoverTime = -1;
		this.finishTime = -1;
	}
	
	/**
	 * ToString-Methode. Gibt das Knoten-Objekt als Zeichenkette zurück.
	 */
	public String toString() {
		//return "Node " + this.Id + " [Degree: " + this.getDegree() + ", visited: " + this.visited + ", finished: " + this.finished + "]";
		return "" + this.Id;
	}
}
