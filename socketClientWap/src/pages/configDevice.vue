<template>
  <f7-page name="ConfigDevice">
    <f7-navbar title="Configuracion del dispositivo" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-block-title>General </f7-block-title>
    <f7-list simple-list>
      <f7-list-item v-for="(a, i) in actions">
        <span>{{displayPermisos(a)}}</span>
        <f7-toggle :ref="a" color="green" @change="toggleSet(a, $event.target.checked)"></f7-toggle>
      </f7-list-item>
    </f7-list>    
  </f7-page>
</template>
<script>
  import Dom7 from 'dom7';
  import routes from '../js/routes.js';
  export default {
    data() {
      return {
        actions: ["WIFI"]
      };
    },
    methods: {
      resetDefaultConfig(){
        var formConfigPerfil = this.$refs.configPerfil.$el;
        var getDataForm = this.$f7.form.convertToData(formConfigPerfil);
        const configDefaultString = JSON.stringify(this.$f7.data.configDefault);
        const configDefaultJSON = JSON.parse(configDefaultString); 
        this.$f7.form.fillFromData(formConfigPerfil, configDefaultJSON.perfil);
        this.$f7.data.config = configDefaultJSON;
      },
      toggleSet(permission, e){
        switch (permission) {
          case 'WIFI': this.setWifi(e);
          break;
          case '3g/4g': this.set3g4g(e);
          break;
        }
      },
      setWifi(e){
        window.cordova.plugins.WifiManager.setWifiEnabled(e, function (err, success) {
        });
      },
      set3g4g(e){
        // aca la solucion es nativa, por tanto hay que realizar un brodcast a app(java)
        // app(java), recibe e intenta realizar la accion, realiza un brodcast a f7 y setea definitivamente
        var sendData = {
          data : e
        };
        window.broadcaster.fireNativeEvent("set3g4g", sendData);

      },
      displayPermisos(t){
        t = t.charAt(0).toUpperCase() + t.substr(1).toLowerCase();
        return t;
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {

        }
        // Call F7 APIs here

        // Set Dom7 style, events

        // Set socket on
        var self = this;
      }); 
    } 
  };
</script>