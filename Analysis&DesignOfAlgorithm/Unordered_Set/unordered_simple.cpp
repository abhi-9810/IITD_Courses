//unordered Set i,e. There is no ordering between elements of the set, we can't compare any two of them.
#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;
  //function to check if a element is present in a set or not.    
  bool contains(vector<string> a, string s){
     for(int i=0;i<a.size();i++)
       if(a[i]==s)
         return true;
     return false; 
  }
//checking if two sets are equal
bool is_equal(vector<string> a, vector<string> b){ 
     if(a.size()==b.size()){ //if size of a is equal to b
        for(int i=0;i<a.size();i++)
           if(!contains(b,a[i]))
              return false;// element in a is not in b return false.
         return true;
     }
     else//else return false
          return false; 
  }
  //union of the two sets.
  vector<string> union_fun(vector<string> a, vector<string> b){
     vector<string> c;
    //copying all elements of a to c  
     for(int i=0;i<a.size();i++)
       c.push_back(a[i]);
    //copying all elements of b to c if not already present in c.  
     for(int i=0;i<b.size();i++)
       if(!contains(c,b[i]))
         c.push_back(b[i]);
     //returning c
     return c; 
  }
  //intersections of the two sets.
 vector<string> intersection_fun(vector<string> a, vector<string> b){
     vector<string> c;
    //copying all elements to c which are present in both a and b.
    if(a.size()>b.size()){
     for(int i=0;i<b.size();i++)
       if(contains(a,b[i]))
         c.push_back(b[i]);
     }
    else{
      for(int i=0;i<a.size();i++)
       if(contains(b,a[i]))
         c.push_back(a[i]);
      }  
     //returning c
     return c; 
  }
 
 //returning A\B for the sets.
vector<string> minus_fun(vector<string> a, vector<string> b){
     vector<string> c;
    //copying all elements to c which are present in both a and b.
      for(int i=0;i<a.size();i++)
       if(!contains(b,a[i]))
         c.push_back(a[i]);  
     //returning c
     return c; 
  }
int main() {
    //For simplicity we can assume them to be strings.
    vector<string> a;
    vector<string> b;
     //Storing Elements in Set A
     a.push_back("abhi");
     a.push_back("abhi1");
     a.push_back("bari5");
     a.push_back("abhi3");
     a.push_back("bari4");
     //Storing Elements in Set B
     b.push_back("abhi3");
     b.push_back("abhi5");
     b.push_back("bari3");
     b.push_back("abhi1");
     b.push_back("abhi9");
     b.push_back("bari4");
     b.push_back("bari");
     vector<string> c=union_fun(a,b);//union of the Two Sets
     vector<string> d=intersection_fun(a,b);//Intersection of the two sets.
     vector<string> e=minus_fun(a,b);//A\B for the two sets.
     
    //Printing the first set.
    cout<<"The size of set A is "<<a.size()<<endl;
    for(int i=0;i<a.size();i++)
       cout<<a[i]<<" ";
    cout<<endl<<endl;
    
    //Printing the Second set.
    cout<<"The size of set B is "<<b.size()<<endl;
    for(int i=0;i<b.size();i++)
       cout<<b[i]<<" ";
    cout<<endl<<endl;
    
    //Union of the two sets.
    cout<<"The size of Union of the sets is "<<c.size()<<endl;
    for(int i=0;i<c.size();i++)
       cout<<c[i]<<" ";
    cout<<endl<<endl;
  
    //Intersection of the two sets.
    cout<<"The size of Intersections of the two sets is "<<d.size()<<endl;
    for(int i=0;i<d.size();i++)
       cout<<d[i]<<" ";
    cout<<endl<<endl;
    
    //A\B for the two sets.
    cout<<"The size of A minus B  is "<<e.size()<<endl;
    for(int i=0;i<e.size();i++)
       cout<<e[i]<<" ";
    cout<<endl<<endl;
  
    return 0;
}
