/*
IT 328: Programming Assignment 1
Katelyn Hartman & Kaci Stromberger
Graph structure for independent set reduction
9/29/2021
*/

public class Graph{
    private int n;
    private int[][] m;

    public Graph(int n, int[][] m){
        this.n = n;
        this.m = m;
    }

    public int getN(){
        return n;
    }

    public int[][] getM(){
        return m;
    }
}