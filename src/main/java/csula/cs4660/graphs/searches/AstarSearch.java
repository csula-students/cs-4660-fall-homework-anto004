package csula.cs4660.graphs.searches;

import com.google.common.collect.Lists;
import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

public class AstarSearch implements SearchStrategy {
    Node endTile = new Node(Integer.MAX_VALUE);
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        System.out.println("AStar");
        Map<Node, Node> exploredMap = new HashMap<>();
        Map<Node, Node> parents = new HashMap<>();

        Map<Node, Integer> gscore = new HashMap<>();
        gscore.put(source, 0);

        Map<Node, Integer> fscore = new HashMap<>();
        fscore.put(source, heuristicCost(source, dist));

        Queue<Node<Tile>> frontier = new PriorityQueue<Node<Tile>>((node1, node2)->{
            int n1Score = fscore.get(node1), n2Score = fscore.get(node2);

            if (n1Score < n2Score) {
                return -1;
            } else if (n1Score == n2Score) {
                int g1score = gscore.get(node1);
                int g2score = gscore.get(node2);

                if (g1score < g2score)
                    return 0;
                else if (g1score > g2score)
                    return 7;
                else {
                    if (isNorth(node1, node2))
                        return 2;
                    else if (isEast(node1, node2))
                        return 3;
                    else if (isWest(node1, node2))
                        return 4;
                    else if (isSouth(node1, node2))
                        return 5;
                    else
                        return 6;
                }
            } else return 7;
        });

        //source.setDistance(0);
        frontier.add(source);

        while (!frontier.isEmpty()) {
            // pop with the lowest fscore
            Node u = frontier.poll();
            Tile uTile = (Tile)u.getData();
            System.out.println("frontier.poll():" + uTile.getX() +" "+uTile.getY());
            if (u.equals(dist)) {
                System.out.println("found goal");

                for (Node node : exploredMap.keySet()) {
                    System.out.println("Explored Node-" + exploredMap.get(node));
                }
                for (Node node : parents.keySet()) {
                    System.out.println("Parent of " + node + "is:" + parents.get(node));
                }

                return constructPath(graph, u, parents);
            }
            exploredMap.put(u, u);

            for (Node node : graph.neighbors(u)) {
                System.out.println("Neighbor of " + u + " is " + node);
                if (exploredMap.containsValue(node)) {
                    System.out.println("Explored map already contains" + exploredMap.get(node));
                    continue;
                }
//                else
//                    node = exploredMap.get(node);
//                if (gscore.get(node) == null)
//                    node.setDistance(Integer.MAX_VALUE);
//                else {
//                    node.setDistance(gscore.get(node));
//                    System.out.println(node + "Setting Distance to " + gscore.get(node));
//                }
                int tempGScore = gscore.get(u) + 1;
                System.out.println("tempGScore=" + tempGScore);

                if (tempGScore < gscore.get(node)) {
                    gscore.put(node, tempGScore);
                    parents.put(node, u);
                    fscore.put(node, gscore.get(node) + heuristicCost(node, dist));
                    System.out.println(node + "gscore is" + gscore.get(tempGScore));
                }

                if (!frontier.contains(node)) {
                    frontier.add(node);
                }

            }
        }

        return null;
    }

    public List<Edge> constructPath(Graph graph, Node endTile, Map<Node,Node> parents) {
        List<Edge> result = Lists.newArrayList();
        List<Edge> reverseList = Lists.newArrayList();

        while (parents.get(endTile) != null) {
            Node fromNode = new Node(parents.get(endTile));
            result.add(new Edge(fromNode, endTile, 1));
            endTile = new Node(parents.get(endTile));
        }
        for (ListIterator<Edge> iterator = result.listIterator(result.size()); iterator.hasPrevious(); ) {
            reverseList.add(iterator.previous());
        }

        for (Edge edge : result) {
            System.out.println("A star Result list:" + edge);
        }
        for (Edge edge : reverseList) {
            System.out.println("Reversed list" + edge);
        }
        return reverseList;
    }


    private int heuristicCost(Node node, Node goal) {
        Tile nodeTile = (Tile) node.getData();
        Tile goalTile = (Tile) goal.getData();

        int dx = Math.abs(nodeTile.getX() - goalTile.getX());
        int dy = Math.abs(nodeTile.getY() - goalTile.getY());

        return dx + dy;
    }

    private boolean isNorth(Node src, Node goal) {
        return ((Tile) src.getData()).getY() > ((Tile) goal.getData()).getY();
    }

    private boolean isSouth(Node src, Node goal) {
        return ((Tile) src.getData()).getY() < ((Tile) goal.getData()).getY();
    }

    private boolean isWest(Node src, Node goal) {
        return ((Tile) src.getData()).getX() > ((Tile) goal.getData()).getX();
    }

    private boolean isEast(Node src, Node goal) {
        return ((Tile) src.getData()).getX() < ((Tile) goal.getData()).getX();
    }
}

