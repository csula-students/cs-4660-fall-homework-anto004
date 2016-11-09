package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;

import java.util.Iterator;
import java.util.List;

public class AlphaBeta {
    private static Integer index;
    private static Integer bestValue;
    public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean max) {
        // TODO: implement your alpha beta pruning algorithm here
        Node node = alphabeta(graph, source, depth, alpha, beta);

        MiniMaxState miniMaxState = (MiniMaxState)node.getData();
        System.out.println("Get Best Move Index-"+miniMaxState.getIndex()+" Value- "+ miniMaxState.getValue());

        return null;
    }

    public static Node alphabeta(Graph graph, Node source, Integer depth, Integer alpha, Integer beta){
        if(depth == 0 || graph.neighbors(source).isEmpty()){
            return source;
        }
        Node best;
        List<Node> nodes = graph.neighbors(source);
        Iterator<Node> iterator = nodes.listIterator();
        while(iterator.hasNext()){
            Node sourceNode = iterator.next();
            MiniMaxState snMiniMaxState = (MiniMaxState)sourceNode.getData();
            Integer snValue = snMiniMaxState.getValue();

            Node alphabetaNode = alphabeta(graph, sourceNode, depth - 1, -beta, -alpha);

            MiniMaxState abNodeMiniMaxState = (MiniMaxState)alphabetaNode.getData();
            index = abNodeMiniMaxState.getIndex();
            Integer abNodeValue = abNodeMiniMaxState.getValue();


            if(-abNodeValue > alpha){
                alpha = -abNodeValue;
                bestValue = alpha;
                graph = reconstructGraph(graph, source, alpha);
            }
            best = new Node<>(new MiniMaxState(index, abNodeValue));
            if (alpha > beta){
                return best;
            }
        }
        return new Node<>(new MiniMaxState(index, bestValue));
    }

    public static Graph reconstructGraph(Graph graph, Node node, Integer value){
        MiniMaxState miniMaxState = (MiniMaxState) node.getData();
        Integer index = miniMaxState.getIndex();

        graph.removeNode(node);
        graph.addNode(new Node<>(new MiniMaxState(index, value)));

        return graph;
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
