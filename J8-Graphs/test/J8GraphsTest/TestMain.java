package J8GraphsTest;

import J8Graphs.model.DiGraph;
import J8Graphs.util.GraphReader;

public class TestMain {

	static final String graphWithoutCycles = "data/DummyGraph.dat";
	static final String graphWithCycles = "data/DummyGraph2.dat";
	static final String graphTopologicalOrder = "data/DummyGraph3.dat";
	static final String graphWithUniqueCycle = "data/DummyGraph4.dat";

	
	public static void main(String[] args) {
		
		System.out.println("Start Test..");
		
		GraphReader gr = new GraphReader("data/randomgraph.out");
		DiGraph d = gr.getDiGraph();
		System.out.println(d.topologicalSorting());
//		ArrayList<Tree> forest = d.depthFirstSearch(d.getNodeWithID(1));		
//		
//		forest.forEach(System.out::println);
//		
//		LinkedList<Node> topSortLinkedList = d.topologicalSorting(d.getNodeWithID(1));
//		
//		topSortLinkedList.forEach(System.out::println);
		
//		RandomDiGraph r = new RandomDiGraph(1000, 2500);
//		DiGraph randomGraph = r.getRandomDiGraph();
//		
////		System.out.println("RandomGraph: \n" + randomGraph);
//		
//		System.out.println("TopSort: ");
//		System.out.println(randomGraph.topologicalSorting(randomGraph.getNodeWithID(0)));
//		
//		GraphWriter gw = new GraphWriter("data/graph.out");
//		gw.writeGraph(randomGraph.graphScanDFS(randomGraph.getNodeWithID(r.getRandomNodeId())));
//		
//		Tree dfsTree = d.depthFirstSearch(d.getNodeWithID(1));
//		
//		System.out.println("DFS-Baum:\n" + dfsTree);
//		
//		Tree bfsTree = d.breadthFirstSearch(d.getNodeWithID(1));
//		
//		System.out.println("BFS-Baum:");
//		System.out.println(bfsTree);
//		
//		Tree dfsTree2 = d.graphScanDFS(d.getNodeWithID(4));
//		
//		System.out.println("DFS-Baum:\n" + dfsTree2);
//		
//		Tree bfsTree2 = d.breadthFirstSearch(d.getNodeWithID(4));
//		
//		System.out.println("BFS-Baum:");
//		System.out.println(bfsTree2);
//		
//		RandomDiGraph r = new RandomDiGraph(100, 250);
//		
//		DiGraph randomGraph = r.getRandomDiGraph();
//		DiGraph randomGraph = r.getRandomAcyclicDiGraph();
//		
//		System.out.println("#########################\nRandomGraph:\n" + randomGraph);
//		
//		System.out.println(randomGraph.graphScanDFS(randomGraph.getNodeWithID(r.getRandomInt(0, 99))));
		
		System.out.println("Test finished..");
		}
	
}
