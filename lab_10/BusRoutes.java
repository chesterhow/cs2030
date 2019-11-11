import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Encapsulates the result of a query: for a bus stop and a search string, it
 * stores a map of bus services that servce stops with matching name. e.g., stop
 * 12345, name "MRT", with map contains: 96: 11223 "Clementi MRT" 95: 22334
 * "Beuno Vista MRT"
 *
 * @author: Ooi Wei Tsang
 * @version: CS2030 AY19/20 Semester 1, Lab 10
 */
class BusRoutes {
    final BusStop stop;
    final String name;
    final CompletableFuture<Map<BusService, CompletableFuture<Set<BusStop>>>> futureServices;

    /**
     * Constructor for creating a bus route.
     * 
     * @param stop     The first bus stop.
     * @param name     The second bus stop.
     * @param services The set of bus services between the two stops.
     */
    BusRoutes(BusStop stop, String name,
            CompletableFuture<Map<BusService, CompletableFuture<Set<BusStop>>>> futureServices) {
        this.stop = stop;
        this.name = name;
        this.futureServices = futureServices;
    }

    /**
     * Return a string describing the bus route.
     * 
     * @return The first line contains the query information: bus stop id and search
     *         string. The remaining line contains the bus services and matching
     *         stops served.
     */
    public CompletableFuture<String> description() {
        return futureServices.thenCompose(services -> {
            String result = "Search for: " + this.stop + " <-> "
                + name + ":\n" + "From " + this.stop + "\n";
            CompletableFuture<String> output = CompletableFuture.completedFuture(result);

            for (BusService service : services.keySet()) {
                output = output.thenCombine(
                    describeService(service, services.get(service)),
                    (x, y) -> x + y);
            }

            return output;
        });
    }

    /**
     * Return a string representation a bus service and its matching stops.
     * 
     * @return The first line contains the query information: bus stop id and search
     *         string. The remaining line contains the bus services and matching
     *         stops served.
     */
    public CompletableFuture<String> describeService(BusService service,
            CompletableFuture<Set<BusStop>> futureStops) {
        return futureStops.thenApply(stops -> {
            if (stops.isEmpty()) {
                return "";
            }

            return stops.stream()
                .filter(stop -> stop != this.stop)
                .reduce("- Can take " + service + " to:\n",
                    (str, stop) -> str += "  - " + stop + "\n",
                    (str1, str2) -> str1 + str2);
        });
    }
}
