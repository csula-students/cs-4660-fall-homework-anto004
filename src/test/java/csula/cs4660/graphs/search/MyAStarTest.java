package csula.cs4660.graphs.search;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.searches.AstarSearch;
import csula.cs4660.graphs.searches.BFS;
import csula.cs4660.graphs.utils.Parser;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * Created by anto004 on 10/11/16.
 */
public class MyAStarTest extends TestCase {
    private Stopwatch timer;
    private ClassLoader classLoader;
    Graph[] graph1s;
    Graph[] graph2s;
    Graph[] graph3s;
    Graph[] graph4s;
    Graph[] graph5s;

    @Test
    // a dummy test to warm up JVM machine about variables we use
    public void warmup() {
        File file1 = new File(classLoader.getResource("homework-2/grid-1.txt").getFile());
        File file2 = new File(classLoader.getResource("homework-2/grid-2.txt").getFile());
        File file3 = new File(classLoader.getResource("homework-2/grid-3.txt").getFile());
        File file4 = new File(classLoader.getResource("homework-2/grid-4.txt").getFile());
        File file5 = new File(classLoader.getResource("homework-2/grid-5.txt").getFile());

        graph1s = buildGraphs(file1);
        graph2s = buildGraphs(file2);
        graph3s = buildGraphs(file3);
        graph4s = buildGraphs(file4);
        graph5s = buildGraphs(file5);
    }

    private Graph[] buildGraphs(File file) {
        Graph[] graphs = new Graph[3];

        graphs[0] = Parser.readRectangularGridFile(
                Representation.STRATEGY.ADJACENCY_LIST,
                file
        );

//        graphs[1] = Parser.readRectangularGridFile(
//                Representation.STRATEGY.ADJACENCY_MATRIX,
//                file
//        );

        graphs[1] = Parser.readRectangularGridFile(
                Representation.STRATEGY.OBJECT_ORIENTED,
                file
        );

        return graphs;
    }

    @Before
    public void setUp() {
        classLoader = getClass().getClassLoader();
        timer = Stopwatch.createStarted();
    }

    @Test(timeout=15)
    public void testAStar1() {
        Arrays.stream(graph1s)
                .forEach(graph -> {
                    assertEquals(
                            "Test A* on graph 1",
                            "SSSSE",
                            Parser.converEdgesToAction(
                                    graph.search(
                                            new AstarSearch(),
                                            new Node<>(new Tile(3, 0, "@1")),
                                            new Node<>(new Tile(4, 4, "@6")))
                            )
                    );
                });

        System.out.println("A star 1 spends " + timer.stop());
    }

//    public void testMyAStar() {
//        Arrays.stream(graph2s)
//                .forEach(graph -> {
//                    assertEquals(
//                            "Test Astar on graph 2 from Node 0 to 5",
//                            Lists.newArrayList(
//                                    new Edge(new Node(0), new Node(6), 3),
//                                    new Edge(new Node(6), new Node(4), 1),
//                                    new Edge(new Node(4), new Node(5), 5)
//                            ),
//                            graph.search(new AstarSearch(), new Node(0), new Node(5))
//                    );
//                });
//    }
}
