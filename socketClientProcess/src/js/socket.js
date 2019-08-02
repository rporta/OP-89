import io from './libs/socket.io-2.2.0.js';
import config from '../config/config.json';

socket = io("http://" + config.api.host + ":" + config.api.port);

socket.on('connect', function() {
    socket.emit("init", {
        id: socket.id,
        driver: navigator.userAgent
    });
});