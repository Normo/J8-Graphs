package J8Graphs.test;

import java.util.Date;

import J8Graphs.algorithms.Dijkstra;
import J8Graphs.model.DiGraph;
import J8Graphs.model.Node;
import J8Graphs.util.GraphReader;

public class DijkstraTest {

	public static void main(String[] args) {
		
		if (args.length < 1) {
			return;
		}
		
		System.out.println("Start Test...");
		
		long start = new Date().getTime();
		DiGraph graph = new GraphReader(true, args[0]).getDiGraph();
		long runningTime = new Date().getTime() - start;
		System.out.println("Laufzeit Graph einlesen: " + runningTime + " ms\n\n");
		
		System.out.println("Anzahl Knoten: " + graph.nodeAmount);
		System.out.println("Anzahl Kanten: " + graph.edgeAmount);
		
		System.out.println("##########################################");
		
		Node s = graph.get(1);
		Node t = graph.getNodeWithID(graph.nodeAmount-1);
		
		Dijkstra.standardDijkstra(graph, s, t, true);
		
		Dijkstra.standardDijkstra(graph, s, t, false);

		Dijkstra.bidirectionalDijkstra(graph, s, t);

		//Dijkstra.dialsImplementation(graph, s, t, true);
		
		Dijkstra.dialsImplementation(graph, s, t, false);
		
		Dijkstra.bidirectionalDial(graph, s, t);
		
		System.out.println("##########################################");
		
		s = graph.get(0);
		t = graph.getNodeWithID(graph.getNodeAmount()-1);
		
		Dijkstra.standardDijkstra(graph, s, t, true);
		
		Dijkstra.standardDijkstra(graph, s, t, false);

		Dijkstra.bidirectionalDijkstra(graph, s, t);

		//Dijkstra.dialsImplementation(graph, s, t, true);
		
		Dijkstra.dialsImplementation(graph, s, t, false);
		
		Dijkstra.bidirectionalDial(graph, s, t);
		
		System.out.println("##########################################");
		
		s = graph.get(1809+1);
		t = graph.get(7012+1);
		
		Dijkstra.standardDijkstra(graph, s, t, true);
		
		Dijkstra.standardDijkstra(graph, s, t, false);

		Dijkstra.bidirectionalDijkstra(graph, s, t);

		//Dijkstra.dialsImplementation(graph, s, t, true);
		
		Dijkstra.dialsImplementation(graph, s, t, false);
		
		Dijkstra.bidirectionalDial(graph, s, t);
		
		System.out.println("Ende Test...");
		
	}
	
}
