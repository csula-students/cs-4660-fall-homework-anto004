package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;
import java.util.Map.*;

/**
 * Adjacency matrix in a sense store the nodes in two dimensional array
 *
 * TODO: please fill the method body of this class
 */
public class AdjacencyMatrix implements Representation {
    private Node[] nodes = new Node[11];
    private int[][] adjacencyMatrix = new int[11][11];
    private HashMap<String, Node> nodeMap = new HashMap<>();

    public AdjacencyMatrix(File file) {
        for(int i=0; i < 11; i++)
            for(int j=0; j < 11; j++) {
                adjacencyMatrix[i][j] = 0;
            }

        try (Stream<String> stream = Files.lines(file.toPath())) {
            stream.forEach(line -> {
                for (String token: line.split(" ")) {
                    if(token.contains(":")) {
                        String[] currentLine = token.split(":");
                        Integer row = Integer.parseInt(currentLine[0]);
                        Integer column = Integer.parseInt(currentLine[1]);
                        adjacencyMatrix[row][column] = 1;
                        nodeMap.put("node-"+ row, new Node(row));
                        nodeMap.put("node-"+ column, new Node(column));

                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Node> nodeList = new ArrayList<>(nodeMap.values());
        for(int i=0; i < nodeList.size(); i++){
            nodes[i] = nodeList.get(i);
        }

//        Collection<Entry<String,Node>> entrySett = nodeMap.entrySet();
//        Iterator<Entry<String, Node>> iterator = entrySett.iterator();
//        while(iterator.hasNext()){
//            Entry entry = iterator.next();
//            Node node = (Node) entry.getValue();
//            System.out.println(node);
//        }

        System.out.print("  ");
        for(int q=0; q<adjacencyMatrix.length; q++)
            System.out.print(q+ " ");
        System.out.println();
        for(int i=0; i< adjacencyMatrix.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public AdjacencyMatrix() {

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
