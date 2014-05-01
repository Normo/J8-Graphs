package J8Graphs.model;

import java.util.LinkedList;

/**
 * Graph
 * @author Undisputed
 *
 */
public class Graph extends LinkedList<Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int NodeAmount;
	public int EdgeAmount;
	
	
	public Graph() {
	}
	
	public boolean NodeExist(int id)
	{
		boolean result = false;
		for(Node node : this){
			if (node.Id == id){
				result = true;
				break;
			}
		};
		return result;
	}


}
