const zmq = require('zeromq')
let req = zmq.socket('req')
var id = ''+process.argv[3]
req.identity = id
var con = process.argv[2]
var respuesta = process.argv[4]
req.connect(''+con)//'tcp://127.0.0.1:9999')
req.on('message', (c, sep , msg)=>{
    setTimeout(()=>{
        req.send([c,'', ''+respuesta])//'resp'])
        console.log("Mensaje llegado: " + msg)
    },1000)
})
req.send(['','',''])

console.log("Conectando..")