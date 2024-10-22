package org.uma.ed.datastructures.set;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Test cases for class SortedLinkedSet")
class SortedLinkedSetTest {

  @Nested
  @DisplayName("An SortedLinkedSet is created")
  class TestCasesForCreatingSortedSets {

    @Test
    @DisplayName("by calling the default constructor")
    void whenTheConstructorIsCalledThenASortedSetIsCreated() {
      // Arrange
      SortedSet<Integer> set = new SortedLinkedSet<>(Integer::compareTo);

      // Assert
      assertNotNull(set);
      assertTrue(set.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method without a comparator")
    void whenTheEmptyMethodIsCalledWithoutComparatorThenAnSortedSetIsCreated() {
      // Arrange
      SortedSet<Double> set = SortedLinkedSet.empty();

      // Assert
      assertNotNull(set);
      assertTrue(set.isEmpty());
    }

    @Test
    @DisplayName("by calling the empty() method with a comparator")
    void whenTheEmptyMethodIsCalledWithComparatorThenAnSortedSetIsCreated() {
      // Arrange
      Comparator<Double> reversedComparator = Comparator.reverseOrder();
      SortedSet<Double> set = SortedLinkedSet.empty(reversedComparator);

      // Assert
      assertNotNull(set);
      assertTrue(set.isEmpty());
      assertEquals(reversedComparator, set.comparator());
    }

    @Test
    @DisplayName("from a sequence of different values using the of() method with a comparator")
    void givenTheOfMethodWithoutRepeatedElementsWhenUsingAComparatorThenASortedSetIsCreated() {
      // Arrange
      Comparator<String> comparator = Comparator.naturalOrder();
      SortedSet<String> set = SortedLinkedSet.of(comparator, "orange", "blue", "green");

      // Assert
      assertNotNull(set);

      int expectedElementsInTheSet = 3;
      assertEquals(expectedElementsInTheSet, set.size());

      String expectedSetContents = "SortedLinkedSet(blue, green, orange)";
      assertEquals(expectedSetContents, set.toString());
    }

    @Test
    @DisplayName("from a sequence of different values using the of() method without a comparator")
    void givenTheOfMethodWithoutRepeatedElementsWhenNoUsingAComparatorThenASortedSetIsCreated() {
      // Arrange
      SortedSet<String> set = SortedLinkedSet.of("orange", "blue", "green");

      // Assert
      assertNotNull(set);

      int expectedElementsInTheSet = 3;
      assertEquals(expectedElementsInTheSet, set.size());

      String expectedSetContents = "SortedLinkedSet(blue, green, orange)";
      assertEquals(expectedSetContents, set.toString());
    }


    @Test
    @DisplayName("from a sequence with repeated values using the of() method")
    void whenTheOfMethodWithRepeatedElementsIsCalledThenASortedSetIsCreated() {
      // Arrange
      Comparator<Integer> comparator = Comparator.naturalOrder();
      SortedSet<Integer> set = SortedLinkedSet.of(comparator, 4, 3, 2, 1, 3, 2, 1, 5);

      // Assert
      assertNotNull(set);

      int expectedElementsInTheSet = 5;
      assertEquals(expectedElementsInTheSet, set.size());

      String expectedSetContents = "SortedLinkedSet(1, 2, 3, 4, 5)";
      assertEquals(expectedSetContents, set.toString());
    }

    @Nested
    @DisplayName("using the from() method")
    class TestCasesForCreatingLinkedSetsUsingTheFromMethod {

      @Test
      @DisplayName("from an iterable (list) of values without repeated elements with a comparator")
      void givenAListOfValuesWithoutRepeatedElementsAndComparatorThenASortedSetIsCreated() {
        // Arrange
        List<Double> initialValues = List.of(5.5, -2.0, 4.9, -4.0, 5.6, 6.4, 0.1);
        Comparator<Double> comparator = Comparator.reverseOrder();
        SortedSet<Double> set = SortedLinkedSet.from(comparator, initialValues);

        // Assert
        assertNotNull(set);

        int expectedElementsInTheSet = initialValues.size();
        assertEquals(expectedElementsInTheSet, set.size());

        String expectedSetContents = "SortedLinkedSet(6.4, 5.6, 5.5, 4.9, 0.1, -2.0, -4.0)";
        assertEquals(expectedSetContents, set.toString());
      }

      @Test
      @DisplayName("from an iterable (list) of values with repeated elements with a comparator")
      void givenAListOfValuesWithRepeatedElementsAndComparatorThenASortedSetIsCreated() {
        // Arrange
        List<Double> initialValues = List.of(5.5, -2.0, 4.9, -2.0, 5.6, 5.5, 2.4, 0.1);
        Comparator<Double> comparator = Comparator.naturalOrder();
        SortedSet<Double> set = SortedLinkedSet.from(comparator, initialValues);

        // Assert
        assertNotNull(set);

        int expectedElementsInTheSet = initialValues.size() - 2;
        assertEquals(expectedElementsInTheSet, set.size());

        String expectedSetContents = "SortedLinkedSet(-2.0, 0.1, 2.4, 4.9, 5.5, 5.6)";
        assertEquals(expectedSetContents, set.toString());
      }

      @Test
      @DisplayName("from an iterable (list) of values without repeated elements with the default comparator")
      void givenAListOfValuesWithRepeatedElementsWithDefaultComparatorThenASortedSetIsCreated() {
        // Arrange
        List<Double> initialValues = List.of(1.2, -2.0);
        SortedSet<Double> set = SortedLinkedSet.from(initialValues);

        // Assert
        assertNotNull(set);

        int expectedElementsInTheSet = initialValues.size();
        assertEquals(expectedElementsInTheSet, set.size());

        String expectedSetContents = "SortedLinkedSet(-2.0, 1.2)";
        assertEquals(expectedSetContents, set.toString());
      }

      @Test
      @DisplayName("from an iterable (list) of values with repeated values with the default comparator")
      void givenAListOfValuesWithoutRepeatedElementsWithDefaultComparatorThenASortedSetIsCreated() {
        // Arrange
        List<Double> initialValues = List.of(5.5, -2.0, 4.9, -2.0, 5.6, 5.5, 2.4, 0.1);
        SortedSet<Double> set = SortedLinkedSet.from(initialValues);

        // Assert
        assertNotNull(set);

        int expectedElementsInTheSet = initialValues.size() - 2;
        assertEquals(expectedElementsInTheSet, set.size());

        String expectedSetContents = "SortedLinkedSet(-2.0, 0.1, 2.4, 4.9, 5.5, 5.6)";
        assertEquals(expectedSetContents, set.toString());
      }
    }
  }

  @Nested
  @DisplayName("A call to method clear()")
  class TestCasesForMethodClear {

    @Test
    @DisplayName("removes all the elements of the set")
    void givenASetWhenClearThenTheSetIsEmpty() {
      // Arrange
      Comparator<Integer> comparator = Comparator.naturalOrder();
      SortedSet<Integer> set = SortedLinkedSet.of(comparator, 4, 3, 2, 1, 3, 2, 1, 5);

      // Act
      set.clear();

      // Assert
      assertTrue(set.isEmpty());
    }
  }

  @Nested
  @DisplayName("The copyOf() method")
  class TestCasesForCopyOfMethod {

    @Test
    @DisplayName("works properly with an empty SortedLinkedSet")
    void givenAEmptySetWhenCopyOfThenTheNewSetIsAlsoEmpty() {
      // Arrange
      Comparator<Double> comparator = Comparator.naturalOrder();
      SortedSet<Double> set = SortedLinkedSet.of(comparator);

      // Act
      SortedSet<Double> copiedSet = SortedLinkedSet.copyOf(set);

      // Assert
      assertTrue(copiedSet.isEmpty());
      assertEquals(set, copiedSet);
    }

    @Test
    @DisplayName("works properly with a SortedLinkedSet with one element")
    void givenALinkedSortedSetWithOneElementWhenCopyOfThenTheNewSetIsEqual() {
      // Arrange
      Comparator<Double> comparator = Comparator.naturalOrder();
      SortedSet<Double> set = SortedLinkedSet.of(comparator, 0.3e2);

      // Act
      SortedSet<Double> copiedSet = SortedLinkedSet.copyOf(set);

      // Assert
      assertEquals(set, copiedSet);
    }

    @Test
    @DisplayName("works properly with an SortedLinkedSet with four elements")
    void givenAListWithFourElementWhenCopyOfThenTheNewListIsEqual() {
      // Arrange
      Comparator<String> comparator = Comparator.naturalOrder();
      SortedSet<String> set = SortedLinkedSet.of(comparator, "Monday", "Tuesday", "Friday",
          "Sunday");

      // Act
      SortedSet<String> copiedSet = SortedLinkedSet.copyOf(set);

      // Assert
      assertEquals(set, copiedSet);
    }

    @Test
    @DisplayName("works properly with an SortedArraySet with four elements")
    void givenALinkedListWithFourElementWhenCopyOfThenTheNewListIsEqual() {
      // Arrange
      Comparator<String> comparator = Comparator.naturalOrder();
      SortedSet<String> set = SortedArraySet.of(comparator, "Monday", "Tuesday", "Friday",
          "Sunday");

      // Act
      SortedSet<String> copiedSet = SortedLinkedSet.copyOf(set);

      // Assert
      assertEquals(set, copiedSet);
    }
  }

  @Nested
  @DisplayName("When method insert() is called")
  class TestCasesForMethodInsert {

    @Test
    @DisplayName("if the set is empty, the element is in the set")
    void givenTheSortedSetIsEmptyWhenInsertThenTheElementIsInTheSet() {
      // Arrange
      SortedLinkedSet<Integer> set = SortedLinkedSet.empty();

      // Act
      int value = 4;
      set.insert(value);

      // Assert
      assertTrue(set.contains(value));
    }

    @Test
    @DisplayName("if the set is empty, its size becomes one")
    void givenTheSortedSetIsEmptyWhenInsertThenTheSortedSetHasOneElement() {
      // Arrange
      SortedLinkedSet<Integer> set = SortedLinkedSet.empty();

      // Act
      int value = 4;
      set.insert(value);

      // Assert
      assertEquals(1, set.size());
    }

    @Test
    @DisplayName("if the set is not empty, its size is incremented by one if the new element is not in the set")
    void givenTheSetIsNoNEmptyWhenInsertAndTheElementIsNotInTheSetThenTheSetSizeIsIncrementedByOne() {
      // Arrange
      SortedSet<Integer> set = SortedLinkedSet.of(1, 2, 3, 4, 5);
      int setSize = set.size();

      // Act
      int element = 6;
      set.insert(element);

      // Assert
      int expectedElementsInTheQueue = setSize + 1;
      assertEquals(expectedElementsInTheQueue, set.size());
    }

    @Test
    @DisplayName("if the set is not empty and the element is not repeated the new element is in the set")
    void givenTheSetIsNoNEmptyWhenInsertAndTheElementIsNotInTheSetThenTheElementIsInTheSet() {
      // Arrange
      SortedSet<Integer> set = SortedLinkedSet.of(1, 2, 3, 4, 5);

      // Act
      int element = 6;
      set.insert(element);

      // Assert
      assertTrue(set.contains(element));
    }

    @Test
    @DisplayName("if the set is not empty, its size does not increase if the new element is in the set")
    void givenTheSetIsNoNEmptyWhenInsertAndTheElementIsInTheSetThenTheSetSizeDoesNotChange() {
      // Arrange
      SortedSet<Integer> set = SortedLinkedSet.of(1, 2, 3, 4, 5);
      int setSize = set.size();

      // Act
      int element = 2;
      set.insert(element);

      // Assert
      assertEquals(setSize, set.size());
    }

    @Test
    @DisplayName("if the set has two elements, the new element is the first one ")
    void givenASetWithTwoElementsWhenInsertThenTheNewElementIsTheFirstOne() {
      // Arrange
      List<Double> elements = List.of(6.0, -1.0);
      SortedSet<Double> set = SortedLinkedSet.from(elements);

      // Act
      double newElement = 7.3;
      set.insert(newElement);

      // Assert
      String expectedOutput = "SortedLinkedSet(-1.0, 6.0, 7.3)" ;
      assertEquals(expectedOutput, set.toString());
    }

    @Test
    @DisplayName("if the set has two elements, the new element is the second one ")
    void givenASetWithTwoElementsWhenInsertThenTheNewElementIsTheSecondOne() {
      // Arrange
      List<Double> elements = List.of(6.0, -1.0);
      SortedSet<Double> set = SortedLinkedSet.from(elements);

      // Act
      double newElement = 5.8;
      set.insert(newElement);

      // Assert
      String expectedOutput = "SortedLinkedSet(-1.0, 5.8, 6.0)" ;
      assertEquals(expectedOutput, set.toString());
    }

    @Test
    @DisplayName("if the set has two elements, the new element is the third one ")
    void givenASetWithTwoElementsWhenInsertThenTheNewElementIsTheThirdOne() {
      // Arrange
      List<Double> elements = List.of(6.0, -1.0);
      SortedSet<Double> set = SortedLinkedSet.from(elements);

      // Act
      double newElement = 253.1;
      set.insert(newElement);

      // Assert
      String expectedOutput = "SortedLinkedSet(-1.0, 6.0, 253.1)" ;
      assertEquals(expectedOutput, set.toString());
    }
  }

  @Nested
  @DisplayName("A call to method delete()")
  class TestCasesForMethodDelete {
    @DisplayName("if the set is empty, it remains empty")
    @Test
    void whenAnEmptySetWhenDeleteThenTheSetRemainsEmpty() {
      // Arrange
      SortedSet<Integer> set = SortedLinkedSet.empty();

      // Act
      set.delete(25) ;

      // Assert
      assertTrue(set.isEmpty());
    }

    @DisplayName("if the set has one element, it remains unchanged if the deleted element is not present")
    @Test
    void whenASetHasOneElementWhenDeleteADifferentElementThenTheSetRemainsUnchanged() {
      // Arrange
      int element = 1 ;
      SortedSet<Integer> set = SortedLinkedSet.from(List.of(element));

      // Act
      set.delete(4) ;

      // Assert
      assertTrue(set.contains(element));
    }

    @DisplayName("if the set has four elements, it remains unchanged if the deleted element is not present")
    @Test
    void whenASetHasFourElementWhenDeleteADifferentElementThenTheSetRemainsUnchanged() {
      // Arrange
      SortedSet<Integer> set = SortedLinkedSet.from(List.of(4,3,5,0));

      // Act
      set.delete(14) ;

      // Assert
      String expectedOutput = "SortedLinkedSet(0, 3, 4, 5)" ;

      assertEquals(expectedOutput, set.toString());
    }

    @DisplayName("if the set has one element, it becomes empty if the deleted element is present")
    @Test
    void whenASetHasOneElementWhenDeleteTheSameValueThenTheSetIsEmpty() {
      // Arrange
      int element = 1 ;
      SortedSet<Integer> set = SortedLinkedSet.from(List.of(element));

      // Act
      set.delete(element) ;

      // Assert
      assertTrue(set.isEmpty());
    }

    @DisplayName("if the set has three elements, the first element is deleted")
    @Test
    void whenASetHasThreeElementWhenDeleteThenTheFirstElementIsDeleted() {
      // Arrange
      SortedSet<Integer> set = SortedLinkedSet.from(List.of(4,3,5));

      // Act
      set.delete(3) ;

      // Assert
      String expectedOutput = "SortedLinkedSet(4, 5)" ;

      assertEquals(expectedOutput, set.toString());
    }

    @DisplayName("if the set has three elements, the second element is deleted")
    @Test
    void whenASetHasThreeElementWhenDeleteThenTheSecondElementIsDeleted() {
      // Arrange
      SortedSet<Integer> set = SortedLinkedSet.from(List.of(4,3,5));

      // Act
      set.delete(4) ;

      // Assert
      String expectedOutput = "SortedLinkedSet(3, 5)" ;

      assertEquals(expectedOutput, set.toString());
    }
    @DisplayName("if the set has three elements, the third element is deleted")
    @Test
    void whenASetHasThreeElementWhenDeleteThenTheThirdElementIsDeleted() {
      // Arrange
      SortedSet<Integer> set = SortedLinkedSet.from(List.of(4,3,5));

      // Act
      set.delete(5) ;

      // Assert
      String expectedOutput = "SortedLinkedSet(3, 4)" ;

      assertEquals(expectedOutput, set.toString());
    }
  }

  @Nested
  @DisplayName("A call to method minimum()")
  class TestCasesForMethodMinimum {
    @DisplayName("raises and exception if the set is empty")
    @Test
    void whenAnEmptySetThenAnExceptionIsRaised() {
      // Arrange
      SortedSet<Double> set = SortedLinkedSet.empty();

      // Act and Assert
      Exception exception = assertThrows(NoSuchElementException.class, () -> set.minimum());
      assertEquals("minimum on empty set", exception.getMessage());
    }

    @DisplayName("returns the element in the set is this as one element")
    @Test
    void whenTheSetHasOneElementThenThatElementIsReturned() {
      // Arrange
      double element = 5.5 ;
      SortedSet<Double> set = SortedLinkedSet.from(List.of(element));

      // Act
      double minimum = set.minimum() ;

      // Assert
      assertEquals(element, minimum) ;
    }

    @DisplayName("returns the minimum of a set ")
    @Test
    void whenTheSetHasSeveralElementsThenTheMinimumOneIsReturned() {
      // Arrange
      SortedSet<Double> set = SortedLinkedSet.from(List.of(5.6, 0.2, 4.9, 3.7));

      // Act
      double minimum = set.minimum() ;

      // Assert
      assertEquals(0.2, minimum) ;
    }
  }

  @Nested
  @DisplayName("A call to method maximum()")
  class TestCasesForMethodMaximum {
    @DisplayName("raises and exception if the set is empty")
    @Test
    void whenAnEmptySetThenAnExceptionIsRaised() {
      // Arrange
      SortedSet<Double> set = SortedLinkedSet.empty();

      // Act and Assert
      Exception exception = assertThrows(NoSuchElementException.class, () -> set.maximum());
      assertEquals("maximum on empty set", exception.getMessage());
    }

    @DisplayName("returns the element in the set is this as one element")
    @Test
    void whenTheSetHasOneElementThenThatElementIsReturned() {
      // Arrange
      double element = 5.5 ;
      SortedSet<Double> set = SortedLinkedSet.from(List.of(element));

      // Act
      double maximum = set.minimum() ;

      // Assert
      assertEquals(element, maximum) ;
    }

    @DisplayName("returns the maximum of a set ")
    @Test
    void whenTheSetHasSeveralElementsThenTheMaximumOneIsReturned() {
      // Arrange
      SortedSet<Double> set = SortedLinkedSet.from(List.of(5.6, 0.2, 4.9, 3.7));

      // Act
      double maximum = set.maximum() ;

      // Assert
      assertEquals(5.6, maximum) ;
    }
  }

  @Nested
  @DisplayName("The union() method")
  class TestCasesForMethodUnion {
    @DisplayName("throws an exception if the comparators of the sets are not equal")
    @Test
    void whenTheComparatorsOfTheSetsAreNotEqualThenThrowsAnException() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.of(Comparator.naturalOrder(), "red", "white", "blue") ;
      SortedSet<String> set2 = SortedLinkedSet.of(Comparator.reverseOrder(), "blue", "red", "white");

      // Act & Assert
      Exception exception = assertThrows(IllegalArgumentException.class, () -> SortedLinkedSet.union(set1, set2));
      assertEquals("union: both sorted sets must use same comparator", exception.getMessage());
    }

    @DisplayName("returns an empty set when the two sets are empty")
    @Test
    void whenTheSetsAreEmptyThenReturnsAnEmptySet() {
      // Arrange
      SortedSet<Double> set1 = SortedLinkedSet.empty();
      SortedSet<Double> set2 = SortedLinkedSet.empty();

      // Act
      SortedSet<Double> unionSet = SortedLinkedSet.union(set1, set2) ;

      // Assert
      assertTrue(unionSet.isEmpty());
    }

    @DisplayName("returns the first set is the second one empty")
    @Test
    void whenTheSecondSetIsEmptyThenReturnsTheFirstSet() {
      // Arrange
      SortedSet<Integer> set1 = SortedLinkedSet.from(List.of(5,2,3,9)) ;
      SortedSet<Integer> set2 = SortedLinkedSet.empty();

      // Act
      SortedSet<Integer> unionSet = SortedLinkedSet.union(set1, set2) ;

      // Assert
      assertEquals(set1, unionSet);
    }

    @DisplayName("returns the second set is the first one empty")
    @Test
    void whenTheFirstSetIsEmptyThenReturnsTheSecondSet() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.empty() ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "green", "white"));

      // Act
      SortedSet<String> unionSet = SortedLinkedSet.union(set1, set2) ;

      // Assert
      assertEquals(set2, unionSet);
    }

    @DisplayName("returns the expected set is the both sets are disjoint")
    @Test
    void whenTheSetsAreDisjointThenReturnsTheUnionSet() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.from(List.of("purple", "red")) ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "green", "white"));

      // Act
      SortedSet<String> unionSet = SortedLinkedSet.union(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(blue, green, purple, red, white)" ;
      assertEquals(expectedResult, unionSet.toString());
    }

    @DisplayName("returns the expected set is the both sets share one element")
    @Test
    void whenTheSetsShareAnElementThenReturnsTheUnionSet() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.from(List.of("purple", "red")) ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "red", "white"));

      // Act
      SortedSet<String> unionSet = SortedLinkedSet.union(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(blue, purple, red, white)" ;
      assertEquals(expectedResult, unionSet.toString());
    }

    @DisplayName("returns the expected set is the both sets are equals")
    @Test
    void whenTheSetsAreEqualsThenReturnsOneOfThem() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.from(List.of("red", "white", "blue")) ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "red", "white"));

      // Act
      SortedSet<String> unionSet = SortedLinkedSet.union(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(blue, red, white)" ;
      assertEquals(expectedResult, unionSet.toString());
    }

    @DisplayName("returns the expected set is both sets are equals and one of them is a SortedArraySet")
    @Test
    void givenTheSetsAreEqualsWhenOneOfThemIsSortedArrayLinkedSetThenReturnsASortedLinkedSet() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.from(List.of("red", "white", "blue")) ;
      SortedSet<String> set2 = SortedArraySet.from(List.of("blue", "red", "white"));

      // Act
      SortedSet<String> unionSet = SortedLinkedSet.union(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(blue, red, white)" ;
      assertEquals(expectedResult, unionSet.toString());
    }

    @DisplayName("returns the expected set is the both sets share one element and one of them is a SortedArraySet")
    @Test
    void givenTwoSetsSharingAnElementWhenOneOfThemIsSortedLinkedLinkedSetThenReturnsASortedArraySet() {
      // Arrange
      SortedSet<String> set1 = SortedArraySet.from(List.of("purple", "red")) ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "red", "white"));

      // Act
      SortedSet<String> unionSet = SortedLinkedSet.union(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(blue, purple, red, white)" ;
      assertEquals(expectedResult, unionSet.toString());
    }
  }

  @Nested
  @DisplayName("The intersection() method")
  class TestCasesForMethodIntersection {
    @DisplayName("throws an exception if the comparators of the sets are not equal")
    @Test
    void whenTheComparatorsOfTheSetsAreNotEqualThenThrowsAnException() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.of(Comparator.naturalOrder(), "red", "white", "blue") ;
      SortedSet<String> set2 = SortedLinkedSet.of(Comparator.reverseOrder(), "blue", "red", "white");

      // Act & Assert
      Exception exception = assertThrows(IllegalArgumentException.class, () -> SortedLinkedSet.intersection(set1, set2));
      assertEquals("intersection: both sorted sets must use same comparator", exception.getMessage());
    }

    @DisplayName("returns an empty set when the two sets are empty")
    @Test
    void whenTheSetsAreEmptyThenReturnsAnEmptySet() {
      // Arrange
      SortedSet<Double> set1 = SortedLinkedSet.empty();
      SortedSet<Double> set2 = SortedLinkedSet.empty();

      // Act
      SortedSet<Double> intersectionSet = SortedLinkedSet.intersection(set1, set2) ;

      // Assert
      assertTrue(intersectionSet.isEmpty());
    }

    @DisplayName("returns an empty set is the second set empty")
    @Test
    void whenTheSecondSetIsEmptyThenReturnsAnEmptySet() {
      // Arrange
      SortedSet<Integer> set1 = SortedLinkedSet.from(List.of(5,2,3,9)) ;
      SortedSet<Integer> set2 = SortedLinkedSet.empty();

      // Act
      SortedSet<Integer> intersectionSet = SortedLinkedSet.intersection(set1, set2) ;

      // Assert
      assertTrue(intersectionSet.isEmpty());
    }

    @DisplayName("returns an empty set is the first one empty")
    @Test
    void whenTheFirstSetIsEmptyThenReturnsAnEmptySet() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.empty() ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "green", "white"));

      // Act
      SortedSet<String> intersectionSet = SortedLinkedSet.intersection(set1, set2) ;

      // Assert
      assertTrue(intersectionSet.isEmpty());
    }

    @DisplayName("returns a set with an element when the two sets contains only the same element")
    @Test
    void whenTheSetsContainTheSameElementThenReturnsASetWithThatElement() {
      // Arrange
      SortedSet<Double> set1 = SortedLinkedSet.from(List.of(1.0));
      SortedSet<Double> set2 = SortedLinkedSet.from(List.of(1.0));

      // Act
      SortedSet<Double> intersectionSet = SortedLinkedSet.intersection(set1, set2) ;

      // Assert
      assertTrue(intersectionSet.contains(1.0));
      assertEquals(1, intersectionSet.size());
    }

    @DisplayName("returns the expected set is the both sets share two elements")
    @Test
    void whenTheSetsShareAnElementThenReturnsTheUnionSet() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.from(List.of("purple", "red", "blue", "orange")) ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "red", "white", "pink"));

      // Act
      SortedSet<String> intersectionSet = SortedLinkedSet.intersection(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(blue, red)" ;
      assertEquals(expectedResult, intersectionSet.toString());
    }

    @DisplayName("returns the expected set is the both sets share two elements and one of them is a SortedArraySet")
    @Test
    void givenTheSetsShareAnElementWhenOneIsASortedLinkedSetThenReturnsTheASortedArraySet() {
      // Arrange
      SortedSet<String> set1 = SortedArraySet.from(List.of("purple", "red", "blue", "orange")) ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "red", "white", "pink"));

      // Act
      SortedSet<String> intersectionSet = SortedLinkedSet.intersection(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(blue, red)" ;
      assertEquals(expectedResult, intersectionSet.toString());
    }
  }

  @Nested
  @DisplayName("The difference() method")
  class TestCasesForMethodDifference {

    @DisplayName("throws an exception if the comparators of the sets are not equal")
    @Test
    void whenTheComparatorsOfTheSetsAreNotEqualThenThrowsAnException() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.of(Comparator.naturalOrder(), "red", "white",
          "blue");
      SortedSet<String> set2 = SortedLinkedSet.of(Comparator.reverseOrder(), "blue", "red",
          "white");

      // Act & Assert
      Exception exception = assertThrows(IllegalArgumentException.class,
          () -> SortedLinkedSet.difference(set1, set2));
      assertEquals("difference: both sorted sets must use same comparator", exception.getMessage());
    }

    @DisplayName("returns an empty set when the two sets are empty")
    @Test
    void whenTheSetsAreEmptyThenReturnsAnEmptySet() {
      // Arrange
      SortedSet<Double> set1 = SortedLinkedSet.empty();
      SortedSet<Double> set2 = SortedLinkedSet.empty();

      // Act
      SortedSet<Double> differenceSet = SortedLinkedSet.difference(set1, set2) ;

      // Assert
      assertTrue(differenceSet.isEmpty());
    }

    @DisplayName("returns the first set is the second one empty")
    @Test
    void whenTheSecondSetIsEmptyThenReturnsTheFirstSet() {
      // Arrange
      SortedSet<Integer> set1 = SortedLinkedSet.from(List.of(5,2,3,9)) ;
      SortedSet<Integer> set2 = SortedLinkedSet.empty();

      // Act
      SortedSet<Integer> differenceSet = SortedLinkedSet.difference(set1, set2) ;

      // Assert
      assertEquals(set1, differenceSet);
    }

    @DisplayName("returns an empty set is the first one empty")
    @Test
    void whenTheFirstSetIsEmptyThenReturnsTheSecondSet() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.empty() ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "green", "white"));

      // Act
      SortedSet<String> differenceSet = SortedLinkedSet.difference(set1, set2) ;

      // Assert
      assertTrue(differenceSet.isEmpty());
    }

    @DisplayName("returns an empty set with when the two sets contains only the same element")
    @Test
    void whenTheSetsContainTheSameElementThenReturnsAnEmptySet() {
      // Arrange
      SortedSet<Double> set1 = SortedLinkedSet.from(List.of(1.0));
      SortedSet<Double> set2 = SortedLinkedSet.from(List.of(1.0));

      // Act
      SortedSet<Double> differenceSet = SortedLinkedSet.difference(set1, set2) ;

      // Assert
      assertTrue(differenceSet.isEmpty());
    }

    @DisplayName("returns the first set when the two sets contains only an element that is different")
    @Test
    void whenTheSetsContainADifferentElementThenReturnsASetWithTheElementOfTheFirstSet() {
      // Arrange
      SortedSet<Double> set1 = SortedLinkedSet.from(List.of(2.0));
      SortedSet<Double> set2 = SortedLinkedSet.from(List.of(1.0));

      // Act
      SortedSet<Double> differenceSet = SortedLinkedSet.difference(set1, set2) ;

      // Assert
      assertTrue(differenceSet.contains(2.0));
      assertEquals(1, differenceSet.size());
    }

    @DisplayName("returns the expected set is the both sets share two elements")
    @Test
    void whenTheSetsShareAnElementThenReturnsTheUnionSet() {
      // Arrange
      SortedSet<String> set1 = SortedLinkedSet.from(List.of("purple", "red", "blue", "orange")) ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "red", "white", "pink"));

      // Act
      SortedSet<String> differenceSet = SortedLinkedSet.difference(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(orange, purple)" ;
      assertEquals(expectedResult, differenceSet.toString());
    }

    @DisplayName("returns the expected set is the both sets share two elements and one of them is a SortedArraySet")
    @Test
    void givenTheSetsShareAnElementWhenOneIsASortedLinkedSetThenReturnsASortedArraySet() {
      // Arrange
      SortedSet<String> set1 = SortedArraySet.from(List.of("purple", "red", "blue", "orange")) ;
      SortedSet<String> set2 = SortedLinkedSet.from(List.of("blue", "red", "white", "pink"));

      // Act
      SortedSet<String> differenceSet = SortedLinkedSet.difference(set1, set2) ;

      // Assert
      String expectedResult = "SortedLinkedSet(orange, purple)" ;
      assertEquals(expectedResult, differenceSet.toString());
    }
  }
}