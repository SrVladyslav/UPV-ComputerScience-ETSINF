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
## @deftypefn {} {@var{retval} =} gaussianEVA_PCA (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-05-28

function error = gaussianEVA_PCA(X, xl, Y, yl, alpha, kPCA)
  N = rows(X);
  rand("seed",23); 
  permutation = randperm(N);
  X = X(permutation,:);
  xl = xl(permutation,:);
  minError = [];

  % ==================================================================
  % PCA
  % ==================================================================
  % Calculamos el PCA del Training Data
  [media W] = pca(X);
  printf("[ PCA Calculado! ]\n");
  
  % Realizamos la proyeccion a k dimensiones de train y valid original
  XpR = (X - media)*W(:,1:kPCA);
  YpR = (Y - media)*W(:,1:kPCA);
  
  % ==================================================================
  % El resto del ejercicio
  % ==================================================================
  
  % Llamo al gaussiano
  [_, error] = gaussian(XpR, xl, YpR, yl, alpha);
  
endfunction
