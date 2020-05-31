// agente que se limita a estar quieto e ir a curar
//TEAM_ALLIED 

+flag (F): team(200)
  <-
   .wait(3000);
   .get_service("coronel");
   .goto(F).
 
+target_reached(T):team(200) & not amenaza & coronelli(C) & position(P)
  <- 
  .cure;
  .send(C, tell, medicPos(P));
  .print("estoy en mi posicion").

+mueveteA(P)
  <-
  .goto(P).

+target_reached(T): amenaza
  <-
  .print("estoy en posicion").

+ayuda([X,Y,Z]): team(200) & position(P) & flag(F)
   <-
   .goto(F);
   .print("Voy a Flag").

+ayuda([X,Y,Z]): not position(P) | not flag(F)
  <-
  .print("F").

+coronel(C)
  <-
  +coronelli(C);
  .print("Hola coronel!").
