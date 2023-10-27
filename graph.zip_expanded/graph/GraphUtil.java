package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet; 
import java.util.Map;
import java.util.Stack;

/**
 * A utility class which calculates paths and distances within a Graph.
 * 
 * This class is complete and should not be modified
 *
 * @author Caleb Brose
 */
public class GraphUtil {

    /**
     * Calculates distances from vertex s to all other vertices in g using a
     * breadth-first-search. If a vertex is not reachable, its distance is
     * Integer.MAX_VALUE.
     * 
     * @param g
     * - the Graph to traverse
     * @param s
     * - the vertex to start the traversal from
     * @return a Map of the distances from s to all reachable vertices, null if
     * s is not a vertex in g
     */
    public static <V> Map<V, Integer> getDistancesFrom(Graph<V> g, V s) {
        if (!g.hasVertex(s))
            return null;

        LinkedQueue<V> queue = new LinkedQueue<V>();

        // green - unreached, red - reached but not processed, black - processed
        HashMap<V, String> color = new HashMap<V, String>();
        HashMap<V, Integer> dist = new HashMap<V, Integer>();

        for (V w : g.vertices()) {
            if (!w.equals(s)) {
                color.put(w, "green");
                dist.put(w, Integer.MAX_VALUE);
            }
        }

        color.put(s, "red");
        dist.put(s, 0);
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            V c = queue.front();
            for (V w : g.adjacentTo(c))
                if (color.get(w).equals("green")) {
                    color.put(w, "red");
                    dist.put(w, dist.get(c) + 1);
                    queue.enqueue(w);
                }

            queue.dequeue();
            color.put(c, "black");
        }

        return dist;
    }

    /**
     * Finds a path from s to t in g by performing a depth-first-search.
     * 
     * @param g
     * - the Graph to traverse
     * @param s
     * - the start vertex
     * @param t
     * - the destination vertex
     * @return a path from s to t if it exists, null otherwise
     */
    public static <V> Iterable<V> getPath(Graph<V> g, V s, V t) {

        // Vertices in this set have been processed
        HashSet<V> seen = new HashSet<V>();
        HashMap<V, V> pred = new HashMap<V, V>();
        
        visitDFS(g, s, seen, pred);

        // If t wasn't found in the DFS, there is no path
        if (!pred.containsKey(t))
            return null;

        // Construct the path from t to s by traversing pred
        V cur = t;
        Stack<V> revPath = new Stack<>();

        while (cur != null) {
            revPath.add(cur);
            cur = pred.get(cur);
        }

        // Get the correctly ordered path from s to t
        ArrayList<V> path = new ArrayList<>();
        while (!revPath.isEmpty())
            path.add(revPath.pop());

        return path;
    }

    // DFS Traversal helper
    private static <V> void visitDFS(Graph<V> g, V s, HashSet<V> seen, HashMap<V, V> pred) {

        seen.add(s);

        for (V v : g.adjacentTo(s)) {

            if (!seen.contains(v)) {
                pred.put(v, s);
                visitDFS(g, v, seen, pred);
            }
        }
    }

}
