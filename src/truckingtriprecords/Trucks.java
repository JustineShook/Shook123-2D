package truckingtriprecords;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trucks {

    public void trTransaction() {
        Scanner sc = new Scanner(System.in);
        String resp;

        do {
            System.out.println("\n--- TRUCK Menu ---");
            System.out.println("1. ADD TRUCK");
            System.out.println("2. VIEW TRUCK");
            System.out.println("3. UPDATE TRUCK");
            System.out.println("4. DELETE TRUCK");
            System.out.println("5. BACK TO MAIN MENU");

            int action = getValidAction(sc);

            switch (action) {
                case 1:
                    addTruck();
                    break;
                case 2:
                    viewTruck();
                    break;
                case 3:
                    updateTruck();
                    break;
                case 4:
                    deleteTruck();
                    break;
                case 5:
                    return;  // Return to the main menu
                default:
                    System.out.println("Please try again.");
                    break;
            }

            System.out.print("Continue in TRUCK menu? (yes/no): ");
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

    public void addTruck() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String plateNumber = getValidPlateNumber(sc);
        String model = getValidModel(sc);
        String status = getValidStatus(sc);

        String sql = "INSERT INTO tbl_trucks (truck_plate_number, truck_model, truck_status) VALUES (?, ?, ?)";
        conf.addRecord(sql, plateNumber, model, status);
    }

    private String getValidPlateNumber(Scanner sc) {
        String plateNumber;
        while (true) {
            System.out.print("Truck Plate Number: ");
            plateNumber = sc.nextLine().trim();
            if (isValidPlateNumber(plateNumber)) {
                break;
            } else {
                System.out.println("Error: Invalid plate number format. It should be alphanumeric (with optional spaces and hyphens).");
            }
        }
        return plateNumber;
    }

    private boolean isValidPlateNumber(String plateNumber) {
        String regex = "^[A-Za-z0-9\\-\\s]+$"; // Alphanumeric with optional spaces and hyphens
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(plateNumber);
        return matcher.matches();
    }

    private String getValidModel(Scanner sc) {
        String model;
        while (true) {
            System.out.print("Truck Model: ");
            model = sc.nextLine().trim();
            if (!model.isEmpty() && model.matches("[A-Za-z0-9\\s]+")) { // Allows alphanumeric and spaces
                break;
            } else {
                System.out.println("Error: Model must not be empty and can only contain letters, numbers, and spaces.");
            }
        }
        return model;
    }

    private String getValidStatus(Scanner sc) {
        String status;
        while (true) {
            System.out.print("Truck Status (Active/Inactive): ");
            status = sc.nextLine().trim();
            if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive")) {
                break;
            } else {
                System.out.println("Status must be either 'Active' or 'Inactive'.");
            }
        }
        return status;
    }

    private void viewTruck() {
        String qry = "SELECT * FROM tbl_trucks";
        String[] hdrs = {"ID", "Plate Number", "Model", "Status"};
        String[] clms = {"truck_id", "truck_plate_number", "truck_model", "truck_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateTruck() {
        Scanner sc = new Scanner(System.in);
        int id = getValidTruckId(sc);

        String newPlateNumber = getValidPlateNumber(sc);
        String newModel = getValidModel(sc);
        String newStatus = getValidStatus(sc);

        String qry = "UPDATE tbl_trucks SET truck_plate_number = ?, truck_model = ?, truck_status = ? WHERE truck_id = ?";
        config conf = new config();
        conf.updateRecord(qry, newPlateNumber, newModel, newStatus, id);
    }

    private void deleteTruck() {
        Scanner sc = new Scanner(System.in);
        int id = getValidTruckId(sc);

        String qry = "DELETE FROM tbl_trucks WHERE truck_id = ?";
        config conf = new config();
        conf.deleteRecord(qry, id);
    }

    private int getValidTruckId(Scanner sc) {
        int id = 0;
        while (true) {
            System.out.print("Enter Truck ID: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                if (id > 0) {  // ID should be a positive integer
                    break;
                } else {
                    System.out.println("ID must be a positive integer.");
                }
            } else {
                System.out.println("Please enter a numeric ID.");
                sc.next();  // Clear invalid input
            }
        }
        return id;
    }

    
}