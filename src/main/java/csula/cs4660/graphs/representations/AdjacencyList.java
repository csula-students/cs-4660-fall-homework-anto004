package csula.cs4660.graphs.representations;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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
    private Map<Node,List<Edge>> adjacencyList = new HashMap<>();
    private Node nodeArray[];

    protected AdjacencyList(File file) {
        List<Edge> edges = Lists.newArrayList();
        HashMap<String, Node> nodeMap = new HashMap();

        try {
            List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());

            int numOfNodes = Integer.parseInt(lines.get(0));
            nodeArray = new Node[numOfNodes];

            for(int i=0; i< nodeArray.length; i++){
                adjacencyList.put(new Node(i), new ArrayList<Edge>());
            }


            for(String str: lines){
                if(str.contains(":")){
                    String[] currentLine = str.split(":");
                    Node fromNode = new Node(Integer.parseInt(currentLine[0]));
                    Node toNodeEdge = new Node(Integer.parseInt(currentLine[1]));
                    Integer edgeValue = Integer.parseInt(currentLine[2]);
                    Edge edge = new Edge(fromNode, toNodeEdge, edgeValue);
                    adjacencyList.get(fromNode).add(edge);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected AdjacencyList() {

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
        if(adjacencyList.get(x) == null)
            return Lists.newArrayList();
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
            adjacencyList.put(x, new ArrayList<Edge>());
        }
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        if(adjacencyList.containsKey(x)){
            adjacencyList.remove(x);
            Collection<Map.Entry<Node, List<Edge>>> keySet = adjacencyList.entrySet();
            Iterator<Map.Entry<Node, List<Edge>>> iteratorNode = keySet.iterator();
            while(iteratorNode.hasNext()) {
                Entry entry = iteratorNode.next();
               // System.out.println("entry: "+entry);
                List<Edge> edges = (List<Edge>) entry.getValue();
                for(Edge edge: edges) {
                    if (edge.getTo().getData() == x.getData()) {
                        iteratorNode.remove();
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
        if(adjacencyList.get(x.getFrom()).contains(x))
            return false;
        else{
            // assuming there are already nodes of that edge present
            adjacencyList.get(x.getFrom()).add(x);
        }

        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        if(!adjacencyList.get(x.getFrom()).contains(x))
            return false;

        Collection<Map.Entry<Node, List<Edge>>> keySet = adjacencyList.entrySet();
        Iterator<Map.Entry<Node, List<Edge>>> iteratorNode = keySet.iterator();
        while(iteratorNode.hasNext()) {
            Entry entry = iteratorNode.next();
            List<Edge> edges = (List<Edge>)entry.getValue();
            for(Edge edge: edges) {
                if (edge.getFrom().getData() == x.getFrom().getData() && edge.getTo().getData() == x.getTo().getData()) {
                    iteratorNode.remove();
                }
            }
        }
        return true;
    }

    @Override
    public int distance(Node from, Node to) {
        Collection<Edge> edges = adjacencyList.get(from);
        for(Edge edge: edges){
            if (edge.getTo().equals(to)){
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
