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
## @deftypefn {} {@var{retval} =} mnn (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: trigo <trigo@LAPTOP-98VUH47R>
## Created: 2020-04-15

function [V] = mnn(X,xl,m)
  N=rows(X);
  %numbatches=N*N*4/intmax*10;
  numbatches=ceil(N*N*4/1024^3)*4;
  V = [];

  if (numbatches<1) numbatches=1; end
    batchsize=ceil(N/numbatches);
   
  for i=1:numbatches
    printf("[ MNN batch %d / %d ]\n", i, numbatches);
    % Building batches of test samples of batchsize
    Ybatch = X((i-1)*batchsize+1:min(i*batchsize,N),:);

    % D is a distance matrix where training samples are by rows 
    % and test sample by columns
    D = L2dist(X,Ybatch);

    % Sorting descend per column from closest to farthest
    [D,idx] = sort(D,'ascend');

    % Indexes in the training set of m nearest neighbors of each test sample
    idx = idx(2:m+1,:);
    
    % Appending idx to the end
    V = [V, idx];
  endfor
endfunction
