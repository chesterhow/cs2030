import cs2030.simulator.SimState;

import java.util.Scanner;
import java.util.Optional;
import java.io.FileReader;
import java.io.FileNotFoundException;

/**
 * The Main class is the entry point into DES+.
 *
 * @version CS2030 AY19/20 Sem 1 DES+
 */
public class Main {
    /**
     * Read from inputs, populate the simulator with arrival events.
     *
     * @param scanner The scanner to read inputs from.
     * @return A new simulation state.
     */
    public static SimState initSimState(Scanner scanner) {
        int seed = scanner.nextInt();
        int numOfServers = scanner.nextInt();
        int numOfCounters = scanner.nextInt();
        int maxQueueLength = scanner.nextInt();
        int numOfCustomers = scanner.nextInt();
        double arrivalRate = scanner.nextDouble();
        double serviceRate = scanner.nextDouble();
        double restingRate = scanner.nextDouble();
        double restingProbability = scanner.nextDouble();
        double greedyProbability = scanner.nextDouble();

        SimState state = new SimState(seed, numOfServers, numOfCounters, maxQueueLength,
            arrivalRate, serviceRate, restingRate, restingProbability);
        return state.generateArrivals(numOfCustomers, greedyProbability);
    }

    /**
     * Create and return a scanner. If a command line argument is given, treat the
     * argument as a file and open a scanner on the file. Else, create a scanner
     * that reads from standard input.
     *
     * @param args The arguments provided for simulation.
     * @return A scanner or {@code null} if a filename is provided but the file
     *         cannot be open.
     */
    private static Optional<Scanner> createScanner(String[] args) {
        try {
            // Read from stdin if no filename is given, otherwise read from the given file.
            if (args.length == 0) {
                // If there is no argument, read from standard input.
                return Optional.of(new Scanner(System.in));
            } else {
                // Else read from file
                FileReader fileReader = new FileReader(args[0]);
                return Optional.of(new Scanner(fileReader));
            }
        } catch (FileNotFoundException exception) {
            System.err.println("Unable to open file " + args[0] + " " + exception);
        }
        return Optional.empty();
    }

    /**
     * The main method for DES+. Reads data from file and then run a simulation
     * based on the input data.
     *
     * @param args specific settings for the simulator instance.
     */
    public static void main(String[] args) {
        createScanner(args)
            .map(scanner -> initSimState(scanner).run())
            .ifPresent(System.out::println);
    }
}
