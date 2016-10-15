package csula.cs4660.graphs.search;

import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.utils.Parser;

import java.io.File;

/**
 * Created by anto004 on 10/13/16.
 */
public class MyGridTest {

    public static void main(String [] agrs){

        File file1 = new File("/Users/anto004/Desktop/AI/cs4660-fall-2016/src/test/resources/homework-2/grid-1.txt");

        Parser.readRectangularGridFile(
                Representation.STRATEGY.ADJACENCY_LIST,
                file1);

    }
}
