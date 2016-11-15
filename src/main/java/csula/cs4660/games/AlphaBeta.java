package csula.cs4660.games;

import com.google.common.collect.Lists;
import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;

import java.util.*;

public class AlphaBeta {
    private static Integer index;
    private static Integer copyAlpha;
    private static Integer copyBeta;
    private static Node best;
    public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean max) {

        Optional<Node> copySource = graph.getNode(source);
        Node copySourceNode = copySource.get();
        // startingNode gets resetted so we re-assign it

        Node node = alphabeta(graph, copySourceNode, depth, alpha, beta, max);

        MiniMaxState miniMaxState = (MiniMaxState)node.getData();
        System.out.println("Get Best Move Index: "+miniMaxState.getIndex()+" Value "+ miniMaxState.getValue());

        return null;
    }

    public static Node alphabeta(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean max) {
        if (depth == 0 || graph.neighbors(source).isEmpty()) {
            return source;
        }

        List<Node> nodes = graph.neighbors(source);
        for (Node node : nodes) {
            //Source Node
            MiniMaxState snMiniMaxState = (MiniMaxState) source.getData();
            Integer snIndex = snMiniMaxState.getIndex();
            Integer snValue = snMiniMaxState.getValue();
            System.out.println("source Node Index: " + snIndex + " Value: " + snValue);
            //current Node
            MiniMaxState miniMaxState = (MiniMaxState) node.getData();
            index = miniMaxState.getIndex();
            Integer value = miniMaxState.getValue();
            System.out.println("current Node Index: " + miniMaxState.getIndex() + " value:" + miniMaxState.getValue());

            if (max) {
                //alpha = Integer.max(alpha, beta);
                Node alphabetaNode = alphabeta(graph, node, depth - 1, -beta, -alpha, false);
                //alphabeta Returned Node
                System.out.println(" MAX ");
                MiniMaxState abNodeMiniMaxState = (MiniMaxState) alphabetaNode.getData();
                Integer abNodeIndex = abNodeMiniMaxState.getIndex();
                Integer abNodeValue = abNodeMiniMaxState.getValue();
                System.out.println("alphabetaNode Returned: index: " + abNodeIndex + " value: " + abNodeValue);

                if(copyAlpha != null && copyBeta != null)
                if(alpha.equals(Integer.MAX_VALUE) || alpha.equals(Integer.MIN_VALUE) || copyAlpha > alpha ){
                    alpha = Integer.max(copyAlpha, copyBeta);
                }

                System.out.println("alpha before setting: " + alpha + " beta is "+beta);
                System.out.println("copyAlpha: " + copyAlpha + " copybeta:" + copyBeta);
                if (-abNodeValue > alpha) {
                    alpha = -abNodeValue;
                    copyAlpha = alpha;
                    copyBeta = beta;
                    graph = reconstructGraph(graph, source, alpha);
                    System.out.println("alpha: " + alpha + " beta:" + beta);
                    System.out.println("copyAlpha: " + copyAlpha + " copybeta:" + copyBeta);
                }
                System.out.println("alpha after setting: " + alpha);
                best = new Node<>(new MiniMaxState(index, copyAlpha));
                //pruning
                if (alpha > beta) {
                    System.out.println("pruned");
                    return best;
                }
            }
            else {
                Node alphabetaNode = alphabeta(graph, node, depth - 1, -beta, -alpha, true);
                System.out.println(" MIN ");
                //alphabeta Returned Node
                MiniMaxState abNodeMiniMaxState = (MiniMaxState) alphabetaNode.getData();
                Integer abNodeIndex = abNodeMiniMaxState.getIndex();
                Integer abNodeValue = abNodeMiniMaxState.getValue();
                System.out.println("alphabetaNode Returned: index: " + abNodeIndex + " value: " + abNodeValue);

                if(copyAlpha != null && copyBeta != null)
                if(beta.equals(Integer.MAX_VALUE) || beta.equals(Integer.MIN_VALUE) || copyBeta < beta ){
                    //beta = Integer.min(copyAlpha, copyBeta);
                    //alpha = copyAlpha;
                    beta = abNodeValue;
                }

                System.out.println("alpha before setting: " + alpha + " beta is "+beta);
                System.out.println("copyAlpha: " + copyAlpha + " copybeta:" + copyBeta);
                if (-abNodeValue > alpha) {
                    alpha = -abNodeValue;
                    copyAlpha = alpha;
                    copyBeta = -alpha;
                    graph = reconstructGraph(graph, source, copyBeta);
                    System.out.println("alpha: " + alpha + " beta:" + beta);
                }
                best = new Node<>(new MiniMaxState(index, copyAlpha));
                //pruning
                if (alpha > beta) {
                    System.out.println("pruned");
                    return best;
                }
            }
            debugGraph(graph, source);
        }
        return best;
    }

    public static Graph reconstructGraph(Graph graph, Node node, Integer value){
        MiniMaxState miniMaxState = (MiniMaxState) node.getData();
        Integer index = miniMaxState.getIndex();
        System.out.println("Reconstruct index "+index +" with value "+ value);
        graph.removeNode(node);
        graph.addNode(new Node<>(new MiniMaxState(index, value)));

        return graph;
    }

    public static void debugGraph(Graph graph, Node source){
        System.out.println(" DEBUG ");
        Queue queue = new LinkedList<Node>();

        //used explored set instead of initializing to MAX value
        List<Node> exploredSet = Lists.newArrayList();
        List<Edge> result = new ArrayList<>();

        queue.add(source);
        exploredSet.add(source);

        while(!queue.isEmpty()){
            Node u = (Node)queue.poll();
            for(Node node: graph.neighbors(u)){
                if(!exploredSet.contains(node)){
                    queue.add(node);
                    exploredSet.add(node);
                }
            }
        }

        for(Node node: exploredSet){
            MiniMaxState miniMaxState = (MiniMaxState) node.getData();
            System.out.println("Nodes index "+miniMaxState.getIndex() +" value "+miniMaxState.getValue());
        }

        System.out.println(" END OF DEBUG ");
    }

    public static void main(String [] args){
        Graph graph = new Graph(Representation.of(Representation.STRATEGY.OBJECT_ORIENTED));

        for (int i = 0; i < 15; i ++) {
            graph.addNode(new Node<>(new MiniMaxState(i, 0)));
        }
        graph.addNode(new Node<>(new MiniMaxState(15, 8)));
        graph.addNode(new Node<>(new MiniMaxState(16, -8)));
        graph.addNode(new Node<>(new MiniMaxState(17, 4)));
        graph.addNode(new Node<>(new MiniMaxState(18, -9)));
        graph.addNode(new Node<>(new MiniMaxState(19, -15)));
        graph.addNode(new Node<>(new MiniMaxState(20, 9)));
        graph.addNode(new Node<>(new MiniMaxState(21, -9)));
        graph.addNode(new Node<>(new MiniMaxState(22, -13)));
        graph.addNode(new Node<>(new MiniMaxState(23, -14)));
        graph.addNode(new Node<>(new MiniMaxState(24, -16)));
        graph.addNode(new Node<>(new MiniMaxState(25, -14)));
        graph.addNode(new Node<>(new MiniMaxState(26, -15)));
        graph.addNode(new Node<>(new MiniMaxState(27, 9)));
        graph.addNode(new Node<>(new MiniMaxState(28, 3)));
        graph.addNode(new Node<>(new MiniMaxState(29, 6)));
        graph.addNode(new Node<>(new MiniMaxState(30, 14)));

        int diff = 1;
        for (int i = 0; i < 7; i ++ ) {
            graph.addEdge(new Edge(new Node<>(new MiniMaxState(i, 0)), new Node<>(new MiniMaxState(i+diff, 0)), 1));
            diff ++;
            graph.addEdge(new Edge(new Node<>(new MiniMaxState(i, 0)), new Node<>(new MiniMaxState(i+diff, 0)), 1));
        }

        graph.addEdge(new Edge(new Node<>(new MiniMaxState(7, 0)), new Node<>(new MiniMaxState(15, 8)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(7, 0)), new Node<>(new MiniMaxState(16, -8)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(8, 0)), new Node<>(new MiniMaxState(17, 4)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(8, 0)), new Node<>(new MiniMaxState(18, -9)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(9, 0)), new Node<>(new MiniMaxState(19, -15)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(9, 0)), new Node<>(new MiniMaxState(20, 9)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(10, 0)), new Node<>(new MiniMaxState(21, -9)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(10, 0)), new Node<>(new MiniMaxState(22, -13)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(11, 0)), new Node<>(new MiniMaxState(23, -14)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(11, 0)), new Node<>(new MiniMaxState(24, -16)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(12, 0)), new Node<>(new MiniMaxState(25, -14)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(12, 0)), new Node<>(new MiniMaxState(26, -15)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(13, 0)), new Node<>(new MiniMaxState(27, 9)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(13, 0)), new Node<>(new MiniMaxState(28, 3)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(14, 0)), new Node<>(new MiniMaxState(29, 6)), 1));
        graph.addEdge(new Edge(new Node<>(new MiniMaxState(14, 0)), new Node<>(new MiniMaxState(30, 14)), 1));

        AlphaBeta ab = new AlphaBeta();
        ab.getBestMove(graph, new Node<>(new MiniMaxState(0,0)),4,Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    }

}
