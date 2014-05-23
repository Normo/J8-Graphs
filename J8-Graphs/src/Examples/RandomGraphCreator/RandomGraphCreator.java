package Examples.RandomGraphCreator;

import java.util.Date;

import J8Graphs.model.DiGraph;
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
			
			GraphWriter gw = new GraphWriter(path);
			DiGraph g;
			long start, runningTime;
			
			if (args.length > 3 && args[3].equals("azyklisch")) {
				System.out.println("Erzeuge zufälligen azyklischen gerichteten Graphen mit " + nodeAmount + " Knoten und " + edgeAmount + " Kanten.");
				start = new Date().getTime();
				g = RandomDiGraph.getCoordinatedRandomDiGraph(nodeAmount, edgeAmount, true);
				runningTime = new Date().getTime() - start;
				System.out.println("Laufzeit Graph erzeugen: " + runningTime + " ms");
			} else {
				System.out.println("Erzeuge zufälligen gerichteten Graphen mit " + nodeAmount + " Knoten und " + edgeAmount + " Kanten.");
				start = new Date().getTime();
				g = RandomDiGraph.getCoordinatedRandomDiGraph(nodeAmount, edgeAmount, false);
				runningTime = new Date().getTime() - start;
				System.out.println("Laufzeit Graph erzeugen: " + runningTime + " ms");
			}
			
			long start2 = new Date().getTime();
			gw.writeGraph(g);
			long runningTime2 = new Date().getTime() - start2;
			System.out.println("Laufzeit Graph schreiben: " + runningTime2 + " ms");
			
			System.out.println("Ergebnis in Outputdatei: " + path);
			
//			g.forEach(node -> {
//				node.outEdges.forEach(edge -> {
//					System.out.println(edge + " Länge: " +edge.length );
//				});
//			});
			
		}
		
		
		
		
	}
	
}
