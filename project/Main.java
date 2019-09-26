import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Iterator;

enum State {
    ARRIVES, SERVED, WAITS, LEAVES, DONE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

public class Main {
    private static List<Customer> readCustomers(Scanner sc) {
        List<Customer> customersList = new ArrayList<>();
        
        while (sc.hasNextDouble()) {
            Customer c = new Customer(sc.nextDouble());
            c.setState(State.ARRIVES);
            customersList.add(c); 
        }

        return customersList;
    }

    private static void handleArrivals(Queue<Customer> customersQueue, List<Customer> customersList) { 
        System.out.println("# Adding arrivals");

        for (Customer c : customersList) {
            customersQueue.add(c);
        }

        printQueue(customersQueue);
    } 

    private static void handleQueue(Queue<Customer> customersQueue) {
        Server server = new Server();

        while (customersQueue.peek() != null) {
            Customer c = customersQueue.poll();
            System.out.println("# Get next event: " + c);

            if (c.getState() != State.LEAVES && c.getState() != State.DONE) {
                server.serveCustomer(c);
                customersQueue.add(c);
            }

            printQueue(customersQueue);
        }

       // for (Customer customer : customersList) {
       //     customer.setState(State.ARRIVES);
       //     System.out.println(customer);

       //     server.serveCustomer(customer);
       //     System.out.println(customer);
       // }
    }

    private static void printQueue(Queue<Customer> customersQueue) {
        Iterator pqIterator = customersQueue.iterator();

        while (pqIterator.hasNext()) {
            System.out.println(pqIterator.next());
        }
        
        System.out.println("");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Customer> customersList = readCustomers(sc);

        Queue<Customer> customersQueue = new PriorityQueue<>(customersList.size(), new CustomerComparator());
        handleArrivals(customersQueue, customersList);

        handleQueue(customersQueue);
        System.out.println("Number of customers: " + Customer.getNumCustomers());
    }
}
