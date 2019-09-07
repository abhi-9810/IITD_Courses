import csv
import numpy as np
from sklearn.neighbors import KNeighborsClassifier
neigh = KNeighborsClassifier(n_neighbors=3)
#It is the function for the loading of the csv file into a numpy array 
def readfile(data):
    with open(data,"r") as csvfile:
        item  = list(csv.reader(csvfile))
        x = [[] for i in range(len(item))]
        for i in range(len(item)):
            item[i] = [int(x) for x in item[i]]
            k1=item[i][len(item[i])-1]
            del item[i][-1]
            x[i].append(k1)
    return item,x
def f(x,y,x1,y1,s,k):
    u = [[] for i in range(len(x))]
    v=y
    u1= [[] for i in range(len(x1))]
    v1=y1
    for i in range(len(x)):
        u[i].append(x[i][k])
        for j in s:
            u[i].append(x[i][j])
            
    for i in range(len(x1)):
        u1[i].append(x1[i][k])
        for j in s:
            u1[i].append(x1[i][j])
    neigh.fit(u, np.array(v).ravel())
    acc=0
    for i in range(len(u1)):
       pre=neigh.predict([u1[i]])
       if pre==v1[i]:
           acc+=1
    return acc
if __name__ == "__main__":
  x,y = readfile('data.csv')
  x1,y1 = readfile('data.csv') 
  temp=list()
  a=len(x[0])
  w = [0] * a
  d=7
  s=set()
  for i in range(0,d):
      accr=-1
      index=-1
      for k in range(0,a):
          if w[k]==0:
              accr1=f(x,y,x1,y1,s,k)
              if accr1>accr:
                  index=k
                  accr=accr1
      w[index]=1
      s.add(index)            
  print(s)




















        
