//imports
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;


import javax.swing.JOptionPane;


public class Main {

    public ArrayList<Coordinates> path = new ArrayList<Coordinates>();
    public int interval = 10; // meters
    public int crusingAltitude = 1000; // meters
    public static void main(String[] args) {
        String streetAddress;
        int bearing;
        int offset;

        streetAddress = JOptionPane.showInputDialog("Please enter street address");
        bearing = Integer.parseInt(JOptionPane.showInputDialog("Please enter bearing"));
        offset = Integer.parseInt(JOptionPane.showInputDialog("Please enter offset in m (use integers)"));


        System.out.println("street address: " + streetAddress + ", Bearing : " + bearing + ", offset : " + offset + " m");

        getCoordinatesWithAddress(streetAddress);

    }

    public static Coordinates getCoordinatesWithAddress(String streetAddress) {
        String callUrl;
         callUrl = urlFormating(streetAddress);
         String callResponse;
         callResponse = curl(callUrl);
        System.out.println("callResponse :" + callResponse);

        return null;
    }

    public void findPath() {}

    public Coordinates offsetCalculator(Coordinates coords,int bearing,int distance){ 
        // https://www.igismap.com/formula-to-find-bearing-or-heading-angle-between-two-points-latitude-longitude/
        
        //new latitude 
        double ad = distance/6.3781*Math.pow(10,6 );
        double newLat = Math.asin(Math.sin(Math.toRadians(coords.latitude))*Math.cos(Math.toRadians(ad))
        +Math.cos(Math.toRadians(coords.latitude))*Math.sin(Math.toRadians(ad))*Math.cos(bearing));
        //new longitude
        double newLon = coords.latitude+Math.atan2(Math.sin(Math.toRadians(bearing))*Math.sin(Math.toRadians(ad))
        *Math.cos(Math.toRadians(coords.latitude)),Math.cos(Math.toRadians(ad))-Math.sin(Math.toRadians(coords.latitude))*Math.sin(newLat));

        newLat = Math.toDegrees(newLat);
        newLon = Math.toDegrees(newLon);
        Coordinates newCoords = new Coordinates(coords.elevation, newLat, newLon);

        return newCoords;
    }

    public void decoler(Coordinates startingPoint) {
        Coordinates current = startingPoint;
        while (current.elevation < startingPoint.elevation + crusingAltitude) {
            double newHight = current.elevation + interval;
            if (newHight >= startingPoint.elevation + crusingAltitude) {
            newHight = startingPoint.elevation + crusingAltitude;
            }
            current = new Coordinates(newHight, current.latitude, current.longitude);
            path.add(current);
        }
    }

    public void atterir(Coordinates endpoint, Coordinates landdingPoint) {
        Coordinates current = endpoint;
        while (current.elevation > landdingPoint.elevation) {
            double newHight = current.elevation - interval;
            if (newHight <= landdingPoint.elevation) {
                newHight = landdingPoint.elevation;
            }
            current = new Coordinates(newHight, current.latitude, current.longitude);
            path.add(current);
        }
    }

    public static double getAltitude(String lat, String lon) {
        String http  = "https://maps.googleapis.com/maps/api/elevation/json" + "?locations=" + lat + "+%2C" + lon + "&key=AIzaSyD3AlQuRcSyxOPDw49Mn2T846kH1G0j5zo";
        String res = curl(http);
        
        String[] both = res.split("elevation\" : ");
        String[] ans = both[1].split(",");
        return Double.parseDouble(ans[0]);
    }

    public double getReletiveAltitude() {
        return 0;
    }

    public static String curl(String http) {

		String stringUrl = http;
		URL url;
		try {
			url = new URL(stringUrl);
			URLConnection uc;
			uc = url.openConnection();

			uc.setRequestProperty("X-Requested-With", "Curl");

			BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));

			}
			String result = builder.toString();
            return result;
		} catch (IOException e) {
			e.printStackTrace();
            return null;
		}
	}

    public static String urlFormating(String streetAddress) {

        String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json?";
        String key = "&key=AIzaSyD3AlQuRcSyxOPDw49Mn2T846kH1G0j5zo";
        //String formatedAddress;
        int lastSpace = 0;
        int length = streetAddress.length();
        char tmpChar;

        streetAddress = streetAddress.replaceAll(",", "");
        streetAddress = streetAddress.replaceAll(" ", "%20");
        System.out.println(streetAddress);

        String finalUrl = baseUrl + "address="+ streetAddress + key;

        return finalUrl;
    }
}
