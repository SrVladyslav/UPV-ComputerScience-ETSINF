const zmq = require('zeromq')
const TTL = 2000
let req=[], workers=[]
let sc = zmq.socket('dealer') // backend broker 1
let st = zmq.socket('dealer') // backend broker 1 clients
let sw = zmq.socket('router') // frontend workers
let contadorClientesLibres = 0
let fallo = [] //guardara el request para cada worker
let timeOut = [] //los workers que tienen que devolver aun cosas
var totalPeticionesAtendidas = 0
var cont = []

sc.connect('tcp://localhost:97991') //broker1
st.connect('tcp://localhost:97993') //broker1 time and clients
sw.bind('tcp://*:97992') //workers

function resend (w, c, m){
	return function() {
		fallo[w] = true
		if(workers.length > 0){
			var w = workers.shift()
			timeOut[w] = setTimeont(resend)
			sw.send([w,'',c,'',request])
		}
	}
}

//recibir la informacion del broker 1 y distribuirla al primer worker libre
sc.on('message', (sep,c, sep2, request)=>{
	if(workers.length > 0 ){
		console.log("Cliente:" + c +"Ha llegado vivo")
		var w = workers.shift()
		timeOut[w] = setTimeout(resend)
		sw.send([w,'',c,'',request])
	}
})

st.on('message', (m)=>{//console.log(m)
})

//recibir respuesta del worker que ejecuto el trabajo y reenviarla al broker 1
sw.on('message', (w,sep,c,sep2,response)=>{
	console.log("w: "+w+"--sep: "+sep+"--c: "+c+"--sep2: "+sep2 + "--response: "+response)
	if(fallo[w]) return
	totalPeticionesAtendidas++
	if(timeOut[w]){
		clearTimeout(timeOut[w])
		delete timeOut[w]
	}
	if(c==''){
		workers.push(w)
		console.log("NUEVO WORKER SE CONCETO WEY")
		return;
	}else{
		workers.push(w)
	} 
	sc.send([c,'',response])//manda al broker 1 la informacion procesada

	if(!cont[w]){
		cont[w] = 0
	}else{
		cont[w] ++
	}
})

st.on('message',(clientes) => {
	contadorClientesLibres = clientes
})

setInterval(()=>{
	st.send(workers.length) //mando la canitdad de workers que tengo
},10)


setInterval(()=>{
	console.log("Total peticiones atendidas: " + totalPeticionesAtendidas + " || Clientes: "+ contadorClientesLibres)
	cont.forEach((i)=>{
		console.log("Workers: "+ cont[i])
	})
},5000)