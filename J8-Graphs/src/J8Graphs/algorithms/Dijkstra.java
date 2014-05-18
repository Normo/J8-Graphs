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

	/**
	 * Standard Dijkstra-Algorithmus mit vorzeitigen Abbruch, wenn Zielknoten
	 * permanent gelabelt wurde.
	 * @param graph DiGraph, in dem der kürzeste Pfad gesucht werden soll
	 * @param startNode Startknoten
	 * @param targetNode Zielknoten
	 */
	public static void standardDijkstra(DiGraph graph, Node startNode, Node targetNode) {
		
		BinaryHeap queue = new BinaryHeap(false); 	// Prioritätswarteschlange in Form eines binären Heaps

		graph.resetLabels();		// setze alle Labels und Flags der Knoten auf default-Werte zurück
		startNode.distance = 0;		// Distanz zu sich selbst ist 0
		startNode.pred = null;		// Startknoten hat keinen Vorgänger
		
		queue.insert(startNode);	
		
		Node currentNode;			// der in der Iteration aktuell betrachtete Knoten
		Node neighbor;				// möglicher Nachbarknoten von currentNode
		int deleteMinCounter = 0;	// Zähler für deleteMin-Operationen
		
		while (!queue.isEmpty()) {
			currentNode = queue.deleteMin();		// hole den nächsten ereichbaren Knoten
			deleteMinCounter++;
			
//			System.out.println(deleteMinCounter+ ". " + currentNode);
			
			// ist unser Zielknoten gefunden, dann breche Algorithmus ab (early termination)
			if(currentNode.Id == targetNode.Id) {
				break;
			}
			
			// iteriere über die erreichbaren Nachbarknoten
			for (Edge outEdge : currentNode.outEdges) {
				
				neighbor = outEdge.targetNode;
				
				if (neighbor.distance > currentNode.distance + outEdge.length) {
					if (neighbor.distance == Integer.MAX_VALUE) {
						neighbor.distance = currentNode.distance + outEdge.length;
						neighbor.pred = currentNode;
						queue.insert(neighbor);
					} else {
						neighbor.distance = currentNode.distance + outEdge.length;
						neighbor.pred = currentNode;
						queue.decreaseKey(neighbor, neighbor.distance);
					}
				}
			}
//			System.out.println(queue);
		}
		
//		Node node = targetNode;
//		
//		System.out.print(" Pfad: " + node.Id);
//		
//		while (node != startNode) {
//			node= node.pred;
//			System.out.print(", " + node.Id);
//			if (node == null) break;
//		}
//		System.out.println();
		if (targetNode.distance < Integer.MAX_VALUE) {
			System.out.println("Kürzester s-t-Weg: " + targetNode.distance + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		} else {
			System.out.println("Es gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		}
		
		
	}

	/**
	 * Bidirektionaler Dijkstra-Algorithmus.
	 * @param graph
	 * @param startNode
	 * @param targetNod
	 */
	public static void bidirectionalDijkstra(DiGraph graph, Node startNode, Node targetNode) {
		
		BinaryHeap sQueue = new BinaryHeap(false);	// Prioritätswarteschlange des Startknoten
		BinaryHeap tQueue = new BinaryHeap(true);	// Prioritätswarteschlange des Zielknoten
		
		graph.resetLabels();		// setze alle Labels und Flags der Knoten auf default-Werte zurück
		
		startNode.distance = 0;		// Distanz von s zu sich selbst ist 0
		startNode.pred = null;		// Startknoten hat keinen Vorgänger
		
		targetNode.distanceBackward = 0; // Distanz von t zu sich selbst ist 0
		targetNode.pred = null;
		
		sQueue.insert(startNode);
		tQueue.insert(targetNode);

		Node currentNode = null;			// der in der Iteration aktuell betrachtete Knoten
		Node neighbor;				// möglicher Nachbarknoten von currentNode
		int deleteMinCounter = 0;	// Zähler für deleteMin-Operationen
		boolean isForwardSearch;
		
		while (!sQueue.isEmpty() || !tQueue.isEmpty()) {
			
			if (!sQueue.isEmpty() && sQueue.findMin().distance <= tQueue.findMin().distanceBackward) {
				currentNode = sQueue.deleteMin();
//				System.out.println("Node " + currentNode + " removed from Q_s with distance " + currentNode.distance);
				deleteMinCounter++;
				
				if (currentNode.isFinished) {
//					System.out.println("Node " + currentNode.Id + " wurde aus beiden Queues entfernt!");
					break;
				} else {
					currentNode.finished(true);
				}
				
				// iteriere über die erreichbaren Nachbarknoten
				for (Edge outEdge : currentNode.outEdges) {

					neighbor = outEdge.targetNode;

					if (neighbor.distance > currentNode.distance + outEdge.length) {
						if (neighbor.distance == Integer.MAX_VALUE) {
							neighbor.distance = currentNode.distance + outEdge.length;
							neighbor.pred = currentNode;
							sQueue.insert(neighbor);
						} else {
							neighbor.distance = currentNode.distance + outEdge.length;
							neighbor.pred = currentNode;
							sQueue.decreaseKey(neighbor, neighbor.distance);
						}
					}
				}
			} else {
				currentNode = tQueue.deleteMin();
				deleteMinCounter++;
//				System.out.println("Node " + currentNode + " removed from Q_ts with distance " + currentNode.distanceBackward);
				
				if (currentNode.isFinished) {
//					System.out.println("Node " + currentNode.Id + " wurde aus beiden Queues entfernt!");
					break;
				} else {
					currentNode.finished(true);
				}
				
				// iteriere über die erreichbaren Nachbarknoten
				for (Edge reverseEdge : currentNode.inEdges) {

					neighbor = reverseEdge.startNode;

					if (neighbor.distanceBackward > currentNode.distanceBackward + reverseEdge.length) {
						if (neighbor.distanceBackward == Integer.MAX_VALUE) {
							neighbor.distanceBackward = currentNode.distanceBackward + reverseEdge.length;
							neighbor.pred = currentNode;
							tQueue.insert(neighbor);
						} else {
							neighbor.distanceBackward = currentNode.distanceBackward + reverseEdge.length;
							neighbor.pred = currentNode;
							tQueue.decreaseKey(neighbor, neighbor.distanceBackward);
						}
					}
				}
			}
		}
		
		int min;
		
		if (currentNode.distance < Integer.MAX_VALUE && currentNode.distance < Integer.MAX_VALUE) {
			min = currentNode.distance + currentNode.distanceBackward;
			if (!sQueue.isEmpty()) {
				for (int i = 0; i < sQueue.size; i++) {
					if ((sQueue.heap[i].distanceBackward < Integer.MAX_VALUE)
							&& (sQueue.heap[i].distance
									+ sQueue.heap[i].distanceBackward < min)) {
						min = sQueue.heap[i].distance
								+ sQueue.heap[i].distanceBackward;
					}
				}
			}
			System.out.println("Kürzester s-t-Weg: " + (min) + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		} else {
			System.out.println("Es gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		}
		
	}

}
