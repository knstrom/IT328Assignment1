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
    static ArrayList<Integer> verts = new ArrayList<Integer>();
    static ArrayList<Integer> vertices = new ArrayList<Integer>();
    public static void main(String args[])
    {
        String fileName = "graphs2021.txt";//args[0];
        /* Read Adjacency Matrix from graphs2021.txt*/
        ArrayList<Graph> graphs = readFile(fileName);
        System.out.println(" Max Cliques in graphs in "+fileName+"\n (|V|,|E|) Cliques (size, ms used)");
        for(int i = 0; i<graphs.size();i++)
        {
            Graph current = graphs.get(i);
            fillVerts(current.getN());
            System.out.print("G"+(i+1)+" ("+current.getN()+", "+getNumEdges(current) + ") {");
            long startTime = System.currentTimeMillis();
            int maxClique = findMaxClique(current, 0, 1);
            kclique(current, 0, 1, maxClique);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.print("} size="+maxClique+", "+duration+"ms)\n");
            verts.clear();
            vertices.clear();
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
                        if(i==j)
                        {
                            m[i][j]=0;
                        }
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
        }
        total = total/2;
        return total;
    }

    public static boolean isAClique(Graph g, int numVert) //checks if adding vertex at index numVert will be a clique
    {
        for(int i=0;i<numVert;i++)
        {
            for(int j=i+1; j<numVert; j++)
            {
                if(g.getM()[i][j]==0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static int findMaxClique(Graph g, int startVert, int vertNum)
    {
        int max = 0;
        for(int i=startVert+1; i<g.getN(); i++) //goes through vertices after current certex
        {
            verts.set(vertNum, i); //add this vertex to vertex list
            if(isAClique(g, vertNum+1)); //if it is not a clique, then adding a vertex would still not make a clique 
            {
                max = Math.max(max, vertNum); //update max
                max = Math.max(max,findMaxClique(g, i, vertNum+1));//see if another edge/vertex could be added
            }
        }
        return max;
    }

    public static void kclique(Graph g, int startVert, int vertNum, int maxClique)
    {
        int max = maxClique;
        for(int i= startVert+1; i<g.getN()-(max-vertNum); i++) //go through the vertexes that have not been checked
        {
            if(getDegree(g, i) >= max) //the degree of the vertex must be >= max
            {
                verts.set(vertNum, i); //add vertex to vertex list
                if(isAClique(g, vertNum + 1))//if it is not a clique, then adding a vertex would still not make a clique 
                {
                    if(vertNum < max) //if the clique found is not the max clique size
                    {
                        kclique(g, i, vertNum+1, max); //recursion to find next vertex to add to clique
                    }
                    else //clique size is max clique size
                    {
                        printAndStore(vertNum+1); //print and store the clique
                        return;
                    }
                }
            }
        }
    }

    static void printAndStore(int n)
    {
        for (int i = 0; i < n; i++)
        {
            vertices.add(verts.get(i)); //store vertices of clique
            System.out.print(verts.get(i) + " ");
            if(i != n-1)
            {
                System.out.print(",");
            }
        }
    }

    public static int getDegree(Graph g, int vert)
    {
        int deg = 0;
        for(int i=0; i<g.getN(); i++)
        {
            deg += g.getM()[vert][i];
        }
        return deg;
    }

    public static void printVerts(ArrayList<Integer> verticies)
    {
        for(int i=0; i<verticies.size(); i++)
        {
            System.out.print(verticies.get(i));
            if(i != verts.size()-1)
            {
                System.out.print(",");
            }
        }
    }

    public static void fillVerts(int n)
    {
        for(int i=0; i<n; i++)
        {
            verts.add(0);
        }
    }

    public static void printGraph(Graph g)
    {
        for(int i=0; i<g.getN(); i++)
        {
            for(int j=0; j<g.getN();j++)
            {
                System.out.print(g.getM()[i][j]);
            }
            System.out.println();
        }
    }
}