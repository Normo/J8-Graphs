package J8GraphsTest;

import J8Graphs.model.DiGraph;
import J8Graphs.model.Node;
import J8Graphs.util.BinaryHeap;
import J8Graphs.util.GraphReader;

public class TestMain {

	static final String graphWithoutCycles = "data/DummyGraph.dat";
	static final String graphWithCycles = "data/DummyGraph2.dat";
	static final String graphTopologicalOrder = "data/DummyGraph3.dat";
	static final String graphWithUniqueCycle = "data/DummyGraph4.dat";
	static final String bigTestGraph1 = "data/n100000_m400000.txt";

	public static void testBinaryHeap() {
		//		BinaryNodeHeap bHeap = new BinaryNodeHeap();
		//		
		//		for (int i = 0; i < 10; i++) {
		//			bHeap.heap[i+1] = new Node(i+1);
		//			if(i > 0) {
		//				System.out.println("ParentIndex von Node " + (i+1) + " ist " + bHeap.parentIndex(i+1));
		//			}
		//		}
		//		
		//		System.out.println(Arrays.toString(bHeap.heap));

		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		Node node7 = new Node(7);
		Node node8 = new Node(8);
		Node node9 = new Node(9);
		Node node10 = new Node(10);
		//				node1.distance = 4;
		//				node2.distance = 6;
		//				node3.distance = 8;
		//				node4.distance = 2;
		//				node5.distance = 3;
		//				node6.distance = 7;
		//				node7.distance = 0;
		//				node8.distance = 0;
		//				node9.distance = 5;
		//				node10.distance = 9;

		node1.distance = 1;
		node2.distance = 2;
		node3.distance = 3;
		node4.distance = 4;
		node5.distance = 5;
		node6.distance = 6;
		node7.distance = 7;
		node8.distance = 8;
		node9.distance = 9;
		node10.distance = 10;

		Node[] array = { node1, node2, node3, node4, node5,
				node6, node7, node8, node9, node10};

		//		Node[] array = new Node[10];
		//		Node node;
		//		Random r = new Random();
		//
		//		for (int i = 0; i < array.length; i++) {
		//			node = new Node(i+1);
		//			//node.distance = (int) (Math.random()*10);
		//			node.distance = r.nextInt(100);
		//			System.out.println("Node " + (i+1) + " d = " + node.distance);
		//			array[i] = node;
		//		}

		BinaryHeap bHeap = new BinaryHeap(array);
		//bHeap.build();

		System.out.println(bHeap);
		System.out.println("Heap-Size: " + bHeap.size);
		System.out.println("Heap-Kapazität: " + bHeap.heap.length);
		System.out.println("Kleinstes Element: Node "+ bHeap.findMin());


		Node node11 = new Node(11);
		node11.distance = 0;

		bHeap.insert(node11);

		System.out.println("\n" + bHeap);
		System.out.println("Heap-Size: " + bHeap.size);
		System.out.println("Heap-Kapazität: " + bHeap.heap.length);
		System.out.println("Kleinstes Element: Node "+ bHeap.findMin());
		
		System.out.println("\nEntferne Node 11");
		bHeap.delete(0);
		System.out.println(bHeap);
		System.out.println("Heap-Size: " + bHeap.size);
		System.out.println("Heap-Kapazität: " + bHeap.heap.length);
		System.out.println("Kleinstes Element: Node "+ bHeap.findMin());

		
		System.out.println("\nEntferne Node 3");
		bHeap.delete(2);
		System.out.println(bHeap);
		System.out.println("Heap-Size: " + bHeap.size);
		System.out.println("Heap-Kapazität: " + bHeap.heap.length);
		System.out.println("Kleinstes Element: Node "+ bHeap.findMin());

		System.out.println("Entferne kleinstes Element:");
		bHeap.deleteMin();
		System.out.println(bHeap);
		System.out.println("Heap-Size: " + bHeap.size);
		System.out.println("Heap-Kapazität: " + bHeap.heap.length);
		System.out.println("Kleinstes Element: Node "+ bHeap.findMin());
		
		while (bHeap.size > 0) {
			System.out.println("\nEntferne kleinstes Element:");
			bHeap.deleteMin();
			System.out.println(bHeap);
			System.out.println("Heap-Size: " + bHeap.size);
			System.out.println("Heap-Kapazität: " + bHeap.heap.length);
			System.out.println("Kleinstes Element: Node "+ bHeap.findMin());
		}
		
	}
	
	public static void main(String[] args) {
		
		System.out.println("Start Test..");
		
		GraphReader gr = new GraphReader(graphWithoutCycles);
		DiGraph d = gr.getDiGraph();
		System.out.println(d);
		
		testBinaryHeap();
		
//		System.out.println(d.topologicalSorting());
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
