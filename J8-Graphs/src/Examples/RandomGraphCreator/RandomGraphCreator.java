package Examples.RandomGraphCreator;

import J8Graphs.util.GraphWriter;
import J8Graphs.util.RandomDiGraph;

public class RandomGraphCreator {

	public static void main(String[] args) {
		
		if (args.length < 3) {
			System.out.println("Fehler: Es wurden zu wenig Argumente übergeben!");
			System.out.println("Verwendung: java -jar RandomGraphCreator.jar /Pfad/zur/Output/Datei n m [azyklisch]");
			System.out.println("Optionen:\tn - Knotenanzahl\n\t\tm - Kantenanzahl\n\t\tazyklisch - für azyklische Graphen (optional)");
		} else {
			String path = args[0];
			int nodeAmount = Integer.parseInt(args[1]);
			int edgeAmount = Integer.parseInt(args[2]);
			
			if(edgeAmount < nodeAmount) {
				System.out.println("Kantenanzahl ist kleiner als Knotenanzahl!");
				System.exit(1);
			}
			
			RandomDiGraph randomGraph = new RandomDiGraph(nodeAmount, edgeAmount);
			GraphWriter gw = new GraphWriter(path);
			
			if (args.length > 3 && args[3].equals("azyklisch")) {
				System.out.println("Erzeuge zufälligen azyklischen gerichteten Graphen mit " + nodeAmount + " Knoten und " + edgeAmount + " Kanten.");
				gw.writeGraph(randomGraph.getAcyclicRandomDiGraph());
			} else {
				System.out.println("Erzeuge zufälligen gerichteten Graphen mit " + nodeAmount + " Knoten und " + edgeAmount + " Kanten.");
				gw.writeGraph(randomGraph.getRandomDiGraph());
			}
			
			System.out.println("Ergebnis in Outputdatei: " + path);
			
		}
		
		
		
		
	}
	
}
