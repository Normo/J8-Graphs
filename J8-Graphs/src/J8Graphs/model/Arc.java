package J8Graphs.model;

/**
 * Basisklasse für Kanten in gerichteten Graphen.
 * @author Undisputed
 * @author normo
 *
 */
public class Arc {

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
	 * Konstruktor.
	 * @param start Startknoten
	 * @param target Zielknoten
	 */
	public Arc(Node start, Node target){
		this.startNode = start;
		this.targetNode = target;
		//this.Id = Integer.parseInt(this.startNode.Id + "" + this.targetNode.Id);
	}
	
	/**
	 * Überschriebene toString-Methode.
	 */
	public String toString() {
		return "e " + this.startNode.Id + " " + this.targetNode.Id;
	}
}
