package truckingtriprecords;

import java.util.Scanner;
import truckingtriprecords.config;

public class Drivers {

    public void dTransaction() {
        Scanner sc = new Scanner(System.in);
        String resp;

        do {
            System.out.println("\n--- DRIVER Menu ---");
            System.out.println("1. ADD DRIVER");
            System.out.println("2. VIEW DRIVER");
            System.out.println("3. UPDATE DRIVER");
            System.out.println("4. DELETE DRIVER");
            System.out.println("5. BACK TO MAIN MENU");

            int action = getValidAction(sc);

            switch (action) {
                case 1:
                    addDriver();
                    break;
                case 2:
                    viewDriver();
                    break;
                case 3:
                    updateDriver();
                    break;
                case 4:
                    deleteDriver();
                    break;
                case 5:
                    return;  // Return to the main menu
                default:
                    System.out.println("Please try again.");
                    break;
            }

            System.out.print("Continue in DRIVER menu? (yes/no): ");
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
                System.out.println("Please enter a number.");
                sc.next();  // Clear invalid input
            }
        }
        return action;
    }

    public void addDriver() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String fname = getValidName(sc, "First Name");
        String lname = getValidName(sc, "Last Name");
        String license = getValidLicense(sc);
        String status = getValidStatus(sc);

        String sql = "INSERT INTO tbl_drivers (d_fname, d_lname, d_license, d_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, license, status);
    }

    private String getValidName(Scanner sc, String field) {
        String name;
        while (true) {
            System.out.print("Driver " + field + ": ");
            name = sc.nextLine().trim();
            if (name.matches("[A-Za-z]+")) {  // Only letters allowed
                break;
            } else {
                System.out.println("Error: " + field + " must contain only letters.");
            }
        }
        return name;
    }

    private String getValidLicense(Scanner sc) {
        String license;
        while (true) {
            System.out.print("Driver License: ");
            license = sc.nextLine().trim();
            if (license.matches("[A-Za-z0-9]{8,15}")) { 
                break;
            } else {
                System.out.println("License must be alphanumeric and between 8 to 15 characters.");
            }
        }
        return license;
    }

    private String getValidStatus(Scanner sc) {
        String status;
        while (true) {
            System.out.print("Driver Status: ");
            status = sc.nextLine().trim();
            if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive")) {
                break;
            } else {
                System.out.println("Status must be 'Active' or 'Inactive'.");
            }
        }
        return status;
    }

    private void viewDriver() {
        String qry = "SELECT * FROM tbl_drivers";
        String[] hdrs = {"ID", "First Name", "Last Name", "License", "Status"};
        String[] clms = {"d_id", "d_fname", "d_lname", "d_license", "d_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateDriver() {
        Scanner sc = new Scanner(System.in);
        int id = getValidDriverId(sc);

        String nfname = getValidName(sc, "New First Name");
        String nlname = getValidName(sc, "New Last Name");
        String nlicense = getValidLicense(sc);
        String nstatus = getValidStatus(sc);

        String qry = "UPDATE tbl_drivers SET d_fname = ?, d_lname = ?, d_license = ?, d_status = ? WHERE d_id = ?";
        config conf = new config();
        conf.updateRecord(qry, nfname, nlname, nlicense, nstatus, id);
    }

    private void deleteDriver() {
        Scanner sc = new Scanner(System.in);
        int id = getValidDriverId(sc);

        String qry = "DELETE FROM tbl_drivers WHERE d_id = ?";
        config conf = new config();
        conf.deleteRecord(qry, id);
    }

    private int getValidDriverId(Scanner sc) {
        int id = 0;
        while (true) {
            System.out.print("Enter Driver ID: ");
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