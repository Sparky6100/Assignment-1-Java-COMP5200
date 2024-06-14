import java.util.Arrays;

/**
 * Model a shuttle on a circular route.
 */
public class Shuttle extends Vehicle {
    /**
     * Constructor for objects of class Shuttle.
     *
     * @param id The shuttle's ID.
     * @param pricePerStop The price per stop.
     * @param route The route.
     */
    int pricePerStop;
    String[] route;

    public Shuttle(String id, int pricePerStop, String[] route) {
        super(id);
        this.pricePerStop = pricePerStop;
        this.route = route;
    }

    public String toString() {
        return "Shuttle " + getId() + " has the route" + ": " + Arrays.toString(route);
    }

    /**
     * •	For shuttles, this is a little more complicated.  The price per journey is based on how many stops the journey covers. For example: Suppose the price per stop was £1 and the route is:
     *
     * Canterbury West  Canterbury East  Canterbury Bus Station  University of Kent
     *
     * A booking from Canterbury West to the University of Kent is 3 stops, so the price for that journey was £3.  If the price per stop had been £2, the price would be £6.  The price per stop is always a whole number.
     *
     * That's a straightforward example, but what if the person departed from Canterbury Bus Station and arrived at Canterbury East? That's a little more difficult to work out as you get to the end of the list and then have to continue from the beginning of the list because it's a circular route.  So, the bus would go to the University of Kent first, then Canterbury West, then Canterbury East.  So this journey is also 3 stops.
     *
     */

    public int getTakings() {
        int takings = 0;
        for (Booking booking : getBookings()) {
            int start = Arrays.asList(route).indexOf(booking.pickupLocation());
            int end = Arrays.asList(route).indexOf(booking.destination());
            int stops = Math.abs(start - end);
            if (start > end) {
                stops = route.length - stops;
            }
            takings += pricePerStop * stops;
        }
        return takings;
    }
}