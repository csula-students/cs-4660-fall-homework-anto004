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
 * Object oriented representation of graph is using OOP approach to store nodes
 * and edges
 *
 * TODO: Please fill the body of methods in this class
 */
public class ObjectOriented implements Representation {
    private Collection<Node> nodes;
    private Collection<Edge> edges;

    public ObjectOriented(File file) {
        edges = Lists.newArrayList();
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
        nodes = new ArrayList<Node>(nodeMap.values());
        for(Node node: nodes)
            System.out.println("Node:"+node);

        for(Edge edge: edges)
            System.out.println("Edge:"+edge);

    }

    public ObjectOriented() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        for(Edge edge: edges){
            if (edge.getFrom().getData() == x.getData() && edge.getTo().getData() == y.getData()){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        List<Node> nodeList = Lists.newArrayList();
        for(Edge edge: edges){
            if (edge.getFrom().getData() == x.getData()){
                nodeList.add(new Node(edge.getTo().getData()));
            }
        }
        return nodeList;
    }

    @Override
    public boolean addNode(Node x) {
        if(nodes.contains(x))
            return false;
        nodes.add(new Node(x));
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        if(!nodes.contains(x))
            return false;
        nodes.remove(x);
        for(Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();){
            Edge edge = iterator.next();
            if(edge.getFrom().getData() == x.getData() ||
                    edge.getTo().getData() == x.getData())
                iterator.remove();
        }
//        for(Edge edge: edges)
//            System.out.println("New Edge:"+edge);

        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        for(Edge edge: edges){
            if (edge.getFrom().getData() == x.getFrom().getData() &&
                    edge.getTo().getData() == x.getTo().getData()){
                return false;
            }
        }
        edges.add(x);
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        if(!edges.contains(x))
            return false;
        edges.remove(x);
        return true;
    }

    @Override
    public int distance(Node from, Node to) {
        return 1;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
