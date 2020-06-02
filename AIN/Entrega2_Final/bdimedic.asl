// agente que se limita a estar quieto e ir a curar
//TEAM DEFENSOR 
/** Guarda la flag en una variable para que no explote por concurrencia
y tambien localiza al coronel **/
+flag (F): team(200)
  <-
   +f(F);
   .wait(3000);
   .get_service("coronel");
   .goto(F).
 
/** Cuando llega al centro, empieza a generar paquetes **/
+target_reached(T):team(200)
  <- 
  .cure;
  .print("estoy en mi posicion").

/** Se pueden darle ordenes al medico para que vaya a un sitio,
solo usamos el ir al centro **/
+mueveteA(P)
  <-
  .goto(P).

/** Cuando aparece el coronel, lo guarda en una variable más fiable
para que en un futuro no explote **/
+coronel(C)
  <-
  +coronelli(C);
  .print("Hola coronel!").

/** Da vueltas para comprobar por si hay alguien **/
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

/** Va al centro cuando lo llaman y activa modoWAR**/
+venAlCentro:f(F)
  <-
  +modoWAR;
  .goto(F);
  .print("Entendido");
  .look_at(F).

/** Cuando esta en el centro, empieza a generar botiquines **/
+target_reached(T): modoWAR
 <-
 .cure;
 +crearPaquetes;
 +comprobar.

/** empieza a crear botiquines sin parar cada 4 segundos **/
+crearPaquetes
  <-
  .cure;
  .wait(4000);
  -+crearPaquetes.
