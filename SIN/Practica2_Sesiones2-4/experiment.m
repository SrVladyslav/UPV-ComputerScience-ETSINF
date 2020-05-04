#!/usr/bin/octave -qf
if (nargin!=3)
printf("Usage: ./experiment.m <data> <alphas> <bes>\n");
exit(1);
end
arg_list=argv();
data=arg_list{1};
as=str2num(arg_list{2}); %guarda las alphas
bs=str2num(arg_list{3}); %guarda las betas
load(data); 
[N,L]=size(data);   %N>> numero de filas de data, L>> numero de columnas
D=L-1; %obtengo numero de columnas de datos solo sin su etiqueta


rand("seed",23);          %Los random siempre seran iguales y no muy random xD
data=data(randperm(N),:); %permuto las filas de mi data
ll=unique(data(:,L));     %Saco las etiquetas unicas de mi data y, por tanto, las clases que tengo
C=numel(ll);              %Saco el numero de clases que tengo
NTr=round(.7*N);          %Obtengo el numero de filas de data que pertenecen al 70 per cent para mi set de entrenamieto
M=N-NTr;                  %Obtengo el numero de filas de data que perteneceran al 30 por ciento restante para validacion
te=data(NTr+1:N,:);       %obtengo las filas restantes que usaré para test
printf("#   a          b    E   k  Ete   Ete(%%)   Intervalo Confi.\n");
printf("#-------      ---- --- --- ---  --------  ----[s-----r]-----\n");
for a=as
  for b=bs
    [w,E,k]=perceptron(data(1:NTr,:),b,a);      %Obtengo w>>la matriz de pesos, E>> clasificaciones erroneas, k>>iteraciones hechas
    rl=zeros(M,1);                              %Declaro la matriz de comprobacion de pesos para los valores de validacion
    for n=1:M 
      
      rl(n)=ll(linmach(w,[1 te(n,1:D)]'));      %Obtengo la clase a la que me predice el perceptron los datos del test
    end
    [nerr nn]=confus(te(:,L),rl);               %Obtengo la presicion con la que me predijo el perceptron los datos del test
                                                %nerr muestras mal clasificadas, nn = matriz de clasificacion del confus
    
    etePerCent = nerr/M;                        %Estimacion empirica (decisiones erroneas),M = datos totales
    s = sqrt(etePerCent*(1-etePerCent)/M);      %Estimacion para el 95 por ciento
    r = 1.96*s;
    
    printf("%8.4f %8.4f %3d %3d %3d %8.4f [%.3f, %.3f]\n",a,b,E,k,nerr,etePerCent*100,(etePerCent - r)*100,(etePerCent +
    r) *100);
  end
end

%alpha = 0.1
%beta = 1000