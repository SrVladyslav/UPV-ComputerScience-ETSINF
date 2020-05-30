% This function computes the L2 distance between the N samples 
% in the training set X and the M samples in the test set Y. 
% Samples in X and Y are arranged by rows and the resulting 
% distance matrix D is N x M where each position (i,j) 
% is the L2 distance between the i-th training sample and 
% the j-th test sample 
% function D = L2dist(X,Y)

% Efficient implementation of L2 distance without square root
% d(x,y) = \sum_d (x_d - y_d)^2 = 
%        = \sum_d x_d^2 + y_d^2 - 2*x_d*y_d
%        = \sum_d x_d^2 + \sum_d y_d^2 - 2 * \sum_d x_d*y_d
%function D = L0dist(X,Y)
  %XX = sum(X.*X,2);
  %YY = sum(Y.*Y,2);
 % D = XX + (YY' - 2*X*Y');
%end
% Non-efficient implementation that computes the L2 distance for 
% each test sample with respect to all samples in the training set
function D = L0dist(X,Y)
 for i=1:rows(Y)
  D(:,i) = max(abs(X-Y(i,:)), [],2);
 end
endfunction