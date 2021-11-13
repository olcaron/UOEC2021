public class Coordinates {
    public double elevation;
    public double latitude;
    public double longitude;

    public Coordinates(double elevation, double latitude, double longitude) {
        this.elevation = elevation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toString() {
        String s = "Elevation: " + elevation + " latitude: " + latitude + " longitude: " + longitude;
    }
}
