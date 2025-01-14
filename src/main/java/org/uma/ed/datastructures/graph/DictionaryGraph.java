package org.uma.ed.datastructures.graph;

import java.util.StringJoiner;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;

/**
 * Class for undirected graphs implemented with a dictionary
 *
 * @param <V> Type for vertices in graph
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DictionaryGraph<V> implements Graph<V> {
    private final Set<V> vertices;                // set with all vertices in graph
    private final Dictionary<V, Set<V>> diEdges;  // dictionary with sources as keys and Set of destinations as values

    /**
     * Creates an empty graph.
     */
    public DictionaryGraph() {
        vertices = JDKHashSet.empty();
        diEdges = JDKHashDictionary.empty();
    }

    /**
     * Creates an empty graph.
     *
     * @param <V> Type for vertices in graph.
     * @return An empty DictionaryGraph.
     */
    public static <V> DictionaryGraph<V> empty() {
        return new DictionaryGraph<>();
    }

    /**
     * Creates a graph with given vertices and edges.
     *
     * @param vertices vertices to add to graph.
     * @param edges    edges to add to graph.
     * @param <V>      Type for vertices in graph.
     * @return A DictionaryGraph with given vertices and edges.
     */
    public static <V> DictionaryGraph<V> of(Set<V> vertices, Set<Edge<V>> edges) {
        DictionaryGraph<V> graph = new DictionaryGraph<>();
        for (V vertex : vertices) {
            graph.addVertex(vertex);
        }
        for (Edge<V> edge : edges) {
            graph.addEdge(edge.vertex1(), edge.vertex2());
        }
        return graph;
    }

    /**
     * Creates a graph with same vertices and edges as given graph.
     *
     * @param graph Graph to copy.
     * @param <V>   Type for vertices in graph.
     * @return A DictionaryGraph with same vertices and edges as given graph.
     */
    public static <V> DictionaryGraph<V> copyOf(Graph<V> graph) { // done ?
        DictionaryGraph<V> newGraph = of(graph.vertices(), graph.edges());

        return newGraph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() { // done
        return vertices.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addVertex(V vertex) {  // done in class
        vertices.insert(vertex);
         if (!diEdges.isDefinedAt(vertex)){
              diEdges.insert(vertex, JDKHashSet.empty());
         }

    }

    private void addDiEdge(V source, V destination) { // done in class
        diEdges.valueOf(source).insert(destination);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(V vertex1, V vertex2) { // done in class
        if (!vertices.contains(vertex1)){
            throw new GraphException("first vertex is not in graph");
        }
        if (!vertices.contains(vertex2)){
            throw new GraphException("second vertex is not in graph");
        }
        addDiEdge(vertex1, vertex2);
        addDiEdge(vertex2, vertex1);
    }

    private void deleteDiEdge(V source, V destination) { // done
        diEdges.valueOf(source).delete(destination);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEdge(V vertex1, V vertex2) { // done
        if (!vertices.contains(vertex1)){
            throw new GraphException("first vertex is not in graph");
        }
        if (!vertices.contains(vertex2)){
            throw new GraphException("second vertex is not in graph");
        }
        deleteDiEdge(vertex1, vertex2);
        deleteDiEdge(vertex2, vertex1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteVertex(V vertex) { // done
        if (vertices.contains(vertex)) {

            // delete all the appearances of the vertex so all the edges containing this vertex will be removed
            for (Set<V> adjacentVertices : diEdges.values()) {
                adjacentVertices.delete(vertex);
            }

            // delete the vertex from the dictionary
            diEdges.delete(vertex);
            vertices.delete(vertex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> vertices() { // done
        return vertices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Edge<V>> edges() { // done

        Set<Edge<V>> edges = new JDKHashSet<>(); // initialization of the data structure

        for (V key : diEdges.keys()) { // traverse the adjacency table
            for (V adjacentVertex : diEdges.valueOf(key)) {

                Edge<V> edge1 = new Edge<>(key, adjacentVertex);
                Edge<V> edge2 = new Edge<>(adjacentVertex, key);

                if (!edges.contains(edge1) && !edges.contains(edge2)) {
                    // insertion is performed only if this edge does not already exist in the set (in any order)
                    edges.insert(edge1);
                }
            }
        }

        return edges;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numberOfVertices() { // done
        return vertices.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numberOfEdges() { // done
        return edges().size();
    }

    /**
     * Returns the successors of a vertex in graph (i.e. vertices to which there is an edge from given vertex).
     *
     * @param vertex vertex for which we want to obtain its successors.
     * @return Successors of a vertex.
     */
    @Override
    public Set<V> successors(V vertex) {   // done
        // all vertices that can be reached from a given vertex by following a single edge.
        return (vertices.contains(vertex) && diEdges.isDefinedAt(vertex))  ?  diEdges.valueOf(vertex)  :  null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int degree(V vertex) {  // done
        return successors(vertex) == null  ?  0  :  successors(vertex).size();
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();

        StringJoiner verticesSJ = new StringJoiner(", ", "vertices(", ")");
        for (V vertex : vertices()) {
            verticesSJ.add(vertex.toString());
        }

        StringJoiner edgesSJ = new StringJoiner(", ", "edges(", ")");
        for (Edge<V> edge : edges()) {
            edgesSJ.add(edge.toString());
        }

        StringJoiner sj = new StringJoiner(", ", className + "(", ")");
        sj.add(verticesSJ.toString());
        sj.add(edgesSJ.toString());
        return sj.toString();
    }
}
