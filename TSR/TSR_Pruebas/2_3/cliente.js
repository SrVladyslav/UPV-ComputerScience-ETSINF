const zmq = require('zeromq')
let req = zmq.socket('req')

var port = process.argv[2]
var nic = process.argv[3]
var prti = process.argv[4]
req.connect(''+port)//'tcp://127.0.0.1:9998')
req.identity = nic+''
req.on('message', (msg)=>{
    console.log('resp: '+msg)
    process.exit(0)
})

req.send(''+prti)//'Hola')// ['',hola]
console.log("Conectando..")