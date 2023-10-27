package graph;


/**
 * 
 * @author Xiaoqiu Huang
 * 
 * 
 */

public class TestGraph 
{
    public static void main(String[] args) 
    {
        Graph<String> G = new Graph<String>();

        G.addEdge("A", "B");
        G.addEdge("B", "C");
        G.addEdge("C", "D");
        G.addEdge("C", "G");
        G.addEdge("D", "E");
        G.addEdge("E", "F");
        G.addEdge("F", "G");

      	// print out graph
        System.out.println(G);

        // print out graph again by iterating over vertices and edges
        for (String v : G.vertices())
        {
            System.out.print(v + ": ");
            for (String w : G.adjacentTo(v)) 
            {
                System.out.print(w + " ");
            }
            System.out.println();
        }

        GraphTraversals.breadthFirstSearch(G, "A");

        G.addEdge("H", "I");
        G.addEdge("H", "J");
        G.addEdge("I", "J");
        G.addEdge("I", "K");
		G.addEdge("J", "K");
		G.addEdge("K", "L");
		G.addEdge("K", "M");
		G.addEdge("L", "M");
		G.addEdge("L", "M");

		GraphTraversals.depthFirstSearch(G);

    }
}
