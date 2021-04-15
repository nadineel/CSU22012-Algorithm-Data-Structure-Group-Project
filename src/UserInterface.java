import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		//makes the graph from the start so less loading after
		//ShortestPathGraph graph = new ShortestPathGraph("stop_times.txt", "transfers.txt");

		Scanner input = new Scanner (System.in)	;
		System.out.println("Welcome to Vancouver public transport system. This system will help you plan your route.");
		System.out.println("Enter 1 if you would like to find the shortest path between 2 bus stops");
		System.out.println("Enter 2 if you would like to search for a bus stop");
		System.out.println("Enter 3 if you would like to find all trips in a given arrival time");
		System.out.println("Enter quit if you would like to exit");

		boolean finished = false;
		while(!finished)
		{
			if  (input.hasNextLine())
			{
				if (input.hasNext("quit"))
				{
					System.out.println("Thank you for using Vancouver public transport system. We hope you have a pleasent day :)");
					finished = true ;
				}
				else if(input.hasNext("1"))
				{
					//System.out.println("Please enter the two bus stops you want to find the shortest path between as numbers seperated by a comma");
					input.nextLine(); //move the scanner past 1 entered

					boolean one = true ;
					while(one == true )
					{
						System.out.println("Please enter the two bus stops you want to find the shortest path between as numbers seperated by a comma.(enter quit to exit or back to return to main)");
						if (input.hasNextLine())	
						{
							String userInput =input.nextLine();
							String[] stops = userInput.split(",");

							if (userInput.equalsIgnoreCase("quit"))	
							{
								finished = true;
								System.out.println("Thank you for using Vancouver public transport system. We hope you have a pleasent day :)"); 
							}
							else if(userInput.equalsIgnoreCase("back"))
							{
								System.out.println("Enter 1 for shortest path, 2 to search by bustops , 3 to find all trips by arrival time or  quit to exit the application");
								one = false ;
							}
							else if(stops.length < 0 || stops.length >2)
							{
								System.out.println("Error enter in exactly two stop numbers");
							}
							else if (stops.length == 2)
							{
								try {
									int stop1 = Integer.parseInt(stops[0]);
									int stop2 = Integer.parseInt(stops[1]);

									//System.out.println("Shortest distance from " + stop1 + " to " + stop2 + ":" + graph.shortestDistanceAndTrace(stop1, stop2));
									System.out.println("Shortest distance from " + stop1 + " to " + stop2 + ":" );
								}
								catch(NumberFormatException nfe)//catches letters entered instead
								{
									System.out.println("Error enter in integers only");	
								}
							}//will add more else if for how many stop numbers are there?
							else 
							{
								System.out.println("Error please enter two integers comma seperated");
							}
						}	
					}
				}
				
				else if(input.hasNext("2"))
				{	
					System.out.println("2:");
					System.out.println("Enter 1 for shortest path, 2 to search by bustops , 3 to find all trips by arrival time or  quit to exit the application");
					input.nextLine();
				}
				
				else if(input.hasNext("3"))
				{
					System.out.println("3");
					System.out.println("Enter 1 for shortest path, 2 to search by bustops , 3 to find all trips by arrival time or  quit to exit the application");
					input.nextLine();
				}
				
				else
				{
					System.out.println("Sorry that is not a valid input please enter in 1 , 2 or 3 (or quit to exit)");
					input.nextLine();
				}
			}
		}
	}
}
