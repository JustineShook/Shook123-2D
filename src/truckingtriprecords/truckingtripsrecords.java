package truckingtriprecords;
import java.util.Scanner;

public class truckingtripsrecords {

    public static void main(String[] args) {
        
        try (Scanner sc = new Scanner(System.in)) {
            boolean exit = true;

            do {
                System.out.println("\n--- Main Menu ---");
                System.out.println("1. DRIVER");
                System.out.println("2. TRIPS");
                System.out.println("3. TRUCKS");
                System.out.println("4. REPORTS");
                System.out.println("5. EXIT");

                int act = 0;
                boolean validInput = false;

                // Strong validation for action selection
                while (!validInput) {
                    System.out.print("Enter Action (1-5): ");
                    String input = sc.next().trim();  // Trim whitespace from input
                    try {
                        act = Integer.parseInt(input);  // Parse input to integer
                        if (act >= 1 && act <= 5) {
                            validInput = true;
                        } else {
                            System.out.println("Please enter a number between 1 and 5.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a numeric value.");
                    }
                }

                switch (act) {
                    case 1:
                        Drivers dr = new Drivers();
                        dr.dTransaction();
                        break;

                    case 2:
                        Trips tp = new Trips();
                        tp.tripTransaction();
                        break;

                    case 3:
                        Trucks tr = new Trucks();
                        tr.trTransaction();
                        break;

                    case 4:
                        Reports rp = new Reports();
                        rp.viewIndividualRecord();
                        break;

                    case 5:
                        boolean confirmExit = false;
                        while (!confirmExit) {
                            System.out.print("Exiting... Are you sure? (yes/no): ");
                            String resp = sc.next().trim().toLowerCase();  // Trim and standardize case
                            switch (resp) {
                                case "yes":
                                    exit = false;
                                    confirmExit = true;
                                    break;
                                case "no":
                                    confirmExit = true;  // Stay in the loop if user says no
                                    break;
                                default:
                                    System.out.println("Please respond with 'yes' or 'no'.");
                                    break;
                            }
                        }
                        break;

                    default:
                        System.out.println("Please try again.");
                        break;
                }

            } while (exit);

            System.out.println("Goodbye!");
        }
    }
}
