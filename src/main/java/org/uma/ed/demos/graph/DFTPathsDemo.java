package org.uma.ed.demos.graph;

import org.uma.ed.datastructures.graph.DFTPaths;
import org.uma.ed.datastructures.graph.DictionaryGraph;
import org.uma.ed.datastructures.graph.Graph;

public class DFTPathsDemo {
    public static void main(String[] args) {

        Graph<String> graph = DictionaryGraph.empty();

        // Add vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        graph.addVertex("H");

        // Add edges as shown in the graph
        graph.addEdge("A", "C");
        graph.addEdge("A", "H");
        graph.addEdge("A", "D");
        graph.addEdge("B", "H");
        graph.addEdge("C", "G");
        graph.addEdge("D", "F");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");
        graph.addEdge("E", "H");
        graph.addEdge("E", "G");

        System.out.println("\n");
        System.out.println("Graph is: " + graph);
        System.out.println("\n");

        DFTPaths<String> dftPaths = new DFTPaths<>(graph, "A");

        System.out.println("Traversal: ");
        System.out.println(dftPaths.getTraversal());
        System.out.println("\n");


        System.out.println("Parents of: ");
        System.out.println(dftPaths.getParentOf());
        System.out.println("\n");


        System.out.println("Path to D: ");
        System.out.println(dftPaths.pathTo("D"));
        System.out.println("\n");

        System.out.println("Path to B: ");
        System.out.println(dftPaths.pathTo("B"));
        System.out.println("\n");
    }

}
