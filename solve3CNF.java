/*
IT 328: Programming Assignment 1
Katelyn Hartman & Kaci Stromberger
Reduce 3CNF to K-Clique
9/27/2021
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

public class solve3CNF{
    public static void main(String[] args){
        String fileName = args[0];
        ArrayList<CNF> cnfs = readFile(fileName);

        System.out.println("* Solve 3CNF " + fileName + ":  (reduced to K-Clique) *");
        System.out.println("X means can be either T or F\n");

        for(int i = 0; i < cnfs.size(); i++){
            long start = System.nanoTime();

            CNF curCNF = cnfs.get(i);
            int k = curCNF.findK();
            int terms = curCNF.findCountHigh();
            int size = k * 3;

            int[][] matrix = createGraph(curCNF, size);

            String[] indexes = new String[terms];
            for(int j = 0; j < terms; j++){
                indexes[j] = "X";
            }

            //clique
            ArrayList<Integer> maxClique = solveClique.findMaxClique(matrix, size, 0);
            int cliqueSize = maxClique.size();

            //No clique
            if (cliqueSize != k){
                Random rand = new Random();
                for(int j = 0; j < terms; j++){
                    int assignment = rand.nextInt(2) + 1;

                    if(assignment == 1){
                        indexes[j] = "T";
                    }
                    else{
                        indexes[j] = "F";
                    }
                }
            }

            //Number of clique is k
            for(Integer vertex : maxClique){
                int termVal = curCNF.getIntCnfValue(vertex);
                if(termVal > 0){
                    indexes[Math.abs(termVal) - 1] = "T";
                }
                else{
                    indexes[Math.abs(termVal) - 1] = "F";
                }
            }

            long end = System.nanoTime();
            
            //Print
            System.out.print("3CNF No. " + (i + 1) + ": [n=" + terms + " k=" + k + "] ");

            if(cliqueSize != k){
                System.out.print("No solution.");
            }
            else{
                print(indexes, terms);
            }
            System.out.println();

            for(int j = 0; j < k; j++){
                System.out.print("(");
                for(int l = 0; l < 3; l++){
                    System.out.print(curCNF.getIntCnfValue(j*3 + l));
                    if(l < 2){
                        System.out.print("|");
                    }
                }
                System.out.print(")");
                if(j < k-1){
                    System.out.print("^");
                }
            }
            System.out.print(" ");

            
            print(indexes, terms);
            System.out.print("==>");

            for(int j = 0; j < k; j++){
                System.out.print("(");
                for(int l = 0; l < 3; l++){
                    int termVal = curCNF.getIntCnfValue(j*3 + l);
                    if(termVal > 0 && indexes[Math.abs(curCNF.getIntCnfValue(j*3 + l)) - 1].equals("T") || termVal < 0 && indexes[Math.abs(curCNF.getIntCnfValue(j*3 + l)) - 1].equals("F")){
                        System.out.print("T");
                    }
                    else if(termVal < 0 && indexes[Math.abs(curCNF.getIntCnfValue(j*3 + l)) - 1].equals("T") || termVal > 0 && indexes[Math.abs(curCNF.getIntCnfValue(j*3 + l)) - 1].equals("F")){
                        System.out.print("F");
                    }
                    else{
                        System.out.print("X");
                    }
                    
                    if(l < 2){
                        System.out.print("|");
                    }
                }
                System.out.print(")");
                if(j < k-1){
                    System.out.print("^");
                }
            }
            

            System.out.println("  (" + (end - start) + " ms)\n");
        }
        System.out.println("***");
    }
    
    public static ArrayList<CNF> readFile(String fileName){
        ArrayList<CNF> cnfsFromFile = new ArrayList<CNF>();

        try{
            Scanner file = new Scanner(new File(fileName));

            while(file.hasNext()){
                cnfsFromFile.add(new CNF(file.nextLine()));
            }
            file.close();
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Could not find" + fileName);
        }

        return cnfsFromFile;
    } 

    public static int[][] createGraph(CNF cnf, int size){
        int[][] g = new int[size][size];

        for(int i = 0; i < size; i++){
            g[i][i] = 1;
        }

        for(int i = 0; i < size; i++){
            for(int j = i + 1; j < size; j++){
                if(!(cnf.getIntCnfValue(i) == -cnf.getIntCnfValue(j)) && i/3 != j/3){
                    g[i][j] = 1;
                }
            }
        }

        return g;
    }

    public static void print(String[] bools, int n){
        System.out.print("[");
        for(int j = 0; j < n; j++){
            System.out.print((j+1) + ":" + bools[j]);
            if(j < n-1){
                System.out.print(" ");
            }
        }
        System.out.print("]");
    }
}