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
## @deftypefn {} {@var{retval} =} gc (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-05-20

function prob = gc(pc, mu, sigma, X)
  % ===================================================
  % Obtenemos Wc 
  % ===================================================
  Wc = -0.5 .* pinv(sigma);
  % ===================================================
  % Obtenemos wc
  % ===================================================
  wc = pinv(sigma) * mu';
  
  % ===================================================
  % Calculamos la wc0
  % ===================================================
  wc0 = log(pc) - 0.5 * logdet(sigma) - 0.5 * mu * pinv(sigma) * mu';

  % ===================================================
  % Calculamos el argmax utilizando os datos
  % ===================================================
  prob = sum((X * Wc) .* X ,2) + (wc' * X')' + wc0;
endfunction
