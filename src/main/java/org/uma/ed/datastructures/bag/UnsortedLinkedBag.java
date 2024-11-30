package org.uma.ed.datastructures.bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

//////////////////////////////////////////////////////////////////
///////////////// TESTING  ONLY  ////////////////////////////////
/////////////////////////////////////////////////////////////////
public class UnsortedLinkedBag<T> implements Bag<T> {
    private Node<T> head;
    private int size;

    // Internal Node class
    private static class Node<E> {
        E element;
        int occurrences;
        Node<E> next;

        Node(E element, int occurrences, Node<E> next) {
            this.element = element;
            this.occurrences = occurrences;
            this.next = next;
        }
    }

    public UnsortedLinkedBag() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void insert(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Null elements are not allowed");
        }

        Node<T> current = head;

        // Check if element already exists
        while (current != null) {
            if (current.element.equals(element)) {
                current.occurrences++;
                size++;
                return;
            }
            current = current.next;
        }

        // Add as new node
        head = new Node<>(element, 1, head);
        size++;
    }

    @Override
    public void delete(T element) {
        if (head == null || element == null) {
            return;
        }

        Node<T> current = head;
        Node<T> prev = null;

        while (current != null) {
            if (current.element.equals(element)) {
                if (current.occurrences > 1) {
                    current.occurrences--;
                } else {
                    if (prev == null) {
                        head = current.next;
                    } else {
                        prev.next = current.next;
                    }
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    @Override
    public int occurrences(T element) {
        if (element == null) {
            return 0;
        }

        Node<T> current = head;
        while (current != null) {
            if (current.element.equals(element)) {
                return current.occurrences;
            }
            current = current.next;
        }

        return 0;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;
            private int remainingOccurrences = current != null ? current.occurrences : 0;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                T element = current.element;
                remainingOccurrences--;

                if (remainingOccurrences <= 0) {
                    current = current.next;
                    if (current != null) {
                        remainingOccurrences = current.occurrences;
                    }
                }

                return element;
            }
        };
    }
}

