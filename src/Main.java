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

    }

    public void atterir() {}

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
