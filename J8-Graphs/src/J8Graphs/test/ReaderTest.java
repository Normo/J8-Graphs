package J8GraphsTest;

import java.util.Date;

import J8Graphs.util.GraphReader;

public class ReaderTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		System.out.println("Start ReaderTest...");
		
		long start = new Date().getTime();
		GraphReader reader = new GraphReader(args[0]);
		long runningTime = new Date().getTime() - start;
		System.out.println("Laufzeit Standard-Reader: " + runningTime + " ms");
		
		start = new Date().getTime();
		GraphReader reader2 = new GraphReader(true,args[0]);
		runningTime = new Date().getTime() - start;
		System.out.println("Laufzeit Neuer Reader: " + runningTime + " ms");
		
		System.out.println("Ende ReaderTest...");
		
	}

}
