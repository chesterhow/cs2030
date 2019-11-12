package cs2030.simulator;

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
    java.util.PriorityQueue<T> pq;

    /**
     * Constructor for an empty priority queue.
     **/
    public PriorityQueue() {
        pq = new java.util.PriorityQueue<T>();
    }

    /**
     * Constructor for an empty priority queue.
     **/
    private PriorityQueue(PriorityQueue<T> source) {
        this.pq = new java.util.PriorityQueue<>();
        this.pq.addAll(source.pq);
    }

    /**
     * Add an object into the priority queue following the add() method of the JCF
     * PriorityQueue. Return the priority queue after the object is added.
     * 
     * @param object The item to add
     **/
    public PriorityQueue<T> add(T object) {
        PriorityQueue<T> copy = new PriorityQueue<>(this);
        copy.pq.add(object);
        return copy;
    }

    /**
     * Return as a pair, (i) the next priortized item according to the natural order
     * of the objects in the priority queue, and (ii) the priority queue after the
     * item is removed.
     * 
     * @return The pair (item, priority queue)
     **/
    public Pair<Optional<T>, PriorityQueue<T>> poll() {
        PriorityQueue<T> copy = new PriorityQueue<>(this);
        T t = copy.pq.poll();
        return Pair.of(Optional.ofNullable(t), copy);
    }
}
