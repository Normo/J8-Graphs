package J8Graphs.model.tree;

import java.util.LinkedList;

import J8Graphs.model.Arc;
import J8Graphs.model.DiGraph;
import J8Graphs.model.Node;

/**
 * Klasse für gerichtete Bäume.
 * @author normo
 *
 */
public class Tree extends DiGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Referenz auf den Wurzelknoten des Baumes.
	 */
	public Node root;
	
	/**
	 * Verkettete Liste mit den Kanten des Baumes.
	 */
	public LinkedList<Arc> arcs;
	
	/**
	 * Standard-Konstruktor
	 */
	public Tree() {
		super();
		arcs = new LinkedList<>();
	}
	
	/**
	 * Wurzelknoten-Konstruktor.
	 * @param rootNode Knoten, der als Wurzelknoten gesetzt werden soll
	 */
	public Tree(Node rootNode) {
		super();
		this.root = rootNode;
		this.insertFirstNode(this.root);
		arcs = new LinkedList<>();
	}

	/**
	 * Setzt den angegeben Knoten als Wurzelknoten des Baumes.
	 * @param rootNode Knoten, der als Wurzelknoten markiert werden soll
	 */
	public void setRootNode(Node rootNode) {
		this.root = rootNode;
		this.insertFirstNode(this.root);
	}
	
	@Override
	/**
	 * @see J8Graphs.model.DiGraph#insertNode(J8Graphs.model.Node)
	 * Funktioniert wie die insertNode-Methode der DiGraph-Klasse mit dem
	 * Unterschied, dass der erste Knoten, der zum Baum hinzugefügt wird, als
	 * Wurzelknoten markiert bzw. gesetzt wird.
	 */
	public boolean insertNode(Node node) {
		System.out.println("Rufe überschriebene insertNode-Methode der Tree-Klasse auf..");
		if(this.isEmpty() && this.add(node)) {
			this.root = node;
			this.nodeAmount = this.size();
			return true;
		} else if(this.add(node)) {
			this.nodeAmount = this.size();
			return true;			
		} else {
			return false;
		}
	}
	
	/**
	 * Füge Kante zum Baum hinzu.
	 * @param e Neue Kante
	 * @return siehe java.util.Collection.add
	 */
	public boolean addArc(Arc e) {
		return this.arcs.add(e);
	}
	
	/**
	 * Gibt die Gesamtanzahl der Kanten des Baumes zurück.
	 * @return Anzahl der Elemente der Kanten-Liste
	 */
	public int getArcAmount() {
		return this.arcs.size();
	}
	
	/**
	 * toString-Methode - gibt den Baum als Zeichenkette aus (spezifiziert im
	 * Graph-Dateiformat).
	 */
	public String toString() {
		
		StringBuilder sb = new StringBuilder("n " + this.size() + " m " + this.arcs.size());
		
		for (Arc e : this.arcs) {
			sb.append("\n" + e.toString());
		}
		
		return sb.toString();
	}
}
