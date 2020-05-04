
;###########################################################

;Práctica hecha por Vladyslav Mazurkevych y Carlos Gálvez

;###########################################################



(deffacts datos
    ;VARIABLES ESTATICAS
    ;tipos de vagon tipo pesoMin pesoMax
    ;solucion sale a nivel 22

    ;tipos de vagon tipo pesoMin pesoMax
    (tipo T1 0 15);
    (tipo T2 16 23)

    ;informacion maletas
    ;(maleta Nombre peso origen destino)
    ;VARIABLES DINAMICAS
    (maleta M1 12 Fact P3)
    (maleta M2 18 Fact P5)
    (maleta M3 20 P1 Rec)
    (maleta M4 14 P6 Rec)
    ;(maleta M1 12 Fact P3)
    ;(maleta M3 10 Fact P6)
    ;(maleta M5 15 P1 Rec)
    ;(maleta M11 14 P2 Rec)

    ;para tener mas vagon, usar nmVag en tren

    ;(tren P6 vagon T1 P6 1 T2 P2 0 maletas M1 0 M3 0 M5 0 M11 0)
)

;contador de nodos generados
(defglobal ?*nodosgen* = 0)

(deffacts mapa
        (enlace P2 Fact)
        (enlace P2 P4)
        (enlace P4 P3)
        (enlace P4 P2)
        (enlace P3 P1)
        (enlace P3 P4)
      	(enlace P1 Fact)
   	    (enlace P1 P5)
        (enlace P1 P3)
        (enlace Fact P2)
        (enlace Fact P1)
        (enlace P5 Rec)
        (enlace P5 P7)
        (enlace P5 P1)
        (enlace P7 P8)
        (enlace P7 P5)
        (enlace P8 P6)
        (enlace P8 P7)
        (enlace P6 Rec)
        (enlace P6 P8)
        (enlace Rec P5)
        (enlace Rec P6)
)
(deffunction inicio ()
	(reset)
	(printout t "Profundidad Maxima:= " )
	(bind ?userDepth (read))
	(printout t "Tipo de Busqueda " crlf "1.- Anchura" crlf  "2.- Profundidad" crlf )
	(bind ?a (read))
	(if (= ?a 1)
 		then (set-strategy breadth)
 		else (set-strategy depth));)
    (printout t " Ejecuta run para poner en marcha el programa " crlf)
	(assert (tren P6 1 vagon V1 T1 P6 1 0 0 vagon V2 T2 P2 0 0 0 maletas maleta M1 0 maleta M3 0 maleta M5 0 maleta M11 0 nivel 0 entregadas))
	(assert (max-depth ?userDepth))
)

(defrule final
	(declare (salience 150))
  (max-depth ?userDepth)
	?f <- (tren $?vt nivel ?nivel entregadas $?entr)
	      (max-depth ?userDepth)
	(test (< ?nivel ?userDepth))
  (test (= (length$ $?entr) 4))

    =>
	(printout t  "SOLUCION ENCONTRADA EN EL NIVEL " ?nivel  crlf)
	(printout t  "NUM DE NODOS EXPANDIDOS O REGLAS DISPARADAS " ?*nodosgen* crlf)
	(printout t  "HECHO OBJETIVO " ?f crlf)
	(halt)
)

(defrule noSolucion
	(declare (salience -50))
	?f <- (tren ? ? vagon $?v maletas $?vt2 maleta $?m nivel ?nivel entregadas $?entr)
	      (max-depth ?userDepth)
     =>
	(printout t  "SOLUCION NO ENCONTRADA EN EL NIVEL " ?nivel crlf)
	(printout t  "NUM DE NODOS EXPANDIDOS O REGLAS DISPARADAS " ?*nodosgen*  crlf)
	(halt)
)

;tren posTren numVagones vagon id tipoVagon ubi enganchado numMaletas maleta nombre cargada entregada nivel
                                                                                              ;si -> 1
                                                                                              ;no -> 0
(defrule cargarMaleta
    (max-depth ?userDepth)
   	(tren ?pos ?numVagones $?vt vagon ?vagon ?tipoVagon ?p 1 ?numMaletas $?vt1 maletas $?vt2 maleta ?maleta 0 $?c3 nivel ?nivel entregadas $?entr)
   	(maleta ?maleta ?peso ?pos ?)
   	(tipo ?tipoVagon ?min ?max)
	  (test (< ?peso ?max))
	  (test (> ?peso ?min))
    (test (< ?nivel ?userDepth))
    (test (> ?numVagones 0))
    (test (eq (member$ ?maleta $?entr) FALSE))
	=>
	(assert (tren ?pos ?numVagones $?vt vagon ?vagon ?tipoVagon ?p 1 (+ ?numMaletas 1) $?vt1 maletas $?vt2 maleta ?maleta 1 $?c3 nivel (+ ?nivel 1) entregadas $?entr))
  (bind ?*nodosgen* (+ ?*nodosgen* 1))
)

(defrule descargarMaleta
   (max-depth ?userDepth)
   (tren ?pos ?numVagones $?vt vagon ?vagon ?tipoVagon ?p 1 ?numMaletas $?vt1 maletas $?vt2 maleta ?maleta 1 $?c3 nivel ?nivel entregadas $?entr)
   (maleta ?maleta ? ? ?pos)
   (test (> ?numMaletas 0))
   (test (< ?nivel ?userDepth))
   (test (eq (member$ ?maleta $?entr) FALSE))
    ;Movidas con nombre y tipos de vagon
	=>
	(assert (tren ?pos ?numVagones $?vt vagon ?vagon ?tipoVagon ?p 1 (- ?numMaletas 1) $?vt1 maletas $?vt2 maleta ?maleta 0 $?c3 nivel (+ ?nivel 1) entregadas $?entr ?maleta))
  (bind ?*nodosgen* (+ ?*nodosgen* 1))
)


;tren posTren numVagones vagon id tipoVagon ubi enganchado numMaletas maleta nombre cargada entregada nivel
                                                                                              ;si -> se
                                                                                              ;no -> ne

(defrule engancharVagon
   (max-depth ?userDepth)
   (tren ?pos ?numVagones $?vt vagon ?vagon ?tipoVagon ?pos 0 ?numMaletas $?vt1 maletas $?vt2 maleta $?c3 nivel ?nivel entregadas $?entr)
	 (test (< ?nivel ?userDepth))
   (test (= 0 ?numVagones)) ;Comprueba que no hay ningún vagón enganchado al tren
   (test (= 0 ?numMaletas));OJO CONDICION DEL ENUNCIADO PERO PUEDE SER QUE PETE POR ESO
	=>
	(assert (tren ?pos (+ ?numVagones 1) $?vt vagon ?vagon ?tipoVagon ?pos 1 ?numMaletas $?vt1 maletas $?vt2 maleta $?c3 nivel (+ ?nivel 1) entregadas $?entr))
	(bind ?*nodosgen* (+ ?*nodosgen* 1))
)

(defrule desengancharVagon
   (max-depth ?userDepth)
   (tren ?pos ?numVagones $?vt vagon ?vagon ?tipoVagon ?p 1 ?numMaletas $?vt1 maletas $?vt2 maleta $?c3 nivel ?nivel entregadas $?entr)
   (test (> ?numVagones 0))
    (test (= ?numMaletas 0))
    (test (< ?nivel ?userDepth))
	=>
	(assert (tren ?pos (- ?numVagones 1) $?vt vagon ?vagon ?tipoVagon ?pos 0 ?numMaletas $?vt1 maletas $?vt2 maleta $?c3 nivel (+ ?nivel 1) entregadas $?entr))
  (bind ?*nodosgen* (+ ?*nodosgen* 1))
)

;usar contadores para vagon, cuantas maletas lleva cada vagon?


(defrule moverMaquina
    (max-depth ?userDepth)
    (tren ?origen ?numVagones $?vt vagon $?v maletas $?vt2 maleta $?m nivel ?nivel entregadas $?entr)
    (enlace ?origen ?destino)
    (test (< ?nivel ?userDepth))
       =>
	  (assert (tren ?destino ?numVagones $?vt vagon $?v maletas $?vt2 maleta $?m nivel (+ ?nivel 1) entregadas $?entr))
    (bind ?*nodosgen* (+ ?*nodosgen* 1))
)
;tren posTren numVagones vagon id tipoVagon ubi enganchado numMaletas maleta nombre cargada entregada nivel
                                                                                              ;si -> se
                                                                                              ;no -> ne
