#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
#include <deque>
using std::cout;
using std::cin;
using namespace std;
 static int count1;
void per(int a[],int first,int n);
bool check(int a[],int n);
bool f(deque<int> ll,deque<int> ll1,deque<int> ll2);
int main() {
   count1=0;
    int n;
    cin>>n;
    int a[n];
    for(int i=0;i<n;i++)
       a[i]=i+1;
     per(a,0,n);
    cout<<count1;      
    return 0;
}
//Function of permutation;
void per(int a[],int first,int n){
        if(first==n){
          if(!check(a,n)){
            count1++; 
           // for(int i=0;i<n;i++)
             //  cout<<a[i]<<" ";
              // cout<<" "<<"\n";
           }
        }    
        else{
            for(int i=first;i<n;i++){
                int temp=a[i];
                a[i]=a[first];
                a[first]=temp;
               per(a,first+1,n);
                temp=a[i];
                a[i]=a[first];
                a[first]=temp;
               
            }
          
        }
        
    }

 //checking if the permutation is possible or not.
 bool check(int a[],int n){
    
      deque<int> ll;
        for (int i=0; i<n; ++i) {
            ll.push_back(a[i]);
        }
     deque<int> ll1;
     deque<int> ll2;
      for(int i=0;i<n;i++)
        ll2.push_back(i+1);
   
    
      return f(ll,ll1,ll2); 
    // return false;
    }
     bool f(deque<int> ll,deque<int> ll1,deque<int> ll2){
        if(ll.size()==0)
          return true;
        else{
           if((signed)ll2.size()==0){
              int temp=ll.front();
              ll.pop_front();
              int a1=ll1.front();
              if(a1==temp){
                ll1.pop_front();
                return f(ll,ll1,ll2);
              }
              int a2=ll1.back();
              if(a2==temp){
                ll1.pop_back();
                return f(ll,ll1,ll2);
              }
              return false;
           }
           else{
              if(ll.front()>=ll2.front()){
               while(ll.front()>ll2.front()){
                  ll1.push_front(ll2.front());
                  ll2.pop_front();
               }
               ll.pop_front();
               ll2.pop_front();
               return f(ll,ll1,ll2);
              }
              else{
                 int temp=ll.front();
                 ll.pop_front();
                 int a1=ll1.front();
                 if(a1==temp){
                  ll1.pop_front();
                  return f(ll,ll1,ll2);
                 }
                 int a2=ll1.back();
                 if(a2==temp){
                  ll1.pop_back();
                  return f(ll,ll1,ll2);
                 }
                 return false;  
              }
           }
        }
    }