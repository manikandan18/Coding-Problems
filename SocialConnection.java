/*
 * This code maintains connection between 2 people. It searches for
 * connections in a symmetric manner.
 * The connections are maintained much like Facebook or LinkedIn.
 * The connect() method will take 2 parameters and connect the people. 
 * It is bi-directional. For eg., If 1 is connected to 2, 2 
 * is connected to 1 as well.
 * The isConnected() method will take 2 parameters and returns true if
 * they are connected. Else, returns false. 
 * Also, consider there is no self loop which means if isConnected 
 * called with (1, 1), it returns false. This can be changed as you like.
 * Graph is maintained for connections using adjacency
 * list and it is traversed using BFS algorithm which means you will get
 * shortest path for your connections.
 * Java Generics is maintained in the class and methods so that
 * it can be extended to multiple types. Just remember to override
 * hashcode and equals method if you declare your own custom class
 * for generic type though.
 * Happy Coding
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SocialConnection<T> {
   HashMap<T, ArrayList<T>> hm = new HashMap<T, 
		            ArrayList<T>>();
   public void connect(T x, T y) {
	   if (!hm.containsKey(x)) {
		   ArrayList<T> arrOfx = new ArrayList<T>();
		   arrOfx.add(y);
		   hm.put(x, arrOfx);
		   if (hm.containsKey(y)) {
			   /* Add it in y as it is bi-directional */
			   ArrayList<T> arrOfy = hm.get(y);
			   arrOfy.add(x);
			   hm.put(y, arrOfy);
		   }
	   }
	   if (!hm.containsKey(y)) {
		   ArrayList<T> arrOfy = new ArrayList<T>();
		   arrOfy.add(x);
		   hm.put(y, arrOfy);
		   if (hm.containsKey(x)) {
			   /* Add it in x as it is bi-directional */
			   ArrayList<T> arrOfx = hm.get(x);
			   arrOfx.add(y);
			   hm.put(x, arrOfx);
		   }
	   }
   }
   public boolean isConnected(T x, T y) {
	   if (!hm.containsKey(x) || !hm.containsKey(y))
		   return false;
	   if (x.equals(y)) return false;
	   Queue<T> q = new LinkedList<T>();
	   T src = x;
	   HashSet<T> visited = new HashSet<T>();
	   q.add(src);
	   visited.add(src);
	   while (!q.isEmpty()) {
		   T node = q.remove();
		   if (node == y) return true;
		   ArrayList<T> arr = hm.get(node);
		   for (T i:arr) {
			   if (i.equals(y))
				   return true;
			   else if (!visited.contains(i)) {
				   visited.add(i);
			       q.add(i);
			   }    
 		   }
	   }
	   return false;
   }
   public static void main(String[] args) {
	   SocialConnection<Integer> conn = new SocialConnection<Integer>();
	   conn.connect(1, 2);
	   conn.connect(2, 4);
	   conn.connect(3, 4);
	   conn.connect(0, 5);
	   System.out.println(conn.isConnected(1, 4));
   }
}
