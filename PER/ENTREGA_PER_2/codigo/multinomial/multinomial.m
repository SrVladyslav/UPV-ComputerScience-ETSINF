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

function [etr, edv] = multinomial(X, xl, Y, yl, epsilons)
  etr = [];
  edv = [];
  
  for e=1:columns(epsilons)
    wc = [];
    wc0 = [];
    predicciones = [];
    % Obtengo el numero de clases que tenemos
    clases = unique(xl);

    # Probs a priori y posterior
    N = rows(X);
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
      if epsilons(e) != 0
        % Suavizado
        wc = [wc; log((posteriori + epsilons(e)) / (sum(posteriori + epsilons(e))))];
        wc0 = [wc0; log(probPrioriC(clase))];
      endif
    endfor
    
    % Calculamos las predicciones de clase y error para las muestras de entrenamiento 
    %=================================================================================
    [_, predicciones] = max(X * wc' + wc0',[],2);
    
    % Calculo los fallos
    errores = sum((predicciones-1) != xl);
		porErr = (errores/rows(xl))*100;
    etr = [etr; porErr];
    printf("Error TS para epsilon %e es: %.2f \n",epsilons(e),porErr);
    
    % Calculamos las predicciones de clase y error para las muestras de validadcion
    %=================================================================================
    [_, predicciones] = max(Y * wc' + wc0',[],2);
    
    % Calculo los fallos
    errores = sum((predicciones-1) != yl);
		porErr = (errores/rows(yl))*100;
    edv = [edv; porErr];
    printf("Error VS para epsilon %e es: %.2f\n",epsilons(e),porErr);
    printf("===================================================\n")
    
    errores = [etr, edv];
    save erroresMultinomial.txt errores
  endfor
 endfunction
