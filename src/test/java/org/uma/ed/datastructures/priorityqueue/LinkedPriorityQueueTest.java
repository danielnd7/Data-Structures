package org.uma.ed.datastructures.priorityqueue;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Comparator;


class LinkedPriorityQueueTest {

    @Test
    void testEnqueueAndFirst() {
        LinkedPriorityQueue<Integer> queue = LinkedPriorityQueue.empty();
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(8);

        assertEquals(2, queue.first(), "First element should be the smallest (highest priority).");
    }

    @Test
    void testDequeue() {
        LinkedPriorityQueue<Integer> queue = LinkedPriorityQueue.empty();
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(8);

        assertEquals(2, queue.first(), "First element should be 2 before dequeue.");
        queue.dequeue();
        assertEquals(5, queue.first(), "First element should be 5 after removing the smallest element.");
    }

    @Test
    void testDequeueEmptyQueue() {
        LinkedPriorityQueue<Integer> queue = LinkedPriorityQueue.empty();

        // Ensure exception is thrown when dequeuing an empty queue
        assertThrows(NullPointerException.class, queue::dequeue, "Dequeueing an empty queue should throw NullPointerException.");
    }

    @Test
    void testCopyOf() {
        LinkedPriorityQueue<Integer> queue = LinkedPriorityQueue.of(5, 2, 8);

        LinkedPriorityQueue<Integer> copiedQueue = LinkedPriorityQueue.copyOf(queue);
        assertEquals(3, copiedQueue.size(), "Copied queue should have the same size as the original.");
        assertEquals(2, copiedQueue.first(), "Copied queue should have the same first element as the original.");

        queue.dequeue(); // Modify original queue
        assertEquals(2, copiedQueue.first(), "Copied queue should remain unchanged when the original is modified.");
    }

    @Test
    void testFromIterable() {
        Iterable<Integer> elements = java.util.List.of(5, 2, 8);
        LinkedPriorityQueue<Integer> queue = LinkedPriorityQueue.from(elements);

        assertEquals(3, queue.size(), "Queue should contain all elements from the iterable.");
        assertEquals(2, queue.first(), "Queue should be correctly ordered by priority.");
    }

    @Test
    void testWithCustomComparator() {
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
        LinkedPriorityQueue<String> queue = LinkedPriorityQueue.empty(lengthComparator);

        queue.enqueue("short");
        queue.enqueue("tiny");
        queue.enqueue("lengthy");

        assertEquals("tiny", queue.first(), "Custom comparator should prioritize shortest strings.");
    }

    @Test
    void testMultipleEnqueueAndDequeue() {
        LinkedPriorityQueue<Integer> queue = LinkedPriorityQueue.empty();
        queue.enqueue(10);
        queue.enqueue(1);
        queue.enqueue(7);
        queue.enqueue(3);

        assertEquals(1, queue.first(), "First element should be 1.");
        queue.dequeue();
        assertEquals(3, queue.first(), "First element should be 3 after removing 1.");
        queue.dequeue();
        assertEquals(7, queue.first(), "First element should be 7 after removing 3.");
    }
}
