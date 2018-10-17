import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;


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
    
    public static void read_in() throws FileNotFoundException {

        Vector<Vector<Integer>> vector = new Vector<>();
        boolean vege = true;
        int targy = 0;

        //beolvasunk
        File file = new File("C:\\Users\\TomM\\IdeaProjects\\MI\\src\\matrix1.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine() && vege) {
            String line = scanner.nextLine();
            String[] integerStrings = line.split(" ");
            Vector<Integer> vector_tmp = new Vector<>();

            if (vector_tmp.size() > 0) {
                vector_tmp.removeAllElements();
            }
            for (int i = 0; i < integerStrings.length; i++) {
                if (!integerStrings[i].equals("")) {
                    vector_tmp.add(Integer.parseInt(integerStrings[i]));
                }
                if (integerStrings.length == 1) {
                    vege = false;
                }

            }
            if (vege) {
                vector.add(vector_tmp);
            } else {
                targy = vector_tmp.get(0);
            }

        }
        scanner.close();
        for(int i=0;i<vector.size();i++)
            for(int j = 0; j<vector.firstElement().size();j++){
                System.out.println(vector.get(i).get(j));
            }
    }
    
    
    public static void main(String[] args) throws FileNotFoundException{
        read_in();
    }

}
