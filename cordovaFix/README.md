# cordovaFix 

Este fix se debe aplicar solo al path de socketClientWap, 

## Requerimientos

 * platform android
 * build android

## Pasos para realizar cordovaFix

 * copiar la carpeta cordova
 * pegar y reemplazar la carpeta cordova en socketClientWap/
 * abrir el proyecto en android studios y realizar sync con gradle

# www 

La carperta www, contiene algunas modificaciones de plugins, 
que van acompañados de #cordovaFix.

## Requerimientos

 * platform android
 * build android

## Pasos para realizar www 

 * copiar y pegar la carpeta plugins, js en ROUTE
 * agregar al script cordova-app.js en ROUTE2, los objetos 'appJava', 'java'
```objetos
var appJava = {
  mensaje: ""
}
var java = {
  send: function(arg) {
    appJava = arg;
  }
};
```


ROUTE : cordova/platforms/android/app/src/main/assets/www
ROUTE2 : socketClientWap/src/js
