package csula.cs4660.graphs.representations;

import com.google.common.collect.Lists;
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
 * Adjacency matrix in a sense store the nodes in two dimensional array
 *
 * TODO: please fill the method body of this class
 */
public class AdjacencyMatrix implements Representation {
    private Node[] nodes;
    private int[][] adjacencyMatrix;
    private HashMap<String, Node> nodeMap = new HashMap<>();

    public AdjacencyMatrix(File file) {
//        for(int i=0; i < 11; i++)
//            for(int j=0; j < 11; j++) {
//                adjacencyMatrix[i][j] = 0;
//            }
//
//
//        try (Stream<String> stream = Files.lines(file.toPath())) {
//            stream.forEach(line -> {
//                for (String token: line.split(" ")) {
//                    if(token.contains(":")) {
//                        String[] currentLine = token.split(":");
//                        Integer row = Integer.parseInt(currentLine[0]);
//                        Integer column = Integer.parseInt(currentLine[1]);
//                        Integer value = Integer.parseInt(currentLine[2]);
//                        adjacencyMatrix[row][column] = value;
//                        nodeMap.put("node-"+ row, new Node(row));
//                        nodeMap.put("node-"+ column, new Node(column));
//
//                    }
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            List<String> lines = Files.readAllLines(file.toPath(),Charset.defaultCharset());
            int size = Integer.parseInt(lines.get(0));
            System.out.println("size of lines"+size);
            nodes = new Node[size];
            adjacencyMatrix= new int[size][size];
            for(int i=0; i < adjacencyMatrix.length; i++)
                for(int j=0; j < adjacencyMatrix.length; j++) {
                    adjacencyMatrix[i][j] = 0;
            }

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

            for(int i=0; i < lines.size(); i++) {
                if(lines.get(i).contains(":")) {
                    String[] currentLine = lines.get(i).split(":");
                    Integer row = Integer.parseInt(currentLine[0]);
                    Integer column = Integer.parseInt(currentLine[1]);
                    Integer value = Integer.parseInt(currentLine[2]);
                    adjacencyMatrix[row][column] = value;
                    nodeMap.put("node-" + row, new Node(row));
                    nodeMap.put("node-" + column, new Node(column));
                }

            }
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
        int row = (int) x.getData();
        int column = (int) y.getData();
        if(adjacencyMatrix[row][column] == 1)
            return true;
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        int row = (int) x.getData();
        List<Node> nodes = Lists.newArrayList();
        for(int i=0; i < adjacencyMatrix.length; i++){
            if(adjacencyMatrix[row][i] != 0){
                nodes.add(new Node(i));
            }
        }
        return nodes;
    }

    @Override
    public boolean addNode(Node x) {
        System.out.println("NODES.LENGTH"+nodes.length);
        for(int i=0; i<nodes.length; i++){
            if(nodes[i].equals(x))
                return false;
        }
        Node [] newNodes = Arrays.copyOf(nodes, nodes.length+1);
        newNodes[nodes.length - 1]= x;
        nodes = newNodes;
        System.out.println("Addedd New Node:"+nodes[nodes.length-2]);
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        int row = (int)x.getData();
        if(!(Arrays.asList(nodes).contains(x)))
            return false;

        List<Node> nodeList = new ArrayList<>(Arrays.asList(nodes));
        nodeList.remove(x);
        nodes = nodeList.toArray(nodes);
        // array is no longer sorted
        for(int column=0; column< adjacencyMatrix.length; column ++){
            adjacencyMatrix[row][column] = 0;
            adjacencyMatrix[column][row] = 0;
        }
        System.out.println("Removed Node:"+x);
        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        int row = (int)x.getFrom().getData();
        int column = (int)x.getTo().getData();
        if(adjacencyMatrix[row][column] == 1)
            return false;
        adjacencyMatrix[row][column] = x.getValue();
        System.out.println("Added Edge: Node"+row+" and Node"+ column+"- " +adjacencyMatrix[row][column]);
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        int row = (int)x.getFrom().getData();
        int column = (int)x.getTo().getData();
        if(adjacencyMatrix[row][column] == 0)
            return false;
        adjacencyMatrix[row][column] = 0;
        System.out.println("Removed Edge:"+x);
        return true;

    }

    @Override
    public int distance(Node from, Node to) {
        int row = (int)from.getData();
        int col = (int )to.getData();
        return adjacencyMatrix[row][col];
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
