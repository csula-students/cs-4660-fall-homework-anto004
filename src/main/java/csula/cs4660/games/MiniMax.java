package csula.cs4660.games;

import com.google.common.collect.Lists;
import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;

import java.util.*;

public class MiniMax {
    private static Integer bestValue;
    private static Integer index;

    public static Node getBestMove(Graph graph, Node root, Integer depth, Boolean max) {

        Node node = minimax(graph, root, depth, max);

        MiniMaxState miniMaxState = (MiniMaxState) node.getData();
        System.out.println(miniMaxState.getIndex()+ " "+ miniMaxState.getValue());
        return new Node<>(new MiniMaxState(miniMaxState.getIndex(), miniMaxState.getValue()));
    }

    public static Node minimax(Graph graph, Node startingNode, Integer depth, Boolean max) {
        MiniMaxState miniMaxState = (MiniMaxState) startingNode.getData();

        if (depth == 0 || graph.neighbors(startingNode).isEmpty()) {
            return startingNode;
        }
        if (max) {
            //bestValue = Integer.MAX_VALUE;

            List<Node> nodes = graph.neighbors(startingNode);
            ListIterator<Node> iterator = nodes.listIterator();

            while (iterator.hasNext()) {
                Node node = iterator.next();
                MiniMaxState mmsStartingNode = (MiniMaxState) startingNode.getData();
                Integer mmsStartingNodeValue = mmsStartingNode.getValue();
                System.out.println(" MAX Starting Node: Index " + mmsStartingNode.getIndex() + " value" + mmsStartingNode.getValue());
                MiniMaxState mmsTest = (MiniMaxState) node.getData();
                System.out.println("Checking Child Node: Index " + mmsTest.getIndex() + " value" + mmsTest.getValue());
                Node minimaxNode = minimax(graph, node, depth - 1, false);

                MiniMaxState mmsMiniMaxNode = (MiniMaxState) minimaxNode.getData();
                index = mmsMiniMaxNode.getIndex();
                Integer value = mmsMiniMaxNode.getValue();

                if ((mmsStartingNodeValue == 0) || (value > mmsStartingNodeValue)) {
                    graph = reconstructGraph(graph, startingNode, value);
                }

                bestValue = Integer.max(mmsStartingNodeValue, value);

                Optional<Node> nodeTest = graph.getNode(startingNode);
                Node nodeTemp = nodeTest.get();
                MiniMaxState mms = (MiniMaxState) nodeTemp.getData();
                startingNode = new Node<>(mms);
                System.out.println(" After Checking Startin Node: Node " + mms.getIndex() + " " + mms.getValue());
            }
            System.out.println("Max Best Value " + bestValue + " index "+ index);

            System.out.println("--- End of Max");

            Node bestValueNode = new Node<>(new MiniMaxState(index, bestValue));
            return bestValueNode;
        } else {
            //bestValue = Integer.MIN_VALUE;

            List<Node> nodes = graph.neighbors(startingNode);
            ListIterator<Node> iterator = nodes.listIterator();

            for (Node node : nodes) {
                MiniMaxState mmsStartingNode = (MiniMaxState) startingNode.getData();
                Integer mmsStartingNodeValue = mmsStartingNode.getValue();
                System.out.println(" MIN Starting Node: Index " + mmsStartingNode.getIndex() + " value" + mmsStartingNode.getValue());

                MiniMaxState mmsTest = (MiniMaxState) node.getData();
                System.out.println(" MIN Checking Child Node: Index " + mmsTest.getIndex() + " value" + mmsTest.getValue());

                Node miniMaxNode = minimax(graph, node, depth - 1, true);


                MiniMaxState mmsMiniMaxNode = (MiniMaxState) miniMaxNode.getData();
                index = mmsMiniMaxNode.getIndex();
                Integer value = mmsMiniMaxNode.getValue();
                System.out.println("VALUE: " + value);

                if (mmsStartingNodeValue == 0) {
                    System.out.println(value + " " + mmsStartingNodeValue);
                    graph = reconstructGraph(graph, startingNode, value);
                } else if (value < mmsStartingNodeValue) {
                    graph = reconstructGraph(graph, startingNode, value);
                }

                bestValue = Integer.min(mmsStartingNodeValue, value);

                Optional<Node> nodeTest = graph.getNode(startingNode);
                Node nodeTemp = nodeTest.get();
                MiniMaxState mms = (MiniMaxState) nodeTemp.getData();
                startingNode = new Node<>(mms);
                System.out.println("After MIN Checking Node :" + mms.getIndex() + " " + mms.getValue());
            }
            System.out.println("Max Best Value " + bestValue + " index "+ index);

            System.out.println("--- End of Min");
        }
        Node bestValueNode = new Node<>(new MiniMaxState(index, bestValue));
        return bestValueNode;
    }

    private static Graph reconstructGraph(Graph graph, Node startingNode, Integer value) {
        MiniMaxState miniMaxState = (MiniMaxState)startingNode.getData();
        Integer index = miniMaxState.getIndex();

        graph.removeNode(startingNode);
        graph.addNode(new Node<>(new MiniMaxState(index, value)));

        return graph;
    }

    public static Integer evaluate(Node node){
        MiniMaxState miniMaxState = (MiniMaxState) node.getData();
        return miniMaxState.getValue();
    }

    public Boolean isLeaf(Graph graph, Node node){
        return false;
    }

    public static void main(String [] args){
        Graph graph = new Graph(Representation.of(Representation.STRATEGY.OBJECT_ORIENTED));

        for (int i = 0; i < 7; i ++) {
            graph.addNode(new Node<>(new MiniMaxState(i, 0)));
        }
        graph.addNode(new Node<>(new MiniMaxState(7, -10)));
        graph.addNode(new Node<>(new MiniMaxState(8, 8)));
        graph.addNode(new Node<>(new MiniMaxState(9, 9)));
        graph.addNode(new Node<>(new MiniMaxState(10, 15)));
        graph.addNode(new Node<>(new MiniMaxState(11, 6)));
        graph.addNode(new Node<>(new MiniMaxState(12, 100)));
        graph.addNode(new Node<>(new MiniMaxState(13, -15)));
        graph.addNode(new Node<>(new MiniMaxState(14, -9)));

        graph.addEdge(new Edge(new Node<>(new MiniMaxState(0, 0)), new Node<>(new MiniMaxState(1, 0)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(0, 0)), new Node<>(new MiniMaxState(2, 0)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(1, 0)), new Node<>(new MiniMaxState(3, 0)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(1, 0)), new Node<>(new MiniMaxState(4, 0)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(2, 0)), new Node<>(new MiniMaxState(5, 0)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(2, 0)), new Node<>(new MiniMaxState(6, 0)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(3, 0)), new Node<>(new MiniMaxState(7, -10)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(3, 0)), new Node<>(new MiniMaxState(8, 8)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(4, 0)), new Node<>(new MiniMaxState(9, 9)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(4, 0)), new Node<>(new MiniMaxState(10, 15)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(5, 0)), new Node<>(new MiniMaxState(11, 6)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(5, 0)), new Node<>(new MiniMaxState(12, 100)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(6, 0)), new Node<>(new MiniMaxState(13, -15)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(6, 0)), new Node<>(new MiniMaxState(14, -9)), 1));

        MiniMax mm = new MiniMax();
        System.out.println(mm.getBestMove(graph, new Node<>(new MiniMaxState(0,0)), 3, true));
    }
}
