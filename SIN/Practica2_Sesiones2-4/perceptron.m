function [w,E,k]=perceptron(data,b,a,K,iw)
  [N,L]=size(data); D=L-1;
  labs=unique(data(:,L)); C=numel(labs);
  if (nargin<5) w=zeros(D+1,C); else w=iw; end
  if (nargin<4) K=200; end;
  if (nargin<3) a=1.0; end;
  if (nargin<2) b=0.1; end;
  for k=1:K
    E=0;
    for n=1:N
      xn=[1 data(n,1:D)]';
      cn=find(labs==data(n,L));
      er=0; g=w(:,cn)'*xn;
      for c=1:C; if (c!=cn && w(:,c)'*xn+b>g)
	      w(:,c)=w(:,c)-a*xn; er=1; end; end
      if (er)
	      w(:,cn)=w(:,cn)+a*xn; E=E+1; end; end
    if (E==0) break; end; end
endfunction
