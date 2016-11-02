package csula.cs4660.quizes;

import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.quizes.models.DTO;
import csula.cs4660.quizes.models.Event;
import csula.cs4660.quizes.models.State;
import csula.cs4660.quizes.models.DTO;

import java.util.*;

/**
 * Here is your quiz entry point and your app
 */
//public class App {
//    public static void main(String[] args) {
//        Graph graph = new Graph(Representation.of(Representation.STRATEGY.ADJACENCY_LIST));
//
//        // to get a state, you can simply call `Client.getState with the id`
//        State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
//<<<<<<< HEAD
//        Node source = new Node<State>(initialState);
//        System.out.println("Initial State:"+initialState);
//
//        State finalState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();
//        System.out.println("Final State:"+finalState);
//        Node dest = new Node<State>(finalState);
//
//        System.out.println("BFS");
//        //graph = constructGraph(graph, initialState, finalState);
//
//        List<Edge> path = BFSSearch(graph, source, dest);
//
//    }
//
//    public static List<Edge> BFSSearch(Graph graph, Node source, Node dest) {
//        System.out.println("BFS Search:");
//
//        State destState = (State)dest.getData();
//        State sourceState = (State)source.getData();
//
//        Node<State> endTile = new Node<State>(destState);
//
//        Queue queue = new LinkedList<Node<State>>();
//        //HashMap(Child, Parent)
//        HashMap<Node<State>,Node<State>> parent = new HashMap<>();
//        //used explored set instead of initializing to MAX value
//        List<Node<State>> exploredSet = Lists.newArrayList();
//        List<Edge> result = new ArrayList<>();
//
//        queue.add(new Node<State>(Client.getState(sourceState.getId()).get()));
//        exploredSet.add(source);
//
//        while(!queue.isEmpty()){
//            Node<State> u = (Node<State>)queue.poll();
//            State uState = u.getData();
//            System.out.println("queue.poll:"+u.getData());
//
//            for(State state: uState.getNeighbors()) {
//
//                Node<State> nodeState = new Node<State>(state);
//                if (!exploredSet.contains(nodeState)){
//                    parent.put(nodeState, u);
//                    if (nodeState.equals(dest)) {
//
//                        endTile = new Node<State>(nodeState.getData());
//                        System.out.println("Found Goal");
//                    }
//                    queue.add(new Node<State>(Client.getState(state.getId()).get()));
//                    exploredSet.add(nodeState);
//=======
//        System.out.println(initialState);
//        // to get an edge between state to its neighbor, you can call stateTransition
//        System.out.println(Client.stateTransition(initialState.getId(), initialState.getNeighbors()[0].getId()));
//
//        Queue<State> frontier = new LinkedList<>();
//        Set<State> exploredSet = new HashSet<>();
//        Map<State, State> parents = new HashMap<>();
//        frontier.add(initialState);
//
//        while (!frontier.isEmpty()) {
//            State current = frontier.poll();
//            exploredSet.add(current);
//
//            // for every possible action
//            for (State neighbor: Client.getState(current.getId()).get().getNeighbors()) {
//                // state transition
//                if (neighbor.getId().equals("e577aa79473673f6158cc73e0e5dc122")) {
//                    // construct actions from endTile
//                    System.out.println("found solution with depth of " + findDepth(parents, current, initialState));
//                }
//                if (!exploredSet.contains(neighbor)) {
//                    parents.put(neighbor, current);
//                    frontier.add(neighbor);
//>>>>>>> 41c66024dc24338984344469dcaf47c89b98a914
//                }
//            }
//        }
//
//<<<<<<< HEAD
//        for(Node node: exploredSet){
//            System.out.println("Explored Node-"+node);
//        }
//        for(Node node: parent.keySet()){
//            System.out.println("Parent of "+ node + "is:"+ parent.get(node));
//        }
//
//        while(parent.get(endTile) != null){
//            Node<State> fromNode = new Node<State>(parent.get(endTile).getData());
//            Node<State> toNode = new Node<State>(endTile.getData());
//
//            DTO stateTransition = Client.stateTransition(fromNode.getData().getId(), toNode.getData().getId()).get();
//            result.add(new Edge(fromNode, toNode, stateTransition.getEvent().getEffect()));
//
//            endTile = new Node<State>(fromNode.getData());
//        }
//
//        for(Edge edge: result){
//            System.out.println("BFS Result list:"+ edge);
//        }
//
//        List<Edge> reverseList = Lists.newArrayList();
//        for(ListIterator<Edge> iterator = result.listIterator(result.size()); iterator.hasPrevious();){
//            reverseList.add(iterator.previous());
//        }
//        for(Edge edge: reverseList){
//            System.out.println("Reversed list"+edge);
//        }
//
//        return reverseList;
//    }
//
//    public static Graph constructGraph(Graph graph, State initialState, State finalState){
//        List<Edge> edges = Lists.newArrayList();
//
//        Node source = new Node<State>(initialState);
//        Node dest = new Node<State>(finalState);
//
//        Queue queue = new LinkedList<State>();
//        queue.add(initialState);
//        while(!queue.isEmpty()) {
//            State u = (State)queue.poll();
//            System.out.println("ConsGraph queue.poll "+ u);
//
//            for (State state : u.getNeighbors()) {
//                Node fromNode = new Node<State>(u);
//                Node toNode = new Node<State>(state);
//                //source has been added, we now add the children
//                graph.addNode(toNode);
//                System.out.println("Node added to graph"+toNode);
//
//                if (Client.stateTransition(u.getId(), state.getId()).isPresent()) {
//                    DTO stateTransition = Client.stateTransition(u.getId(), state.getId()).get();
//                    graph.addEdge(new Edge(fromNode, toNode, stateTransition.getEvent().getEffect()));
//                    System.out.println("Edge added from "+u.getId()+ " to "+state.getId()+ " is "
//                            +stateTransition.getEvent().getEffect());
//                }
//                queue.add(Client.getState(state.getId()).get());
//            }
//        }
//        return graph;
//=======
//        System.out.println("Not found solution");
//    }
//
//    public static int findDepth(Map<State, State> parents, State current, State start) {
//        State c = current;
//        int depth = 0;
//
//        while (!c.equals(start)) {
//            depth ++;
//            c = parents.get(c);
//        }
//
//        return depth;
//>>>>>>> 41c66024dc24338984344469dcaf47c89b98a914
//    }
//
//
//
//}

public class App {
    public enum SEARCH_STRATEGY {
        BFS,
        DIJKSTRA
    }

    public static void main(String[] args) {
        Map<State, Integer> bfsPath = search(
            SEARCH_STRATEGY.BFS,
            "10a5461773e8fd60940a56d2e9ef7bf4",
            "e577aa79473673f6158cc73e0e5dc122"
        );
        Map<State, Integer> dijkstraPath = search(
            SEARCH_STRATEGY.DIJKSTRA,
            "10a5461773e8fd60940a56d2e9ef7bf4",
            "e577aa79473673f6158cc73e0e5dc122"
        );

        System.out.println("BFS:");
        printPathReverse(bfsPath);
        System.out.println("DijkStra:");
        printPathReverse(dijkstraPath);
    }

    public static void printPathReverse(Map<State, Integer> path) {
        // traverse the map in reverse order by iterator
        ListIterator<Map.Entry<State, Integer>> iterator =
            new ArrayList<>(path.entrySet()).listIterator(path.size());
        while (iterator.hasPrevious()) {
            Map.Entry<State, Integer> entry = iterator.previous();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public static Map<State, Integer> search(SEARCH_STRATEGY strategy, String start, String end) {
        State initialState = Client.getState(start).get();
        Map<State, Integer> distances = new HashMap<>();
        Map<State, State> parents = new HashMap<>();
        Set<State> exploredSet = new HashSet<>();
        Queue<State> frontier;
        switch (strategy) {
            case BFS:
                frontier = new LinkedList<>();
                break;
            case DIJKSTRA:
                frontier = new PriorityQueue<>(10, new StateComparator(distances));
                break;
            default:
                frontier = new LinkedList<>();
                break;
        }
        frontier.add(initialState);
        distances.put(initialState, 0);

        while (!frontier.isEmpty()) {
            State current = frontier.poll();
            exploredSet.add(current);

            // state transition
            if (current.getId().equals(end)) {
                // construct actions from endTile
                System.out.println("Found path");
                return constructPath(parents, distances, current);
            }

            // for every possible action
            for (State neighbor: Client.getState(current.getId()).get().getNeighbors()) {
                if (!exploredSet.contains(neighbor)) {
                    Optional<DTO> transition = Client.stateTransition(current.getId(), neighbor.getId());
                    distances.put(
                        neighbor,
                        distances.getOrDefault(current, 0) + transition.get().getEvent().getEffect()
                    );
                    parents.put(neighbor, current);
                    frontier.add(neighbor);
                }
            }
        }

        return new HashMap<>();
    }

    public static Map<State, Integer> constructPath(Map<State, State> parents, Map<State, Integer> distances, State current) {
        State c = current;
        Map<State, Integer> result = new LinkedHashMap<>();

        while (parents.get(c) != null) {
            result.put(c, distances.get(c));
            c = parents.get(c);
        }
        result.put(c, distances.get(c));

        return result;
    }

    public static class StateComparator implements Comparator<State> {
        private final Map<State, Integer> distances;
        public StateComparator(Map<State, Integer> distances) {
            this.distances = distances;
        }

        @Override
        public int compare(State a, State b) {
            return distances.getOrDefault(b, Integer.MAX_VALUE)
                .compareTo(distances.getOrDefault(a, Integer.MAX_VALUE));
        }
    }
}
