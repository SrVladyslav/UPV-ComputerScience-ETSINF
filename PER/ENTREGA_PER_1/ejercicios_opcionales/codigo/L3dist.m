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
function D = L3dist(X,Y)
  %XX = sum(X.*X,2);
  %YY = sum(Y.*Y,2);
  %D = XX + (YY' - 2*X*Y');
  %D = sum(X.*X.*X)-3*sum(X.*X)*Y'+3*(X*(Y.*Y))-3*Y;
  %somos vagos, asi que haremos la funcion en la grafica
  for i=1:rows(Y)
    D(:,i) = sum(abs(X-Y(i,:)).^3, 2);
  endfor
end
