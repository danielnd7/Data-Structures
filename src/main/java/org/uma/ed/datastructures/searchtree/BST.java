package org.uma.ed.datastructures.searchtree;


import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

import org.uma.ed.datastructures.either.Either;
import org.uma.ed.datastructures.stack.ArrayStack;
import org.uma.ed.datastructures.stack.Stack;

/**
 * Search tree implemented using an unbalanced binary search tree. Nodes are sorted according to their keys and keys are
 * sorted using the provided comparator or their natural order if no comparator is provided.
 *
 * @param <K> Type of keys.
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BST<K> implements SearchTree<K> {
    private static final class Node<K> {
        K key;
        Node<K> left, right;

        // creates a singleton node with given key
        Node(K key) {
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }

  /*
   INVARIANT:
   - Keys in left child are smaller than key in node.
   - Keys in right child are greater than key in node.
   - There are no duplicate keys in tree.
   - size is number of nodes in tree.
  */

    private final Comparator<K> comparator;
    private Node<K> root;
    private int size;

    /**
     * Creates an empty unbalanced binary search tree. Keys are sorted according to provided comparator.
     * <p> Time complexity: O(1)
     *
     * @param comparator Comparator defining order of keys in this search tree.
     */
    public BST(Comparator<K> comparator) {
        this(comparator, null, 0);
    }

    private BST(Comparator<K> comparator, Node<K> root, int size) {
        this.comparator = comparator;
        this.root = root;
        this.size = size;
    }

    /**
     * Creates an empty unbalanced binary search tree. Keys are sorted according to their natural order.
     * <p> Time complexity: O(1)
     */
    public static <K extends Comparable<? super K>> BST<K> empty() {
        return new BST<K>(Comparator.naturalOrder());
    }

    /**
     * Creates an empty unbalanced binary search tree. Keys are sorted according to provided comparator.
     * <p> Time complexity: O(1)
     *
     * @param comparator Comparator defining order of keys in this search tree.
     */
    public static <K> BST<K> empty(Comparator<K> comparator) {
        return new BST<>(comparator);
    }

    /**
     * Returns a new binary search tree with same elements and same structure as argument.
     * <p> Time complexity: O(n²)
     *
     * @param that binary search tree to be copied.
     * @return a new BST with same elements and structure as {@code that}.
     */
    public static <K> BST<K> copyOf(SearchTree<K> that) { // ????

        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns a new unbalanced binary search tree with same elements and same structure as argument.
     * <p> Time complexity: O(n)
     *
     * @param that binary search tree to be copied.
     * @return a new BST with same elements and structure as {@code that}.
     */
    public static <K> BST<K> copyOf(BST<K> that) {
        return new BST<>(that.comparator, copyOf(that.root), that.size);
    }

    private static <K> Node<K> copyOf(Node<K> node) { // checked
        if (node == null){
            return null;
        } else {
            Node<K> nodeNew = new Node<>(node.key);
            nodeNew.left = copyOf(node.left);
            nodeNew.right = copyOf(node.right);
            return nodeNew;
        }
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public Comparator<K> comparator() {
        return comparator;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return root == null;
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
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public int height() {
        return height(root);
    }

    private static int height(Node<?> node) {
        return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void insert(K key) {
        root = insert(root, key);
    }

    // returns modified tree
    private Node<K> insert(Node<K> node, K key) { // checked
        if (node == null){
            node = new Node<>(key);
            size++;
        } else {
            int comparison = comparator.compare(key, node.key);
            if (comparison < 0){
                node.left = insert(node.left, key);
            } else if (comparison > 0){
                node.right = insert(node.right, key);
            } else {
                node.key = key;  // key was found
            }
        }
        return node;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public K search(K key) {
        return search(root, key);
    }

    private K search(Node<K> node, K key) { // checked
        K element;
        if (node == null){ // empty bst
            element = null;

        } else {
            int comparison = comparator.compare(node.key, key);
            if (comparison == 0){
                element = node.key; // key was found
            } else if (comparison < 0){
                element = search(node.right, key);
            } else {
                element = search(node.left, key);
            }
        }
        return element;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public boolean contains(K key) {
        return search(key) != null;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void delete(K key) {
        root = delete(root, key);
    }

    // returns modified tree
    private Node<K> delete(Node<K> node, K key) {
        if (node != null) { // key not found; do nothing

            int cmp = comparator.compare(key, node.key);
            if (cmp < 0) {
                node.left = delete(node.left, key);
            } else if (cmp > 0) {
                node.right = delete(node.right, key);
            } else {
                node = delete(node);
            }
        }
        return node;
    }

    // returns modified tree
    private Node<K> delete(Node<K> node) { // ?????
        if (node.left == null) {
            node = node.right;
        } else if (node.right == null) {
            node = node.left;
        } else {
            node.right = split(node.right, node);
        }
        size--;
        return node;
    }

    /**
     * Precondition: node is a non-empty tree. Removes minimum key from tree rooted at node. Before deletion, key is saved
     * into temp node. Returns modified tree (without min key).
     */
    private static <K> Node<K> split(Node<K> node, Node<K> temp) { // ?????
        if (node.left == null) {
            // min node found, so copy min key
            temp.key = node.key;
            return node.right; // remove node
        } else {
            // remove min from left subtree
            node.left = split(node.left, temp);
            return node;
        }
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public K minimum() { // checked
        if (isEmpty()){
            throw new NoSuchElementException("minimum on empty bst");
        }
        Node<K> node = root;
        while (node.left != null){
            node = node.left;
        }
        return node.key;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public K maximum() { // checked
        if (isEmpty()){
          throw new EmptySearchTreeException("minimum on empty tree");
        }
        Node<K> node = root;
        while (node.right != null){
          node = node.right;
        }
        return node.key;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void deleteMinimum() { // checked
        if (isEmpty()){
            throw new NoSuchElementException("deleteMinimum on empty bst");
        }

        Node<K> node = root;
        Node<K> parent = null;

        while (node.left != null){
            parent = node;
            node = node.left;
        }

        if (parent != null){
            parent.left = node.right;
        } else {
            root = root.right;
        }
        size--;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void deleteMaximum() { // checked
        if (isEmpty()){
            throw new NoSuchElementException("deleteMaximum on empty bst");
        }
        Node<K> node = root;
        Node<K> parent = null;

        while (node.right != null){
            parent = node;
            node = node.right;
        }
        if (parent != null){
            parent.right = node.left;
        } else {
            root = root.left;
        }
        size--;
    }

    /**
     * {@inheritDoc}
     * <p> Time complexity: from O(log n) to O(n)
     */
    @Override
    public void deleteOrUpdateOrInsert(K key, Predicate<K> delete, Function<K, K> update, boolean insert) {
        DeleteUpdateOrInsert dui = new DeleteUpdateOrInsert();
        root = dui.deleteOrUpdateOrInsert(root, key, delete, update, insert);
        if (dui.reinsert) {
            insert(key);
        }
    }

    private final class DeleteUpdateOrInsert {
        boolean reinsert = false;

        Node<K> deleteOrUpdateOrInsert(Node<K> node, K key, Predicate<K> delete, Function<K, K> update, boolean insert) {
            if (node == null) {
                if (insert) {
                    node = new Node<>(key);
                    size++;
                }
            } else {
                int cmp = comparator.compare(key, node.key);
                if (cmp < 0) {
                    node.left = deleteOrUpdateOrInsert(node.left, key, delete, update, insert);
                } else if (cmp > 0) {
                    node.right = deleteOrUpdateOrInsert(node.right, key, delete, update, insert);
                } else {
                    if (delete.test(node.key)) {
                        node = delete(node);
                    } else {
                        K newKey = update.apply(node.key);
                        if (comparator.compare(newKey, node.key) == 0) {
                            node.key = newKey;
                        } else {
                            node = delete(node);
                            reinsert = true;
                        }
                    }
                }
            }
            return node;
        }
    }

    // An iterator on keys in tree
    private abstract class Traversal implements Iterator<K> {
        Stack<Either<Node<K>, Node<K>>> stack = ArrayStack.empty();

        public Traversal() {
            if (root != null) {
                save(root);
            }
        }

        abstract void save(Node<K> node);

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Either<Node<K>, Node<K>> either = stack.top();
            stack.pop();

            while (either.isRight()) {
                Node<K> node = either.right();
                save(node);
                either = stack.top();
                stack.pop();
            }
            return either.left().key;
        }
    }

    private final class InOrderIterator extends Traversal {
        void save(Node<K> node) {
            // in reverse order, cause stack is LIFO
            if (node.right != null) {
                stack.push(Either.right(node.right));
            }
            stack.push(Either.left(node));
            if (node.left != null) {
                stack.push(Either.right(node.left));
            }
        }
    }

    private final class PreOrderIterator extends Traversal {
        void save(Node<K> node) {
            // in reverse order, cause stack is LIFO
            if (node.right != null) {
                stack.push(Either.right(node.right));
            }
            if (node.left != null) {
                stack.push(Either.right(node.left));
            }
            stack.push(Either.left(node));
        }
    }

    private final class PostOrderIterator extends Traversal {
        void save(Node<K> node) {
            // in reverse order, cause stack is LIFO
            stack.push(Either.left(node));
            if (node.right != null) {
                stack.push(Either.right(node.right));
            }
            if (node.left != null) {
                stack.push(Either.right(node.left));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<K> inOrder() {
        return InOrderIterator::new;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<K> preOrder() {
        return PreOrderIterator::new;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<K> postOrder() {
        return PostOrderIterator::new;
    }

    /**
     * Returns representation of this search tree as a String.
     */
    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className).append("(");
        toString(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void toString(StringBuilder sb, Node<?> node) {
        if (node == null) {
            sb.append("null");
        } else {
            String className = node.getClass().getSimpleName();
            sb.append(className).append("(");
            toString(sb, node.left);
            sb.append(", ");
            sb.append(node.key);
            sb.append(", ");
            toString(sb, node.right);
            sb.append(")");
        }
    }
}
