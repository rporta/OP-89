<template>
  <f7-page name="processUrl">
    <f7-navbar title="Proceso URL" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
  </f7-page>
</template>
<script>
  import Dom7 from 'dom7';
  import cordovaApp from '../js/cordova-app.js';
  import routes from '../js/routes.js';
  import config from '../config/config.json';
  import configDefault from '../config/configDefault.json'; 
  export default {
    data() {
      return {
        config : config,
        configDefault : configDefault  
      };
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
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);
        }

        // Call F7 APIs here

        // Set Dom7 style, events

        // Set socket on
        var self = this;

      }); 
    },    
    computed: {
    }
  };
</script>