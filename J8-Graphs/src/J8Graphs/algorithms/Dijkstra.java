package J8Graphs.algorithms;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import J8Graphs.model.DiGraph;
import J8Graphs.model.Edge;
import J8Graphs.model.Node;
import J8Graphs.util.BinaryHeap;

/////////////////////////////////////////////////////
//it was hard to write so it should be hard to read//
/////////////////////////////////////////////////////

/**
 * Stellt verschiedene Implementierungen des Dijkstra Algorithmus bereit, um
 * Kürzeste-Wege-Probleme zu lösen.
 * @author normo
 *
 */
public class Dijkstra {

	/**
	 * Führt entweder den Standard-Dijkstra- oder den zielgerichteten Dijkstra-Algorithmus aus.
	 * @param graph Graph
	 * @param startNode Startknoten
	 * @param targetNode Zielknoten
	 * @param isGoalDirected Angabe, ob zielgerichtet oder nicht
	 */
	public static void standardDijkstra(DiGraph graph, Node startNode, Node targetNode, boolean isGoalDirected) {

		graph.resetLabels(); // setze alle Labels und Flags der Knoten auf default-Werte zurück

		if (isGoalDirected) {
			// zielgerichtet
			computeLowerBounds(graph, targetNode);		// berechne untere Schranken
			computeModifiedEdgeLength(graph);			// berechne modifizierte Kantenlängen

			targetNode.lowerBound = 0;

			goalDirectedStandardDijkstra(graph, startNode, targetNode);
		} else {
			//standard-dijkstra
			standardDijkstra(graph, startNode, targetNode);
		}
		
		// DEBUG: Ausgabe des Rückwärtspfades
		//printReversePath(targetNode);
	}

	/**
	 * Standard Dijkstra-Algorithmus mit vorzeitigen Abbruch, wenn Zielknoten
	 * permanent gelabelt wurde.
	 * @param graph DiGraph, in dem der kürzeste Pfad gesucht werden soll
	 * @param startNode Startknoten
	 * @param targetNode Zielknoten
	 */
	private static void standardDijkstra(DiGraph graph, Node startNode, Node targetNode) {

		BinaryHeap queue = new BinaryHeap(false); 	// Prioritätswarteschlange in Form eines binären Heaps

		startNode.distance = 0;		// Distanz zu sich selbst ist 0
		startNode.pred = null;		// Startknoten hat keinen Vorgänger

		queue.insert(startNode);	

		Node currentNode;			// der in der Iteration aktuell betrachtete Knoten
		Node neighbor;				// möglicher Nachbarknoten von currentNode
		int deleteMinCounter = 0;	// Zähler für deleteMin-Operationen

		long start = new Date().getTime();

		while (!queue.isEmpty()) {
			currentNode = queue.deleteMin();		// hole den nächsten ereichbaren Knoten
			deleteMinCounter++;

			//System.out.println(deleteMinCounter+ ". " + currentNode);

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
		}
		long runningTime = new Date().getTime() - start;

		if (targetNode.distance < Integer.MAX_VALUE) {
			System.out.println("\nKürzester s-t-Weg: " + targetNode.distance + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		} else {
			System.out.println("\nEs gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		}

		System.out.println("Laufzeit Dijkstra: " + runningTime + " ms\n");
	}

	/** 
	 * Standard Dijkstra-Algorithmus mit zielgerichteter Suche und vorzeitigen
	 * Abbruch, wenn Zielknoten permanent gelabelt wurde.
	 * @param graph DiGraph, in dem der kürzeste Pfad gesucht werden soll
	 * @param startNode
	 * @param targetNode
	 */
	private static void goalDirectedStandardDijkstra(DiGraph graph, Node startNode, Node targetNode) {

		BinaryHeap queue = new BinaryHeap(false); 	// Prioritätswarteschlange in Form eines binären Heaps

		startNode.distance = 0;			// modifizierte Distanz zu sich selbst ist 0
		startNode.pred = null;		// Startknoten hat keinen Vorgänger

		queue.insert(startNode);	

		Node currentNode;			// der in der Iteration aktuell betrachtete Knoten
		Node neighbor;				// möglicher Nachbarknoten von currentNode
		int deleteMinCounter = 0;	// Zähler für deleteMin-Operationen

		long start = new Date().getTime();

		while (!queue.isEmpty()) {
			currentNode = queue.deleteMin();		// hole den nächsten ereichbaren Knoten
			deleteMinCounter++;

			//System.out.println(deleteMinCounter+ ". " + currentNode);

			// ist unser Zielknoten gefunden, dann breche Algorithmus ab (early termination)
			if(currentNode.Id == targetNode.Id) {
				break;
			}

			// iteriere über die erreichbaren Nachbarknoten
			for (Edge outEdge : currentNode.outEdges) {

				neighbor = outEdge.targetNode;

				if (neighbor.distance > currentNode.distance + outEdge.modifiedLength) {
					if (neighbor.distance == Integer.MAX_VALUE) {
						neighbor.distance = currentNode.distance + outEdge.modifiedLength;
						neighbor.pred = currentNode;
						queue.insert(neighbor);
					} else {
						neighbor.distance = currentNode.distance + outEdge.modifiedLength;
						neighbor.pred = currentNode;
						queue.decreaseKey(neighbor, neighbor.distance);
					}
				}
			}
		}
		long runningTime = new Date().getTime() - start;

		if (targetNode.distance < Integer.MAX_VALUE) {
			System.out.println("\nKürzester s-t-Weg: " + (targetNode.distance + startNode.lowerBound) + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		} else {
			System.out.println("\nEs gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		}

		System.out.println("Laufzeit Dijkstra zielgerichtet: " + runningTime + " ms\n");
	}

	/**
	 * Bidirektionaler Dijkstra-Algorithmus.
	 * @param graph DiGraph, in dem der kürzeste Pfad gesucht werden soll
	 * @param startNode Startknoten
	 * @param targetNod Zielknoten
	 */
	public static void bidirectionalDijkstra(DiGraph graph, Node startNode, Node targetNode) {

		graph.resetLabels(); // setze alle Labels und Flags der Knoten auf default-Werte zurück

		BinaryHeap sQueue = new BinaryHeap(false);	// Prioritätswarteschlange des Startknoten
		BinaryHeap tQueue = new BinaryHeap(true);	// Prioritätswarteschlange des Zielknoten

		startNode.distance = 0;		// Distanz von s zu sich selbst ist 0
		startNode.pred = null;		// Startknoten hat keinen Vorgänger

		targetNode.distanceBackward = 0; // Distanz von t zu sich selbst ist 0
		targetNode.pred = null;

		sQueue.insert(startNode);
		tQueue.insert(targetNode);

		Node currentNode = null;	// der in der Iteration aktuell betrachtete Knoten
		Node neighbor;				// möglicher Nachbarknoten von currentNode
		int deleteMinCounter = 0;	// Zähler für deleteMin-Operationen

		long start = new Date().getTime();

		while (!sQueue.isEmpty() || !tQueue.isEmpty()) {

			if (!sQueue.isEmpty() && !tQueue.isEmpty() && sQueue.findMin().distance <= tQueue.findMin().distanceBackward) {
				// Vorwärtssuche
				
				currentNode = sQueue.deleteMin();
				deleteMinCounter++;

				//System.out.println("CurrentNode: " +currentNode + " distF= " + currentNode.distance);
				
				if (currentNode.isFinished) {
					//Knoten wurde aus beiden queues entfernt!

					//System.out.println("!!!!CurrentNode: " +currentNode + " distF= " + currentNode.distance + " distB= " +currentNode.distanceBackward);
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
			} else if (!tQueue.isEmpty()) {
				// Rückwärtssuche
				
				currentNode = tQueue.deleteMin();
				deleteMinCounter++;

				//System.out.println("CurrentNode: " +currentNode + " distB= " + currentNode.distanceBackward);
				
				if (currentNode.isFinished) {
					//Knoten wurde aus beiden queues entfernt!
					//System.out.println("!!!!CurrentNode: " +currentNode + " distF= " + currentNode.distance + " distB= " +currentNode.distanceBackward);
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
			} else {
				break;
			}
		}

		int min;
		
		// suche alternative minimalen Distanz
		if (currentNode.distance < Integer.MAX_VALUE && currentNode.distanceBackward < Integer.MAX_VALUE) {
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
			System.out.println("\nKürzester s-t-Weg: " + (min) + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		} else {
			System.out.println("\nEs gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		}
		long runningTime = new Date().getTime() - start;
		System.out.println("Laufzeit Dijkstra bidirektional: " + runningTime + " ms\n\n");
	}

	
	
	/**
	 * Bidirektional Dials Implementation.
	 * @param graph Graph
	 * @param startNode Startknoten
	 * @param targetNode Zielknoten
	 */
	public static void bidirectionalDial(DiGraph graph, Node startNode, Node targetNode) {
		
		//This Code can only be loved by a mother.
		
		graph.resetLabels();

		ArrayList<LinkedList<Node>> forwardBucketList = new ArrayList<>();
		ArrayList<LinkedList<Node>> backwardBucketList = new ArrayList<>();
		int upperBound_C = graph.getMaxEdgeLength();

		for (int i = 0; i < upperBound_C+1; i++) {
			forwardBucketList.add(new LinkedList<>());
			backwardBucketList.add(new LinkedList<>());
		}

		// initialisiere Startknoten für Vorwärtssuche
		startNode.distance = 0;
		startNode.pred = null;
		startNode.bucketPointer = 0;

		// initialisiere Zielknoten für Rückwärtssuche
		targetNode.distanceBackward = 0;
		targetNode.pred = null;
		targetNode.bucketPointer = 0;

		forwardBucketList.get(0).add(startNode); 	// Startknoten liegt im Bucket 0
		backwardBucketList.get(0).add(targetNode); 	// Zielknoten liegt im Bucket 0

		Node currentNode = startNode;				// der in der Iteration aktuell betrachtete Knoten
		Node neighbor;								// möglicher Nachbarknoten von currentNode
		int deleteMinCounter = 0;					// Zähler für deleteMin-Operationen	

		int lastForwardBucketPointer = 0;
		int lastBackwardBucketPointer = 0;
		boolean nodeFound;
		int min;

		long start = new Date().getTime();

		while (true) {

			if (lastForwardBucketPointer == -1 && lastBackwardBucketPointer == -1) break;

			nodeFound = false;
			if (lastForwardBucketPointer != -1) {
				/* Suche ab der letzten gemerkten Position in der Bucketliste, und
				 * hole den ersten Knoten aus dem ersten nicht-leeren Bucket */
				for (int i = lastForwardBucketPointer; i < forwardBucketList
						.size(); i++) {
					if (!forwardBucketList.get(i).isEmpty()) {
						nodeFound = true;
						lastForwardBucketPointer = i;
						break;
					}
				}
				/* Wurde im ersten Durchlauf bis zum Listenende nur leere Buckets 
				 * gefunden, so suche nun vom ersten Bucket an bis zur letzten
				 * gemerkten Position */
				if (!nodeFound) {
					for (int i = 0; i < lastForwardBucketPointer; i++) {
						if (!forwardBucketList.get(i).isEmpty()) {
							nodeFound = true;
							lastForwardBucketPointer = i;
							break;
						}
					}
				}
			}
			// falls alle ForwardBuckets leer sind
			if (!nodeFound) { 
				lastForwardBucketPointer = -1;
			}
			nodeFound = false;

			if (lastBackwardBucketPointer != -1) {
				/* Suche ab der letzten gemerkten Position in der Bucketliste, und
				 * hole den ersten Knoten aus dem ersten nicht-leeren Bucket */
				for (int i = lastBackwardBucketPointer; i < backwardBucketList
						.size(); i++) {
					if (!backwardBucketList.get(i).isEmpty()) {
						nodeFound = true;
						lastBackwardBucketPointer = i;
						break;
					}
				}
				/* Wurde im ersten Durchlauf bis zum Listenende nur leere Buckets 
				 * gefunden, so suche nun vom ersten Bucket an bis zur letzten
				 * gemerkten Position */
				if (!nodeFound) {
					for (int i = 0; i < lastBackwardBucketPointer; i++) {
						if (!backwardBucketList.get(i).isEmpty()) {
							nodeFound = true;
							lastBackwardBucketPointer = i;
							break;
						}
					}
				}
			}
			// falls alle Buckets leer sind, breche hier ab
			if (!nodeFound) { 
				if (lastForwardBucketPointer == -1) {
					break;
				} else {
					lastBackwardBucketPointer = -1;
				}
			}

			if (((lastForwardBucketPointer != -1 && lastBackwardBucketPointer != -1) && 
					forwardBucketList.get(lastForwardBucketPointer).peek().distance <= backwardBucketList.get(lastBackwardBucketPointer).peek().distanceBackward)
					|| (lastForwardBucketPointer != -1 && lastBackwardBucketPointer == -1)) {

				currentNode = forwardBucketList.get(lastForwardBucketPointer).poll();
				deleteMinCounter++;

				if (currentNode.isFinished) {
					// Knoten wurde aus beiden bucket-Listen entfernt
					
					//System.out.println("!!!!CurrentNode: " +currentNode + " distF= " + currentNode.distance + " distB= " +currentNode.distanceBackward);
					break;
				} else {
					currentNode.finished(true);
				}

				//System.out.println("CurrentNode: " +currentNode + " distF= " + currentNode.distance);

				// iteriere über die erreichbaren Nachbarknoten
				for (Edge outEdge : currentNode.outEdges) {

					neighbor = outEdge.targetNode;
					//System.out.print("\t aktuelle Distanz: "+ neighbor.distance +" > " + currentNode.distance +"+"+ outEdge.length);

					if (neighbor.distance > currentNode.distance + outEdge.length) {
						if (neighbor.distance == Integer.MAX_VALUE) {

							//setze erstmalig das Distanzlabel
							neighbor.distance = currentNode.distance + outEdge.length;

							// Weise Knoten einem Bucket zu
							neighbor.bucketPointer = neighbor.distance % (upperBound_C + 1);
							forwardBucketList.get(neighbor.bucketPointer).add(neighbor);
						} else {

							//Aktualisiere Distanzlabel
							neighbor.distance = currentNode.distance + outEdge.length;

							//Verschiebe den Knoten in einen kleineren Bucket
							forwardBucketList.get(neighbor.bucketPointer).remove(neighbor);
							neighbor.bucketPointer = neighbor.distance % (upperBound_C + 1);
							forwardBucketList.get(neighbor.bucketPointer).add(neighbor);
						}
					}
				}
			} else if (lastBackwardBucketPointer != -1) {

				currentNode = backwardBucketList.get(lastBackwardBucketPointer).poll();
				deleteMinCounter++;

				if (currentNode.isFinished) {
					// Knoten wurde aus beiden bucket-Listen entfernt
					
					//System.out.println("!!!!CurrentNode: " +currentNode + " distF= " + currentNode.distance + " distB= " +currentNode.distanceBackward);
					break;
				} else {
					currentNode.finished(true);
				}

				//System.out.println("CurrentNode: " +currentNode + " distB= " + currentNode.distanceBackward);

				// iteriere über die erreichbaren Nachbarknoten
				for (Edge reverseEdge : currentNode.inEdges) {

					neighbor = reverseEdge.startNode;
					//System.out.print("\t aktuelle Distanz: "+ neighbor.distance +" > " + currentNode.distance +"+"+ outEdge.length);

					if (neighbor.distanceBackward > currentNode.distanceBackward + reverseEdge.length) {
						if (neighbor.distanceBackward == Integer.MAX_VALUE) {

							//setze erstmalig das Distanzlabel
							neighbor.distanceBackward = currentNode.distanceBackward + reverseEdge.length;

							// Weise Knoten einem Bucket zu
							neighbor.bucketPointer = neighbor.distanceBackward % (upperBound_C + 1);
							backwardBucketList.get(neighbor.bucketPointer).add(neighbor);
						} else {

							//Aktualisiere Distanzlabel
							neighbor.distanceBackward = currentNode.distanceBackward + reverseEdge.length;

							//Verschiebe den Knoten in einen kleineren Bucket
							backwardBucketList.get(neighbor.bucketPointer).remove(neighbor);
							neighbor.bucketPointer = neighbor.distanceBackward % (upperBound_C + 1);
							backwardBucketList.get(neighbor.bucketPointer).add(neighbor);
						}
					}
					//System.out.println("Neighbor: "+ neighbor + " dist= " + neighbor.distance + " BucketPointer: " + neighbor.bucketPointer + " l=" + outEdge.length );
				}
			} // Ende Iteration über Nachbarknoten
		} // Ende Iteration über Buckets
		
		long runningTime = new Date().getTime() - start;
		
		if (currentNode.distance < Integer.MAX_VALUE && currentNode.distance < Integer.MAX_VALUE) {
			min = currentNode.distance + currentNode.distanceBackward;

			if (lastForwardBucketPointer != -1) {
				for (LinkedList<Node> bucket : forwardBucketList) {
					for (Node node : bucket) {
						if ((node.distanceBackward < Integer.MAX_VALUE) &&
								(node.distance + node.distanceBackward < min)) {
							min = node.distance + node.distanceBackward;
						}
					}
				}
			}
			if (min > 0) {
				System.out.println("\nKürzester s-t-Weg: " + (min) + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
			} else {
				System.out.println("\nEs gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
			}
		} else {
			System.out.println("\nEs gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		}
		
		System.out.println("Laufzeit Dial's Implementation bidirektional: " + runningTime + " ms\n\n");
	}

	/**
	 * Dials Implementation - ruft interne Dials-Methoden auf, je nach dem ob
	 * zielgerichtet erwünscht ist oder nicht.
	 * @param graph Graph
	 * @param startNode Startknoten
	 * @param targetNode Zielknoten
	 * @param isGoalDirected Angabe, ob zielgerichtet oder nicht
	 */
	public static void dialsImplementation(DiGraph graph, Node startNode, Node targetNode, boolean isGoalDirected) {

		graph.resetLabels(); // setze alle Labels und Flags der Knoten auf default-Werte zurück

		if (isGoalDirected) {
			// zielgerichtete Variante
			goalDirectedDialsImplementation(graph, startNode, targetNode);
		} else {
			// Standardvariante
			dialsImplementation(graph, startNode, targetNode);
		}
	}

	/**
	 * Zielgerichtete Dials Implementation.
	 * @param graph Graph
	 * @param startNode Startknoten
	 * @param targetNode Zielknoten
	 */
	private static void goalDirectedDialsImplementation(DiGraph graph, Node startNode, Node targetNode) {

		int max = computeLowerBounds(graph, targetNode);
		computeModifiedEdgeLength(graph);

		targetNode.lowerBound = 0;

		int upperBound_C = graph.getMaxEdgeLength();
		max += upperBound_C + 1;
		max *= 25;
		ArrayList<LinkedList<Node>> bucketList = new ArrayList<>(max);

		// initialisiere Bucketliste
		for (int i = 0; i < max; i++) {
			bucketList.add(new LinkedList<>());
		}

		// initialisiere Startknoten
		startNode.distance = 0;
		startNode.pred = null;
		startNode.bucketPointer = 0;
		bucketList.get(0).add(startNode);	// Startknoten liegt im Bucket 0

		Node currentNode = startNode;		// der in der Iteration aktuell betrachtete Knoten
		Node neighbor;						// möglicher Nachbarknoten von currentNode
		int deleteMinCounter = 0;			// Zähler für deleteMin-Operationen	

		int lastBucketPointer = 0;			// hält den zuletzt verwendeten Bucket-Index
		boolean nodeFound = false;			// gibt an, ob ein Knoten in der Bucketliste gefunden wurde

		long start = new Date().getTime();

		while (true) {
			/* Suche ab der letzten gemerkten Position in der Bucketliste, und
			 * hole den ersten Knoten aus dem ersten nicht-leeren Bucket */ 
			for (int i = lastBucketPointer; i < bucketList.size(); i++) {
				if(!bucketList.get(i).isEmpty()) {
					currentNode = bucketList.get(i).poll();
					deleteMinCounter++;
					nodeFound = true;
					lastBucketPointer = i;
					break;
				}
			} 

			// falls alle Buckets leer sind, breche hier ab
			if (!nodeFound) { break; }

			//System.out.println("CurrentNode: " +currentNode + " dist= " + currentNode.distance);

			// vorzeitiger Abbruch, falls Zielknoten permanent markiert ist
			if(currentNode.Id == targetNode.Id) {
				break;
			}

			// iteriere über die erreichbaren Nachbarknoten
			for (Edge outEdge : currentNode.outEdges) {

				neighbor = outEdge.targetNode;
				//System.out.print("\t aktuelle Distanz: "+ neighbor.distance +" > " + currentNode.distance +"+"+ outEdge.length);

				if (neighbor.distance > currentNode.distance + outEdge.modifiedLength) {
					if (neighbor.distance == Integer.MAX_VALUE) {

						//setze erstmalig das Distanzlabel und Vorgänger
						neighbor.distance = currentNode.distance + outEdge.modifiedLength;
						neighbor.pred = currentNode;

						// Weise Knoten einem Bucket zu
						neighbor.bucketPointer = neighbor.distance;
						bucketList.get(neighbor.bucketPointer).add(neighbor);
					} else {

						//Aktualisiere Distanzlabel und Vorgänger
						neighbor.distance = currentNode.distance + outEdge.modifiedLength;
						neighbor.pred = currentNode;

						//Verschiebe den Knoten in einen kleineren Bucket
						bucketList.get(neighbor.bucketPointer).remove(neighbor);
						neighbor.bucketPointer = neighbor.distance;
						bucketList.get(neighbor.bucketPointer).add(neighbor);
					}
				}
				//System.out.println("Neighbor: "+ neighbor + " dist= " + neighbor.distance + " BucketPointer: " + neighbor.bucketPointer + " l=" + outEdge.length + " l'=" + outEdge.modifiedLength);
			} // Ende Iteration über Nachbarknoten
			nodeFound = false;
		} // Ende Iteration über Buckets

		long runningTime = new Date().getTime() - start;

		if (targetNode.distance < Integer.MAX_VALUE) {
			System.out.println("Kürzester s-t-Weg: " + (targetNode.distance + startNode.lowerBound) + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		} else {
			System.out.println("Es gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		}

		System.out.println("Laufzeit Dial's Implementation zielgerichtet: " + runningTime + " ms\n\n");
	}

	/**
	 * Dials Implementation.
	 * @param graph 
	 * @param startNode
	 * @param targetNode
	 */
	private static void dialsImplementation(DiGraph graph, Node startNode, Node targetNode) {

		ArrayList<LinkedList<Node>> bucketList = new ArrayList<>();
		int upperBound_C = graph.getMaxEdgeLength();

		for (int i = 0; i < upperBound_C+1; i++) {
			bucketList.add(new LinkedList<>());
		}

		startNode.distance = 0;
		startNode.pred = null;
		startNode.bucketPointer = 0;
		bucketList.get(0).add(startNode);	// Startknoten liegt im Bucket 0

		Node currentNode = startNode;			// der in der Iteration aktuell betrachtete Knoten
		Node neighbor;						// möglicher Nachbarknoten von currentNode
		int deleteMinCounter = 0;			// Zähler für deleteMin-Operationen	

		int lastBucketPointer = 0;
		boolean nodeFound;

		long start = new Date().getTime();

		while (true) {

			nodeFound = false;

			/* Suche ab der letzten gemerkten Position in der Bucketliste, und
			 * hole den ersten Knoten aus dem ersten nicht-leeren Bucket */ 
			for (int i = lastBucketPointer; i < bucketList.size(); i++) {
				if(!bucketList.get(i).isEmpty()) {
					currentNode = bucketList.get(i).poll();
					deleteMinCounter++;
					nodeFound = true;
					lastBucketPointer = i;
					break;
				}
			} 
			/* Wurde im ersten Durchlauf bis zum Listenende nur leere Buckets 
			 * gefunden, so suche nun vom ersten Bucket an bis zur letzten
			 * gemerkten Position */
			if (!nodeFound) {
				for (int i = 0; i < lastBucketPointer; i++) {
					if(!bucketList.get(i).isEmpty()) {
						currentNode = bucketList.get(i).poll();
						deleteMinCounter++;
						nodeFound = true;
						lastBucketPointer = i;
						break;
					}
				} 
			}

			// falls alle Buckets leer sind, breche hier ab
			if (!nodeFound) { break; }

			//System.out.println("CurrentNode: " +currentNode + " dist= " + currentNode.distance);

			// vorzeitiger Abbruch, falls Zielknoten permanent markiert ist
			if(currentNode.Id == targetNode.Id) {
				break;
			}

			// iteriere über die erreichbaren Nachbarknoten
			for (Edge outEdge : currentNode.outEdges) {

				neighbor = outEdge.targetNode;
				//System.out.print("\t aktuelle Distanz: "+ neighbor.distance +" > " + currentNode.distance +"+"+ outEdge.length);

				if (neighbor.distance > currentNode.distance + outEdge.length) {
					if (neighbor.distance == Integer.MAX_VALUE) {

						//setze erstmalig das Distanzlabel und Vorgänger
						neighbor.distance = currentNode.distance + outEdge.length;
						neighbor.pred = currentNode;

						// Weise Knoten einem Bucket zu
						neighbor.bucketPointer = neighbor.distance % (upperBound_C + 1);
						bucketList.get(neighbor.bucketPointer).add(neighbor);
					} else {

						//Aktualisiere Distanzlabel und Vorgänger
						neighbor.distance = currentNode.distance + outEdge.length;
						neighbor.pred = currentNode;

						//Verschiebe den Knoten in einen kleineren Bucket
						bucketList.get(neighbor.bucketPointer).remove(neighbor);
						neighbor.bucketPointer = neighbor.distance % (upperBound_C + 1);
						bucketList.get(neighbor.bucketPointer).add(neighbor);
					}
				}
				//				System.out.println("Neighbor: "+ neighbor + " dist= " + neighbor.distance + " BucketPointer: " + neighbor.bucketPointer + " l=" + outEdge.length );
			} // Ende Iteration über Nachbarknoten
		} // Ende Iteration über Buckets
		long runningTime = new Date().getTime() - start;

		if (targetNode.distance < Integer.MAX_VALUE) {
			System.out.println("Kürzester s-t-Weg: " + targetNode.distance + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		} else {
			System.out.println("Es gibt keinen kürzesten s-t-Weg!" + "\nAnzahl der deleteMin-Operationen: " + deleteMinCounter);
		}
		System.out.println("Laufzeit Dial's Implementation: " + runningTime + " ms\n\n");
		//printReversePath(targetNode);
	}

	/**
	 * Berechnet für jeden Knoten die unteren Schranken für die zielgerichtete
	 * Suche.
	 * @param graph Graph
	 * @param targetNode Zielknoten
	 * @return maximale Schranke
	 */
	public static int computeLowerBounds(DiGraph graph, Node targetNode) {

		int lb; 
		int max = -1;

		for (Node node : graph) {

			lb = Math.abs(node.xCoord - targetNode.xCoord)
					+ Math.abs(node.yCoord - targetNode.yCoord);

			node.lowerBound = lb;
			max = (lb > max) ? lb : max;
			//System.out.println("Node "+ node + " lb= " + node.lowerBound);
		}
		return max;
	}

	/**
	 * Berechnet für alle Kanten die modifizierte Kantenlänge für die 
	 * zielgerichtete Suche.
	 * @param graph Graph
	 */
	public static void computeModifiedEdgeLength(DiGraph graph) {
		for (Node node : graph) {
			for (Edge edge : node.outEdges) {
				if (!((edge.length + edge.targetNode.lowerBound) >= edge.startNode.lowerBound)) {
					System.out.println("Konsistenz verletzt!! Node " + edge.startNode + " + Node " + edge.targetNode);
					System.out.println("b(v,t)=" + edge.startNode.lowerBound + " b(w.t)=" + edge.targetNode.lowerBound + " l(v,w)=" + edge.length);
				} else {
					edge.modifiedLength = edge.length - edge.startNode.lowerBound + edge.targetNode.lowerBound;

					//System.out.println(edge.toString() + " l(v,w)="  + edge.length + " l'(v,w)=" + edge.modifiedLength);
				}
			}
		}
	}

	/**
	 * Gibt rekursiv die Vorgängerknoten und die Kantenlängen auf Konsole aus.
	 * Bricht ab, wenn Vorgängerknoten null ist. Das Ergebnis ist ein 
	 * Rückwärtspfad. Nützlich zum Debuggen.
	 * @param node Startknoten des Rückwärtspfades
	 */
	@SuppressWarnings("unused")
	private static void printReversePath(Node node) {
		Node currentNode = node;
		//if (currentNode.pred == null) return;
		System.out.print(" Pfad: " + currentNode.Id);
//		for (Edge  edge : currentNode.inEdges) {
//			if (edge.startNode == currentNode.pred) {
//				System.out.print(" [" + edge.length + "] ");
//			}
//		}

		while ((currentNode=currentNode.pred) != null) {
			System.out.print(", " + currentNode.Id);
//			for (Edge  edge : currentNode.inEdges) {
//				if (edge.startNode == currentNode.pred) {
//					System.out.print(" [" + edge.length + "] ");
//				}
//			}
		}
		System.out.println();
	}
}
