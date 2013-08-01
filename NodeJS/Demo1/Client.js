var net = require('net');
var Packet = require('Packet');

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