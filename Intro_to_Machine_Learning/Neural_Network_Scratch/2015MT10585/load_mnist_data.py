###########_Description of dataset_###########
#
# Training Set : 60000 examples
# Test Set : 10000 examples
#
##############################################


#This is a helper script load in the numpy and
#visualize the loaded data
#Libraries needed
# 1. Numpy
# 2. matlplotlib
# 3. pickle
# 4. gzip

#Importing libraries
from __future__ import print_function
import numpy as np
#import matplotlib
#matplotlib.use('agg')
#import matplotlib.pyplot as plt
import pickle
import gzip                         #See docs here : https://docs.python.org/2/library/gzip.html

##############################
#                            #
# Class Information:         #
#                            #
# Class to load idx files    #
# Parent class : None        #
# Child class : load_mnist   #
#                            #
##############################

class load_idx:
    
    ###################################################################################################################
    #                                                                                                                 #
    # Class members:                                                                                                  #
    # get_magic_number() : This function returns the magic_number of the idx file, It accepts no value.               #
    #                                                                                                                 #
    # _extract_header()  : This function read all the header and returns the information about datatype and number    #
    #                    : of dimensions present in the idx file. This function is meant to be private to this class, #
    #                    : hence should not be called outside the class. It accepts no value.                         #
    #                                                                                                                 #
    # load_file()        : This function loads the data according to the information provided by the _extract_header  #
    #                    : function, and returns the shaped(According to the dimmension) numpy array.                 #
    #                                                                                                                 #
    ###################################################################################################################
    
    
    ###################################################################################################################
    #                                                                                                                 #
    # Description of Constructor:                                                                                     #
    #                                                                                                                 #
    # file_name     : Name of the file to be read, if this file having some special format, file_handler is also      #
    #               : needs to be specified. For example if the file being opened is in the gunzip format. It's       #
    #               : file handler gzip.GzipFile should be given as the file_handler.                                 #
    #                                                                                                                 #
    # fstream       : This accepts the file descriptor, and act on the previously opened file.                        #
    #                                                                                                                 #
    #       ##########################################################                                                #
    #       #                                                        #                                                #
    #       # file_name and fstream shouldn't be specified together. #                                                #
    #       #                                                        #                                                #
    #       ##########################################################                                                #
    #                                                                                                                 #
    # file_handler  : Method to open the file specified by file_name. it's default value is open function, which is   #
    #               : the core function of the python.                                                                #
    #                                                                                                                 #
    ###################################################################################################################
    
    def __init__(self, file_name=None, fstream=None, file_handler=open):
        self.file_name = file_name
        self.fstream = fstream
        self.file_handler = file_handler
        self.magic_number = 0
        self.header_dtype = np.dtype(np.uint32).newbyteorder('>')            # Defining the header datatype,
                                                                             # '>' specifies big-endian byteorder
                                                                             # so that conversion can be done correctly.
                                                                         
        if not (self.file_name is not None) ^ (self.fstream is not None):    # Condition to check if both input method
                                                                             # are not defined
            raise ValueError('Define either File Name or File Stream')
        elif self.file_name is not None:
            self.fstream = self.file_handler(self.file_name, 'rb')
    
    
    ###################################################################################################################
    #                                                                                                                 #
    # Description of get_magic_number():                                                                              #
    #                                                                                                                 #
    # This function reads the first 4 bytes and convert them into the specified datatype for header.                  #
    #                                                                                                                 #
    ###################################################################################################################
    
    def get_magic_number(self):
        self.magic_number = np.frombuffer(self.fstream.read(4), dtype=self.header_dtype)[0]
        return self.magic_number
    
    ###################################################################################################################
    #                                                                                                                 #
    # Description of _extract_header():                                                                               #
    #                                                                                                                 #
    # This function extracts the information from the magic_number and read the complete header accordingly.          #
    #                                                                                                                 #
    ###################################################################################################################
    
    def _extract_header(self):
        mask_dim = int('0x000000ff',16)                                  # Mask for dimensions. Since the information
                                                                         # about the dimensions is present in the last
                                                                         # (fourth) byte.
        
        mask_datatype = int('0x0000ff00',16)                             # Mask for datatype. Since the information
                                                                         # about the datataype is present in the second
                                                                         # last (third) byte
        
        no_of_dimensions = np.bitwise_and(self.magic_number, np.array(mask_dim, dtype=np.uint32))
                                                                         # Extracting the last byte i.e. Number of
                                                                         # dimenstions. And operation with
                                                                         # the created mask
        
        datatype_index = np.right_shift(np.bitwise_and(self.magic_number, np.array(mask_datatype, dtype=np.uint32)),8)
                                                                         # Extracting the second last byte i.e. datatype
                                                                         # index. And operation with Mask and then right
                                                                         # shift by 1 byte (8 bits).
                    
        # Defining the datatype based on the datatype information gathered from the header.
        if datatype_index == int('0x08',16):
            dt = np.dtype(np.uint8)
        elif datatype_index == int('0x09',16):
            dt = np.dtype(np.int8)
        elif datatype_index == int('0x0B',16):
            dt = np.dtype(np.int16)
        elif datatype_index == int('0x0C',16):
            dt = np.dtype(np.int32)
        elif datatype_index == int('0x0D',16):
            dt = np.dtype(np.float32)
        elif datatype_index == int('0x0E',16):
            dt = np.dtype(np.float64)
        
        dimensions = np.empty(no_of_dimensions, dtype=np.uint32)
        
        
        # Extracting the information about dimensions from the file.
        for i in range(no_of_dimensions):
            read_val = np.frombuffer(self.fstream.read(4),dtype=self.header_dtype)[0]
            dimensions[i] = read_val
        
        return dimensions, dt
    
    ###################################################################################################################
    #                                                                                                                 #
    # Description of load_file():                                                                                     #
    #                                                                                                                 #
    # This function loads the file in the numpy array and convert it into the specified format.                       #
    #                                                                                                                 #
    ###################################################################################################################
    
    def load_file(self):
        if self.magic_number == 0:
            self.get_magic_number()
        [dimensions, dt] = self._extract_header()
        total_bytes_to_be_read = np.prod(dimensions, dtype=np.int32)*dt.itemsize
        #print(self.fstream.read(total_bytes_to_be_read))
        data = np.frombuffer(self.fstream.read(total_bytes_to_be_read),dtype=dt)
        data = np.reshape(data,dimensions)
        if self.file_name is not None:
            self.fstream.close()
        return data
        
##############################
#                            #
# Class Information:         #
#                            #
# Class to load mnist file   #
# Parent class : load_idx    #
# Child class : None         #
#                            #
##############################

class load_mnist(load_idx):
    ###################################################################################################################
    #                                                                                                                 #
    # Class members:                                                                                                  #
    #                                                                                                                 #
    # load()                    : This function loads the specified file in the numpy array. It accepts no argument   # 
    #                                                                                                                 #
    # display_samples(how_many) : This function display some the loaded samples [default value 5], Just for sanity    #
    #                           : check. It accepts one arugment, number of samples to be displayed.                  #
    #                                                                                                                 #
    # display_images(number)    : This function displays specified images in the number variable. Number can be a     #
    #                           : scalar, list or numpy array.                                                        #
    #                                                                                                                 #
    ###################################################################################################################
    
    ###################################################################################################################
    #                                                                                                                 #
    # Description of Constructor:                                                                                     #
    #                                                                                                                 #
    # file_name        : Inherited argument from the load_idx class. For description see there.                       #
    #                                                                                                                 #
    # file_type        : type of the file, Accepted values are ['data', 'label']                                      #
    #                                                                                                                 #
    # file_handler     : Inherited argument from the load_idx class, For description see there.                       #
    #                                                                                                                 #
    # display_sample   : Display some of the values during loading file, Accepted values (True, False)                #
    #                                                                                                                 #
    # convert_to_float : Convert the loaded to the float, Accepted values (True, False)                               #
    #                                                                                                                 #
    ###################################################################################################################
    
    def __init__(self, file_name, file_type, file_handler=open, convert_to_float = False, display_sample = 0):
        load_idx.__init__(self, file_name = file_name, file_handler=file_handler)
        self.file_type = file_type
        self.convert_to_float = convert_to_float
        self.display_sample = display_sample
        self.mnist_magic_number={'data':2051, 'label':2049}
        if self.file_type == 'label':
            self.display_sample = 0
    
    ###################################################################################################################
    #                                                                                                                 #
    # Description of load():                                                                                          #
    #                                                                                                                 #
    # This function checks if the provided file is MNIST. If yes then it loads the data, Internally it calls the      #
    # load_file function from the parent class. If specified, it converts the data into the float format.             #
    #                                                                                                                 #
    # In the float format it nomalizes the data. [range 0 to 1]                                                       #
    #                                                                                                                 #
    ###################################################################################################################
    
    def load(self):
        self.get_magic_number()
        if self.mnist_magic_number[self.file_type] == self.magic_number:
            self.data = self.load_file()
            if self.convert_to_float:
                self.data = self.data.astype(np.float32)
                self.data = np.multiply(self.data, 1.0/255.0)
            if self.display_sample != 0:
                self.display_samples(self.display_sample)
            return self.data
        else:
            print('Given file is not mnist : (%s,%s)'%(self.file_name, self.file_type))
            
    ###################################################################################################################
    #                                                                                                                 #
    # Description of display_samples():                                                                               #
    #                                                                                                                 #
    # Displays the randomly selected images                                                                           #
    #                                                                                                                 #
    ###################################################################################################################
    
    def display_samples(self, how_many=5):
        size = self.data.shape[0]
        perm = np.random.permutation(size)
        perm = perm[:how_many]
        images = self.data[perm,:,:]
        for i in range(how_many):
            plt.figure()
            plt.imshow(images[i])
        
    ###################################################################################################################
    #                                                                                                                 #
    # Description of display_imagess():                                                                               #
    #                                                                                                                 #
    # Displays the specified selected images                                                                          #
    #                                                                                                                 #
    ###################################################################################################################
    
    def display_images(self, number):
        if number.shape.__len__() > 1:
            print('Number should be 1D array')
        else:
            for i in number:
                plt.figure()
                plt.imshow(self.data[i])


if __name__ == '__main__':
    
    #Test bench


    #Path to the mnist data folder

    mnist_data_folder = 'mnist/original/'

    #Name of training and testing files in gunzip format

    training_set_file_name =             'train-images-idx3-ubyte.gz'
    training_labels_file_name =          'train-labels-idx1-ubyte.gz'
    testing_set_file_name =              't10k-images-idx3-ubyte.gz'
    testing_labels_file_name =           't10k-labels-idx1-ubyte.gz'

    train_images_obj = load_mnist(mnist_data_folder+training_set_file_name, 'data', file_handler=gzip.GzipFile, display_sample=0)
    train_labels_obj = load_mnist(mnist_data_folder+training_labels_file_name, 'label', file_handler=gzip.GzipFile)
    test_images_obj = load_mnist(mnist_data_folder+testing_set_file_name, 'data', file_handler=gzip.GzipFile, display_sample=0)
    test_labels_obj = load_mnist(mnist_data_folder+testing_labels_file_name, 'label', file_handler=gzip.GzipFile)
    train_images = train_images_obj.load()
    train_labels = train_labels_obj.load()
    test_images = test_images_obj.load()
    test_labels = test_labels_obj.load()
    # Many learning algorithms accepts images in the vector format. Hence converting images in the vector format.

    train_images = train_images.reshape(train_images.shape[0],np.prod(train_images.shape[1:]))
    train_labels = train_labels.reshape(train_labels.shape[0],1)
    test_images = test_images.reshape(test_images.shape[0], np.prod(test_images.shape[1:]))
    test_labels = test_labels.reshape(test_labels.shape[0],1)
    print(test_images)
    print('min value train {}'.format(np.min(train_images)))
    print('max value train {}'.format(np.max(train_images)))
    print('Number of train images {}'.format(train_images.shape))
    print('Number of test images {}'.format(test_images.shape))
    
    
