
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;

class Edge {

    public Vertex dest;   // Second vertex in Edge
    public double cost;   // Edge cost

    public Edge(Vertex d, double c) {
        dest = d;
        cost = c;
    }
}

class Vertex {

    public String name;
    public List<Edge> adj;
    public double dist;
    public Vertex prev;
    public int scratch;

    public Vertex(String nm) {
        name = nm;
        adj = new LinkedList<Edge>();
        reset();
    }

    public void reset() {
        dist = Graph.INFINITY;
        prev = null;

        scratch = 0;
    }
}

class Graph {

    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();

    public void addEdge(String sourceName, String destName, double cost) {
        Vertex v = getVertex(sourceName);
        Vertex w = getVertex(destName);
        v.adj.add(new Edge(w, cost));
    }
    
    public void removeEdge(String sourceName, String destName, double cost) {
        Vertex v = getVertex(sourceName);
        Vertex w = getVertex(destName);
        Edge e = (w, cost);
    }

    public void printPath(String destName) {
        Vertex w = vertexMap.get(destName);
        if (w == null) {
            throw new NoSuchElementException("Destination vertex not found");
        } else if (w.dist == INFINITY) {
            System.out.println(destName + " is unreachable");
        } else {
            System.out.print("(Cost is: " + w.dist + ") ");
            printPath(w);
            System.out.println();
        }
    }
    
        private void printPath( Vertex dest )
    {
        if( dest.prev != null )
        {
            printPath( dest.prev );
            System.out.print( " to " );
        }
        System.out.print( dest.name );
    }

    private Vertex getVertex(String vertexName) {
        Vertex v = vertexMap.get(vertexName);
        if (v == null) {
            v = new Vertex(vertexName);
            vertexMap.put(vertexName, v);
        }
        return v;
    }

    private void clearAll() {
        for (Vertex v : vertexMap.values()) {
            v.reset();
        }
    }

    public void unweighted(String startName) {
        clearAll();

        Vertex start = vertexMap.get(startName);
        if (start == null) {
            throw new NoSuchElementException("Start vertex not found");
        }

        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(start);
        start.dist = 0;

        while (!q.isEmpty()) {
            Vertex v = q.remove();

            for (Edge e : v.adj) {
                Vertex w = e.dest;
                if (w.dist == INFINITY) {
                    w.dist = v.dist + 1;
                    w.prev = v;
                    q.add(w);
                }
                System.out.println(v.name + " " + w.name + " " + e.cost);
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addEdge("D", "C", 10);
        g.addEdge("A", "B", 12);
        g.addEdge("D", "B", 23);
        g.addEdge("A", "D", 87);
        g.addEdge("E", "D", 43);
        g.addEdge("B", "E", 11);
        g.addEdge("C", "A", 19);
        g.unweighted("D");
        g.removeEdge("C", "A", 19);
        g.unweighted("D");
        System.out.println("done");
    }
}
