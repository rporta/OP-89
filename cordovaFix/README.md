# cordovaFix 

Este fix se debe aplicar solo al path de `socketClientWap` 

## Requerimientos

 * cordova add platform android
 * cordova build android

## Pasos para realizar cordovaFix

 * copiar la carpeta cordova
 * pegar y reemplazar la carpeta cordova en `socketClientWap/`
 * abrir el proyecto en `android studios` y realizar sync con gradle

# www 

La carperta www, contiene algunas modificaciones de plugins, 
que van acompañados de `cordovaFix`

## Requerimientos

 * cordova add platform android
 * cordova build android 

## Pasos para realizar www 

 * copiar y pegar la carpeta plugins, js en `ROUTE`
 * agregar al script `cordova-app.js` en `ROUTE2`, los objetos `appJava`, `java`
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
* en `android studios` realizar build android

# Problemas con Gradle version

Si por casualidad falla al realizar cordova build android
la solucion es exportar una variable de entorno en `ROUTE3`, en mi caso solicito la version gradle-5.1.1-all.zip

```bashrc 
export CORDOVA_ANDROID_GRADLE_DISTRIBUTION_URL="https\\://services.gradle.org/distributions/gradle-5.1.1-all.zip"
```

## Referencias path

* `ROUTE` : `cordova/platforms/android/app/src/main/assets/www`
* `ROUTE2` : `socketClientWap/src/js`
* `ROUTE3` : `~/.bashrc`

# Instrucciones local para usar comandos local

## Requerimientos

* clonar `OP-89` en `/home/$USER/www/git`
* realizar los requerimientos de `cordovaFix`, `www`

## comando para copiar `cordovaFix`, `www`

* cp -R /home/$USER/www/git/OP-89/cordovaFix/cordova/ /home/$USER/www/git/OP-89/socketClientWap/ | cp -R /home/$USER/www/git/OP-89/cordovaFix/www/plugins/ /home/$USER/www/git/OP-89/socketClientWap/cordova/platforms/android/app/src/main/assets/www/

