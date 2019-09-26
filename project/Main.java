import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;

enum State {
    ARRIVES, SERVED, WAITS, LEAVES, DONE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

public class Main {
    private static int customersThatLeft = 0;

    private static List<Server> initialiseServers(Scanner sc) {
        int numServers = sc.nextInt();
        List<Server> serversList = new ArrayList<>();

        for (int i = 0; i < numServers; i++) {
            serversList.add(new Server());
        }

        return serversList;
    }

    private static List<Customer> initialiseCustomers(Scanner sc) {
        List<Customer> customersList = new ArrayList<>();

        while (sc.hasNextDouble()) {
            Customer c = new Customer(sc.nextDouble());
            customersList.add(c);
        }

        return customersList;
    }

    private static void queueArrivalEvents(Queue<Event> eventQueue, List<Customer> customersList) {
        for (Customer c : customersList) {
            eventQueue.add(new Event(c.getArrivalTime(), c));
        }
    }

    private static void executeEvents(Queue<Event> eventQueue, List<Server> serversList) {
        while (eventQueue.peek() != null) {
            Event e = eventQueue.poll();
            System.out.println(e);

            Customer customer = e.getCustomer();

            if (customer.getState() == State.ARRIVES) {
                boolean served = false;

                // Check for idle servers
                for (Server server : serversList) {
                    if (server.isIdle()) {
                        customer.setState(State.SERVED);
                        // Create a new SERVED event
                        Event servedEvent = new Event(customer.getArrivalTime(), customer, server);
                        served = true;
                        eventQueue.add(servedEvent);
                        break;
                    }
                }

                // If no idle servers, check for servers with empty queue
                if (!served) {
                    for (Server server : serversList) {
                        if (server.hasEmptyQueue()) {
                            customer.setState(State.WAITS);
                            // Create a new WAITS event
                            Event waitsEvent = new Event(customer.getArrivalTime(), customer, server);
                            served = true;
                            eventQueue.add(waitsEvent);
                            break;
                        }
                    }
                }

                // If no servers with empty queue, customer leaves
                if (!served) {
                    customer.setState(State.LEAVES);
                    // Create a new LEAVES event
                    Event leavesEvent = new Event(customer.getArrivalTime(), customer);
                    eventQueue.add(leavesEvent);
                    customersThatLeft++;
                }
            } else if (customer.getState() == State.WAITS) {
                customer.setState(State.SERVED);
                Server server = e.getServer();
                server.queueCustomer(customer);
                // Create a new SERVED event
                Event servedEvent = new Event(server.getNextServiceTime(), customer, server); 
                eventQueue.add(servedEvent);
            } else if (customer.getState() == State.SERVED) {
                customer.setState(State.DONE);
                Server server = e.getServer();
                server.serveCustomer(customer, e.getEventTime());
                // Create a new DONE event
                Event doneEvent = new Event(server.getNextServiceTime(), customer, server);
                eventQueue.add(doneEvent);
            } else if (customer.getState() == State.DONE) {
                Server server = e.getServer();
                server.endService(customer);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Server> serversList = initialiseServers(sc);
        List<Customer> customersList = initialiseCustomers(sc);

        Queue<Event> eventQueue = new PriorityQueue<>(customersList.size(), new EventComparator());
        queueArrivalEvents(eventQueue, customersList);
        executeEvents(eventQueue, serversList);

        System.out.println("[" + String.format("%.3f", Server.getAverageWaitTime()) + " " + Server.getCustomersServed() + " " + customersThatLeft + "]");
    }
}
