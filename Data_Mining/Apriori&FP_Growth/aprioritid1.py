# This is an improvement over the Apriori algorithm
# Here, we discard infrequent item sets at each step
# and Hashing is used to efficiently access the data

import argparse
from itertools import chain, combinations
import csv
import operator
import time
import itertools
def joinset(itemset, k):
    return set([i.union(j) for i in itemset for j in itemset if len(i.union(j)) == k])


def subsets(itemset):
    return chain(*[combinations(itemset, i + 1) for i, a in enumerate(itemset)])
    

def itemset_from_data(data):
    itemset = set()
    #transaction_list = list()
    i=-1
    temp=dict()
    for row in data:
        i=i+1
        item1=list()
        #transaction_list.append(row)
        for item in row:
            item11=set()
            if item:
                itemset.add(frozenset([item]))
                item11.add(item)
                item1.append(item11)
        temp.update({i:item1})         
    return itemset,temp
    
#updating the TID list
def updatetid(tl,c,k):
    end = time.time()
    print("update")
    print(end - start)
    temp=dict()
    #filling in the dictionary values
    for i,row in tl.items():
        l1=[(frozenset(item),0) for item in row]
        m11=dict([(item,support) for item,support in l1])
        #row2=list(itertools.combinations((row), k))
        if(k>1):
            row1=joinset(m11,k)
        else:
            row1=m11
        item1=list()
        for item in row1:
            if item in c:
                item1.append(item)
        temp.update({i:item1})        
    return temp

#getting the list which are having the minimum support
def tidsupport(tl, itemset, min_support,k):
    print(k)
    print("support")
    end = time.time()
    print(end - start)
    l = len(tl)
    temp=dict()
    #filling in the dictionary values
    for i,set1 in tl.items():
            for item in set1:
                if(item in temp):
                    temp[item]+=1
                else:
                    temp.update({item:1})
    m2=dict()
    for k, v in temp.items():
        if v >= min_support:
            m2[k] = v             
    return m2    


def freq_itemset(c_itemset, min_support,tid):
    #taking frequent item as dictionary
    f_itemset = dict()
    end = time.time()
    print(end - start)
    temp1=tid
    k = 1
    while True:
        if k > 1:
            end = time.time()
            print(end - start)
            c_itemset = joinset(l_itemset, k)
            end = time.time()
            print("joinset")
            print(end - start)
            #print(l_itemset)
        temp1=updatetid(temp1,c_itemset,k)
        l_itemset = tidsupport(temp1, c_itemset, min_support,k)
        if not l_itemset:
            break
        f_itemset.update(l_itemset)
        k += 1

    return f_itemset  
    
def apriori(data, min_support, min_confidence):
    # Get first itemset and transactions
    itemset,temp = itemset_from_data(data)
    # Get the frequent itemset
    f_itemset = freq_itemset(itemset, min_support,temp)
    print(f_itemset)
    # Association rules
    rules = list()
    for item, support in f_itemset.items():
        if len(item) > 1:
            for A in subsets(item):
                B = item.difference(A)
                if B:
                    A = frozenset(A)
                    AB = A | B
                    confidence = float(f_itemset[AB]) / f_itemset[A]
                    if confidence >= min_confidence:
                        rules.append((A, B, confidence))    
    return rules, f_itemset


def print_report(rules, f_itemset):
    print('--Frequent Itemset--')
    i=0
    for item, support in sorted(f_itemset.items(), key=lambda support: support[1]):
        i=i+1   
        print('[I'+str(i)+'] {} : {}'.format(tuple(item), round(support, 4)))

    print('')
    print('--Rules--')
    i=0
    for A, B, confidence in sorted(rules, key=lambda confidence: confidence[1]):
        i=i+1
        print('[R'+str(i)+'] {} => {} : {}'.format(tuple(A), tuple(B), round(confidence, 4))) 

start = time.time()
with open('chess.csv', 'rU') as f:
    reader = csv.reader(f)
    your_list = list(reader)
end = time.time()
print(end - start) 
rules, itemset = apriori(your_list, 3000, 0.5)
print_report(rules, itemset)
end = time.time()
print(end - start)
