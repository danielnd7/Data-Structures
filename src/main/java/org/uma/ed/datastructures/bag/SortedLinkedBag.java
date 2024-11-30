package org.uma.ed.datastructures.bag;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Bags implemented using a sorted linked structure of nodes. Order of elements is defined by provided comparator or
 * natural order if none is provided.
 *
 * @param <T> Type of elements in bag.
 * @author Pablo López, Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedLinkedBag<T> extends AbstractSortedBag<T> implements SortedBag<T> {
    /**
     * A node in the sorted linked structure containing an element, its number of occurrences and a reference to the next
     * node.
     *
     * @param <E> Type of element stored in this node.
     */
    private static final class Node<E> {
        E element;
        int occurrences;
        Node<E> next;

        Node(E element, int occurrences, Node<E> next) {
            this.element = element;
            this.occurrences = occurrences;
            this.next = next;
        }
    }

    /*
     * INVARIANT:
     * - The linked structure maintains elements in ascending order.
     * - Each node contains a unique element (no two nodes contain the same element) and a reference to the next node or
     *   null if it is the last node.
     * - Nodes must have more than zero occurrences; nodes with zero occurrences are eliminated from the linked structure.
     * - `first` is a reference to the first node in the linked structure or null if bag is empty.
     * - `size` is number of elements stored in this bag.
     */

    /**
     * Comparator defining order of elements in sorted linked structure.
     */
    private final Comparator<T> comparator;

    /**
     * Reference to first node in sorted linked structure or null if bag is empty.
     */
    private Node<T> first;

    /**
     * Reference to last node in sorted linked structure or null if bag is empty.
     */
    private Node<T> last;

    /**
     * Number of elements stored in this bag
     */
    private int size;

    /**
     * Constructs an empty sorted bag with order provided by parameter.
     * <p> Time complexity: O(1)
     *
     * @param comparator Comparator defining order of elements in this sorted bag.
     */
    public SortedLinkedBag(Comparator<T> comparator) {
        this.comparator = comparator;
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * Returns a new sorted bag with same elements in same order as argument.
     * <p> Time complexity: O(n)
     *
     * @param that Sorted bag to be copied.
     * @return a new SortedLinkedBag with same elements and order as {@code that}.
     */
    public static <T extends Comparable<? super T>> SortedLinkedBag<T> copyOf(SortedBag<T> that) {
        SortedLinkedBag<T> copy = new SortedLinkedBag<>(that.comparator());
        for (T element : that) {
            copy.append(element);
        }
        return copy;
    }

    /**
     * Constructs an empty sorted bag with order provided by parameter.
     * <p> Time complexity: O(1)
     *
     * @param comparator Comparator defining order of elements in this sorted bag.
     */
    public static <T> SortedLinkedBag<T> empty(Comparator<T> comparator) {
        return new SortedLinkedBag<>(comparator);
    }

    /**
     * Constructs an empty sorted bag with natural order of elements.
     * <p> Time complexity: O(1)
     */
    public static <T extends Comparable<? super T>> SortedLinkedBag<T> empty() {
        return new SortedLinkedBag<T>(Comparator.naturalOrder());
    }

    /**
     * Returns a new sorted bag with given comparator and elements.
     * <p> Time complexity: O(n²)
     *
     * @param comparator Comparator defining order of elements in new sorted bag.
     * @param elements   Elements to include in new sorted bag.
     * @param <T>        Type of elements in bag.
     * @return a new SortedLinkedBag with given comparator and elements.
     */
    @SafeVarargs
    public static <T> SortedLinkedBag<T> of(Comparator<T> comparator, T... elements) {
        SortedLinkedBag<T> sortedLinkedBag = new SortedLinkedBag<>(comparator);
        sortedLinkedBag.insert(elements);
        return sortedLinkedBag;
    }

    /**
     * Returns a new sorted bag with natural order and provided elements.
     * <p> Time complexity: O(n²)
     *
     * @param elements Elements to include in new sorted bag.
     * @param <T>      Type of elements in bag.
     * @return a new SortedLinkedBag with natural order and provided elements.
     */
    @SafeVarargs
    public static <T extends Comparable<? super T>> SortedLinkedBag<T> of(T... elements) {
        return of(Comparator.naturalOrder(), elements);
    }

    /**
     * Returns a new sorted bag with given comparator and elements in provided iterable.
     * <p> Time complexity: O(n²)
     *
     * @param comparator Comparator defining order of iterable in new sorted bag.
     * @param iterable   iterable with elements to include in new sorted bag.
     * @param <T>        Type of elements in bag.
     * @return a new SortedLinkedBag with given comparator and elements in provided iterable.
     */
    public static <T> SortedLinkedBag<T> from(Comparator<T> comparator, Iterable<T> iterable) {
        SortedLinkedBag<T> sortedLinkedBag = new SortedLinkedBag<>(comparator);
        for (T element : iterable) {
            sortedLinkedBag.insert(element);
        }
        return sortedLinkedBag;
    }

    /**
     * Returns a new sorted bag with natural order and iterable in provided iterable.
     * <p> Time complexity: O(n²)
     *
     * @param iterable iterable with elements to include in new sorted bag.
     * @param <T>      Type of elements in bag.
     * @return a new SortedLinkedBag with natural order and provided iterable.
     */
    public static <T extends Comparable<? super T>> SortedLinkedBag<T> from(Iterable<T> iterable) {
        return from(Comparator.naturalOrder(), iterable);
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public Comparator<T> comparator() {
        return this.comparator;
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
     * This class searches for an element within a sorted linked structure.
     * - If the element is found:
     * - `found` is set to true.
     * - `current` points to the node containing the element.
     * - `previous` points to the preceding node, or is null if the element is at the first node.
     * - If the element is not found:
     * - `found` is set to false.
     * - `current` points to the node that would follow the element.
     * - `previous` points to the node that would precede the element, or is null if the element would
     * be at the first node.
     */
    private final class Finder {
        boolean found;
        Node<T> previous, current;

        Finder(T element) {
            previous = null;
            current = first;

            int cmp = 0;
            while (current != null && (cmp = comparator.compare(element, current.element)) > 0) {
                previous = current;
                current = current.next;
            }

            found = current != null && cmp == 0;
        }
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(n)
     */
    @Override
    public void insert(T element) {
        Finder finder = new Finder(element);
        if (finder.found) { // the element is already in the bag
            finder.current.occurrences++;
        } else {
            if (finder.previous == null) { // insertion at the beginning
                first = new Node<>(element, 1, first);

                if (finder.current == null) { // insertion in the empty bag
                    last = first;
                }
            } else { // normal insertion . also valid for the insertion at the end
                finder.previous.next = new Node<>(element, 1, finder.current);

                if (finder.current == null) { // insertion at the end
                    last = finder.previous.next;
                }
            }
        }
        size++;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(n)
     */
    @Override
    public void delete(T element) {
        Finder finder = new Finder(element);
        if (finder.found) {
            finder.current.occurrences--;
        }
        if (finder.current.occurrences == 0) {
            if (finder.previous == null) { // deletion of the first element
                first = finder.current.next;

                if (finder.current.next == null) { // deletion of the single element
                    last = first;
                }
            } else {
                finder.previous.next = finder.current.next; // normal deletion

                if (finder.current.next == null) { // deletion of the last element
                    last = finder.previous;
                }
            }
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
     * {@inheritDoc}
     * <p> Time complexity: O(n)
     */
    @Override
    public int occurrences(T element) {
        Finder finder = new Finder(element);
        return (finder.found) ? finder.current.occurrences : 0;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public T minimum() {
        if (isEmpty()) {
            throw new NoSuchElementException("minimum on empty bag");
        }
        return first.element;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public T maximum() {
        if (isEmpty()) {
            throw new NoSuchElementException("maximum on empty bag");
        }
        return last.element;
    }

    @Override
    public Iterator<T> iterator() {
        return new BagIterator();
    }

    /**
     * Invariant conditions:
     * - `current` refers to the node holding the next element to be returned, or is null if no elements remain.
     * - `returned` tracks the count of elements already yielded from the current node.
     */
    private final class BagIterator implements Iterator<T> {
        Node<T> current;
        int returned;

        BagIterator() {
            current = first;
            returned = 0;
        }

        public boolean hasNext() {
            return (current != null);
        }

        public T next() {
            T element = current.element;
            current = current.next;
            return element;
        }
    }

    private void append(T element) {
        assert first == null || comparator.compare(element, last.element) >= 0;

        if (first == null) {
            first = new Node<>(element, 1, null);
            last = first;
        } else if (comparator.compare(element, last.element) == 0) {
            last.occurrences++;
        } else {
            Node<T> node = new Node<>(element, 1, null);
            last.next = node;
            last = node;
        }
        size++;
    }

    // Does union of this and bag. Result is stored on this. bag is not modified
    public void union(Bag<T> bag) {
        for (T x : bag) {
            this.insert(x);
        }
    }

    // We use that the argument is iterated in order
    public void union(SortedLinkedBag<T> that) { // could be implemented in a more efficient way...
        for (T element : that) {
            this.insert(element);
        }
    }

    public void intersection(Bag<T> bag) { // bag is NOT NECESSARY SORTED
        SortedLinkedBag<T> intersection = new SortedLinkedBag<>(this.comparator);

        Node<T> current = first;
        while (current != null){
            int minOccurrenceCurrent = bag.occurrences(current.element);

            if (minOccurrenceCurrent > 0){ // the element exists in both bags
                  if (current.occurrences < minOccurrenceCurrent) { // insert lower number of occurrences
                      minOccurrenceCurrent = current.occurrences;  // current.occurrences > 0
                  }

                  Node<T> node = new Node<>(current.element, minOccurrenceCurrent, null);

                  if (intersection.isEmpty()){
                      intersection.last =  node;
                      intersection.first = node;
                  } else {
                      intersection.last.next = node;
                      intersection.last = node;
                  }
                  intersection.size++;
            }

          current = current.next;
        }
        this.first = intersection.first;
        this.last = intersection.last;
        this.size = intersection.size;

    }


    public void intersection(SortedLinkedBag<T> that) { // both are SORTED
        SortedLinkedBag<T> intersection = new SortedLinkedBag<>(this.comparator);
        Iterator<T> iterator1 = that.iterator();
        Iterator<T> iterator2 = this.iterator();

        T elem1 = iterator1.next();
        T elem2 = iterator2.next();

        while ((iterator1.hasNext()) && (iterator2.hasNext())) {

            if (comparator.compare(elem1, elem2) > 0) {
                elem2 = iterator2.next();
            } else if (comparator.compare(elem1, elem2) < 0) {
                elem1 = iterator1.next();

            } else { // there is the same element in both bags:
                if (occurrences(elem1) < that.occurrences(elem1)) { // we add the element with lower occurrences
                    intersection.append(elem1);
                } else {
                    intersection.append(elem2);
                }

                elem1 = iterator1.next();
                elem2 = iterator2.next();
            }
        }

        this.first = intersection.first;
        this.last = intersection.last;
        this.size = intersection.size;
    }

    public void difference(Bag<T> bag) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // We use that argument iterates in order
    public void difference(SortedLinkedBag<T> that) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
