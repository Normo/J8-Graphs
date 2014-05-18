package J8Graphs.algorithms;

import J8Graphs.model.DiGraph;
import J8Graphs.model.Edge;
import J8Graphs.model.Node;
import J8Graphs.util.BinaryHeap;

/**
 * Stellt verschiedene Implementierungen des Dijkstra Algorithmus bereit, um
 * Kürzeste-Wege-Probleme zu lösen.
 * @author normo
 *
 */
public class Dijkstra {
	
	DiGraph graph;
	
	public Dijkstra(DiGraph d) {
		this.graph = d;
		
		if(d.cycleExists()) {
			System.out.println("Graph enthält Zyklus!");
		} else {
			System.out.println("Graph ist zyklusfrei!");
		}
	}

	public void standardDijkstra(Node startNode, Node targetNode) {
		
		BinaryHeap  heap = new BinaryHeap();
		
		this.graph.resetLabels();
		startNode.distance = 0;
		startNode.pred = null;
		
		heap.insert(startNode);
		
		Node currentNode;
		Node neighbor;
		int deleteMinCounter = 0;
		
		while (!heap.isEmpty()) {
			currentNode = heap.deleteMin();
			deleteMinCounter++;
			
			for (Edge outEdge : currentNode.outEdges) {
				
				neighbor = outEdge.targetNode;
				
				if (neighbor.distance > currentNode.distance + outEdge.length) {
					if (neighbor.distance == Integer.MAX_VALUE) {
						neighbor.distance = currentNode.distance + outEdge.length;
						neighbor.pred = currentNode;
						heap.insert(neighbor);
					} else {
						neighbor.distance = currentNode.distance + outEdge.length;
						neighbor.pred = currentNode;
						heap.decreaseKey(neighbor, neighbor.distance);
					}
				}
			}
		}
		
		System.out.println("Kürzester s-t-Weg: " + targetNode.distance + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		
	}

}
