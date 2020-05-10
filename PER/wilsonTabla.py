import statistics as st
import numpy as np
# ===================================== RELLENA LAS PROPIEDADES =====================================
# Inserta las tuplas con los datos en formato
# (x1, x2, "nombre clase")
data = [
	#(,,""),
	(1,1,"A"),
	(3,1,"B"),
	(1,3,"A"),
	(4,2,"B"),
	(3,4,"A"),
	(3,2,"B"),
	(5,4,"B"),


]
# Hacerlo con L2: L2 = 2,sin L2: L1 = 1
DISTANCIA_L2 = 2

# Hacerlo usando raiz cuadrada: RAIZ = True, sin raiz: RAIZ = False 
RAIZ = False

# Calcular las varianzas de los datos: VARIANZA = True, sin Varianza VARIANZA = False
VARIANZA = True

# ======================================================================================================










	
# 	
# =======================================PROGRAMA EN SI, NO SE TOCA========================
# 
# Calcula la distancia L1
def distL2(x1, x2, sqrt = False):
	if sqrt:
		return round(((x1[0] - x2[0])**2 + (x1[1] - x2[1])**2)**(1/2),2)
	return ((x1[0] - x2[0])**2 + (x1[1] - x2[1])**2) 


# Calcula la distancia L2
def distL1(x1, x2, sqrt = False):
	if sqrt:
		return round((abs(x1[0] - x2[0]) + abs(x1[1] - x2[1]))**(1/2),2)
	return abs(x1[0] - x2[0]) + abs(x1[1] - x2[1]) 

# Dibuja la tabla
def drawTable(data = [], dist = 2,sqrt=False):
	print("								DISTANCIAS")
	print("======================================================================")
	sol = ["#######|"]
	sol2 = ["_Dist:"+str(dist)+"_"]
	solF = []
	for i in range(1,len(data)+1):
		sol.append("x"+str(i))
		sol2.append(">"+data[i-1][2])
	solF.append(sol)
	solF.append(sol2)

	k = 1
	for i in data:
		sol = ["x"+str(k) + " ("+i[2]+") |"]; k+=1
		for j in data:
			if dist == 2:
				sol.append(distL2(i,j,sqrt))
			else:
				sol.append(distL1(i,j,sqrt))
		sol.append("  |")
		solF.append(sol)
	for a in solF:
		s = ''
		for b in a:
			s += str(b) + "   " if len(str(b)) == 2 else str(b) + "    "  
		print(s)
	print("======================================================================")
	#print(solF)

# Sacaria el minimo (/**TODO**/)
def diveMin(data = [], ind = 0, elemento = 0, borrados = None):
	eata[elemento] = 5
	vini = 0; ind = 0
	for i in range(len(data)):
		if int(data[i]) > mini:
			lini = data[i]
			and = "x"+str(i+1)
	print(data)

# Calcula la varianza por clases
def vari(data=[], show= False):
	#(a,b,Clase)
	#Separo por clases
	if show:
		clases = {}
		clasesV = {}
		clasesM = {}
		m1 = 0; m2 = 0
		for clase in data:
			if clases.get(clase[2]) == None:
				m1 += clase[0]; m2 += clase[1]
				clases[clase[2]] = [(clase[0], clase[1])]
				clasesM[clase[2]+"_m1"] = clase[0]
				clasesM[clase[2]+"_m2"] = clase[1]
			else:
				m1 += clase[0]; m2 += clase[1]
				clases[clase[2]].append((clase[0], clase[1]))
				clasesM[clase[2]+"_m1"] = clasesM[clase[2]+"_m1"]+ clase[0]
				clasesM[clase[2]+"_m2"] = clasesM[clase[2]+"_m2"] +clase[1]
		# Hago la varianza del primer componente
		print("		         VARIANZA                     ")
		print("===========================================")
		for clase in clases.keys():
			s1 = 0; s2 = 0
			for x1, x2 in clases[clase]:
				s1 += (x1- (clasesM[clase+"_m1"]/len(clases[clase])))**2
				s2 += (x2- (clasesM[clase+"_m2"]/len(clases[clase])))**2
			print("Clase: ", clase," > σ (x1): ", round(s1/len(clases[clase]),3), "  |  σ (x2): ", round(s2/len(clases[clase]),3))
			clasesV[clase] = (s1/len(clases[clase]), s2/len(clases[clase]))
			print("-------------------------------------------")
			suma = 0		
		print("===========================================")
	else:
		pass
	#print(clases)
	#print(clasesM)
	#print(clasesV)

drawTable(data = data, dist = DISTANCIA_L2, sqrt = RAIZ)

vari(data, VARIANZA)
