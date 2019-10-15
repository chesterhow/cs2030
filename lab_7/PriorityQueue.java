import java.util.Optional;

/**
 * This class wraps around the class PriorityQueue from Java Collection
 * Framework. It provides an alternative API that could be modified to support
 * an immutable Priority Queue.
 *
 * @author Ooi Wei Tsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 **/
public class PriorityQueue<T> {
    /** The actual priority queue that stores the items. */
    private final java.util.PriorityQueue<T> pq;

    /**
     * Constructor for an empty priority queue.
     **/
    public PriorityQueue() {
        this.pq = new java.util.PriorityQueue<>();
    }

    public PriorityQueue(java.util.PriorityQueue<T> pq) {
        this.pq = pq;
    }

    /**
     * Add an object into the priority queue following the add() method of the JCF
     * PriorityQueue. Return the priority queue after the object is added.
     * 
     * @param object The item to add
     **/
    public PriorityQueue<T> add(T object) {
        java.util.PriorityQueue<T> newQueue = new java.util.PriorityQueue<>(this.pq);
        newQueue.add(object);
        return new PriorityQueue<>(newQueue);
    }

    /**
     * Return as a pair, (i) the next priortized item according to the natural order
     * of the objects in the priority queue, and (ii) the priority queue after the
     * item is removed.
     * 
     * @return The pair (item, priority queue)
     **/
    public Pair<Optional<T>, PriorityQueue<T>> poll() {
        java.util.PriorityQueue<T> newQueue = new java.util.PriorityQueue<>(this.pq);
        Optional<T> t = Optional.ofNullable(newQueue.poll());
        return Pair.of(t, new PriorityQueue<>(newQueue));
    }
}
