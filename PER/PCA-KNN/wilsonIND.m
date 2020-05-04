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
## @deftypefn {} {@var{retval} =} wilsonIND (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-04-15

function ind = wilsonIND(X,xl,k)
  % Inicializamos el vector de indices
  ind = [1:rows(X)];
  m = 100;
  % Calculo los vecinos más cercanos
  vecinos = mnn(X,xl,m); %Vi = m
  printf("Vecinos calculados \n");
  error = true;
  it = 0;
  while(error)
    it ++;
    j = columns(ind);
    i = 1;
    printf('Iteracion WilsonIND: %d\n', it);
    error = false;
    while(i <= j)
      c = knnV(vecinos(:,ind(i)), ind,xl,k);
      if(xl(ind(i)) != c)
        error = true;
        %printf("Eliminando %d,%d C(%d) ->C(%d)\n", X(ind(i),1),X(ind(i),2),xl(ind(i)),c);
        %printf("Eliminado: %d,%d================\n", X(ind(i),1),X(ind(i),2));
        ind(i) = []; % Elimino el elemento de la clase c de los indices
        i--;
        j--;
      endif
      i++;
    endwhile
  endwhile  
  printf("Devolviendo la solucon...\n");
endfunction





