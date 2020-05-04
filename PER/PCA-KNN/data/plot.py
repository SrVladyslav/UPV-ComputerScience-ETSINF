import seaborn as sns; sns.set()
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import math as m

ejeX = [1, 2, 5, 10, 20, 50, 100, 200, 500]
ejeY = [2, 3, 5, 10, 20, 50, 80, 100]
#	PCA
PCA_L0 = [75.466, 61.8667, 31.067, 8.9667, 4.2167, 3.467, 3.4334, 3.4334,3.434]
PCA_L0_O = [16.999, 16.999, 16.999, 16.999, 16.999, 16.999, 16.999, 16.999, 16.999]

PCA_L1 = [75.4666, 62.53, 30.516667, 8.716667, 3.633, 2.533, 2.9667, 3.6, 4.633]
PCA_L1_O =[3.516667, 3.516667, 3.516667, 3.516667, 3.516667, 3.516667, 3.516667, 3.516667, 3.516667]

PCA_L2 = [75.466, 62.15, 30.483, 8.4, 3.1, 2.5, 2.4, 2.75, 2.8167]
PCA_L2_O = [2.81667, 2.81667, 2.81667, 2.81667, 2.81667, 2.81667, 2.81667,2.81667, 2.81667]

PCA_L3 = [75.466, 62.1166, 30.933, 8.45, 3.25, 2.46, 2.5166, 2.6333, 2.64]
PCA_L3_O = [2.65, 2.65, 2.65, 2.65, 2.65, 2.65, 2.65, 2.65, 2.65]

PCA_WILSON = [74.633, 61.766, 30.016667, 8.083, 3.1833, 2.616667, 2.6833, 2.9,2.933]
PCA_WILSON_O = [2.933, 2.933, 2.933, 2.933, 2.933, 2.933, 2.933, 2.933, 2.933]


import matplotlib
from matplotlib import pyplot as plt
fig1, ax1 = plt.subplots()

ax1.plot(ejeX, PCA_L0,'-ok', color='red', lw=1, label='PCA + L0')
ax1.plot(ejeX, PCA_L0_O,linestyle='--', color='red', lw=1, label='L0+Original')

ax1.plot(ejeX, PCA_L1,'-ok',color='orange', lw=1, label='PCA + L1')##3498DB
ax1.plot(ejeX, PCA_L1_O,color='orange',linestyle='--', lw=1, label='L1+Original')

ax1.plot(ejeX, PCA_L2,'-ok',color='#239B56', lw=1, label='PCA + L2')#'-ok',
ax1.plot(ejeX, PCA_L2_O,color='#239B56', linestyle='--', lw=1, label='L2+Original')#239B56

ax1.plot(ejeX, PCA_L3,'-ok',color='#8E44AD', lw=1, label='PCA + L3')
ax1.plot(ejeX, PCA_L3_O,color='#8E44AD', linestyle='--', lw=1, label='L3+Original')##8E44AD

ax1.plot(ejeX, PCA_WILSON,color='red', lw=1, label='PCA + Wilson')
ax1.plot(ejeX, PCA_WILSON_O,color='red', linestyle='--', lw=1, label='PCA+Wilson+Original')
ax1.plot(ejeX, [46.1, 46.1, 46.1, 46.1, 46.1, 46.1, 46.1, 46.1, 46.1],linestyle='--',color='#99A3A4', lw=1, label='Mahalanobis')




ax1.set_xscale('log')
ax1.set_yscale('log')
ax1.set_xticks(ejeX)
ax1.set_yticks(ejeY)
ax1.get_xaxis().set_major_formatter(matplotlib.ticker.ScalarFormatter())
ax1.get_yaxis().set_major_formatter(matplotlib.ticker.ScalarFormatter())
plt.legend()

plt.show()
