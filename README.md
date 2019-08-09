## Puntos pendientes a realizar

* Mover implementacion socketClientWap al package (Java) com.socketImplement.socketConection
* Desde el flujo Web, comunicarse al package io.framework7.myapp.MainActivity, para solicitar informacion por socket al client socketClientProcess. 

: javascript -> java -> socket.emit(socketClientProcess)

* Desde el package com.socketImplement.socketConection, implemetar el comportamiento para la informacion que llega del origen socketClientProcess, trabajar con el intercambio de flujos con loadUrl(), finalmente enviar desde io.framework7.myapp.MainActivity codigo javascript al flujo build cordova's `www` <- (socketClientWap/src/: f7), para que f7 se comporte en funcion de los datos que recibe por socketClientProcess, y ademas el usuario pueda explotar las capacidades de f7
* En el package io.framework7.myapp.MainActivity, en relacion con ^, implementar el control del method io.framework7.myapp.MainActivity::loadUrl()
: El method ^loadUrl(), permite cargar la URL remota, y cargar el build cordova's `www` <- (socketClientWap/src/: f7), ambos desde el webView
* realizar merge de las funcionalidades sms, en mod_01 (old), al socketClientWap/src/: f7
* implementar google + API, (load perfil, configApp.vue)
* implementar chat individual(socketClientWap <-> socketClientProcess), la interfaz esta lista, solo hay que trabajar socket en los clientes y server.
* implementar intercambio de configApp (socketClientWap <-> socketClientProcess), la interfaz esta lista, solo hay que trabajar socket en los clientes y server.
* En socketClientWap/src/: f7, crear vista, 
* En socketClientWap/src/: f7, crear componente, processURLConsole
* En socketClientWap/src/: f7, crear componente, processURLPanel
* En socketClientWap/src/: f7, crear componente, processURLForm
: En la Pagina processURLForm, implementar los methods :
* mover item (realizar click y mantener, para mover la posicion del item sobre la lista)
* borrar item 
* agragar item
* modificar text item
* modificar click (reacer click regenerar data x y)
* enviar formulario
* truncar formulario
* implementar socket.emit data processURLForm al socketClientWap, por medio de socketServer
: ^ fin implementacion methods 
* En socketClientWap/src/: f7, crear pagina, que cargue los componente processURLConsole, processURLPanel, processURLForm
* En socketClientWap/src/: f7, agregar a la pagina PanelMenu:Principal, los links a los componentes processURLConsole, processURLPanel, processURLForm
* Implementar la funcionalidad de envio de sms 


#Problemas de comunicacion f7 APP(JAVA)

##Problemas con comunicacion f7->APP(JAVA)

Para resolver este problema existen 2 vias

* cordova-broadcaster
: para utlizar cordova-broadcaster, se debe realizar modificaciones en f7 y APP(JAVA)
```f7
window.broadcaster.fireNativeEvent( "test.event", { item:'test data' }, function() {
    console.log( "event fired!" );
    } );
```
```APP(JAVA)
final BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getExtras().getString("data");

        Log.d("CDVBroadcaster",
                String.format("Native event [%s] received with data [%s]", intent.getAction(), data));

    }
};
LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, new IntentFilter("test.event"));
}
```
* cordova-plugin-inappbrowser
: para utlizar cordova-broadcaster, se debe realizar modificaciones en f7, en APP(JAVA) se recibe por onDataFW()
```f7

var i = window.cordova.InAppBrowser.open("sendDataModuleApp");
i.sendDataModuleApp({
  mensaje: "hola",
});
```

##Problemas con comunicacion APP(JAVA)->F7

Para resolver este problema existen 1 vias

* cordova-broadcaster
: para utlizar cordova-broadcaster, se debe realizar modificaciones en f7(`cordova-app.js`) y APP(JAVA)
```f7(cordova-app.js)
    window.broadcaster.addEventListener('onDataModuleJava', (data) => {
      alert(JSON.stringify(data));
    }, true);
```
```APP(JAVA)
    final Intent intent = new Intent("onDataModuleJava");

    Bundle b = new Bundle();
    b.putString( "mensaje", "hola" );
    intent.putExtras( b);

    LocalBroadcastManager.getInstance(self).sendBroadcastSync(intent);
```

#Problemas de comunicacion con componentes

##Solucion :

Para resolver este problema existen multiples vias, la mejor forma de resolverlo es crear data en f7, y trabajar con los eventos on, emit en los componentes desde la instancia f7, 

* `f7.emit(String, mix)`
* `f7.on(String, callback)`

```f7 example
f7.on('onFafa', function (data) {
  console.log(data); // {fafa: fafa}
})

// emit event
f7.emit('onFafa', {fafa: fafa});
```
#Modelo de datos con comunicacion f7 APP(JAVA)

##Modelo de datos a enviar APP(JAVA)->f7:

```modelo de datos
{
    type: String, 
    event: String,
    data: mix
}
```

##Valores de datos para los campos type socket

```type socket
{
    type: [socket]
    event: [sendConfigProcessUrlSocket,sendClient,disconnect,sendClients,sendTypingMessage,sendOffTypingMessage,connect,sendMessage]
}
```

##Valores de datos para los campos type data

```type data
{
    type: [data]
    event: [onData]
}
```
##Valores de datos para los campos type img

```type img
{
    type: [img]
    event: [onImg]
}
```