//TEAM_DEFENSOR

cont(0).
soldiers(0).
soldados([]).
i(0).

/** Creencias iniciales, se obtienen los médicos y los soldados,
tambien se inicia la reparticion de posiciones **/
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
/** Reparte 4 posiciones iniciales entre los 4 primeros agentes registrados**/
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
  -repartirPOS;
  +dejarMuni;
  .print("Posiciones repartidas...").

/** Cuando un agente se conecta, este se registra en la lista de fieldop para
su posterior posible uso **/
+registrar[source(S)]:team(200) & soldados(SS)
  <-
  -registrar;
  .concat(SS, [S], L);
  -+soldados(L).


/** Se obtiene la posicion de cada vigilante, y si no hay amenaza, el fieldop
ira dando vueltas de uno en uno dejandoles la municion al lado, por si en caso de 
una emboscada, puedan tener munición rapidamente a su alcance y no perder tiempo 
comunicandose con otros **/
+dejarMuni: team(200) & vigilantes(V) & not modoWAR
  <-
  .nth(0, V, R);
  +repartir;
  .goto(R);
  .delF(V,W);
  .concat(W, [R], E);
  -+vigilantes(E);
  -dejarMuni.

/** En caso de que algo falle, ira a parar en el centro **/
+dejarMuni: team(200) & not modoWAR
 <-
 .goto(F);
 +repartir;
 -dejarMuni.

/** Cuando llegue a su objetivo para dejar la munición, generará un paquete
y seguirá al sigueinte putno de reencuentro mientras no se encuentre en amenaza
 **/
+target_reached(T): team(200) & repartir & not modoWAR
  <-
  .print("Toma muni");
  .reload;
  -repartir;
  +dejarMuni.

/** Cuando se carguen los medicos, los guarda en una variable a parte para
poder usarlo en caso de emergencia **/
+myMedics(M) <- +medico(M).

/*** ======================================== MODO GUERRA =================================***/
/** En caso de amenaza, el fieldop se irá al centro, ya que como no podemos seguir la flag
nos jugamos la partida a no dejar que pase nadie al centro,y se quedará campeando  **/
+veteAlCentro([X,Y,Z])[source(S)]:flag(F) & not modoWAR
  <-
  +modoWAR;
  +objetivo([X,Y,Z]);
  .goto(F);
  .print("[Coronel]: Voy al centro!!!").

/** Cuando llega al centro, empieza a generar paquetes de municion y llama al
medico para que venga al centro a gnerar tambien paquetes**/
+target_reached(T): team(200) & modoWAR & objetivo([X,Y,Z]) & medico(M)
  <-
  .look_at([X,Y,Z]);
  .print("Estoy en el centro");
  .send(M, tell, venAlCentro);
  .reload;
  +crearMUNICION.

/** Cuando llega al punto de reencuentro con un agente que lo pidio, 
resetea e uso de ayuda para que otro agente pueda llamarlo **/
+target_reached(T): ayudando
  <-
  -ayudando.

/** Crea municion cada 4 segundos, así pues, siempre habrán paquetes por el campo **/
+crearMUNICION
  <-
  .wait(4000);
  -+crearMUNICION.

/** Cuando un agente pide ayuda, se decide una posicion donde quedar, 
que será el punto medio entre los dos **/
+help(P)[source(S)]: not ayudando & position(PP)
  <-
  +ayudando;
  .distMedia(P, PP, D);
  .send(S, tell, reunion(D));
  .print("Apunta punto de reunion ").

/** Debugger para position, ya que suele fallar, con lo que mandará al agente al centro 
Si flag falla, no habrá ayuda para el soldado**/
+help(P)[source(S)]: ayudando & flag(F)
  <-
  .send(S, tell, reunion(F));
  .print("Ahora no puedo ayudarte").

/** Debugguer para flag **/
+help(P)[source(S)]: team(200) & not flag(F)
  <-
  -ayudando.

/** El fieldop tambien dispara, si ve a algun enemigo en campo de vision, le diparara **/
+enemies_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]):team(200) & modoWAR
 <-
  .look_at([X,Y,Z]);
  .shoot(5, [X,Y,Z]).







