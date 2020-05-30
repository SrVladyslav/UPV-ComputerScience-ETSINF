## -*- texinfo -*- 
## @deftypefn {} {@var{retval} =} Bernoulli (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-05-20

function [edv] = BernoulliEVA_PCA(X, xl, Y, yl, epsilon, kPCA)
  N = rows(X);
  rand("seed",23); 
  permutation = randperm(N);
  X = X(permutation,:);
  xl = xl(permutation,:);
  
  % Calculamos el PCA del Training Data
  [media W] = pca(X);
  printf("[ PCA Calculado! ]\n");
  
  % Realizamos la proyeccion a k dimensiones de train y valid original
  XpR = (X - media)*W(:,1:kPCA);
  YpR = (Y - media)*W(:,1:kPCA);
  
  % Aplicando el EVA
  [_, edv] = Bernoulli(XpR, xl, YpR, yl, epsilon);  

  % Imprimo la solucion
  save Entrega2/data/errores/bernoulli_EVA_PCA_1.txt edv;
  
endfunction