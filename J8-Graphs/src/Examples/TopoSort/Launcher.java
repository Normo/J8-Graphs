package Examples.TopoSort;

import J8Graphs.model.DiGraph;
import J8Graphs.model.Node;
import J8Graphs.util.GraphReader;
import J8Graphs.util.GraphWriter;

public class Launcher {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Fehler: Es wurden zu wenig Argumente übergeben!");
			System.out.println("Verwendung: java -jar toposort.jar inputFile outputFile");
		} else {
			String inFile = args[0];
			String outFile = args[1];
			
			GraphReader gr = new GraphReader(inFile);
			DiGraph graph = gr.getDiGraph();
			
			StringBuilder output;
			
			if (graph.topologicalSorting()) {
				
				System.out.println("Graph enthält keinen Zyklus und konnte topologisch sortiert werden.");
				
				output = new StringBuilder("1");
				
				for (Node node : graph.topoSort) {
					output.append("\n" + node.Id);
				}				
			} else {
				System.out.println("Graph enthält einen Zyklus!");

				output = new StringBuilder("0");
				
				for (int i = 0; i < graph.cyclePath.size()-1; i++) {
					output.append("\ne " + graph.cyclePath.get(i) + " " + graph.cyclePath.get(i+1));
				}
			}
			
			GraphWriter gw = new GraphWriter(outFile);
			gw.writeToFile(output.toString());
			System.out.println("Ergebnis in Outputfile: " + outFile);
		}

	}

}
