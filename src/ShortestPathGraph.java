import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ShortestPathGraph {
	private String stop_timesFilename, transfersFilename;
	private double adjacencyMatrix[][] = new double[12479][12479];

	ShortestPathGraph(String stop_timesFilename, String transfersFilename) {
		this.stop_timesFilename = stop_timesFilename;
		this.transfersFilename = transfersFilename;
		
		//build our matrix
		try {
			makeAdjacencyMatrix();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void makeAdjacencyMatrix() throws FileNotFoundException {
		
		//initialize matrix to infinity
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			for(int j = 0; j < adjacencyMatrix[i].length; j++) {
				if(i != j) {
					adjacencyMatrix[i][j] = Double.NaN;
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
	
	public String shortestDistanceAndTrace(int from, int to){
		
		if(from == to) {
			return "" + adjacencyMatrix[from][to] + " through " + from;
		}
		
		int visited[] = new int[adjacencyMatrix.length];
    	double distTo[] = new double[adjacencyMatrix.length];
    	int edgeTo[] = new int[adjacencyMatrix.length];
    	
    	//set all but starting node to infinity
    	for(int i = 0; i < distTo.length; i++) {
    		if(i != from)
    		{
    			distTo[i] = Double.POSITIVE_INFINITY;
    		}
    	}
    	
    	//use dijkstras algorthm for shortest path
    	visited[from] = 1;
    	distTo[from] = 0; //to identify starting node
    	int currentNode = from;
    	int totalNodesVisited = 0;
    	while(totalNodesVisited < distTo.length)
    	{
    		//relax the edges pointing from the current node then set it as visited
    		for(int i = 0; i < adjacencyMatrix[currentNode].length; i ++) {
    			if(!Double.isNaN(adjacencyMatrix[currentNode][i]) && visited[i] == 0) {
        			relaxEdge(currentNode, i, distTo, edgeTo);
        		}
    		}
    		visited[currentNode] = 1;
    		
    		//pick the next node that is currently the one with the shortest distance value 
    		//and that has not been yet visitedin our graph to relax 
    		double shortestDist = Integer.MAX_VALUE;
    		for(int i = 0; i < distTo.length; i++) {
    			if(visited[i] != 1 && shortestDist > distTo[i]) {
    				currentNode = i;
    				shortestDist = distTo[i];
    			}
    		}
    		totalNodesVisited++;
    	}
    	
    	//build the path we took through the graph
    	if(distTo[to] == Double.POSITIVE_INFINITY) {
    		return "not existent";
    	}
    	
    	int u = from;
    	int v = to;
    	String trace = "";
    	while(v != u) {
    		trace =  "->" + edgeTo[v] + trace;
    		v = edgeTo[v];
    	}
    	trace = trace + "->" + to;
    	
    	return distTo[to] + " through " + trace;
    }
	
	//used code from the slides for edge relaxation
    private void relaxEdge(int from, int to, double[] distTo, int[] edgeTo) {
    	if(distTo[to] > distTo[from] + adjacencyMatrix[from][to]) {
    		distTo[to] = distTo[from] + adjacencyMatrix[from][to];
    		edgeTo[to] = from;
    	}
    }
	
	// public static void main(String[] args) {
	// 	ShortestPathGraph graph = new ShortestPathGraph("C:\\Users\\Filip Kowalski\\Desktop\\inputs\\stop_times.txt", "C:\\Users\\Filip Kowalski\\Desktop\\inputs\\transfers.txt");
	// 	try {
	// 		graph.makeAdjacencyMatrix();
	// 	} catch (FileNotFoundException e) {
	// 		e.printStackTrace();
	// 	}
	// 	System.out.println("Shortest distance from 35 to 8035: " + graph.shortestDistanceAndTrace(35, 8035));
	// 	//System.out.println("Path Takes: " + graph.shortestPathTrace(8756, 8577));
	// 	System.out.println("Shortest distance from 0 to 8035: " + graph.shortestDistanceAndTrace(0, 8035));
	// 	System.out.println("Shortest distance from 646 to 381: " + graph.shortestDistanceAndTrace(646, 381));
	// }
}