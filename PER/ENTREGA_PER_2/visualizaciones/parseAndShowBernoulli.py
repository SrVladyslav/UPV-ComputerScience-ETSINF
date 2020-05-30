
import numpy as np
import pandas as pd
import seaborn as sns; sns.set()
import math as m
import os 
import matplotlib
from matplotlib import pyplot as plt


#ejeX = [1e-10, 1e-9, 1e-8, 1e-7, 1e-6, 1e-5, 1e-4, 1e-3, 1e-2, 1e-1,9e-1]
#ejeX = [1e-320, 1e-300, 1e-280 , 1e-260, 1e-240, 1e-220, 1e-200, 1e-150, 1e-100, 1e-50, 1e-20, 1e-6, 1e-5,1e-4, 1e-3, 1e-2, 0.1, 1, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7]
ejeX = [1e-320, 1e-300, 1e-280, 1e-260, 1e-240, 1e-220, 1e-200, 1e-150, 1e-100, 1e-50, 1e-20, 1e-06, 1e-05, 0.0001, 0.001, 0.01, 0.1, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7]
ejeY = [10, 15, 20, 50, 60, 70 , 80, 90]


def clearList(lis = []):
	res = []
	for l in range(len(lis)):
		if len(lis[l].lstrip()) > 0:
			res.append(lis[l])
	return res

def readTXT(path= ''):
	try:
		EPSILONS = {}

		file = open(path, "r")
		lin = file.readlines()

		data = []
		for l in lin:
			lista = clearList(l.replace('\n', '').split(' '))
			if len(lista) > 0:
				data.append(lista)
		#print(data)

		# limpiamos los datos de nuevo
		errores = []
		epsilons = []
		for d in data:
			epsilons.append(round(float(d[0]), 323))
			errores.append(round(float(d[1]), 3))


		print(epsilons)
		print(errores)

		# Starting our plot 
		fig1, ax1 = plt.subplots()

		ax1.plot(epsilons, errores,'-ko',color= 'red', lw=1, label=('Bernoulli con epsilon= '))
		#ax1.plot(epsilons, [31.467,31.467,31.467,31.467,31.467],'--',color= 'blue', lw=1, label=('Bernoulli con epsilon= '))
		


		ax1.set_xscale('log')
		ax1.set_yscale('log')
		ax1.set_xticks(epsilons)
		ax1.set_yticks(ejeY)
		ax1.get_xaxis().set_major_formatter(matplotlib.ticker.ScalarFormatter())
		ax1.get_yaxis().set_major_formatter(matplotlib.ticker.ScalarFormatter())
		plt.title('MNIST Bernoulli')
		plt.legend(title = 'Tipo de datos')
		plt.ylabel('Error (%)')
		plt.xlabel('Epsilon (e)')
		plt.show()

	except IOError:
		print("UPS, there was a problem with file somewhere...")

readTXT('Entrega2/data/errores/bernoulli_EXP_2.txt')