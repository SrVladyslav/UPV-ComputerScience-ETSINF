const zmq = require('zeromq')
let dealer = zmq.socket('req')

//var canal = process.argv[2]

//dealer.subscribe(canal)
dealer.connect('tcp://127.0.0.1:6666')

console.log("conectando...")
/*
dealer.on('message', (c,t,a)=>{
    console.log("Canal: " + c + " |  num: " + t + "  |  mensaje: "+ a)
})*/

dealer.send("hola")




/*
process.stdin.resume()
process.stdin.on('data', (d)=>{
    dealer.close()
})

dealer.on('close',()=>{
    dealer = zmq.socket('sub')
    dealer.subscribe('dos')
    dealer.connect('tcp://127.0.0.1:6666')
})

process.on('SIGINT',()=>{
    process.stdin.end()
})

process.stdin.on('end', ()=>{
    dealer.close()
})

dealer.on('message', (c,t,a)=>{
    console.log("Canal: " + c + " |  num: " + t + "  |  mensaje: "+ a)
})*/