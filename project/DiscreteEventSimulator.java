import java.util.List;
import java.util.Queue;
import java.util.PriorityQueue;

public class DiscreteEventSimulator {
    private final List<Server> serversList;
    private final List<Customer> customersList;
    private final Queue<Event> eventQueue;

    private static int customersThatLeft = 0;

    /**
     * Initialises a new Discrete Event Simulator with given servers and customers lists.
     *
     * @param serversList the list of servers
     * @param customersList the list of customers
     */
    public DiscreteEventSimulator(List<Server> serversList, List<Customer> customersList) {
        this.serversList = serversList;
        this.customersList = customersList;
        this.eventQueue = new PriorityQueue<>(this.customersList.size(), new EventComparator());

        // Generate arrival events and queue them
        for (Customer c : this.customersList) {
            this.eventQueue.add(new Event(c.getArrivalTime(), c));
        }
    }

    /**
     * Retrieves and executes events from the head of the queue until the queue is empty.
     * Handles events based on the customer's state. Manages which customers will be served
     * by which servers.
     */
    public void run() {
        while (this.eventQueue.peek() != null) {
            Event e = this.eventQueue.poll();
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
                        this.eventQueue.add(servedEvent);
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
                            this.eventQueue.add(waitsEvent);
                            break;
                        }
                    }
                }

                // If no servers with empty queue, customer leaves
                if (!served) {
                    c.setState(State.LEAVES);
                    // Create a new LEAVES event
                    Event leavesEvent = new Event(c.getArrivalTime(), c);
                    this.eventQueue.add(leavesEvent);
                    this.customersThatLeft++;
                }
            } else if (c.getState() == State.WAITS) {
                c.setState(State.SERVED);
                Server server = e.getServer();
                server.queueCustomer(c);
                // Create a new SERVED event
                Event servedEvent = new Event(server.getNextServiceTime(), c, server); 
                this.eventQueue.add(servedEvent);
            } else if (c.getState() == State.SERVED) {
                c.setState(State.DONE);
                Server server = e.getServer();
                server.serveCustomer(c, e.getEventTime());
                // Create a new DONE event
                Event doneEvent = new Event(server.getNextServiceTime(), c, server);
                this.eventQueue.add(doneEvent);
            } else if (c.getState() == State.DONE) {
                Server server = e.getServer();
                server.endService();
            }
        }

        System.out.println("[" + String.format("%.3f", Server.getAverageWaitTime()) + 
            " " + Server.getCustomersServed() + " " + this.customersThatLeft + "]");
    }
}
