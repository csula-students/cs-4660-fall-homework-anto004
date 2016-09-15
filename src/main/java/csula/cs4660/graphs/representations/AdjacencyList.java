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
import java.util.Map.*;

/**
 * Adjacency list is probably the most common implementation to store the unknown
 * loose graph
 *
 * TODO: please implement the method body
 */
public class AdjacencyList implements Representation {
    private Multimap<Node, Edge> adjacencyList = ArrayListMultimap.create();

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

        for(Node node: nodes){
            for(Edge edge: edges) {
                if (node.getData() == edge.getFrom().getData()) {
                    adjacencyList.put(node, edge);
                }
            }
        }

        for(Node node: adjacencyList.keySet()){
            Collection<Edge> edge = adjacencyList.get(node);
            System.out.println(node+ " : " + edge);
        }
    }

    public AdjacencyList() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        Collection<Edge> edges = adjacencyList.get(x);
        for(Edge edge: edges){
            if (edge.getTo().getData() == y.getData()){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        Collection<Edge> edges = adjacencyList.get(x);
        List<Node> nodes = Lists.newArrayList();
        for(Edge edge: edges){
            nodes.add(edge.getTo());
        }
        return nodes;
    }

    @Override
    public boolean addNode(Node x) {
        if (adjacencyList.containsKey(x))
            return false;
        else{
            adjacencyList.put(x, new Edge());
        }
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        if(adjacencyList.containsKey(x)){
            adjacencyList.removeAll(x);
            Collection<Map.Entry<Node, Edge>> keySet = adjacencyList.entries();
            Iterator<Map.Entry<Node, Edge>> iteratorNode = keySet.iterator();
            while(iteratorNode.hasNext()) {
                Entry entry = iteratorNode.next();
                Edge edge = (Edge)entry.getValue();
                if(edge.getTo().getData() == x.getData()) {
                    iteratorNode.remove();
                }
            }
            return true;
        }
        System.out.println("Nodes and Edges after removing Node-"+ x);
        for(Node node: adjacencyList.keySet()){
            Collection<Edge> edge = adjacencyList.get(node);
            System.out.println(node+ " : " + edge);
        }
        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
        if(adjacencyList.containsValue(x))
            return false;
        else{
            adjacencyList.put(x.getFrom(), x);
        }
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        if(!adjacencyList.containsValue(x))
            return false;

        Collection<Map.Entry<Node, Edge>> keySet = adjacencyList.entries();
        Iterator<Map.Entry<Node, Edge>> iteratorNode = keySet.iterator();
        while(iteratorNode.hasNext()) {
            Entry entry = iteratorNode.next();
            Edge edge = (Edge)entry.getValue();
            if(edge.getFrom().getData() == x.getFrom().getData() && edge.getTo().getData() == x.getTo().getData()) {
                iteratorNode.remove();
            }
        }
        System.out.println("Nodes and Edges after removing Edge-"+ x);
        for(Node node: adjacencyList.keySet()){
            Collection<Edge> edge = adjacencyList.get(node);
            System.out.println(node+ " : " + edge);
        }
        return true;
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
