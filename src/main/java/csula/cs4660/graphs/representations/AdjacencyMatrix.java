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

    public AdjacencyMatrix(File file) {

        try {
            List<String> lines = Files.readAllLines(file.toPath(),Charset.defaultCharset());
            int size = Integer.parseInt(lines.get(0));

            nodes = new Node[size];
            adjacencyMatrix= new int[size][size];
            for(int i=0; i < adjacencyMatrix.length; i++)
                for(int j=0; j < adjacencyMatrix.length; j++) {
                    adjacencyMatrix[i][j] = 0;
            }


            for(int i=0; i < lines.size(); i++) {
                if(lines.get(i).contains(":")) {
                    String[] currentLine = lines.get(i).split(":");
                    Integer row = Integer.parseInt(currentLine[0]);
                    Integer column = Integer.parseInt(currentLine[1]);
                    Integer value = Integer.parseInt(currentLine[2]);
                    adjacencyMatrix[row][column] = value;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i < nodes.length; i++){
            nodes[i] = new Node(i);

        }

    }

    public AdjacencyMatrix() {
        nodes = new Node[0];
        adjacencyMatrix = new int[0][0];
    }

    @Override
    public boolean adjacent(Node x, Node y) {
        int row = indexOfNode(x);
        int column = indexOfNode(y);

        if(adjacencyMatrix[row][column] != 0)
            return true;
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        int row = indexOfNode(x);

        List<Node> result = Lists.newArrayList();
        //return an empty list, node doesn't exist
        if (row == -1)
            return result;

        for(int column=0; column < adjacencyMatrix[row].length; column++){
            if(adjacencyMatrix[row][column] != 0){
                result.add(nodes[column]);
            }
        }

        return result;
    }

    @Override
    public boolean addNode(Node x) {

        if(indexOfNode(x) > -1)
            return false;
        Node [] newNodes = Arrays.copyOf(nodes, nodes.length+1);
       // System.out.println("newNodes.length:"+newNodes.length);
        newNodes[newNodes.length - 1]= x;
        nodes = newNodes;
        //System.out.println("Addedd New Node:"+nodes[nodes.length - 1]);

        int[][] newMatrix = new int[adjacencyMatrix.length + 1][adjacencyMatrix.length + 1];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            newMatrix[i] = Arrays.copyOf(adjacencyMatrix[i], newMatrix[i].length);
        }
        adjacencyMatrix = newMatrix;

        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        int row = indexOfNode(x);
        if(!(Arrays.asList(nodes).contains(x)))
            return false;

        Node[] newNodes = new Node[nodes.length - 1];
        for (int i = 0, k = 0; i < nodes.length; i++) {
            // don't copy this one, this is x...
            if (i == row) continue;

            // copy everything else!
            newNodes[k++] = nodes[i];
        }

        nodes = newNodes;

        // array is no longer sorted
        for(int column=0; column< adjacencyMatrix[row].length; column ++){
            adjacencyMatrix[row][column] = 0;
            adjacencyMatrix[column][row] = 0;
        }
        System.out.println("Removed Node:"+x);

        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        int row = indexOfNode(x.getFrom());
        int column = indexOfNode(x.getTo());

        if (row == -1) {
            addNode(x.getFrom());
        }

        if (column == -1) {
            addNode(x.getTo());
        }

        if (row == -1 || column == -1) {
            row = indexOfNode(x.getFrom());
            column = indexOfNode(x.getTo());
        }
       // System.out.println("row:"+row+" col :"+column);
        if(adjacencyMatrix[row][column] !=0 )
            return false;

        adjacencyMatrix[row][column] = x.getValue();
       // System.out.println("Added Edge: Node"+row+" and Node"+ column+"- " +adjacencyMatrix[row][column]);
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        int row = indexOfNode(x.getFrom());
        int column = indexOfNode(x.getTo());
        if(row == -1 || column == -1 || adjacencyMatrix[row][column] == 0)
            return false;
        adjacencyMatrix[row][column] = 0;
        return true;

    }

    @Override
    public int distance(Node from, Node to) {
        int row = indexOfNode(from);
        int col = indexOfNode(to);
        return adjacencyMatrix[row][col];
    }

    @Override
    public Optional<Node> getNode(int index) {

        return null;
    }

    private int indexOfNode(Node x) {
        int index = -1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(x))
                index = i;
        }
        return index;
    }
}
