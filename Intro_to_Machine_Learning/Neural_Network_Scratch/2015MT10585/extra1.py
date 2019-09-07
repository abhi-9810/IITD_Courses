from __future__ import print_function
import load_mnist_data
import numpy as np
import matplotlib.pyplot as plt
import gzip
import random
import neural

def recursion(i,j,w,b,k,v):
    if(i==0):
        return neural.sigmoid(w[0][k][j]+b[0][0][j])
    else:
        sum1=0
        for i1 in range(0,len(v[i-1][0])):
            sum1+=v[i-1][0][i1][k]*w[i][i1][j]+b[i-1][0][i1]
        return neural.sigmoid(sum1)    
            
def recursion1(i,j,w,b,v):
    l=list()
    for k in range(0,784):
        l.append(recursion(i,j,w,b,k,v))
    return l    
def visualize(w,b):
    v=list()
    lw=len(w)
    for i in range(0,lw):
        c=np.zeros((b[i].shape[0],b[i].shape[1],784))
        v.append(c)
    for i in range(0,lw):
        for j in range(0,len(v[i][0])):
            temp=recursion1(i,j,w,b,v)
            #print(temp)
            #print("\n\n\n")
            v[i][0][j]=temp
    return v

def visualize_cat(image):
        img=np.squeeze(image,axis=0)
        #print(img)
        plt.imshow(img)
        plt.show()

if __name__ == '__main__':
    
    mnist_data_folder = 'mnist/original/'

    #Name of training and testing files in gunzip format

    training_set_file_name =             'train-images-idx3-ubyte.gz'
    training_labels_file_name =          'train-labels-idx1-ubyte.gz'
    testing_set_file_name =              't10k-images-idx3-ubyte.gz'
    testing_labels_file_name =           't10k-labels-idx1-ubyte.gz'

    train_images_obj = load_mnist_data.load_mnist(mnist_data_folder+training_set_file_name, 'data', file_handler=gzip.GzipFile, display_sample=0)
    train_labels_obj = load_mnist_data.load_mnist(mnist_data_folder+training_labels_file_name, 'label', file_handler=gzip.GzipFile)
    test_images_obj = load_mnist_data.load_mnist(mnist_data_folder+testing_set_file_name, 'data', file_handler=gzip.GzipFile, display_sample=0)
    test_labels_obj = load_mnist_data.load_mnist(mnist_data_folder+testing_labels_file_name, 'label', file_handler=gzip.GzipFile)
    train_images = train_images_obj.load()
    train_labels = train_labels_obj.load()
    test_images = test_images_obj.load()
    test_labels = test_labels_obj.load()
    # Many learning algorithms accepts images in the vector format. Hence converting images in the vector format.

    train_images = train_images.reshape(train_images.shape[0],np.prod(train_images.shape[1:]))
    train_labels = train_labels.reshape(train_labels.shape[0],1)
    test_images = test_images.reshape(test_images.shape[0], np.prod(test_images.shape[1:]))
    test_labels = test_labels.reshape(test_labels.shape[0],1)
  
    #visualize(np.expand_dims(X[0].reshape(28,28),axis=0))
    epoch=500 #Setting training iterations
    lr=0.01 #Setting learning rate
    layers=[784,45,30,10]
    w,b=neural.initial_weights_and_bias(layers)
    lw=len(w)
    leng=10
    X1=train_images[:100]
    Y1=train_labels[:100]
    #X1=[[1,1,0],[1,0,1],[0,1,0]]
    #Y1[[0],[1],[0]]
    for i1 in range(epoch):
        X1,Y1=neural.unison_shuffled_copies(X1, Y1)
        for l1 in range(0,len(X1)//leng):
            X=X1[(leng*l1):(leng*(l1+1))]
            y=Y1[(leng*l1):(leng*(l1+1))]
            y1=neural.givevalues(y,10)
            #Forward Propogation
            values=neural.forward(X,w,b)                   
            #Backpropagation
            error=neural.Error_calculation(w,values,y1)
            w,b=neural.update_weights(X,w,b,error,values,lr)
        #neural.accuracy(X1,Y1,w,b)    
    #Accuracy           
    neural.accuracy(X1,Y1,w,b)
    v=visualize(w,b)
    
    sum1=0
    
    temp1=np.zeros((1,784))
    for i1 in range(0,784):
        Xm=np.zeros((1,784))
        Xm[0][i1]=1
        if i1==7831:
            Xm=X1[0]
            visualize_cat(np.expand_dims(Xm.reshape(28,28),axis=0))
        accuracy1=list()
        start=neural.sigmoid(np.dot(Xm,w[0])+b[0])
        accuracy1.append(start)
        for i in range(1,lw):
            temp=np.dot(accuracy1[i-1],w[i])
            k1=neural.sigmoid(temp+b[i])
            accuracy1.append(k1)
            #v[][0][][i1]=k1    
        #print(accuracy1[-1][0][0])
        if i1==7831:
            print(accuracy1[-1][0])
            print(Xm)
        temp1[0][i1]=accuracy1[1][0][10]
    for i in range(0,784):
        sum1+=v[2][0][0][i]
    #print(temp1)
    ind1=np.argmax(temp[0])
    ind=temp1[0].argsort()[-20:][::-1]
    #ind = np.argpartition(temp1[0], -30)[-30:]
    temp11=np.zeros((1,784))
    for i in range(0,len(ind)):
        temp11[0][ind[i]]=255*temp1[0][i]/temp[0][ind1]
    visualize_cat(np.expand_dims(temp11.reshape(28,28),axis=0))
    for i in range(0,10):
        #ind =np.argwhere(v[2][0][i] > 0)
        ind = np.argpartition(v[2][0][i], -100)[-100:]
        #print(v[2][0][i][ind])
        temp1=np.zeros((784,1))
        for i in range(0,len(ind)):
            temp1[ind[i]][0]=255
        #visualize_cat(np.expand_dims(temp1.reshape(28,28),axis=0))
        
