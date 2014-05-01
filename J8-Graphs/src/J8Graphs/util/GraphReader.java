package J8Graphs.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Stream;

import J8Graphs.model.*;

/**
 * Liest Graphen aus einer Datei. 
 * @author Undisputed
 *
 */
public class GraphReader {
	
	public GraphReader(String path){
		
		Stream<String> source;
		try {

			String s;
			
			Graph result = new Graph();
			source = Files.lines(new File(path).toPath());
			
			source.forEach((line) -> {
				String[] sa = line.split("//s");
				if (sa[0] == "n")
				{					
					result.NodeAmount = Integer.parseInt(sa[line.indexOf("n")+2]);
					result.EdgeAmount = Integer.parseInt(sa[line.indexOf("m")+2]);
				}
				else
					ApplyLineToList(result, sa);
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	private void ApplyLineToList(Graph graph, String[] line)
	{	
		int startId = Integer.parseInt(line[1]);
		if (!graph.NodeExist(startId))
		{
			Node newNode = new Node(startId);
//			newNode.;
			graph.add(newNode);
		}
		
		return ;
	}
}
