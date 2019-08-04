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
    <f7-view url="/socketClient/">
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
  import Dom7 from 'Dom7';
  import cordovaApp from '../js/cordova-app.js';
  import routes from '../js/routes.js';
  export default {
    data() {
      return {
        // Framework7 Parameters
        f7params: {
          id: 'io.framework7.myapp', // App bundle ID
          name: 'socketClientProcess', // App name
          theme: 'auto', // Automatic theme detection
          panel: {
            swipe: 'right',
            effect: 'reveal',
            leftBreakpoint: 800,
          },   
          // App root data
          data: function () {
            return {
              user: {
                firstName: 'John',
                lastName: 'Doe',
              },

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
        username: '',
        password: '',
      }
    },
    methods: {
      alertLoginData() {
        this.$f7.dialog.alert('Username: ' + this.username + '<br>Password: ' + this.password);
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);
        }
        // Call F7 APIs here
        this.messagebar = this.$refs.messagebar.f7Messagebar;
        this.messages = this.$refs.messages.f7Messages;   
        this.camera = this.$refs.camera;   

        // Set Dom7 style, events
      }); 
    }    
  }
</script>