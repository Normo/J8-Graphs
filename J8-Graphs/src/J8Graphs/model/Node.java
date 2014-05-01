package J8Graphs.model;

import java.util.LinkedList;

/**
 * Knoten Basisklasse
 * @author Undisputed
 *
 */
public class Node {

	public int Id;
	public LinkedList<Edge> OutEdges;
	public LinkedList<Edge> InEdges;
	public boolean Visited;
	public boolean Finished;
	
	public int GetDegree(){
		int result = 0;
		if (OutEdges != null){
			result = OutEdges.size(); 
		}
		if (InEdges != null){
			result += InEdges.size(); 
		}
		return result;
	};
	
	public Node(int id){
		Id = id;
		OutEdges = new LinkedList<Edge>();
		InEdges = new LinkedList<Edge>();
		Visited = false;
		Finished = false;
	}
	
}
