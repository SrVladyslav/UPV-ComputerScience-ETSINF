import numpy as np


# ==========================================================
# Rellena los datos de cada variable
# =========================================
# 					 TEMA 1 
# =========================================
# Activate TEMA1 
TEMA1 = True

# Data
A = [2, 4, 8, 16, 32, 64, 128]
B = [1, 2, 3, 5, 8, 13, 21, 34]


# >>>>>>>>>>>>>>> METODOS <<<<<<<<<<<<<<<<<
# ===== Returns list C that contains the items from A that are not in list B =====
AND_NOT = True

# ===== Returns list C that contains the items that are in A and B at same time =====
INTERSECT = True

# =========================================
# 					 TEMA 2 
# =========================================
# Activate TEMA2 
TEMA2 = True

# DATA
TEXT = '''
	The project is a final examination exercise in the Informatics
Engineering and in both Diplomas. Consequently, the student is
required to show that he or she is able to apply the knowledge
acquired during his/her studies to typical informatics engineering
situations, allowing for the different specific course objectives,
which are defined in the curriculum. 
'''
# Add your options if you want 'OLD':'NEW'
PORTER_RULE = {
	"sses": 'ss',
	"ies": 'i',
	"ss": 'ss',
	"s": ''
}

# ====== Porter's rule, stemmed text================
PORTER = True

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




# Ecxecution
if TEMA1:
	print("====================== TEMA 1 ======================")
	print('A= ', A)
	print('B= ', B)
	if AND_NOT: and_not(A, B)
	if INTERSECT: intersect(A, B)
if TEMA2:
	print("====================== TEMA 2 ======================")
	print('TEXT:', TEXT)
	print('PorterRule:', PORTER_RULE)
	if PORTER: stemmer(TEXT, PORTER_RULE)
	#if SKIP_LIST: