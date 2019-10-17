import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * The LabOFourA class is the entry point into Lab 4a.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Main {
    /**
     * The main method for Lab 4a. Reads data from file and then run a simulation
     * based on the input data.
     *
     * @param args two arguments, first an integer specifying number of servers in
     *             the shop. Second a file containing a sequence of double values,
     *             each being the arrival time of a customer (in any order).
     */
    public static void main(String[] args) {
        Optional<Scanner> scanner = createScanner(args);
        if (scanner.isEmpty()) {
            return;
        }

        SimState state = initSimState(scanner.get());
        System.out.println(state.run());
    }

    /**
     * Read from inputs, populate the simulator with events, and run.
     *
     * @param scanner The scanner to read inputs from.
     */
    public static SimState initSimState(Scanner scanner) {
        // Read the first line of input as number of servers in the shop
        int numOfServers = scanner.nextInt();
        SimState currState = new SimState(numOfServers);

        if (scanner.hasNextDouble()) {
            List<Double> arrivalTimes = Stream.iterate(
                    scanner.nextDouble(),
                    i -> i >= 0,
                    i -> scanner.hasNextDouble() ? scanner.nextDouble() : -1
                ).collect(Collectors.toList());

            int numArrivals = arrivalTimes.size();
            List<Double> processedArrivals = new ArrayList<>();

            currState = Stream.iterate(
                    currState,
                    s -> {
                        double time = arrivalTimes.remove(0);
                        return s.addEvent(time, state -> state.simulateArrival(time));
                    }
                ).dropWhile(s -> !arrivalTimes.isEmpty())
                .findFirst().get();
        }

        return currState;
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
        Optional<Scanner> scanner = Optional.empty();

        try {
            // Read from stdin if no filename is given, otherwise read from the
            // given file.
            if (args.length == 0) {
                // If there is no argument, read from standard input.
                scanner = Optional.ofNullable(new Scanner(System.in));
            } else {
                // Else read from file
                FileReader fileReader = new FileReader(args[0]);
                scanner = Optional.ofNullable(new Scanner(fileReader));
            }
        } catch (FileNotFoundException exception) {
            System.err.println("Unable to open file " + args[0] + " " + exception);
        }
        return scanner;
    }
}
