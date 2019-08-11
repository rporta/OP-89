<template>
  <f7-app :params="f7params" theme-dark>
    <!-- Status bar overlay for fullscreen mode-->
    <f7-statusbar></f7-statusbar>
    <!-- Right panel with reveal effect-->
    <f7-panel right swipe>
      <f7-view url="/rightPanel/">
      </f7-view>
    </f7-panel>  
    <f7-panel left >
      <f7-view url="/leftPanel/">
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
            swipe: "left",
            effect: 'reveal',
            // leftBreakpoint: 800,
          },   
          // App root data
          data: function () {
            return {
              perfil: {
                name: config.perfil.name,
                avatar: config.perfil.avatar,
              },
              processUrl: null
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
        socket.emit("configProcessUrlSocket", Object.assign({
          socketId : self.socketId,
        }, getDataForm));
      },
      resolverClickSms(){
        var configProcessUrl = this.$refs.configProcessUrl.$el;
        var getDataForm = this.$f7.form.convertToData(configProcessUrl);
        console.log(getDataForm);
        var self = this;
        // Send socket
        socket.emit("configProcessUrlSms", Object.assign({
          socketId : self.socketId,
        }, getDataForm));
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);
        }
        // Set Dom7 style, events

        // Set socket on
        var self = this;
        socket.on("sendConfigProcessUrlSocket", function(data) {
          if(data.type == "Wap"){
            self.getF7().data.processUrl = data;            
            self.redirectTo('/getProcessUrl/' + data.socketId);
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