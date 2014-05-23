package J8Graphs.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import J8Graphs.model.DiGraph;

/**
 * GraphWriter-klasse: Schreibt Graphen bzw Zeichenketten in eine Datei.
 * @author normo
 *
 */
public class GraphWriter {

	/**
	 * Pfad zur Ausgabedatei
	 */
	public String outputFile;
	
	/**
	 * Standard-Konstruktor.
	 * @param filePath Pfad zur Ausgabedatei
	 */
	public GraphWriter(String filePath) {
		this.outputFile = filePath;
	}
	
	/**
	 * Schreibt DiGraphen bzw. Arboreszenzen in eine vorher festgelegte
	 * Ausgabedatei.
	 * @param graph DiGraph- oder Tree-Objekt, das in eine Datei geschrieben werden soll
	 */
	public void writeGraph(DiGraph graph) {
		try {
			Files.write(new File(this.outputFile).toPath(), graph.toString().getBytes(), StandardOpenOption.CREATE_NEW);
		} catch (FileAlreadyExistsException e) {
			System.out.printf("[Fehler]: %s\n", e);
			System.out.println("\tDie Datei " + this.outputFile + " existiert bereits!");
			System.out.println("\tBenennen Sie diese um oder löschen Sie die Datei.\n");
			System.out.println("Beende Programm!");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Schreibt eine Zeichekette in eine vorher festgelegte Ausgabedatei.
	 * @param sequence Zeichenkette
	 */
	public void writeToFile(String sequence) {
		try {
			Files.write(new File(this.outputFile).toPath(), sequence.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
