package csula.cs4660.graphs.searches;

import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * Depth first search
 */
public class DFS implements SearchStrategy {
    List<Node> isDiscovered = Lists.newArrayList();
    HashMap<Node, Node> parent = new HashMap<>();
    Node endTile;

    @Override
    public List<Edge> search(Graph graph, Node source, Node dest) {
        System.out.println("DFS Search");

        List<Edge> result = Lists.newArrayList();

        isDiscovered.add(source);

        dfs(graph, source, dest);

        for(Node key: parent.keySet()){
            System.out.println("Parent of "+ key + "is:"+ parent.get(key));
        }
        while(parent.get(endTile) != null){
            Node fromNode = parent.get(endTile);
            Node toNode = endTile;
            result.add(new Edge(fromNode, toNode, graph.distance(fromNode, toNode)));
            endTile = fromNode;
        }
        for(Edge edge: result){
            System.out.println("DFS Result list:"+ edge);
        }
        List<Edge> reverseList = Lists.newArrayList();
        for(ListIterator<Edge> iterator = result.listIterator(result.size()); iterator.hasPrevious();){
            reverseList.add(iterator.previous());
        }
        for(Edge edge: reverseList){
            System.out.println("Dfs Reversed list"+edge);
        }
        return reverseList;
    }


    public  void dfs(Graph graph, Node node, Node dest)
    {
        List<Node> children = graph.neighbors(node);

        for(Node child: children){
            if(!isDiscovered.contains(child)) {
                isDiscovered.add(child);
                parent.put(child, node);
                dfs(graph, child, dest);
            }
        }
        if(node.equals(dest)){
            endTile = node;
            System.out.println("Found Goal"+ endTile);
        }
    }
}
