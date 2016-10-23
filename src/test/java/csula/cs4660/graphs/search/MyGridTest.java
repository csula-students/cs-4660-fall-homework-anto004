package csula.cs4660.graphs.search;

import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.searches.AstarSearch;
import csula.cs4660.graphs.utils.Parser;

import java.io.File;

/**
 * Created by anto004 on 10/13/16.
 */
public class MyGridTest {

    public static void main(String [] agrs){

        File file1 = new File("/Users/anto004/Desktop/AI/cs4660-fall-2016/src/test/resources/homework-2/grid-1.txt");
        Graph graph = new Graph(Representation.of(Representation.STRATEGY.ADJACENCY_LIST));
        graph = Parser.readRectangularGridFile(
                Representation.STRATEGY.ADJACENCY_LIST,
                file1);

        String str = Parser.converEdgesToAction(graph.search(
                        new AstarSearch(),
                        new Node<>(new Tile(3, 0, "@1")),
                        new Node<>(new Tile(4, 4, "@6")))
        );
        System.out.println("parser.converEdgesToAction: "+str);


    }
}
