package csula.cs4660.graphs.representations;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

/**
 * Object oriented representation of graph is using OOP approach to store nodes
 * and edges
 *
 * TODO: Please fill the body of methods in this class
 */
public class ObjectOriented implements Representation {
    private List<Node> nodes;
    private List<Edge> edges;

    private BiMap<Node, Integer> nodeIndex = HashBiMap.create();
    private int count = 0;

    public ObjectOriented(File file) {
        nodes = new ArrayList<>();
        edges = Lists.newArrayList();

        try {
            List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());

            int numOfNodes = Integer.parseInt(lines.get(0));
            for(int i=0; i<numOfNodes; i++){
                nodes.add(new Node(i));
                nodeIndex.put(nodes.get(i), count++);
            }

            for(String str: lines){
                if(str.contains(":")){
                    String[] currentLine = str.split(":");
                    Node fromNode = new Node(Integer.parseInt(currentLine[0]));
                    Node toNodeEdge = new Node(Integer.parseInt(currentLine[1]));
                    Integer edgeValue = Integer.parseInt(currentLine[2]);
                    Edge edge = new Edge(fromNode, toNodeEdge, edgeValue);
                    edges.add(edge);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Edge edge : edges)
            nodes.get(nodeIndex.get(edge.getFrom())).addNeighbor(edge.getTo());


    }

    public ObjectOriented() {
        nodes = Lists.newArrayList();
        edges = Lists.newArrayList();
    }

    @Override
    public boolean adjacent(Node x, Node y) {
       // System.out.println("nodes.get: "+nodes.get(nodeIndex.get(x)));
        return nodes.get(nodeIndex.get(x)).isNeighbor(y);
    }

    @Override
    public List<Node> neighbors(Node x) {
        int index = nodeIndex.get(x);
        Node node = nodeIndex.inverse().get(index);

        return node.getNeighbors();
    }

    @Override
    public boolean addNode(Node x) {
        Integer index = nodeIndex.get(x);
        if (index != null)
            return false;
        nodes.add(x);
        nodeIndex.put(x, count++);

        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        if (!nodes.contains(x))
            return false;
        nodeIndex.remove(x);

        for (Node node : nodes)
            node.removeNeighbor(x);

        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        for(Edge edge: edges){
            if (edge.equals(x)){
                return false;
            }
        }
        edges.add(x);

        return nodes.get(nodeIndex.get(x.getFrom())).addNeighbor(x.getTo());
    }

    @Override
    public boolean removeEdge(Edge x) {
        if(!edges.contains(x))
            return false;

        edges.remove(x);

        return nodes.get(nodeIndex.get(x.getFrom())).removeNeighbor(x.getTo());
    }

    @Override
    public int distance(Node from, Node to) {
        for(Edge edge: edges){
            if(edge.getFrom().equals(from) && edge.getTo().equals(to)){
                return edge.getValue();
            }
        }
        return 0;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
