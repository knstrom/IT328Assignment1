/*
IT 328: Programming Assignment 1
Katelyn Hartman & Kaci Stromberger
Find K-Clique for given undirected graph
9/27/2021
*/
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;

public class solveClique
{
    public static void main(String args[])
    {
        String fileName = args[0];
        /* Read Adjacency Matrix from graphs2021.txt*/
        File file = new File(fileName);
        ArrayList<Graph> graphs = readFile(fileName);
        System.out.println(" Max Cliques in graphs in "+fileName+"\n (|V|,|E|) Cliques (size, ms used)");
            //For each graph in graphs

                /* Find Number vertices and edges */
                    //int verticies = n;
                    //int edges = count ones

                /* Find Maximum k-clique */

                /* Print in following format:
                        Max Cliques in graphs in graphs2021.txt
                        (|V|,|E|) Cliques (size, ms used)
                        G1 (5, 2) {0,4} (size=2, 0 ms)
                        G2 (5, 4) {0,1,4} (size=3, 0 ms)
                        G3 (5, 6) {0,2,4} (size=3, 0 ms)
                        ............
                        ............
                        G100 (100,3428) {4,6,7,11,24,32,33,39,41,67,68,84,88,94 } (size=14, 8011 ms) */
        }
    public static ArrayList<Graph> readFile(String fileName)
    {
        ArrayList<Graph> graphs = new ArrayList<Graph>();
        try{
            Scanner file = new Scanner(new File(fileName));

            while(file.hasNext()){
                int n = file.nextInt();
                int[][] m = new int[n][n];
                file.nextLine();

                for (int i = 0; i < n; i ++){
                    String mLine = file.nextLine();
                    StringTokenizer parser = new StringTokenizer(mLine, " ", false);

                    for(int j = 0; j < n; j++){
                        m[i][j] = Integer.parseInt(parser.nextToken());
                    }                    
                }
                graphs.add(new Graph(n, m));
            }
            file.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Could not find" + fileName);
        }
        return graphs;
    }
}