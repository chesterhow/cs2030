import cs2030.simulator.SimState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;
import java.util.Scanner;

/**
 * The LabOFourB class is the entry point into Lab 4b.
 *
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Main {
  /**
   * Read from inputs, populate the simulator with events, and run.
   *
   * @param scanner The scanner to read inputs from.
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

    SimState state = new SimState(seed, numOfServers, numOfCounters, maxQueueLength, arrivalRate, serviceRate, restingRate, restingProbability);
    return state.generateArrivals(numOfCustomers);
  }

  /**
   * Create and return a scanner. If a command line argument is given,
   * treat the argument as a file and open a scanner on the file. Else,
   * create a scanner that reads from standard input.
   *
   * @param args The arguments provided for simulation.
   * @return A scanner or {@code null} if a filename is provided but the file
   *     cannot be open.
   */
  private static Optional<Scanner> createScanner(String[] args) {
    try {
      // Read from stdin if no filename is given, otherwise read from the
      // given file.
      if (args.length == 0) {
        // If there is no argument, read from standard input.
        return Optional.of(new Scanner(System.in));
      } else {
        // Else read from file
        FileReader fileReader = new FileReader(args[0]);
        return Optional.of(new Scanner(fileReader));
      }
    } catch (FileNotFoundException exception) {
      System.err.println("Unable to open file " + args[0] + " "
          + exception);
    }
    return Optional.empty();
  }

  /**
   * The main method for Lab 4b. Reads data from file and
   * then run a simulation based on the input data.
   *
   * @param args two arguments, first an integer specifying number of servers
   *      in the shop. Second a file containing a sequence of double values, each
   *      being the arrival time of a customer (in any order).
   */
  public static void main(String[] args) {
    createScanner(args)
      .map(scanner -> initSimState(scanner).run())
      .ifPresent(System.out::println);
  }
}
