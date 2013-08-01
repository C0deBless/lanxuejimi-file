var net = require('net');

// var host = "0.0.0.0";
// var PORT = 9000;
//
// // 创建一个TCP服务器实例，调用listen函数开始监听指定端口
// // 传入net.createServer()的回调函数将作为”connection“事件的处理函数
// // 在每一个“connection”事件中，该回调函数接收到的socket对象是唯一的
// var server = net.createServer(function(sock) {
//
// // 我们获得一个连接 - 该连接自动关联一个socket对象
// console.log('CONNECTED: ' + sock.remoteAddress + ':' + sock.remotePort);
//
// // 为这个socket实例添加一个"data"事件处理函数
// sock.on('data', function(data) {
// console.log('DATA ' + sock.remoteAddress + ': ' + data);
// // 回发该数据，客户端将收到来自服务端的数据
// sock.write('You said "' + data + '"');
// });
//
// // 为这个socket实例添加一个"close"事件处理函数
// sock.on('close', function(data) {
// console.log('CLOSED: ' + sock.remoteAddress + ' ' + sock.remotePort);
// });
//
// });
//
// server.listen(PORT, host, function() {
// console.log("tcp server created at port " + PORT + ", addr: %j", server
// .address());
// });

var json = "{\"user_id\":100003075441353,\"sig\":\"f5d7598f260f4fe83cc2e6167f6adff8\"}";
var bufferSize = 300000;

var client = net.connect(8000, "www.easymode.com", function() {
	console.log("client connected,");

	client.on('data', function(data) {
		var buffer = new Buffer(data);
		var packetSize = buffer.readInt32BE(0);
		var cmd = buffer.readInt16BE(4);
		var json = buffer.toString("utf8", 6, buffer.length);
		console.log("data:", packetSize, cmd, json);

		if (cmd == 1300) {
			
		}
	});
	client.on('end', function() {
		console.log('client disconnected');
	});
	client.on('close', function() {
		console.log('tcp connection closed');
	});

	var packetSize = json.length + 4 + 2;
	var buffer = new Buffer(bufferSize);
	buffer.writeInt32BE(packetSize, 0);
	buffer.writeInt16BE(300, 4);
	buffer.write(json, 6);
	var str = buffer.toString("utf-8", 0, packetSize);
	client.write(str);

	console.log("send login packet,", packetSize, str);
});