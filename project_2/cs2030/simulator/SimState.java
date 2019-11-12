package cs2030.simulator;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.function.Function;

/**
 * This class encapsulates all the simulation states. There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop (the
 * servers) and (iv) the event logs.
 *
 * @version CS2030 AY19/20 Sem 1 DES+
 */
public class SimState {
    /**
     * The Event class encapsulates information and methods pertaining to a
     * Simulator event. Stores a lambda that denotes type of event - arrival,
     * done or endRest.
     */
    private class Event implements Comparable<Event> {
        /** The time this event occurs at. */
        private double time;

        /** A function that this event will execute. */
        private Function<SimState, SimState> lambda;

        /**
         * Create and initialize a new event.
         * 
         * @param time The time of occurrence.
         * @param f    The function that this event will execute.
         */
        public Event(double time, Function<SimState, SimState> f) {
            this.time = time;
            this.lambda = f;
        }

        /**
         * Defines natural ordering of events by their time. Events ordered in ascending
         * order of their timestamps.
         *
         * @param other Another event to compare against.
         * @return 0 if two events occur at same time, a positive number if this event
         *         has later than other event, a negative number otherwise.
         */
        public int compareTo(Event other) {
            return (int) Math.signum(this.time - other.time);
        }

        /**
         * Simulates this event by applying the lambda.
         *
         * @param state The current simulation state.
         * @return A new state of simulation.
         */
        public SimState simulate(SimState state) {
            return this.lambda.apply(state);
        }
    }

    /** The priority queue of events. */
    private PriorityQueue<Event> events;

    /** The statistics maintained. */
    private final Statistics stats;

    /** The shop of servers. */
    private final Shop shop;

    /** The event logs. */
    private final String log;

    /** The last customer id. */
    private final int lastCustomerId;

    /** The probability of a human server resting. */
    private final double restingProbability;

    /** A RandomGenerator object. */
    private final RandomGenerator rng;

    /**
     * A private constructor of internal states.
     * 
     * @param shop               The list of servers.
     * @param stats              The statistics being kept.
     * @param events             A priority queue of events.
     * @param log                A log of what's happened so far.
     * @param lastCustomerId     The last customer id.
     * @param restingProbability The probability of a human server resting.
     * @param rng                A RandomGenerator object.
     */
    private SimState(Shop shop, Statistics stats, PriorityQueue<Event> events, String log,
            int lastCustomerId, double restingProbability, RandomGenerator rng) {

        this.shop = shop;
        this.stats = stats;
        this.events = events;
        this.log = log;
        this.lastCustomerId = lastCustomerId;
        this.restingProbability = restingProbability;
        this.rng = rng;
    }

    /**
     * Constructor for creating the simulation state from scratch.
     * 
     * @param seed               The seed for the RandomGenerator object.
     * @param numOfServers       The number of human servers.
     * @param numOfCounters      The number of self-checkout counters.
     * @param maxQueueLength     The max length of each server's queue.
     * @param arrivalRate        The arrival rate for the RandomGenerator object.
     * @param serviceRate        The service rate for the RandomGenerator object.
     * @param restingRate        The resting rate for the RandomGenerator object.
     * @param restingProbability The probability of a human server resting.
     */
    public SimState(int seed, int numOfServers, int numOfCounters, int maxQueueLength,
            double arrivalRate, double serviceRate, double restingRate, double restingProbability) {

        this(new Shop(numOfServers, numOfCounters, maxQueueLength),
            new Statistics(),
            new PriorityQueue<Event>(),
            "", 1, restingProbability,
            new RandomGenerator(seed, arrivalRate, serviceRate, restingRate));
    }

    /**
     * Update the statistics of this simulation.
     * 
     * @param stats The updated statistics to replace the existing one.
     * @return The new simulation state.
     */
    private SimState stats(Statistics stats) {
        return new SimState(this.shop, stats, this.events, this.log, this.lastCustomerId,
            this.restingProbability, this.rng);
    }

    /**
     * Update a server of this simulation.
     * 
     * @param s The updated server to replace the existing one.
     * @return The new simulation state.
     */
    private SimState server(Server s) {
        return new SimState(shop.replace(s), this.stats, this.events, this.log, this.lastCustomerId,
            this.restingProbability, this.rng);
    }

    /**
     * Update the event queue of this simulation.
     * 
     * @param pq The priority queue to replace the existing one.
     * @return The new simulation state.
     */
    private SimState events(PriorityQueue<Event> pq) {
        return new SimState(this.shop, this.stats, pq, this.log, this.lastCustomerId,
            this.restingProbability, this.rng);
    }

    /**
     * Update the event log of this simulation.
     * 
     * @param s The log string to append to the existing event log.
     * @return The new simulation state.
     */
    private SimState log(String s) {
        return new SimState(this.shop, this.stats, this.events, this.log + s, this.lastCustomerId,
            this.restingProbability, this.rng);
    }

    /**
     * Update the last customer id of this simulation.
     * 
     * @param id The updated last customer id.
     * @return The new simulation state.
     */
    private SimState id(int id) {
        return new SimState(this.shop, this.stats, this.events, this.log, id,
            this.restingProbability, this.rng);
    }

    /**
     * Add an event to the simulation's event queue.
     * 
     * @param time   The time the event will occur.
     * @param lambda The function that the event will execute.
     * @return The new simulation state.
     */
    public SimState addEvent(double time, Function<SimState, SimState> lambda) {
        return events(events.add(new Event(time, lambda)));
    }

    /**
     * Retrieve the next event with earliest timestamp from the priority queue, and
     * a new state. If there are no more events, an Optional.empty will be returned.
     * 
     * @return A pair object with an (optional) event and the new simulation state.
     */
    private Pair<Optional<Event>, SimState> nextEvent() {
        Pair<Optional<Event>, PriorityQueue<Event>> result = this.events.poll();
        return Pair.of(result.first, events(result.second));
    }

    /**
     * Called when a customer arrives in the simulation. This method updates the
     * simulation logs.
     * 
     * @param time The time the customer arrives.
     * @param c    The customer that arrrives.
     * @return A new state of the simulation after the customer arrives.
     */
    public SimState noteArrival(double time, Customer c) {
        return log(String.format("%.3f %s arrives\n", time, c));
    }

    /**
     * Called when a customer joins a server's queue in the simulation. This method
     * updates the simulation logs.
     * 
     * @param time The time the customer joins a server's queue.
     * @param s    The server whose queue the customer joins.
     * @param c    The customer that joins a server's queue.
     * @return A new state of the simulation after the customer joins a server's queue.
     */
    public SimState noteWait(double time, Server s, Customer c) {
        return log(String.format("%.3f %s waits to be served by %s\n", time, c, s));
    }

    /**
     * Called when a customer is served in the simulation. This method updates the
     * simulation logs and statistics.
     * 
     * @param time The time the customer is served.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is served.
     */
    public SimState noteServed(double time, Server s, Customer c) {
        return log(String.format("%.3f %s served by %s\n", time, c, s))
            .stats(stats.serveOneCustomer()
                .recordWaitingTime(c.timeWaited(time)));
    }

    /**
     * Called when a customer is done being served in the simulation. This method updates
     * the simulation logs.
     * 
     * @param time The time the customer is done being served.
     * @param s    The server that serves the customer.
     * @param c    The customer that is served.
     * @return A new state of the simulation after the customer is done being served.
     */
    public SimState noteDone(double time, Server s, Customer c) {
        return log(String.format("%.3f %s done serving by %s\n", time, c, s));
    }

    /**
     * Called when a customer leaves the shops without service. This method updates the
     * simulation logs and statistics.
     * 
     * @param time The time this customer leaves.
     * @param c    The customer who leaves.
     * @return A new state of the simulation.
     */
    public SimState noteLeave(double time, Customer c) {
        return log(String.format("%.3f %s leaves\n", time, c))
            .stats(stats.looseOneCustomer());
    }

    /**
     * Generate arrival events and add them to the events queue. Arriving customers might
     * be greedy, depending on the probability.
     * 
     * @param numOfCustomers    The number of customers to arrive.
     * @param greedyProbability The probability of a customer being greedy.
     * @return A new state of the simulation.
     */
    public SimState generateArrivals(int numOfCustomers, double greedyProbability) {
        double time = 0;
        SimState state = this;

        while (numOfCustomers > 0) {
            final double arrivalTime = time;
            boolean greedy = this.rng.genCustomerType() < greedyProbability;
            state = state.addEvent(arrivalTime, s -> s.simulateArrival(arrivalTime, greedy));
            time += this.rng.genInterArrivalTime();
            numOfCustomers--;
        }

        return state;
    }

    /**
     * Simulates the logic of what happens when a customer arrives. The customer is
     * either served, waits to be served, or leaves.
     * 
     * @param time   The time the customer arrives.
     * @param greedy Boolean indicating if the customer is greedy.
     * @return A new state of the simulation.
     */
    public SimState simulateArrival(double time, boolean greedy) {
        Customer customer = new Customer(this.lastCustomerId, time, greedy);

        return noteArrival(time, customer)
            .id(this.lastCustomerId + 1)
            .processArrival(time, customer);
    }

    /**
     * Handle the logic of finding available servers to serve the customer, or a server
     * that the customer can wait for, or leave. Called from simulateArrival.
     * 
     * @param time     The time the customer arrives.
     * @param customer The customer to be served.
     * @return A new state of the simulation.
     */
    private SimState processArrival(double time, Customer customer) {
        return shop.find(server -> server.isAvailable())
            .map(server -> serveCustomer(time, server, customer))
            .or(() -> (customer.isGreedy() ?
                    shop.findShortestQueue() :
                    shop.find(server -> !server.queueFull()))
                .map(server -> noteWait(time, server, customer)
                .server(server.askToWait(customer))))
            .orElseGet(() -> noteLeave(time, customer));
    }

    /**
     * Simulates the logic of what happens when a customer is done being served.
     * The server either serves the next customer, rests (if human), or becomes available.
     * 
     * @param time     The time the service is done.
     * @param server   The server serving the customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState simulateDone(double time, Server server, Customer customer) {
        if (server instanceof HumanServer) {
            if (this.rng.genRandomRest() < this.restingProbability) {
                return noteDone(time, server, customer)
                    .restServer(time, (HumanServer) server, customer);
            }
        }

        return shop.find(s -> s.equals(server))
            .flatMap(s -> s.getNextWaitingCustomer())
            .map(nextCustomer -> noteDone(time, server, customer)
                .serveCustomer(time, server, nextCustomer))
            .orElseGet(() -> noteDone(time, server, customer)
                .server(server.removeCustomer(customer)));
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
    public SimState serveCustomer(double time, Server server, Customer customer) {
        double doneTime = time + this.rng.genServiceTime();

        return server(server.serve(customer))
            .noteServed(time, server, customer)
            .addEvent(doneTime, state -> state.simulateDone(doneTime, server, customer));
    }

    /**
     * Handle the logic of a human server taking a rest. A new end rest event is
     * generated and scheduled.
     * 
     * @param time     The time the server goes to rest.
     * @param server   The server serving this customer.
     * @param customer The customer being served.
     * @return A new state of the simulation.
     */
    public SimState restServer(double time, HumanServer server, Customer customer) {
        double restEndTime = time + this.rng.genRestPeriod();

        return server(server.removeCustomer(customer))
            .server(server.makeRest())
            .addEvent(restEndTime, state -> state.endServerRest(restEndTime, server));
    }

    /**
     * Handle the logic of a human server ending their rest. Serves next customer
     * in queue (if any).
     * 
     * @param time   The time the server ends their rest.
     * @param server The server ending the rest.
     * @return A new state of the simulation.
     */
    public SimState endServerRest(double time, HumanServer server) {
        return shop.find(s -> s.equals(server))
            .flatMap(s -> s.getNextWaitingCustomer())
            .map(nextCustomer -> server(server.endRest())
                .serveCustomer(time, server, nextCustomer))
            .orElseGet(() -> server(server.endRest()));
    }

    /**
     * The main simulation loop. Repeatedly get events from the event queue,
     * simulates and updates the event. Return the final simulation state.
     * 
     * @return The final state of the simulation.
     */
    public SimState run() {
        Pair<Optional<Event>, SimState> s = Stream.iterate(this.nextEvent(),
            p -> p.first.isPresent(),
            p -> p.first.get().simulate(p.second).nextEvent())
            .reduce((p, q) -> q).orElseThrow();

        return s.first.get().simulate(s.second);
    }

    /**
     * Return a string representation of the simulation state, which consists of all
     * logs and stats.
     * 
     * @return A string representation of the simulation.
     */
    public String toString() {
        return log + stats.toString();
    }
}
