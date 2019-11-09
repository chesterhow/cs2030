import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.Random;
import java.time.Instant;
import java.time.Duration;

/**
 * Main is the main driver class for testing matrix multiplication.
 * Usage: java Main n
 * 2^n is the dimension of the square matrixOne
 */
class Main {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Incorrect number of arguments. " +
          "Usage: java Main n");
      return;
    }

    int n = Integer.parseInt(args[0]);

    Random random = new Random(1); // random number generator to fill matrix
    int dimension = 1 << n; // dimension of square matrix 2^n;
    System.out.println("dimension " + dimension);

    // fill two matrices of size 2^n x 2^n with random numbers.
    Matrix matrixOne = Matrix.generate(dimension, () -> random.nextDouble());
    Matrix matrixTwo = Matrix.generate(dimension, () -> random.nextDouble());

    Matrix result1 = Matrix.nonRecursiveMultiply(matrixOne, matrixTwo);
    Matrix result2 = Matrix.parallelMultiply(matrixOne, matrixTwo);

    boolean match = Matrix.equals(result1, result2);
    if (!match) {
      System.out.println("ERROR: matrix multiplication gives inconsistent " +
          "result in sequential and parallel implementations.");
      return;
    }

    double d1 = measureTimeToRun(() -> Matrix.nonRecursiveMultiply(matrixOne, matrixTwo));
    double d2 = measureTimeToRun(() -> Matrix.parallelMultiply(matrixOne, matrixTwo));

    System.out.printf("Sequential %.3f ms Parallel %.3f ms Speedup %.3f times\n", d1, d2, d1 / d2);
  }

  /**
   * Return the average time needed to run the task over three runs.
   * @param  task A lambda expression for the task to be run
   * @return The average time taken in ms.
   */
  private static double measureTimeToRun(Supplier<Matrix> task) {
    final int numOfTimes = 3;
    double sum = 0;
    for (int i = 0; i < numOfTimes; i++) {
      Instant start = Instant.now();
      Matrix m = task.get();
      Instant stop = Instant.now();
      sum += Duration.between(start, stop).toMillis();
    }
    return sum / numOfTimes;
  }
}
