// agente que se limita a estar quieto e ir a curar
//TEAM_ALLIED 

+flag (F): team(200)
  <-
   +f(F);
   .wait(3000);
   .get_service("coronel");
   .goto(F).
 
+target_reached(T):team(200)
  <- 
  .cure;
  .print("estoy en mi posicion").

+mueveteA(P)
  <-
  .goto(P).

+coronel(C)
  <-
  +coronelli(C);
  .print("Hola coronel!").


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

+venAlCentro:f(F)
  <-
  +modoWAR;
  .goto(F);
  .print("Entendido");
  .look_at(F).

+target_reached(T): modoWAR
 <-
 .cure;
 +crearPaquetes;
 +comprobar.

+crearPaquetes
  <-
  .cure;
  .wait(4000);
  -+crearPaquetes.
