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
        //root node is not a node part of the graph, it has a different memory reference
        Node node = minimax(graph, root, depth, max);

        MiniMaxState miniMaxState = (MiniMaxState) node.getData();

        return new Node<>(miniMaxState);
    }

    public static Node minimax(Graph graph, Node startingNode, Integer depth, Boolean max) {
        MiniMaxState miniMaxState = (MiniMaxState) startingNode.getData();

        if (depth == 0 || graph.neighbors(startingNode).isEmpty()) {
            return startingNode;
        }
        if (max) {
            //bestValue = Integer.MAX_VALUE;
            List<Node> nodes = graph.neighbors(startingNode);

            for(Node node: nodes){
                MiniMaxState mmsStartingNode = (MiniMaxState) startingNode.getData();
                Integer mmsStartingNodeValue = mmsStartingNode.getValue();

                Node minimaxNode = minimax(graph, node, depth - 1, false);

                MiniMaxState mmsMiniMaxNode = (MiniMaxState) minimaxNode.getData();
                Integer value = mmsMiniMaxNode.getValue();

                if ((mmsStartingNodeValue == 0) || (value > mmsStartingNodeValue)) {
                    index = mmsMiniMaxNode.getIndex();
                    graph = reconstructGraph(graph, startingNode, value);
                }

                bestValue = Integer.max(mmsStartingNodeValue, value);

                Optional<Node> startingNodeTemp = graph.getNode(startingNode);
                Node nodeTemp = startingNodeTemp.get();
                // startingNode gets resetted so we re-assign it,
                MiniMaxState mms = (MiniMaxState) nodeTemp.getData();
                startingNode = new Node<>(mms);
            }
            return new Node<>(new MiniMaxState(index, bestValue));
        } else {
            //bestValue = Integer.MIN_VALUE;
            List<Node> nodes = graph.neighbors(startingNode);

            for (Node node : nodes) {
                MiniMaxState mmsStartingNode = (MiniMaxState) startingNode.getData();
                Integer mmsStartingNodeValue = mmsStartingNode.getValue();

                Node miniMaxNode = minimax(graph, node, depth - 1, true);

                MiniMaxState mmsMiniMaxNode = (MiniMaxState) miniMaxNode.getData();

                Integer value = mmsMiniMaxNode.getValue();

                if ((mmsStartingNodeValue == 0) || (value < mmsStartingNodeValue)){
                    System.out.println(value + " " + mmsStartingNodeValue);
                    index = mmsMiniMaxNode.getIndex();
                    graph = reconstructGraph(graph, startingNode, value);
                }

                bestValue = Integer.min(mmsStartingNodeValue, value);

                Optional<Node> startingNodeTemp = graph.getNode(startingNode);
                Node nodeTemp = startingNodeTemp.get();
                MiniMaxState mms = (MiniMaxState) nodeTemp.getData();
                startingNode = new Node<>(mms);
            }
            return new Node<>(new MiniMaxState(index, bestValue));
        }

    }

    private static Graph reconstructGraph(Graph graph, Node startingNode, Integer value) {
        MiniMaxState miniMaxState = (MiniMaxState)startingNode.getData();
        Integer index = miniMaxState.getIndex();

        graph.removeNode(startingNode);
        graph.addNode(new Node<>(new MiniMaxState(index, value)));

        return graph;
    }
}
