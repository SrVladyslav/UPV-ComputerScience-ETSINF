const zmq = require('zeromq')
let cli = [], req = [], workers = []
let sc = zmq.socket('router')
let sw = zmq.socket('router')

var portc = process.argv[2] || 9998
var portw = process.argv[3] || 9999

sc.bind('tcp://*:'+ portc)
sw.bind('tcp://*:' + portw)

sc.on('message', (c,sep, m)=>{
    if(workers.length == 0){
        cli.push(c); req.push(m)
    }else{
        sw.send([workers.shift(), '', c, '', m])
    }
})

sw.on('message', (w,sep, c, sep2, r)=>{
    if(c == ''){
        console.log("New worker here!")
        workers.push(w)
        return
    }
    if(cli.length > 0){
        console.log("New job")
        sw.send([w, '', cli.shift(), '', req.shift()])
    } else {
        workers.push(w)
        console.log("No workers :(")
    }
    sc.send([c,'',r])
})

console.log("Conectando..")