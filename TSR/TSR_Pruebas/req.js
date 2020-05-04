const zmq = require('zeromq')
let s = zmq.socket('rep')

s.connect('tcp://127.0.0.1:6666')
s.send('hola')
s.on('message', (...a)=>{
    console.log(a)
})
