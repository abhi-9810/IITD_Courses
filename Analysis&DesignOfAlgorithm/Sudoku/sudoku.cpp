#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

    //checking if column is ok or not.
    bool isokcolumn(int col,int grid[9][9],int num,int r){
       for(int i=0;i<9;i++)
          if(grid[i][col]==num && i!=r)
             return false;
        return true;
    }
    //checking if row is ok or not.
     bool isokrow(int row,int grid[9][9],int num,int c){
       for(int i=0;i<9;i++)
          if(grid[row][i]==num && i!=c)
             return false;
        return true;
    }
   
    //checking if grid is not conflicting
    bool isokgrid(int r, int c, int grid[9][9],int num){
        int start=(r/3)*3;
        int end=(c/3)*3;
        for(int i=0;i<3;i++)
           for(int j=0;j<3;j++)
               if(grid[i+start][j+end]==num)
                    return false;
        return true;
    }
    //printig the grid
    void print(int grid[9][9]){
       for(int i=0;i<9;i++){
         for(int j=0;j<9;j++)
            cout<<grid[i][j]<<" ";
         cout<<"\n";
       }
    }
    //find zero where to write new number.
    int * findzero(int grid[9][9]){
      static int a1[2];
       a1[0]=-1;
       a1[1]=-1; 
       //print(grid); 
       for(int i=0;i<9;i++){
          for(int j=0;j<9;j++)
             if(grid[i][j]==0){
                a1[0]=i;
                a1[1]=j;
                break; 
             }
         if(a1[0]!=-1)
             break;
       } 
       return a1;          
    }
    //solving the sudoku...backtracking.
    bool soduko(int grid[9][9]){
       int *a2; 
       a2=findzero(grid); 
       if(*(a2+0)==-1){
          return true; 
       }
       int a[2];
       a[0]=*(a2+0);
       a[1]=*(a2+1);
       for(int i=1;i<=9;i++){
        //checking if that number is possible to that place.   
        if(isokcolumn(a[1],grid,i,a[0])&& isokrow(a[0],grid,i,a[1])&& isokgrid(a[0],a[1],grid,i)){   
          grid[a[0]][a[1]]=i;  
          //becktracking (if it is possible use that number in that place)  
          if(soduko(grid)==false){
            grid[a[0]][a[1]]=0;     
          }
          else 
            return true;  
        }   
       }
       return false; 
    }
    //taking the input main function
int main() {
    int grid[9][9];
    for(int i=0;i<9;i++)
      for(int j=0;j<9;j++)
          cin>>grid[i][j];
    if(soduko(grid))
       print(grid);
    else
       cout<<"No solution exists for the given grid";
     return 0;
}
