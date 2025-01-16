package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.queue.JDKQueue;
import org.uma.ed.datastructures.queue.Queue;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;


public class BFTPaths<V> { // MY CUSTOM CLASS
    private final V source;                  // source of traversal
    private final Set<V> visited;            // already visited nodes
    private final List<V> traversal;         // traversal

    public BFTPaths(Traversable<V> graph, V source) {
        this.source = source;

        traversal = JDKArrayList.empty();
        visited = JDKHashSet.empty();

        Queue<V> queue = new JDKQueue<>();
        queue.enqueue(source);

        while (!queue.isEmpty()) {
            var vertex = queue.first();
            queue.dequeue();

            if (!visited.contains(vertex)) {

                visited.insert(vertex); // visit the vertex
                traversal.append(vertex); // add the vertex to the traversal

                for (var successor : graph.successors(vertex)) {

                    if (!visited.contains(successor)) {
                        queue.enqueue(successor);
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

}
