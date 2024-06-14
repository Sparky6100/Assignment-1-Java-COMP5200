import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic test class for assignment 1.
 * Use this to test the basic functionality of your code but don't
 * assume that it is a thorough set of tests.
 * @author d.j.barnes@kent.ac.uk
 */
public class TaxiCompanyTest
{
    private Taxi aTaxi;
    private Shuttle aShuttle;
    private final String taxiID = "T20";
    private final String taxiDriver = "A driver";
    private final int taxiCost = 50;
    private final String shuttleID = "S20";
    private final int pricePerStop = 50;
    private final String[] route = { "A", "B", "C", "D", "E", "F", "G" };
    private final List<Booking> taxiBookings = List.of(
            new Booking(taxiID, "A", "B"),
            new Booking(taxiID, "C", "D"),
            new Booking(taxiID, "E", "F"),
            new Booking(taxiID, "G", "H")
    );
    private final List<Booking> shuttleBookings = List.of(
            new Booking(shuttleID, "B", "C"),
            new Booking(shuttleID, "C", "G"),
            new Booking(shuttleID, "F", "A"),
            new Booking(shuttleID, "E", "D")
    );

    @BeforeEach
    public void setup()
    {
        aTaxi = new Taxi(taxiID, taxiDriver, taxiCost);
        taxiBookings.forEach(aBooking -> aTaxi.addBooking(aBooking));
        aShuttle = new Shuttle(shuttleID, pricePerStop, route);
        shuttleBookings.forEach(aBooking -> aShuttle.addBooking(aBooking));
    }

    /**
     * Test the toString method of Booking.
     */
    @Test
    public void testBookingToString()
    {
        String id = "ABC";
        String start = "start";
        String finish = "finish";
        Booking aBooking = new Booking(id, start, finish);
        String expected = String.format("%s,%s,%s", id, start, finish);
        String actual = aBooking.toString();
        assertEquals(expected, actual);
    }

    /**
     * Test the calculation of taxi takings.
     */
    @Test
    public void testTaxiTakings()
    {
        int expected = taxiCost * taxiBookings.size();
        int actual = aTaxi.getTakings();
        assertEquals(expected, actual, "Incorrect calculation of taxi takings.");
    }

    /**
     * Test the calculation of shuttle takings.
     */
    @Test
    public void testShuttleTakings()
    {
        int expected = pricePerStop * (1 + 4 + 2 + 6);
        int actual = aShuttle.getTakings();
        assertEquals(expected, actual, "Incorrect calculation of shuttle takings.");
    }

    /**
     * Test the taxi company's report.
     */
    @Test
    public void testReport()
    {
        Random rand = new Random();
        String reportFile = String.format("report%d.txt", rand.nextInt(1000));
        TaxiCompany company = new TaxiCompany();
        company.addVehicle(aTaxi);
        company.addVehicle(aShuttle);
        try {
            company.writeReport(reportFile);
        }
        catch(IOException ex) {
            fail("Failed to write report to file " + reportFile);
        }
        try {
            Path filePath = Paths.get(reportFile);
            List<String> lines = Files.readAllLines(filePath);
            assertEquals(5, lines.size(), "The report should contain five lines");

            Files.deleteIfExists(new File(reportFile).toPath());
            List<String> actualReport = new ArrayList<>(lines.stream().map(String::trim).toList());
            assertEquals("Taxi Company Report", actualReport.get(0), "First line of the report.");

            actualReport.remove(0);
            int shuttleTakings = pricePerStop * (1 + 4 + 2 + 6);
            int taxiTakings = taxiCost * taxiBookings.size();
            int expectedTakings = shuttleTakings + taxiTakings;
            String expectedLastLine = String.format("Total takings for the day £%d", expectedTakings);
            assertEquals(expectedLastLine, actualReport.get(actualReport.size() - 1), "Last line of the report.");

            actualReport.remove(actualReport.size() - 1);
            assertEquals("", actualReport.get(actualReport.size() - 1),
                    "There should be a blank line before the total takings line.");

            actualReport.remove(actualReport.size() - 1);
            String shuttleLine = String.format("Shuttle %s had %d journeys and made £%d",
                    shuttleID, shuttleBookings.size(), shuttleTakings);
            String taxiLine = String.format("Taxi %s driven by %s had %d journeys and made £%d",
                    taxiID, taxiDriver, taxiBookings.size(), taxiTakings);
            List<String> expected;
            if(actualReport.get(0).startsWith("T")) {
                expected = List.of(taxiLine, shuttleLine);
            }
            else {
                expected = List.of(shuttleLine, taxiLine);
            }
            for(int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(i), actualReport.get(i), String.format(
                        "Mismatch in line %d of the report", (i+1)));
            }
        }
        catch(IOException e) {
            fail("Could not read the output file that was written by the processor.");
        }
    }
}