package truckingtriprecords;

import java.util.Scanner;

public class Trips {

    public void tripTransaction() {
        Scanner sc = new Scanner(System.in);
        String resp;

        do {
            System.out.println("\n--- TRIP Menu ---");
            System.out.println("1. ADD TRIP");
            System.out.println("2. VIEW TRIP");
            System.out.println("3. UPDATE TRIP");
            System.out.println("4. DELETE TRIP");
            System.out.println("5. BACK TO MAIN MENU");

            int action = getValidAction(sc);

            switch (action) {
                case 1:
                    addTrip();
                    break;
                case 2:
                    viewTrip();
                    break;
                case 3:
                    updateTrip();
                    break;
                case 4:
                    deleteTrip();
                    break;
                case 5:
                    return;  // Return to the main menu
                default:
                    System.out.println("Please try again.");
                    break;
            }

            System.out.print("Continue in TRIP menu? (yes/no): ");
            resp = sc.next().trim();

        } while (resp.equalsIgnoreCase("yes"));
    }

    private int getValidAction(Scanner sc) {
        int action = 0;
        while (true) {
            System.out.print("Enter Action (1-5): ");
            if (sc.hasNextInt()) {
                action = sc.nextInt();
                if (action >= 1 && action <= 5) {
                    break;
                } else {
                    System.out.println("Please enter a number between 1 and 5.");
                }
            } else {
                System.out.println("Please enter a numeric value.");
                sc.next();  // Clear invalid input
            }
        }
        return action;
    }

    // Add a new trip
    public void addTrip() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int driverId = getValidDriverId(sc);
        int truckId = getValidTruckId(sc);
        String cargo = getValidCargo(sc);  // Fix here
        String startLocation = getValidLocation(sc, "Start");
        String dropLocation = getValidLocation(sc, "Drop");
        String tripDate = getValidTripDate(sc);
        String status = getValidStatus(sc);

        String sql = "INSERT INTO tbl_trips (d_id, truck_id, cargo, start_location, drop_location, trip_date, trip_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, driverId, truckId, cargo, startLocation, dropLocation, tripDate, status);
    }

    private int getValidDriverId(Scanner sc) {
        int driverId = 0;
        while (true) {
            System.out.print("Enter Driver ID: ");
            if (sc.hasNextInt()) {
                driverId = sc.nextInt();
                if (driverId > 0) {  // ID should be a positive integer
                    break;
                } else {
                    System.out.println("Driver ID must be a positive integer.");
                }
            } else {
                System.out.println("Please enter a numeric ID.");
                sc.next();  // Clear invalid input
            }
        }
        return driverId;
    }

    private int getValidTruckId(Scanner sc) {
        int truckId = 0;
        while (true) {
            System.out.print("Enter Truck ID: ");
            if (sc.hasNextInt()) {
                truckId = sc.nextInt();
                if (truckId > 0) {  // ID should be a positive integer
                    break;
                } else {
                    System.out.println("Truck ID must be a positive integer.");
                }
            } else {
                System.out.println("Please enter a numeric ID.");
                sc.next();  // Clear invalid input
            }
        }
        return truckId;
    }

    private String getValidLocation(Scanner sc, String locationType) {
        String location;
        while (true) {
            System.out.print(locationType + " Location: ");
            location = sc.nextLine().trim();
            if (!location.isEmpty()) {
                break;
            } else {
                System.out.println(locationType + " Location must not be empty.");
            }
        }
        return location;
    }

    private String getValidTripDate(Scanner sc) {
        String tripDate;
        while (true) {
            System.out.print("Trip Date (YYYY-MM-DD): ");
            tripDate = sc.nextLine().trim();
            if (tripDate.matches("\\d{4}-\\d{2}-\\d{2}")) { // Simple date validation
                break;
            } else {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        return tripDate;
    }

    private String getValidStatus(Scanner sc) {
        String status;
        while (true) {
            System.out.print("Trip Status (Completed/In Progress/Cancelled): ");
            status = sc.nextLine().trim();
            if (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("In Progress") || status.equalsIgnoreCase("Cancelled")) {
                break;
            } else {
                System.out.println("Status must be 'Completed', 'In Progress', or 'Cancelled'.");
            }
        }
        return status;
    }

    // View all trips
    private void viewTrip() {
        String qry = "SELECT * FROM tbl_trips";
        String[] hdrs = {"Trip ID", "Driver ID", "Truck ID", "Cargo Description", "Start Location", "Drop Location", "Trip Date", "Trip Status"};
        String[] clms = {"trip_id", "d_id", "truck_id", "cargo", "start_location", "drop_location", "trip_date", "trip_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    // Update an existing trip
    private void updateTrip() {
        Scanner sc = new Scanner(System.in);
        int tripId = getValidTripId(sc);

        int driverId = getValidDriverId(sc);
        int truckId = getValidTruckId(sc);
        String startLocation = getValidLocation(sc, "Start");
        String dropLocation = getValidLocation(sc, "Drop");
        String tripDate = getValidTripDate(sc);
        String status = getValidStatus(sc);

        String qry = "UPDATE tbl_trips SET d_id = ?, truck_id = ?, cargo = ?, start_location = ?, drop_location = ?, trip_date = ?, trip_status = ? WHERE trip_id = ?";
        config conf = new config();
        conf.updateRecord(qry, driverId, truckId, startLocation, dropLocation, tripDate, status, tripId);
    }

    // Delete an existing trip
    private void deleteTrip() {
        Scanner sc = new Scanner(System.in);
        int tripId = getValidTripId(sc);

        String qry = "DELETE FROM tbl_trips WHERE trip_id = ?";
        config conf = new config();
        conf.deleteRecord(qry, tripId);
    }

    private int getValidTripId(Scanner sc) {
        int tripId = 0;
        while (true) {
            System.out.print("Enter Trip ID: ");
            if (sc.hasNextInt()) {
                tripId = sc.nextInt();
                if (tripId > 0) { 
                    break;
                } else {
                    System.out.println("Trip ID must be a positive integer.");
                }
            } else {
                System.out.println("Please enter a numeric ID.");
                sc.next();  // Clear invalid input
            }
        }
        return tripId;
    }

    private String getValidCargo(Scanner sc) {
        String cargo;
        while (true) {
            System.out.print("Cargo Description: ");
            cargo = sc.nextLine().trim();  // Get the cargo description
            if (!cargo.isEmpty()) {
                break;  // Valid input, exit the loop
            } else {
                System.out.println("Cargo Description cannot be empty.");
            }
        }
        return cargo;
    }
}
