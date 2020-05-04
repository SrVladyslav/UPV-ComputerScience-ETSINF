const zmq = require('zeromq')
let s = zmq.socket('pub')

s.bind('tcp://*:6666')

let canales = ['uno', 'dos', 'tres', 'cuatro']

function sendPost(){
    let msg = canales[0]
    s.send([msg, msg])
    console.log("Mandando "+ msg + "Args: "+ arguments[0])
    canales.shift()
    canales.push(msg)
}

setInterval(sendPost, 1000)