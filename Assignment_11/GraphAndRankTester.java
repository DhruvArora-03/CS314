/**
 * CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 * On my honor, Dhruv, this programming assignment is my own work and I have not provided this code
 * to any other student.
 *
 * UTEID: da 32895 <br>
 * email address: dhruvarora@utexas.edu <br>
 * TA name: Grace <br>
 */

/*
 * Questions.
 *
 * 1. The assignment presents three ways to rank teams using graphs. The results, especially for the
 * last two methods are reasonable. However if all results from all college football teams are
 * included some unexpected results occur. Explain the unexpected results. You may have to do some
 * research on the various college football divisions to make an informed answer.
 * 
 * If all of the games are included then it can cause issues due to the fact that all of the top
 * teams are all likely to be connected to the 'worst' teams at the 'bottom' of the rankings. Since
 * all of them would be connected to them it is irrelevant to include the bottom teams in the
 * calculations. It also makes it harder for rankings to be accurate since the differences between
 * teams would come down to smaller margins. By not counting the bottom teams we can avoid these
 * issues and ensure fairer and easier rankings.
 * 
 * 
 * 2. Suggest another way of method of ranking teams using the results from the graph. Thoroughly
 * explain your method. The method can build on one of the three existing algorithms.
 * 
 * My method would have each vertex store the number of vertexes connected TO that vertex. It would
 * then use this number in combination with the second ranking system to rank the teams. The goal
 * here is to reward teams less for beating teams who have been beaten by many other teams, and
 * reward them more for beating teams that have not been beat as much.
 * 
 * 
 */

public class GraphAndRankTester {
    /**
     * Runs tests on Graph classes and FootballRanker class. Experiments involve results from
     * college football teams. Central nodes in the graph are compared to human rankings of the
     * teams.
     * 
     * @param args None expected.
     */
    public static void main(String[] args) {
        // dijkstra tests
        // dijkstra test 1
        String[][] g1Edges = {{"A", "B", "4"}, {"B", "G", "7"}, {"A", "D", "6"}, {"A", "H", "30"},
                {"G", "E", "5"}, {"E", "H", "5"}, {"D", "F", "2"}, {"C", "E", "5"},
                {"C", "H", "1"}};
        testDijkstra(g1Edges, "C", "F", "[C, E, G, B, A, D, F]", 1);

        // dijkstra test 2 - NOT CONNECTED
        String[][] g2Edges = {{"B", "G", "1"}, {"A", "D", "12"}, {"G", "E", "4"}, {"E", "C", "38"},
                {"D", "F", "7"}, {"E", "H", "1"}, {"H", "C", "5"}};
        testDijkstra(g2Edges, "B", "C", "[B, G, E, H, C]", 2);

        // findAllPaths tests
        // findAllPaths test 1
        String[] expectedPaths = {"Name: B                    cost per path: 11.2857, num paths: 7",
                "Name: G                    cost per path: 11.2857, num paths: 7",
                "Name: A                    cost per path: 12.4286, num paths: 7",
                "Name: E                    cost per path: 12.7143, num paths: 7",
                "Name: C                    cost per path: 15.7143, num paths: 7",
                "Name: H                    cost per path: 15.7143, num paths: 7",
                "Name: D                    cost per path: 15.8571, num paths: 7",
                "Name: F                    cost per path: 17.5714, num paths: 7"};
        testFindAllPaths("Graph 1", g1Edges, 6, 29.0, expectedPaths);

        // findAllPaths test 2
        expectedPaths =
                new String[] {"Name: E                    cost per path: 4.0000, num paths: 4",
                        "Name: H                    cost per path: 4.2500, num paths: 4",
                        "Name: G                    cost per path: 5.0000, num paths: 4",
                        "Name: B                    cost per path: 5.7500, num paths: 4",
                        "Name: C                    cost per path: 8.0000, num paths: 4",
                        "Name: D                    cost per path: 9.5000, num paths: 2",
                        "Name: F                    cost per path: 13.0000, num paths: 2",
                        "Name: A                    cost per path: 15.5000, num paths: 2"};
        testFindAllPaths("Graph 2", g2Edges, 2, 19.0, expectedPaths);

        /**
         * FootBallRanker RMSE results: - also posted to piazza
         * 
         * results from 2005 data: <br>
         * 
         * Unweighted RMSE test: 6.8 <br>
         * Weighted RMSE test: 5.8 <br>
         * Weighted and Win # RMSE test: 3.0 <br>
         * 
         * results from 2014 data: <br>
         * 
         * Unweighted RMSE test: 10.1 <br>
         * Weighted RMSE test: 8.5 <br>
         * Weighted and Win # RMSE test: 4.2 <br>
         */
    }

    private static void testDijkstra(String[][] edges, String start, String end, String expected,
            int testNum) {
        Graph graph = getGraph(edges, false);

        graph.dijkstra(start);

        String actualPath = graph.findPath(end).toString();
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test " + testNum);
        } else {
            System.out.println("Failed dijkstra path test " + testNum + " Expected: " + expected
                    + " actual " + actualPath);
        }
    }

    private static void testFindAllPaths(String graphNumber, String[][] edges, int expectedDiameter,
            double expectedCostOfLongestShortestPath, String[] expectedPaths) {
        doAllPathsTests(graphNumber, getGraph(edges, false), true, expectedDiameter,
                expectedCostOfLongestShortestPath, expectedPaths);
    }

    // return a Graph based on the given edges
    private static Graph getGraph(String[][] edges, boolean directed) {
        Graph result = new Graph();
        for (String[] edge : edges) {
            result.addEdge(edge[0], edge[1], Double.parseDouble(edge[2]));
            // If edges are for an undirected graph add edge in other direction too.
            if (!directed) {
                result.addEdge(edge[1], edge[0], Double.parseDouble(edge[2]));
            }
        }
        return result;
    }

    // Tests the all paths method. Run each set of tests twice to ensure the Graph
    // is correctly reseting each time
    private static void doAllPathsTests(String graphNumber, Graph g, boolean weighted,
            int expectedDiameter, double expectedCostOfLongestShortestPath,
            String[] expectedPaths) {

        System.out.println("\nTESTING ALL PATHS INFO ON " + graphNumber + ". ");
        for (int i = 0; i < 2; i++) {
            System.out.println("Test run = " + (i + 1));
            System.out.println("Find all paths weighted = " + weighted);
            g.findAllPaths(weighted);
            int actualDiameter = g.getDiameter();
            double actualCostOfLongestShortesPath = g.costOfLongestShortestPath();
            if (actualDiameter == expectedDiameter) {
                System.out.println("Passed diameter test.");
            } else {
                System.out.println("FAILED diameter test. " + "Expected = " + expectedDiameter
                        + " Actual = " + actualDiameter);
            }
            if (actualCostOfLongestShortesPath == expectedCostOfLongestShortestPath) {
                System.out.println("Passed cost of longest shortest path. test.");
            } else {
                System.out.println("FAILED cost of longest shortest path. " + "Expected = "
                        + expectedCostOfLongestShortestPath + " Actual = "
                        + actualCostOfLongestShortesPath);
            }
            testAllPathsInfo(g, expectedPaths);
            System.out.println();
        }

    }

    // Compare results of all paths info on Graph to expected results.
    private static void testAllPathsInfo(Graph g, String[] expectedPaths) {
        int index = 0;

        for (AllPathsInfo api : g.getAllPaths()) {
            if (expectedPaths[index].equals(api.toString())) {
                System.out.println(expectedPaths[index] + " is correct!!");
            } else {
                System.out.println("ERROR IN ALL PATHS INFO: ");
                System.out.println("index: " + index);
                System.out.println("EXPECTED: " + expectedPaths[index]);
                System.out.println("ACTUAL: " + api.toString());
                System.out.println();
            }
            index++;
        }
        System.out.println();
    }
}
