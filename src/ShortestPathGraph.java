import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ShortestPathGraph {
	private String stop_timesFilename, transfersFilename;
	private double adjacencyMatrix[][] = new double[12479][12479];
	
	//in the context of this program the Filenames should always be correct
	//as they are static
	ShortestPathGraph(String stop_timesFilename, String transfersFilename) {
		this.stop_timesFilename = stop_timesFilename;
		this.transfersFilename = transfersFilename;
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
		Scanner fileScanner = new Scanner(stopTimesFile);
		//scanner to decode the lines of the file
		Scanner lineScanner = null;
		//get rid of the first line of the file
		fileScanner.nextLine();
		
		int from = 0, to = 0, prevRouteId = 0, routeId = 0;
		double weight;
		String currentLine;
		
		//while loop to scan through the file and build the matrix
		//since this is the stop times file the weight is 1
		weight = 1;
		while(fileScanner.hasNextLine()) {
			currentLine = fileScanner.nextLine();
			lineScanner = new Scanner(currentLine);
			lineScanner.useDelimiter(",");
			
			prevRouteId = routeId;
			routeId = lineScanner.nextInt();
			
			//skip some values we dont care about
			lineScanner.next();
			lineScanner.next();
			
			from = to;
			to = lineScanner.nextInt();
			if(prevRouteId == routeId) {
				adjacencyMatrix[from][to] = weight; 
			}
			lineScanner.close();
		}
		fileScanner.close();
		
		int transferType; 
		double minimumTransferTime;
		double transferType2Divisor = 100;
		File transfersFile = new File(transfersFilename);
		fileScanner = new Scanner(transfersFile);
		
		//throw away the first line of the file
		fileScanner.nextLine();
		
		//while loop to scan through the file and add connections to the matrix
		//weight is 2 when transfer type is 0 and when transfer type is 1 the
		//weight is the minimum transfer time / 100
		while(fileScanner.hasNextLine()) {
			currentLine = fileScanner.nextLine();
			lineScanner = new Scanner(currentLine);
			lineScanner.useDelimiter(",");
			
			from = lineScanner.nextInt();
			to = lineScanner.nextInt();
			transferType = lineScanner.nextInt();
			
			if(transferType == 0) {
				adjacencyMatrix[from][to] = 2;
			}
			else if(transferType == 2) {
				minimumTransferTime = lineScanner.nextDouble();
				adjacencyMatrix[from][to] = minimumTransferTime / transferType2Divisor;
			}
			lineScanner.close();
		}
		fileScanner.close();
	}
	
	public static void main(String[] args) {
		ShortestPathGraph graph = new ShortestPathGraph("C:\\Users\\Filip Kowalski\\Desktop\\inputs\\stop_times.txt", "C:\\Users\\Filip Kowalski\\Desktop\\inputs\\transfers.txt");
		try {
			graph.makeAdjacencyMatrix();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int hello =0;
		hello++;
	}
}
