## Copyright (C) 2020 MAZURKEVYCH
## 
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*- 
## @deftypefn {} {@var{retval} =} dMahalanobis (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: MAZURKEVYCH <vlama@EVIRL-024-OK>
## Created: 2020-03-18

function D = Mahalanobis(X, xl,Y, alpha)
  
  W = Md4cweight(X, xl, alpha);
  printf("Calculando Mahalanobis\n");
  % Resta de cuadrados
  %XX = sum(X.*X,2);
  %YY = sum(Y.*Y,2);
  %DD = XX + YY' - 2*X*Y';
  %D = W*DD';
  for j=1:rows(X)
     D(j,:) = W(j,:) * ((X(j,:) - Y).^2)';
  endfor
  
  # ==============================================
  # Seguimos trabajando en una version optimizada
  # ==============================================
  
  
  
  
  %%%%NO EJECUTARLO, MORTAL para el ordenador
  %M = X - mean(Y);
  %D = M/W.*conj(M)
  
  
  %D = ((X-Y)'.*inv(W).*(X-Y));
  %C = chol(W);
  %z = C \ (X - Y);
  %D = sqrt(z' * z);
endfunction
