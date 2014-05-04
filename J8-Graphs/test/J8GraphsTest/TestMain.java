package J8GraphsTest;

import J8Graphs.model.DiGraph;
import J8Graphs.model.tree.Tree;
import J8Graphs.util.GraphReader;

public class TestMain {

	static final String graphWithoutCycles = "data/DummyGraph.dat";
	static final String graphWithCycles = "data/DummyGraph2.dat";
	static final String graphTopologicalOrder = "data/DummyGraph3.dat";
	
	public static void main(String[] args) {
		
		System.out.println("Start Test..");
		
		GraphReader gr = new GraphReader(graphTopologicalOrder);
		DiGraph d = gr.getDiGraph();
		
		Tree dfsTree = d.depthFirstSearch(d.getNodeWithID(1));
		
		System.out.println("DFS-Baum:\n" + dfsTree);
		
		Tree bfsTree = d.breadthFirstSearch(d.getNodeWithID(1));
		
		System.out.println("BFS-Baum:");
		System.out.println(bfsTree);
		
		Tree dfsTree2 = d.depthFirstSearch(d.getNodeWithID(3));
		
		System.out.println("DFS-Baum:\n" + dfsTree2);
		
		Tree bfsTree2 = d.breadthFirstSearch(d.getNodeWithID(3));
		
		System.out.println("BFS-Baum:");
		System.out.println(bfsTree2);
		
		System.out.println("Test finished..");
		}
	
}
