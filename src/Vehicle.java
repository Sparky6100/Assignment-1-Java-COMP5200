import java.util.ArrayList;

/**
 * Model shared features of taxis and shuttles.
 */
public class Vehicle {
    private String id;
    private ArrayList<Booking> bookingsList;
    public Vehicle(String id) {
        this.id = id;
        bookingsList = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    /**
     * Return the daily takings for this vehicle.
     *
     * @return The takings.
     */
    public int getTakings() {
        throw new RuntimeException("getTakings must be overridden");
    }

    /**
     * Return details of this vehicle.
     *
     * @return A string representation of this vehicle.
     */
    public String getDetails() {
        return "getDetails must be overridden";
    }

    public ArrayList<Booking> getBookings() {
        return bookingsList;
    }

    public void addBooking(Booking booking) {
        bookingsList.add(booking);
    }
}