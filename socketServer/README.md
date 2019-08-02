# Instrucciones 

## Pasos para inciar socketServer

* npm install
* npm start

## Pasos para crear proyecto socketServer

* npm init
* npm i --save-dev nodemon 
* npm i --save socket.io express
* touch server.js
* vim server.js
:  copiar y pegar en server.js
```server.js
'use strict'

const express = require('express')
const socketio = require('socket.io')
const http = require('http')

const app = express()
const server = http.createServer(app)
const io = socketio(server)
const port = 3000

//corriendo el servidor
server.listen(port, () => {
	console.log(`Server running in http://localhost:${port}`);
	io.on('connection', function(socket) {
		console.log(`client: ${socket.id}`)
	})
})
```
* vim package.json
: copiar y pegar en package.json, 
```package.json
"scripts": {
	"start-dev": "nodemon server.js",
	"start": "node server.js"
},
```
* npm start

### Agregando al repositorio

* git init
: crear el repositorio y copiar la url
* git remote add [url]
* touch .gitignore
: copiar y pegar en .gitignore, 
```.gitignore
node_modules/
```
* touch .gitignore
* git add *
* git commit -m "init socketServer"
* git push

#### siguiendo practicas de https://medium.com/@eddydecena/servidor-real-time-con-socket-io-18e84d39d12b