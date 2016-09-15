package csula.cs4660.graphs.representations;
import csula.cs4660.graphs.*;

import java.io.File;

/**
 * Created by anto004 on 9/11/16.
 */
public class MyTest {
    public static void main(String [] args){

        File file1 = new File("/Users/anto004/Desktop/AI/cs4660-fall-2016/src/test/resources/homework-1/graph-1.txt");

        //AdjacencyList al = new AdjacencyList(file1);

        //System.out.println(al.addEdge(new Edge(new Node(1), new Node(4),1)));
       // System.out.println(al.removeNode(new Node(6)));
       // System.out.println(al.removeEdge(new Edge(new Node(6), new Node(5), 1)));

        AdjacencyMatrix am = new AdjacencyMatrix(file1);


    }
}
