package csula.cs4660.graphs.searches;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

public class AstarSearch implements SearchStrategy {

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        System.out.println("AStar");
        Map<Node, Node> exploredMap = new HashMap<>();

        Map<Node<Tile>, Node<Tile>> parents = new HashMap<>();
        parents.put(source, source);


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
            if (u.equals(dist)) {
                System.out.println("found goal");

                List<Edge> result = Lists.newArrayList();

                while (!parents.get(dist).equals(dist)) {
                    result.add(new Edge(parents.get(dist), u, 1));
                    dist = parents.get(dist);
                }
//                for (ListIterator<Edge> iterator = result.listIterator(result.size()); iterator.hasPrevious(); ) {
//                    reverseList.add(iterator.previous());
//                }
                return result;


                //return constructPath(graph, u, parents, dist);
            }
            exploredMap.put(u, u);

            for (Node node : graph.neighbors(u)) {
                if (exploredMap.containsValue(node)) {
                    continue;
                }

                int tempGScore = gscore.get(u) + 1;

                if (!gscore.containsKey(node) || tempGScore < gscore.get(node)) {
                    gscore.put(node, tempGScore);
                    parents.put(node, u);
                    fscore.put(node, gscore.get(node) + heuristicCost(node, dist));
                }

                if (!frontier.contains(node)) {
                    frontier.add(node);
                }
            }
        }
        return new ArrayList<>();
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

