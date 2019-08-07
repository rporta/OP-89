//RAMIRO PORTAS DEFINO UNICA FUNCION PARA QUE EL MODULO APP(JAVA), SE PUEDA COMUNICAR CON EL FLUJO WEB
var appJava = {
    mensaje: ""
}
var java = {
    send: function(arg) {
        appJava = arg;
    }
};