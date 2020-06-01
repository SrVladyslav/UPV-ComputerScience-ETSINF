//TEAM_AXIS
+flag ([X,Y,Z]): team(100)
  <-
  .goto([X-10,Y,Z-10]);
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
//TEAM DEFENSOR
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
  .goto(F);
  +check.

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
+target_reached(T): team(200) & alCentro & not laTorre & not modoWAR
  <-
  -alCentro;
  !apatrullar;
  .print("Apatrullando la ciudad!").

+!apatrullar: team(200) & flag(F) & position(P) & not modoWAR
 <-
 -+seguir;
 .next(P, F, D);
 .goto(D);
 .print("Voy a ", D).

+!apatrullar: amenaza & team(200) & not modoWAR
  <-
  .print("F").


+target_reached(T):team(200) & seguir & not modoWAR
  <-
  +comprobar;
  !apatrullar.

+target_reached(T):team(200) & modoWAR
  <-
  -amenaza(_);
  -eliminar;
  .print("FIN").

/** fin patrulla aleatoria**/


/** Patrulla mientras no vea un objetivo **/
+!apatrullandoLaCiudad:team(200) & not modoWAR
  <-
  +comprobar;
  .wait(2100);
  !apatrullandoLaCiudad.

+!apatrullandoLaCiudad:team(200) & modoWAR
  <-
  .print("Apatrullando la ciudad  F").

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
+enemies_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]):team(200) & not modoWAR & coronelli(C)
 <-
 +modoWAR;
 .goto([X,Y,Z]);
 -+tg(ID);
 .look_at([X,Y,Z]);
 .shoot(5,[X,Y,Z]);
 +comprobar;
 .send(C, tell, veteAlCentro([X,Y,Z]));
 .print("Entrando en combate!").
 /*.send(B, tell, sos([X,Y,Z], HEALTH)).*/



/** =========================================================MODO GUERRA =====================================================================***/

+enemies_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]):team(200) & modoWAR
 <-
  .look_at([X,Y,Z]);
  .shoot(1, [X,Y,Z]).

+enemies_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]):team(200) & modoWAR & position(P)
  <-
  .distance([X,Y,Z], P, D);
  if(D > 30){
    .goto([X,Y,Z]);
  };
  if(D < 20){
    .look_at([X,Y,Z]);
    .shoot(5, [X,Y,Z]);
  }.

/** COMPROBAR vida y municion **/
+check: team(200) & not modoWAR & ammo(A) & health(H) & not gallina & position(P)
  <-
  .wait(2000);
  -check;
  .print("Checking");
  if(H < 40 | A < 20){
    +reuniendose;
    .print("Saliendo por patas...");
    ?flag(F);
    .goto(F);
    ?coronelli(C);
    .send(C, tell, help(P));
  };
  +check.

+check: not position(P) <- -+check.

+reunion(P)[source(S)]: team(200)
  <-
  -+reuniendose;
  +gallina;
  .goto(P);
  .print("OK, voy para alla").

+target_reached(T):reuniendose
  <-
  -reuniendose;
  -gallina;
  +paquetes;
  +comprobar;
  .print("Estoy aqui").

+packs_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): paquetes
  <-
  -paquetes;
  +aporpaquete;
  .goto([X,Y,Z]);
  .wait(2000);
  .look_at([X,Y,Z]);
  .print("Vamos por paquete").

+target_reached(T): aporpaquete
  <-
  -aporpaquete;
  .print("Paquete cogido");
  +comprobar.


+friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]):team(200) & modoWAR & soldados(SS)
  <-
  .nth(ID, SS,S);
  .send(S, tell, ayudame([X,Y,Z]));
  .print("AYUDADME!!").

+ayudame([X,Y,Z])[source(S)]
 <-
 .look_at([X,Y,Z]);
 .goto([X,Y,Z]);
 -+modoWAR;
 .print("VOY!").







/** ===================================================================== **/
+ponerModoWar <- -+modoWAR; -ponerModoWar; .print("Entrando en combate!!!").