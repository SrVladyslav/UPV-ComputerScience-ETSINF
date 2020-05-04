const zmq = require('zeromq')
let sPull = zmq.socket('pull') //recibira todos los mensajes y los reenviara
let sPub = zmq.socket('pub') 

sPull.bind('tcp://*:9991')
sPub.bind('tcp://*:9992')

console.log("Server conectado...")


sPull.on('message', (nick, msg)=>{
    console.log(nick + " envio: " + msg)
    switch (msg.toString()) {
        case 'HI':
            sPub.send(['SERVER', nick+' se conecto.'])
            break;
        case 'BYE':
            sPub.send(['SERVER', nick+'se desconecto'])
            break;
        default:
            sPub.send([nick, msg])
            break;
    }
})

