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
## @deftypefn {} {@var{retval} =} Md4cweight (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-04-15

function W = Md4cweight(X, xl,alpha)
  printf('[ Empezando a calcular W... ]\n');
  W = [zeros(rows(X),columns(X))];
  % Obtengo las clases existentes
  clases = unique(xl);
  
  % Calculo la varianza para cada clase
  for clase=1:rows(clases)
      % Obtenemos los elementos de clase
      elementos = find(xl==clases(clase));
      
      % Calculamos la varianza
      v = var(X(elementos,:));
      
      % Le pasamos el suavizado 
      v = alpha*v + (1 - alpha) * eye(rows(v));
      
      % Los pesos de las clases seríann
      pesos = 1./v;
      
      % Volcamos dichos pesos en la matriz de pesos en las posiciones correspondientes
      for elemento=(elementos')
        W(elemento,:) = pesos;
      endfor
  endfor
  printf('[ Matriz W calculada! ]\n');
endfunction
























