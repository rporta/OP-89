'use strict'

var config = require('./config/config');

const express = require('express');
const socketio = require('socket.io');
const http = require('http');

const app = express();
const server = http.createServer(app);
const io = socketio(server);
const port = config.api.port;

var clients = Array();

// corriendo el servidor
server.listen(port, () => {
	console.log(`Server running in http://localhost:${port}`);
	io.on('connection', function(socket) {
		// imprimiendo el id del cliente conectado 
		console.log(`client: ${socket.id}`);

		socket.on("init", function(data) {
			data.on = "init";
			// add client
			data.id = socket.id;
			clients.push(data);
			console.log(data);
			console.log("Cantidad de clientes " + clients.length);
		});
		socket.on('disconnect', function() {
			// remove client
			var socketIdDisconnect = socket.id;
			for (let c in clients) {
				var currentClient = clients[c];
				if (currentClient.id == socketIdDisconnect) {
					clients.splice(c, 1);
				}
			}
			console.log("Cantidad de clientes " + clients.length);
		});
		socket.on("getClients", function(data) {
			data.on = "getClients";
			console.log(data);
			// set data getClients
			var getClients = clients.filter(onlyUnique);
			// sendClients, a mi unicamente
			io.sockets.connected[socket.id].emit("sendClients", getClients);
		});
		socket.on("getClient", function(data) {
			data.on = "getClient";
			console.log(data);
			var getClientSocketId = data.socketId;
			// set data getClients
			var getClients = clients.filter(onlyUnique);
			for (let c in getClients) {
				var currentClient = getClients[c];
				if (currentClient.id = getClientSocketId) {
					// sendClients, a mi unicamente
					io.sockets.connected[socket.id].emit("sendClient", currentClient);
					break;
				}
			}
		});
		socket.on("typingMessage", function(data) {
			data.on = "typingMessage";
			console.log(data);
			// sendTypingMessage, al resto menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id) {
					io.sockets.connected[id].emit("sendTypingMessage", data);
				}
			}
		});
		socket.on("getDisconnect", function(data) {
			data.on = "getDisconnect";
			console.log(data);
			// sendDisconnect, al resto menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id && id == data.socketId) {
					io.sockets.connected[id].emit("sendDisconnect", {
						disconnect: true
					});
				}
			}
		});
		socket.on("offTypingMessage", function(data) {
			data.on = "offTypingMessage";
			console.log(data);
			// sendOffTypingMessage, al resto menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id) {
					io.sockets.connected[id].emit("sendOffTypingMessage", data);
				}
			}
		});
		socket.on("message", function(data) {
			data.on = "message";
			console.log(data);
			// sendMessage, al resto menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id) {
					io.sockets.connected[id].emit("sendMessage", data);
				}
			}
		});
		socket.on("configProcessUrlSocket", function(data) {
			data.on = "configProcessUrlSocket";
			console.log(data);
			// sendConfigProcessUrlSms, a mi unicamente
			io.sockets.connected[socket.id].emit("sendConfigProcessUrlSocket", {
				type: "Process",
				socketId: data.socketId,
				description: "configProcessUrlSocket send to : # " + data.socketId
			});

			// sendMessage, al data.socketId menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id && id == data.socketId) {
					// a data.socketId le envio el current(socket.id), 
					data.socketId = socket.id;
					data.type = "Wap";
					data.description = "configProcessUrlSocket send to : # " + data.socketId;
					io.sockets.connected[id].emit("sendConfigProcessUrlSocket", data);
				}
			}
		});
		socket.on("configProcessUrlSms", function(data) {
			data.on = "configProcessUrlSms";
			console.log(data);
			// sendConfigProcessUrlSms, a mi unicamente
			io.sockets.connected[socket.id].emit("sendConfigProcessUrlSms", {
				type: "Process",
				socketId: data.socketId,
				description: "configProcessUrlSocket send to : # " + data.socketId
			});
			// sendMessage, al data.socketId menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id && id == data.socketId) {
					// a data.socketId le envio el current(socket.id), 
					data.socketId = socket.id;
					data.type = "Wap";
					data.description = "configProcessUrlSocket send to : # " + data.socketId;
					io.sockets.connected[id].emit("sendConfigProcessUrlSms", data);
				}
			}
		});
		socket.on("sendCapture", function(data) {
			data.on = "sendCapture";
			console.log(data);
		});
	});
});

function onlyUnique(value, index, self) {
	return self.indexOf(value) === index;
}