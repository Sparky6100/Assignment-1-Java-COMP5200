import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaxiCompany {
    private HashMap<String, Vehicle> vehicles;


    public TaxiCompany() {
        vehicles = new HashMap<>();
    }

    public void addVehicle(Vehicle aVehicle) {
        // TODO: Add the vehicle to the HashMap.
        vehicles.put(aVehicle.getId(), aVehicle);

    }


    public void listAllVehicles() {
        // TODO: List all of the vehicles.
        for (Vehicle vehicle : vehicles.values()) {
            System.out.println(vehicle);
        }
    }


    public void listAllBookings() {
        // TODO: List all of the bookings for all of the vehicles.
        for (Vehicle vehicle : vehicles.values()) {
            ArrayList<Booking> bookings = vehicle.getBookings();
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }


    }

    public int getVehicleTakings(String id) {
        // TODO: Return the takings for the vehicle with the given id.
        return vehicles.get(id).getTakings();
    }

    /**
     * Read the bookings from the given file.
     *
     * @param bookingsFilename The name of the file containing the bookings.
     * @throws IOException If there is an error reading the file.
     */
    public void readBookings(String bookingsFilename)
            throws IOException {
        Path filePath = Paths.get(bookingsFilename);
        List<String> lines = Files.readAllLines(filePath);
        for (String bookingDetails : lines) {
            bookingDetails = bookingDetails.trim();
            String[] parts = bookingDetails.split(",");
            String id = parts[0];
            String pickupLocation = parts[1];
            String destination = parts[2];
            // TODO: Create a Booking object.
            Booking booking = new Booking(id, pickupLocation, destination);
            vehicles.get(id).addBooking(booking);
            // Using the id, pickupLocation and destination.

        }
    }


    /**
     * Write a report of all of the vehicles.
     *
     * @param reportFilename The name of the file to write the report to.
     * @throws IOException If there is an error writing the file.
     */
    public void writeReport(String reportFilename)
            throws IOException {
        FileWriter writer = new FileWriter(reportFilename);
        writer.write("Taxi Company Report\n");
        int totalTakings = 0;
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle instanceof Taxi) {
                Taxi taxi = (Taxi) vehicle;
                writer.write(String.format("Taxi %s driven by %s had %d journeys and made £%d\n",
                        taxi.getId(), taxi.driver, taxi.getBookings().size(), taxi.getTakings()));
                totalTakings += taxi.getTakings();
            } else if (vehicle instanceof Shuttle) {
                Shuttle shuttle = (Shuttle) vehicle;
                writer.write(String.format("Shuttle %s had %d journeys and made £%d\n",
                        shuttle.getId(), shuttle.getBookings().size(), shuttle.getTakings()));
                totalTakings += shuttle.getTakings();
            }

        }
        writer.write("\n");
        writer.write(String.format("Total takings for the day £%d\n", totalTakings));
        writer.close();
    }
}