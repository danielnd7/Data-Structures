package org.uma.ed.datastructures.bag;

import static org.junit.jupiter.api.Assertions.*;
///////////////////
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Comparator;


class SortedLinkedBagTest {
    private SortedLinkedBag<Integer> bag;
    private SortedLinkedBag<Integer> anotherBag;

    @BeforeEach
    void setUp() {
        bag = SortedLinkedBag.empty();
        anotherBag = SortedLinkedBag.empty();
    }

    @Test
    @Order(4)
    void testOccurrences() {
        bag.insert(1);
        bag.insert(1);
        bag.insert(2);

        assertEquals(2, bag.occurrences(1), "Occurrences of 1 should be 2");
        assertEquals(1, bag.occurrences(2), "Occurrences of 2 should be 1");
        assertEquals(0, bag.occurrences(3), "Occurrences of 3 should be 0");
    }

    @Test
    @Order(1) // This test will run first
    void testClear() {
        bag.insert(1);
        bag.insert(2);
        bag.insert(3);

        bag.clear();
        assertEquals(0, bag.size(), "Bag should be empty after clear");
    }

    @Test
    @Order(2)
    void testDelete() {
        bag.insert(1);
        bag.insert(2);
        bag.insert(3);

        // Delete first element
        bag.delete(1);
        assertEquals(0, bag.occurrences(1), "1 should be removed from bag");
        assertEquals(2, bag.size(), "Bag size should be 2 after deletion");

        // Delete last element
        bag.delete(3);
        assertEquals(0, bag.occurrences(3), "3 should be removed from bag");
        assertEquals(1, bag.size(), "Bag size should be 1 after deletion");

        // Delete intermediate element
        bag.insert(2);
        bag.insert(3);
        bag.delete(2);
        assertEquals(1, bag.occurrences(2), "2 should be decremented");
        assertEquals(2, bag.size(), "Bag size should be correct after deletion");

        // Deletion in an empty bag
        bag.clear();
        assertDoesNotThrow(() -> bag.delete(1), "Deleting from empty bag should not throw exception");
    }

    @Test
    @Order(3)
    void testInsert() {
        // Insert into empty bag
        bag.insert(1);
        assertEquals(1, bag.occurrences(1), "1 should be added to bag");
        assertEquals(1, bag.size(), "Bag size should be 1 after insertion");

        // Insert into non-empty bag
        bag.insert(2);
        bag.insert(3);
        assertEquals(3, bag.size(), "Bag size should be 3 after multiple insertions");
        assertTrue(bag.occurrences(2) > 0, "2 should exist in the bag");

        // Insert duplicate elements
        bag.insert(3);
        assertEquals(2, bag.occurrences(3), "3 should have two occurrences");
    }

    @Test
    @Order(6)
    void testUnionWithUnsortedBag() {
        Bag<Integer> unsortedBag = new UnsortedLinkedBag<>();
        unsortedBag.insert(3);
        unsortedBag.insert(1);
        unsortedBag.insert(2);

        bag.insert(1);
        bag.insert(2);

        bag.union(unsortedBag);
        assertEquals(2, bag.occurrences(1), "1 should have correct occurrences after union");
        assertEquals(2, bag.occurrences(2), "2 should have correct occurrences after union");
        assertEquals(1, bag.occurrences(3), "3 should be added to the bag");
    }

    @Test
    @Order(5)
    void testUnionWithSortedBag() {
        anotherBag.insert(3);
        anotherBag.insert(1);
        anotherBag.insert(2);

        bag.insert(1);
        bag.insert(2);

        bag.union(anotherBag);
        assertEquals(2, bag.occurrences(1), "1 should have correct occurrences after union");
        assertEquals(2, bag.occurrences(2), "2 should have correct occurrences after union");
        assertEquals(1, bag.occurrences(3), "3 should be added to the bag");
    }

    @Test
    @Order(8)
    void testIntersectionWithUnsortedBag() {
        Bag<Integer> unsortedBag = new UnsortedLinkedBag<>();
        unsortedBag.insert(1);
        unsortedBag.insert(3);

        bag.insert(1);
        bag.insert(2);

        bag.intersection(unsortedBag);
        assertEquals(1, bag.occurrences(1), "1 should remain in the intersection");
        assertEquals(0, bag.occurrences(2), "2 should be removed in the intersection");
        assertEquals(0, bag.occurrences(3), "3 should not be added in the intersection");
    }

    @Test
    @Order(7)
    void testIntersectionWithSortedBag() {
        anotherBag.insert(1);
        anotherBag.insert(3);

        bag.insert(1);
        bag.insert(2);

        bag.intersection(anotherBag);
        assertEquals(1, bag.occurrences(1), "1 should remain in the intersection");
        assertEquals(0, bag.occurrences(2), "2 should be removed in the intersection");
        assertEquals(0, bag.occurrences(3), "3 should not be added in the intersection");
    }

    @Test
    @Order(10)
    void testDifferenceWithUnsortedBag() {
        Bag<Integer> unsortedBag = new UnsortedLinkedBag<>();
        unsortedBag.insert(1);
        unsortedBag.insert(3);

        bag.insert(1);
        bag.insert(2);

        bag.difference(unsortedBag);
        assertEquals(0, bag.occurrences(1), "1 should be removed in the difference");
        assertEquals(1, bag.occurrences(2), "2 should remain in the bag");
        assertEquals(0, bag.occurrences(3), "3 should not be added in the difference");
    }

    @Test
    @Order(9)
    void testDifferenceWithSortedBag() {
        anotherBag.insert(1);
        anotherBag.insert(3);

        bag.insert(1);
        bag.insert(2);

        bag.difference(anotherBag);
        assertEquals(0, bag.occurrences(1), "1 should be removed in the difference");
        assertEquals(1, bag.occurrences(2), "2 should remain in the bag");
        assertEquals(0, bag.occurrences(3), "3 should not be added in the difference");
    }

}
