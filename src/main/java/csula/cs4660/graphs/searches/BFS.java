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

        HashMap<String, Node> endTile = new HashMap<>();
        List<Edge> result = Lists.newArrayList();

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
                    if(node.equals(dest)){
                        endTile.put("Goal", node);
                    }
                    queue.add(node);
                    exploredSet.add(node);
                }
            }
        }
        System.out.println("The Destination Node is:"+endTile.get("Goal"));
       // while(parent.get() != null))

        for(Node node: parent.keySet()){
            System.out.println("Parent of "+ node + "is"+ parent.get(node));
        }
        while(parent.get(endTile.get("Goal") != null))


        return null;
    }
}
