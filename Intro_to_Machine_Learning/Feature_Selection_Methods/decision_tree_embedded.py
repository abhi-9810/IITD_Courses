import csv
import numpy as np
import pandas as pd
from sklearn.metrics import confusion_matrix
from sklearn.tree import DecisionTreeClassifier 

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
# Function to perform training with giniIndex.
def decision_tree(X_train, y_train):
 
    # Creating the classifier object
    clf_gini = DecisionTreeClassifier(criterion = "gini",
            random_state = 100,max_depth=3, min_samples_leaf=5)
     
    # Performing training
    clf_gini.fit(X_train, y_train)
    features = clf_gini.n_features_
    tree=clf_gini.tree_
    print(tree)
    n_nodes = clf_gini.tree_.node_count
    children_left = clf_gini.tree_.children_left
    children_right = clf_gini.tree_.children_right 
    feature = clf_gini.tree_.feature
    threshold = clf_gini.tree_.threshold

    temp=set()
    node_depth = np.zeros(shape=n_nodes, dtype=np.int64)
    is_leaves = np.zeros(shape=n_nodes, dtype=bool)
    stack = [(0, -1)]  # seed is the root node id and its parent depth
    while len(stack) > 0:
        node_id, parent_depth = stack.pop()
        node_depth[node_id] = parent_depth + 1

    # If we have a test node
        if (children_left[node_id] != children_right[node_id]):
            stack.append((children_left[node_id], parent_depth + 1))
            stack.append((children_right[node_id], parent_depth + 1))
        else:
            is_leaves[node_id] = True
    for i in range(n_nodes):
        if is_leaves[i]:
            continue
        f=feature[i]
        if f<0:
            f+=features
        temp.add(f)    
        
    importances = clf_gini.feature_importances_
    #print(importances)
    return clf_gini,temp
     
if __name__ == "__main__":
  x,y = readfile('madelon_data.csv')
  c,temp=decision_tree(x,y)
  print(temp)
  
