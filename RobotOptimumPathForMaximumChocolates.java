package codingInterviewCracking;

import java.util.Stack;

/* This problem deals with robot collecting maximum number of chocolates.
 * There is a 2-dimensional matrix with each row and column element has
 * number of chocolates to have. A robot has to pass through this matrix
 * from top left corner to bottom right corner in an optimum path so that
 * it can collect maximum number of chocolates. But, Robot has a constraint
 * of moving only to right and down when starting from top left corner. It
 * also has a constraint of moving one cell at a time.
 * Further, you have to find path robot has used to get to bottom right
 * corner.
 * For eg., in below matrix
 * 1 2 3
 * 2 0 0
 * 0 0 0
 * The maximum chocolates it can collect is 6. It should've followed
 * the path 1->2->3->0->0. It cannot collect maximum number of chocolates
 * if it follows any other path.
 *
 * Solution can be achieved in many ways but one of the optimum solutions
 * can be using dynamic programming.
 * Intuitively, we can create a new matrix with the sum of maximum
 * chocolates it can have in each cell.
 * So, the first row and column will be a mere sum.
 * In above eg.,
 * 1 3 6
 * 3
 * 3
 * When it comes to sub-matrix, the only way robot can reach there is from 
 * just up or left. So, the sum can be computed as
 * max(arr[row-1][col], arr[row][col-1])+arr[row][col]).
 * 
 * so, now matrix becomes 
 * 1 3 6
 * 3 3 6
 * 3 3 6
 * 
 * Hey, we have collected maximum chocolates which is 6. But, to print path,
 * start from bottom right corner and check maximum of cell to left and cell
 * above. ie.,
 * max(arr[row][col-1], arr[row-1][col]) and move in that direction. Before
 * moving, just maintain the nodes in a custom object (rows and columns)
 * and put it in Stack.
 * You'll get the path if you pop elements out of stack.
 * Time Complexity O(n^2) Space Complexity O(n^2).
 * The code below implements this algorithm.
 */

public class RobotOptimumPathForMaximumChocolates {
  Integer[][] chocolateSumMatrix;	
  public void createChocolateSumMatrix(Integer [][] chocolates) {
	  if (chocolates == null) return;
	  chocolateSumMatrix = new Integer[chocolates.length][chocolates[0].length];
      int noOfrows = chocolates.length;
      int noOfCols = chocolates[0].length;
      int sum = chocolates[0][0];
      int row = 0; int col = 1; 
      
	  while (row == 0 && col < noOfCols){
		  /* Populate first row */
		  chocolateSumMatrix[row][col] = chocolates[row][col]+sum;
          sum = chocolateSumMatrix[row][col];
		  col++;
	  }
	  sum = chocolates[0][0];
	  /* 0th is already populated. So, start with 1 */
	  row = 1;
	  col = 0; 
	  while (col ==0 && row < noOfrows){
		  /* Populate first column */
	      chocolateSumMatrix[row][col] = chocolates[row][col]+sum;
	      sum = chocolateSumMatrix[row][col];
		  row++;
	  }	 
	  /* Now populate the rest of matrix */
	  row = 1; 
	  while (row < noOfrows) {
		  col = 1;
		  while (col < noOfCols) {
			  chocolateSumMatrix[row][col] =  chocolates[row][col]+
			  Math.max(chocolateSumMatrix[row-1][col], chocolateSumMatrix[row][col-1]);
              col++;
		  }
		  row++;
	  }
   }
   public void findPathOfRobot(Stack<Point> path) {
	   int row = chocolateSumMatrix.length-1;
	   int col = chocolateSumMatrix[0].length-1;
	   Point point = new Point(row, col);
	   path.push(point);
	   while (row >= 0 && col >= 0) {
		   if (row > 0 && col > 0) {
		      if (chocolateSumMatrix[row-1][col] > 
		                      chocolateSumMatrix[row][col-1]) {
		    	 point = new Point(row-1, col);
		    	 row--;
		      }
		      else {
		    	  point = new Point(row, col-1);
		    	  col--;
		      }
		   }
		   else if (row > 0 && col == 0) {
			   /* We reached first column. The only way to go is up */
			   point = new Point(row-1, col);
			   row --;
		   }
		   else if (row == 0 && col > 0) {
			   /* We reached first row. The only way to go is left */ 
			   point = new Point(row, col-1);
			   col--;
		   }
		   else {
			   /* Reached first row first column */
			   point = new Point(row, col);
			   return;
		   }
		   path.push(point);
	   }	   
   }
   public static void main(String[] args) {
	   //Integer[][] chocolates = {{1,2,3}, {2,0,0}, {0,0,0}};
	   Integer[][] chocolates = {{1,2,3}, {3,2,1}, {1,2,3}};
	   RobotOptimumPathForMaximumChocolates paths = new
			   RobotOptimumPathForMaximumChocolates();
	   paths.createChocolateSumMatrix(chocolates);
	   System.out.println("The maximum chocolates collected is "
	                       +paths.chocolateSumMatrix[paths.chocolateSumMatrix.length-1]
	                    		   [paths.chocolateSumMatrix[0].length-1]);
	   Stack<Point> path = new Stack<Point>();
	   paths.findPathOfRobot(path);
       while (!path.isEmpty()) {
    	   Point p = path.pop();
    	   System.out.println("("+p.row+","+p.col+")");
       }
   }
}
class Point {
	int row;
	int col;
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
