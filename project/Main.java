import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;

public class Main {
    private static int customersThatLeft = 0;

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
     * Generates arrival events from a list of customers and queues them.
     *
     * @param eventQueue the queue of existing events
     * @param customersList the list of customers set to arrive
     */
    private static void queueArrivalEvents(Queue<Event> eventQueue, List<Customer> customersList) {
        for (Customer c : customersList) {
            eventQueue.add(new Event(c.getArrivalTime(), c));
        }
    }

    /**
     * Retrieves and executes events from the head of the queue until the queue is empty.
     * Handles events based on the customer's state. Managers which customers will be served
     * by which servers.
     *
     * @param eventQueue the queue of existing events
     * @param serversList the list of all servers
     */
    private static void executeEvents(Queue<Event> eventQueue, List<Server> serversList) {
        while (eventQueue.peek() != null) {
            Event e = eventQueue.poll();
            System.out.println(e);

            Customer c = e.getCustomer();

            if (c.getState() == State.ARRIVES) {
                boolean served = false;

                // Check for idle servers
                for (Server server : serversList) {
                    if (server.isIdle()) {
                        c.setState(State.SERVED);
                        // Create a new SERVED event
                        Event servedEvent = new Event(c.getArrivalTime(), c, server);
                        served = true;
                        eventQueue.add(servedEvent);
                        break;
                    }
                }

                // If no idle servers, check for servers with empty queue
                if (!served) {
                    for (Server server : serversList) {
                        if (server.hasEmptyQueue()) {
                            c.setState(State.WAITS);
                            // Create a new WAITS event 
                            Event waitsEvent = new Event(c.getArrivalTime(), c, server);
                            served = true;
                            eventQueue.add(waitsEvent);
                            break;
                        }
                    }
                }

                // If no servers with empty queue, customer leaves
                if (!served) {
                    c.setState(State.LEAVES);
                    // Create a new LEAVES event
                    Event leavesEvent = new Event(c.getArrivalTime(), c);
                    eventQueue.add(leavesEvent);
                    customersThatLeft++;
                }
            } else if (c.getState() == State.WAITS) {
                c.setState(State.SERVED);
                Server server = e.getServer();
                server.queueCustomer(c);
                // Create a new SERVED event
                Event servedEvent = new Event(server.getNextServiceTime(), c, server); 
                eventQueue.add(servedEvent);
            } else if (c.getState() == State.SERVED) {
                c.setState(State.DONE);
                Server server = e.getServer();
                server.serveCustomer(c, e.getEventTime());
                // Create a new DONE event
                Event doneEvent = new Event(server.getNextServiceTime(), c, server);
                eventQueue.add(doneEvent);
            } else if (c.getState() == State.DONE) {
                Server server = e.getServer();
                server.endService();
            }
        }
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

        Queue<Event> eventQueue = new PriorityQueue<>(customersList.size(), new EventComparator());
        queueArrivalEvents(eventQueue, customersList);
        executeEvents(eventQueue, serversList);

        System.out.println("[" + String.format("%.3f", Server.getAverageWaitTime()) + 
            " " + Server.getCustomersServed() + " " + customersThatLeft + "]");
    }
}
