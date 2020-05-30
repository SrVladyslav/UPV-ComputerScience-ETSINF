## Copyright (C) 2020 trigo
## 
## This program is free software: you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see
## <https://www.gnu.org/licenses/>.

## -*- texinfo -*- 
## @deftypefn {} {@var{retval} =} pcaEXPERIMENT (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-04-17
# pca+knn-exp.m <trdata> <trlabels> <ks> <%%trper> <%%dvper>\n
%=======USO=========================================================
# 1- Cargar usando octave los distintos archivos del dataset
# load("t10k-images-idx3-ubyte.mat.gz")
# load("t10k-labels-idx1-ubyte.mat.gz")
#
# 2- Llamar desde el interprete de Octave la funcion pasandole el parametro k necesario
# pcaEXPERIMENT (X,xl,[1,2,5,10,20,50,100,200,500], 90, 10)
%===================================================================
function pcaEXPERIMENT (X, xl, ks, trper, dvper)

%if (nargin!=5)
%printf("Usage: pca+knn-exp.m <trdata> <trlabels> <ks> <%%trper> <%%dvper>\n")
%exit(1);
%end;

%arg_list=argv();
%trdata=a1;
%trlabs=a2;
%ks=a3;
%trper=a4;
%dvper=a5;
%printf("Cargando training data....\n");
%load(trdata);
%printf("Cargando validation data....\n");
%load(trlabs);

printf("F");
N=rows(X);
rand("seed",23); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);

Ntr=round(trper/100*N);
Ndv=round(dvper/100*N);
Xtr=X(1:Ntr,:); xltr=xl(1:Ntr);
Xdv=X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);

%
% HERE YOUR CODE
%PCA devuelve la matriz proyeccion(X); []
errores = [];

%========================================== DATOS ORIGINALES ==========================================

err = knn(Xtr, xltr, Xdv, xldv, 1);

printf("Original sin PCA: %f \n",err);

%========================================== DATOS POR K CON PCA========================================

[media W] = pca(Xtr);
printf("[ PCA Calculado! ]\n");

printf("[ Empezando el calculo para la prueba de k ]\n");
printf("=iter==error=\n");

% Resto de la matriz
for k = ks
  % Realizamos la proyeccion a k dimensiones de train y valid sobre los datos sin modificar
  XpR = (Xtr - media)*W(:,1:k);
  
  YpR = (Xdv - media)*W(:,1:k);
  
  err = knn(XpR, xltr, YpR, xldv, 1);
  printf("%d %f \n",k,err);
  errores = [errores, err];
endfor

errores = [ks', errores'];
%[1 2 5 10 20 50 100 200 500]
save errores_original_pca_L2.txt errores
endfunction
