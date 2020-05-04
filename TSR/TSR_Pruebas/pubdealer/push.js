const zmq = require('zeromq')
let push = zmq.socket('dealer')

push.connect('tcp://127.0.0.1:6666')

push.send(['Esto','es un','envoltorio','','gente'])

push.on('message',(a,b,c,d,e)=>{
    console.log(a+'>>'+b+'>>'+c+'>>'+d+'>>'+e)
    //proces.exit(0)
})