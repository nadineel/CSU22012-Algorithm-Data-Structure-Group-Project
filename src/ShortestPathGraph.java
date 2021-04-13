import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ShortestPathGraph {
	private String stop_timesFilename, transferFilename;
	private double adjacencyMatrix[][] = new double[12478][12478];
	
	//in the context of this program the Filenames should always be correct
	//as they are static
	ShortestPathGraph(String stop_timesFilename, String transfersFilename) {
		this.stop_timesFilename = stop_timesFilename;
		this.transferFilename = transfersFilename;
		//initialise matrix to infinity
	}
	
	public void makeAdjacencyMatrix() throws FileNotFoundException {
		
		//initialize matrix to infinity
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			for(int j = 0; j < adjacencyMatrix[i].length; j++) {
				if(i != j) {
					adjacencyMatrix[i][j] = Double.POSITIVE_INFINITY;
				}
				else {
					adjacencyMatrix[i][j] = 0;
				}
			}
		}
		
		File stopTimesFile = new File(stop_timesFilename);
		//scanner to parse lines of the file
		Scanner stopTimesScanner = new Scanner(stopTimesFile);
		//scanner to decode the lines of the file
		Scanner stopTimesLineScanner = null;
		//get rid of the first line of the file
		stopTimesScanner.nextLine();
		
		int from = 0, to = 0, prevRouteId = 0, routeId = 0;
		double weight;
		String currentLine;
		
		//for loop to scan through the file and build the matrix
		//since this is the stop times file the weight is 1
		weight = 1;
		while(stopTimesScanner.hasNextLine()) {
			currentLine = stopTimesScanner.nextLine();
			stopTimesLineScanner = new Scanner(currentLine);
			stopTimesLineScanner.useDelimiter(",");
			
			prevRouteId = routeId;
			routeId = stopTimesLineScanner.nextInt();
			
			//skip some values we dont care about
			stopTimesLineScanner.next();
			stopTimesLineScanner.next();
			
			from = to;
			to = stopTimesLineScanner.nextInt();
			if(prevRouteId == routeId) {
				adjacencyMatrix[from][to] = weight; 
			}
		}
	}
	
	public static void main(String[] args) {
		ShortestPathGraph graph = new ShortestPathGraph("C:\\Users\\Filip Kowalski\\Desktop\\inputs\\stop_times.txt", "C:\\Users\\Filip Kowalski\\Desktop\\inputs\\transfers.txt");
		try {
			graph.makeAdjacencyMatrix();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
