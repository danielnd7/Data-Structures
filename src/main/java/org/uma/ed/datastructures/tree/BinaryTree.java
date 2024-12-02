package org.uma.ed.datastructures.tree;

import org.uma.ed.datastructures.list.ArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.queue.ArrayQueue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * This class defines different methods to process binary trees. A binary tree is represented by a root node. If the
 * tree is empty, this root node is null. Otherwise, the root node contains an element and references to left and right
 * children nodes.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BinaryTree {
    /**
     * This class represents a node in a binary tree. Each node contains an element and references to left and right
     * children nodes.
     *
     * @param <E>
     */
    public static final class Node<E> {
        private final E element;
        private final Node<E> left, right;

        /**
         * Creates a node with an element and no children.
         *
         * @param element Element in node.
         */
        public Node(E element) {
            this(element, null, null);
        }

        /**
         * Creates a node with an element and left and right children.
         *
         * @param element The element in the node.
         * @param left    The left child of the node.
         * @param right   The right child of the node.
         */
        public Node(E element, Node<E> left, Node<E> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        /**
         * Creates a node with an element and no children.
         *
         * @param element The element in the node.
         * @param <T>     The type of the element in the node.
         * @return A new node with given element and no children.
         */
        public static <T> Node<T> of(T element) {
            return new Node<>(element);
        }

        /**
         * Creates a node with an element and left and right children.
         *
         * @param element The element in the node.
         * @param left    The left child of the node.
         * @param right   The right child of the node.
         * @param <T>     The type of the element in the node.
         * @return A new node with given element and children.
         */
        public static <T> Node<T> of(T element, Node<T> left, Node<T> right) {
            return new Node<>(element, left, right);
        }
    }

    /**
     * Returns the number of nodes in a binary tree.
     *
     * @param root The root node of the tree.
     * @return The number of nodes in the tree.
     */
    public static int size(Node<?> root) {
        return root == null ? 0 : 1 + size(root.left) + size(root.right);
    }

    /**
     * Returns the height of a binary tree.
     *
     * @param root The root node of the tree.
     * @return The height of the tree.
     */
    public static int height(Node<?> root) {
        return root == null ? 0 : 1 + Math.max(height(root.left), height(root.right));
    }

    /**
     * Returns the sum of the elements in a binary tree of integers.
     *
     * @param root The root node of the tree.
     * @return The sum of the elements in the tree.
     */
    public static int sum(Node<Integer> root) {
        return  root == null ? 0 : root.element + sum(root.left) + sum(root.right);
    }

    /**
     * Returns the maximum element in a binary tree of integers.
     *
     * @param root       The root node of the tree.
     * @param comparator Comparator to compare elements.
     * @return The maximum element in the tree.
     */
    public static int maximum(Node<Integer> root, Comparator<Integer> comparator) {
        if (root == null){
            return Integer.MIN_VALUE;
        } else {
            int max = root.element;

            // set the maximumChild
            int maximumChild = maximum(root.left, comparator);
            int rightChild = maximum(root.right, comparator);
            if (comparator.compare(maximumChild, rightChild) < 0){
                maximumChild = rightChild;
            }

            // compare maximumChild with the root
            if (comparator.compare(max, maximumChild) < 0){
                max = maximumChild;
            }

            return max;
        }
    }

    /**
     * Returns the number of times an element appears in a binary tree.
     *
     * @param root    The root node of the tree.
     * @param element The element to count.
     * @return The number of times the element appears in the tree.
     */
    public static int count(Node<Integer> root, int element) {
        int counter = 0;
        if (root == null){
            return 0;
        } else {
            if (root.element == element){
                counter = 1;
            }
            counter = counter + count(root.left, element) + count(root.right, element);
        }
        return counter;
    }

    /**
     * Returns the leaves of a binary tree.
     *
     * @param root The root node of the tree.
     * @return The leaves of the tree.
     */
    public static <T> List<T> leaves(Node<T> root) {
        List<T> leaves = ArrayList.empty();
        leaves(root, leaves);
        return leaves;
    }

    /**
     * Auxiliary method to compute leaves of a binary tree.
     *
     * @param root   The root node of the tree.
     * @param leaves List to store leaves.
     * @param <T>    The type of elements in the tree.
     */
    private static <T> void leaves(Node<T> root, List<T> leaves) {
        if (root != null){
            if (root.right == null && root.left == null){ // base case ( a leaf)
                leaves.append(root.element);

            } else { // general case
                leaves(root.left, leaves);
                leaves(root.right, leaves);
            }
        }
    }

    /**
     * Returns the preorder traversal of a binary tree.
     *
     * @param root The root node of the tree.
     * @return The preorder traversal of the tree.
     */
    public static <T> List<T> preorder(Node<T> root) {
        List<T> traversal = ArrayList.empty();
        preorder(root, traversal);
        return traversal;
    }

    /**
     * Auxiliary method to compute preorder traversal of a binary tree.
     *
     * @param root      The root node of the tree.
     * @param traversal List to store traversal.
     * @param <T>       The type of elements in the tree.
     */
    private static <T> void preorder(Node<T> root, List<T> traversal) {
        if (root != null){
            traversal.append(root.element);
            preorder(root.left, traversal);
            preorder(root.right, traversal);
        }
    }

    /**
     * Returns the postorder traversal of a binary tree.
     *
     * @param root The root node of the tree.
     * @return The postorder traversal of the tree.
     */
    public static <T> List<T> postorder(Node<T> root) {
        List<T> traversal = ArrayList.empty();
        postorder(root, traversal);
        return traversal;
    }

    /**
     * Auxiliary method to compute postorder traversal of a binary tree.
     *
     * @param root      The root node of the tree.
     * @param traversal List to store traversal.
     * @param <T>       The type of elements in the tree.
     */
    private static <T> void postorder(Node<T> root, List<T> traversal) {
        if (root != null){
            postorder(root.left, traversal);
            postorder(root.right, traversal);
            traversal.append(root.element);
        }
    }

    /**
     * Returns the inorder traversal of a binary tree.
     *
     * @param root The root node of the tree.
     * @return The inorder traversal of the tree.
     */
    public static <T> List<T> inorder(Node<T> root) {
        List<T> traversal = ArrayList.empty();
        inorder(root, traversal);
        return traversal;
    }

    /**
     * Auxiliary method to compute inorder traversal of a binary tree.
     *
     * @param root      The root node of the tree.
     * @param traversal List to store traversal.
     * @param <T>       The type of elements in the tree.
     */
    private static <T> void inorder(Node<T> root, List<T> traversal) {
        if (root != null){
            inorder(root.left, traversal);
            traversal.append(root.element);
            inorder(root.right, traversal);
        }



    }

    /**
     * Returns the breadth-first traversal of a binary tree.
     *
     * @param root The root node of the tree.
     * @return The breadth-first traversal of the tree.
     */
    public static <T> List<T> breadthFirst(Node<T> root) {
        List<T> list = new ArrayList<>();

        ArrayQueue<Node<T>> queue = ArrayQueue.of(root);

        while (!queue.isEmpty()){
            Node<T> node = queue.first();
            list.append(node.element);
            queue.dequeue();

            if (node.left != null) {
                queue.enqueue(node.left);
            }
            if (node.right != null) {
                queue.enqueue(node.right);
            }

        }
        return list;
    }
}
