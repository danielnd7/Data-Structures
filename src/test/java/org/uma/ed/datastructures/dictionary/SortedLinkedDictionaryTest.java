package org.uma.ed.datastructures.dictionary;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;



class SortedLinkedDictionaryTest {

    @Test
    void testInsertSingleEntry() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));

        assertTrue(dictionary.isDefinedAt(1));
        assertEquals("Value1", dictionary.valueOf(1));
        assertEquals(1, dictionary.size());
    }

    @Test
    void testInsertMultipleEntriesSorted() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));
        dictionary.insert(new Dictionary.Entry<>(2, "Value2"));
        dictionary.insert(new Dictionary.Entry<>(3, "Value3"));

        assertEquals("Value1", dictionary.valueOf(1));
        assertEquals("Value2", dictionary.valueOf(2));
        assertEquals("Value3", dictionary.valueOf(3));
        assertEquals(3, dictionary.size());
    }

    @Test
    void testInsertUpdatesValueIfKeyExists() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));
        dictionary.insert(new Dictionary.Entry<>(1, "UpdatedValue1"));

        assertEquals("UpdatedValue1", dictionary.valueOf(1));
        assertEquals(1, dictionary.size());
    }

    @Test
    void testInsertEntryAtBeginning() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(2, "Value2"));
        dictionary.insert(new Dictionary.Entry<>(1, "Value1")); // Insert before first

        assertEquals("Value1", dictionary.valueOf(1));
        assertEquals("Value2", dictionary.valueOf(2));
        assertEquals(2, dictionary.size());
    }

    @Test
    void testInsertEntryAtEnd() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));
        dictionary.insert(new Dictionary.Entry<>(2, "Value2")); // Insert at end

        assertEquals("Value1", dictionary.valueOf(1));
        assertEquals("Value2", dictionary.valueOf(2));
        assertEquals(2, dictionary.size());
    }

    @Test
    void testDeleteSingleEntry() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));
        dictionary.delete(1);

        assertFalse(dictionary.isDefinedAt(1));
        assertNull(dictionary.valueOf(1));
        assertEquals(0, dictionary.size());
    }

    @Test
    void testDeleteFirstEntry() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));
        dictionary.insert(new Dictionary.Entry<>(2, "Value2"));

        dictionary.delete(1);

        assertFalse(dictionary.isDefinedAt(1));
        assertEquals("Value2", dictionary.valueOf(2));
        assertEquals(1, dictionary.size());
    }

    @Test
    void testDeleteLastEntry() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));
        dictionary.insert(new Dictionary.Entry<>(2, "Value2"));

        dictionary.delete(2);

        assertFalse(dictionary.isDefinedAt(2));
        assertEquals("Value1", dictionary.valueOf(1));
        assertEquals(1, dictionary.size());
    }

    @Test
    void testDeleteMiddleEntry() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));
        dictionary.insert(new Dictionary.Entry<>(2, "Value2"));
        dictionary.insert(new Dictionary.Entry<>(3, "Value3"));

        dictionary.delete(2);

        assertFalse(dictionary.isDefinedAt(2));
        assertEquals("Value1", dictionary.valueOf(1));
        assertEquals("Value3", dictionary.valueOf(3));
        assertEquals(2, dictionary.size());
    }

    @Test
    void testDeleteNonExistentEntry() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));

        dictionary.delete(2); // No effect

        assertEquals("Value1", dictionary.valueOf(1));
        assertEquals(1, dictionary.size());
    }

    @Test
    void testDeleteFromEmptyDictionary() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.delete(1); // Should do nothing

        assertEquals(0, dictionary.size());
    }

    @Test
    void testInsertAndDeleteComplexSequence() {
        SortedLinkedDictionary<Integer, String> dictionary = SortedLinkedDictionary.empty();
        dictionary.insert(new Dictionary.Entry<>(3, "Value3"));
        dictionary.insert(new Dictionary.Entry<>(1, "Value1"));
        dictionary.insert(new Dictionary.Entry<>(2, "Value2"));

        dictionary.delete(2);
        dictionary.insert(new Dictionary.Entry<>(2, "NewValue2"));

        assertEquals("Value1", dictionary.valueOf(1));
        assertEquals("NewValue2", dictionary.valueOf(2));
        assertEquals("Value3", dictionary.valueOf(3));
        assertEquals(3, dictionary.size());
    }
}
