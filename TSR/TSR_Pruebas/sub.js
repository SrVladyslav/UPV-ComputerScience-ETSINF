const zmq = require('zeromq')
let s = zmq.socket('sub')

s.subscribe('uno')/*
s.subscribe('tres')
s.connect('tcp://127.0.0.1:6666')*/
s.connect('tcp://127.0.0.1:6666')
process.stdin.resume()
process.stdin.setEncoding('utf8')
process.stdin.on('data', (a)=>{
    console.log(a)
    //s.subscribe(''+a)
})

s.on('message', (a)=>{
    console.log(''+a)
})