import csv
import numpy as np
import matplotlib.pyplot as plt
 
#It is the function for the loading of the csv file into a numpy array 
def readfile(data):
    with open(data,"r") as csvfile:
        item  = list(csv.reader(csvfile))
        for i in range(len(item)):
            item[i] = [float(x) for x in item[i]]     
    return np.array(item)
 
# as logistic regression needs the variable to be normalized  
def normalize(X):
    min1 = np.min(X, axis = 0)
    max1 = np.max(X, axis = 0)
    norm_X = 1 - ((max1 - X)/(max1-min1))
    return norm_X
 
# the sigmoid function to map the datasets into an interval of 0-1 
def sigmodal(beta, X):
    return 1.0/(1 + np.exp(-np.dot(X, beta.T)))
 
# gradient of the objective function 
def gradient(beta, X, y):
    return np.dot((sigmodal(beta, X) - y.reshape(X.shape[0], -1)).T,X)
      
#the cost function which we want to minimize
def obj_func(beta, X, y):
    temp = sigmodal(beta, X)
    y = np.squeeze(y)
    return np.mean(-y * np.log(temp) - (1 - y) * np.log(1 - temp))
 
#applying gradient descent to minimize the objective function 
def grad_desc(X, y, beta, learn=.005):
    c = obj_func(beta, X, y)
    change_cost = 1
    num_iter = 1 #it is the number of iteration
     
    while(change_cost > 0.01 and num_iter<1000):#we need to set a limit to the num_iter 
        oc = c
        beta = beta - (learn * gradient(beta, X, y))#applying gradient descdent
        c = obj_func(beta, X, y)
        change_cost = abs(oc - c)
        num_iter += 1
     
    return beta, num_iter 
 
 
def pred_values(beta, X):
    w, h = 10,len(X) 
    temp = [[0 for x in range(w)] for y in range(h)]
    temp=sigmodal(beta[0], X)
    for i in range(1,10):
        temp1=sigmodal(beta[i], X)
        temp=np.concatenate((temp, temp1))
    v=temp.argmax(axis=0)#findind the most probable class and assigning that value to the class
    return v

 

def plot_reg(X, y, beta):
    x_0 = X[np.where(y == 0.0)]
    x_1 = X[np.where(y == 1.0)]
     
    # plotting points with diff color for diff label
    plt.scatter([x_0[:, 1]], [x_0[:, 2]], c='b', label='y = 0')
    plt.scatter([x_1[:, 1]], [x_1[:, 2]], c='r', label='y = 1')
     
    # plotting decision boundary
    x1 = np.arange(0, 1, 0.1)
    x2 = -(beta[0,0] + beta[0,1]*x1)/beta[0,2]
    plt.plot(x1, x2, c='k', label='reg line')
 
    plt.xlabel('x1')
    plt.ylabel('x2')
    plt.legend()
    plt.show()
    
 
     
if __name__ == "__main__":
    # load the dataset
    item = readfile('train_data.csv')
     
    # normalizing feature matrix
    X = normalize(item[:, :-1])
     
    # stacking columns wth all ones in feature matrix
    X = np.hstack((np.matrix(np.ones(X.shape[0])).T, X))
    data1 = readfile('train_labels.csv')
    # response vector
    y = data1[:, -1]
     
    # initial beta values
    k=10
    n=15
    all_theta = np.zeros((k, n + 1))
    for i in range(0,10):
        tmp_y = np.array(y == i, dtype = int)
        beta = np.matrix(np.zeros(X.shape[1]))
        beta, num_iter = grad_desc(X, tmp_y, beta)
        #print(beta)
        plot_reg(X, tmp_y, beta)
        print("No. of iterations:", num_iter)
        all_theta[i] = beta
        
    # estimated beta values and number of iterations
    print("Estimated regression coefficients:", beta)
    print("No. of iterations:", num_iter)
    
    # predicted labels
    item1 = readfile('test_data.csv')
     
    # normalizing feature matrix
    X1 = normalize(item1[:, :-1])
     
    # stacking columns wth all ones in feature matrix
    X1 = np.hstack((np.matrix(np.ones(X1.shape[0])).T, X1))
    data11 = readfile('test_labels.csv')
    # response vector
    y1 = data11[:, -1]
    y_pred = pred_values(all_theta, X1)
    print(y_pred) 
    # number of correctly predicted labels
    print("Correctly predicted labels:", np.sum(y1 == y_pred))
