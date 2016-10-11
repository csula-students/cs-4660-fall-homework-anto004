package csula.cs4660.graphs.representations;
import csula.cs4660.graphs.*;

import java.io.File;

/**
 * Created by anto004 on 9/11/16.
 */
public class MyTest {
    public static void main(String [] args){

        File file1 = new File("/Users/anto004/Desktop/AI/cs4660-fall-2016/src/test/resources/homework-1/graph-1.txt");

        File file2 = new File("/Users/anto004/Desktop/AI/cs4660-fall-2016/src/test/resources/homework-1/graph-2.txt");
       // AdjacencyList al = new AdjacencyList(file2);

        //System.out.println(al.addEdge(new Edge(new Node(1), new Node(4),1)));
       // System.out.println(al.removeNode(new Node(6)));
       // System.out.println(al.removeEdge(new Edge(new Node(6), new Node(5), 1)));
        //System.out.println(al.distance(new Node (3), new Node(5)));

        AdjacencyMatrix am = new AdjacencyMatrix(file2);
       // System.out.println(am.removeNode(new Node(6)));
        //System.out.println(am.addNode(new Node(1)));
//        System.out.println(am.removeEdge(new Edge(new Node(1), new Node(2), 1)));
        //System.out.println(am.distance(new Node(3), new Node(5)));
        //System.out.println("Neigbours of"+ new Node(1) + "is:"+ am.neighbors(new Node (1)));

        //ObjectOriented oo = new ObjectOriented(file2);
        //System.out.println(oo.neighbors(new Node(4)));
        //System.out.println(oo.addEdge(new Edge(new Node(1), new Node(4),1)));
        //System.out.println(oo.removeNode(new Node(6)));
        // System.out.println(al.removeEdge(new Edge(new Node(6), new Node(5), 1)));
       // System.out.println(oo.distance(new Node (3), new Node(5)));
        Node testdistNode = new Node(0);
        testdistNode.setDistance(5);
        System.out.print("Integer MAX value after adding"+Integer.MAX_VALUE + 20);

    }
}
