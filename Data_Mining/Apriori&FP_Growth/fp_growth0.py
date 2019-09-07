"""
The program gets the min_sup and min_conf from the user in runtime only but the filename is entered beforehand.
In order to change the file from where you want to get the data just edit the filename variable in main function(Add .csv or .dat file).

References::
http://hanj.cs.illinois.edu/pdf/dami04_fptree.pdf
"""
import itertools


class FPNode(object):       #Class for defining a node of fptree.
    

    def __init__(self, value, count, parent):       #The constructor
        
        self.value = value
        self.count = count
        self.parent = parent
        self.link = None
        self.children = []

    # Function to check if given node has a particular child node.
    def has_child(self, value):
        
        for node in self.children:
            if node.value == value:
                return True

        return False

    #Function to return the node containing particular value in children of a node.
    def get_child(self, value):
        
        for node in self.children:
            if node.value == value:
                return node

        return None

    #Function to add a child to the given node.
    def add_child(self, value):
        
        child = FPNode(value, 1, self)
        self.children.append(child)
        return child


class FPTree(object):
    

    def __init__(self, transactions, threshold, root_value, root_count):     #constructor for tree initialisation.
        
        self.frequent = self.find_frequent_items(transactions, threshold)
        self.headers = self.create_header_table(self.frequent)
        self.root = self.build_fptree(
            transactions, root_value,
            root_count, self.frequent, self.headers)

    @staticmethod
    #Function to create a dictionary of items with support above the threshold.
    def find_frequent_items(transactions, min_sup):
        
        items = {}

        for transaction in transactions:
            for item in transaction:
                if item in items:
                    items[item] += 1
                else:
                    items[item] = 1

        for key in list(items.keys()):
            if items[key] < min_sup:
                del items[key]

        return items

    @staticmethod
    #Build the header table.
    def create_header_table(frequent):
        
        headers = {}
        for key in frequent.keys():
            headers[key] = None

        return headers

    #Function to build the FP tree from the transactions.
    def build_fptree(self, transactions, root_value, root_count, frequent, headers):
        
        root = FPNode(root_value, root_count, None)

        for transaction in transactions:
            sorted_items = [x for x in transaction if x in frequent]
            sorted_items.sort(key=lambda x: frequent[x], reverse=True)
            if len(sorted_items) > 0:
                self.insert_transaction(sorted_items, root, headers)

        return root

    #To insert a transaction into the fp tree.
    def insert_transaction(self, items, node, headers):
        
        start = items[0]
        child = node.get_child(start)
        if child is not None:
            child.count += 1
        else:
            # Add new child.
            child = node.add_child(start)

            # Link it to header structure.
            if headers[start] is None:
                headers[start] = child
            else:
                current = headers[start]
                while current.link is not None:
                    current = current.link
                current.link = child

        # Call function recursively for the remaining items in the transaction.s
        remaining_items = items[1:]
        if len(remaining_items) > 0:
            self.insert_transaction(remaining_items, child, headers)

    #If there is a no branching in the tree i:e it has only single path, return True, else return False.
    def check_no_branching(self, node):
        
        num_of_children = len(node.children)
        if num_of_children > 1:
            return False
        elif num_of_children == 0:
            return True
        else:
            return True and self.check_no_branching(node.children[0])
    
    #Generate a dictionary of patterns along with their support counts.
    def get_patterns(self):
        
        patterns = {}
        items = self.frequent.keys()

        # We have to include the suffix as a pattern if we are in a conditional tree.
        if self.root.value is None:
            suffix_value = []
        else:
            suffix_value = [self.root.value]
            patterns[tuple(suffix_value)] = self.root.count

        for i in range(1, len(items) + 1):
            for subset in itertools.combinations(items, i):
                pattern = tuple(sorted(list(subset) + suffix_value))
                patterns[pattern] = min([self.frequent[x] for x in subset])

        return patterns

    #Mine the constructed FP tree for frequent patterns.
    def find_frequent_patterns(self, threshold):
        
        if self.check_no_branching(self.root):
            return self.get_patterns()
        else:
            return self.add_suffix_patterns(self.find_in_subtrees(threshold))


    #Generate subtrees and mine them for patterns.
    def find_in_subtrees(self, threshold):
        
        patterns = {}
        order = sorted(self.frequent.keys(),
                              key=lambda x: self.frequent[x])

        # We take the items in descending order of their support value
        for item in order:
            suffixes = []
            conditional_tree_transactions = []
            node = self.headers[item]

            # We need to collect all the places where item occurs in the fp tree. this is done by just following the node links
            while node is not None:
                suffixes.append(node)
                node = node.link

            # For each occurrence of the item, trace the path back to the root node.
            for suffix in suffixes:
                frequency = suffix.count
                path = []
                parent = suffix.parent

                while parent.parent is not None:
                    path.append(parent.value)
                    parent = parent.parent

                for i in range(frequency):
                    conditional_tree_transactions.append(path)

            # Now we have the input for a subtree, so construct Conditional fp tree for this subtree and grab the patterns.
            subtree = FPTree(conditional_tree_transactions, threshold,
                             item, self.frequent[item])
            subtree_patterns = subtree.find_frequent_patterns(threshold)

            # Insert subtree patterns into main patterns dictionary.
            for pattern in subtree_patterns.keys():
                if pattern in patterns:
                    patterns[pattern] += subtree_patterns[pattern]
                else:
                    patterns[pattern] = subtree_patterns[pattern]

        return patterns

    #If we are in a conditional FP tree, append suffix to patterns in dictionary.
    def add_suffix_patterns(self, patterns):
        
        suffix = self.root.value

        if suffix is not None:
            # We are in a conditional tree.
            new_patterns = {}
            for key in patterns.keys():
                new_patterns[tuple(sorted(list(key) + [suffix]))] = patterns[key]

            return new_patterns

        return patterns


#Given a set of transactions, find the patterns in it over the specified support threshold.
def find_frequent_patterns(transactions, min_sup):
    
    fptree = FPTree(transactions, min_sup, None, None)
    return fptree.find_frequent_patterns(min_sup)


#Given a set of frequent itemsets, return a dict of association rules in the form {(left): ((right), confidence)}
def find_association_rules(patterns, min_conf):
    
    asso_rules = {}
    for itemset in patterns.keys():
        union_sup = patterns[itemset]

        for i in range(1, len(itemset)):
            for ante in itertools.combinations(itemset, i):
                ante = tuple(sorted(ante))
                conse = tuple(sorted(set(itemset) - set(ante)))

                if ante in patterns:
                    ante_sup = patterns[ante]
                    confidence = float(union_sup) / ante_sup

                    if confidence >= min_conf:      #We need only the rules for which confidence>minconf provided.
                        asso_rules[ante] = (conse, confidence)

    return asso_rules


if __name__ == "__main__":
    import time
    import csv
    transactions = []
    filename="chess.dat"                           #Put the name of file form where you want to read data from, here.
    
    if(filename[-4::]==".csv" or filename[-4::]==".CSV"):       #Make the transactions database from CSV or DAT file.
        with open(filename,"r") as database:
            for row in csv.reader(database):
                transactions.append(row)
    elif (filename[-4::]==".dat" or filename[-4::]==".DAT"): 
        transactions = [i.strip().split() for i in open(filename).readlines()]
        
    min_sup=float(input("Enter the fractional minsup: "));      #Get the min_sup from user
    min_sup= int(min_sup * len(transactions))
    
    time0=time.time();
    l1=(find_frequent_patterns(transactions,min_sup))           #Get the frequent Patterns
    time1=time.time();
    
    print("Frequent Patterns:: \n")
    
    for a in l1 :                                               #Display obtained patterns
       # if len(a)>1:
            print (a)
    print("Time taken:",time1-time0)
            
    minconf=float(input(("Enter the fractional confidence threshold: ")))       #Get the min_conf from user
    print("Association Rules: ")
    print ("In format: (left)=> (right) :: (confidence)\n")
    
    l2=find_association_rules(l1, minconf)                      #Get the association rules.
    for a in l2:
        print (a,"=>",l2[a][0]," :: ", l2[a][1])                #Display obtained Association rules
