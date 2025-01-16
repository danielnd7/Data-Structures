package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;
import org.uma.ed.datastructures.stack.JDKStack;
import org.uma.ed.datastructures.stack.Stack;


public class DFTPaths<V> { // MY CUSTOM CLASS
    private final V source;                  // source of traversal
    private final Set<V> visited;            // already visited nodes
    private final List<V> traversal;         // traversal
    private final Dictionary<V, V> parentOf; // spanning tree as "parent of" dictionary

    public DFTPaths(Traversable<V> graph, V source) {
        this.source = source;

        parentOf = JDKHashDictionary.empty();
        parentOf.insert(source, source);
        traversal = JDKArrayList.empty();
        visited = JDKHashSet.empty();

        Stack<DiEdge<V>> stack = new JDKStack<>();
        stack.push(DiEdge.of(source, source));

        while (!stack.isEmpty()) {
            var edge = stack.top();
            stack.pop();

            V sourceV = edge.source();
            V destination = edge.destination();
            if (!visited.contains(destination)) {

                visited.insert(destination); // visit the vertex
                traversal.append(destination); // add the vertex to the traversal
                parentOf.insert(destination, sourceV); // add the entry to the dictionary

                for (var successor : graph.successors(destination)) {
                    if (!visited.contains(successor)) {
                        stack.push(DiEdge.of(destination, successor)); // push the edges to all the successors not visited yet
                    }
                }
            }
        }

    }

    public Set<V> getVisited() {
        return visited;
    }

    public List<V> getTraversal() {
        return traversal;
    }
    public Dictionary<V, V> getParentOf() {
        return parentOf;
    }

    public List<V> pathTo(V vertex) {
        List<V> path = JDKArrayList.empty();
        path.append(vertex);

        V currentVertex = vertex;
        while (!currentVertex.equals(source)) {
            V parent = parentOf.valueOf(currentVertex);
            path.prepend(parent);
            currentVertex = parent;
        }

        return path;
    }
}
