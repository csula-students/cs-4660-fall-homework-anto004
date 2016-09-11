package csula.cs4660.graphs.representations;

import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

/**
 * Adjacency list is probably the most common implementation to store the unknown
 * loose graph
 *
 * TODO: please implement the method body
 */
public class AdjacencyList implements Representation {
    private Map<Node, Collection<Edge>> adjacencyList;

    public AdjacencyList(File file) {
        List<Integer> graph = Lists.newArrayList();
        List<Edge> edges = Lists.newArrayList();
        HashMap nodes = new HashMap();
        try (Stream<String> stream = Files.lines(file.toPath())) {
            stream.forEach(line -> {
                for (String token: line.split(" ")) {
                    if(token.contains(":")) {
                        String[] currentLine = token.split(":");
                        nodes.put("node" + token, Integer.parseInt(currentLine[0]));

                        Node fromNode = new Node(Integer.parseInt(currentLine[0]));
                        Node toNodeEdge = new Node(Integer.parseInt(currentLine[1]));
                        Integer edgeValue = Integer.parseInt(currentLine[2]);

                        Edge edge = new Edge(fromNode, toNodeEdge, edgeValue);
                        edges.add(edge);
                    }

                    //graph.add(Integer.parseInt(token));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Edge edge: edges){
            System.out.println(edge);
        }

        System.out.println("/n /n HashMap values");
        // Get a set of the entries
        Set set = nodes.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getKey() + "--");
            System.out.println(me.getValue());
        }

    }

    public AdjacencyList() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        return null;
    }

    @Override
    public boolean addNode(Node x) {
        return false;
    }

    @Override
    public boolean removeNode(Node x) {
        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        return false;
    }

    @Override
    public int distance(Node from, Node to) {
        return 0;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}