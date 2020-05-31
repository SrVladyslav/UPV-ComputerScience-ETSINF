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
  +repartirPOS.

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


/** Planificacion defensa **/
+target_reached(F):hola
  <- 
  .print("Apatrullando la ciudad!").


+dejarMuni: team(200) & vigilantes(V) & not ayuda
  <-
  .nth(0, V, R);
  +repartir;
  .goto(R);
  .delF(V,W);
  .concat(W, [R], E);
  -+vigilantes(E);
  -dejarMuni.

+target_reached(T): team(200) & repartir & not ayuda
  <-
  .print("Toma muni");
  .reload;
  -repartir;
  +dejarMuni.


/** AL RESCATE **/
+sos([X,Y,Z], HEALTH)[soyurce(SS)]: team(200) & flag(F) & soldados(S)
  <-
  .focuseAT([X,Y,Z], flag(F), A);
  .send(S, tell, eliminarA(A));
  .send(S, tell, amenaza);
  +medicoH([X,Y,Z]);
  .print("Maten a ",A," cueste lo que cueste").

+medicoH([X,Y,Z]): myMedics(M)
  <-
  -medicoH;
  .send(M, tell, amenaza);
  .print("Medico!").

+medicPos(P)[source(S)]:flag(F)
  <-
  .next(P,F,D);
  .send(S, tell, mueveteA(D)).


/*
+friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): position(P)
  <-
  .distance(P, [X,Y,Z], D);
  .print("Distancia: ",D).
  */



/** ATAQUES**/
+sos[source(S)]
 <-
 .print("Dios te ayudara bro").