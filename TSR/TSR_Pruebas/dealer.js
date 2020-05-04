const zmq = require('zeromq')
let s = zmq.socket('dealer')

s.bind('tcp://*:6666')
//s.send('hola')
s.on('message', (s1,b,a)=>{
    console.log(s+b+a)
    s.send([s1,b,a])
})

s.send(['','hola'])
