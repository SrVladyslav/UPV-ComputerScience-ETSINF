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
## @deftypefn {} {@var{retval} =} Bernoulli (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-05-20

function [etr, edv] = Bernoulli(X, xl, Y, yl, epsilon)
  % Aplicamos la sigmoide
  X = sigmoid(X); 
 
  %X(X == 0) = realmin;
  % ====================================================
  predicciones = [];
  
  % Obtengo el numero de clases que tenemos
  clases = unique(xl);
  N = rows(X);
  probPriori = [];
  probPosteriori = [];
  wcd = [];
  wc0 = [];
  
  for clase = 1:rows(clases)
    % Probs a priori
    ind = find(xl == clases(clase));
    NC = rows(ind);
    probPriori = [probPriori; NC/N];
    
    % Probs a posteriori
    xn = sum(X(ind,:));
    pcd = xn / NC;
    
    % Hacemos truncamiento simple a la probabilidad a posteriori
    pcd = truncarSimplemente(pcd, epsilon); 
    
    probPosteriori = [probPosteriori, pcd]; 
    
    % Acabo con los costes
    wcd1 = log(pcd) - log(1- pcd);
    wc01 = log(probPriori(clase)) + sum(log(1- pcd));
    wcd = [wcd; wcd1];
    wc0 = [wc0; wc01];
  endfor

  % Predicciones
  [_, predicciones] = max(X*wcd' + wc0',[],2);

  % Calculo los fallos
  errores = sum((predicciones-1) != xl);
	etr = (errores/rows(xl))*100;
  printf("\n=== Epsilon: %d ===================\n", epsilon);
  printf(" > Error Train: %.2f \n",etr);
  printf("---------------------------------------------------\n");
    
    
  % Predicciones
  [_, predicciones] = max(Y * wcd' + wc0',[],2);
  
  % Calculo los fallos
  errores = sum((predicciones-1) != yl);
  edv = (errores/rows(yl))*100;
  printf(" > Error Valid: %.2f \n",edv);
  printf("===================================================\n");
endfunction
