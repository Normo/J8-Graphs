package J8GraphsTest;

import java.util.Date;

import J8Graphs.util.GraphReader;

public class ReaderTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		long start = new Date().getTime();
		GraphReader reader = new GraphReader(args[0]);
		long runningTime = new Date().getTime() - start;
		System.out.println("Laufzeit: " + runningTime + " ms");
		
		start = new Date().getTime();
		GraphReader reader2 = new GraphReader(args[0], true);
		runningTime = new Date().getTime() - start;
		System.out.println("Laufzeit: " + runningTime + " ms");
		
	}

}
