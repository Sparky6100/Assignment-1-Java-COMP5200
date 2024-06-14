public record Booking(String id, String pickupLocation, String destination) {

    public String toString() {
        return id + "," + pickupLocation + "," + destination;
    }
}

