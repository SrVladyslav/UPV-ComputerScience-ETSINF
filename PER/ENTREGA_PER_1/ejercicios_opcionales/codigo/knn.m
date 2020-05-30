% Computes the error rate of k nearest neighbors 
% of the test set Y with respect to training set X
% X  is a n x d training data matrix 
% xl is a n x 1 training label vector 
% Y is a m x d test data matrix
% yl is a m x 1 test label vector 
% k is the number of nearest neigbors
function [err]=knn(X,xl,Y,yl,k)

N=rows(X);
M=rows(Y);
%numbatches=M*N*4/intmax*10;
numbatches=ceil(M*N*4/1024^3)*4;
if (numbatches<1) numbatches=1; end

batchsize=ceil(M/numbatches);
alpha = 0.95;
classification=[];

% The classification of the test samples is split 
% into batches to make sure that the distance matrix D 
% computed in the distance function fits into memory
for i=1:numbatches
  % Building batches of test samples of batchsize
  Ybatch=Y((i-1)*batchsize+1:min(i*batchsize,M),:);

  % D is a distance matrix where training samples are by rows 
  % and test sample by columns
  %D = Mahalanobis(X ,xl, Ybatch, alpha);
  %D = L0dist(X,Ybatch);
  %D = L1dist(X, Ybatch);
  D = L2dist(X,Ybatch);
  %D = L3dist(X,Ybatch);

  % Sorting descend per column from closest to farthest
  [D,idx] = sort(D,'ascend');

  % indexes in the training set of k nearest neighbors of each test sample
  idx = idx(1:k,:);

  % Classification of the test samples in the majority class among the k nearest neighbors 
  % Note: (xl' is needed when k=1 and xl(idx) is a vector and not a matrix
  classification = [classification mode(xl'(idx),1)];
end

% percentage of error
err = mean(yl!=classification')*100;

end
