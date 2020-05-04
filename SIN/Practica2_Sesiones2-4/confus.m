function [nerr m]=confus(truelabs,recolabs)
  truelabs=reshape(truelabs,numel(truelabs),1);
  recolabs=reshape(recolabs,numel(recolabs),1);
  N=rows(truelabs); labs=unique([truelabs;recolabs]);
  C=numel(labs); m=zeros(C);
  for n=1:N m(find(labs==truelabs(n)),find(labs==recolabs(n)))++; end
  a=0; for c=1:C a+=m(c,c); end; nerr=N-a;
endfunction
