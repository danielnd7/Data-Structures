
package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.priorityqueue.JDKPriorityQueue;
import org.uma.ed.datastructures.priorityqueue.LinkedPriorityQueue;
import org.uma.ed.datastructures.priorityqueue.PriorityQueue;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;
import org.uma.ed.datastructures.tuple.Tuple2;

/**
 * Class for computing shortest paths in a weighted graph using Dijkstra's algorithm.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class Dijkstra {
    /**
     * Computes costs of shortest paths from a source vertex to all other vertices in a weighted graph.
     *
     * @param weightedGraph The weighted graph.
     * @param source        The source vertex.
     * @param <V>           The type of the vertices in the graph.
     * @return a dictionary where keys are vertices and values are the minimum cost to reach them from the source.
     */
    public static <V> Dictionary<V, Integer> dijkstra(WeightedGraph<V, Integer> weightedGraph, V source) {

        // Class for representing an extension of a path from vertex source to
        // vertex destination and total cost of reaching destination.
        // This class implements Comparable interface to allow sorting of extensions based on total cost.


        record Extension<V>(V source, V destination, Integer totalCost) implements Comparable<Extension<V>> {
            @Override
            // Best extension is the one with the smallest total cost.
            // Will be used later by the priority queue.
            public int compareTo(Extension that) {
                return this.totalCost.compareTo(that.totalCost);
            }

            static <V> Extension<V> of(V source, V destination, Integer totalCost) {
                return new Extension<>(source, destination, totalCost);
            }
        }

        // Initialization

        Set<V> optimalVertices = JDKHashSet.of(source);

        Set<V> vertices = JDKHashSet.copyOf(weightedGraph.vertices());
        vertices.delete(source);

        Dictionary<V, Integer> dictionary = JDKHashDictionary.empty();
        dictionary.insert(source, 0);


        while (!vertices.isEmpty()){
            // Create a PQ of extensions
            PriorityQueue<Extension<V>> priorityQueue = new LinkedPriorityQueue<>(Extension::compareTo);

            // put in the PQ extensions connecting one vertex in verticesOpt to vertices in vertices
            for (var optimalVertex : optimalVertices){
                for (var successor : weightedGraph.successors(optimalVertex)){

                    V destination = successor.vertex();
                    Integer weight = successor.weight();

                    if (vertices.contains(destination)){
                        priorityQueue.enqueue(Extension.of(optimalVertex, destination, dictionary.valueOf(optimalVertex) + weight));
                    }

                    // extract best extension
                    var bestExtension = priorityQueue.first();

                    var bestDestination = bestExtension.destination;
                    var bestTotalCost = bestExtension.totalCost;

                    dictionary.insert(bestDestination, bestTotalCost);
                }
            }

        }
        return dictionary;
    }

    /**
     * Computes shortest paths (and their costs) from a source vertex to all other vertices in a weighted graph.
     *
     * @param weightedGraph The weighted graph.
     * @param source        The source vertex.
     * @param <V>           The type of the vertices in the graph.
     * @return a dictionary where keys are vertices and values are pairs with the minimum cost to reach them from the
     * source and the path to reach them.
     */
    public static <V> Dictionary<V, Tuple2<Integer, List<V>>> dijkstraPaths(
            WeightedGraph<V, Integer> weightedGraph, V source) {

        // Class for representing an extension of a path from vertex source to
        // vertex destination and total cost of reaching destination plus path from source to destination.
        // This class implements Comparable interface to allow sorting of extensions based on total cost.
        record Extension<V>(V source, V destination, Integer totalCost,
                            List<V> path) implements Comparable<Extension<V>> {
            @Override
            // Best extension is the one with the smallest total cost.
            // Will be used later by the priority queue.
            public int compareTo(Extension that) {
                return this.totalCost.compareTo(that.totalCost);
            }

            static <V> Extension<V> of(V source, V destination, Integer totalCost, List<V> path) {
                return new Extension<>(source, destination, totalCost, path);
            }
        }

       // Initialization

        /*

        Set<V> optimalVertices = JDKHashSet.of(source);

        Set<V> vertices = JDKHashSet.copyOf(weightedGraph.vertices());
        vertices.delete(source);

        Dictionary<V, Tuple2<Integer, List<V>>> dictionary = JDKHashDictionary.empty();
        dictionary.insert(source, 0);


        while (!vertices.isEmpty()){
            // Create a PQ of extensions
            PriorityQueue<Extension<V>> priorityQueue = new LinkedPriorityQueue<>(Extension::compareTo);

            // put in the PQ extensions connecting one vertex in verticesOpt to vertices in vertices
            for (var optimalVertex : optimalVertices){
                for (var successor : weightedGraph.successors(optimalVertex)){

                    V destination = successor.vertex();
                    Integer weight = successor.weight();

                    if (vertices.contains(destination)){
                        priorityQueue.enqueue(new Extension<>(optimalVertex, destination, dictionary.valueOf(optimalVertex) + weight));
                    }
                    // extract best extension
                    var bestExtension = priorityQueue.first();

                    var bestDestination = bestExtension.destination;
                    var bestTotalCost = bestExtension.totalCost;

                    dictionary.insert(bestDestination, bestTotalCost);
                }
            }

        }
        return dictionary;
        */
        throw new RuntimeException("not finished yet");
    }


    // improvement : remove optimalVertices set as we can use a dictionary keys for get it
}
