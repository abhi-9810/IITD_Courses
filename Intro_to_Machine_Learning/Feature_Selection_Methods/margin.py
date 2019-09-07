from sklearn import svm
import numpy as np
import matplotlib.pyplot as plt
X = [[0, 0],[1, 1],[1,0],[0,2],[2,2],[6,10],[5,6],[3,7],[2,9],[1,10]]
Y = [0,0,0,0,0,1,1,1,1,1]
x=list()
y=list()
clf = svm.SVC(kernel='linear', C=100)
for k in range(0,1000):
    X1=list()
    for i in range(0,len(X)):
        X1.append([X[i][0],k*0.0125*X[i][1]])
    clf.fit(X1, Y)
    margin = 1 / np.sqrt(np.sum(clf.coef_ ** 2))
    x.append(k*0.0125)
    y.append(margin)
    if k==0:
        continue
   # print(margin)
plt.ylabel('Margin')
plt.xlabel('K')
plt.plot(x,y)
plt.show()
