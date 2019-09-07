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
  clf = ExtraTreesClassifier()
  clf = clf.fit(X, y)
  #print(clf.feature_importances_)
  model = SelectFromModel(clf, prefit=True)
  X_new = model.transform(X)
  print(X_new.shape)
  svc = svm.SVC(kernel='linear', C=1000).fit(X, y)
  x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
  y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
  h = (x_max / x_min)/100
  xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                       np.arange(y_min, y_max, h))
  plt.subplot(1, 1, 1)
  Z = svc.predict(np.c_[xx.ravel(), yy.ravel()])
  Z = Z.reshape(xx.shape)
  plt.contourf(xx, yy, Z, cmap=plt.cm.Paired, alpha=0.8)
  plt.scatter(X[:, 0], X[:, 1], c=y, cmap=plt.cm.Paired)
  plt.xlabel('Sepal length')
  plt.ylabel('Sepal width')
  plt.xlim(xx.min(), xx.max())
  plt.title('SVC with linear kernel')
  plt.show()
  #neigh.fit(X, y)
  #y1=neigh.predict(X)
  #print(accuracy_score(y, y1))
  #neigh.fit(X_new, y)
  #y1=neigh.predict(X_new)
  #new1=accuracy_score(y, y1)
  #print(new1)
  
