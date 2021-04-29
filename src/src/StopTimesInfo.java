// note: if value=-1, its null
// not know what type stopheadsign is. all are empty hence i left it as string atm

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

 class StopTimesInfo {

	protected static List<TripInfo> TripInfos;

	StopTimesInfo(String filename) {
		File file;
		try {
			if (filename != null) {
				TripInfos = new ArrayList<>();
				double i = -1;
				file = new File(filename);
				Scanner scan = new Scanner(file);
				scan.nextLine();//skip the label
				while (scan.hasNextLine()) {
					String s[] = scan.nextLine().split("\\s+|,\\s*");
                    //check which line is being processed
					//System.out.println(count+=1);
					int a = (s[0] != "") ? Integer.parseInt(s[0]) : -1;
					Time b = Time.valueOf(s[1]);
					Time c = Time.valueOf(s[2]);
					int d = (s[3] != "") ? Integer.parseInt(s[3]) : -1;
					int e = (s[4] != "") ? Integer.parseInt(s[4]) : -1;
					String f = (s[5] != "") ? s[5] : null;
					int g = (s[6] != "") ? Integer.parseInt(s[6]) : -1;
					int h = (s[7] != "") ? Integer.parseInt(s[7]) : -1;
					try {
					i = (s[8] != "") ? Double.parseDouble(s[8]) : -1;
					
					}
					catch(ArrayIndexOutOfBoundsException e1) {
						i = -1;
						
					}

					TripInfos.add(new TripInfo(a, b, c, d, e, f, g, h, i));
				}
				scan.close();
			} else {

			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	// sort by trip id
	protected static Comparator<TripInfo> sortByTripId = new Comparator<TripInfo>() {

		@Override
		public int compare(TripInfo a, TripInfo b) {

			// sort in ascending order
			return a.trip_id - b.trip_id;

		}
	};

//returns all Stops with the specified arrival time sorted by by trip_id	
	public static List<TripInfo> getStopsInfo(String arrivalTime) {
		
			List<TripInfo>stopsByArrival= new ArrayList<>();
			try {
				Time arriveT= Time.valueOf(arrivalTime);
				String testTime[] = arrivalTime.split(":");
				if (Integer.parseInt(testTime[0]) < 24 && Integer.parseInt(testTime[1]) < 60 
					&& Integer.parseInt(testTime[2]) < 60) {
				//get all with same arrivalTime
				for(int i=0;i<TripInfos.size();i++) {
	//System.out.println(count+=1);
					if((TripInfos.get(i).arrival_time).equals(arriveT)){
						stopsByArrival.add(TripInfos.get(i));
						
					}
				}
				Collections.sort(stopsByArrival,StopTimesInfo.sortByTripId);			
				
				return stopsByArrival;
				}else {
					System.out.println("STRING input is not in time format");
					return null;
				}
				
			}
			catch(Exception e) {
				System.out.println("String input is not in time format");
				return null;
			}
		
	}
	
	public static void main(String[] args) {
		
		new StopTimesInfo("stop_times.txt");
		
		List<TripInfo>myStops= StopTimesInfo.getStopsInfo("5:25:00");
		if(myStops!=null) {
			System.out.println(myStops.size());
		
			for(TripInfo s:myStops){
				System.out.println("stopheadsign:"+s.stop_headsign);
				System.out.printf("trip_id:%d,arrival_time:%s,departure_time:%s,stop_id:%d,stop_sequence:%d,"
						+ "stop_headsign:%s,pickup_type:%d,drop_off_type:%d,shape_dist_traveled:%f%n",
				s.trip_id,s.arrival_time,s.departure_time,s.stop_id,s.stop_sequence,s.stop_headsign,s.pickup_type,
				s.drop_off_type,s.shape_dist_traveled);
			}
		}
		else {
			System.out.println("error in string input");
		}
	}

	protected class TripInfo {
		protected int trip_id;
		protected Time arrival_time;
		protected Time departure_time;
		protected int stop_id;
		protected int stop_sequence;
		protected String stop_headsign;
		protected int pickup_type;
		protected int drop_off_type;
		protected double shape_dist_traveled;

		protected TripInfo(int trip_id, Time arrival_time, Time departure_time, int stop_id, int stop_sequence,
				String stop_headsign, int pickup_type, int drop_off_type, double shape_dist_traveled) {
			this.trip_id = trip_id;
			this.arrival_time = arrival_time;
			this.departure_time = departure_time;
			this.stop_id = stop_id;
			this.stop_sequence = stop_sequence;
			this.stop_headsign = stop_headsign;
			this.pickup_type = pickup_type;
			this.drop_off_type = drop_off_type;
			this.shape_dist_traveled = shape_dist_traveled;
		}
	}
}


