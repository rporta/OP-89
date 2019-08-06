# cordovaFix 

Este fix se debe aplicar solo al path de socketClientWap, 

## Requerimientos

 * platform android
 * build android

## Pasos para realizar el fix 

 * copiar la carpeta cordova
 * pegar y reemplazar la carpeta cordova en socketClientWap/
 * abrir el proyecto en android studios y realizar sync con gradle

# www 

La carperta www, contiene algunas modificaciones de plugins, 
que van acompañados de #cordovaFix.

## Requerimientos

 * platform android
 * build android

## Pasos para realizar el fix 

 * copiar y pegar la carpeta plugins, js en ROUTE
 * agregar en cordova.js ROUTE, los objetos 'appJava', 'java' del archivo www/cordova.js

ROUTE : cordova/platforms/android/app/src/main/assets/www
