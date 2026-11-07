import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileIO {
    //TODO: Load events and customers from CSV files

    public static ArrayList<Customer> readCustomers(Path filePath) {

        ArrayList<Customer> customers = new ArrayList<>();

        // try with resources
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.toFile()))) {

            //skip first line
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();

                String[] tokens = line.split(",");

                // safe skip
                if (tokens.length != 4) {
                    continue;
                }

                // try to convert the input to the corresponding java type
                // if an exception occurs, just skip this line
                try {
                    int customerID = Integer.parseInt(tokens[0].trim());
                    String customerName = tokens[1].trim();
                    int balance = Integer.parseInt(tokens[2].trim());
                    MembershipType membershipType = MembershipType.valueOf(tokens[3].trim());

                    if (membershipType == MembershipType.REGULAR) {
                        customers.add(new Customer(customerID, customerName, balance));
                    } else if (membershipType == MembershipType.PREMIUM) {
                        customers.add(new PremiumCustomer(customerID, customerName, balance));
                    }

                } catch (IllegalArgumentException e) {
                    System.err.println(e);
                }

            }

        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        return customers;
    }

    public static ArrayList<Event> readEvents(Path filePath) {

        ArrayList<Event> events = new ArrayList<>();

        // try with resources
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.toFile()))) {

            //skip first line
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();

                String[] tokens = line.split(",");

                // safe skip
                if (tokens.length != 5) {
                    continue;
                }

                // try to convert the input to the corresponding java type
                // if an exception occurs, just skip this line
                try {
                    int eventID = Integer.parseInt(tokens[0].trim());
                    String eventName = tokens[1].trim();
                    EventCategory eventCategory = EventCategory.valueOf(tokens[2].trim());
                    int capacity = Integer.parseInt(tokens[3].trim());
                    int ticketPrice = Integer.parseInt(tokens[4].trim());

                    events.add(new Event(eventID, eventName, eventCategory, capacity, ticketPrice));

                } catch (IllegalArgumentException e) {
                    System.err.println(e);
                }

            }

        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        return events;
    }
}
