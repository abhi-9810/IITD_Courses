import csv
from random import randint
import numpy as np
import pandas as pd
from sklearn import svm
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
  X1 = readfile('data/test_data.csv')
  Y1=   readfile('data/test_labels.csv')
  clf = ExtraTreesClassifier()
  clf = clf.fit(X, y)
  #print(clf.feature_importances_)
  model = SelectFromModel(clf, prefit=True)
  X_new = model.transform(X)
  print(X_new.shape)
  neigh = svm.SVC(kernel='linear', C=10000)
  neigh.fit(X, y)
  y1=neigh.predict(X)
  
  print(accuracy_score(y1, y1))
  neigh.fit(X_new, y)
  X_new = model.transform(X1)
  y1=neigh.predict(X_new)
  #y1=neigh.predict(X_new)
  new1=accuracy_score(Y1, y1)
  print(new1)
  
