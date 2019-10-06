import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    /**
     * Reads user input and initialises specified number of servers.
     *
     * @param sc a Scanner object for reading user input
     * @return an ArrayList of servers as specified by the user
     */
    private static List<Server> initialiseServers(Scanner sc) {
        int numServers = sc.nextInt();
        List<Server> serversList = new ArrayList<>();

        for (int i = 0; i < numServers; i++) {
            serversList.add(new Server());
        }

        return serversList;
    }

    /**
     * Reads user input and initialises customers and their arrival times.
     *
     * @param sc a Scanner object for reading user input
     * @return an ArrayList of customers and their arrival times as specified by the user
     */
    private static List<Customer> initialiseCustomers(Scanner sc) {
        List<Customer> customersList = new ArrayList<>();

        while (sc.hasNextDouble()) {
            Customer c = new Customer(sc.nextDouble());
            customersList.add(c);
        }

        return customersList;
    }

    /**
     * Reads user input and executes events.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Server> serversList = initialiseServers(sc);
        List<Customer> customersList = initialiseCustomers(sc);

        DiscreteEventSimulator simulator = new DiscreteEventSimulator(serversList, customersList);
        simulator.run();
    }
}
