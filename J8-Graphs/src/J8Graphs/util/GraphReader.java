package J8Graphs.util;

import J8Graphs.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	 * Standard-Konstruktor.
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
					createNewEdge(splittedLine);
				} else if (splittedLine[0].equals("v")) {
					createNewNode(splittedLine);
				} else if (splittedLine[0].equals("n"))	{					
					try {
						this.resultGraph.nodeAmount = Integer.parseInt(splittedLine[1]);
						this.resultGraph.edgeAmount = Integer.parseInt(splittedLine[3]);
					} catch (NumberFormatException e) {
						System.out.printf("[Fehler]: %s\n", e);
						System.out.println("\tFür Knoten- oder Kantenanzahl wurde ein ungültiger Wert angegeben!");
						System.out.println("\tErlaubte Werte liegen zwischen 0 und " + Integer.MAX_VALUE + ".\n");
						System.out.println("Beende Programm!");
						System.exit(1);
					}
				}				
			});
			
			srcStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	/**
	 * Alternativer Konstruktor, der eine Einlese-Methode ohne Streams und
	 * Lambda-Ausdrücke verwendet. Ist i.d.R. langsamer als der 
	 * Standard-Konstruktor.
	 * @param filePath Pfad zur Input-Datei
	 * @param var dient nur zum Unterschied der Konstruktor-Signatur
	 */
	public GraphReader(String filePath, boolean var) {
		
		File file = new File(filePath);
		this.resultGraph = new DiGraph();
		
		if(!file.exists()) {
			System.out.println("Datei " + filePath +" existiert nicht!");
			System.exit(1);
		}
		
		BufferedReader in = null;
		String line;
		String[] splittedLine;
		
		try {
			in = new BufferedReader(new FileReader(file));
			
			while ((line = in.readLine()) != null) {
				splittedLine = line.split("\\s");
				
				if (splittedLine[0].equals("e")) {
					createNewEdge(splittedLine);
				} else if (splittedLine[0].equals("v")) {
					createNewNode(splittedLine);
				} else if (splittedLine[0].equals("n"))	{					
					try {
						this.resultGraph.nodeAmount = Integer.parseInt(splittedLine[1]);
						this.resultGraph.edgeAmount = Integer.parseInt(splittedLine[3]);
					} catch (NumberFormatException e) {
						System.out.printf("[Fehler]: %s\n", e);
						System.out.println("\tFür Knoten- oder Kantenanzahl wurde ein ungültiger Wert angegeben!");
						System.out.println("\tErlaubte Werte liegen zwischen 0 und " + Integer.MAX_VALUE + ".\n");
						System.out.println("Beende Programm!");
						System.exit(1);
					}
				}
				
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Wertet eine Zeile aus, die eine neue Kante enthält. Das neue Kantenobjekt
	 * wird gemäß der Informationen aus der Zeile den inzidenten Knoten als ein-
	 * bzw. ausgehende Kante hinzugefügt. Nichtexistente Knoten werden neu erzeugt
	 * und dem DiGraph hinzugefügt.
	 * @param line String-Array, dass die Whitespace-gesplittete Zeile enthält
	 */
	private void createNewEdge(String[] line) {
		if(line.length<3) {
			System.out.println("Zeile enthält falsches Format!");
			return;
		}
		
		int startNodeId = -1;
		int targetNodeId = -1;
		try {
			startNodeId = Integer.parseInt(line[1]);
			targetNodeId = Integer.parseInt(line[2]);
		} catch (NumberFormatException e) {
			System.out.printf("[Fehler]: %s\n", e);
			System.out.println("\tFür eine Knoten-ID wurde ein ungültiger Wert angegeben!");
			System.out.println("\tErlaubte Werte liegen zwischen 0 und " + Integer.MAX_VALUE + ".\n");
			System.out.println("Beende Programm!");
			System.exit(1);
		}
				
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
		Edge newEdge = new Edge(startNode, targetNode); 
		
		startNode.addOutgoingEdge(newEdge);
		targetNode.addIncomingEdge(newEdge);
	}
	
	/**
	 * Wertet eine Zeile, beginnend mit 'v', aus und erstellt einen neuen Knoten.
	 * @param line String-Array, dass die Whitespace-gesplittete Zeile enthält
	 */
	private void createNewNode(String[] line) {
		if (line.length < 4) {
			System.out.println("Zeile enthält falsches Format!");
			return;
		}
		
		try {
			int newNodeId = Integer.parseInt(line[1]);
			int xCoord = Integer.parseInt(line[2]);
			int yCoord = Integer.parseInt(line[3]);
			
			Node newNode = new Node(newNodeId, xCoord, yCoord);
			this.resultGraph.add(newNode);
			
		} catch (NumberFormatException e) {
			System.out.printf("[Fehler]: %s\n", e);
			System.out.println("\tFür eine Knoten-ID oder Koordinate wurde ein ungültiger Wert angegeben!");
			System.out.println("\tErlaubte Werte liegen zwischen 0 und " + Integer.MAX_VALUE + ".\n");
			System.out.println("Beende Programm!");
			System.exit(1);
		}
	}
	
	public DiGraph getDiGraph() {
		return this.resultGraph;
	}
}
