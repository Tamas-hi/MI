import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;


class Point{
    public int x;
    public int y;

    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }
}

public class Main {

    private static final int NORTH_WALL = 1;
    private static final int EAST_WALL = 2;
    private static final int SOUTH_WALL = 4;
    private static final int WEST_WALL = 8;
    private static final int OBJECT = 16;

    private static boolean[][] marked;


    //give a graph and a point, i give back a point that can be reachable from your point
    public static Point give_neighbour(int[][] matrix_array, Point point) {
        int current_point = matrix_array[point.x][point.y];

        //ÉSZAKON NINCS FAL
        if ((current_point & NORTH_WALL) == 0&&point.x-1!=-1) {
            if (!marked[point.x-1][point.y])
                return new Point(point.x-1, point.y);
        }

        //KELETEN NINCS FAL
        if ((current_point & EAST_WALL)==0&&point.y+1< matrix_array.length) {
            if (!marked[point.x][point.y+1])
                return new Point(point.x, point.y+1);
        }

        //DÉLEN NINCS FAL
        if ((current_point & SOUTH_WALL) == 0&&point.x+1<matrix_array[0].length) {
            if (!marked[point.x+1][point.y])
                return new Point(point.x+1, point.y);
        }

        //NYUGATON NINCS FAL
        if ((current_point & WEST_WALL) == 0&&point.y-1!=-1) {
            if (!marked[point.x][point.y-1])
                return new Point(point.x , point.y-1);
        }
        return new Point(-1, -1);

    }

    public static void NonrecursiveDFS(int[][] matrix_array) {
        int row = matrix_array.length;
        int column = matrix_array[0].length;
        //marked[v] = is there an s-v path?
        marked = new boolean[row][column];


        // depth-first search using an explicit stack
        Stack<Point> stack = new Stack<Point>();
        Stack<Point> stack_final=new Stack<Point>();
        Point targy=new Point(-1,-1);
        boolean targy_bool=false;
        marked[0][0] = true;
        stack.push(new Point(0, 0));
        while (!stack.isEmpty()) { // ameddig a stack nem ures
            Point v = stack.peek(); //akkor kiveszunk egy elemet a stackbol
            if (give_neighbour(matrix_array, v).x!=-1&&give_neighbour(matrix_array,v).y!=-1) {   // meg kell hivni egy olyan fuggveny ami visszaad egy olyan pontot, amibe at tudunk lepni a jelenlegibol
                Point w = give_neighbour(matrix_array, v); // itt történik meg a lépés, egy olyan mezőbe ahol még nem voltunk

                if((matrix_array[w.x][w.y]&OBJECT) !=0) {
                    targy.x=w.x;
                    targy.y=w.y;
                    targy_bool=true;

                }
                marked[w.x][w.y] = true;
                if(w.x==matrix_array.length-1&&w.y==matrix_array[0].length-1)
                {
                    stack_final=(Stack<Point>)stack.clone();
                }
                stack.push(w);
            } else {
                stack.pop();
            }
            System.out.format("%d %d\n",v.x,v.y);
            if(targy_bool&&v.x==targy.x&&v.y==targy.y){
                System.out.format("felvesz\n");
                targy_bool=false;
            }
        }
        Collections.reverse(stack_final);
        while(!stack_final.isEmpty()) {

            Point s=stack_final.pop();
            if(s.x!=0&&s.y!=0)
                System.out.format("%d %d\n", s.x,s.y);
        }
        System.out.format("%d %d\n",matrix_array.length-1,matrix_array[0].length-1);
    }


    public static int[][] read_in() throws IOException {

        Vector<Vector<Integer>> vector = new Vector<>();
        boolean vege = true;
        int targy = 0;

        //beolvasunk
        //File file = new File("C:\\Users\\Tomi\\Desktop\\MI\\src\\matrix1.txt");
        Scanner scanner = new Scanner(System.in);
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //String line;
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
        //br.close();
        int row;
        int column;
        row = vector.size();
        column = vector.firstElement().size();
        int[][] array = new int[row][column];
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++) {
                array[i][j] = vector.get(i).get(j);
            }
        }
        return array;

    }


    public static void main(String[] args) throws IOException {
        int[][] a=read_in();
        NonrecursiveDFS(a);
        //System.out.print("\n");
    }

}
