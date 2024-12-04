package truckingtriprecords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Reports {

    public void viewIndividualRecord() {
        Scanner sc = new Scanner(System.in);
        String resp;

        do {
            System.out.println("\n--- VIEW INDIVIDUAL RECORD ---");
            System.out.println("1. VIEW DRIVER");
            System.out.println("2. VIEW TRUCK");
            System.out.println("3. VIEW TRIP");
            System.out.println("4. BACK TO MAIN MENU");

            int action = getValidAction(sc);

            switch (action) {
                case 1:
                    viewAllDrivers();
                    break;
                case 2:
                    viewAllTrucks();
                    break;
                case 3:
                    viewAllTrips();
                    break;
                case 4:
                    return;  // Return to the main menu
                default:
                    System.out.println("Please try again.");
                    break;
            }

            System.out.print("Continue in REPORTS menu? (yes/no): ");
            resp = sc.next().trim();

        } while (resp.equalsIgnoreCase("yes"));
    }

    private int getValidAction(Scanner sc) {
        int action = 0;
        while (true) {
            System.out.print("Enter Action (1-4): ");
            if (sc.hasNextInt()) {
                action = sc.nextInt();
                if (action >= 1 && action <= 4) {
                    break;
                } else {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            } else {
                System.out.println("Please enter a numeric value.");
                sc.next();  // Clear invalid input
            }
        }
        return action;
    }

    private void viewAllDrivers() {
        String qry = "SELECT * FROM tbl_drivers";
        String[] hdrs = {"ID", "First Name", "Last Name", "License", "Status"};
        String[] clms = {"d_id", "d_fname", "d_lname", "d_license", "d_status"};

        // Establishing connection and querying database directly in Reports class
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(qry);
             ResultSet rs = pstmt.executeQuery()) {

            // Print the headers dynamically
            StringBuilder headerLine = new StringBuilder();
            headerLine.append("--------------------------------------------------------------------------------------------------------------------\n| ");
            for (String header : hdrs) {
                headerLine.append(String.format("%-20s | ", header)); // Adjust formatting as needed
            }
            headerLine.append("\n-------------------------------------------------------------------------------------------------------------------");

            System.out.println(headerLine.toString());

            // Print the rows dynamically based on the provided column names
            while (rs.next()) {
                StringBuilder row = new StringBuilder("| ");
                for (String colName : clms) {
                    String value = rs.getString(colName);
                    row.append(String.format("%-20s | ", value != null ? value : "")); // Adjust formatting
                }
                System.out.println(row.toString());
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }

    private void viewAllTrucks() {
        String qry = "SELECT * FROM tbl_trucks";
        String[] hdrs = {"ID", "Plate Number", "Model", "Status"};
        String[] clms = {"truck_id", "truck_plate_number", "truck_model", "truck_status"};

        // Establishing connection and querying database directly in Reports class
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(qry);
             ResultSet rs = pstmt.executeQuery()) {

            // Print the headers dynamically
            StringBuilder headerLine = new StringBuilder();
            headerLine.append("--------------------------------------------------------------------------------------------------------------------\n| ");
            for (String header : hdrs) {
                headerLine.append(String.format("%-20s | ", header)); // Adjust formatting as needed
            }
            headerLine.append("\n-------------------------------------------------------------------------------------------------------------------");

            System.out.println(headerLine.toString());

            // Print the rows dynamically based on the provided column names
            while (rs.next()) {
                StringBuilder row = new StringBuilder("| ");
                for (String colName : clms) {
                    String value = rs.getString(colName);
                    row.append(String.format("%-20s | ", value != null ? value : "")); // Adjust formatting
                }
                System.out.println(row.toString());
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }

    private void viewAllTrips() {
        String qry = "SELECT * FROM tbl_trips";
        String[] hdrs = {"Trip ID", "Driver ID", "Truck ID", "Cargo", "Start Location", "Drop Location", "Trip Date", "Status"};
        String[] clms = {"trip_id", "d_id", "truck_id", "cargo", "start_location", "drop_location", "trip_date", "trip_status"};

        // Establishing connection and querying database directly in Reports class
        try (Connection conn = config.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(qry);
             ResultSet rs = pstmt.executeQuery()) {

            // Print the headers dynamically
            StringBuilder headerLine = new StringBuilder();
            headerLine.append("--------------------------------------------------------------------------------------------------------------------\n| ");
            for (String header : hdrs) {
                headerLine.append(String.format("%-20s | ", header)); // Adjust formatting as needed
            }
            headerLine.append("\n-------------------------------------------------------------------------------------------------------------------");

            System.out.println(headerLine.toString());

            // Print the rows dynamically based on the provided column names
            while (rs.next()) {
                StringBuilder row = new StringBuilder("| ");
                for (String colName : clms) {
                    String value = rs.getString(colName);
                    row.append(String.format("%-20s | ", value != null ? value : "")); // Adjust formatting
                }
                System.out.println(row.toString());
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }
}
