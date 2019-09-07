import csv
import numpy as np
import matplotlib.pyplot as plt
import scipy
import scipy.spatial
import random
import math
#It is the function for the loading of the csv file into a numpy array 
def readfile(data,k):
    with open(data,"r") as csvfile:
        x = [[] for i in range(k)]
        item  = list(csv.reader(csvfile))
        for i in range(len(item)):
            item[i] = [int(x) for x in item[i]]
            k1=item[i][len(item[i])-1] 
            del item[i][-1]
            x[k1].append(item[i])        
    return x,len(item)

if __name__ == "__main__":
  x,m = readfile('madelon_data.csv',2)
  x1=list()
  for i in range(0,2):
   temp=scipy.spatial.KDTree(x[i])
   x1.append(temp)
  a=len(x[0][0])
  w = [0] * a
  w1= [0] * a
  k=2
  n=66
  alpha=0.000000005
  thres=1/math.sqrt(alpha*m)
  for i in range(0,n):
    class1=random.randint(0, k-1)
    sample=random.randint(0, len(x[class1])-1)
    temp=x[class1][sample]
    #print(temp)
    dist2=math.inf
    dist1=math.inf
    index1=0
    index2=0
    class2=0
    for j in range(0,k):
      if(j==class1):
        dist1,d1=x1[class1].query(temp, k=2, eps=0, p=2, distance_upper_bound=math.inf)
        index1=d1[1]
      else:
        dist21,d2=x1[j].query(temp, k=1, eps=0, p=2, distance_upper_bound=math.inf)
        if dist2<dist21:
          dist2=dist21
          index2=d2[0]
          class2=j
    for j in range(0,a):
      point=x[class1][sample][j]
      w[j]+=math.pow((x[class2][index2][j]-point),2)-math.pow((x[class1][index1][j]-point),2)
  for j in range(0,a):
    w[j]/=n
    if w[j]>=thres:
        w1[j]=1
        #print(w[j])
print(w1)
#print(w)
#print(thres)





















        
