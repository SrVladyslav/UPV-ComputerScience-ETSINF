/** Version 11.A: PIENSA MAS O MENOS BIEN Y MATA**/
/** Creencias iniciales **/
contAmenaza(0).
inconciente(0).
/*contAmenaza(-4).*/
cuadrante([20,0,20],[240,0,0],[240,0,240],[0,0,240]). /** Puntos para huir**/

/**
Estado inicial, guarda la posicion inicial y la vida por si acaso xD, 
quien sabe, alomejor se necesitara en un futuro
**/
@a[atomic]
+flag(F): team(200)
	<-
	/**Inicio**/
	+objetivo(F);
	.print("La flag está en: ",F);
	.look_at(F);
	?position([X,Y,Z]);
	!!comprobar;

	/** Control points para el final **/
	.create_control_points(F,50,15,C);
	+control_points([F|C]);
	.length(C,L);
	+sitios(L);
	+patroll_point(0);

	?health(V);
	+vidaAgente(V);

	/**Inicio los Deamons**/
	!vida;
	.print("POR LA HORDA!!!").

+patroll_point(P): sitios(T) & P<T
  <-
  ?control_points(C);
  .nth(P,C,A);
  .goto(A).

+patroll_point(P): sitios(T) & P==T
  <-
  -patroll_point(P);
  +patroll_point(0).


/**
==========================================================================
1-COMPROBAR: Distintas comprobaciones usadas en la humilde vida del agente
==========================================================================
**/
/** Me sirve para detectar de si hay alguien disparandome cuando estoy en pacifico **/
+vida: health(H)
	<-
	-vida;
	if(H < 10){
		.print("HUYENDO... ");
	};
	+vida.


+!vida: health(H) & ammo(A)
	<-
	?vidaAgente(VA);
	if(VA > H & not amenaza){
		/** Alguien me está pegando [source(self)]**/
		.print("Alguien me está pegando!");
		?inconciente(I);
		if(I > 5){
			-inconciente(_);
			+inconciente(0);
			.print("DANDO GIROS DE PANICO");
			/*.stop;
			!!comprobar;*/
		}else{
			-+inconciente(I+1);
		}		
		.stop;
		!!comprobar;
		?objetivo(OBJ);
		.goto(OBJ);
		.look_at(OBJ);
		.wait(500);
	};
	/** Multiple focus **/
	if(VA-10 > H & not amenaza) {
		+huir;
		.print("Nos están pegando varios...");
	}

	if(VA = H){
		?contAmenaza(AGRO);
		+probarAmenaza(VA);
		if(not paquete | not porObjetivo){
			-contAmenaza(_);
			+contAmenaza(AGRO+1);
		}
		.print("AGRO: ",AGRO);
		if(AGRO > 1 & H < 80 & not amenaza){
			.print("Tengo poca vida, voy a curarme");
			?flag(F);
			/*.stop;*/
			if(salud(_)){
				.print("Vamos a por botiquin");
				?salud(BO);
				.goto(BO);
				.look_at(BO);
				+aCura;
			}else{
				+cura;
				.goto(F);
				.look_at(F);
				+porObjetivo;
			}
			-contAmenaza(_);
			+contAmenaza(0);
		}else { /**else if ni elif funcionan*/
			if(AGRO > 1 & not amenaza){
				-contAmenaza(_);
				+contAmenaza(0);
				.print("Tengo algo de vida, asi que vamos a buscar presas");
				if(A < 70 & municion){
					+porObjetivo;
					?municion(MUNI);
					.goto(MUNI);
					.look_at(MUNI);
					.print("Nos vamos a por municion");
				}else{
					!darVueltas;
				}
			}
		}

	}
	/*.wait(100);*/
	-vidaAgente(_);
	+vidaAgente(H);
	!!vida.

+probarAmenaza(VA)
	<-
	-probarAmenaza;
	.wait(1500);
	?health(H);
	if(VA = H & amenaza){
		-amenaza;
		.print("======================NO AMENAZA==================");
	}.

+probarAmenaza(VA): not health(H)
	<-
	-+probarAmenaza(VA);
	.print("No posicion").

/** Por si no hay nada que hacer**/
+!darVueltas
	<-
	+porObjetivo;
	if(not amenaza){
		?patroll_point(P);
  		-+patroll_point(P+1);
	}else{
		.print("UPS");
	}.

/** Por si se buguea y explota el health **/
+!vida: not health(H)
	<- .print("UPS");!!vida.


/** Gira en los 4 sentidos y espera 100ms por si detecta a alguien **/
@a[atomic]
+!comprobar: position([X,Y,Z])
	<-
  	/** AQUI A VECES EXPLOTA, CON ATOMIC PARECE QUE VA **/
	.look_at([X+1,Y,Z]);
	.wait(1000);
	.print("S");

	.look_at([X-1,Y,Z]);
	.wait(1000);
	.print("N");

	.look_at([X,Y,Z+1]);
	.wait(1000);
	.print("E");

	.look_at([X,Y,Z-1]);
	.wait(1000);
	.print("O");

  	/** Enfoco hacia el objetivo a donde voy**/
	?objetivo([Xx,Yy,Zz]);
	.look_at([Xx,Yy,Zz]).

@a[atomic]
+!comprobar: not position([X,Y,Z])
	<-	
	.print("Comprobando esquinas");
	/** En caso de que explote la position, comprobaremos con las esquinas, es un PLAN B**/
	.look_at([10,0,10]);
	.wait(1000);

	.look_at([250,0,10]);
	.wait(1000);

	.look_at([10,0,250]);
	.wait(1000);

	.look_at([250,0,250]);
	.wait(1000);

  	/** Enfoco hacia el objetivo a donde voy**/
	?objetivo([Xx,Yy,Zz]);
	.look_at([Xx,Yy,Zz]).



/**
==========================================================================
2-PENSAR: Peleará en la batalla
==========================================================================
**/

+friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X ,Y,Z]): not cura & health(Xh) & position([A,B,C]) & ammo(AA) & not disparando
	<-
	-+porObjetivo;
	+disparando;
	+amenaza;
	-contAmenaza(_);
	+contAmenaza(0);
	if(quitarAmenaza){
		-quitarAmenaza;
	};
	.print("Municion: ",AA);
	if(AA < 3){
		.print("[ NO TENGO MUNICION, VOY A LA F]");
		if(municion(_)){
			?municion(MM);
			.goto(MM);
			.look_at(MM);
			+porObjetivo;
			+aCura;
		}else{
			?flag(F);
			/**.stop;**/
			+cura;
			.goto(F);
			.look_at(F);
			+porObjetivo;
		};
	}else{
		.print("Enemigo encontrado");
		-objetivo([_,_,_]);
		+objetivo([X,Y,Z]);
		.goto([X,Y,Z]);
		.look_at([X,Y,Z]);
		.shoot(5,[X,Y,Z]);
		if(HEALTH <= Xh){
			.print("A la carga!!!!");
			/** Obtengo la distancia **/
			+distE(((X-A)**2+(X-C)**2)**(0.5));
			?distE(DE);
			-distE(_);
			.print("Esta a distancia: ", DE);
			if(DE < 30) {
				/**.stop;**/
				.goto([X,Y,Z]);
				.shoot(20,[X,Y,Z]);
				.look_at([X,Y,Z]);
				.print("Disparando 20");
			}
		};
	};
	-disparando;
	-friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X ,Y,Z]).
	/**-friends_in_fov(_, _, _, _, _, [_,_,_]).**/

+friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X ,Y,Z]): not cura & not health(vida)
	<-
	-+porObjetivo;
	-objetivo([_,_,_]);
	+objetivo([X,Y,Z]);
	.goto([X,Y,Z]);
	.look_at([X,Y,Z]);
	-contAmenaza(_);
	+contAmenaza(0);
	/**-friends_in_fov(_, _, _, _, _, [_,_,_]);**/
	-friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X ,Y,Z]);
	.print("No obtengo la vida").


+friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X ,Y,Z]): not aCura
	<-
	-+porObjetivo;
	-contAmenaza(_);
	+contAmenaza(0);
	-objetivo([_,_,_]);
	+objetivo([X,Y,Z]);
	.look_at([X,Y,Z]);
	-friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X ,Y,Z]).


/**Simplemente lo usaré para quitar la amenaza**/
-friends_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X ,Y,Z]): not quitarAmenaza
	<-
	+quitarAmenaza;
	.wait(3000);
	if(quitarAmenaza){
		/**-amenaza;
		.print("Quitando la Amenaza, stay home 4 more...");**/
	}.


/** Listeners de haber llegado al centro para empezar a buscar paquetes o no **/
+target_reached([X, Y, Z]): cura 
	<-
	.print("Llegue al centro");
	-cura;
	?health(HH);
	+aCura;
	!!comprobar;
	-target_reached([X, Y, Z]).

/** Si hay paquetes en el campo de vision va por ellos aunque muera**/
+packs_in_fov(ID,Type,Angle,Distance,Health,Position): (Type = 1001 | Type = 1002) & aCura
	<-
	if(Type = 1002){
		-municion(_);
		+municion(Position);
		.print("[ Paquete de municion a la vista ]");
	};
	if(Type = 1001){
		-salud(_);
		+salud(Position);
		.print("[ Botiquin a la vista ]");
	}
	-+objetivo(Position);
  	.goto(Position);
	.look_at(Position);
  	-aCura;
	+paquete;
	-packs_in_fov(ID,Type,Angle,Distance,Health,Position);
  	.print("Vamos a curarnos cueste lo que cueste!").



/** Si hay paquetes en el campo de vision, los guardo, por si acaso**/
+packs_in_fov(ID,Type,Angle,Distance,Health,Position): (Type = 1001 | Type = 1002) & not aCura
	<-
	if(Type = 1002){
		-municion(_);
		+municion(Position);
	};
	if(Type = 1001){
		-salud(_);
		+salud(Position);
	};
	-packs_in_fov(ID,Type,Angle,Distance,Health,Position).

+target_reached(Position): paquete & health(H)
	<-
	-paquete;
	.print("Llego al Paquete");
	!!comprobar;
	-packs_in_fov(_,_,_,_,_,_);
	.wait(2500);
	if(not amenaza & H < 50){
		.print("Necesito mas cosas");
		+aCura;
	};
	-target_reached(Position).


/** Debugguer**/
+target_reached(Position)
	<-
	/** Si llega a un objetivo, permitirá debuguer la posicion, por si está buscando un jugador**/
	-porObjetivo;
	-target_reached(Position);
	.print("Llegue a la posicion WEY").

/**
==========================================================================
METODOS UTILES 
==========================================================================
**/

/** Calcula la distancia Euclidea entre los puntos Ax Ay y Bx By y lo 
guarda como una creencia distE**/
+!dameDistancia(Bx,By)[source(self)]:position([Ax,Ay])
	<-
    +distE(((Bx-Ax)**2+(By-Ay)**2)**(0.5));
	.print("Calculado distancia Euclidea").


/** Se mueve en una posicion cercana random y compruba si hay alguien**/
@alabel[atomic]
+!movimientoRandom: position(P)
	<-
	?flag(F);
	.create_control_points(P,20,1,C);
	-+control_points(C);
	.print("Creando punto nuevo...");
	-+patrolling;
	-nth(0,C,A);
	.stop;
	+porObjetivo;
	.goto(A);
	.look_at(A).


/** Si la posicion explota, ira a una esquina**/
+!movimientoRandom: not position(P)
	<-
	.print("Vamos a buscar a los camperos por esquinas... ");
	.goto([20,0,20]);
	.look_at([20,0,20]).

+huir: position([X,Y,Z])
	<-
	-huir;
	/** Hará una media entre los últimos enemigos vistos y correrá en el sentido contrario 
	equitativo a estos, por si se trata de un 1 VS varios **/
	?enemigos([[X1,Z1],[X2,Z2]]);
	/** Calculo la media para saber a donde huir **/
	
	
	.print("HUYENDDOOO");
	?flag(F);
	+porObjetivo;
	.goto(F);
	.look_at(F).

+huir: not postion([X,Y,Z])
	<-
	-huir;
	.print("No hay pocision...").