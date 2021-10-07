/*
IT 328: Programming Assignment 1
Katelyn Hartman & Kaci Stromberger
Structure of a CNF
9/29/2021
*/

public class CNF{
    private int countHigh;
    private int k;
    private String[] stringCnf;
    private int[] intCnf;
    private boolean[] cnf;

    public CNF(String stringRep){
        stringCnf = stringRep.split(" ");

        intCnf = new int[stringCnf.length];
        cnf = new boolean[stringCnf.length];

        for(int i = 0; i < stringCnf.length; i++){
            intCnf[i] = Integer.parseInt(stringCnf[i]);
        }

        for(int i = 0; i < stringCnf.length; i++){
            if(intCnf[i] > 0){
                cnf[i] = true;
            }
            else if(intCnf[i] < 0){
                cnf[i] = false;
            }
        }
    }

    public int findK(){
        k = cnf.length / 3;
        return k;
    }

    public int findCountHigh(){
        countHigh = 0;

        for(int i = 0; i < cnf.length; i++){
            if(Math.abs(intCnf[i]) > countHigh){
                countHigh = Math.abs(intCnf[i]);
            }
        }

        return countHigh;
    }

    public int getIntCnfValue(int index){
        return intCnf[index];
    }
}