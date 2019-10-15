import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Predicate;

/**
 * A shop object maintains the list of servers and support queries for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Shop {
    /** List of servers. */
    private final List<Server> servers;

    /**
     * Create a new shop with a given number of servers.
     * 
     * @param numOfServers The number of servers.
     */
    public Shop(int numOfServers) {
        this.servers = Stream.iterate(1, i -> i + 1)
            .limit(numOfServers)
            .map(i -> new Server(i))
            .collect(Collectors.toList());
    }

    public Shop(List<Server> servers) {
        this.servers = servers;
    }

    public Optional<Server> find(Predicate<Server> pred) {
        return this.servers.stream()
            .filter(pred)
            .findFirst();
    }

    public Shop replace(Server newServer) {
        List<Server> newServers = new ArrayList<>(this.servers);

        newServers = newServers.stream()
            .map(server -> newServer.toString().equals(server.toString()) ? newServer : server)
            .collect(Collectors.toList());

        return new Shop(newServers);
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
