package J8Graphs.model.tree;

import J8Graphs.model.Arc;
import J8Graphs.model.Node;

/**
 * Klasse für Knoten in Arboreszenzen (derzeit nicht verwendet).
 * @author normo
 *
 */
public class TreeNode extends Node {

	/**
	 * Die einzige eingehende Kante des TreeNode
	 */
	public Arc enteringArc;
	
	/**
	 * Vorgänger des TreeNode in der Arboreszenz
	 */
	public TreeNode ancestor;
	
	/**
	 * Label, das angibt, ob der Knoten ein Wurzelknoten in einem Baum ist.
	 */
	public boolean isRoot;
	
	/**
	 * Standardkonstruktor
	 * @param node Knotenobjekt
	 */
	public TreeNode(Node node) {
		super(node.Id);
		this.isRoot = false;
	}
	
	/**
	 * Wurzelknoten-Knonstruktor
	 * @param node Knotenobjekt
	 * @param isRoot TRUE, falls dieser TreeNode ein Wurzelknoten ist
	 */
	public TreeNode(Node node, boolean isRoot) {
		super(node.Id);
		this.isRoot = isRoot;
	}

	/**
	 * Setzt das "isRoot"-Label des Knotens.
	 * @param state TRUE, wenn Knoten die Wurzel ist, sonst FALSE
	 */
	public void setRoot(boolean state) {
		this.isRoot = state;
	}
	
	/**
	 * Gibt den Status des "isRoot"-Labels zurück.
	 * @return TRUE, falls dieser TreeNode ein Wurzelknoten ist
	 */
	public boolean isRoot() {
		return this.isRoot;
	}
	
}
