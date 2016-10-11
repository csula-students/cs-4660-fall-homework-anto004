package csula.cs4660.graphs.searches;

import com.google.common.collect.*;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * Breadth first search
 */
public class BFS implements SearchStrategy {
    Node endTile;
    @Override
    public List<Edge> search(Graph graph, Node source, Node dest) {
        System.out.println("BFS Search:");

        Queue queue = new LinkedList<Node>();
        //HashMap(Child, Parent)
        HashMap<Node,Node> parent = new HashMap<>();
        //used explored set instead of initializing to MAX value
        List<Node> exploredSet = Lists.newArrayList();
        List<Edge> result = new ArrayList<>();

        queue.add(source);
        exploredSet.add(source);

        while(!queue.isEmpty()){
            Node u = (Node)queue.poll();
            for(Node node: graph.neighbors(u)){
                if(!exploredSet.contains(node)){
                    parent.put(node, u);
                    if(node.equals(dest)){
                        endTile = new Node(node.getData());
                        //System.out.println("Found Goal");
                    }
                    queue.add(node);
                    exploredSet.add(node);
                }
            }
        }

        for(Node node: exploredSet){
            System.out.println("Explored Node-"+node);
        }
        for(Node node: parent.keySet()){
            System.out.println("Parent of "+ node + "is:"+ parent.get(node));
        }

       while(parent.get(endTile) != null){
           Node fromNode = new Node(parent.get(endTile).getData());
           Node toNode = new Node(endTile.getData());
           result.add(new Edge(fromNode, toNode, graph.distance(fromNode, toNode)));
           endTile = new Node(fromNode.getData());
       }

       for(Edge edge: result){
           System.out.println("BFS Result list:"+ edge);
       }

       List<Edge> reverseList = Lists.newArrayList();
       for(ListIterator<Edge> iterator = result.listIterator(result.size()); iterator.hasPrevious();){
           reverseList.add(iterator.previous());
       }
       for(Edge edge: reverseList){
           System.out.println("Reversed list"+edge);
       }

       return reverseList;
    }
}
