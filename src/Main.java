//imports
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


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
    }

    public Coordinates getCoordinatesWithAdress() {
        return null;
    }

    public void findPath() {}

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
}
