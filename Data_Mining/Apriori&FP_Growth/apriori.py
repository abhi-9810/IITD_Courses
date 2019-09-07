import itertools
from itertools import chain, combinations
import csv
import operator
import time
#it is for finding the subset of size of k
def findsubsets(S,m):
    return set(itertools.combinations(S, m))
#it will return all the subsets of the set which are k in size
def joinset(itemset, k):
    return sorted(set([i.union(j) for i in itemset for j in itemset if len(i.union(j)) == k]))

#returns subset of the given set
def subsets(itemset):
    return chain(*[combinations(itemset, i + 1) for i, a in enumerate(itemset)])
    
#reading data from csv file
def itemset_from_data(data):
    itemset = set()
    transaction_list = list()
    for row in data:
        transaction_list.append(row)
        for item in row:
            if item:
                itemset.add(frozenset([item]))
    return itemset, transaction_list


#giving all the itemsets which are having min_support 
def itemset_support(transaction_list, itemset, min_support,k):
    if(k==1):
        l=[(item,0) for item in itemset]
        m1=dict([(item,support) for item,support in l])
        for row in transaction_list:
           for item in row:
            if item:
                m1[frozenset([item])]+=1  
        temp=list()
        for row in transaction_list:
            row=[item for item in row if m1.get(frozenset([item]),0)>=min_support]
            temp.append(row)  
        temp = [x for x in temp if x != []] 
        m2=dict()
        for k, v in m1.items():
            if v >= min_support:
                m2[k] = v

        return m2,temp
    #print(k)
    l=[(item,0) for item in itemset]
    m1=dict([(item,support) for item,support in l])
    i=0
    for row in transaction_list:
        row22=findsubsets(row,k)
        temp=list()
        for item in row22:
            t1=set()
            for item1 in item:
                t1.add(item1)
            temp.append(t1)    
        l11=[([item],0) for item in row22]
        l1=[(frozenset(item),0) for item in temp]
        m11=dict([(frozenset(item),support) for item,support in l1])
        row1=m11
        for item in row1:
            if item in m1.keys():
                m1[item]+=1  
    m2=dict()
    temp=list()
    for k1, v in m1.items():
        if v >= min_support:
            m2[k1] = v
    return m2    

#giving all the itemsets which are frequent
def freq_itemset(transaction_list, c_itemset, min_support):
    #taking frequent item as dictionary
    f_itemset = dict()
    #starting with one frequent item set
    k = 1
    while True:
        if k > 1:
            c_itemset = joinset(l_itemset, k)
            l_itemset= itemset_support(transaction_list, c_itemset, min_support,k)
        if(k==1):
            l_itemset,transaction_list = itemset_support(transaction_list, c_itemset, min_support,k)
            #print(l_itemset)
        if not l_itemset:
            break
        f_itemset.update(l_itemset)
        k += 1
    
    return f_itemset    


def apriori(data, min_support, min_confidence):
    # Get first itemset and transactions
    itemset, transaction_list = itemset_from_data(data)
    # Get the frequent itemset
    f_itemset = freq_itemset(transaction_list, itemset, min_support)
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


with open('chess.csv', 'rU') as f:
    reader = csv.reader(f)
    your_list = list(reader)
start = time.time()    
rules, itemset = apriori(your_list, 3000, 0.5)
print_report(rules, itemset)
end = time.time()
print(end - start)
