package csula.cs4660.graphs.searches;

import com.apple.concurrent.Dispatch;
import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * As name, dijkstra search using graph structure
 */
public class DijkstraSearch implements SearchStrategy {
    Node endTile = new Node(Integer.MAX_VALUE);
    @Override
    public List<Edge> search(Graph graph, Node source, Node dest) {

        System.out.println("Dijkstras Search:");

        Queue<Node> queue = new PriorityQueue<>(11, new MyCompare());
        //HashMap(Child, Parent)
        HashMap<Node,Node> parent = new HashMap<>();
        HashMap<Node,Node> exploredMap = new HashMap<>();
        List<Edge> result = new ArrayList<>();
        source.setDistance(0);   //dist[source]=0
        queue.add(source);
        exploredMap.put(source, source);
        endTile.setDistance(Integer.MAX_VALUE);

        while(!queue.isEmpty()){
            Node u = queue.poll();
            System.out.println("Node at queue.poll()"+ u);
            for(Node node: graph.neighbors(u)){
                if(!exploredMap.containsValue(node)){
                    node.setDistance(Integer.MAX_VALUE);
                }
                else{
                    node = exploredMap.get(node); // A new node object is constructed so you need the old value.
                }
                int alt = u.getDistance() + graph.distance(u, node);
                System.out.println("value of alt:" +alt+": value of "+node+" :"+node.getDistance());
                if(alt < node.getDistance()){
                    node.setDistance(alt);
                    System.out.println(node+"is being set distance"+node.getDistance());
                    parent.put(node, u);
                    queue.add(node);
                }
                // There are many goals, you want the goal with the lowest distance
                if((node.equals(dest)) && (node.getDistance() < endTile.getDistance())){
                    endTile = new Node(node.getData());
                    endTile.setDistance(node.getDistance());
                    System.out.println("Found Goal"+endTile+"-Distance:"+endTile.getDistance());
                }
                //set doesn't add new duplicates
                //you want the duplicates with the least distance
                exploredMap.put(node,node);

            }
        }

        for(Node node: exploredMap.keySet())
            System.out.println(node+"-Distance Values:"+ exploredMap.get(node).getDistance());

        for(Node node: exploredMap.keySet()){
            System.out.println("Explored Node-"+exploredMap.get(node));
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
            System.out.println("Dijkstras Result list:"+ edge);
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

class MyCompare implements Comparator<Node>  {
    @Override
    public int compare(Node node1, Node node2) {
        if (node1.getDistance() < node2.getDistance()) {
            return -1;
        }
        else{
            return 1;
        }
    }
}
