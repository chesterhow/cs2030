import java.util.Optional;
import java.util.stream.Stream;
import java.util.function.Function;

/**
 * This class encapsulates all the simulation states. There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop (the
 * servers) and (iv) the event logs.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
public class SimState {
    public class Event implements Comparable<Event> {
        private final double time;
        private final Function<SimState, SimState> action;

        public Event(double time, Function<SimState, SimState> action) {
            this.time = time;
            this.action = action;
        }

        public SimState simulate(SimState sim) {
            return this.action.apply(sim);
        }

        @Override
        public int compareTo(Event e) {
            return (int) Math.signum(this.time - e.time);
        }
    }

    /** The priority queue of events. */
    private final PriorityQueue<Event> events;

    /** The statistics maintained. */
    private final Statistics stats;

    /** The shop of servers. */
    private final Shop shop;

    private final String eventsLog;

    /**
     * Constructor for creating the simulation state from scratch.
     * 
     * @param numOfServers The number of servers.
     */
    public SimState(int numOfServers) {
        this.shop = new Shop(numOfServers);
        this.stats = new Statistics();
        this.events = new PriorityQueue<>();
        this.eventsLog = "";
    }

    public SimState(Shop shop, Statistics stats, PriorityQueue<Event> events, String eventsLog) {
        this.shop = shop;
        this.stats = stats;
        this.events = events;
        this.eventsLog = eventsLog;
    }

    /**
     * Add an event to the simulation's event queue.
     * 
     * @param time      The event to be added to the queue.
     * @param action    The action to run when the event is simulated.
     * @return The new simulation state.
     */
    public SimState addEvent(double time, Function<SimState, SimState> action) {
        PriorityQueue<Event> newEvents = this.events.add(new Event(time, action));
        return new SimState(this.shop, this.stats, newEvents, this.eventsLog);
    }

    /**
     * Retrieve the next event with earliest time stamp from the priority queue, and
     * a new state. If there is no more event, an Optional.empty will be returned.
     * 
     * @return A pair object with an (optional) event and the new simulation state.
     */
    private Pair<Optional<Event>, SimState> nextEvent() {
        Pair<Optional<Event>, PriorityQueue<Event>> result = this.events.poll();
        return Pair.of(
            result.first,
            new SimState(this.shop, this.stats, result.second, this.eventsLog)
        );
    }

    /**
     * Log a customer's arrival in the simulation.
     * 
     * @param time The time the customer arrives.
     * @param c    The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteArrival(double time, Customer c) {
        String newEventsLog = this.eventsLog + String.format("%.3f %s arrives\n", time, c);
        return new SimState(this.shop, this.stats, this.events, newEventsLog);
    }

    /**
     * Log when a customer waits in the simulation.
     * 
     * @param time The time the customer starts waiting.
     * @param s    The server the customer is waiting for.
     * @param c    The customer who waits.
     * @return A new state of the simulation after the customer waits.
     */
    public SimState noteWait(double time, Server s, Customer c) {
        String newEventsLog = this.eventsLog
            + String.format("%.3f %s waits to be served by %s\n", time, c, s);
        return new SimState(this.shop, this.stats, this.events, newEventsLog);
    }

    /**
     * Log when a customer is served in the simulation.
     * 
     * @param time The time the customer arrives.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is served.
     */
    public SimState noteServed(double time, Server s, Customer c) {
        String newEventsLog = this.eventsLog + String.format("%.3f %s served by %s\n", time, c, s);
        Statistics newStats = this.stats.serveOneCustomer().recordWaitingTime(c.timeWaited(time));
        return new SimState(this.shop, newStats, this.events, newEventsLog);
    }

    /**
     * Log when a customer is done being served in the simulation.
     * 
     * @param time The time the customer arrives.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is done being
     *         served.
     */
    public SimState noteDone(double time, Server s, Customer c) {
        String newEventsLog = this.eventsLog
            + String.format("%.3f %s done serving by %s\n", time, c, s);
        return new SimState(this.shop, this.stats, this.events, newEventsLog);
    }

    /**
     * Log when a customer leaves the shops without service.
     * 
     * @param time  The time this customer leaves.
     * @param c     The customer who leaves.
     * @return A new state of the simulation.
     */
    public SimState noteLeave(double time, Customer c) {
        String newEventsLog = this.eventsLog + String.format("%.3f %s leaves\n", time, c);
        Statistics newStats = this.stats.looseOneCustomer();
        return new SimState(this.shop, newStats, this.events, newEventsLog);
    }

    /**
     * Simulates the logic of what happened when a customer arrives. The customer is
     * either served, waiting to be served, or leaves.
     * 
     * @param time The time the customer arrives.
     * @return A new state of the simulation.
     */
    public SimState simulateArrival(double time) {
        Customer customer = new Customer(time, this.stats.getTotalCustomers() + 1);
        Statistics newStats = this.stats.incrementCustomers();
        return new SimState(this.shop, newStats, this.events, this.eventsLog)
            .noteArrival(time, customer)
            .processArrival(time, customer);
    }

    /**
     * Handle the logic of finding idle servers to serve the customer, or a server
     * that the customer can wait for, or leave. Called from simulateArrival.
     * 
     * @param time     The time the customer arrives.
     * @param customer The customer to be served.
     * @return A new state of the simulation.
     */
    private SimState processArrival(double time, Customer customer) {
        Optional<Server> s = this.shop.find(server -> server.isIdle());

        if (s.isPresent()) {
            return serveCustomer(time, s.get(), customer);
        }

        s = shop.find(server -> !server.hasWaitingCustomer());

        if (s.isPresent()) {
            Server newServer = s.get().askToWait(customer);
            Shop newShop = this.shop.replace(newServer);
            return new SimState(newShop, this.stats, this.events, this.eventsLog)
                .noteWait(time, newServer, customer);
        }

        return noteLeave(time, customer);
    }

    /**
     * Simulate the logic of what happened when a customer is done being served. The
     * server either serve the next customer or becomes idle.
     * 
     * @param time     The time the service is done.
     * @param server   The server serving the customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState simulateDone(double time, Server server, Customer customer) {
        Optional<Server> actualServer = this.shop.find(s -> s.equals(server));

        if (actualServer.isPresent()) {
            SimState newSimState = noteDone(time, server, customer);
            Optional<Customer> c = actualServer.get().getWaitingCustomer();

            if (c.isPresent()) {
                return newSimState.serveCustomer(time, server, c.get());
            }

            Shop newShop = this.shop.replace(actualServer.get().makeIdle());
            return new SimState(
                newShop,
                newSimState.stats,
                newSimState.events,
                newSimState.eventsLog
            );
        }

        return this;
    }

    /**
     * Handle the logic of server serving customer. A new done event is generated
     * and scheduled.
     * 
     * @param time     The time this customer is served.
     * @param server   The server serving this customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    private SimState serveCustomer(double time, Server server, Customer customer) {
        Optional<Server> actualServer = this.shop.find(s -> s.equals(server));

        if (actualServer.isPresent()) {
            double doneTime = time + Simulation.SERVICE_TIME;
            Server newServer = actualServer.get().serve(customer);
            Shop newShop = this.shop.replace(newServer);
            return new SimState(newShop, this.stats, this.events, this.eventsLog)
                .noteServed(time, newServer, customer)
                .addEvent(doneTime, state -> state.simulateDone(doneTime, newServer, customer));
        }

        return this;
    }

    /**
     * The main simulation loop. Repeatedly get events from the event queue,
     * simulate and update the event. Return the final simulation state.
     * 
     * @return The final state of the simulation.
     */
    public SimState run() {
        Pair<Optional<Event>, SimState> pair = nextEvent();

        return Stream.iterate(pair,p -> p.first.get().simulate(p.second).nextEvent())
            .dropWhile(p -> p.first.isPresent())
            .findFirst().get().second;
    }

    /**
     * Return a string representation of the simulation state, which consists of all
     * the logs and the stats.
     * 
     * @return A string representation of the simulation.
     */
    public String toString() {
        return this.eventsLog + stats.toString();
    }
}
