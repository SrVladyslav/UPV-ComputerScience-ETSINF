const zmq = require('zeromq')
let pub = zmq.socket('dealer')

pub.bind('tcp://*:6666')

pub.on('message', (s,a)=>{
    console.log(a)
    pub.send(['','a pesar de todo :)'])
})


//pub.send(['a pesar de todo :)'])





















var i = 0

let canales = ['uno', 'dos','tres']
console.log("conectando...")

/*
setInterval(()=>{
    var c = canales.shift()
    pub.send([c, i, 'hola'])
    canales.push(c)
    console.log("Canal: "+ c)
    i++
},1000)*/

