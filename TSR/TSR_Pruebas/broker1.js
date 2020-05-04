const zmq = require('zeromq')
let cli=[], req=[]
let sc = zmq.socket('router') // frontend
let sw = zmq.socket('dealer') // backend broker2
let st = zmq.socket('dealer') // backend broker2 mensajes
let contadorWorkersLibres = 0

var totalPeticionesAtendidas = 0
var cont = []

sc.bind('tcp://*:97990') //clientes
sw.bind('tcp://*:97991') //broker2
st.bind('tcp://*:97993') //broker2 time

sc.on('message',(c,sep,m)=> {
	totalPeticionesAtendidas++
	if (contadorWorkersLibres <= 0) { 
		cli.push(c); req.push(m)
	} else {	
		console.log("CLIENTE: " +c + " HA LLEGADO desde el frontend")
		console.log("--c: "+c+"--sep: "+sep + "--msg: "+m)
		sw.send(['',c,'', m])
		//sw.send(['',cli.shift(),'', req.shift()]) //mandar el cliente con su respectivo curro a hacer
		//sw.send([cli.shift(),'',c,'',m]) //COMPROBAR QUE ES LO UQE TENEMOS QUE MANDAR, WORKER O CLIENT
	}
})

//compruebo si hay workers disponibles y tareas a hacer, y mandaré los de la cola
setInterval(()=>{
	if(contadorWorkersLibres > 0 && cli.length > 0){
		sw.send(['',cli.shift(),'', req.shift()])
	}
},5)

//Metodo encargado de recibir el trabajo procesado desde el broker 2
sw.on('message',(c,sep2,response)=> {
	console.log("[broker 1] CLIENTE: " +c + " HA LLEGADO de tipo : "+ typeof(c))
	sc.send([c,'',response]) //manda al respectivo cliente su peticion procesada
})


//recibe la cantidad de workers libres cubanos
st.on('message',(workers)=> {
	contadorWorkersLibres = workers 
})

//mando la canitdad de clientes que tengo
setInterval(()=>{
	st.send(cli.length)
	console.log("Clientes:"+cli.length +"  Libres: " + contadorWorkersLibres) 
},1000)



//los ovnis existen
setInterval(()=>{
	console.log("Total peticiones atendidas: " + totalPeticionesAtendidas)
	cont.forEach((i)=>{
		console.log("Workers: "+ cont[i])
	})
},5000)



//nunca podemos hacer, pensar que el otro lo averiguará
//llega una peticion de un cliente, cuando se si tengo que encolarla o no=??, porque acaba de encolar, la puedo encolar si hay trabajadores libres?
//saber si hay que encolarlo o no requiere que sepa si hay trabajadores libres


//en nroker dos, si llega un trabajo, no puedo mandar a un trabajador a descansar, entonces los brokers deberian mandar las colas suyas.
