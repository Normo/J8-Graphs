package J8Graphs;

import J8Graphs.algorithms.Dijkstra;
import J8Graphs.model.DiGraph;
import J8Graphs.model.Node;
import J8Graphs.util.GraphReader;

public class DijkstraMain {

	private static void printUsage() {
		System.out.println("Aufruf: Dijkstra <filename> -s <startknoten> -t <zielknoten> <optionen>");
	}
	
	public static void main(String[] args) {
		
		if (args.length < 6) {
			System.out.println("Zu wenig Parameter angegeben!");
			printUsage();
			System.exit(1);
		}

		String fileName = "";
		int startNodeId = -1;
		int targetNodeId = -1;
		boolean goalDirected = false;
		boolean binaryHeap= false;
		boolean bidirectional= false;
		boolean dial= false;
		
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-s":
				try {
					startNodeId = Integer.parseInt(args[i+1]);
				} catch (NumberFormatException e) {
					System.out.printf("[Fehler]: %s\n", e);
					System.out.println("\tDer Wert für den Startknoten ist ungültig!");
					System.out.println("\tErlaubte Werte liegen zwischen 0 und " + Integer.MAX_VALUE + ".\n");
					System.out.println("Beende Programm!");
					System.exit(1);
				}
				i++;
				break;
			case "-t":
				try {
					targetNodeId = Integer.parseInt(args[i+1]);
				} catch (NumberFormatException e) {
					System.out.printf("[Fehler]: %s\n", e);
					System.out.println("\tDer Wert für den Zielknoten ist ungültig!");
					System.out.println("\tErlaubte Werte liegen zwischen 0 und " + Integer.MAX_VALUE + ".\n");
					System.out.println("Beende Programm!");
					System.exit(1);
				}
				i++;
				break;
			case "-h": binaryHeap = true; break;
			case "-z": goalDirected = true; break;
			case "-b": bidirectional = true; break;
			case "-d": dial = true; break;
			default: fileName = args[i];
				break;
			}
		}
		
		DiGraph graph = null;
		Node start = null;
		Node target = null;
		
		if (!fileName.isEmpty()) {
			GraphReader reader = new GraphReader(true, fileName);
			graph = reader.getDiGraph();
		}
		
		if (startNodeId < 0 || startNodeId > graph.nodeAmount-1) {
			System.out.println("[Fehler]: Der Wert des Startknotens scheint für den gegebenen Graph nicht zu existieren!");
		} else {
			if ((start = graph.getNodeWithID(startNodeId)) == null)
				System.out.println("Startknoten wurde im Graph nicht gefunden!");
		}
		
		if (targetNodeId < 0 || targetNodeId > graph.nodeAmount-1) {
			System.out.println("[Fehler]: Der Wert des Zielknotens scheint für den gegebenen Graph nicht zu existieren!");
		} else {
			if ((target = graph.getNodeWithID(targetNodeId)) == null)
				System.out.println("Zielknoten wurde im Graph nicht gefunden!");
		}

		if (binaryHeap) {
			if (bidirectional) {
				// bidirektionaler Dijkstra
				Dijkstra.bidirectionalDijkstra(graph, start, target);
			} else {
				if (goalDirected) {
					// zielgerichteter Dijkstra mit binären Heap
					Dijkstra.standardDijkstra(graph, start, target, true);
				} else {
					// Dijkstra mit binären Heap
					Dijkstra.standardDijkstra(graph, start, target, false);
				}
			}
		} else if (dial) {
			if (bidirectional) {
				// Dials Implementation bidirektional
				Dijkstra.bidirectionalDial(graph, start, target);
			}else if (goalDirected) {
				//  Dials Implementation zielgerichtet
				Dijkstra.dialsImplementation(graph, start, target, true);
			} else {
				// Dials Implementation
				Dijkstra.dialsImplementation(graph, start, target, false);
			}
		} else {
			System.out.println("[Fehler]: Fehlende Optionen!");
		}


	}

}
