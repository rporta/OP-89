<template>
<f7-app :params="f7params" theme-dark>
  <!-- Status bar overlay for fullscreen mode-->
  <f7-statusbar></f7-statusbar>
  <!-- Right panel with reveal effect-->
  <f7-panel>
    <f7-view url="/rightPanel/">
    </f7-view>
  </f7-panel>
  <!-- Your main view, should have "view-main" class -->
  <f7-view main class="safe-areas" url="/"></f7-view>
  <!-- Popup -->
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
            swipe: 'right',
            effect: 'reveal',
            leftBreakpoint: 800,
          },   
          // App root data
          data: function () {
            return {
              perfil: {
                name: config.perfil.name,
                avatar: config.perfil.avatar,
              },
              config : config,
              processUrl: null,
              appJava: null,
              sendConfigProcessUrlSocket: null,
              sendClient: null,
              disconnect: null,
              sendClients: null,
              sendTypingMessage: null,
              sendOffTypingMessage: null,
              connect: null,
              sendMessage: null,
              img : null,
              appJava : null
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
        // Login screen data
        // Config
        config : config,
        configDefault : configDefault

      }
    },
    methods: {
      getF7(){
        return this.$f7;
      },
      redirectTo(path){
        this.getF7().view.main.router.navigate(path);
        this.getF7().panel.close();
      },      
      generateColor(color){
        return {
          "background-color": color  + "!important"
        };
      },
      alertLoginData() {
        this.$f7.dialog.alert('Username: ' + this.username + '<br>Password: ' + this.password);
      },
      resetDefaultConfig(){
        var configProcessUrl = this.$refs.configProcessUrl.$el;
        var getDataForm = this.$f7.form.convertToData(configProcessUrl);
        const configDefaultString = JSON.stringify(this.configDefault);
        const configDefaultJSON = JSON.parse(configDefaultString); 
        this.$f7.form.fillFromData(configProcessUrl, configDefaultJSON.processURL);
        this.config = configDefaultJSON;
      },
      resolverClickSocket(){
        var configProcessUrl = this.$refs.configProcessUrl.$el;
        var getDataForm = this.$f7.form.convertToData(configProcessUrl);
        // Send socket
        var self = this;      
        // socket.emit("configProcessUrlSocket", Object.assign({
        //   socketId : self.socketId,
        // }, getDataForm));//<- no va, porque :
        // hay que enviar la data f7->AppJava para que envie el evento al socketServer
      },
      resolverClickSms(){
        var configProcessUrl = this.$refs.configProcessUrl.$el;
        var getDataForm = this.$f7.form.convertToData(configProcessUrl);
        console.log(getDataForm);
        var self = this;
        // Send socket
        // socket.emit("configProcessUrlSms", Object.assign({
        //   socketId : self.socketId,
        // }, getDataForm));//<- no va, porque :
        // hay que enviar la data f7->AppJava para que envie el evento al socketServer
      },
      getInfoDevice(){
        var device = this.getF7().device;
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
        var self = this;
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);

          // sendDataModuleApp: socket init 
          var i = window.cordova.InAppBrowser.open("sendDataModuleApp");
          i.sendDataModuleApp({
              type: "socket",
              event: "init",
              data : {
                id: "",
                host: self.getF7().data.config.api.host,
                port: self.getF7().data.config.api.port,
                type: self.config.type,
                wifi: "on",
                driver:  self.getInfoDevice()
              }
          });
        }
        // Set Dom7 style, events

        // Set socket on
        this.getF7().on("sendConfigProcessUrlSocket", function(data) {
          if(data.type == "Wap"){
            self.getF7().data.processUrl = data;            
            self.redirectTo('/getProcessUrl/' + data.socketId);
          }
        });

        // Set socket 
        this.getF7().on("sendDisconnect", function(data){
          console.log(data);
          if(data.disconnect){
            socket.disconnect();
          }
        });

      }); 
    }    
  }
</script>