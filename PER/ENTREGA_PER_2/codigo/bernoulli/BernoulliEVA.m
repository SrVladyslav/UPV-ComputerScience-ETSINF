## -*- texinfo -*- 
## @deftypefn {} {@var{retval} =} Bernoulli (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-05-20

function [edv] = BernoulliEVA(X, xl, Y, yl, epsilon)
  N = rows(X);
  rand("seed",23); 
  permutation = randperm(N);
  X = X(permutation,:);
  xl = xl(permutation,:);
  
  % Aplicando el EVA
  [_, edv] = Bernoulli(X, xl, Y, yl, epsilon);  

  % Imprimo la solucion
  save Entrega2/data/errores/bernoulli_EVA_2.txt edv;
  
endfunction