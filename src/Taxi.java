/**
 * Model a Taxi.
 */
public class Taxi extends Vehicle {
    /**
     * Constructor for objects of class Taxi.
     *
     * @param id The taxi's ID.
     * @param driver The taxi's driver.
     * @param pricePerJourney The price per journey.
     */
    String driver;
    int pricePerJourney;

    public Taxi(String id, String driver, int pricePerJourney) {
        super(id);
        this.driver = driver;
        this.pricePerJourney = pricePerJourney;
    }

    public String toString() {
        return "Taxi " + getId() + " driven by " + driver;
    }

    public int getTakings() {
        return pricePerJourney * getBookings().size();
    }

}