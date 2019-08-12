<template>
  <f7-app :params="f7params" theme-dark>
    <f7-statusbar></f7-statusbar>
    <f7-panel right swipe>
      <f7-view url="/rightPanel/">
      </f7-view>
    </f7-panel>  
    <f7-panel left >
      <f7-view url="/leftPanel/">
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
      }
    },
    mounted() {
      this.$f7ready((f7) => {

        //add method
        f7.redirectTo = (path) =>{
          f7.view.main.router.navigate(path);
          f7.panel.close();
        }   

        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);
        }
        // Set Dom7 style, events

        // Set socket on
        var self = this;

        socket.on('connect', function() {
          var initData = {
            id: socket.id,
            type: f7.data.config.type,
            wifi: "on",
            driver:  self.getInfoDevice()
          };
          socket.emit("init", initData);
        });


        socket.on("sendConfigProcessUrlSocket", function(data) {
          if(data.type == "Wap"){
            f7.data.processUrl = data;            
            f7.redirectTo('/getProcessUrl/' + data.socketId);
          }
        });

        // Set socket 
        socket.on("sendDisconnect", function(data){
          console.log(data);
          if(data.disconnect){
            socket.disconnect();
          }
        });
      }); 
    }    
  }
</script>