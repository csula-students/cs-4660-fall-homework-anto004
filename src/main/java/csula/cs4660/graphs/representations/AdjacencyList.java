package csula.cs4660.graphs.representations;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
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
    private Multimap<Node, Collection<Edge>> adjacencyList = ArrayListMultimap.create();

    public AdjacencyList(File file) {
        List<Edge> edges = Lists.newArrayList();
        HashMap<String, Node> nodeMap = new HashMap();

        try (Stream<String> stream = Files.lines(file.toPath())) {
            stream.forEach(line -> {
                for (String token: line.split(" ")) {
                    if(token.contains(":")) {
                        String[] currentLine = token.split(":");

                        Node fromNode = new Node(Integer.parseInt(currentLine[0]));
                        Node toNodeEdge = new Node(Integer.parseInt(currentLine[1]));
                        Integer edgeValue = Integer.parseInt(currentLine[2]);

                        nodeMap.put("node-" + Integer.parseInt(currentLine[0]), fromNode);
                        nodeMap.put("node-" + Integer.parseInt(currentLine[1]), toNodeEdge);

                        Edge edge = new Edge(fromNode, toNodeEdge, edgeValue);
                        edges.add(edge);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Node> nodes = new ArrayList<Node>(nodeMap.values());

        for(Edge edge: edges){
            System.out.println(edge);
        }
        System.out.println("Nodes");
        for(Node node: nodes){
            System.out.println(node);
        }


        for(Node node: nodes){
            for(Edge edge: edges) {
                if (node.getData() == edge.getFrom().getData()) {
                    List<Edge> edgeList = Lists.newArrayList();
                    edgeList.add(edge);
                    adjacencyList.put(node, edgeList);
                }
            }
        }

        for(Node node: adjacencyList.keySet()){
            Collection<Collection<Edge>> edge = adjacencyList.get(node);
            System.out.println(node+ " : " + edge);

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
