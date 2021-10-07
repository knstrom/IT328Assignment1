/*
IT 328: Programming Assignment 1
Katelyn Hartman & Kaci Stromberger
Reduce Independent Set to K-Clique
9/27/2021
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;

public class solveISet{
    public static void main(String[] args){
        String fileName = args[0];
        ArrayList<Graph> graphs = readFile(fileName);

        System.out.println("* Max Independent Sets in graphs in " + fileName + " :  (reduced to K-Clique) *");
        System.out.println("   (|V|,|E|) Independent Set (size, ms used)");
        
        for(int i = 0; i < graphs.size(); i++){
            long start = System.nanoTime();
            
            Graph curGraph = graphs.get(i);
            int[][] m = curGraph.getM();
            int n = curGraph.getN();;

            int e = countEdges(m, n);

            ArrayList<Integer> cliqueResult = solveClique.findMaxClique(m, n, 1);
            int cliqueSize = cliqueResult.size();
            
            long end = System.nanoTime();

            System.out.print("G" + (i+1) + " (" + n + ", " + e + ") {");

            for(int j = 0; j < cliqueSize; j++){
                System.out.print(cliqueResult.get(j));

                if(j < cliqueSize - 1){
                    System.out.print(", ");
                }
            }
            System.out.println("} (size=" + cliqueSize + ", " + (end - start) + " ms)");
        }
        System.out.println("***");
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
}