<template>
  <f7-page name="ConfigPermisos">
    <f7-navbar title="Configuracion de permisos" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-block-title>General </f7-block-title>
    <f7-list simple-list>
      <f7-list-item v-for="(p, i) in permissions">
        <span>{{displayPermisos(p)}}</span>
        <f7-button :ref="p" color="yellow" @click="toggleSet(p)">consutar</f7-button>
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
        permissions: this.$f7.data.config.android.permission
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
      toggleSet(permission){
        window.cordova.plugins.permissions.requestPermission(permission, (status) => {
          if(!status.hasPermission){
            // !OK : hasPermission
          }else{
              //  OK : hasPermission    
            }
          }, 
          (error) => {
          // error : hasPermission
        });
      },      
      displayPermisos(t){
        t = t.split(".")[2].replace(/_/g, " ").toLowerCase();
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