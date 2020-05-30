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
## @deftypefn {} {@var{retval} =} ultinomialEXP (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-04-21

# multinomialEXP(X, xl, [1e-10 1e-9 1e-8 1e-7 1e-6 1e-5 1e-4 1e-3 1e-2 1e-1])

function [error] = multinomialEXP(X, xl, epsilons)
  
  N = rows(X);
  rand("seed",23); 
  permutation = randperm(N);
  X = X(permutation,:);
  xl = xl(permutation,:);

  Ntr = round(0.9*N);
  Ndv = round(0.1*N);
  Xtr = X(1:Ntr,:); xltr=xl(1:Ntr);
  Xdv = X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);
  
  % Llamo al multinomial
  [_, error] = multinomial(Xtr, xltr, Xdv, xldv, epsilons);
  
endfunction
