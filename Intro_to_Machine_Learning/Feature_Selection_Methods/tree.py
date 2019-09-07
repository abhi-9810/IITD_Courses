import csv
import numpy as np
import pandas as pd 
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.feature_selection import SelectFromModel
def readfile(data):
    with open(data,"r") as csvfile:
        item  = list(csv.reader(csvfile))
        y=list()
        for i in range(len(item)):
            item[i] = [int(x) for x in item[i]]
            k1=item[i][len(item[i])-1] 
            del item[i][-1]
            y.append(k1)        
    return item,y 

if __name__ == "__main__":
  X,y = readfile('madelon_data.csv')
  clf = ExtraTreesClassifier()
  clf = clf.fit(X, y)
  #print(clf.feature_importances_)
  model = SelectFromModel(clf, prefit=True)
  X_new = model.transform(X)
  print(X_new.shape)
  
