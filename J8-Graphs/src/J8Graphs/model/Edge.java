package J8Graphs.model;

/**
 * Basisklasse für Kanten in gerichteten Graphen.
 * @author Undisputed
 * @author normo
 *
 */
public class Edge {

	/**
	 * Kanten-Id, setzt sich zusammen aus den konkatenierten Knoten-Id's der
	 * beiden inzidenten Knoten. Wird nicht mehr verwendet, da mehr als 4 Byte
	 * für die Id benötigt werden.
	 */
	@Deprecated
	public int Id;
	
	/**
	 * Startknoten von dem die Kante ausgeht. 
	 */
	public Node startNode;
	
	/**
	 * Zielknoten in dem die Kante endet.
	 */
	public Node targetNode;
	
	/**
	 * Länge der Kante. Wird aus dem Manhattan-Abstand der beiden Endknoten 
	 * berechnet.
	 */
	public int length;

	/**
	 * Konstruktor.
	 * @param start Startknoten
	 * @param target Zielknoten
	 */
	public Edge(Node start, Node target){
		this.startNode = start;
		this.targetNode = target;
		
		//@Deprecated
		//this.Id = Integer.parseInt(this.startNode.Id + "" + this.targetNode.Id);
		
		//Berechne Manhattan-Distanz
		if (this.startNode.hasCoordinates && this.targetNode.hasCoordinates) {
			length = Math.abs(this.startNode.xCoord - this.targetNode.xCoord)
					+ Math.abs(this.startNode.yCoord - this.targetNode.yCoord);
		}
	}
	
	/**
	 * Überschriebene toString-Methode.
	 */
	public String toString() {
		return "e " + this.startNode.Id + " " + this.targetNode.Id + " Länge: " + this.length;
	}
}
