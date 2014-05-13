package J8Graphs.util;

import java.util.Random;
import J8Graphs.model.Arc;
import J8Graphs.model.DiGraph;
import J8Graphs.model.Node;

/**
 * Klasse zum Erzeugen zufälliger gerichteter Graphen.
 * @author normo
 *
 */
public class RandomDiGraph {

	/**
	 * Anzahl der Knoten
	 */
	public int nodeAmount;

	/**
	 * Anzahl der Kanten
	 */
	public int edgeAmount;

	/**
	 * Standard-Konstruktor
	 * @param nodeAmount Knotenanzahl
	 * @param edgeAmount Kantenanzahl
	 */
	public RandomDiGraph(int nodeAmount, int edgeAmount) {
		this.nodeAmount = nodeAmount;
		this.edgeAmount = edgeAmount;
	}

	/**
	 * Erzeugt einen zufälligen gerichteten Graphen. Es kann nicht ausgeschlossen
	 * werden, dass dieser zufällige Graph Zyklen enthält.
	 * @return zufälliger DiGraph
	 */
	public DiGraph getRandomDiGraph() {

		DiGraph randomGraph = new DiGraph(this.nodeAmount, edgeAmount);

		Node newNode,targetNode;
		Arc newArc;
		int randomNodeId;

		for (int i = 0; i < this.nodeAmount; i++) {
			if ((newNode = randomGraph.getNodeWithID(i)) == null) {
				newNode = new Node(i);
				randomGraph.add(newNode);
			}

			randomNodeId = this.getRandomNodeId();

			if ((targetNode = randomGraph.getNodeWithID(randomNodeId)) == null) {
				targetNode = new Node(randomNodeId);
				randomGraph.add(targetNode);
			};

			newArc = new Arc(newNode, targetNode);
			newNode.addOutgoingArc(newArc);
			targetNode.addIncomingArc(newArc);

		}

		int remainingArcs = this.edgeAmount - this.nodeAmount;

		for (int i = 0; i < remainingArcs; i++) {

			newNode = randomGraph.getNodeWithID(this.getRandomNodeId());
			targetNode = randomGraph.getNodeWithID(this.getRandomNodeId());
			newArc = new Arc(newNode, targetNode);
			newNode.addOutgoingArc(newArc);
			targetNode.addIncomingArc(newArc);
		}

		return randomGraph;
	}

	/**
	 * Erzeugt einen gerichteten, kreisfreien Graphen.
	 * @return zufällig erzeugter, kreisfreier DiGraph
	 */
	public DiGraph getAcyclicRandomDiGraph() {

		DiGraph randomGraph = new DiGraph(this.nodeAmount, edgeAmount);

		Node startNode,targetNode;
		Arc newArc;
		int randomId1, randomId2;
		
		for (int i = 0; i < this.nodeAmount-1; i++) {
			if ((startNode = randomGraph.getNodeWithID(i)) == null) {
				startNode = new Node(i);
				randomGraph.add(startNode);
			}

			while ((randomId1 = this.getRandomNodeId()) <= i);

			if ((targetNode = randomGraph.getNodeWithID(randomId1)) == null) {
				targetNode = new Node(randomId1);
				randomGraph.add(targetNode);
			};

			newArc = new Arc(startNode, targetNode);
			startNode.addOutgoingArc(newArc);
			targetNode.addIncomingArc(newArc);

		}
		
		int remainingArcs = this.edgeAmount - (this.nodeAmount-1);
		startNode = null;
		targetNode = null;
		
		for (int i = 0; i < remainingArcs; i++) {
			
			randomId1 = this.getRandomNodeId();
			randomId2 = this.getRandomNodeId();
			
			while (randomId1 == randomId2) {
				randomId1 = this.getRandomNodeId();
				randomId2 = this.getRandomNodeId();
			}
			
			if (randomId1 > randomId2) {
				startNode = randomGraph.getNodeWithID(randomId2);
				targetNode = randomGraph.getNodeWithID(randomId1);
			} else {
				startNode = randomGraph.getNodeWithID(randomId1);
				targetNode = randomGraph.getNodeWithID(randomId2);
			} 
			
			newArc = new Arc(startNode, targetNode);
			startNode.addOutgoingArc(newArc);
			targetNode.addIncomingArc(newArc);
		}

		return randomGraph;
	}

	/**
	 * Gibt eine zufällige Knoten-ID zurück
	 * @return Knoten-ID
	 */
	public int getRandomNodeId() {
		Random r = new Random();
		return r.nextInt((this.nodeAmount - 0));
	}

	/**
	 * Gibt eine zufällige Integer-Zahl zurück
	 * @param min Minimum (inklusive)
	 * @param max Maximum (inklusive)
	 * @return zufälliger Integer zwischen min und max
	 */
	public int getRandomInt(int min, int max) {
		Random r = new Random();
		return r.nextInt((max -min) + 1) + min;
	}

}
