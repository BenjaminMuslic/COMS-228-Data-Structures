package graph;

/**
 * 
 * @author Xiaoqiu Huang
 * 
 * 
 */

import java.util.HashMap;
import java.util.Iterator;

public class GraphTraversals
{
    // It visits the vertices of a graph reachable from a source vertex s
    // in breadth-first traversal, computes the distances from s
    // to those vertices, and produces a breadth-first tree.
    public static <V> void breadthFirstSearch(Graph<V> aGraph, V s)
    {
      if ( ! aGraph.hasVertex(s) )
        throw new RuntimeException("s not in graph");
      LinkedQueue<V> queue = new LinkedQueue<V>();
      HashMap<V, String> color = new HashMap<V, String>();
      HashMap<V, Integer> dist = new HashMap<V, Integer>();
      HashMap<V, V> pred = new HashMap<V, V>();
      for ( V w : aGraph.vertices() )
        if ( ! w.equals(s) )
        {
          color.put(w, "green"); // unreached
          dist.put(w, Integer.MAX_VALUE );
          pred.put(w, null);
        }

      color.put(s, "red"); // reached but not processed
      dist.put(s, 0);
      pred.put(s, null);
      queue.enqueue(s);

      while ( ! queue.isEmpty() )
      { 
    	  V c = queue.front();
    	  for ( V w: aGraph.adjacentTo(c) )
    		  if ( color.get(w).equals( "green" ) )
    		  {
    			  color.put(w, "red"); // reached but not processed
    			  dist.put(w, dist.get(c) + 1);
    			  pred.put(w, c);
    			  queue.enqueue(w);
    		  }
    	      
    	      // Skip this else block (for finding a simple cycle).
    	      /*
    		  else  // 
    			  if ( color.get(w).equals( "red" ) )
    			  {
    				  V x = c;
    				  V y = w;
    				  while ( x != y )
    					  if ( dist.get(x) > dist.get(y) )
    						  x = pred.get(x);
    					  else
    						  y = pred.get(y);
    				  System.out.println("Part 1 of a simple cycle");
    				  for ( V z = c; z != x; z = pred.get(z) )
    					  System.out.println(z.toString() + ", " + pred.get(z).toString() );
    				  System.out.println("Part 2 of the cycle");
    				  System.out.println(c.toString() + ", " + w.toString() );
    				  for ( V z = w; z != x; z = pred.get(z) )
    					  System.out.println(z.toString() + ", " + pred.get(z).toString() );
    			  }
    			  */
    	  queue.dequeue();
    	  color.put(c, "black"); // processed
      	}

      	System.out.println( "The root: " + s.toString() );
      	for ( V w : aGraph.vertices() )
      		if ( ! w.equals(s) && pred.get(w) != null )
      			System.out.println( "Tree edge: "
      					+ pred.get(w).toString()
      					+ "->" +  w.toString()
      					+ ", Distance to "
      					+ w.toString() + ": " + dist.get(w) );
    }

    // It visits the vertices of a graph
    // in depth-first traversal, and produces a depth-first forest.
    public static <V> void depthFirstSearch(Graph<V> aGraph)
    {
      HashMap<V, String> color = new HashMap<V, String>();
      HashMap<V, V> pred = new HashMap<V, V>();
      for ( V w : aGraph.vertices() )
      {
    	  color.put(w, "green"); // unreached
          pred.put(w, null);
      }

      for ( V w : aGraph.vertices() )
        if ( color.get(w).equals( "green" ) )
        	recvisitDFS(aGraph, w, color, pred);
 //       	visitDFS(aGraph, w, color, pred);

      System.out.println("\nDFS Forest");  // This display part is omitted in class.
      for ( V w : aGraph.vertices() )
       if ( pred.get(w) == null )
         System.out.println( "The root of a DFS tree: " + w.toString() );
       else
         System.out.println( "Tree edge: "
	       + pred.get(w).toString()
	       + "->" +  w.toString() );
    }

    /* This recursive method is covered in class. */
    private static <V> void recvisitDFS(Graph<V> aGraph, V s,
            HashMap<V, String> color, HashMap<V, V> pred)
    {
      color.put(s, "red"); // reached but not processed
      for ( V w: aGraph.adjacentTo(s) )
    	  if ( color.get(w).equals( "green" ) )
    	  {
    		  pred.put(w, s);
    		  recvisitDFS(aGraph, w, color, pred);
    	  }
      color.put(s, "black"); // processed
    }

    /* An iterative depth-first search that is supposed to produce
       the same depth-first search tree as the recursive depth-first search. */
    private static <V> void visitDFS(Graph<V> aGraph, V s,
            HashMap<V, String> color, HashMap<V, V> pred)
    {
      color.put(s, "red"); // reached but not processed
      LinkedStack<V> nodestack = new LinkedStack<V>();
      LinkedStack<Iterator<V>> edgestack = new LinkedStack<Iterator<V>>();
      Iterator<V> siter = aGraph.adjacentTo(s).iterator();
      nodestack.push(s);
      edgestack.push(siter);
      while ( ! nodestack.isEmpty() )
      { 
    	  V c = nodestack.peek();
    	  Iterator<V> citer = edgestack.peek();
    	  if ( citer.hasNext() )
    	  { 
        	V w = citer.next();
        	if ( color.get(w).equals( "green" ) )
        	{ 
        		color.put(w, "red"); // reached but not processed
        		pred.put(w, c);
        		Iterator<V> witer = aGraph.adjacentTo(w).iterator();
        		nodestack.push(w);
        		edgestack.push(witer);
        	}
    	  }
    	  else
    	  { 
        	color.put(c, "black"); // processed
        	nodestack.pop(); // vertex c is removed
        	edgestack.pop(); // its edge iterator is removed
    	  }
      }
    }
}
