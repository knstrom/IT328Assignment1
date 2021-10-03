/*
IT 328: Programming Assignment 1
Katelyn Hartman & Kaci Stromberger
Reduce 3CNF to K-Clique
9/27/2021
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class solve3CNF{
    public static void main(String[] args){
        String fileName = args[0];
        ArrayList<CNF> cnfs = readFile(fileName);

        System.out.println("* Solve 3CNF " + fileName + ":  (reduced to K-Clique) *");
        System.out.println("X means can be either T or F");

        for(int i = 0; i < cnfs.size(); i++){
            CNF curCNF = cnfs.get(i);
            int k = curCNF.findK();
            int size = k * 3;

            int[][] g = createGraph(curCNF, size);

            String[] indexes = new String[size];
            for(int j = 0; j < size; j++){
                indexes[j] = "X";
            }

            //clique on g
            //Number of clique is k
            //If in clique and not negative, T
            //If in clique and negative, F
            //If not in clique, X

            //g.kclique(k);

            //for each vertex in clique
            //assign T or F
        }
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
            for(int j = i + 1; j < size; j++){
                if(!(cnf.getIntCnfValue(i) == -cnf.getIntCnfValue(j)) && i/3 != j/3){
                    g[i][j] = 1;
                }
            }
        }

        return g;
    }

    
}