package J8Graphs.util;

import J8Graphs.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * GraphReader-Klasse: Liest Graphen aus einer Datei. 
 * @author Undisputed
 * @author normo
 *
 */
public class GraphReader {
	
	File source;
	DiGraph resultGraph;
	
	/**
	 * Konstruktor.
	 * @param filePath Dateipfad
	 */
	public GraphReader(String filePath) {

		Stream<String> srcStream = null;
		File graphFile = new File(filePath);
		this.resultGraph = new DiGraph();
		
		if (!graphFile.exists()) {
			System.out.println("Datei " + filePath + " konnte nicht gefunden werden!");
			System.exit(1);
		}
		
		try {
			srcStream = Files.lines(graphFile.toPath());
			
			srcStream.forEach((String line) -> {

				String[] splittedLine = line.split("\\s");
				
				if (splittedLine[0].equals("e")) {
					ApplyLineToList(splittedLine);
				} else if (splittedLine[0].equals("n"))	{					
					this.resultGraph.nodeAmount = Integer.parseInt(splittedLine[1]);
					this.resultGraph.arcAmount = Integer.parseInt(splittedLine[3]);
				}
				
			});
			
			srcStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	/**
	 * Wertet eine Zeile aus, die eine neue Kante enthält. Das neue Kantenobjekt
	 * wird gemäß der Informationen aus der Zeile den inzidenten Knoten als ein-
	 * bzw. ausgehende Kante hinzugefügt. Nichtexistente Knoten werden neu erzeugt
	 * und dem DiGraph hinzugefügt.
	 * @param line String-Array, dass die Whitespace-gesplittete Zeile enthält
	 */
	private void ApplyLineToList(String[] line)
	{	
		if(line.length<3) {
			System.out.println("Falsches Format!");
		}
		
		int startNodeId = Integer.parseInt(line[1]);
		int targetNodeId = Integer.parseInt(line[2]);
				
		Node startNode;
		Node targetNode;
		
		//Check, ob die Knoten bereits im Graph existieren
		if ((startNode = this.resultGraph.getNodeWithID(startNodeId)) == null) {
			// Erzeuge neuen Start-Knoten und füge ihn zum Graphen hinzu
			startNode = new Node(startNodeId);
			this.resultGraph.add(startNode);
		} 
		if ((targetNode = this.resultGraph.getNodeWithID(targetNodeId)) == null) {
			// Erzeuge neuen Target-Knoten und füge ihn zum Graphen hinzu
			targetNode = new Node(targetNodeId);
			this.resultGraph.add(targetNode);
			System.out.println();
		}
		//Erstelle neue gerichtete Kante und weise diese den Knoten zu
		Arc newArc = new Arc(startNode, targetNode); 
		
		startNode.addOutgoingArc(newArc);
		targetNode.addIncomingArc(newArc);
	}
	
	public DiGraph getDiGraph() {
		return this.resultGraph;
	}
}
