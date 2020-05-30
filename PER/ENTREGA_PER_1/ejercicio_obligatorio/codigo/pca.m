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
## @deftypefn {} {@var{retval} =} pca (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: MAZURKEVYCH <vlama@EVIRL-015-OK>
## Created: 2020-03-03

function [media, W] = pca(X)
  # Calcula la media m de los datos de entrenamiento X
  #X = X'; Los datos en MNIST ya vienen transpuestos por filas
  media = sum(X) / rows(X);

  #Restar la media a todos los datos de entrenamiento para obtener la Xm
  Xm = X - media;
  
  #Calcula la matriz de covarianza Xm' * Xm
  covarianza = (Xm' * Xm) / rows(X);
  
  #Calcula los valores y vectores propios (eig)
  [vectPropios, valPropios] = eig(covarianza);
  
  #Ordena los vectores propios con Sort
  [Vector, Indice] = sort(diag(valPropios), "descend");
  
  % Obtengo la matriz de pesos
  W = vectPropios(:,Indice);
  
end




