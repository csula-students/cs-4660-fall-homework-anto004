package csula.cs4660.graphs.searches;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * Breadth first search
 */
public class BFS implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dest) {
        System.out.println("BFS Search:");
        //HashMap<Parent, Child>
        Multimap<Node, Node> parent = ArrayListMultimap.create();
       // List<Node> frontier = graph.neighbors(source);
        List<Node> exploredSet = Lists.newArrayList();
        exploredSet.add(source);
        //List<Node> endTile = Lists.newArrayList();
       // List<Edge> result = Lists.newArrayList();

        Queue queue = new LinkedList<Node>();
        queue.add(source);

        while(!queue.isEmpty()){
            Node u = (Node)queue.poll();
            System.out.println("Round begins");
            for(Node node: exploredSet){
                System.out.println("Explored Set contains this node: "+node);
            }
            for(Node node: graph.neighbors(u)){
                parent.put(u, node);
                if(!exploredSet.contains(node)){
//                    if(u.equals(dest)){
//                        endTile.add(u);
//                    }
                    queue.add(node);
                    exploredSet.add(node);
                }
            }
        }

        for(Node node: parent.keySet()){
            System.out.println("Parent of "+ node + "is"+ parent.get(node));
        }


        return null;
    }
}
