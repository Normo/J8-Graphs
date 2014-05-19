package J8Graphs.model;

import java.util.ArrayList;
import java.util.Date;
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
public class DiGraph extends ArrayList<Node> {

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
	public int edgeAmount;
	
	/**
	 * Label, das angibt, ob der DiGraph einen Kreis enthält.
	 */
	public boolean cycleFound = false;
	
	/**
	 * Liste, in der ein Pfad abgespeichert wird, der einen Kreis darstellt.
	 */
	public LinkedList<Node> cyclePath;
	
	/**
	 * Liste, in der eine topologische Sortierung gespeichert wird.
	 */
	public LinkedList<Node> topoSort;
	
	/**
	 * Leerer Konstruktor. Erzeugt einen leeren Graphen.
	 */
	public DiGraph() {}
	
	/**
	 * Standard-Konstruktor. Erzeugt einen leeren Graphen und setzt initial die Anzahl für Knoten und Kanten.
	 * @param nodes Anzahl der Knoten
	 * @param edges Anzahl der gerichteten Kanten
	 */
	public DiGraph(int nodes, int edges) {
		this.nodeAmount = nodes;
		this.edgeAmount = edges;
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
		//XXX
		//this.addFirst(node);
		this.add(0, node);
		this.nodeAmount = this.size();
	}
	
	/**
	 * UNIMPLEMENTIERT
	 * @param node Knoten, über dessen eingehende Kanten iteriert werden soll.
	 */
	public void iterateOverInEdges(Node node) {
		
	}

	/**
	 * Graph Scan Algorithmus mit Tiefensuche-Strategie und Zeitstempeln. Gibt nur
	 * die vom Startknoten aus erreichbaren Knoten als Baum zurück.
	 * @param startNode Startknoten, bei dem der Scanning Algorithmus beginnen soll
	 * @return alle von startNode aus erreichbaren Knoten als Tiefensuche-Baum
	 */
	public Tree graphScanDFS(Node startNode) {
		
		this.resetLabels();					// setze alle Knotenmarkierungen zurück
		
		Tree dfsTree = new Tree(startNode);	// DFS-Baum
		Stack<Node> stack = new Stack<>();	// Menge der betrachteten Knoten
		int time = 0;
		
		stack.push(startNode);				// Füge Startknoten zum Stack hinzu,
		startNode.visited(true);			// markiere ihn als besucht
		startNode.discovered(time);			// und vergebe Zeitstempel 0.
		
		Node currentNode;					// aktuell betrachteter Knoten
		boolean noUnvisitedNode;			// gibt an, ob der aktuelle Knoten noch unbesuchte Nachfolger hat
		
		while (!stack.isEmpty()) {
			currentNode = stack.peek();		// hole den obersten Knoten vom Stack, ohne ihn zu entfernen
			noUnvisitedNode = true;			// gehe standardmäßig davon aus, dass alle Nachfolgerknoten besucht wurden
			
			// Iteration über die ausgehenden Kanten des aktuell betrachteten Knoten
			for (Edge outgoingEdges : currentNode.outEdges) {
				
				// ist der Nachfolgeknoten noch unbesucht, füge ihn zum Stack + DFS-Baum hinzu und markiere ihn als besucht
				if (!outgoingEdges.targetNode.isVisited()){
					outgoingEdges.targetNode.visited(true);
					time++;
					outgoingEdges.targetNode.discovered(time);
					stack.push(outgoingEdges.targetNode);
					dfsTree.insertNode(outgoingEdges.targetNode);
					dfsTree.addEdge(outgoingEdges);
					noUnvisitedNode = false;
					break;
				}
			}
			
			// wurde alle Nachfolgeknoten betrachtet, nehme den aktuell betrachteten Knoten vom Stack herunter
			if (noUnvisitedNode) {
				time++;
				currentNode.finish(time);
				stack.pop();
			}	
		}
		
		return dfsTree;
	}

	/**
	 * Interne DFS-Methode für die Tiefensuche im kompletten DiGraph.
	 * @param startNode neuer Startknoten, von dem die DFS ausgehen soll
	 * @param time aktueller Zeitstempel
	 * @return alle von startNode aus erreichbaren Knoten als Tiefensuche-Baum
	 */
	private Tree dfs(Node startNode, int time) {
				
		Tree dfsTree = new Tree(startNode);	// DFS-Baum
		Stack<Node> stack = new Stack<>();	// Menge der betrachteten Knoten
		int t = time;
		
		stack.push(startNode);				// Füge Startknoten zum Stack hinzu,
		startNode.visited(true);			// markiere ihn als besucht
		startNode.discovered(t);			// und vergebe Zeitstempel 0.
		
		Node currentNode;					// aktuell betrachteter Knoten
		boolean noUnvisitedNode;			// gibt an, ob der aktuelle Knoten noch unbesuchte Nachfolger hat
		
		while (!stack.isEmpty()) {
			currentNode = stack.peek();		// hole den obersten Knoten vom Stack, ohne ihn zu entfernen
			noUnvisitedNode = true;			// gehe standardmäßig davon aus, dass alle Nachfolgerknoten besucht wurden
			
			// Iteration über die ausgehenden Kanten des aktuell betrachteten Knoten
			for (Edge outgoingEdge : currentNode.outEdges) {
				
				// ist der Nachfolgeknoten noch unbesucht, füge ihn zum Stack + DFS-Baum hinzu und markiere ihn als besucht
				if (!outgoingEdge.targetNode.isVisited()){
					outgoingEdge.targetNode.visited(true);
					t++;
					outgoingEdge.targetNode.discovered(t);
					stack.push(outgoingEdge.targetNode);
					dfsTree.insertNode(outgoingEdge.targetNode);
					dfsTree.addEdge(outgoingEdge);
					noUnvisitedNode = false;
					break;
				}
			}
			
			// wurden alle Nachfolgeknoten betrachtet, nehme den aktuell betrachteten Knoten vom Stack herunter
			if (noUnvisitedNode) {
				t++;									// inkrementiere Zeitstempel
				currentNode.finish(t);					// setze finish-Time
				this.topoSort.addFirst(currentNode);	// füge abgearbeiteten Knoten zur topoSort-Liste hinzu
				stack.pop();							// entferne den Knoten vom Stack
			}	
		}
				
		return dfsTree;
	}
	
	/**
	 * Tiefensuche mit Zeitstempeln im kompletten DiGraph. Gibt einen Tiefensuche-Wald
	 * zurück in Form einer ArrayList.
	 * @param startNode Startknoten, bei dem die Tiefensuche beginnt
	 * @return Tiefensuche-Wald
	 */
	public ArrayList<Tree> depthFirstSearch(Node startNode) {
		
		this.resetLabels();
		
		this.topoSort = new LinkedList<>();
		
		ArrayList<Tree> forest = new ArrayList<>();
		Tree dfsTree;
		int time = 0;
		
		dfsTree = this.dfs(startNode, time);
		time = dfsTree.root.finishTime;
		forest.add(dfsTree);
		
		for (Node node : this) {
			if(node.finishTime == -1) {
				dfsTree = this.dfs(node, (time+1));
				time = dfsTree.root.finishTime;
				forest.add(dfsTree);
			}
		}
				
		return forest;
	}
	
	/**
	 * Graph Scan Algorithmus mit Breitensuche-Strategie.
	 * @param startNode Startknoten, bei dem die Breitensuche beginnt
	 * @return Tree - alle von startNode aus erreichbaren Knoten als Breitensuche-Baum
	 */
	public Tree breadthFirstSearch(Node startNode) {
		
		this.resetLabels();						// setze alle Knotenmarkierungen zurück
		
		Tree bfsTree = new Tree(startNode);		// BFS-Baum
		Queue<Node> queue = new LinkedList<>();	// Warteschlange
		
		queue.add(startNode);
		startNode.visited(true);
		
		Node currentNode;
		boolean noUnvisitedNodes;
		
		while(!queue.isEmpty()) {
			currentNode = queue.element();
			noUnvisitedNodes = false;
			
			for (Edge outgoingEdge : currentNode.outEdges) {
				if (!outgoingEdge.targetNode.isVisited) {
					outgoingEdge.targetNode.visited(true);
					queue.add(outgoingEdge.targetNode);
					bfsTree.insertFirstNode(outgoingEdge.targetNode);
					bfsTree.addEdge(outgoingEdge);
					noUnvisitedNodes = true;
//					break;
				}
			}
			
			if (!noUnvisitedNodes) {
				queue.poll();
			}
		}
				
		return bfsTree;
		
	}
	
	/**
	 * Topologische Sortierung.
	 * @return Liste der topologischen Sortierung
	 */
	public boolean topologicalSorting() {
				
		this.resetLabels();
		this.cyclePath = new LinkedList<>();
		
		this.cycleExists(); //prüfe, ob es einen Zyklus gibt

		if(this.cycleFound) {
			// Wenn ein Zyklus gefunden wurde,
			// lösche die Knoten in cyclePath, die nicht zum zyklus gehören
			
			int cylceNodeId = this.cyclePath.get(this.cyclePath.size()-1).Id;	// ID des Knotens, bei dem der Zyklus entdeckt wurde
			int pathLength = this.cyclePath.size();
			
			for (int i = 0; i < pathLength; i++) {
				// lösche alle Knoten in cyclePath bis zum ersten Auftreten des Knotens mit der ID cycleNodeId
				if(this.cyclePath.getFirst().Id != cylceNodeId) {
					this.cyclePath.removeFirst();
					continue;
				} else {
					// Zyklusanfang gefunden, cyclePath enthält nur Knoten, die zum Zyklus gehören
					break;
				}
			}
			//return this.cyclePath;
			return false;
		} else {
			// wenn kein Zyklus existiert, führe Tiefensuche mit Timestamps durch
			// immer wenn ein Knoten eine Finish-Zeit erhält wird er vorne an die topoSort-Liste angefügt
			long start = new Date().getTime();
			//XXX
//			this.depthFirstSearch(this.getFirst());
			this.depthFirstSearch(this.get(0));
			long runningTime = new Date().getTime() - start;
			System.out.println("Laufzeit: " + runningTime + " ms");
			//return this.topoSort;
			return true;
		}
	}
	
	/**
	 * Prüft, ob ein Kreis im DiGraph vorhanden ist und setzt die globale Variable
	 * cycleFound auf TRUE, wenn ein Zyklus existiert.
	 * @return TRUE, wenn ein Kreis existiert
	 */
	public boolean cycleExists() {
		
		this.resetLabels();
		
		this.cyclePath = new LinkedList<>();
		
		//XXX
//		this.cycleDFS(this.getFirst());
		this.cycleDFS(this.get(0));
		
		if (this.cycleFound) {
			return true;
		} else {
			for (Node node : this) {
				if(node.isVisited == false) {
					this.cycleDFS(node);
					if (this.cycleFound) {
						System.out.println("Pfadlänge: " + this.cyclePath.size() +"\n");
						return true;
					}
				}
			}
		}
		return this.cycleFound;
	}
	
	/**
	 * Interne tiefensuche-ähnliche Methode, die rekursive aufgerufen wird, um
	 * Zyklen im DiGraph zu finden. 
	 * @param node Knoten, der besucht werden soll
	 */
	private void cycleDFS(Node node) {
		if (this.cycleFound) {
			return;
		} else {
			if (node.isFinished) {
				return;
			}
			if (node.isVisited) {
//				System.out.println("Zyklus gefunden bei Knoten " + node.Id  );
				this.cycleFound = true;
//				System.out.println("###############!!!!!!!!!!!!!!!!!!!!!!####################################");
				this.cyclePath.add(node);
//				this.cyclePath.forEach(n -> System.out.print(n.Id + " -> "));
//				System.out.println("Pfadlänge: " + this.cyclePath.size() +"\n");
				return;
			}
			node.visited(true);
			this.cyclePath.add(node);
			for (Edge edge : node.outEdges) {
				this.cycleDFS(edge.targetNode);
			}
			node.finished(true);
			if (!this.cycleFound) {
				this.cyclePath.remove(this.cyclePath.size()-1);
			}
		}
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
	public void resetLabels() {
		for (Node node : this) {
			node.resetLabels();
		}
	}
	
	public LinkedList<Node> getCyclePath() {
		if(this.cycleFound) {
			// Wenn ein Zyklus gefunden wurde,
			// lösche die Knoten in cyclePath, die nicht zum zyklus gehören
			
			int cylceNodeId = this.cyclePath.get(this.cyclePath.size()-1).Id;	// ID des Knotens, bei dem der Zyklus entdeckt wurde
			int pathLength = this.cyclePath.size();
			
			for (int i = 0; i < pathLength; i++) {
				// lösche alle Knoten in cyclePath bis zum ersten Auftreten des Knotens mit der ID cycleNodeId
				if(this.cyclePath.getFirst().Id != cylceNodeId) {
					this.cyclePath.removeFirst();
					continue;
				} else {
					// Zyklusanfang gefunden, cyclePath enthält nur Knoten, die zum Zyklus gehören
					break;
				}
			}
			return this.cyclePath;
		}
		return null;
	}
	
	public int getLongestEdgeLength() {
		int max = -1;
		
		for (Node node : this) {
			max = (node.lengthOfLongestEdge > max)? node.lengthOfLongestEdge : max ;
		}
		
		return max;
	}
	
	/**
	 * toString-Methode
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("n " + this.size() + " m " + this.edgeAmount);
		
		this.forEach((Node node) -> {
			sb.append("\n" + node.outputString());
		});
		
		this.forEach((Node node) -> {
			if(node.outEdges.size() > 0) {
				for (Edge edge : node.outEdges)
				sb.append("\n" + edge );
			}
		});
		
		return sb.toString();
	}
}
