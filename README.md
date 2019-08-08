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