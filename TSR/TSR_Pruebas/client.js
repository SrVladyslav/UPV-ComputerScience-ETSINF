const zmq = require('zeromq')
let req = zmq.socket('req');
let port = process.argv[2] || 97990
let repeticiones = process.argv[3] || 1

req.connect('tcp://localhost:'+ port) //97990
req.on('message', (msg)=> {
	console.log('resp: '+msg)
	process.exit(0);
})
for(let rep= 0;rep < repeticiones; rep++ ){
	setTimeout((r)=>{
		req.send("[Cliente]:" + rep)
		console.log(rep)
	}, 50*rep, rep)
}
//req.send(['a','b','c'])