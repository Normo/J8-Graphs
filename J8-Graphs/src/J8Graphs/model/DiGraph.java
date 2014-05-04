package J8Graphs.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import J8Graphs.model.tree.Tree;

/**
 * Basisklasse für gerichtete Graphen.
 * @author Undisputed
 * @author normo
 *
 */
public class DiGraph extends LinkedList<Node> {

	/**
	 * Default serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Anzahl der Knoten im DiGraph.
	 */
	public int nodeAmount;
	/**
	 * Anzahl der Kanten im DiGraph.
	 */
	public int arcAmount;
	
	/**
	 * Leerer Konstruktor. Erzeugt einen leeren Graphen.
	 */
	public DiGraph() {}
	
	/**
	 * Standard-Konstruktor. Erzeugt einen leeren Graphen und setzt initial die Anzahl für Knoten und Kanten.
	 * @param nodes Anzahl der Knoten
	 * @param arcs Anzahl der gerichteten Kanten
	 */
	public DiGraph(int nodes, int arcs) {
		this.nodeAmount = nodes;
		this.arcAmount = arcs;
	}
	
	/**
	 * Liefert den Knoten mit der angegebenen Id oder null, wenn der Knoten im
	 * Digraph nicht existiert, zurück. 
	 * @param nodeId Nummer des gesuchten Knotens
	 * @return Referenz auf den Knoten oder null
	 */
	public Node getNodeWithID(int nodeId) {
		for (Node node : this) {
			if (node.Id == nodeId) return node;
		}
		return null;
	}
	
	/**
	 * Prüft, ob ein Knoten mit der angegebenen Id im Graph vorhanden ist.
	 * @param id Nummer des gesuchten Knotens
	 * @return TRUE, wenn der Knoten im DiGraph existiert, FALSE sonst
	 */
	public boolean nodeExists(int id)
	{
		boolean result = false;
		for(Node node : this){
			if (node.Id == id){
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Fügt einen Knoten an das Ende der Knotenliste hinzu.
	 * @param node Neuer Knoten, der hinzugefügt werdfen soll
	 * @return TRUE (gemäß LinkedList.add)
	 */
	public boolean insertNode(Node node) {
		if (this.add(node)) {
			this.nodeAmount = this.size();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Fügt einen Knoten an den Anfang der Knotenliste hinzu.
	 * @param node Neuer Knoten, der hinzugefügt werden soll
	 */
	public void insertFirstNode(Node node) {
		this.addFirst(node);
		this.nodeAmount = this.size();
	}
	
	/**
	 * UNIMPLEMENTIERT
	 * @param node Knoten, über dessen eingehende Kanten iteriert werden soll.
	 */
	public void iterateOverInArcs(Node node) {
		
	}
	
	//TODO einfügen/löschen von Kanten/Knoten
	//TODO Test, ob es eine Kante (v,w) existiert
	//TODO Iteration über die Nachbarschaft eines Knoten

	/**
	 * Tiefensuche.
	 * @param startNode Startknoten, bei dem die Teifensuche starten soll
	 * @return Tree - alle von startNode aus erreichbaren Knoten als Tiefensuche-Baum
	 */
	public Tree depthFirstSearch(Node startNode) {
		
		System.out.println("Starte DFS...");
		
		Tree dfsTree = new Tree(startNode);	// DFS-Baum
		Stack<Node> stack = new Stack<>();	// Menge der betrachteten Knoten

		stack.push(startNode);				// Füge Startknoten zum Stack hinzu
		startNode.visited(true);			// und markieren ihn als besucht
		
		Node currentNode;			// aktuell betrachteter Knoten
		boolean noUnvisitedNode;	// gibt an, ob der aktuelle Knoten noch unbesuchte Nachfolger hat
		
		while (!stack.isEmpty()) {
			currentNode = stack.peek();	// hole den obersten Knoten vom Stack, ohne ihn zu entfernen
			noUnvisitedNode = true;		// gehe standardmäßig davon aus, dass alle Nachfolgerknoten besucht wurden
			
			// Iteration über die ausgehenden Kanten des aktuell betrachteten Knoten
			for (Arc outgoingArc : currentNode.outArcs) {
				
				// ist Nachfolgeknoten noch unbesucht, füge ihn zum Stack + DFS-Baum hinzu und markiere ihn als besucht
				if (!outgoingArc.targetNode.isVisited()){
					outgoingArc.targetNode.visited(true);
					stack.push(outgoingArc.targetNode);
					dfsTree.insertNode(outgoingArc.targetNode);
					dfsTree.addArc(outgoingArc);
					noUnvisitedNode = false;
					break;
				}
			}
			
			// wurde alle Nachfolgeknoten betrachtet, nehme den aktuell betrachteten Knoten vom Stack herunter
			if (noUnvisitedNode) {
				stack.pop();
			}	
		}
		
		//setze alle Knotenmarkierungen zurück auf false
		this.resetLabels();
		
		System.out.println("Beende DFS...");
		
		return dfsTree;
	}

	/**
	 * Breitensuche.
	 * @param startNode Startknoten, bei dem die Breitensuche beginnt
	 * @return Tree - alle von startNode aus erreichbaren Knoten als Breitensuche-Baum
	 */
	public Tree breadthFirstSearch(Node startNode) {
		
		System.out.println("Start BFS..");
		
		Tree bfsTree = new Tree(startNode);		// BFS-Baum
		Queue<Node> queue = new LinkedList<>();	// Warteschlange
		
		queue.add(startNode);
		startNode.visited(true);
		
		Node currentNode;
		boolean noUnvisitedNodes;
		
		while(!queue.isEmpty()) {
			currentNode = queue.element();
			noUnvisitedNodes = false;
			
			for (Arc outgoingArc : currentNode.outArcs) {
				if (!outgoingArc.targetNode.visited) {
					outgoingArc.targetNode.visited(true);
					queue.add(outgoingArc.targetNode);
					bfsTree.insertFirstNode(outgoingArc.targetNode);
					bfsTree.addArc(outgoingArc);
					noUnvisitedNodes = true;
//					break;
				}
			}
			
			if (!noUnvisitedNodes) {
				queue.poll();
			}
		}
		
		this.resetLabels();
		
		System.out.println("End BFS..");
		
		return bfsTree;
		
	}
	
	/**
	 * Gibt die Anzahl der Knoten im DiGraph zurück.
	 * @return Anzahl der Elemente der Knotenliste
	 */
	public int getNodeAmount() {
		return this.size();
	}
	
	/**
	 * Setzt die Knotenmarkierungen aller Knoten im DiGraph auf false zurück.
	 * Markierungen, die während einer Operation (z.B. DFS) auf dem Graphen,
	 * gesetzt wurden, werden so wieder rückgängig gemacht. Diese Methode 
	 * sollte nach jedem Graph-Algorithmus ausgeführt werden, um den
	 * Ursprungszustand des Graphen wieder herzustellen.
	 */
	private void resetLabels() {
		for (Node node : this) {
			node.resetLabels();
		}
		
		
	}
	
}
