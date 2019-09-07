#include <iostream>
#include <algorithm>
#include <fstream>
#include <vector>
#include <string>
#include <math.h>
#include <queue>
using namespace std;

int d_split = 0;
int dimension = 0;
int k=0;
struct Node{
    vector<double> data;
    double dist;
    double* mbr_left;
    double* mbr_right;
    int level;
    Node* right;
    Node* left;
};

struct Node* newNode(vector<double> d,double* left,double* right, int level){
    struct Node* node = new Node;
    node ->mbr_left=new double[dimension];
    node ->mbr_right=new double[dimension];
    node -> data = d;
    node -> dist = -1;
    for(int i=0;i<dimension;i++){
      node -> mbr_left[i] = left[i];
      node -> mbr_right[i] = right[i];    
    }
    node -> level=level;
    node -> left = NULL;
    node -> right = NULL;
    return node;
}

bool compare_function(const vector<double>& a, const vector<double>& b){
    for(int i=0;i<dimension;i++){
        if(a[(d_split+i)%dimension]<b[(d_split+i)%dimension])
           return 1;
        if(a[(d_split+i)%dimension]>b[(d_split+i)%dimension])
           return 0;
    }
    return 1;
}


vector< vector<double> > Sort(vector< vector<double> > vec,int start,int last,int dimension_split){
    int i,j;
    d_split=dimension_split;
    sort(vec.begin()+start,vec.begin()+last+1,compare_function);
    return vec;
}

vector<double> insert_vectors(double arr[]){
    int i,j;
    vector<double> ret_vec;
    for(i=0;i<dimension;i++){
        ret_vec.push_back(arr[i]);
    }
    return ret_vec;
}
bool median(vector<double> v1,vector<double> v2){
     for(int i=0;i<v1.size();i++){
         if(v1[i]!=v2[i])
           return false;
     }
     return true;
}
bool compare1(vector<double> v1,vector<double> v2,int d){
    for(int i=0;i<dimension;i++){
        if(v1[(d+i)%dimension]<v2[(d+i)%dimension])
          return true;
        if(v1[(d+i)%dimension]>v2[(d+i)%dimension])
          return false;
    }
    return true;
}
Node* Create_KDTree(int dimension_split,double min[],double max[],int level,vector<vector<double> >temp[]){
    if(temp[0].size()==0)
      return NULL;
    vector<vector<double> >temp1[dimension];
    vector<vector<double> >temp2[dimension];
    int mid1=(temp[dimension_split].size()-1)/2;
    double split=temp[dimension_split][mid1][dimension_split];
    vector<double>split1=temp[dimension_split][mid1];
    for(int i=0;i<dimension;i++){
        for(int i1=0;i1<temp[i].size();i1++){
            if(median(temp[dimension_split][mid1],temp[i][i1])){
                continue;
             }
            if(compare1(temp[i][i1],split1,dimension_split)){
              temp1[i].push_back(temp[i][i1]);
            }
            else{
                temp2[i].push_back(temp[i][i1]);
            }
        }
    }

    vector<double> middle = temp[dimension_split][mid1];
    Node* node1 = newNode(middle,min,max,level);
    double max1=max[dimension_split];
    double min11[dimension];
    double max11[dimension];
    for(int i=0;i<dimension;i++){
        min11[i]=min[i];
        max11[i]=max[i];
    }
    max[dimension_split]=middle[dimension_split];
    node1 -> left = Create_KDTree((dimension_split+1)%dimension,min,max,level+1,temp1);
    //max[dimension_split]=max1;
    //min[dimension_split]=middle[dimension_split];
    min11[dimension_split]=middle[dimension_split];
    
    node1 -> right = Create_KDTree((dimension_split+1)%dimension,min11,max11,level+1,temp2);
    return node1;
 }

class myComparator
{
public:
    int operator() (const Node* p1, const Node* p2)
    {   
        int k=1;
        if(p1->dist > p2->dist)
          return 0;
        if(p1->dist < p2->dist)
         return 1;  
        if(p1->dist==p2->dist){
            for(int i=0;i<dimension;i++){
                if(p1->data[i]<p2->data[i]){
                    k=0;
                    break;
                }
            }
        }  
        return k;
    }
};
class myComparator1
{
public:
    int operator() (const Node* p1, const Node* p2)
    {
        int k=1;
        if(p1->dist < p2->dist)
          return 0;
        if(p1->dist > p2->dist)
          return 1;  
        if(p1->dist==p2->dist){
            for(int i=0;i<dimension;i++){
                if(p1->data[i]<p2->data[i]){
                    k=1;
                    break;
                }
            }
        }  
        return k;
    }
};
priority_queue <Node*, vector<Node *>,myComparator> max_heap;
priority_queue <Node*, vector<Node *>,myComparator1> min_heap;
priority_queue <Node*, vector<Node *>,myComparator> max_heap_sequential;

double distance1(double a[],double b[]){
    double sum=0;
    for(int i=0;i<dimension;i++){
        sum+=(a[i]-b[i])*(a[i]-b[i]);
    }
    return sqrt(sum);
}

void inorder_distance(double query[],struct Node* node){
    if(node==NULL){
        return;
    }
    inorder_distance(query,node->left);
    double point[dimension];
    for(int i=0;i<dimension;i++){
        point[i]=(node->data[i]);
    }
    double dist=distance1(query,point);
    node->dist=dist;
    if(max_heap_sequential.size()<k)
       max_heap_sequential.push(node);
    else {
       Node* temp = max_heap_sequential.top();
       if(temp->dist>node->dist){
           max_heap_sequential.pop();
           max_heap_sequential.push(node);
       }
    }
    inorder_distance(query,node->right);
}
bool lexico(struct Node* p1,struct Node* p2){
    for(int i=0;i<dimension;i++){
        if(p1->data[i]<p2->data[i]){
            return true;
        }
    }
    return false;
}

double mbr_dist(struct Node* node,double * query){
    double sum=0;
    if(node==NULL)
      return 500.00;
    for(int i=0;i<dimension;i++){
        double left=(node->mbr_left[i]);
        double right=(node->mbr_right[i]);
        double query1=query[i];
        double dist=0.0;
        if(query1<left)
          dist=left-query1;
        else if(query1>right)
               dist=query1-right;
        else
           dist=0;
        sum+=dist*dist;
    }
    return sqrt(sum);
}
void knn(struct Node * root,double query1[]){
    double point[dimension];
    for(int i=0;i<dimension;i++){
        point[i]=(root->data[i]);
    }
    root->dist=distance1(query1,point);
    min_heap.push(root);
    max_heap.push(root);
    int i1=0;
     
    while(!min_heap.empty()){
        struct Node* p = max_heap.top();
        struct Node* q = min_heap.top();
        for(int i=0;i<dimension;i++){
           point[i]=(q->data[i]);
        }
        q->dist=distance1(query1,point);
        double top_dist=p->dist;
        double left1=mbr_dist(q->left,query1);
        double right1=mbr_dist(q->right,query1);
        if(q->left!=NULL)
          q->left->dist=left1;
        if(q->right!=NULL)
           q->right->dist=right1;
        min_heap.pop();
        if(max_heap.size()<k){
            i1+=1; 
           if(i1!=1) 
            max_heap.push(q);
           if(q->left!=NULL)
            min_heap.push(q->left);
           if(q->right!=NULL)
            min_heap.push(q->right);
        }
        else{
            if(p->dist>q->dist){
                max_heap.pop();
                max_heap.push(q);
            }
            if(p->dist==q->dist){
               if(lexico(q,p)){ 
                max_heap.pop();
                max_heap.push(q);
               }
            }
            if(left1<top_dist){
                min_heap.push(q->left);
            }
            if(right1<top_dist){
                min_heap.push(q->right);
            }
        }
    }
}


int main(int argc, char* argv[]){
    int k1=0,n=0,i=0;
    vector< vector<double> > vec;
    char* dataset_file = argv[1];
    ifstream infile;
    infile.open(dataset_file);
    if(!infile){
        cerr<<"file not exist";
    }
    double a;
    infile>>a;
    k1 = a;
    infile>>a;
    n = a;
    dimension = k1;
    double new_arr[k1];
    int j =0;
    int p =1;
    vector<vector<double> >temp[dimension];
    long value = n * dimension;
    while(p<=value+1){
        infile>>a;
        if(i!=0 && i>k1-1){
            i = i%k1;
            vector<double> point_vec = insert_vectors(new_arr);
            vec.push_back(point_vec);
        }
        new_arr[i] = a;
        i++;
        p++;
    }
    for(int i=0;i<dimension;i++){
        temp[i]=Sort(vec,0,vec.size()-1,i);
    }
    infile.close();
    double min[dimension];
    double max[dimension];
    for(int i=0;i<dimension;i++)
       min[i]=0.0;
    for(int i=0;i<dimension;i++)
       max[i]=1.0;
    Node* root = Create_KDTree(0,min,max,0,temp);
    cout << 0 << endl;
    char* query_file = new char[100];
    int k_1;
    cin >> query_file >> k_1;
    
    infile.open(query_file);
    if(!infile){
        cerr<<"file not exist";
    }
    infile>>a;
    infile>>a;
    int q=0;
    q = a;
    i=0;
    k=k_1;
    ofstream myfile;
    myfile.open ("results.txt"); 
    while(i<q){
        double query[dimension];
        for(int j=0;j<dimension;j++){
           infile>>a;
           query[j] = a;
        }
        i++;
        int j=0;
        if(dimension>12){ 
          inorder_distance(query,root);
          double points1[k][dimension];
          int l11=0;
          while (max_heap_sequential.empty() == false){
            Node* p = max_heap_sequential.top();
            for(int i1=0;i1<dimension;i1++)
            points1[l11][i1]=p->data[i1];
            l11++;  
            max_heap_sequential.pop();
         }
        for(int i1=l11-1;i1>=0;i1--){
             for(int i2=0;i2<dimension;i2++){
                 myfile<<points1[i1][i2]<<" ";
           }
           myfile<<"\n";
        } 
      }
      else{
       knn(root,query);
       double temp11[dimension];
       j=0;
       double points[k][dimension];

       int l1=0;
       while (max_heap.empty() == false){
           Node* p = max_heap.top();
           for(int i1=0;i1<dimension;i1++)
             points[l1][i1]=p->data[i1];
           l1++;  
           max_heap.pop();
       }
       for(int i1=l1-1;i1>=0;i1--){
           for(int i2=0;i2<dimension;i2++){
               myfile<<points[i1][i2]<<" ";
           }
           myfile<<"\n";
       }
     }
    }

    
    infile.close(); 
    myfile.close();
    cout << 1 << endl;
}
