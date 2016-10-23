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
import java.util.*;
import java.util.stream.Stream;

/**
 * A quick parser class to read different format of files
 */
public class Parser {


    public static Graph readRectangularGridFile(Representation.STRATEGY graphRepresentation, File file) {
        Graph graph = new Graph(Representation.of(graphRepresentation));

        try {
            List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());

            List<String> newLines = removeBorders(lines);
            Tile gridMatrix[][] = readLinesToMatrix(newLines);

            for (Tile[] tileArray : gridMatrix) {
                for (Tile tile : tileArray) {
                    if (tile != null) {
                        List<Edge> edges = creatingTileEdges(gridMatrix, tile);
                        graph.addNode(new Node<Tile>(tile));
                        for (Edge edge : edges) {
                            graph.addEdge(edge);
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }

    private static List<Edge> creatingTileEdges(Tile[][] gridMatrix, Tile tile) {
        //Received help from Shay Nguyen
        List<Edge> edges = Lists.newArrayList();
        // checking for right wall
        if (((tile.getX() + 1) < gridMatrix[0].length) && (gridMatrix[tile.getY()][tile.getX() + 1] != null)) {
            Node fromNode = new Node<Tile>(tile);
            Node toNode = new Node<Tile>(gridMatrix[tile.getY()][tile.getX() + 1]);
            edges.add(new Edge(fromNode, toNode, 1));
        }
        //checking for left wall
        if (((tile.getX() - 1) >= 0) && (gridMatrix[tile.getY()][tile.getX() - 1] != null)) {
            Node fromNode = new Node<Tile>(tile);
            Node toNode = new Node<Tile>(gridMatrix[tile.getY()][tile.getX() - 1]);
            edges.add(new Edge(fromNode, toNode, 1));
        }
        //checking for bottom wall
        if (((tile.getY() + 1) < gridMatrix.length) && (gridMatrix[tile.getY() + 1][tile.getX()] != null)) {
            Node fromNode = new Node<Tile>(tile);
            Node toNode = new Node<Tile>(gridMatrix[tile.getY() + 1][tile.getX()]);
            edges.add(new Edge(fromNode, toNode, 1));
        }
        //checking for up wall
        if (((tile.getY() - 1) >= 0) && (gridMatrix[tile.getY() - 1][tile.getX()] != null)) {
            Node fromNode = new Node<Tile>(tile);
            Node toNode = new Node<Tile>(gridMatrix[tile.getY() - 1][tile.getX()]);
            edges.add(new Edge(fromNode, toNode, 1));
        }

        return edges;
    }

    public static Tile[][] readLinesToMatrix(List<String> lines) {
        int rowSize = lines.size();
        int colSize = lines.get(1).length()/2;
        Tile[][] gridMatrix = new Tile[rowSize][colSize];

        StringBuilder stringType = new StringBuilder();
        for (int row = 0; row < rowSize; row++) {
            // look at two char at a time from the current string
            for (int col = 0; col < lines.get(1).length() ; col += 2) {
                stringType.append(lines.get(row).substring(col, col + 2));
                // since loop start at 1, then to subtract 1
                // row = y, col = x
                // matrix[y][x] but tile (x,y)
                gridMatrix[row ][(col/2)] = new Tile((col/2), row , stringType.toString());
                // reset the string builder
                stringType.setLength(0);
            }
        }
        return gridMatrix;
    }

    public static String converEdgesToAction(Collection<Edge> edges) {
        StringBuilder action = new StringBuilder();
        Tile tile1, tile2;
        for (Edge edge : edges) {
            tile1 = (Tile) edge.getFrom().getData();
            tile2 = (Tile) edge.getTo().getData();

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


    public static List<String> removeBorders (List<String> lines) {

        List<String> newLines = Lists.newArrayList();
        String[] arrayOfCopy = new String[lines.size()];

        lines.remove(0);
        lines.remove(lines.size() - 1);

        lines.toArray(arrayOfCopy);

        for (int row = 0; row < lines.size(); row++) {
            String x = lines.get(row).substring(1, lines.get(0).length() - 1);
            newLines.add(x);
        }
        return newLines;
    }


}
