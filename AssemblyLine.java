package codingInterviewCracking;

/*
 * This code deals with problems involving assembly lines or build order
 * where activity 2 cannot start before activity 1 and activity 1 cannot 
 * start before activity 0 and so on. So, given an activity and
 * their dependent activities in a list, we need to determine the order
 * in which activities have to be done. 
 * This problem is direct variant of Topological Sort which can be solved
 * using dfs on every unvisited node and maintaining the visited nodes in
 * a Stack.
 * Used HashMap<Integer, ArrayList<Integer>> to maintain the activity 
 * and their dependencies but you can use any data structure you feel
 * more fit and optimum for your needs. HashSet<Integer> is used to
 * maintain Visited nodes.
 * As usual generic types are used. Use any primitive or custom data
 * types as you like. 
 * Time Complexity O(E+V) Space Complexity O(E+V)
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class AssemblyLine<T> {
	HashMap<T, ArrayList<T>> hm = new HashMap<T, 
			ArrayList<T>>();
	Stack<T> stckResult = new Stack<T>();
	public void doDFS(T src, HashSet<T> visited) {
		Stack<T> stck = new Stack<T>();
		if (!visited.contains(src)) {
		   stck.add(src);
		   visited.add(src);
		}
		else
			return;
		while (!stck.isEmpty()) {
			T node = stck.peek();
            ArrayList<T> arr = hm.get(node);
            int i = 0;
            while(i<arr.size()) {
            	if (!visited.contains(arr.get(i))) {
            		visited.add(arr.get(i));
            		stck.push(arr.get(i));
            		arr = hm.get(arr.get(i));
            		i=0;
            		continue;
            	}
                i++;
		    }    
        	stckResult.push(stck.pop());    
        }
   }

   public Stack<T> topologicalSort() {
        HashSet<T> visited = new HashSet<T>();
		for (T i:hm.keySet()) {
			doDFS(i, visited);
			if (visited.size() == hm.size())
			   //All nodes are already visited. So, return.	
			   break;	
		}
		return stckResult;
	}
   public void setNodeAndDependencies(T src, T[] dependencies) {

	   if (src == null) return;
	   if (hm.containsKey(src)) {
		   /* The activity is already present. So, dependencies 
		    * are to be added newly. So, remove already existing
		    * dependent activities from hashmap so that you can add
		    * new ones.
		    */
		   hm.remove(src);
	   }
	   ArrayList<T> arr = new ArrayList<T>();
	   if ((dependencies[0] == null) && (src != null)) {
		   /* This may very well happen for the leaf nodes where
		    * we don't have any dependencies further.
		    */
		   hm.put(src, arr);
		   return;
	   }

	   for (T i:dependencies)
		   arr.add(i);
       hm.put(src, arr);
   }
	public static void main(String[] args) {
		AssemblyLine<Integer> tsort = new AssemblyLine<Integer>();
		Integer dependencies[] = {3};
		tsort.setNodeAndDependencies(1, dependencies);
		dependencies = new Integer[2];
		dependencies[0] = 3;
		dependencies[1] = 4;
		tsort.setNodeAndDependencies(2, dependencies);
		dependencies = new Integer[1];
		dependencies[0] = 5;
		tsort.setNodeAndDependencies(3, dependencies);	
		dependencies = new Integer[1];
		dependencies[0] = 6;
		tsort.setNodeAndDependencies(4, dependencies);	
		dependencies = new Integer[2];
		dependencies[0] = 6;
		dependencies[1] = 8;
		tsort.setNodeAndDependencies(5, dependencies);
		dependencies = new Integer[1];
		dependencies[0] = 7;
		tsort.setNodeAndDependencies(6, dependencies);
		dependencies = new Integer[1];
		tsort.setNodeAndDependencies(7, dependencies);
		tsort.setNodeAndDependencies(8, dependencies);	
		Stack<Integer> stckResult = tsort.topologicalSort();
		while (!stckResult.isEmpty())
			System.out.println(stckResult.pop());
	}
}