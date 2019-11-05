package cs2030.simulator;

/**
 * This utility class stores two items together in a pair. It could be used, for
 * instance, to faciliate returning of two values in a function.
 *
 * @author Ooi Wei Tsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 **/
public final class Pair<T,U> {
  /** First item in the pair. */
  public T first;
  /** Second item in the pair. */
  public U second;

  /**
   * A private constructor for a new pair.
   **/
  private Pair(T t, U u) {
    this.first = t;
    this.second = u;
  }

  /**
   * Create a new pair of items.
   * @param t First item in the pair
   * @param u Second item in the pair
   * @return The new pair
   **/
  public static <T,U> Pair<T, U> of(T t, U u) {
    return new Pair<>(t, u);
  }
}
