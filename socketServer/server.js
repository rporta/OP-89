'use strict'

var config = require('./config/config');

const express = require('express')
const socketio = require('socket.io')
const http = require('http')

const app = express()
const server = http.createServer(app)
const io = socketio(server)
const port = config.api.port

//corriendo el servidor
server.listen(port, () => {
    console.log(`Server running in http://localhost:${port}`);
    io.on('connection', function(socket) {
        //imprimiendo el id del cliente conectado 
        console.log(`client: ${socket.id}`);
        socket.on("init", function(data) {
            console.log(data);
        });
    });

})