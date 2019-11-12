package cs2030.simulator;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Predicate;

/**
 * A shop object maintains the list of servers and support queries for server.
 *
 * @version CS2030 AY19/20 Sem 1 DES+
 */
public class Shop {
    /** List of servers. */
    private final List<Server> servers;

    /**
     * Create and initialize a Shop.
     * 
     * @param numOfServers   The number of servers.
     * @param numOfCounters  The number of self-checkout counters.
     * @param maxQueueLength The max length of each server's queue.
     */
    public Shop(int numOfServers, int numOfCounters, int maxQueueLength) {
        this.servers = Stream.iterate(1, i -> i + 1)
            .map(i -> (i <= numOfServers) ?
                new HumanServer(i, maxQueueLength) :
                new SelfCheckout(i, maxQueueLength))
            .limit(numOfServers + numOfCounters)
            .collect(Collectors.toList());
    }

    /**
     * Private constructor for Shop.
     * 
     * @param servers The list of servers.
     */
    private Shop(List<Server> servers) {
        this.servers = servers;
    }

    /**
     * Find a server matching the predicate.
     *
     * @param predicate A predicate to match the server with.
     * @return Optional.empty if no server matches the predicate, or the optional of
     *         the server if a matching server is found.
     */
    public Optional<Server> find(Predicate<Server> predicate) {
        return this.servers.stream()
            .filter(predicate)
            .findFirst();
    }

    /**
     * Find the server with the shortest queue.
     * 
     * @return Optional.empty if all servers' queues are full, or the optional of
     *         the server with the shortest queue.
     */
    public Optional<Server> findShortestQueue() {
        return this.servers.stream()
            .filter(server -> !server.queueFull())
            .min(Comparator.comparing(Server::queueLength));
    }

    /**
     * Replace the given server and return a new shop.
     * 
     * @param server The server to be replaced.
     * @return The new shop with the updated list of servers.
     */
    public Shop replace(Server server) {
        return new Shop(servers.stream()
            .map(s -> s.equals(server) ? server : s)
            .collect(Collectors.toList()));
    }

    /**
     * Return a string representation of this shop.
     * 
     * @return A string reprensetation of this shop.
     */
    public String toString() {
        return servers.toString();
    }
}
