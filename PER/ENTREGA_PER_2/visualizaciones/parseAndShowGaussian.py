
import numpy as np
import pandas as pd
import seaborn as sns; sns.set()
import math as m
import os 
import matplotlib
from matplotlib import pyplot as plt


#ejeX = [1e-10, 1e-9, 1e-8, 1e-7, 1e-6, 1e-5, 1e-4, 1e-3, 1e-2, 1e-1,9e-1]
ejeX = [1,5,10,15,20,25,30,31,32,34,35,36,37,38,39,40,41,42,43,45,46,47,48,49,50,55,60,70,80,90,100,200,250,300,350,400,500,600,700, 784]
ejeY = [5, 10, 15, 20, 50, 80]


def clearList(lis = []):
	res = []
	for l in range(len(lis)):
		if len(lis[l].lstrip()) > 0:
			res.append(lis[l])
	return res

def readTXT(path= ''):
	try:
		ALPHAS = {}

		file = open(path, "r")
		data = file.readlines()
		
		# \n by ' |' to separate the types
		data = ' '.join(data).replace('\n',' |')

		# Getting all the types from the dataset
		data = data.split('|')
		#print(data[0])

		data = clearList(data)
		# Completting ALPHAS
		for alpha in range(len(data)):
			term = data[alpha].split(' ')
			term = clearList(term)
			#print(term)
			
			# Fulfilling the ALPHAS
			ALPHAS[round(float(term[0]),15)] = [term[t] for t in range(len(term)) if t % 2 == 1]

		

		# EXTRA 
		v = [14.08, 6.32, 4.27, 6.38, 10, 11.97]
		l = 0
		for k in ALPHAS.keys():
			ALPHAS[k].append(v[l])
			ALPHAS[k].append(v[l])
			l += 1

		# Starting our plot 
		fig1, ax1 = plt.subplots()

		colors = ['orange', 'red', 'green', 'purple', 'cyan', 'blue', 'black', 'cyan']

		c = 0
		for k in ALPHAS.keys():
			print(k)
			Y = [float(i) for i in ALPHAS[k]]
			print(">>> ",[round(l, 2) for l in Y])
			ax1.plot(ejeX, Y,'-',color= colors[c], lw=1, label=('Gaussian con alpha= '+ str(k)))
			c+=1
		


		ax1.set_xscale('log')
		ax1.set_yscale('log')
		ax1.set_xticks(ejeX)
		ax1.set_yticks(ejeY)
		ax1.get_xaxis().set_major_formatter(matplotlib.ticker.ScalarFormatter())
		ax1.get_yaxis().set_major_formatter(matplotlib.ticker.ScalarFormatter())
		plt.title('MNIST Gaussiano')
		plt.legend(title = 'Tipo de datos')
		plt.ylabel('Error (%)')
		plt.xlabel('Dimension PCA (k)')
		plt.show()

	except IOError:
		print("UPS, there was a problem with file somewhere...")

readTXT('errores.txt')