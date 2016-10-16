package csula.cs4660.graphs.utils;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Lists;
import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * A quick parser class to read different format of files
 */
public class Parser {


    public static Graph readRectangularGridFile(Representation.STRATEGY graphRepresentation, File file) {
        Graph graph = new Graph(Representation.of(graphRepresentation));
        // TODO: implement the rectangular file read and add node with edges to graph
        System.out.println("readRectangularGridFile");


        try {
            List<String> lines = Files.readAllLines(file.toPath(),Charset.defaultCharset());
            Tile gridMatrix[][] = readLinesToMatrix(lines);
            gridDebug(lines);
            gridMatrixDebug(gridMatrix);

            for(Tile []tileArray: gridMatrix){
                for(Tile tile: tileArray){
                    if(tile != null) {
                        System.out.println("TILE PASSED :" + tile.getX()+" "+tile.getY());
                        List<Edge> edges = creatingTileEdges(gridMatrix, tile);
                        graph.addNode(new Node<Tile>(tile));
                        for(Edge edge: edges) {
                            System.out.println("edge added to graph: "+edge);
                            graph.addEdge(edge);
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Node node = new Node<Tile>(new Tile(3,0,"@1"));
        System.out.println("neighbor of Tile 3,0"+ graph.neighbors(node));



        return graph;
    }

    private static List<Edge> creatingTileEdges(Tile[][] gridMatrix, Tile tile) {
        List<Edge> edges = Lists.newArrayList();
        // checking for right wall
        if(((tile.getX()+1) < gridMatrix[0].length) && (gridMatrix[tile.getY()][tile.getX()+1] != null)){
            Node fromNode = new Node<Tile>(tile);
            Node toNode = new Node<Tile>(gridMatrix[tile.getY()][tile.getX()+1]);
            edges.add(new Edge(fromNode, toNode, 1 ));
        }
        //checking for left wall
        if(((tile.getX()-1) >=0) && (gridMatrix[tile.getY()][tile.getX()-1] != null)){
            Node fromNode = new Node<Tile>(tile);
            Node toNode = new Node<Tile>(gridMatrix[tile.getY()][tile.getX()-1]);
            edges.add(new Edge(fromNode, toNode, 1 ));
        }
        //checking for bottom wall
        if(((tile.getY()+1) < gridMatrix.length) && (gridMatrix[tile.getY()+1][tile.getX()] != null)){
            Node fromNode = new Node<Tile>(tile);
            Node toNode = new Node<Tile>(gridMatrix[tile.getY()+1][tile.getX()]);
            edges.add(new Edge(fromNode, toNode, 1 ));
        }
        //checking for up wall
        if(((tile.getY()- 1) >=0) && (gridMatrix[tile.getY()-1][tile.getX()] != null)){
            Node fromNode = new Node<Tile>(tile);
            Node toNode = new Node<Tile>(gridMatrix[tile.getY()-1][tile.getX()]);
            edges.add(new Edge(fromNode, toNode, 1 ));
        }

//        System.out.println("Neighbor of"+tile.getX()+" "+tile.getY());
//        for (Edge edge: edges){
//            System.out.println("edge: "+edge);
//        }
        return edges;
    }

    public static Tile [][] readLinesToMatrix (List<String> lines){
        int rowSize = lines.size();
        int colSize = lines.get(1).length();
        Tile [][] gridMatrix = new Tile[rowSize][colSize];
        StringBuilder stringType = new StringBuilder();
        int colStartingValue = 0;
        int testValue =0;
        int testValue2 =0;
        for(int row=1; row < lines.size(); row++){
            String [] currentLine = lines.get(row).split("");
            for(int col=1; col<currentLine.length-1; col+= 2) {
                if((!currentLine[col].contains("#")) && (!currentLine[col].contains("|")) && (!currentLine[col].contains("-"))){
                   // gridMatrix[row - 1][col - 1] = new Tile(row - 1, col - 1, currentLine[col] + currentLine[col + 1]);
                    gridMatrix[row-1][(col-1)/2] = new Tile((col-1)/2, row-1, stringType.append(currentLine[col])
                            .append(currentLine[col+1]).toString());
                    stringType.setLength(0);
                    System.out.println("gridMatrix at" + ((col-1)/2)+ " " + (row-1) + " is" + gridMatrix[row-1][(col-1)/2]);
                    //graph.addNode(new Node(gridMatrix[row-1][col-1]));
                }

            }
            colStartingValue=0;
            testValue=0;
            testValue2=0;
        }
        return gridMatrix;
    }
    public static void gridDebug(List<String> lines){
        System.out.print("  ");
        for(int q=1; q< lines.size()*2; q++)
            System.out.print((q-1) % 10 + " ");
        System.out.println();
        for (int i = 1; i < lines.size(); i++) {
            String [] currentLine = lines.get(i).split("");
            System.out.print((i-1) + " ");
            for (int j = 1; j < currentLine.length; j++) {
                    System.out.print(currentLine[j]+ " ");
            }
            System.out.println();
        }

    }
    private static void gridMatrixDebug(Tile[][] gridMatrix) {
        System.out.print("  ");

        for(int q=0; q< gridMatrix.length; q++)
            System.out.print((q) % 10  + " ");
        System.out.println();
        for (int row = 0; row < gridMatrix.length; row++) {
            System.out.print((row) + " ");
            for (int col = 0; col < gridMatrix[row].length; col++) {
//                if(gridMatrix[row][col] != null)
//                    System.out.print(gridMatrix[row][col].getType()+ " ");
                System.out.print(gridMatrix[row][col]);
            }
            System.out.println();
        }
    }

    public static String converEdgesToAction(Collection<Edge> edges) {
        // TODO: convert a list of edges to a list of action
        StringBuilder action = new StringBuilder();
        for (Edge edge : edges) {
            Tile tile1 = (Tile) edge.getFrom().getData();
            Tile tile2 = (Tile) edge.getTo().getData();
            if (tile1.getY() > tile2.getY())
                action.append("N");
            else if (tile1.getX() < tile2.getX())
                action.append("E");
            else if (tile1.getY() < tile2.getY())
                action.append("S");
            else if (tile1.getX() > tile2.getX())
                action.append("W");
        }
        return action.toString();
    }
}
