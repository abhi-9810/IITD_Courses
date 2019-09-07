import csv
import numpy as np
import matplotlib.pyplot as plt
import gzip
import random
import neural

def readfile(data):
    with open(data,"r") as csvfile:
        item  = list(csv.reader(csvfile))
        for i in range(len(item)):
            item[i] = [int(x) for x in item[i]]     
    return np.array(item)

if __name__ == '__main__':
    mnist_data_folder = 'emnist/'

    #Name of training and testing files in gunzip format

    train_images = readfile("emnist/emnist-letters-train.csv")
    train_labels = train_images[:,0]
    train_images= train_images[:,1:]
    test_images = readfile("emnist/emnist-letters-test.csv")
    test_labels = test_images[:,0]
    test_images= test_images[:,1:]
    
    #visualize(np.expand_dims(X[0].reshape(28,28),axis=0))
    epoch=150 #Setting training iterations
    lr=0.09 #Setting learning rate
    layers=[784,45,30,26]
    w,b=neural.initial_weights_and_bias(layers)
    lw=len(w)
    leng=10
    X1=train_images
    Y1=train_labels
    for i1 in range(epoch):
        print(i1)
        X1,Y1=neural.unison_shuffled_copies(X1, Y1)
        for l1 in range(0,len(X1)//leng):
            X=X1[(leng*l1):(leng*(l1+1))]
            y=Y1[(leng*l1):(leng*(l1+1))]
            y1=neural.givevalues(y,26)
            #Forward Propogation
            values=neural.forward(X,w,b)                   
            #Backpropagation
            error=neural.Error_calculation(w,values,y1)
            w,b=neural.update_weights(X,w,b,error,values,lr)
        neural.accuracy(X1,Y1,w,b)    
    #Accuracy           
    neural.accuracy(test_images,test_labels,w,b)     

