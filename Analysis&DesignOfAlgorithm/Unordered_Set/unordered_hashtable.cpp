//unordered Set using a hashtable.
#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;
  //function to map one element to an integer in (0,1000);
  int hash1(string s){
     int sum=7;
     for(int i=0; i<s.length(); ++i){
        sum*=31; 
        sum+=(int)s.at(i);
        sum%=1000; 
     }
     return sum; 
  } 
  //function to check if a element is present in a set or not.    
  bool contains(vector<string> a[], string s){
     int k=hash1(s); 
     for(int i=0;i<a[k].size();i++)
       if(a[k][i]==s)
         return true;
     return false; 
  }
  //union of the two sets.
  vector<string> union_fun(vector<string> a, vector<string> b){
     vector<string> c;
     vector<string> c1[1000];//an arrays of vector so that searching time reduced to very low. 
    //copying all elements of a to c  
     for(int i=0;i<a.size();i++){
       c.push_back(a[i]);
       c1[hash1(a[i])].push_back(a[i]);  
     }
    //copying all elements of b to c if not already present in c.  
     for(int i=0;i<b.size();i++)
       if(!contains(c1,b[i]))
         c.push_back(b[i]);
     //returning c
     return c; 
  }
  //intersections of the two sets.
 vector<string> intersection_fun(vector<string> a, vector<string> b){
     vector<string> c;
     vector<string>a1[1000];//hashtable for a1
     vector<string>b1[1000];//hashtable for b1
     //mapping all elements of a to hashtable
     for(int i=0;i<a.size();i++)
        a1[hash1(a[i])].push_back(a[i]);
      //mapping all elements of b to hashtable
     for(int i=0;i<b.size();i++)
        b1[hash1(b[i])].push_back(b[i]);
    //copying all elements to c which are present in both a and b.
    if(a.size()>b.size()){
     for(int i=0;i<b.size();i++)
       if(contains(a1,b[i]))
         c.push_back(b[i]);
     }
    else{
      for(int i=0;i<a.size();i++)
       if(contains(b1,a[i]))
         c.push_back(a[i]);
      }  
     //returning c
     return c; 
  }
 
 //returning A\B for the sets.
vector<string> minus_fun(vector<string> a, vector<string> b){
     vector<string> c;
     vector<string>b1[1000];
      //mapping all elements of b to hashtable
     for(int i=0;i<b.size();i++)
        b1[hash1(b[i])].push_back(b[i]);
    //copying all elements to c which are present in both a and b.
      for(int i=0;i<a.size();i++)
       if(!contains(b1,a[i]))
         c.push_back(a[i]);  
     //returning c
     return c; 
  }
 //checking if two sets are equal
bool is_equal(vector<string> a, vector<string> b){
     vector<string> c;
     vector<string>a1[1000];//hashtable for a1
     vector<string>b1[1000];//hashtable for b1
     //mapping all elements of a to hashtable
     for(int i=0;i<a.size();i++)
        a1[hash1(a[i])].push_back(a[i]);
      //mapping all elements of b to hashtable
     for(int i=0;i<b.size();i++)
        b1[hash1(b[i])].push_back(b[i]);
     if(a.size()==b.size()){
        for(int i=0;i<a.size();i++)
           if(!contains(b1,a[i]))
              return false;
         return true;
     }
     else
          return false; 
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
