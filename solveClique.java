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
        ArrayList<Graph> graphs = readFile(fileName);
        System.out.println(" Max Cliques in graphs in "+fileName+"\n (|V|,|E|) Cliques (size, ms used)");
            //For each graph in graphs
        for(int i = 0; i<graphs.size();i++)
        {
            ArrayList<Integer> verticies = new ArrayList<Integer>();
            Graph current = graphs.get(i);
            System.out.print("G"+i+" ("+current.getN()+", "+getNumEdges(current) + " {");
            long startTime = System.currentTimeMillis();
            int maxClique = findMaxClique(current, 0, 1);
            verticies = kclique(current, 0, 1, verticies);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            printVerts(verticies);
            System.out.print(" size="+maxClique+", "+duration+"ms)\n");
        }
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

    public static int getNumEdges(Graph g)
    {
        int total = 0;
        for(int i=0; i<g.getN(); i++)
        {
            for(int j=0; j<g.getN();j++)
            {
                if(g.getM()[i][j] == 1)
                {
                    total++;
                }
            }
            total--;
        }
        total = total/2;
        return total;
    }

    public static boolean isAClique(int numVert, int[][] m)
    {
        for(int i=1;i<numVert;i++)
        {
            for(int j=i+1; j<numVert; j++)
            {
                if(m[i][j]==0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static int findMaxClique(Graph g, int startVert, int vertNum)
    {
        ArrayList<Integer> verts = new ArrayList<Integer>();
        int max = 0;
        for(int i=startVert+1; i<=g.getN(); i++)
        {
            verts.add(vertNum, i);
            if(isAClique(vertNum+1, g.getM()))
            {
                max = Math.max(max, vertNum);
                max = Math.max(max,findMaxClique(g, i, vertNum+1));
            }
        }
        return max;
    }

    public static ArrayList<Integer> kclique(Graph g, int startVert, int vertNum, ArrayList<Integer> verticies)
    {
        ArrayList<Integer> verts = new ArrayList<Integer>();
        int max = findMaxClique(g, 0, 1);
        for(int i= startVert+1; i<g.getN()-(max-1); i++)
        {
            if(getDegree(g, i) >= max)
            {
                verts.add(vertNum, i);
                if(isAClique(vertNum + 1, g.getM()))
                {
                    if(vertNum < max)
                    {
                        kclique(g, i, vertNum+1, verticies);
                    }
                    else
                    {
                        verticies.add(vertNum-1);
                        if(verticies.size() >= max)
                        {
                            return verticies;
                        }
                    }
                }
            }
        }
        return verticies;
    }

    public static int getDegree(Graph g, int vert)
    {
        int deg = -1;
        for(int i=0; i<g.getN(); i++)
        {
            for(int j=0; j<g.getN(); j++)
            {
                deg += g.getM()[i][j];
            }
        }
        return deg;
    }

    public static void printVerts(ArrayList<Integer> verts)
    {
        for(int i=0; i<verts.size(); i++)
        {
            System.out.print(verts.get(i));
            if(i != verts.size()-1)
            {
                System.out.print(",");
            }
        }
    }

}