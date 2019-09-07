import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Solution {
    public static int a[][];    
    public static int n;
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        n=s.nextInt();
        a=new int[n][n];
        int max=-1;
        for(int i=0;i<n*n;i++){
            int i1=s.nextInt();
            int j=s.nextInt();
            int w=s.nextInt();
            a[i1][j]=w;
            if(max<w)
                max=w;
        }
        //changing the array for maximum matching
        print();
        changing_array(max);
        System.out.println("\n\n\n");
        int c[][]=new int[n][n];
        c=maximum_weight_matching();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(c[i][j]+" ");
            }
            System.out.println(" ");
        }
        
    }
    public static void changing_array(int w){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                a[i][j]=w-a[i][j];
    }
    public static int [][] maximum_weight_matching(){
        
        row_subtract();
        //print();
        if(isassign())
            return give_assignment();
        col_subtract();
        //print();
        //print();
        if(isassign())
            return give_assignment();
        int i=0;
        //print();
        while(!isassign()){
            min_rows_and_cols();
        }
        return give_assignment();
    }
    //print a 
    public static void print(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(a[i][j]+" ");
            }
            System.out.println(" ");
        }
    }
    //give assigment
    public static int[][] give_assignment(){
        int row[]=new int[n];
        int c[][]=new int[n][n];
        int cols[]=new int[n];
        LinkedList<Integer> ll= new LinkedList<Integer>();
        for(int i=0;i<n;i++){
            int sum=0;
            for(int j=0;j<n;j++){
                if(a[i][j]==0)
                    sum++;
            }
            row[i]=sum;
        }
        for(int i=0;i<n;i++)
            ll.add(i);
        int temp=-1;
        int col=-1;
        while(ll.size()!=0){
            for(int i=0;i<ll.size();i++){
                if(row[ll.get(i)]==1){
                    temp=ll.get(i);
                    ll.remove(i);
                    break;
                }
            }
            if(temp==-1){
               temp=ll.get(ll.size()-1);
                ll.removeLast();
            }
            row[temp]=-1;
            for(int i=0;i<n;i++){
                if(a[temp][i]==0 && cols[i]!=1)
                    col=i;
            }
            cols[col]=1;
            c[temp][col]=1;
            //System.out.println(col);
            for(int i=0;i<n;i++){
                if(a[i][col]==0){
                    row[i]--;
                }
                
            }
        }
        
        return c;
    }
    //check if it is perfect matching
    public static boolean isassign(){
        int row[]=new int[n];
        int cols[]=new int[n];
        LinkedList<Integer> ll= new LinkedList<Integer>();
        for(int i=0;i<n;i++){
            int sum=0;
            for(int j=0;j<n;j++){
                if(a[i][j]==0)
                    sum++;
            }
            row[i]=sum;
        }
        for(int i=0;i<n;i++)
            ll.add(i);
        int temp=-1;
        int col=-1;
        while(ll.size()!=0){
            for(int i=0;i<ll.size();i++){
                if(row[ll.get(i)]==1){
                    temp=ll.get(i);
                    ll.remove(i);
                    break;
                }
            }
            if(temp==-1){
               temp=ll.get(ll.size()-1);
                ll.removeLast();
            }
            row[temp]=-1;
            for(int i=0;i<n;i++){
                if(a[temp][i]==0 && cols[i]!=1)
                    col=i;
            }
            cols[col]=1;
            //System.out.println(col);
            for(int i=0;i<n;i++){
                if(a[i][col]==0){
                    row[i]--;
                }
                if(row[i]==0){
                    return false;
                }
            }
        }
        
        return true;
    }
    //step 1 to subtract min from rows
    public static void row_subtract(){
        for(int i=0;i<n;i++){
            int min=Integer.MAX_VALUE;
            for(int j=0;j<n;j++){
                if(min>a[i][j]){
                    min=a[i][j];
                }
            }
            for(int j=0;j<n;j++)
                a[i][j]-=min;
        }
    }
    //step 2 to subtract min from columns
    public static void col_subtract(){
        for(int i=0;i<n;i++){
            int min=Integer.MAX_VALUE;
            for(int j=0;j<n;j++){
                if(min>a[j][i]){
                    min=a[j][i];
                }
            }
            for(int j=0;j<n;j++)
                a[j][i]-=min;
        }
    }
    //step 3 to get minimum rows and column to cover all zeros
    public static void min_rows_and_cols(){
        int rows[]=new int[n];
        int cols[]=new int[n];
        int assign[]=new int[n];
        int c1[][]=new int[n][n];

        c1=minlines();
        for(int i=0;i<n;i++){
            int sum=0;
            for(int j=0;j<n;j++){
                if(c1[i][j]==1)
                    sum++;
            }
            if(sum==n)
                rows[i]=1;
        }
        for(int i=0;i<n;i++){
            int sum=0;
            for(int j=0;j<n;j++){
                if(c1[j][i]==1)
                    sum++;
            }
            if(sum==n)
                cols[i]=1;
        }
       
        int min=Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(rows[i]==0 && cols[j]==0){
                    if(a[i][j]<min)
                        min=a[i][j];
                }
            }
        }
        //System.out.println(min);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
               if(rows[i]==0 && cols[j]==0){
                        a[i][j]-=min;
                }
                if(cols[j]==1 && rows[i]==1){
                    a[i][j]+=min;
                }
            }
        }
    }
    public static int [][] minlines(){ 
        int[][] m2 = new int[n][n];
        int[][] m3 = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (a[row][col] == 0)
                    m2[row][col] = hvMax(row, col);
            }
        }
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (Math.abs(m2[row][col]) > 0) {
                    clearNeighbours(m2, m3, row, col);
                } 
            }
        }
        return m3;
    }
    public static int hvMax(int row, int col) {
        int vertical = 0;
        int horizontal = 0;

        for (int i = 0; i < n; i++) {
            if (a[row][i] == 0)
                horizontal++;
        }

        // check vertical
        for (int i = 0; i < n; i++) {
            if (a[i][col] == 0)
                vertical++;
        }

        return vertical > horizontal ? vertical : horizontal * -1;
    }

    public static void clearNeighbours(int[][] m2, int[][] m3, int row, int col) {
        // if vertical
        if (m2[row][col] > 0) {
            for (int i = 0; i < m2.length; i++) {
                if (m2[i][col] > 0)
                    m2[i][col] = 0; // clear neigbor
                m3[i][col] = 1; // draw line
            }
        } else {
            for (int i = 0; i < m2.length; i++) {
                if (m2[row][i] < 0)
                    m2[row][i] = 0; // clear neigbor
                m3[row][i] = 1; // draw line
            }
        }

        m2[row][col] = 0;
        m3[row][col] = 1;
    }

}
