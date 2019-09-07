import csv
import numpy as np
import pandas as pd 
from sklearn.feature_selection import RFE
from sklearn.svm import SVR
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
  estimator = SVR(kernel="linear")
  selector = RFE(estimator, 5, step=1)
  selector = selector.fit(X, y)
  print(selector.support_ )
  print(selector.ranking_)
  
