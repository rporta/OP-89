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
			// add client
			clients.push(data);
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
		});
		socket.on("getClients", function(data) {
			// set data getClients
			var getClients = clients.filter(onlyUnique);
			// sendClients, a mi unicamente
			for (let id in io.sockets.connected) {
				if (id == socket.id) {
					io.sockets.connected[id].emit("sendClients", getClients);
				}
			}
		});

		socket.on("typingMessage", function(data) {
			// sendTypingMessage, al resto menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id) {
					io.sockets.connected[id].emit("sendTypingMessage", data);
				}
			}
		});
		socket.on("offTypingMessage", function(data) {
			// sendOffTypingMessage, al resto menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id) {
					io.sockets.connected[id].emit("sendOffTypingMessage", data);
				}
			}
		});
		socket.on("message", function(data) {
			// sendMessage, al resto menos current
			for (let id in io.sockets.connected) {
				if (id !== socket.id) {
					io.sockets.connected[id].emit("sendMessage", data);
				}
			}
		});
	});
});

function onlyUnique(value, index, self) {
	return self.indexOf(value) === index;
}