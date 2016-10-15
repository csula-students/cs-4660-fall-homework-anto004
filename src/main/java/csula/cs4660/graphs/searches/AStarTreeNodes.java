package csula.cs4660.graphs.searches;

import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * Created by anto004 on 10/15/16.
 */
public class AStarTreeNodes implements SearchStrategy {
    Node endTile = new Node(Integer.MAX_VALUE);
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        System.out.println("AStar");
        Queue<Node> frontier = new PriorityQueue<Node>(11, new MyComparator1());
        Map<Node, Node> exploredMap = new HashMap<>();
        Map<Node, Node> parents = new HashMap<>();

        source.setDistance(0);
        frontier.add(source);

        Map<Node, Integer> gscore = new HashMap<>();
        gscore.put(source, 0);

        Map<Node, Integer> fscore = new HashMap<>();
        //fscore.put(source, heuristicCost(source, dist));

        while(!frontier.isEmpty()){
            // pop with the lowest fscore
            Node u = frontier.poll();
            System.out.println("frontier.poll():"+u);
            if(u.equals(dist)) {
                System.out.println("found goal");

                for(Node node: exploredMap.keySet()){
                    System.out.println("Explored Node-"+exploredMap.get(node));
                }
                for(Node node: parents.keySet()){
                    System.out.println("Parent of "+ node + "is:"+ parents.get(node));
                }

                return constructPath(graph, u, parents);
            }
            exploredMap.put(u,u);

            for(Node node: graph.neighbors(u)){
                System.out.println("Neighbor of "+ u+" is " + node);
                if(exploredMap.containsValue(node)){
                    System.out.println("Explored map already contains"+exploredMap.get(node));
                    continue;
                }
//                else
//                    node = exploredMap.get(node);
                if(gscore.get(node) == null)
                    node.setDistance(Integer.MAX_VALUE);
                else{
                    node.setDistance(gscore.get(node));
                    System.out.println(node+"Setting Distance to "+ gscore.get(node));
                }
                int tempGScore = gscore.get(u) + graph.distance(u, node);
                System.out.println("tempGScore="+tempGScore);

                if(tempGScore < node.getDistance()){
                    node.setDistance(tempGScore);
                    parents.put(node, u);
                    System.out.println(node+"is being set distance"+node.getDistance());
                }

                if(!frontier.contains(node)){
                    frontier.add(node);
                }
                else if(tempGScore >= gscore.get(node)){
                    continue;
                }
                gscore.put(node, tempGScore);
                //fscore.put(node, gscore.get(node) + heuristicCost(node, dist));
            }
        }

        return null;
    }

    public List<Edge> constructPath(Graph graph, Node node, Map<Node,Node> parents){
        Node endTile = new Node(node.getData());
        List<Edge> result = Lists.newArrayList();
        List<Edge> reverseList = Lists.newArrayList();

        while(parents.get(endTile) != null){
            Node fromNode = new Node(parents.get(endTile).getData());
            Node toNode = new Node(endTile.getData());
            result.add(new Edge(fromNode, toNode, graph.distance(fromNode, toNode)));
            endTile = new Node(parents.get(endTile).getData());
        }
        for(ListIterator<Edge> iterator = result.listIterator(result.size()); iterator.hasPrevious();){
            reverseList.add(iterator.previous());
        }

        for(Edge edge: result){
            System.out.println("A star Result list:"+ edge);
        }
        for(Edge edge: reverseList){
            System.out.println("Reversed list"+edge);
        }
        return reverseList;
    }
}
class MyComparator1 implements Comparator<Node>{

    @Override
    public int compare(Node node1, Node node2) {
        if(node1.getDistance() < node2.getDistance())
            return -1;
        else
            return 1;
    }
}
