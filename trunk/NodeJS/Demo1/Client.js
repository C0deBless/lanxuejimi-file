var net = require('net');
var Packet = require('./Packet');
var eventEmitter = require('events').EventEmitter;

var userId = 100003075441353;
var sig = "15c6befe59673e130c0b5a0fe99fbd20";
var bufferSize = 32768;

var userData = {};

var readBuffer = null;

var leftTopNS = "N62";
var leftTopEW = "W023";
var rightTopNS = 'N62';
var rightTopEW = "W019";
var leftBottomNS = 'N46';
var leftBottomEW = 'W023';
var rightBottomNS = "N46";
var rightBottomEW = "W019";

var Command = {
	C_LOGIN : 300,
	C_UDATAREQ : 301,
	S_OBJECT_UPDATE : 1405
};

function loadMapObject() {

}

function toByteArray(buffer, offset, length) {
	var bytes = [];
	for ( var i = 0; i < length; i++) {
		bytes[i] = buffer[i + offset];
	}
	return bytes;
}

function bytesToString(bytes) {
	var str = "";
	for ( var i = 0; i < bytes.length; i++) {
		str += String.fromCharCode(bytes[i]);
	}
	return str;
}

function putByteToBuffer(buffer, offset, bytes) {
	for ( var index = 0; index < bytes.length; index++) {
		buffer.writeUInt8(bytes[index], index + offset);
	}
}

function parsePacket(data) {

	// console.log("receive binary:", data);

	var packetSize = 0;
	if (readBuffer === null) {
		readBuffer = new Buffer(data);
	} else {
		var offset = readBuffer.length;
		var tmpBuffer = new Buffer(readBuffer.length + data.length);
		readBuffer.copy(tmpBuffer);
		readBuffer = tmpBuffer;
		putByteToBuffer(readBuffer, offset, data);
	}

	for (;;) {

		if (readBuffer === null) {
			break;
		}
		packetSize = readBuffer.readInt32BE(0);

		if (readBuffer.length >= packetSize) {
			var cmd = readBuffer.readInt16BE(4);
			// var json = readBuffer.toString("utf8", 6, buffer.length);
			var bytes = toByteArray(readBuffer, 6, packetSize - 6);

			var packet = new Packet(cmd, bytes);
			handlePacket(packet);

			if (readBuffer.length > packetSize) {
				readBuffer = readBuffer.slice(packetSize, readBuffer.length);
			} else if (readBuffer.length == packetSize) {
				readBuffer = null;
			} else {

			}
		} else {
			console.log("quit loop,", packetSize, readBuffer.length);
			break;
		}
	}
}

function handlePacket(packet) {
	var cmd = packet.cmd;
	var data = packet.data;
	console.log("packet:", cmd, bytesToString(data));

	if (cmd == 1300) {
		sendPacket(Command.C_UDATAREQ, "");
	} else if (cmd == 1301) {
		var strJson = bytesToString(data);
		console.log("recieve user data:", strJson);
		var jsonData = JSON.parse(strJson);
		userData = jsonData;
		console.log("energy=" + userData.commander.energy);
	} else if (cmd == Command.S_OBJECT_UPDATE) {
		var buffer = new Buffer(data.length);
		putByteToBuffer(buffer, 0, data);
		var index = 0;
		var res = buffer.readInt32BE(index);
		index += 4;
		var clear = buffer.readInt32BE(index);
		index += 4;
		var length = buffer.readInt32BE(index);
		
	}
}

function sendPacket(cmd, data) {
	var packetSize = data.length + 4 + 2;
	var buffer = new Buffer(bufferSize);
	buffer.writeInt32BE(packetSize, 0);
	buffer.writeInt16BE(cmd, 4);
	buffer.write(data, 6);
	var str = buffer.toString("utf-8", 0, packetSize);
	client.write(str);
	console.log("send packet:", cmd, data);
}

var client = net.connect(8100, "www.easymode.com", function() {
	console.log("client connected,");

	client.on('data', function(data) {
		parsePacket(data);
	});
	client.on('end', function() {
		console.log('client disconnected');
	});
	client.on('close', function() {
		console.log('tcp connection closed');
	});

	sendPacket(300, "{\"user_id\":" + userId + ",\"sig\":\"" + sig + "\"}");

});