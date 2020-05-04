#!/usr/bin/octave -qf
if (nargin!=3)
printf("Usage: ./experiment.m <data> <alphas> <bes>\n");
exit(1);
end
arg_list=argv();
data=arg_list{1};
as=str2num(arg_list{2});
bs=str2num(arg_list{3});
load(data); 
[N,L]=size(data); 
D=L-1;


rand("seed",23); data=data(randperm(N),:);
ll=unique(data(:,L)); C=numel(ll);
NTr=round(.7*N); M=N-NTr; te=data(NTr+1:N,:);
printf("#   a          b    E   k  Ete   Ete(%%)   Intervalo Confi.\n");
printf("#-------      ---- --- --- ---  --------  ----[s-----r]-----\n");
for a=as
  for b=bs
    [w,E,k]=perceptron(data(1:NTr,:),b,a); rl=zeros(M,1);
    for n=1:M 
      
      rl(n)=ll(linmach(w,[1 te(n,1:D)]')); end
    [nerr nn]=confus(te(:,L),rl);
    
    etePerCent = nerr/M;
    s = sqrt(etePerCent*(1-etePerCent)/M);
    r = 1.96*s;
    
    printf("%8.4f %8.4f %3d %3d %3d %8.4f [%.3f, %.3f]\n",a,b,E,k,nerr,etePerCent*100,(etePerCent - r)*100,(etePerCent +
    r) *100);
  end
end

%alpha = 0.1
%beta = 1000