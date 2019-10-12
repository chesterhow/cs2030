import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;

public class Main {
    public static IntStream factors(int x) {
        return IntStream
            .rangeClosed(1, x)
            .filter(i -> x % i == 0);
    }

    public static boolean isPrime(int x) {
        return IntStream
            .range(2, x)
            .noneMatch(i -> x % i == 0);
    }

    public static IntStream primeFactors(int x) {
        return factors(x)
            .filter(i -> i > 1)
            .filter(i -> isPrime(i));
    }

    public static OptionalDouble variance(int[] array) {
        if (array.length <= 1) {
            return OptionalDouble.empty();
        }
        double mean = IntStream.of(array)
            .asDoubleStream()
            .sum() / array.length;
        
        double sum = IntStream.of(array)
            .asDoubleStream()
            .map(i -> Math.pow((i - mean), 2))
            .sum();

        return OptionalDouble.of(sum / (array.length - 1));
    }

    public static long countRepeats(int[] array) {
        RepeatTracker rt = new RepeatTracker();

        IntStream
            .range(0, array.length - 1)
            .forEach(i -> {
                if (array[i] == array[i+1]) {
                    if (!rt.getAdjacent()) {
                        rt.incrementOccurences();
                    }
                } else {
                    rt.setAdjacent(false);
                }
            });

        return rt.getOccurrences();
    }

    public static boolean isSquare(int n) {
        return IntStream
            .rangeClosed(1, (int)Math.sqrt(n))
            .anyMatch(i -> Math.pow(i, 2) == n);
    }

    public static boolean isPerfect(int n) {
        return n == IntStream
            .range(1, n)
            .map(i -> n % i == 0 ? i : 0)
            .sum();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        factors(sc.nextInt()).forEach(System.out::println);
    }
}
