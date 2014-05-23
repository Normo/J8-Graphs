package J8Graphs.util;

import java.util.Random;
import J8Graphs.model.Edge;
import J8Graphs.model.DiGraph;
import J8Graphs.model.Node;

/**
 * Klasse zum Erzeugen zufälliger gerichteter Graphen.
 * @author normo
 *
 */
public class RandomDiGraph {

	/**
	 * Erzeugt einen zufälligen gerichteten Graphen. Es kann nicht ausgeschlossen
	 * werden, dass dieser zufällige Graph Zyklen enthält.
	 * @return zufälliger DiGraph
	 */
	@Deprecated
	public static DiGraph getRandomDiGraph(int nodeAmount, int edgeAmount) {

		DiGraph randomGraph = new DiGraph(nodeAmount, edgeAmount);

		Node newNode,targetNode;
		Edge newEdge;
		int randomNodeId;

		for (int i = 0; i < nodeAmount; i++) {
			if ((newNode = randomGraph.getNodeWithID(i)) == null) {
				newNode = new Node(i);
				randomGraph.add(newNode);
			}

			randomNodeId = RandomDiGraph.getRandomNodeId(nodeAmount);

			if ((targetNode = randomGraph.getNodeWithID(randomNodeId)) == null) {
				targetNode = new Node(randomNodeId);
				randomGraph.add(targetNode);
			};

			newEdge = new Edge(newNode, targetNode);
			newNode.addOutgoingEdge(newEdge);
			targetNode.addIncomingEdge(newEdge);

		}

		int remainingEdges = edgeAmount - nodeAmount;

		for (int i = 0; i < remainingEdges; i++) {

			newNode = randomGraph.getNodeWithID(RandomDiGraph.getRandomNodeId(nodeAmount));
			targetNode = randomGraph.getNodeWithID(RandomDiGraph.getRandomNodeId(nodeAmount));
			newEdge = new Edge(newNode, targetNode);
			newNode.addOutgoingEdge(newEdge);
			targetNode.addIncomingEdge(newEdge);
		}

		return randomGraph;
	}

	/**
	 * Erzeugt einen gerichteten, kreisfreien Graphen.
	 * @return zufällig erzeugter, kreisfreier DiGraph
	 */
	@Deprecated
	public static DiGraph getAcyclicRandomDiGraph(int nodeAmount, int edgeAmount) {

		DiGraph randomGraph = new DiGraph(nodeAmount, edgeAmount);

		Node startNode,targetNode;
		Edge newEdge;
		int randomId1, randomId2;
		
		for (int i = 0; i < nodeAmount-1; i++) {
			if ((startNode = randomGraph.getNodeWithID(i)) == null) {
				startNode = new Node(i);
				randomGraph.add(startNode);
			}

			while ((randomId1 = RandomDiGraph.getRandomNodeId(nodeAmount)) <= i);

			if ((targetNode = randomGraph.getNodeWithID(randomId1)) == null) {
				targetNode = new Node(randomId1);
				randomGraph.add(targetNode);
			};

			newEdge = new Edge(startNode, targetNode);
			startNode.addOutgoingEdge(newEdge);
			targetNode.addIncomingEdge(newEdge);

		}
		
		int remainingEdges = edgeAmount - (nodeAmount-1);
		startNode = null;
		targetNode = null;
		
		for (int i = 0; i < remainingEdges; i++) {
			
			randomId1 = RandomDiGraph.getRandomNodeId(nodeAmount);
			randomId2 = RandomDiGraph.getRandomNodeId(nodeAmount);
			
			while (randomId1 == randomId2) {
				randomId1 = RandomDiGraph.getRandomNodeId(nodeAmount);
				randomId2 = RandomDiGraph.getRandomNodeId(nodeAmount);
			}
			
			if (randomId1 > randomId2) {
				startNode = randomGraph.getNodeWithID(randomId2);
				targetNode = randomGraph.getNodeWithID(randomId1);
			} else {
				startNode = randomGraph.getNodeWithID(randomId1);
				targetNode = randomGraph.getNodeWithID(randomId2);
			} 
			
			newEdge = new Edge(startNode, targetNode);
			startNode.addOutgoingEdge(newEdge);
			targetNode.addIncomingEdge(newEdge);
		}

		return randomGraph;
	}
	
	/**
	 * Erzeugt einen zufälligen, gerichteten Graphen mit koordinatenlosen Knoten.
	 * Da die Knoten in diesem Graph keine Koordinaten besitzen, eignet sich 
	 * dieser nicht für das Auffinden kürzester Wege.
	 * @param nodeAmount Anzahl der Knoten 
	 * @param edgeAmount Anzahl der Kanten
	 * @param acyclic Angabe, ob der Graph zyklusfrei sein soll oder nicht
	 * @return zufälliger DiGraph
	 */
	public static DiGraph getUncoordinatedRandomDiGraph(int nodeAmount, int edgeAmount, boolean acyclic) {
		DiGraph randomGraph = new DiGraph(nodeAmount, edgeAmount);

		randomGraph = createUncoordinatedNodes(randomGraph);

		//Erzeuge die Kanten
		if (acyclic) {
			randomGraph = createAcyclicEdges(randomGraph);
		} else {
			randomGraph = createEdges(randomGraph);
		}
		return randomGraph;
	}

	/**
	 * Erzeugt einen zufälligen, gerichteten Graphen mit ebenfalls zufälligen
	 * Knotenkooridnaten. Damit ist der resultierende DiGraph für das 
	 * Auffinden von kürzesten Wegen geeignet.
	 * @param nodeAmount Anzahl der Knoten
	 * @param edgeAmount Anzahl der Kanten
	 * @param acyclic Angabe, ob der Graph zyklusfrei sein soll oder nicht
	 * @return
	 */
	public static DiGraph getCoordinatedRandomDiGraph(int nodeAmount, int edgeAmount, boolean acyclic) {
		DiGraph randomGraph = new DiGraph(nodeAmount, edgeAmount);

		randomGraph = createCoordinatedNodes(randomGraph);
		
		//Erzeuge die Kanten
		if (acyclic) {
			randomGraph = createAcyclicEdges(randomGraph);
		} else {
			randomGraph = createEdges(randomGraph);
		}
		return randomGraph;
	}
	
	/**
	 * Erzeugt Knoten mit zufälligen, ganzzahligen Koordinaten.
	 * @param randomGraph DiGraph, dem die Knoten hinzugefügt werden sollen
	 * @return Refernz auf den DiGraph
	 */
	private static DiGraph createCoordinatedNodes(DiGraph randomGraph) {
		
		int nodeAmount = randomGraph.nodeAmount;	
		Node newNode;
		int x, y;
		
		// Erzeuge die Knoten
		for (int i = 0; i < nodeAmount; i++) {

			x = RandomDiGraph.getRandomInt(0, 10*nodeAmount);
			y = RandomDiGraph.getRandomInt(0, 10*nodeAmount);

			newNode = new Node(i, x, y);
			randomGraph.add(newNode);
		}
		
		return randomGraph;
	}
	
	/**
	 * Erzeugt Knoten ohne Koordinaten.
	 * @param randomGraph DiGraph, dem die Knoten hinzugefügt werden sollen
	 * @return Refernz auf den DiGraph
	 */
	private static DiGraph createUncoordinatedNodes(DiGraph randomGraph) {

		int nodeAmount = randomGraph.nodeAmount;	
		Node newNode;

		// Erzeuge die Knoten
		for (int i = 0; i < nodeAmount; i++) {
			newNode = new Node(i);
			randomGraph.add(newNode);
		}

		return randomGraph;
	}
	
	/**
	 * Erzeugt zufällige Kanten zwischen den Knoten des DiGraphen.
	 * @param randomGraph DiGraph, dem die Kanten hinzugefügt werden sollen
	 * @return Refernz auf den DiGraph
	 */
	private static DiGraph createEdges(DiGraph randomGraph) {
		Node startNode,targetNode;
		Edge newEdge;
		
		int nodeAmount = randomGraph.nodeAmount;
		int edgeAmount = randomGraph.edgeAmount;
		
		for (int i = 0; i < edgeAmount; i++) {			
			startNode = randomGraph.getNodeWithID(RandomDiGraph.getRandomNodeId(nodeAmount));
			targetNode = randomGraph.getNodeWithID(RandomDiGraph.getRandomNodeId(nodeAmount));
			newEdge = new Edge(startNode, targetNode);
			startNode.addOutgoingEdge(newEdge);
			targetNode.addIncomingEdge(newEdge);
		}
		return randomGraph;
	}
	
	/**
	 * Erzeugt zufällige Kanten zwischen den Knoten des DiGraphen ohne dass
	 * Zyklen entstehen.
	 * @param randomGraph DiGraph, dem die Kanten hinzugefügt werden sollen
	 * @return Refernz auf den DiGraph
	 */
	private static DiGraph createAcyclicEdges(DiGraph randomGraph) {
		
		Node startNode,targetNode;
		Edge newEdge;
		
		int nodeAmount = randomGraph.nodeAmount;
		int edgeAmount = randomGraph.edgeAmount;
		int randomId1, randomId2, remainingEdges;
		
		//für jeden Knoten außer den letzten eine ausgehende Kante
		for (int i = 0; i < nodeAmount-1; i++) {	
			startNode = randomGraph.get(i);
			targetNode = randomGraph.getNodeWithID(RandomDiGraph.getRandomInt(i+1, nodeAmount-1));
			newEdge = new Edge(startNode, targetNode);
			startNode.addOutgoingEdge(newEdge);
			targetNode.addIncomingEdge(newEdge);
		}
		
		remainingEdges = edgeAmount - (nodeAmount-1);
		
		//restliche Kanten erzeugen
		for (int i = 0; i < remainingEdges; i++) {		
			
			randomId1 = RandomDiGraph.getRandomNodeId(nodeAmount);
			randomId2 = RandomDiGraph.getRandomNodeId(nodeAmount);
			
			while (randomId1 == randomId2) {
				randomId1 = RandomDiGraph.getRandomNodeId(nodeAmount);
				randomId2 = RandomDiGraph.getRandomNodeId(nodeAmount);
			}
			
			if (randomId1 > randomId2) {
				startNode = randomGraph.getNodeWithID(randomId2);
				targetNode = randomGraph.getNodeWithID(randomId1);
			} else {
				startNode = randomGraph.getNodeWithID(randomId1);
				targetNode = randomGraph.getNodeWithID(randomId2);
			}
			
			newEdge = new Edge(startNode, targetNode);
			startNode.addOutgoingEdge(newEdge);
			targetNode.addIncomingEdge(newEdge);
		}	
		return randomGraph;
	}
	
	/**
	 * Gibt eine zufällige Knoten-ID zurück
	 * @return Knoten-ID
	 */
	public static int getRandomNodeId(int nodeAmount) {
		Random r = new Random();
		return r.nextInt((nodeAmount - 0));
	}

	/**
	 * Gibt eine zufällige Integer-Zahl zurück.
	 * @param min Minimum (inklusive)
	 * @param max Maximum (inklusive)
	 * @return zufälliger Integer zwischen min und max
	 */
	public static int getRandomInt(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	/**
	 * Gibt eine zufällige Zahl zwischen Integer.MIN_VALUE und Integer.MAX_VALUE
	 * zurück.
	 * @return zufälliger Integer-Wert
	 */
	public static int getRandomInt() {
		Random r = new Random();
		return r.nextInt();
	}

}
