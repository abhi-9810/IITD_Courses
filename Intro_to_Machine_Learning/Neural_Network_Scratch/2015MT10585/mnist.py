import load_mnist_data
import numpy as np
import matplotlib.pyplot as plt
import gzip
import random
import neural

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
    epoch=75 #Setting training iterations
    lr=0.005 #Setting learning rate
    layers=[784,45,30,10]
    w,b=neural.initial_weights_and_bias(layers)
    lw=len(w)
    leng=10
    X1=train_images
    Y1=train_labels
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
    neural.accuracy(test_images,test_labels,w,b)     
