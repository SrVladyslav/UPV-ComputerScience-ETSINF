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
## @deftypefn {} {@var{retval} =} gaussian (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-05-20

function [etr,edv]=gaussian(X,xltr,Xdv,xldv,alphas)
%% Aquí el código necesario
etr = [];
edv = [];

% Variables por clase
pPosteriori = [];
gcAlphas = {};

%mu = {};
%sigma = {};

% Obtengo las clases del dataset
clases = unique(xltr);
  
% ============================================================
% Preparativos
% ============================================================
for c=1:rows(clases)
  % ==========================================================
  % Estimacion por clase de la gaussiana
  % Probabilidad a Priori
  %===========================================================
  % Busco los elementos de clase
  ind = find(xltr == clases(c));
  
  % Calculo la probabilidad a priori
  pPriori(c) = rows(ind) / rows(X);

  % ==========================================================
  % Media POR CLASE DISPUESTAS POR COLUMNAS en mu
  % ==========================================================
  % Obtengo el sumatorio
  sumatorio = sum(X(ind,:));
  
  % Calculo la mu de clase
  media = sumatorio / rows(ind);
  mu(c,: ) = media;
  
  % ==========================================================
  % Matriz de covarianzas POR CLASE
  % ==========================================================
  sigma{c} = cov( X(ind,:),1);
endfor

% ============================================================
% Vamos a ejecutar aplicando todas las alphas :)
% ============================================================
printf("Empezando con la magia...\n");
for alpha=1:columns(alphas)
  errT = [];
  errV = [];
  % ==========================================================
  % Float smoothing para todas clases
  % ==========================================================  
  for c= 1:rows(clases)
    suavizado{c} = alphas(alpha)* sigma{c} + (1 - alphas(alpha)) * eye(rows(sigma{c}));
    
    % ========================================================
    % Calculamos gc para cada clase para Train y Validation
    % ========================================================
    gcTrain(:, c) = gc(pPriori(c), mu(c,:), suavizado{c}, X);
    gcValid(:, c) = gc(pPriori(c), mu(c,:), suavizado{c}, Xdv);
  endfor
  % ==========================================================
  % Clasificacion de los datos
  % ========================================================== 
  % Training Set
  printf("========= Alpha= %d =========\n", alphas(alpha));
  [_, i] = max(gcTrain,[], 2);
  errT = [errT, (mean((i - 1) != xltr) * 100)];
  printf("Error TS: %d\n", errT);
  
  % Validation Set
  [_, i] = max(gcValid,[], 2);
  errV = [errV, (mean((i - 1) != xldv) * 100)];
  printf("Error VS: %d\n", errV);
  
  % adjunto al resultado final
  etr = [etr; alphas(alpha), errT];
  edv = [edv; alphas(alpha), errV];
endfor
printf("========= ========= =========\n");

% Guardo el resultado
%save gaussian_etr.txt etr;
%save gaussian_edv.txt edv;
endfunction
