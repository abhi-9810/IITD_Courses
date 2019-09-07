#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <stack>
using namespace std;
    int N;
    //checking if vertex is already covered or not.
    bool isvertexcovered(int v,int path[]){
       for(int i=0;i<N+1;i++)
          if(path[i]==v)//it checks if we have already covered the vertex or not.
             return true;
        return false;
    }
    //printig the path
    void print(int path[]){
       for(int i=0;i<N;i++){
            cout<<path[i]<<"-";
       }
    }
    //finding the cycle...backtracking using recursion(recursive method.).
    bool hamiltoniancycle(int** graph,int path[]){
       //edge from where to visit.
       int v1=0,index=0;
       for(int i=0;i<N+1;i++){
          if(path[i]==-1)
              break;
          else{
              v1=path[i];
              index=i;
          }
       }
      //checking the condition if the cycle is found or not.  
       if(index==N-1){
          if(graph[v1][0]==0)
              return false;
          else
              return true;
       }
       for(int i=0;i<N;i++){
        //checking if the vertex is possible to visit.   
        if(!isvertexcovered(i,path) && graph[v1][i]!=0){  
           //cout<<i; 
          path[index+1]=i;  
          //becktracking (if it is possible to visit that vertex and get hamitonain cycle).  
          if(hamiltoniancycle(graph,path)==false){
            path[index+1]=-1;//if path is not possible we will check for next edge.     
          }
          else 
            return true;//if it gives correct result using that edge.  
        }   
       }
       return false; 
    }


   //finding the cycle...backtracking using stacks (iterative method).
    bool hamiltoniancycle1(int** graph,int path[]){
       //edge from where to visit.
       stack <int>st;
       int temp[10000][N+1];
       int k=1; 
       st.push(0);//pushing the current path into the stack.
       for(int i=0;i<N+1;i++)
         temp[0][i]=path[i];  
      while(!st.empty()){//while stack is not empty.
       int path2;
          path2=st.top();//taking the top of the element and doing operations.
          st.pop();
       //int path[N+1];   
       for(int i=0;i<N+1;i++){
          path[i]=temp[path2][i];//assigning the variable the path into the stack. 
       }  
       int v1=0,index=0;
       for(int i=0;i<N+1;i++){
          if(path[i]==-1)
              break;
          else{
              v1=path[i];
              index=i;
          }
       }
       //checking if the path is completed.   
       if(index==N-1){
          if(graph[v1][0]!=0)
              return true;
       } 
       for(int i=0;i<N;i++){
        //checking if the vertex is possible to visit.   
        if(!isvertexcovered(i,path) && graph[v1][i]!=0){  
          path[index+1]=i; 
          for(int i=0;i<N+1;i++){
             temp[k][i]=path[i];
          }  
          //becktracking (if it is possible to visit that vertex and get hamitonain cycle).  
          st.push(k);//pushing the next possible path into the stack to check if it is possible to visit that edge and complete the cycle.
           k++; 
        }   
       }
      }
       return false;//returning false if the cycle is not possible. 
   }

    //taking the input main function
int main() {
    //the graph contains n vertices.
    cin>>N;
    int **graph;
    graph = new int *[N];
    for(int i = 0; i <N; i++)
      graph[i] = new int[N];
    //assuming the graph is directed.
    for(int i=0;i<N;i++){
       for(int j=0;j<N;j++) 
         cin>>graph[i][j];//adding egde 
    }
    //the path 
    int path[N+1];
    
    for(int i=0;i<N+1;i++)
       path[i]=-1; 
    //starting from first vertex;
    path[0]=0;
    if(hamiltoniancycle(graph,path)){//if hamiltonian cycle is there printing the path.
       print(path);
       cout<<"0"; 
    }
    else{
        //No cycle found.
        cout<<"No Hamiltonian Cycle found.";
    } 
    return 0;
}