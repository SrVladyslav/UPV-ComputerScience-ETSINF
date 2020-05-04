const zmq = require('zeromq')

let sPush = zmq.socket('push') //enviara los mensajes
let sSub = zmq.socket('sub')   //recibira los mensajes

sPush.connect('tcp://127.0.0.1:9991')
console.log('conectando...')

sSub.subscribe('')//escuchara todo lo que pase por ahi
sSub.connect('tcp://127.0.0.1:9992')

//manifest
var nic = process.argv[2]
sPush.send([nic, 'HI'])

sSub.on('message', (usr, msg)=>{
    if(usr != nic) console.log("["+usr+"]: "+ msg)
})

process.stdin.resume()
process.stdin.on('end', ()=>{
    console.log("FIN")
    sPush.send([nic, 'BYE'])
    sPush.close()
    sSub.close()
})

process.on('SIGINT', ()=>{
    process.stdin.end()
})


process.stdin.on('data', (d)=>{
    sPush.send([nic, d.toString()])
})


