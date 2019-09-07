import load_mnist_data
import numpy as np
import matplotlib.pyplot as plt
import gzip
import random

def sigmoid (x):
    return 1/(1 + np.exp(-x))

    
def derivatives_sigmoid(x):
    return x * (1 - x)


def visualize(image):
        img=np.squeeze(image,axis=0)
        print(img)
        plt.imshow(img)
        plt.show()


def givevalues(y,n):
    a=np.zeros((len(y),n))
    for i in range(0,len(y)):
        k=y[i]
        for j in range(0,n):
            if k==j:
                a[i][j]=1
        
    return a

def realoutput(y):
    #print(y)
    a=list()
    for i in range(0,len(y)):
        a.append(np.argmax(y[i]))
    return a    
def accuracy_1(y,y1):
    sum1=0
    for i in range(0,len(y1)):
        if(y[i]==y1[i]):
            sum1+=1
    return sum1/len(y1)

def initial_weights_and_bias(layers):
    w=list()
    b=list()
    for i in range(0,len(layers)-1):
        wh=np.random.randn(layers[i],layers[i+1])
        bh=np.random.randn(1,layers[i+1])
        w.append(wh)
        b.append(bh)
    return w,b


def unison_shuffled_copies(a, b):
    assert len(a) == len(b)
    p = np.random.permutation(len(a))
    return a[p], b[p]

def forward(X,w,b):
    values=list()
    lw=len(w)
    start=sigmoid(np.dot(X/255,w[0])+b[0])
    values.append(start)
    t=start
    for i in range(1,lw):
        temp=np.dot(t,w[i])
        t=sigmoid(temp+b[i])
        values.append(t)
    return values


def Error_calculation(w,values,y1):
    error=list()
    lw=len(w)
    for i in range(0,lw):
        error.append(values[i]) 
    e1=y1-values[len(values)-1]
    temp1=derivatives_sigmoid(values[len(values)-1])
    first=(e1)*temp1
    error[lw-1]=first
    for i in range(1,lw):
        error[lw-1-i]=np.dot(error[lw-i],w[lw-i].T)*derivatives_sigmoid(values[lw-1-i])

    return error

def update_weights(X,w,b,error,values,lr):
    lw=len(w)
    w[0]+=np.dot(X.T/255,error[0])*lr
    b[0]+=np.sum(error[0],axis=0,keepdims=True)*lr
    for i in range(1,lw):
        w[i]+=np.dot(values[i-1].T,error[i])*lr
        b[i]+=np.sum(error[i],axis=0,keepdims=True)*lr
    return w,b

def accuracy(X1,Y1,w,b):
    lw=len(w)
    accuracy1=list()
    start=sigmoid(np.dot(X1,w[0])+b[0])
    accuracy1.append(start)
        
    for i in range(1,lw):
        temp=np.dot(accuracy1[i-1],w[i])
        accuracy1.append(sigmoid(temp+b[i]))
    temp=realoutput(accuracy1[lw-1])        
    print(accuracy_1(Y1,temp))
