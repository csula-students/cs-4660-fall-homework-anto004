package csula.cs4660.graphs.representations;
import com.google.common.collect.Lists;
import csula.cs4660.graphs.*;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by anto004 on 9/11/16.
 */
public class MyTest {
    public static void main(String [] args){

        File file1 = new File("/Users/anto004/Desktop/AI/cs4660-fall-2016/src/test/resources/homework-1/graph-1.txt");

        File file2 = new File("/Users/anto004/Desktop/AI/cs4660-fall-2016/src/test/resources/homework-1/graph-2.txt");
      // AdjacencyList al = new AdjacencyList(file1);
        //System.out.println(al.adjacent(new Node(1), new Node(9)));
        //System.out.println(al.addNode(new Node(13)));
        //System.out.println(al.neighbors(new Node(13)));
        //System.out.println(al.addEdge(new Edge(new Node(1), new Node(4),1)));
       // System.out.println(al.removeNode(new Node(3)));
       //System.out.println(al.removeEdge(new Edge(new Node(9), new Node(6), 1)));
        //System.out.println(al.distance(new Node (3), new Node(5)));

        //AdjacencyMatrix am = new AdjacencyMatrix(file2);
        //AdjacencyMatrix am2 = new AdjacencyMatrix();
        //System.out.println(am.addEdge(new Edge(new Node(1), new Node(2), 19)));
       // System.out.println(am.removeNode(new Node(6)));
        //System.out.println(am2.addNode(new Node(13)));
//        System.out.println(am.removeEdge(new Edge(new Node(1), new Node(2), 1)));
        //System.out.println(am.distance(new Node(3), new Node(5)));
        //System.out.println("Neigbours of"+ new Node(1) + "is:"+ am.neighbors(new Node (1)));

        ObjectOriented oo = new ObjectOriented(file1);
        //System.out.println(oo.adjacent(new Node(1), new Node(2)));
        //System.out.println(oo.neighbors(new Node(7)));
        //System.out.println(oo.addNode(new Node(13)));
        //System.out.println(oo.addEdge(new Edge(new Node(1), new Node(4),1)));
        //System.out.println(oo.removeNode(new Node(6)));
        //System.out.println(oo.removeEdge(new Edge(new Node(6), new Node(5), 1)));
       // System.out.println(oo.distance(new Node (3), new Node(5)));

    }
}
