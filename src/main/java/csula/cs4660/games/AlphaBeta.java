package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

public class AlphaBeta {
    private static Integer index;
    private static Integer bestValue;

    public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean max) {

        Node node = alphabeta(graph, source, depth, alpha, beta, max);
        return node;
    }

    public static Node alphabeta(Graph graph, Node startingNode, Integer depth, Integer alpha, Integer beta, Boolean max) {

        if (depth == 0 || graph.neighbors(startingNode).isEmpty()) {
            return startingNode;
        }
        if (max) {
            //bestValue = Integer.MAX_VALUE;
            List<Node> nodes = graph.neighbors(startingNode);

            for(Node node: nodes){
                MiniMaxState mmsStartingNode = (MiniMaxState) startingNode.getData();
                Integer mmsStartingNodeValue = mmsStartingNode.getValue();

                Node minimaxNode = alphabeta(graph, node, depth - 1, alpha, beta, false);

                MiniMaxState mmsMiniMaxNode = (MiniMaxState) minimaxNode.getData();
                Integer miniMaxNodeValue = mmsMiniMaxNode.getValue();

                if ((mmsStartingNodeValue == 0) || (miniMaxNodeValue > alpha)) {
                    alpha = miniMaxNodeValue;
                    index = mmsMiniMaxNode.getIndex();
                    graph = reconstructGraph(graph, startingNode, miniMaxNodeValue);
                }

                bestValue = Integer.min(alpha, beta);

                if(alpha >= beta){
                    return new Node<>(new MiniMaxState(index, bestValue));
                }

                Optional<Node> startingNodeTemp = graph.getNode(startingNode);
                Node nodeTemp = startingNodeTemp.get();
                // startingNode gets resetted so we re-assign it
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

                Node miniMaxNode = alphabeta(graph, node, depth - 1, alpha, beta, true);

                MiniMaxState mmsMiniMaxNode = (MiniMaxState) miniMaxNode.getData();
                Integer miniMaxNodeValue = mmsMiniMaxNode.getValue();

                if ((mmsStartingNodeValue == 0) || (miniMaxNodeValue < beta)) {
                    beta = miniMaxNodeValue;
                    index = mmsMiniMaxNode.getIndex();
                    graph = reconstructGraph(graph, startingNode, miniMaxNodeValue);
                }

                bestValue = Integer.max(alpha, beta);
                if(alpha >= beta){
                    return new Node<>(new MiniMaxState(index, miniMaxNodeValue));
                }

                Optional<Node> startingNodeTemp = graph.getNode(startingNode);
                Node nodeTemp = startingNodeTemp.get();
                MiniMaxState mms = (MiniMaxState) nodeTemp.getData();
                startingNode = new Node<>(mms);
            }
            return new Node<>(new MiniMaxState(index, bestValue));
        }

    }

    public static Graph reconstructGraph(Graph graph, Node node, Integer value){
        MiniMaxState miniMaxState = (MiniMaxState) node.getData();
        Integer index = miniMaxState.getIndex();

        graph.removeNode(node);
        graph.addNode(new Node<>(new MiniMaxState(index, value)));

        return graph;
    }



}
