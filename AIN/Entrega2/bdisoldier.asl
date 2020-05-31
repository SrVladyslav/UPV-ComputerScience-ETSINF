//TEAM_AXIS
+flag (F): team(100)
  <-
  .goto(F);
  .print("Ready to die!").
  /*.focusedAT(FA);
  .print("Focused: ",FA);
  .create_control_points(F,100, 1, CP);
  .nth(0, CP, CPP);
  .canGO(CPP, K);
  .print("Can go : ", CPP, "?? > ", K);
  
  .tryGO(CPP,F,O);
  .goto(O);
  .print("OOO: ", O).*/
  /*
  .tryGO([130,0,130],F);
  .print("A Berlin!!!");
  .tryGO([50,0,50], F).*/



//==================================================================================================================================================0
//TEAM ALIED
+flag (F):team(200)
  <-
  /*.print("Ready to WIN!");
  Miro si hay alguien que no es capitan **/
  .get_backups;
  .get_medics;
  .register_service("cruzada");
  .wait(5000);
  .get_service("coronel");
  .wait(1500);
  .print("Fiesta Fiesta ole!");
  +alCentro;
  +objetivo(F);
  .goto(F).

+coronel(C): team(200) 
  <-
  +coronelli(C);
  .send(C, tell, registrar).

/** Ordenes de movimiento **/
+moverA(Punto)[source(S)]: team(200)
  <-
  .goto(Punto);
  +punto;
  .print("Entendido, moviendome al punto ", Punto, "!").

/** Target reached **/
+target_reached(F): punto
  <-
  -punto;
  !apatrullandoLaCiudad;
  .print("Vigilando la ciudad!").

/** Los que no son torre, patrullan alrededor**/
+target_reached(T):team(200)& alCentro & not laTorre & coronelli(C) & flag(F)
  <-
  -alCentro;
  !apatrullar;
  .print("Apatrullando la ciudad!").

+!apatrullar: not amenaza & team(200) & flag(F) & position(P)
 <-
 -+seguir;
 .next(P, F, D);
 .goto(D);
 .print("Voy a ", D).

+target_reached(T):team(200) & seguir
  <-
  .print("todo ok");
  +comprobar;
  !apatrullar.

/** fin patrulla aleatoria**/


/** Patrulla mientras no vea un objetivo **/
+!apatrullandoLaCiudad:team(200) & not amenaza
  <-
  +comprobar;
  .wait(2100);
  !apatrullandoLaCiudad.

+laTorre <- .print("UPS").

+comprobar: team(200) & position([X,Y,Z])
  <-
  .look_at([X+1,Y,Z]);
  .wait(400);
  .look_at([X-1,Y,Z]);
  .wait(400);
  .look_at([X,Y,Z+1]);
  .wait(400);
  .look_at([X,Y,Z-1]);
  .wait(400);
  ?objetivo(O);
  .look_at(O);
  -comprobar.



/** Enemigos, ataques, y esas cosas **/
+enemies_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]):team(200) & coronelli(C) & myBackups(B)
 <-
 +amenaza;
 .look_at([X,Y,Z]);
 .shoot(5,[X,Y,Z]);
 .send(C, tell, sos([X,Y,Z], HEALTH));
 .send(B, tell, sos([X,Y,Z], HEALTH)).

+sos([X,Y,Z], HEALTH)[source(S)]
 <-
 +amenaza;
 .look_at([X,Y,Z]);
 .goto([X,Y,Z]).

+eliminarA(A)[source(S)]: team(200)
  <-
  .look_at(A);
  .goto(A).

+friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]):team(200) & amenaza & flag(F)
 <-
 .distance([X,Y,Z], F, D);
 if(D < 35) {
    .send(ID, achieve, quitate);
 }
 .turn(-ANGLE).

+quitate[source(S)]:flag(F)
  <-
  .goto(F).