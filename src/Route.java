import java.util.Objects;

public class Route {
    private String id;
    private double distance; // Случайная величина, задаваемая при создании маршрута
    private int popularity;
    private boolean isFavorite;
    private MyList<String> locationPoints;

    public Route(String id, double distance, int popularity, boolean isFavorite, MyList<String> locationPoints) {
        this.id = id;
        this.distance = distance;
        this.popularity = popularity;
        this.isFavorite = isFavorite;
        this.locationPoints = locationPoints;
    }

    public String getId() {
        return id;
    }

    public double getDistance() {
        return distance;
    }

    public int getPopularity() {
        return popularity;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public MyList<String> getLocationPoints() {
        return locationPoints;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Double.compare(route.distance, distance) == 0 &&
                Objects.equals(locationPoints.get(0), route.locationPoints.get(0)) &&
                Objects.equals(locationPoints.get(locationPoints.size() - 1), route.locationPoints.get(route.locationPoints.size() - 1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationPoints.get(0), locationPoints.get(locationPoints.size() - 1), distance);
    }
}