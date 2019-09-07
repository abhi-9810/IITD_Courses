import csv
from random import randint
import numpy as np
import pandas as pd 
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.feature_selection import SelectFromModel
from sklearn.metrics import accuracy_score
from sklearn.neighbors import KNeighborsClassifier
import numpy as np
import matplotlib.pyplot as plt
def readfile(data):
    with open(data,"r") as csvfile:
        item  = list(csv.reader(csvfile))
        for i in range(len(item)):
            item[i] = [int(x) for x in item[i]]        
    return item

if __name__ == "__main__":
  X = readfile('data/train_data.csv')
  y = readfile('data/train_labels.csv')
  clf = ExtraTreesClassifier()
  clf = clf.fit(X, y)
  X1 = readfile('data/test_data.csv')
  Y1=   readfile('data/test_labels.csv')
  #print(clf.feature_importances_)
  model = SelectFromModel(clf, prefit=True)
  X_new = model.transform(X)
  print(X_new.shape)
  neigh = KNeighborsClassifier(n_neighbors=3)
  neigh.fit(X, y)
  y1=neigh.predict(X)
  y_full=neigh.predict(X1)
  print(y_full)
  print(accuracy_score(y, y1))
  neigh.fit(X_new, y)
  y1=neigh.predict(X_new)
  new1=accuracy_score(y, y1)
  print(new1)
  model = SelectFromModel(clf, prefit=True)
  X_new = model.transform(X1)
  y1=neigh.predict(X_new)
  print(y1)
  new1=accuracy_score(Y1, y1)
  print(new1)
  x=list()
  y11=list()
  '''
  for i1 in range(0,10):
      f=set()
      temp=randint(10,300)
      for i in range(0,temp):
          f.add(randint(0, 783))
      X1=list()
      for i in range(0,2000):
          X1.append([X[i][j] for j in f])   
      neigh.fit(X1, y)
      y1=neigh.predict(X1)
      acc=accuracy_score(y, y1)
      x.append(temp)
      y11.append(acc)
      print(acc)    
  plt.ylabel('Accuracy')
  plt.xlabel('No. of Random Features')
  x.append(X_new.shape[1])
  y11.append(new1)
  plt.plot(x, y11, 'ro')

  plt.show()
  '''
