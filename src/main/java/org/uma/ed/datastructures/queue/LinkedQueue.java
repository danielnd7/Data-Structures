package org.uma.ed.datastructures.queue;

/**
 * This class represents a Queue data structure implemented using a linked structure of nodes.
 * Each node in the structure contains a reference to the next node and an element of type T.
 * The structure maintains references to first and last nodes in queue.
 * *
 *
 * @param <T> Type of elements in queue.
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class LinkedQueue<T> extends AbstractQueue<T> implements Queue<T> {
    /**
     * This class represents a node in a linked structure. Each node contains an element and a reference to the next node.
     *
     * @param <E> Type of elements in node.
     */
    private static final class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    /**
     * References to first and last nodes in queue.
     */
    private Node<T> first;
    private Node<T> last;

    /**
     * Number of elements in queue.
     */
    private int size;

    /*
     * INVARIANT:
     * - if queue is empty, `first` and `last` are null.
     * - if queue is not empty, `first` references first node in queue and `last` references last node in queue.
     * - each node in queue contains a reference to next node or null if it is the last node.
     * - `size` is number of elements in queue.
     */

    /**
     * Creates an empty LinkedQueue.
     * <p> Time complexity: O(1)
     */
    public LinkedQueue() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Creates an empty LinkedQueue.
     * <p> Time complexity: O(1)
     */
    public static <T> LinkedQueue<T> empty() {
        return new LinkedQueue<>();
    }

    /**
     * Creates a LinkedQueue with given elements.
     * <p> Time complexity: O(n)
     *
     * @param elements elements to be added to queue.
     * @param <T>      Type of elements in queue.
     * @return a LinkedQueue with given elements.
     */
    @SafeVarargs
    public static <T> LinkedQueue<T> of(T... elements) {
        LinkedQueue<T> queue = new LinkedQueue<>();
        for (T element : elements) {
            queue.enqueue(element);
        }
        return queue;
    }

    /**
     * Creates a LinkedQueue with elements in given iterable.
     * <p> Time complexity: O(n)
     *
     * @param iterable {@code Iterable} of elements to be added to queue.
     * @param <T>      Type of elements in iterable.
     * @return a LinkedQueue with elements in given iterable.
     */
    public static <T> LinkedQueue<T> from(Iterable<T> iterable) {
        LinkedQueue<T> queue = new LinkedQueue<>();
        for (T element : iterable) {
            queue.enqueue(element);
        }
        return queue;
    }

    /**
     * Returns a new LinkedQueue with same elements in same order as argument.
     * <p> Time complexity: O(n)
     *
     * @param that LinkedQueue to be copied.
     * @return a new LinkedQueue with same elements and order as {@code that}.
     */
    public static <T> LinkedQueue<T> copyOf(LinkedQueue<T> that) {
        LinkedQueue<T> copy = LinkedQueue.empty();

        Node<T> current = that.first;
        while (current != null){
            copy.enqueue(current.element);
            current = current.next;
        }

        return copy;
    }

    /**
     * Returns a new LinkedQueue with same elements in same order as argument.
     * <p> Time complexity: O(n)
     *
     * @param that Queue to be copied.
     * @return a new LinkedQueue with same elements and order as {@code that}.
     */
    public static <T> LinkedQueue<T> copyOf(Queue<T> that) {
        LinkedQueue<T> copy = LinkedQueue.empty();

        while (!that.isEmpty()){
          copy.enqueue(that.first());
          that.dequeue();
        }

        // recover the original queue
        Node<T> current = copy.first;
        while (current != null){
            that.enqueue(current.element);
            current = current.next;
        }

        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public void enqueue(T element) {
        Node<T> node = new Node<>(element, null);
        if (isEmpty()) {
            first = node;
            last = node;
        } else { // not empty list
            last.next = node;
            last = node;
        }
        size++;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     *
     * @throws EmptyQueueException {@inheritDoc}
     */
    @Override
    public T first() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("An empty list");
        }
        return first.element;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     *
     * @throws EmptyQueueException {@inheritDoc}
     */
    @Override
    public void dequeue() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("An empty list");
        }
        first = first.next;
        size--;

        if (size == 0) { // the case of only one element in a queue
            last = null;
        }
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Returns a protected iterable over elements in queue.
     */
    protected Iterable<T> elements() {
        return () -> new java.util.Iterator<>() {
            Node<T> current = first;

            public boolean hasNext() {
                return current != null;
            }

            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T element = current.element;
                current = current.next;
                return element;
            }
        };
    }
}
