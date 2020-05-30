## -*- texinfo -*- 
## @deftypefn {} {@var{retval} =} Bernoulli (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-05-20

function [error] = BernoulliEXP_PCA(X, xl, epsilon, nPCA)
  N = rows(X);
  rand("seed",23); 
  permutation = randperm(N);
  X = X(permutation,:);
  xl = xl(permutation,:);
  Ntr = round(0.9*N);
  Ndv = round(0.1*N);
  Xtr = X(1:Ntr,:); xltr=xl(1:Ntr);
  Xdv = X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);
  minError = [];
  
  % ==================================================================
  % Viejo amigo, el PCA
  % ==================================================================
  % Calculamos el PCA del Training Data
  [media W] = pca(Xtr);
  printf("[ PCA Calculado! ]\n");
  
  for pca=nPCA
    printf("PCA = %d", pca);
    % Realizamos la proyeccion a k dimensiones de train y valid original
    XpR = (Xtr - media)*W(:,1:pca);
    YpR = (Xdv - media)*W(:,1:pca);
    
    % ==================================================================
    % El resto del ejercicio
    % ==================================================================
    
    % Llamo al gaussiano
    [_, error] = Bernoulli(XpR, xltr, YpR, xldv, epsilon); 
    
    % Obtengo los minimos errores
    
    minError = [minError , error];
    
  endfor  

  % Imprimo la solucion
  save Entrega2/data/errores/bernoulli_EXP_PCA_1.txt minError;
  
endfunction