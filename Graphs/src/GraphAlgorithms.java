import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Eileen Liu
 * @version 1.0
 * @userid eliu80
 * @GTID 903561092
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph");
        }

        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> list = new ArrayList<>();

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        list.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.peek();
            if (curr == null) {
                throw new IllegalArgumentException("Vertex cannot be null");
            }
            for (VertexDistance<T> vd : adjList.get(curr)) {
                if (!list.contains(vd.getVertex())) {
                    list.add(vd.getVertex());
                    queue.add(vd.getVertex());
                }
            }
            queue.remove();
        }
        return list;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        } else if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException(
                    "Start does not exist in the graph");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        List<Vertex<T>> list = new ArrayList<>();
        Set<Vertex<T>> vlist = new HashSet<>();
        dfsH(start, list, vlist, adjList);
        return list;
    }

    /**
     * Helper recursive method for DFS
     *
     * @param curr    current vertex we are looking at
     * @param vlist   the visited list
     * @param list    the final list
     * @param adjList the adjacency list
     * @param <T>     generic type to return
     */
    private static <T> void dfsH(Vertex<T> curr, List<Vertex<T>> list, Set<Vertex<T>> vlist,
                                 Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        list.add(curr);
        vlist.add(curr);
        for (VertexDistance<T> vd : adjList.get(curr)) {
            if (!vlist.contains(vd.getVertex())) {
                dfsH(vd.getVertex(),  list, vlist, adjList);
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph");
        }
        Map<Vertex<T>, Integer> hs = new HashMap<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        List<Vertex<T>> vlist = new ArrayList<>();
        for (Vertex<T> vertex : adjList.keySet()) {
            if (vertex.equals(start)) {
                hs.put(vertex, 0);
            } else {
                hs.put(vertex, Integer.MAX_VALUE);
            }
        }
        pq.add(new VertexDistance<>(start, 0));
        while (vlist.size() < adjList.size() && !(pq.isEmpty())) {
            VertexDistance<T> curr = pq.remove();
            vlist.add(curr.getVertex());
            for (VertexDistance<T> v : adjList.get(curr.getVertex())) {
                int newDistance = curr.getDistance() + v.getDistance();

                if (!vlist.contains(v.getVertex()) && hs.get(v.getVertex()) > newDistance) {
                    hs.put(v.getVertex(), newDistance);
                    pq.add(new VertexDistance<>(v.getVertex(),
                            newDistance));
                }
            }
        }
        return hs;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * You should NOT allow self-loops or parallel edges into the MST.
     * <p>
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null)  {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        Queue<Edge<T>> edges = new PriorityQueue<>(graph.getEdges());

        Set<Edge<T>> out = new HashSet<>();

        DisjointSet<Vertex<T>> ds = new DisjointSet<Vertex<T>>();

        while (!edges.isEmpty())  {
            Edge<T> edge = edges.remove();
            Vertex<T> v = ds.find(edge.getV());
            Vertex<T> u = ds.find(edge.getU());

            if (!u.equals(v)) {
                ds.union(u, v);
                out.add(edge);
                out.add(new Edge<T>(edge.getV(), edge.getU(),
                        edge.getWeight()));
            }
        }

        int vertexSize = graph.getAdjList().keySet().size();
        if (out.size() == (vertexSize - 1) * 2) {
            return out;
        }
        return null;
    }
}
