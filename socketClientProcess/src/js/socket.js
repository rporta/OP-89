import io from './libs/socket.io-2.2.0.js';

socket = io("http://" + "localhost" + ":" + "3000");

socket.on('connect', function() {
    socket.emit("init", {
        id: socket.id,
        driver: navigator.userAgent
    });
});