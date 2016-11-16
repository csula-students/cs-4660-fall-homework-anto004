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
    private static Integer copyAlpha = Integer.MIN_VALUE;
    private static Integer copyBeta = Integer.MAX_VALUE;
    private static Integer bestValue;
    public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean max) {

        Optional<Node> copySource = graph.getNode(source);
        Node copySourceNode = copySource.get();
        // startingNode gets resetted so we re-assign it

        Node node = alphabeta(graph, copySourceNode, depth, alpha, beta, max);

        MiniMaxState miniMaxState = (MiniMaxState)node.getData();
        System.out.println("Get Best Move Index: "+miniMaxState.getIndex()+" Value "+ miniMaxState.getValue());

        return node;
    }

    public static Node alphabeta(Graph graph, Node startingNode, Integer depth, Integer alpha, Integer beta, Boolean max) {

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
                Node minimaxNode = alphabeta(graph, node, depth - 1, alpha, beta, false);

                MiniMaxState mmsMiniMaxNode = (MiniMaxState) minimaxNode.getData();
                Integer miniMaxNodeValue = mmsMiniMaxNode.getValue();

                //beta = copyBeta;
                System.out.println("Beta value: "+beta);
                System.out.println("Alpha Value at MAX condition: "+alpha +" for starting Node "+mmsStartingNode.getIndex());
                System.out.println("Starting Node value: "+mmsStartingNodeValue);
                if ((mmsStartingNodeValue == 0) || (miniMaxNodeValue > alpha)) {
                    alpha = miniMaxNodeValue;
                    copyAlpha = alpha;
                    //this is max so beta remains beta
                    // to get the right index, if the node satisfies the condition
                    // try with index = mmsStartingNode.getIndex
                    index = mmsMiniMaxNode.getIndex();
                    graph = reconstructGraph(graph, startingNode, miniMaxNodeValue);
                }
                System.out.println("Alpha value after, at MAX condition"+alpha);
                System.out.println("Beta value after, at MAX condition"+beta);
                bestValue = Integer.min(alpha, beta);
                if(alpha >= beta){
                    System.out.println("pruned at "+ mmsTest.getIndex());
                    return new Node<>(new MiniMaxState(index, bestValue));
                }

                Optional<Node> nodeTest = graph.getNode(startingNode);
                Node nodeTemp = nodeTest.get();
                // startingNode gets resetted so we re-assign it
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
            // ListIterator<Node> iterator = nodes.listIterator();

            for (Node node : nodes) {
                MiniMaxState mmsStartingNode = (MiniMaxState) startingNode.getData();
                Integer mmsStartingNodeValue = mmsStartingNode.getValue();
                System.out.println(" MIN Starting Node: Index " + mmsStartingNode.getIndex() + " value" + mmsStartingNode.getValue());

                MiniMaxState mmsTest = (MiniMaxState) node.getData();
                System.out.println(" MIN Checking Child Node: Index " + mmsTest.getIndex() + " value" + mmsTest.getValue());

                Node miniMaxNode = alphabeta(graph, node, depth - 1, alpha, beta, true);

                MiniMaxState mmsMiniMaxNode = (MiniMaxState) miniMaxNode.getData();
                Integer miniMaxNodeValue = mmsMiniMaxNode.getValue();

                //alpha = copyAlpha;

                System.out.println("alpha value: " + alpha);
                System.out.println("Beta Value at MIN condition: "+beta +" for starting Node "+mmsStartingNode.getIndex());
                if ((mmsStartingNodeValue == 0) || (miniMaxNodeValue < beta)) {
                    beta = miniMaxNodeValue;
                    copyBeta = beta;
                    // to get the right index, if the node satisfies the condition
                    index = mmsMiniMaxNode.getIndex();
                    graph = reconstructGraph(graph, startingNode, miniMaxNodeValue);
                }
                System.out.println("Beta value after, at MIN condition"+beta);

                bestValue = Integer.max(alpha, beta);
                if(alpha >= beta){
                    System.out.println("pruned at "+ mmsTest.getIndex());
                    return new Node<>(new MiniMaxState(index, miniMaxNodeValue));
                }

                Optional<Node> nodeTest = graph.getNode(startingNode);
                Node nodeTemp = nodeTest.get();
                MiniMaxState mms = (MiniMaxState) nodeTemp.getData();
                startingNode = new Node<>(mms);
                System.out.println("After MIN Checking Node :" + mms.getIndex() + " " + mms.getValue());
            }
            System.out.println("Max Best Value " + bestValue + " index "+ index);

            System.out.println("--- End of Min");
            Node bestValueNode = new Node<>(new MiniMaxState(index, bestValue));
            return bestValueNode;
        }

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
