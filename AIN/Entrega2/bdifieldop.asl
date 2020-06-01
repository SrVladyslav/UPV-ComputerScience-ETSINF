//TEAM_AXIS

cont(0).
soldiers(0).
soldados([]).
i(0).

+flag (F): team(200)
  <-
  .register_service("coronel");
  .print("Empezando el Fieldop ");
  .look_at(F);
  .goto(F);
  .wait(5000);
  .get_backups;
  .get_medics;
  .wait(4000);
  +uno;
  +repartirPOS.

/** modo normal =============================================***/
/** Reparte 4 posiciones iniciales**/
+repartirPOS:team(200) & flag(F) & soldados(B)
  <-
  .print("Repartiendo posiciones: ",L);
  .defencePOS(F, Po);
  +vigilantes(Po);

  .wait(1000);
  -+i(0);
  while(i(I) & I < 4) {
    .nth(I,Po, P1);
    .print("Punto ",I, ": ", P1);

    .nth(I, B, Sold);
    .send(Sold, tell, moverA(P1));
    .print("Ahora",Sold," es torre.");
    .send(Sold, tell, laTorre);
    -+i(I+1);
  }
  /** Uno necesita estar en el centro siempre**/
  -repartirPOS;
  +dejarMuni;
  .print("Posiciones repartidas...").


+registrar[source(S)]:team(200) & soldados(SS)
  <-
  -registrar;
  .concat(SS, [S], L);
  -+soldados(L).



+dejarMuni: team(200) & vigilantes(V) & not modoWAR
  <-
  .nth(0, V, R);
  +repartir;
  .goto(R);
  .delF(V,W);
  .concat(W, [R], E);
  -+vigilantes(E);
  -dejarMuni.

+dejarMuni: team(200) & not modoWAR
 <-
 .goto(F);
 +repartir;
 -dejarMuni.

+target_reached(T): team(200) & repartir & not modoWAR
  <-
  .print("Toma muni");
  .reload;
  -repartir;
  +dejarMuni.


+myMedics(M) <- +medico(M).

/*** ========================================0 MODO GUERRA =================================***/
+veteAlCentro([X,Y,Z])[source(S)]:flag(F) & not modoWAR
  <-
  +modoWAR;
  +objetivo([X,Y,Z]);
  .goto(F);
  .print("[Coronel]: Voy al centro!!!").

+target_reached(T): team(200) & modoWAR & objetivo([X,Y,Z]) & medico(M)
  <-
  .look_at([X,Y,Z]);
  .print("Estoy en el centro");
  .send(M, tell, venAlCentro);
  .reload;
  +crearMUNICION.

+crearMUNICION
  <-
  .wait(4000);
  -+crearMUNICION.

+help(P)[source(S)]: not ayudando & position(PP)
  <-
  +ayudando;
  .distMedia(P, PP, D);
  .send(S, tell, reunion(D));
  .print("Apunta punto de reunion ").


+help(P)[source(S)]: ayudando & flag(F)
  <-
  .send(S, tell, reunion(F));
  .print("Ahora no puedo ayudarte").


+enemies_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]):team(200) & modoWAR
 <-
  .look_at([X,Y,Z]);
  .shoot(5, [X,Y,Z]).

































/** AL RESCATE 
+sos([X,Y,Z], HEALTH)[source(SS)]: team(200) & soldados(S) & uno
  <-
  -uno;
  +modoWAR;
  .get_service("cruzada");
  .wait(2000);

  .length(S, L);
  .soldiers(LL);
  if(L == LL ){
  .send(S, tell, eliminarA([X,Y,Z]));
  .send(S, tell, ponerModoWAR);
  /*}else{
    .print("A morir");
  }
  +medicoH([X,Y,Z]);
  .print("Maten a ",A," cueste lo que cueste").

+cruzada(C)
  <-
  -+soldados(C).

+sos([X,Y,Z], HEALTH)[source(SS)]: team(200) & (not soldados(S) | not uno)
  <-
  .print("No fui rapido").

+medicoH([X,Y,Z]): myMedics(M)
  <-
  -medicoH;
  .send(M, achieve, ponerModoWAR);
  .print("Medico!").

+medicPos(P)[source(S)]:flag(F)
  <-
  .print("Moviendo medico");
  .next(P,F,D);
  .send(S, tell, mueveteA(D)).

+ayudame([X,Y,Z])[source(S)]
 <-
 .look_at([X,Y,Z]);
 .goto([X,Y,Z]);
 .print("VOY!").
/*
+friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): position(P)
  <-
  .distance(P, [X,Y,Z], D);
  .print("Distancia: ",D).
  *



** ATAQUES**
+sos[source(S)]
 <-
 .print("Dios te ayudara bro").



+dameMuni(P)[source(S)]
  <-
  .stop;
  .goto(P).
  */