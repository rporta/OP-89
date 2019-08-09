import io from './libs/socket.io-2.2.0.js';
import config from '../config/config.json';

// socket = io("http://" + config.api.host + ":" + config.api.port);
socket = io();