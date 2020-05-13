import numpy as np
# GENERAL OPTIONS
ATTEMPS = 10000
# ==========================================================
# Rellena los datos de cada variable
# ==================================================================================
# 					 TEMA 1 
# ==================================================================================
# Activate TEMA1 
TEMA1 = False

# Data
A = [2, 4, 8, 16, 32, 64, 128]
B = [1, 2, 3, 5, 8, 13, 21, 34]

# >>>>>>>>>>>>>>> METODOS <<<<<<<<<<<<<<<<<
# ===== Returns list C that contains the items from A that are not in list B =====
AND_NOT = True

# ===== Returns list C that contains the items that are in A and B at same time =====
INTERSECT = True

# ==================================================================================
# 					 TEMA 2 
# ==================================================================================
# Activate TEMA2 
TEMA2 = True

# DATA
TEXT = '''
	The project is a final examination exercise in the Informatics
Engineering and in both Diplomas.. 
'''
# Add your options if you want 'OLD':'NEW'
PORTER_RULE = {
	"sses": 'ss',
	"ies": 'i',
	"ss": 'ss',
	"s": ''
}

# ==================================================================================
# 						HASHING TEMA 2
# ==================================================================================
# ====== Porter's rule, stemmed text================
PORTER = False

# ===================== HASHING  ====================
LIST2HASH = [23,14,9,6,30,12,18]
UNIQUE_SET = False
HASH_SIZE = 7

# 0 = Addressing Hash | 1 = Redispersion Hash
HASH_TYPE = 1

# ======= SIMPLE Hash Table ==============
CHAINING_HASH = True

# ======= ADDRESSING Hash Table ==============
ADDRESSING_HASH = True

# ======= Lineal Hashing==============
LINEAL_HASH = True


# Define the funciton to use in hash with funtion
# @param: x : value
# @param: size: size of MOD 
def f(x, size = HASH_SIZE):
	return (x % (size -1)) + 1






# =========================================================== CODIGO ================================================================ 
'''
    Problem: get list C, that contains the items from A that are not in list B
	@param: A = [2, 4, 8, 16, 32, 64, 128]
	@param: B = [1, 2, 3, 5, 8, 13, 21, 34]
	@return The solution is:  [4, 16, 32, 64, 128]
'''
def and_not(A= [], B = []):
	C = [] #solution list
	#   We will use two pointers in the list to compare, i and j
	i = j = 0
	a = len(A) 
	b = len(B) 

	while i < a and j < b:
	    if A[i] == B[j]:
	        i += 1
	        j += 1
	    elif A[i] < B[j]:
	        C.append(A[i])
	        i += 1
	    else:
	        j += 1
	# When B is done but A is not completed yet, so we add all A elements to the solution list
	if i < len(A):
	    for k in range(i, len(A)):
	        C.append(A[k])    
	print("---------------------------------------------------------------------------")
	print('> Contains items from A that are not in B: \nC=',C)

'''
	Intersection: given lists A and B, returns C that contains
	elements from both lists
	@param: list A
	@param: list B
	@return list C
'''
def intersect(A= [], B= []):
	C = []
	#   We will use two pointers in the list to compare, i and j
	i = j = 0
	a = len(A) 
	b = len(B) 
	while i < a and j < b:
		if A[i] == B[j]:
			C.append(A[i])
			i += 1; j += 1
		elif A[i] < B[j]:
			i +=1
		else:
			j += 1
	print("---------------------------------------------------------------------------")
	print("> Contains items that are in A and B at same time: \nC=", C)


'''
	Given the text and specific porters rule, returns the stemmed text
	@param: string text
	@param: Porters Rule
	@return: stemmed text as string
'''
def stemmer(TEXT, PORTER_RULE):
	for i in PORTER_RULE.keys():
		TEXT = TEXT.replace(i, PORTER_RULE[i])
	print("---------------------------------------------------------------------------")
	print("> Porter's solution: ", TEXT)


# Just prints the HashTable
def showHash(HT = [], typ = "SIMPLE", useSet= UNIQUE_SET):
	j = 0
	print("===== ", typ,"HASH TABLE -> (SIZE=",len(HT), ")========")
	for i in HT:
		print(j, " -> ", set(i)) if useSet else print(j, " -> ", i) 
		j += 1
	print("==============================================")


'''
	Simple chaining hash function: h(list(x)) = list(x) MOD S
	@param: list of elements to do the hash
	@param: size S
	@return: hash table
'''
def chainingHash(l = [], size = 1):
	hashTable = [None for i in range(size)]
	for i in l:
		value = i % size
		hashTable[value] = [i] if hashTable[value] == None else hashTable[value] + [i]
	showHash(hashTable)

'''
	Simple addressing hash 
	h(list(x)) = list(x+1) MOD S
	@param: list of elements to do the hash
	@param: size S
	@return: hash table
'''
def addressingHash(l = [], size = 1, t = 0):
	hashTable = [None for i in range(size)]
	# TYPE 0: Lineal Dispersion
	tp = "ADDRESSING"
	if t == 0:
		for i in l:
			k = 0
			value = i % size
			while True and k < ATTEMPS:
				if hashTable[value] == None:
					hashTable[value] = [i]
					break
				else:
					# next position
					value = (value + 1) % size
				k += 1
			print("Attempts for", i, ": ", k)
	if t == 1:
		tp = "REDISPERSION"
		for i in l:
			k = 0
			value = i % size
			while True and k < ATTEMPS:
				if hashTable[value] == None:
					hashTable[value] = [i]
					break
				else:
					# next position
					value = (value + f(i)) % size
				k += 1
			print("Attempts for", i, ": ", k)
	showHash(hashTable, typ= tp)


#simpleHash(l = [1,2,3,4,5,6,7,55,2,2,445,8,9,10], size = 5)
'''
	Simple lineal hash function: h(list(x)) = list(x) MOD S
	@param: list of elements to do the hash
	@param: size S
	@return: hash table
'''
def linealHash(l = [], size = 1):
	hastTable = {}
	for i in l:
		pass






# Ecxecution
if TEMA1:
	print("====================== TEMA 1 ======================")
	print('A= ', A)
	print('B= ', B)
	if AND_NOT: and_not(A, B)
	if INTERSECT: intersect(A, B)
if TEMA2:
	print("====================== TEMA 2 ======================")
	if PORTER: 
		print('TEXT:', TEXT)
		print('PorterRule:', PORTER_RULE)
		stemmer(TEXT, PORTER_RULE)
	if CHAINING_HASH: chainingHash(LIST2HASH, HASH_SIZE)
	if ADDRESSING_HASH: addressingHash(LIST2HASH, HASH_SIZE, HASH_TYPE)

'''
if TEMA2:
	print("====================== TEMA 3 ======================")
	print('TEXT:', TEXT)
	print('PorterRule:', PORTER_RULE)
	if PORTER: stemmer(TEXT, PORTER_RULE)
	if SKIP_LIST:
		'''