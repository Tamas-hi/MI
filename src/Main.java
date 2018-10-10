import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;


public class Main {

    private static final int NORTH_WALL = 1;
    private static final int EAST_WALL = 2;
    private static final int SOUTH_WALL = 4;
    private static final int WEST_WALL = 8;
    private static final int OBJECT = 16;

    private static boolean[][] marked;



    //give a graph and a point, i give back a point that can be reachable from your point
    public  static Point give_neighbour(int[][] matrix_array, Point point){
        int current_point=matrix_array[point.x][point.y];
        if((current_point & NORTH_WALL) == 0){
            if(!marked[point.x][point.x-1])
                return new Point(point.x,point.x-1);
        }
        if((current_point & EAST_WALL) == 0){
            if(!marked[point.x+1][point.x])
                return new Point(point.x+1,point.x);
        }
        if((current_point & SOUTH_WALL) == 0){
            if(!marked[point.x][point.x+1])
                return new Point(point.x,point.x+1);
        }
        if((current_point & WEST_WALL) == 0){
            if(!marked[point.x-1][point.x])
                return new Point(point.x-1,point.x);
        }
        return new Point(-1,-1);

    }
    public static void NonrecursiveDFS(int[][] matrix_array) {
        int size = matrix_array.length;
        //marked[v] = is there an s-v path?
        marked = new boolean[size][size];


        // depth-first search using an explicit stack
        Stack<Point> stack = new Stack<Point>();
        marked[0][0] = true;
        stack.push(new Point(0,0));
        while (!stack.isEmpty()) { // ameddig a stack nem ures
            Point v = stack.peek(); //akkor kiveszunk egy elemet a stackbol
            if (new Point(-1,-1)!=give_neighbour(matrix_array,v)) {   // meg kell hivni egy olyan fuggveny ami visszaad egy olyan pontot, amibe at tudunk lepni a jelenlegibol
                Point w = give_neighbour(matrix_array,v); // itt történik meg a lépés, egy olyan mezőbe ahol még nem voltunk
                // StdOut.printf("check %d\n", w);
                // discovered vertex w for the first time
                marked[w.x][w.y] = true;
                // edgeTo[w] = v;
                stack.push(w);
                System.out.println( w.x);
            }
            else {
                // StdOut.printf("%d done\n", v);
                stack.pop();
            }
        }
    }


    public static int[][] readMatrix(String fileName,int row, int col) throws FileNotFoundException {
        int[][] matrix = new int[row][col];

        Scanner inFile = new Scanner(new File(fileName));
        for(int r = 0; r < row; r++) {
            for(int c = 0; c < col; c++) {
                if(inFile.hasNextInt()) {
                    matrix[r][c] = inFile.nextInt();
                }
            }

        }
        inFile.close();

        return matrix;
    }
    public static void printMatrix(int[][] matrix, int row, int col) {
        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++)
                System.out.printf("%5d", matrix[r][c]);
            System.out.println();
        }
    }
    public static void main(String[] args) throws FileNotFoundException{
        int[][] a;
        int row = 10, col = 10;
        Scanner inFile = null;



        a = new int[row][col];
        a = readMatrix("matrix1.txt", row, col);
        printMatrix(a, row, col);
        NonrecursiveDFS(a);
    }

}
