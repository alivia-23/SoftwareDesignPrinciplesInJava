import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        for (QuakeEntry qe : quakeData) {
            double depth = qe.getDepth();
            if (depth > minDepth && depth < maxDepth) {
                answer.add(qe);
            }
        }
        
        return answer;
    }
    
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> result = new ArrayList<>();
        System.out.println("read data for "+list.size()+ " quakes");
        
        result = filterByDepth(list, -10000.0, -5000.0);
        System.out.println("Find quakes with depth between -10000.0 and -5000.0");
        for (QuakeEntry qe : result) {
            System.out.println(qe);
        }
        
        System.out.println("Found "+result.size()+ " quakes that match that criteria");
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, 
    String phrase) {
        ArrayList<QuakeEntry> result = new ArrayList<>();
        for (QuakeEntry qe : quakeData) {
            if (where.equals("starts")) {
                if (qe.getInfo().startsWith(phrase)) {
                    result.add(qe);
                }
            } else if (where.equals("end")) {
                if (qe.getInfo().endsWith(phrase)) {
                    result.add(qe);
                }
            } else if (where.equals("any")) {
                if (qe.getInfo().contains(phrase)) {
                    result.add(qe);
                }
            }
        }
        return result;
    }
    
    public void quakesByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " +list.size()+ " quakes");
        //ArrayList<QuakeEntry> result = filterByPhrase(list, "end", "California");
        //ArrayList<QuakeEntry> result = filterByPhrase(list, "any", "Can");
        ArrayList<QuakeEntry> result = filterByPhrase(list, "start", "Explosion");
        
        for (QuakeEntry qe : result) {
            System.out.println(qe);
        }
        //System.out.println("Found " +result.size()+ " quakes that match California at end");
        //System.out.println("Found " +result.size()+ " quakes that match Can at any");
        System.out.println("Found " +result.size()+ " quakes that match Explosion at start");
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry qe : quakeData) {
            if (qe.getMagnitude() > magMin) {
               answer.add(qe); 
            }
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry qe : quakeData) {
            Location curLoc = qe.getLocation();
            if (curLoc.distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }

        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> bigQuakes = filterByMagnitude(list, 5.0);
        for (QuakeEntry qe : bigQuakes) {
            System.out.println(qe);
        }
        
        System.out.println("Found " +bigQuakes.size()+ " quakes that match the criteria");

    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        // String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        // Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);

        ArrayList<QuakeEntry> closeToCity = filterByDistanceFrom(list, 1000, city);
        
        for (QuakeEntry qe : closeToCity) {
            System.out.println((qe.getLocation().distanceTo(city)) + " " + qe.getInfo());
        }
        
        System.out.println("Found " + closeToCity.size() + " that match that criteria");
        
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
