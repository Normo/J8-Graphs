package J8Graphs;

import java.util.ArrayList;

import J8Graphs.model.DiGraph;
import J8Graphs.model.tree.Tree;
import J8Graphs.util.GraphReader;
import J8Graphs.util.GraphWriter;

public class Main {

	public static void main(String[] args) {

		if (args.length < 2) {
			System.out.println("Fehler: Zu wenig Argumente übergeben!");
			System.out.println("Es muss je ein Pfad für Eingabedatei und Ausgabedatei angegeben werden!");
		} else {
			
			// speichere Kommandozeilenargumente für weitere Verwendung
			String inFile = args[0];
			String outFile = args[1];
			
			// lese Graph aus Datei ein und erzeuge daraus DiGraph-Objekt
			System.out.println("Lese Graph aus Datei " + inFile);
			GraphReader reader = new GraphReader(inFile);
			DiGraph graph = reader.getDiGraph();
			
			// Gebe DiGraph-Objekt auf Konsole aus
			System.out.println("\nGraph:");
			System.out.println(graph);
			
			// Graph Scan Algorithmus ausgehend von ersten Knoten des Graphen und Ausgabe des resultierenden DFS-Baums
			Tree dfsBaum = graph.graphScanDFS(graph.getFirst());
			System.out.println("\nDFS-Baum:");
			System.out.println(dfsBaum);
			
			// Komplette Tiefensuche ausgehend von Knoten 3
			ArrayList<Tree> dfsWald = graph.depthFirstSearch(graph.getNodeWithID(3));
			System.out.println("\n####### Tiefensuche-Wald #######");
			dfsWald.forEach(baum -> System.out.println(baum + "\n"));
			System.out.println("################################");

			// schreibe den DFS in die Ausgabedatei
			GraphWriter writer = new GraphWriter(outFile);
			writer.writeGraph(dfsBaum);
			System.out.println("\nDFS-Baum in Datei " + outFile + " geschrieben.");
		}
		
		
		
	}

}
