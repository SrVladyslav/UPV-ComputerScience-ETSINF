
#Usage: pca+knn-eva.m <trdata> <trlabels> <tedata> <telabels> <k>
# USO:
# 1- Cargar usando octave los distintos archivos del dataset
# load("t10k-images-idx3-ubyte.mat.gz")
# load("t10k-labels-idx1-ubyte.mat.gz")
# load("train-images-idx3-ubyte.mat.gz")
# load("train-labels-idx1-ubyte.mat.gz")
#
# 2- Llamar desde el interprete de Octave la funcion pasandole el parametro k necesario
# pcaTESTING (X,xl,Y,yl,100)
function errores = pcaTESTING (X,xl,Y,yl,k)

%
% HERE YOUR CODE
%
% Valor optimo de k dimensiones sacado es: 100 == 2.4
N=rows(X);
rand("seed",23); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);

%========================ERROR CON PCA=================================

% Aplicando PCA, seria:
[media W] = pca(X);

% Realizamos la proyeccion a k dimensiones optimas de train y valid
XpR = (X - media)*W(:,1:k);
YpR = (Y - media)*W(:,1:k);

err = knn(XpR, xl, YpR, yl, 1);
printf("Error con PCA: %f \n",err);
%=====================================================================



%==========================ERROR SIN PCA==============================
% Sin aplicar el PCA:
errOriginal = knn(X, xl, Y, yl, 1);
printf("Error sin PCA: %f \n",errOriginal);
%=====================================================================


% Imprimimos el resultado
errores = [err, errOriginal];
save testing.txt errores

endfunction