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
## @deftypefn {} {@var{retval} =} multinomialEVA (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-05-20

function [error] = multinomialEVA (X, xl, Y, yl, epsilon)

  N = rows(X);
  rand("seed",23); permutation=randperm(N);
  X = X(permutation,:); xl = xl(permutation,:);
  
  % Entrenamos nuestro modelo con training data y la mejor Epsilon
  wc = [];
  wc0 = [];
  predicciones = [];
  % Obtengo el numero de clases que tenemos
  clases = unique(xl);

  # Probs a priori y posterior
  probPrioriC = [];
  probPosterioriC = [];
  for clase = 1:rows(clases)
    % Probs a priori
    ind = find(xl == clases(clase));
    NC = rows(ind);
    probPrioriC = [probPrioriC; NC/N];
    
    % Probs a posteriori
    xn = sum(X(ind,:));
    xnd = sum(sum(X(ind,:)));
    posteriori = xn / xnd;
    
    % Obtenemos cada clasificador
    if epsilon != 0
      % Suavizado
      wc = [wc; log((posteriori + epsilon) / (sum(posteriori + epsilon)))];
      wc0 = [wc0; log(probPrioriC(clase))];
    endif
  endfor
  
  % Calculamos las predicciones de clase y error para las muestras de entrenamiento 
  %=================================================================================
  [_, predicciones] = max(Y * wc' + wc0',[],2);
  
  % Calculo los fallos
  errores = sum((predicciones-1) != yl);
  porErr = (errores/rows(yl))*100;
  printf("========================================================\n");
  printf("Error sobre Test Data es: %.2f \n",porErr);
  printf("========================================================\n");
  
  save multinomialEVA.txt porErr;
endfunction
