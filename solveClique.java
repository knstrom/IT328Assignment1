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
    public static void main(String[] args){
        String fileName = args[0];
        
        ArrayList<Graph> graphs = readFile(fileName);
        System.out.println("* Max Cliques in graphs in "+fileName+"\n    (|V|,|E|) Cliques (size, ms used)");

        for(int i = 0; i<graphs.size();i++)
        {
            Graph current = graphs.get(i);
            int[][] m = current.getM();
            int n = current.getN();
            
            System.out.print("G" + (i+1) + " (" + n +", " + countEdges(m, n) + ") {");
            
            long startTime = System.currentTimeMillis();

            ArrayList<Integer> maxClique = findMaxClique(m, n, 0);
            int maxCliqueSize = maxClique.size();

            for(int j = 0; j < maxCliqueSize; j++){
                System.out.print(maxClique.get(j));

                if(j < maxCliqueSize - 1){
                    System.out.print(",");
                }
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.print("} size=" + maxCliqueSize + ", " + duration + "ms)\n");
        }
    }

    public static ArrayList<Graph> readFile(String fileName){
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
        catch(FileNotFoundException fnfe){
            System.out.println("Could not find" + fileName);
        }

        return graphs;
    }

    public static int countEdges(int[][] m, int n){
        int total = 0;
        
        for(int i = 0; i < n - 1; i++){
            for(int j = i + 1; j < n; j++){
                if( m[i][j] == 1){
                    total++;
                }
            }
        }
        
        return total;
    }

    public static ArrayList<Integer> findCliqueFromStart(int[][] m, int n, int firstVert, int noEdge){
        ArrayList<Integer> clique = new ArrayList<Integer>();
        clique.add(firstVert);

        for(int i = 0; i < n; i++){
            boolean matchesAll = true;
            for(Integer vertex : clique){
                if(m[vertex][i] == noEdge || i == vertex){
                    matchesAll = false;
                }
            }

            if(matchesAll){
                clique.add(i);
            }
        }
        
        return clique;
    }

    public static ArrayList<Integer> findMaxClique(int[][] m, int n, int noEdge){
        ArrayList<Integer> maxClique = new ArrayList<Integer>();

        for(int i = 0; i < n; i++){
            ArrayList<Integer> curClique = findCliqueFromStart(m, n, i, noEdge);

            if(curClique.size() > maxClique.size()){
                maxClique = curClique;
            }
        }

        return maxClique;
    }
}